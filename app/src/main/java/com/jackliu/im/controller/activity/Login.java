package com.jackliu.im.controller.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.jackliu.im.R;
import com.jackliu.im.model.Model;
import com.jackliu.im.model.bean.UserInfo;

public class Login extends AppCompatActivity {
    private EditText et_login_name, et_login_pwd;
    private Button btn_login, btn_regist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        //初始化控件
        initView();

        //初始化监听
        initListener();
    }

    private void initListener() {
        //注册按钮的点击事件
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });


        //登录按钮的点击事件
        btn_regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registe();
            }
        });

    }

    //注册按钮的业务逻辑处理
    private void registe() {
        // 1.获取输入的用户名和密码
       final String registName = et_login_name.getText().toString();
       final String registPwd = et_login_pwd.getText().toString();

        // 2.校验输入的用户名和密码
        if (TextUtils.isEmpty(registName)||TextUtils.isEmpty(registPwd)){
            Toast.makeText(this,"输入的用户名或密码不能为空",Toast.LENGTH_SHORT).show();
            return;
        }

        //　3.去服务器注册账号
        Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
            @Override
            public void run() {

                try {
                    //　去环信服务器注册账号
                    EMClient.getInstance().createAccount(registName,registPwd);

                    //　更新页面显示
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(Login.this,"注册成功",Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (HyphenateException e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(Login.this,"注册失败",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

    }

    //登录按钮的业务逻辑处理
    private void login() {
        // 1.获取输入的用户名和密码
        final String loginName = et_login_name.getText().toString();
        final String loginPwd = et_login_pwd.getText().toString();

        // 2.校验输入的用户名和密码
        if (TextUtils.isEmpty(loginName)||TextUtils.isEmpty(loginPwd)){
            Toast.makeText(this,"输入的用户名或密码不能为空",Toast.LENGTH_SHORT).show();
            return;
        }

        //　3. 登录逻辑处理
        Model.getInstance().getGlobalThreadPool().execute(new Runnable() {


            @Override
            public void run() {
                //去环信服务器登录
                EMClient.getInstance().login(loginName, loginPwd, new EMCallBack() {
                    //　登录成功后的处理
                    @Override
                    public void onSuccess() {
                        //　对模型层数据的处理
                        Model.getInstance().loginSuccess();

                        //  保存用户账号信息到本地数据库
                        Model.getInstance().getUserAccountDao().addAccount(new UserInfo(loginName));

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //　提示登录成功
                                Toast.makeText(Login.this,"登录成功",Toast.LENGTH_SHORT).show();

                                //  跳转到主页面
                                startActivity(new Intent(Login.this,MainActivity.class));

                                finish();
                            }
                        });

                    }

                    //　登录失败后的处理
                    @Override
                    public void onError(int i, String s) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(Login.this,"登录失败"+s,Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    // 登录过程中的处理
                    @Override
                    public void onProgress(int i, String s) {

                    }
                });
            }
        });
    }
    private void initView() {

        et_login_name = findViewById(R.id.login_username_edit);
        et_login_pwd = findViewById(R.id.login_pwd_edit);
        btn_login = findViewById(R.id.btn_login);
        btn_regist = findViewById(R.id.btn_registe);
    }
}
