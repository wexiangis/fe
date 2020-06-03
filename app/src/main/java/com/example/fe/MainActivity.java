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
            boolean ret = false;
            //控件食用事件
            if(FeData.layoutCurrent != null && FeData.layoutCurrent instanceof FeLayoutParent.Callback)
                ret = FeData.layoutCurrent.callback.keyBack();
            //控件不食用,则由系统食用
            if(ret == false){
                //界面返回
                ;
                //退出app
                ;
            }
            return ret;
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
