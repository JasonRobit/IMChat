package com.jackliu.im.controller.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.hyphenate.chat.EMClient;
import com.jackliu.im.R;
import com.jackliu.im.model.Model;
import com.jackliu.im.model.bean.UserInfo;

import java.util.HashMap;

//欢迎页面

public class SplashActivity extends Activity {

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            //如果当前activity已经退出，那么不处理handler中的消息直接返回。
            if (isFinishing()) {
                return;
            }

            //判断进入主页面还是登录页面
            toMainOrLogin();

        }
    };

    //判断进入主页面还是登录页面
    private void toMainOrLogin() {
//    new Thread(){
//        @Override
//        public void run() {
//            //判断当前账号是否已经登录过
//            if (EMClient.getInstance().isLoggedInBefore()){
//
//                //获取当前登录用户的信息
//
//                //跳转到主页面
//                startActivity(new Intent(SplashActivity.this,MainActivity.class));
//            }else {//没登录过
//                startActivity(new Intent(SplashActivity.this,Login.class));
//            }
//        }
//    }.start();

        Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                //判断当前账号是否已经登录过
                if (EMClient.getInstance().isLoggedInBefore()) { //登录过

                    //获取当前登录用户的信息
                    UserInfo account = Model.getInstance().getUserAccountDao().getAccountByHxId(EMClient.getInstance().getCurrentUser());

                    if (account == null) {
                        //没登录过
                        startActivity(new Intent(SplashActivity.this, Login.class));
                    }else {
                        //跳转到主页面
                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    }

                    //跳转到主页面
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                } else {//没登录过
                    startActivity(new Intent(SplashActivity.this, Login.class));
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);

        //发送2秒钟的延时消息
        handler.sendMessageDelayed(Message.obtain(), 2000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //销毁消息
        handler.removeCallbacksAndMessages(null);
    }
}
