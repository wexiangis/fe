package fans.develop.fe;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

/*
    贯穿全局的参数管理
 */
public class FeData extends Application {

    //核心部件
    public static Activity activity = null;
    public static Context context = null;

    //全局动画心跳(注意: addHeartUnit 过的控件销毁时要先 removeHeartUnit )
    private static FeHeart heart = null;
    public static void addHeartUnit(FeHeartUnit heartUnit){
        if(heart != null)
            heart.addUnit(heartUnit);
    }
    public static void removeHeartUnit(FeHeartUnit heartUnit){
        if(heart != null)
            heart.removeUnit(heartUnit);
    }
    
    //结构层
    public static FeAssets assets = null;//assets文件资源管理
    public static FeFlow flow = null;//界面流程管理

    //章节运转的关键参数,数据+界面
    public static FeSection section = null;
    public static FeLayoutSection sectionLayout = null;

    //用于系统的界面的定位和跳转
    public static FeLayoutParent layoutCurrent = null;//当前界面
    public static FeChain<FeLayoutParent> layoutChain = null;//历史界面链表(该变量指向最后一个)

    //存档数量
    public static final int saveNum = 3;
    //存档状态
    //[x][0]:章节
    //[x][1]:是否中断
    //[x][2]:时长(秒)
    public static int[][] save;
    //更新存档状态
    public static void saveReload(){
        save = assets.save.getSx(saveNum);
    }

    public static void start(Activity act){
        //保留activity
        activity = act;
        //获取application的context
        context = activity.getApplicationContext();
        //全局动画心跳
        heart.start();
        //开始事件
        flow.start();
    }

    public static void stop(){
        //关闭timer定时器
        heart.stop();
        //从当前activity中移除界面控件
        flow.stop();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化
        heart = new FeHeart();
        flow = new FeFlow();
        assets = new FeAssets();
    }
}