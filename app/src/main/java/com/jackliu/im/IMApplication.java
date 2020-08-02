package com.jackliu.im;

import android.app.Application;

import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.EaseUI;
import com.jackliu.im.model.Model;

public class IMApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        //初始化EaseUI
        EMOptions options = new EMOptions();
        options.setAcceptInvitationAlways(false);//设置需要同意后才能接收邀请
        options.setAutoAcceptGroupInvitation(false);//设置需要同意后才能接收群邀请
        EaseUI.getInstance().init(this,options);

        //初始化数据模型层类
        Model.getInstance().init(this);

    }
}
