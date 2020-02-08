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
    private void scanLink(int type, int count){
        FeLink<FeHeartUnit> tmp = link.next;
        while (tmp != null){
            FeHeartUnit u = tmp.data;
            tmp = tmp.next;
            //移除单元
            if(u.useless)
                removeUnit(u);
            //回调单元的方法
            else if(u.type == type)
                u.task.run(count);
        }
    }

    // ---------- 心跳散播 ----------

    //type定义
    public static final int TYPE_ANIM_STAY = 1;//人物原地待机或选中时动画,共3帧,钟摆式循环播放
    public static final int TYPE_ANIM_MOVE = 2;//人物移动时动画,共4帧,循环播放

    //type 1 TYPE_ANIM_MOVE
    private final int[] circleType1 = new int[]{7, 3, 7};//每帧延时
    private int circleType1_timerCount = 1, circleType1_count = 0;
    private boolean circleType1_dir = false;

    //type 2 TYPE_ANIM_MOVE
    private final int[] circleType2 = new int[]{3, 3, 3, 3};//每帧延时
    private int circleType2_timerCount = 1, circleType2_count = 0;

    //定时器
    private Timer timer1 = null, timer2 = null;
    private TimerTask timerTask1 = null, timerTask2 = null;

    public void start(){
        timer1 = new Timer();
        timerTask1 = new TimerTask() {
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
        timer2 = new Timer();
        timerTask2 = new TimerTask() {
            @Override
            public void run() {
                //type 2 TYPE_ANIM_MOVE
                if(++circleType2_timerCount > circleType2[circleType2_count]){
                    circleType2_timerCount = 1;
                    //
                    if(++circleType2_count >= circleType2.length)
                        circleType2_count = 0;
                    //
                    scanLink(TYPE_ANIM_MOVE, circleType2_count);
                }
            }
        };
        //
        timer1.schedule(timerTask1, 200, 100);
        timer2.schedule(timerTask2, 100, 100);
    }

    public void stop(){
        if(timerTask1 != null) {
            timerTask1.cancel();
            timerTask1 = null;
        }
        if(timer1 != null) {
            timer1.cancel();
            timer1 = null;
        }
        if(timerTask2 != null) {
            timerTask2.cancel();
            timerTask2 = null;
        }
        if(timer2 != null) {
            timer2.cancel();
            timer2 = null;
        }
    }

    // ---------- 其他内容 ----------

    public FeHeart(){
        start();
    }
}
