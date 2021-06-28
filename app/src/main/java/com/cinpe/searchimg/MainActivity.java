package com.cinpe.searchimg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;

import com.beloo.widget.chipslayoutmanager.SpacingItemDecoration;
import com.cinpe.searchimg.adapter.BaseRecycleAdapter;
import com.cinpe.searchimg.adapter.BaseRecycleViewHolder;
import com.cinpe.searchimg.control.MainControl;
import com.cinpe.searchimg.databinding.ActivityMainBinding;
import com.cinpe.searchimg.databinding.ItemImgBinding;
import com.cinpe.searchimg.model.BaseModel;
import com.cinpe.searchimg.model.MainBean;
import com.cinpe.searchimg.model.MainModel;
import com.cinpe.searchimg.net.ObserverImpl;
import com.cinpe.searchimg.service.MainService;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends RxBaseActivity<ActivityMainBinding> implements MainControl {



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

            Log.d(TAG,"onServiceConnected");

            try {
                int a = aidlInterface.basicTypes(1, 3L, true, 3.6f, 6.7d,"666");

                Log.d(TAG,"服务中打印:"+a);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG,"onServiceDisconnected");
        }
    };


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
                mBinding.getModel().index.set(mBinding.getModel().index.get()+1);
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
        mBinding.setModel(new MainModel());
        mBinding.setControl(this);
    }

    /**
     * 点击搜索
     *
     * @param view
     */
    @Override
    public void onClickSearchBtn(View view) {

        mBinding.getModel().list.clear();
        mBinding.getModel().index.set(0);
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

            StaggeredGridLayoutManager staggeredGridLayoutManager =
                    new StaggeredGridLayoutManager(3,
                            StaggeredGridLayoutManager.VERTICAL);
            mBinding.searchResult.setLayoutManager(staggeredGridLayoutManager);

            mBinding.searchResult.setAdapter(new BaseRecycleAdapter<ItemImgBinding>(MainActivity.this,
                    R.layout.item_img, mBinding.getModel().list) {
                /**
                 * 完成item绑定的Binding的外挂设置.
                 *
                 * @param holder
                 * @param position
                 */
                @Override
                public void onPostBindViewHolder(@NonNull BaseRecycleViewHolder<ItemImgBinding> holder, int position) {
                    holder.getBinding().setModel((BaseModel) this.list.get(position));
                }

                /**
                 * 返回Binding.getModel()里的数据长度.
                 *
                 * @return
                 */
                @Override
                public int getItemCount() {
                    return this.list.size();
                }
            });
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
                        mBinding.getModel().name.get(),
                        mBinding.getModel().index.get(),
                        mBinding.getModel().count)

                //上游
                .subscribeOn(Schedulers.computation())
                //下游
                .observeOn(AndroidSchedulers.mainThread())
                //.observeOn(Schedulers.computation())

                .subscribe(new ObserverImpl<MainBean>() {
                    @Override
                    public void _onNext(MainBean p) {

                        p.getList().forEach(t -> {
                            BaseModel baseModel = new BaseModel();
                            baseModel.name.set(t.getTitle());
                            try {
                                baseModel.imageUri.set(Uri.parse(t.getThumb()));
                            } catch (Exception e) {
                            }

                            mBinding.getModel().list.add(baseModel);
                        });


                        mBinding.refreshLayout.finishRefresh();

                        if(p.getList()==null||p.getList().isEmpty()){
                            mBinding.refreshLayout.finishLoadMoreWithNoMoreData();
                        }else {
                            mBinding.refreshLayout.finishLoadMore();
                        }

                    }
                });
    }

    /**
     *
     */
}