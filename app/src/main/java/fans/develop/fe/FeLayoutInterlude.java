package fans.develop.fe;

import android.content.*;
import android.view.View;

/*
	过场动画图层(例如轮到一方阵营回合过场动画)
	动画进行中禁止触屏操作
 */
public class FeLayoutInterlude extends FeLayoutParent
{
	private FeLayoutSection.Callback callback;
	
	public FeLayoutInterlude(Context context, FeLayoutSection.Callback callback){
		super(context);
		this.callback = callback;
	}
	
	/*
		阵营切换动画
	 */
	public void campSwitch(int camp){
		callback.setClickState(FeLayoutSection.ON_TOUCH_FORBID);
		;
		callback.cleanClickState(FeLayoutSection.ON_TOUCH_FORBID);
	}
	
	/*
		获得物品、物品损坏等自动结束弹窗
	 */
	public void tips(String tips){
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
			if (v instanceof FeViewParent)
				((FeViewParent)v).onDestory();
		}
		return true;
	}
	public void onReload(){
		;
	}
}
