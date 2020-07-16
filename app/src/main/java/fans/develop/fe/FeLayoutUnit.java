package fans.develop.fe;

import android.content.Context;
import android.view.View;
import android.graphics.Path;

/*
    地图中的人物动画管理
 */
public class FeLayoutUnit extends FeLayout {

    private Context context;
    private FeLayoutSection.Callback callback;
    private int hitAnimOrder = -1;

    public FeLayoutUnit(Context context, FeLayoutSection.Callback callback) {
        super(context);
        this.context = context;
        this.callback = callback;
    }

    /* ---------- function ---------- */

    /*
        who_refresh:
            0/map refresh
            1/select unit refresh
     */
    public void refresh(int who_refresh){
        FeViewUnit tmp;
        //遍历所有子view
        for (int i = 0; i < getChildCount(); i++) {
            tmp = (FeViewUnit)getChildAt(i);
            if(hitAnimOrder == i &&
                callback.checkClickState(FeLayoutSection.ON_HIT_UNIT)) {
                if(who_refresh == 1)
                    tmp.setAnimMode(tmp.getAnimMode()+1);
                callback.getSectionMap().getRectByGrid(tmp.gridX, tmp.gridY, callback.getSectionUnit().selectSite);
            }
            else
                tmp.setAnimMode(0);
            tmp.invalidate();
        }
    }

    public boolean checkHit(float x, float y){
        FeViewUnit fvu;
        //遍历所有子view
        for (int i = 0; i < getChildCount(); i++) {
            fvu = (FeViewUnit)getChildAt(i);
            if (fvu.checkHit(x, y)) {
                hitAnimOrder = i;
                callback.setClickState(FeLayoutSection.ON_HIT_UNIT);
                callback.getSectionUnit().selectView = fvu;
                refresh(1);
                return true;
            }
        }
        //
        hitAnimOrder = -1;
        callback.cleanClickState(FeLayoutSection.ON_HIT_UNIT);
        refresh(0);
        return false;
    }

    /*
        人员增删
     */
    public void addUnit(int id, int y, int x, int camp){
        addView(new FeViewUnit(context, id, x, y, 0, camp, callback));
    }
    public void removeUnit(int id){
        ;
    }

    /*
        选中人物
     */
    public void unitSelect(int id){
        ;
    }

    /*
        人物按轨迹行走
        path: 格子位置(x，y),组成的路径,注意两个节点之间只能x或y一个值变动,
            例如: [0,0] -> [1,1] 是错误的, 要改为 [0,0] -> [0,1] -> [1,1]
        callback: 移动结束后要做什么(一般把人物 setStay())
     */
    public void unitWalk(int id, Path path, Runnable callback){
        ;
    }

    /*
        释放选中
     */
    public void unitRelease(int id){
        ;
    }

    /*
        人员阵营
     */
    public void setCamp(int id, int camp){
        ;
    }

    /*
        人员可行动
     */
    public void setActivity(int id){
        ;
    }

    /*
        人员待机
     */
    public void setStay(int id){
        ;
    }

    /*
        人员异常状态
     */
    public void setError(int id, int errorType){
        ;
    }

    /*
        人员动画
        animMode: 0/待机 1/选中 2,3,4,5/上,下,左,右
     */
    public void setAnim(int id, int animMode){
        ;
    }

    /* ---------- abstract interface ---------- */

    public boolean onKeyBack(){
        return false;
    }
    public boolean onDestory(){
        //释放子view
        for (int i = 0; i < getChildCount(); i++) {
            View v = getChildAt(i);
            if (v instanceof FeView)
                ((FeView)v).onDestory();
        }
        return true;
    }
    public void onReload(){
        ;
    }
}
