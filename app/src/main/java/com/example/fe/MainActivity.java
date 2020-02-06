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
        //按应用图标回到应用时的特殊处理
        if((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            finish();
            return;
        }
        //半透明系统虚拟按键
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        //
        if(feSave == null) {
            feSave = new FeSave();
            feSave.start(this);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        //
        if(feSave != null)
            feSave.feHeart.stop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //
        if(feSave != null)
            feSave.feHeart.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
