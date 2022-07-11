package com.cinpe.searchimg;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;

import com.beloo.widget.chipslayoutmanager.SpacingItemDecoration;
import com.cinpe.searchimg.control.MainControl;
import com.cinpe.searchimg.database.model.BasePojo;
import com.cinpe.searchimg.databinding.ActivityMainBinding;
import com.cinpe.searchimg.databinding.ItemImgBinding;
import com.cinpe.searchimg.database.dao.BaseModelDao;
import com.cinpe.searchimg.database.model.BaseModel;
import com.cinpe.searchimg.databinding.ItemImgPojoBinding;
import com.cinpe.searchimg.lite_adapter.adapter.LiteAdapter;
import com.cinpe.searchimg.lite_adapter.adapter.LiteViewHolder;
import com.cinpe.searchimg.model.MainBean;
import com.cinpe.searchimg.model.MainModel;
import com.cinpe.searchimg.net.ObserverImpl;
import com.cinpe.searchimg.database.AppDatabase;
//import com.cinpe.searchimg.sqlite.User;
//import com.cinpe.searchimg.sqlite.UserDao;
import com.cinpe.searchimg.repository.GroupData;
import com.cinpe.searchimg.repository.ItemData;
import com.cinpe.searchimg.repository.MyRepository;
import com.jakewharton.rxbinding4.recyclerview.RxRecyclerView;
import com.jakewharton.rxbinding4.widget.RxTextView;
import com.jakewharton.rxbinding4.widget.TextViewBeforeTextChangeEvent;
import com.orhanobut.logger.Logger;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.CompletableSource;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;
import kotlin.jvm.functions.Function1;

@AndroidEntryPoint
public class MainActivity extends RxBaseActivity<ActivityMainBinding> implements MainControl {

    /**
     * 数据库
     */
    @Inject
    AppDatabase db;

