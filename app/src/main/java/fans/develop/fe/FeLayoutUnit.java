package fans.develop.fe;

import android.content.Context;

/*
    地图中的人物动画管理
 */
public class FeLayoutUnit extends FeLayoutParent {

    private FeSection.Callback callback;
    private int hitAnimOrder = -1;

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
                callback.checkClickState(FeSection.ON_HIT_UNIT)) {
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
                callback.setClickState(FeSection.ON_HIT_UNIT);
                callback.getSectionUnit().selectView = fvu;
                refresh(1);
                return true;
            }
        }
        //
        hitAnimOrder = -1;
        callback.cleanClickState(FeSection.ON_HIT_UNIT);
        refresh(0);
        return false;
    }

    public FeLayoutUnit(Context context, FeSection.Callback callback) {
        super(context);
        this.callback = callback;

//        loadView(context, 0, 1, 0, callback);
//        loadView(context, 1, 2, 1, callback);
//        loadView(context, 2, 3, 0, callback);
//        loadView(context, 3, 4, 1, callback);
//        loadView(context, 4, 5, 0, callback);
//        loadView(context, 5, 6, 1, callback);
        loadView(context, 6, 7, 0, callback);
        loadView(context, 7, 8, 1, callback);
//        loadView(context, 8, 9, 0, callback);
//        loadView(context, 9, 10, 1, callback);
//        loadView(context, 10, 11, 0, callback);
//        loadView(context, 11, 12, 1, callback);
//        loadView(context, 12, 13, 0, callback);
//        loadView(context, 13, 14, 1, callback);

//        loadView(context, 14, 1, 12, callback);
//        loadView(context, 15, 2, 13, callback);
//        loadView(context, 16, 3, 12, callback);
//        loadView(context, 17, 4, 13, callback);
        loadView(context, 18, 5, 12, callback);
        loadView(context, 19, 6, 13, callback);
//        loadView(context, 20, 7, 12, callback);
//        loadView(context, 21, 8, 13, callback);
//        loadView(context, 22, 9, 12, callback);
//        loadView(context, 23, 10, 13, callback);
//        loadView(context, 24, 11, 12, callback);
//        loadView(context, 25, 12, 13, callback);
//        loadView(context, 26, 13, 12, callback);
//        loadView(context, 27, 14, 13, callback);
    }

    private void loadView(Context context, int id, int y, int x, FeSection.Callback callback){
        addView(new FeViewUnit(context, id, x, y, 0, 0, callback));
        addView(new FeViewUnit(context, id, x+2, y, 1, 1, callback));
        addView(new FeViewUnit(context, id, x+4, y, 2, 2, callback));
        addView(new FeViewUnit(context, id, x+6, y, 3, 3, callback));
        addView(new FeViewUnit(context, id, x+8, y, 4, 4, callback));
        addView(new FeViewUnit(context, id, x+10, y, 5, 5, callback));
        addView(new FeViewUnit(context, id, x+12, y, 6, 6, callback));
    }
}
