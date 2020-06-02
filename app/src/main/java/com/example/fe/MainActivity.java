package com.example.fe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
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
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK )
        {
            if(FeData.layoutCurrent != null && FeData.layoutCurrent instanceof FeLayoutParent.Callback)
                return FeData.layoutCurrent.callback.keyBack();
            return false;
        }
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        FeData.start(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        FeData.stop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
