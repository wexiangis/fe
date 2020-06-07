package fans.develop.fe;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

/*
    主界面:背景动画+“按任意键提示”+超时播放职业动画
 */
public class FeLayoutMainTheme extends FeLayoutParent {

    private TextView textView;
    private RelativeLayout.LayoutParams layoutParams;

    //触屏事件回调函数
    private View.OnTouchListener onTouchListener  = new View.OnTouchListener (){
        public boolean onTouch(View v, MotionEvent event) {
            //触屏UP时
            if(event.getAction() == MotionEvent.ACTION_UP)
                FeData.flow.loadMainMenu();
            //不返回true的话ACTION_DOWN之后的事件都会被丢弃
            return true;
        }
    };

    public void reload(){
        this.removeAllViews();
        //添加到主界面
        this.addView(textView);
        //背景
        this.setBackgroundColor(0xFF404040);
        this.setOnTouchListener(onTouchListener);
    }

    public FeLayoutMainTheme(Context context)
    {
        super(context);

        // //获取屏幕参数
        // DisplayMetrics dm = new DisplayMetrics();
        // FeData.activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        // screenWidth = dm.widthPixels;
        // screenHeight = dm.heightPixels;

        //加载背景动画
        ;

        //按任意键继续
        textView = new TextView(context);
        textView.setText("按任意键继续");
        textView.setTextColor(0x80FFFFFF);
        textView.setTextSize(24);
        //相对主界面的位置
        layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        layoutParams.setMargins(0, 0, 0, 100);
        //添加布局参数
        textView.setLayoutParams(layoutParams);

        //加载结构
        reload();

        //实现父类接口
        callback = new FeLayoutParent.Callback() {
            @Override
            public boolean keyBack() {
                return false;
            }

            @Override
            public boolean destory() {
                return false;
            }

            @Override
            public void reload() {
                FeLayoutMainTheme.this.reload();
            }
        };
    }
}