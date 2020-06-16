package fans.develop.fe;

import android.content.Context;

/*
    放置于界面最上层,用于打印调试信息
 */
public class FeLayoutDebug extends FeLayoutParent {

    private FeLayoutSection.Callback callback;

    public boolean checkHit(float x, float y){
        return false;
    }

    public void refresh(){
        //遍历所有子view
        for (int i = 0; i < getChildCount(); i++)
            getChildAt(i).invalidate();
    }

    public FeLayoutDebug(Context context, FeLayoutSection.Callback callback) {
        super(context);
        this.callback = callback;
    }

    /* ----- debug 条目的增、删、写 ----- */

    //返回一个ID值
    public int addInfo(String name, int color){
        TextView textView = new TextView(callback.getContext());
        return 0;
    }

    public void delInfo(int id){
    }
    
    public void setInfo(int id, String ... values){
        ;
    }
    
    public void setInfo(int id, int ... values){
        ;
    }
}
