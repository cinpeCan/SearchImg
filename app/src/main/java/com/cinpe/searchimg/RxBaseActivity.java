package com.cinpe.searchimg;

import android.os.Bundle;
import android.os.Handler;
import android.os.Process;
import android.view.KeyEvent;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;


/**
 * Created by wneng on 17/10/18.
 */

public abstract class RxBaseActivity<B extends ViewDataBinding> extends AppCompatActivity {


    public final String TAG = getClass().getSimpleName();

    protected B mBinding;

    public B getBinding() {
        return mBinding;
    }

    /**
     * 是否已经点击了一次返回键
     */
    protected boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        //if(APP.isFirst){
        //    APP.isFirst=false;
        //    startActivity(new Intent(this,SplashActivity.class));
        //}

        //关联布局内容
        initBinding(savedInstanceState);

        //初始化控件
        mBinding.getRoot().post(new Runnable() {
            @Override
            public void run() {
                initViews(savedInstanceState);
            }
        });



    }


    /**
     * 初始化binding
     */
    private void initBinding(Bundle savedInstanceState) {
        mBinding = DataBindingUtil.setContentView(this, getLayoutId());
        setModel(savedInstanceState);
        mBinding.executePendingBindings();
    }


    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onDestroy() {

        super.onDestroy();

    }

    public abstract int getLayoutId();

    protected abstract void initViews(Bundle savedInstanceState);

    /**
     * 绑定ViewModel
     */
    protected abstract void setModel(Bundle savedInstanceState);




    @Override
    public void onBackPressed() {
        doBack(KeyEvent.KEYCODE_BACK, null);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 按下的如果是BACK，同时没有重复
        if (KeyEvent.KEYCODE_BACK == keyCode && 0 == event.getRepeatCount()) {
            doBack(keyCode, event);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }




    /**
     * 点击返回键的处理
     */
    protected void doBack(int keyCode, KeyEvent event) {

        if (doubleBackToExitPressedOnce) {

            Exit();

        } else {

            if (isTaskRoot()) {
                doubleBackToExitPressedOnce = true;
                long delaymillis = 300;
                Toast.makeText(this,"再按一下退出",Toast.LENGTH_SHORT).show();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        doubleBackToExitPressedOnce = false;
                    }
                }, delaymillis);
            } else {

                if(!onCustomBack()){
                    //如果不拦截.
                    super.onBackPressed();
                }



            }


        }

    }

    /**
     * 自定义的返回上一页的回调.
     */
    protected boolean  onCustomBack(){


        return false;
    }


    /**
     * 退出应用
     */
    private void Exit() {
        finish();
        Process.killProcess(Process.myPid());
    }


}
