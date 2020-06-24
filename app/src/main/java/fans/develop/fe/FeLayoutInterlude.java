package fans.develop.fe;

import android.content.*;

/*
	过场动画图层(例如轮到一方阵营回合过场动画)
 */
public class FeLayoutInterlude extends FeLayoutParent
{
	private FeLayoutSection.Callback callback;
	
	public FeLayoutInterlude(Context context, FeLayoutSection.Callback callback){
		super(context);
		this.callback = callback;
	}
}
