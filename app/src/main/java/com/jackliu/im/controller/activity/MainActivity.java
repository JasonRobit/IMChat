package com.jackliu.im.controller.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.Activity;
import android.os.Bundle;
import android.widget.RadioGroup;

import com.jackliu.im.R;
import com.jackliu.im.controller.fragment.ChatFragment;
import com.jackliu.im.controller.fragment.ContactListFragment;
import com.jackliu.im.controller.fragment.SettingFragment;

import static com.superrtc.mediamanager.EMediaManager.initData;

public class MainActivity extends Activity {
    private RadioGroup rg_main;
    private Fragment chatFragment;
    private Fragment contactListFragment;
    private Fragment settingFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        initData();

        initListener();
    }

    private void initListener() {
        // RadioGroup的选择事件
        rg_main.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {

                Fragment fragment = null;

                switch (checkedId) {
                    // 会话列表
                    case R.id.rb_main_chat:
                        break;
                        //　联系人列表
                    case R.id.rb_main_contact:
                        break;
                        // 设置页面
                    case R.id.rb_main_setting:
                        break;

                }

                //　实现fragment切换的方法
    //            switchFragment(fragment);
            }
        });
    }


    private void initData() {
        //　创建三个fragment对象
        chatFragment = new ChatFragment();
        contactListFragment = new ContactListFragment();
        settingFragment = new SettingFragment();

    }

    private void initView() {
    rg_main = findViewById(R.id.rg_main);

    }
}
