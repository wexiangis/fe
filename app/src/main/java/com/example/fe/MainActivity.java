package com.example.fe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

public class MainActivity extends Activity {

    private FeSave feSave = null;

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
        //初始化存档并开始
        if(feSave == null) {
            feSave = new FeSave();
            feSave.start(this);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        //关闭全局动画心跳定时器
        if(feSave != null && feSave.feHeart != null)
            feSave.feHeart.stop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //启动全局动画心跳定时器
        if(feSave != null && feSave.feHeart != null)
            feSave.feHeart.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
