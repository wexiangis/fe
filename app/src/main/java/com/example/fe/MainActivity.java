package com.example.fe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //点击应用图标,回到应用时的特殊处理
        if((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            finish();
            return;
        }
        //半透明系统虚拟按键
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //关闭全局动画心跳定时器
        FeData.start(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //启动全局动画心跳定时器
        FeData.stop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
