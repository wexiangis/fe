package fans.develop.fe;

import android.view.MotionEvent;

/*
    事件操作大全
 */
public class FeSectionOperation {

    private FeLayoutSection.Callback callback;

    public FeSectionOperation(FeLayoutSection.Callback callback){
        this.callback = callback;
    }

    /*
        一切事件的源头皆来自触屏
     */
    public void onTouchEvent(MotionEvent event)
    {
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN: {
                //检查点击都命中了谁?
                FeFlagHit flag = callback.checkHit(event.getX(), event.getY());
                //正在对话
                if(flag.checkFlag(FeFlagHit.HIT_CHAT))
                    ;
            }
            break;
            case MotionEvent.ACTION_UP: {
                float tUpX = event.getX();
                float tUpY = event.getY();
            }
            break;
            case MotionEvent.ACTION_MOVE: {
                float tMoveX = event.getX();
                float tMoveY = event.getY();
            }
            break;
        }
    }
}
