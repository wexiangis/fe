package com.example.fe;

import java.util.Timer;
import java.util.TimerTask;

/*
    全局动画统一心跳管理,在FeSave中实例化1个;
    通过注册接口(回掉函数)的方式实现切帧;
    使用addUnit()和removeUnit()来注册和移除动画心跳
 */
public class FeHeart {

    // ---------- 链表部分 ----------

    //本地定义一个链表头
    private FeLink<FeHeartUnit> link = new FeLink<FeHeartUnit>(new FeHeartUnit(0, new FeHeartUnit.TimeOutTask(){
        public void run(int c){}
    }));

    //添加链表单元的方式申请心跳
    public void addUnit(FeHeartUnit u){
        FeLink<FeHeartUnit> tmp = link;
        while (tmp.next != null)
            tmp = tmp.next;
        FeLink<FeHeartUnit> tmp2 = new FeLink<FeHeartUnit>(u);
        tmp2.previous = tmp;
        tmp.next = tmp2;
    }

    //移除单元
    public void removeUnit(FeHeartUnit u){
        FeLink<FeHeartUnit> tmp = link.next;
        while (tmp != null){
            if(tmp.data == u){
                FeLink<FeHeartUnit> tmp2 = tmp.next;
                tmp.previous.next = tmp2;
                if(tmp2 != null)
                    tmp2.previous = tmp.previous;
                break;
            }
            tmp = tmp.next;
        }
    }

    //根据type遍历链表,然后回掉接口函数,传参count为当前要播放第几帧
    private boolean scanLink(int type, int count){
        FeLink<FeHeartUnit> tmp = link.next;
        boolean hit = false;
        while (tmp != null){
            FeHeartUnit u = tmp.data;
            tmp = tmp.next;
            //移除单元
            if(u.useless)
                removeUnit(u);
            //回调单元的方法
            else if(u.type == type) {
                u.task.run(count);
                hit = true;
            }
        }
        return hit;
    }

    // ---------- 心跳散播 ----------

    //type定义
    public static final int TYPE_ANIM_STAY = 1;//人物原地待机或选中时动画,共3帧,钟摆式循环播放
    public static final int TYPE_ANIM_SELECT = 2;//人物原地待机或选中时动画,共3帧,钟摆式循环播放
    public static final int TYPE_ANIM_MOVE = 3;//人物移动时动画,共4帧,循环播放

    //type 1 TYPE_ANIM_STAY
    private final int[] circleType1 = new int[]{7, 3, 7};//每帧延时
    private int circleType1_timerCount = 1, circleType1_count = 0;
    private boolean circleType1_dir = false;

    //type 2 TYPE_ANIM_SELECT
    private final int[] circleType2 = new int[]{7, 3, 7};//每帧延时
    private int circleType2_timerCount = 1, circleType2_count = 0;
    private boolean circleType2_dir = false;

    //type 3 TYPE_ANIM_MOVE
    private final int[] circleType3 = new int[]{3, 3, 3, 3};//每帧延时
    private int circleType3_timerCount = 1, circleType3_count = 0;

    //定时器
    private Timer[] timer = new Timer[TYPE_ANIM_MOVE];
    private TimerTask[] timerTask = new TimerTask[TYPE_ANIM_MOVE];

    public void start(){
        //
        timer[0] = new Timer();
        timerTask[0] = new TimerTask() {
            @Override
            public void run() {
                //type 1 TYPE_ANIM_STAY
                if(++circleType1_timerCount > circleType1[circleType1_count]){
                    circleType1_timerCount = 1;
                    //
                    if(circleType1_dir) {
                        if (--circleType1_count <= 0) {
                            circleType1_count = 0;
                            circleType1_dir = !circleType1_dir;
                        }
                    }else{
                        if (++circleType1_count >= circleType1.length - 1) {
                            circleType1_count = circleType1.length - 1;
                            circleType1_dir = !circleType1_dir;
                        }
                    }
                    //
                    scanLink(TYPE_ANIM_STAY, circleType1_count);
                }
            }
        };
        //
        timer[1] = new Timer();
        timerTask[1] = new TimerTask() {
            @Override
            public void run() {
                //type 2 TYPE_ANIM_SELECT
                if(++circleType2_timerCount > circleType2[circleType2_count]){
                    circleType2_timerCount = 1;
                    //
                    if(circleType2_dir) {
                        if (--circleType2_count <= 0) {
                            circleType2_count = 0;
                            circleType2_dir = !circleType2_dir;
                        }
                    }else{
                        if (++circleType2_count >= circleType2.length - 1) {
                            circleType2_count = circleType2.length - 1;
                            circleType2_dir = !circleType2_dir;
                        }
                    }
                    //
                    if(!scanLink(TYPE_ANIM_SELECT, circleType2_count)) {
                        //随时就绪
                        circleType2_dir = false;
                        circleType2_count = 1;
                    }
                }
            }
        };
        //
        timer[2] = new Timer();
        timerTask[2] = new TimerTask() {
            @Override
            public void run() {
                //type 3 TYPE_ANIM_MOVE
                if(++circleType3_timerCount > circleType3[circleType3_count]){
                    circleType3_timerCount = 1;
                    //
                    if(++circleType3_count >= circleType3.length)
                        circleType3_count = 0;
                    //
                    scanLink(TYPE_ANIM_MOVE, circleType3_count);
                }
            }
        };
        //
        for (int i = 0; i < timer.length; i++)
            timer[i].schedule(timerTask[i], 200, 100);
    }

    public void stop(){
        if(timer != null){
            for (int i = 0; i < timer.length; i++){
                timerTask[i].cancel();
                timerTask[i] = null;
                timer[i].cancel();
                timer[i] = null;
            }
        }
    }

    // ---------- 其他内容 ----------

    public FeHeart(){
        start();
    }
}
