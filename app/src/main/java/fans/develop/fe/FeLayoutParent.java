package fans.develop.fe;

import android.content.Context;
import android.widget.RelativeLayout;

/*
    所有layout的父类,用于提供一些统一的接口或方法,比如返回键事件处理接口
 */
public class FeLayoutParent extends RelativeLayout{

    //接口(回掉函数)定义
    //当前界面想要在按返回键时不被销毁,要么keyBack()返回true，要么destory()返回false
    public interface Callback{

        //按键返回
        //返回: true表示有使用到, false时将由系统决定退回界面或退出
        boolean keyBack();

        //即将注销该layout,请在这里做收尾操作
        //返回: false表示未准备好, 将不注销
        boolean destory();

        //重新加载界面及其数据
        //子类可以在这里添加自己的loading界面
        void reload();
    }
    
    public FeLayoutParent.Callback callback = null;

    public FeLayoutParent(Context context){
        super(context);
    }
}