    MyRepository repository;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }


    /**
     * 测试服务连接
     */
    private AidlInterface aidlInterface;

    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

            aidlInterface = AidlInterface.Stub.asInterface(service);

            Log.d(TAG, "onServiceConnected");
            try {
                int a = aidlInterface.basicTypes(1, 3L, true, 3.6f, 6.7d, "666");

                Log.d(TAG, "服务中打印:" + a);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "onServiceDisconnected");
        }
    };


    /**
     * Returns the {@link ViewModelStore} associated with this activity
     * <p>
     * Overriding this method is no longer supported and this method will be made
     * <code>final</code> in a future version of ComponentActivity.
     *
     * @return a {@code ViewModelStore}
     * @throws IllegalStateException if called before the Activity is attached to the Application
     *                               instance i.e., before onCreate()
     */
    @NonNull
    @Override
    public ViewModelStore getViewModelStore() {
        return super.getViewModelStore();
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {


        //初始化listView
        initListView();

        //滑动回调.
        mBinding.refreshLayout.setEnableAutoLoadMore(true);
        mBinding.refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                onClickSearchBtn(null);
            }
        });
        mBinding.refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                int next = repository.index.getValue().intValue();
                repository.index.setValue(next + repository.count.getValue());
                requestSearch();
            }
        });


        ////测试绑定服务
        //Intent intent = new Intent(this, MainService.class);
        //bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);

    }

    /**
     * 绑定ViewModel
     *
     * @param savedInstanceState
     */
    @Override
    protected void setModel(Bundle savedInstanceState) {
        //来来来,
        repository = new ViewModelProvider(this).get(MyRepository.class);
        mBinding.setLifecycleOwner(this);
        mBinding.setModel(repository);
        mBinding.setControl(this);
    }

    /**
     * 点击搜索
     *
     * @param view
     */
    @Override
    public void onClickSearchBtn(View view) {
        repository.index.setValue(0);
        requestSearch();
    }

    /**
     * 点击退出
     *
     * @param view
     */
    @Override
    public void onClickBack(View view) {
        onBackPressed();
    }

    /**
     * 刷新listView
     */
    private void initListView() {
        if (mBinding.searchResult.getAdapter() == null) {

            SpacingItemDecoration decoration = new SpacingItemDecoration(5, 5);
            mBinding.searchResult.addItemDecoration(decoration);

            //StaggeredGridLayoutManager manager =
            //        new StaggeredGridLayoutManager(3,
            //                StaggeredGridLayoutManager.VERTICAL);
            GridLayoutManager manager = new GridLayoutManager(this, 3);
            mBinding.searchResult.setLayoutManager(manager);



            LiteAdapter<BasePojo, ItemImgPojoBinding> adapter = new LiteAdapter<BasePojo, ItemImgPojoBinding>() {

                @NonNull
                @Override
                public LiteViewHolder<ItemImgPojoBinding> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                    LiteViewHolder<ItemImgPojoBinding> bindingLiteViewHolder = super.onCreateViewHolder(parent, viewType);

                    //bindingLiteViewHolder.getBinding().getPojo().observe(bindingLiteViewHolder, new Observer<BasePojo>() {
                    //    @Override
                    //    public void onChanged(BasePojo pojo) {
                    //        if(!Objects.equals(bindingLiteViewHolder.getBinding().title.getText().toString(),pojo.getName())){
                    //            Logger.i("[name是不同的][observe]" + bindingLiteViewHolder.getBinding().title.getText().toString() + ":" + pojo.getName());
                    //
                    //            bindingLiteViewHolder.getBinding().title.setText(pojo.getName());
                    //        }
                    //    }
                    //});


                    RxTextView.textChanges(bindingLiteViewHolder.getBinding().title)
                            .skipInitialValue()
                            .debounce(2000, TimeUnit.MILLISECONDS)
                            .skipWhile(t ->
                                    {
                                        boolean b=bindingLiteViewHolder.getBinding().getPojo().getValue() == null ||
                                                TextUtils.equals(bindingLiteViewHolder.getBinding().getPojo().getValue().getName(), t);
                                        if(!b){
                                            Logger.i("[name是不同的]:" + Objects.requireNonNull(bindingLiteViewHolder.getBinding().getPojo().getValue()).getName()+","+t);
                                        }else {
                                            Logger.i("[name是不同的]为假被过滤:" +t);
                                        }
                                        return b;
                                    }
                            )
                            .map(CharSequence::toString)
                            .concatMapCompletable(t ->
                                    repository.pojoDao.update(Objects.requireNonNull(bindingLiteViewHolder.getBinding().getPojo().getValue()).getuId(), t)
                                            .subscribeOn(Schedulers.io())
                                            .doOnComplete(() -> Logger.i("[name是不同的]name更新成功:" + t))
                                            .doOnError(throwable -> Logger.i("name更新失败" + throwable.getMessage()))

                            )
                            .subscribe();

                    return bindingLiteViewHolder;
                }

                @Override
                public void onPostBindViewHolder(@NonNull LiteViewHolder<ItemImgPojoBinding> holder, int position,
                                                 @NonNull List<Object> payloads) {
                    if (!Objects.equals(holder.getBinding().getPojo().getValue(), getItem(position))) {
                        BasePojo basePojo = getItem(position);
                        holder.getBinding().getPojo().postValue(basePojo);

                        //holder.getBinding().getVmPojo().postPojo(basePojo);




                        //if (holder.getBinding().getName() == null) {
                        //
                        //    holder.getBinding().getPojo().observe(holder,p->{
                        //        MutableLiveData<String> name =
                        //                (MutableLiveData<String>) Transformations.map(holder.getBinding().getPojo(), BasePojo::getName);
                        //        holder.getBinding().setName(name);
                        //        name.postValue(p.getName());
                        //        name.observe(holder, s ->
                        //                {
                        //                    if (!Objects.equals(s, basePojo.getName())) {
                        //                        basePojo.setName(s);
                        //                        Single.just(s)
                        //                                .delay(2000, TimeUnit.MILLISECONDS, Schedulers.single())
                        //                                .concatMapCompletable(s1 -> {
                        //                                    if (!Objects.equals(s1, basePojo.getName())) {
                        //                                        return null;
                        //                                    } else {
                        //                                        return repository.pojoDao.update(basePojo.getUId(), s1).subscribeOn(Schedulers.io());
                        //                                    }
                        //
                        //                                })
                        //                                .subscribe();
                        //
                        //                    }else {
                        //                        Logger.i("[出现了]setName:又一次");
                        //                    }
                        //                }
                        //        );
                        //    });
                        //
                        //
                        //}
                    }


                }
            };
            mBinding.searchResult.setAdapter(adapter);
            repository.listData.observe(this, basePojos -> adapter.submitList(basePojos, () -> Logger.i("size:" + adapter.getItemCount() + ",foreList:" + adapter.getCurrentList())));

        }
    }

    /**
     * 接口请求
     */
    public void requestSearch() {

        APP
                .getsInstance()
                .getApi()
                .searchLikeTypesUsingGET(
                        repository.searchStr.getValue(),
                        repository.index.getValue(),
                        repository.index.getValue() + repository.count.getValue())

                //上游
                .subscribeOn(Schedulers.computation())
                //下游
                .observeOn(Schedulers.computation())
                .doOnNext(mainBean -> {
                    mBinding.refreshLayout.finishRefresh();
                    if (mainBean.getList() == null || mainBean.getList().isEmpty()) {
                        mBinding.refreshLayout.finishLoadMoreWithNoMoreData();
                    } else {
                        mBinding.refreshLayout.finishLoadMore();
                    }
                })
                .map(mainBean -> mainBean.getList().stream().map(listBean -> BasePojo.builder()
                        .uId(listBean.getId())
                        .name(listBean.getTitle())
                        .nameEn(listBean.getLitetitle())
                        .createdTime(System.currentTimeMillis())
                        .imageUri(listBean.getThumb()).build()).collect(Collectors.toList()))
                .concatMapCompletable(l ->
                        {
                            Logger.i("Https:" + l);
                            return db.pojoDao().insert(l);
                        }
                )
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.i("MainActivity", "onSubscribe");
                    }

                    @Override
                    public void onComplete() {
                        Log.i("MainActivity", "onComplete");
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.i("MainActivity", "onError:" + e.getMessage());
                    }
                });

    }

}