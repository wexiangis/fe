package fans.develop.fe;

import android.content.Context;
import android.view.View;

/*
    封装的公用View父类，添加回收方法
 */
public abstract class FeViewParent extends View {

    public abstract void onDestory();

    public FeViewParent(Context context){
        super(context);
    }
}
