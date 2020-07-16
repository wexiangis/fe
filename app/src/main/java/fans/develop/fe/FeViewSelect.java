package fans.develop.fe;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Rect;

/*
    光标动画
 */
public class FeViewSelect extends FeView {

    private FeLayoutSection.Callback callback;

    //选中框图片
    private Bitmap bitmapSelect;
    //
    private Rect rectSrcSelect, rectDistSelect;
    //
    private int bitmapSelectFrameHeight;
    //
    private Paint paintSelct;

    public void onDestory(){
        //解除心跳注册
        callback.removeHeartUnit(heartUnit);
    }

    public FeViewSelect(Context context, FeLayoutSection.Callback callback){
        super(context);
        this.callback = callback;
        //
        bitmapSelect = callback.getAssets().menu.getMapSelect();
        bitmapSelectFrameHeight = bitmapSelect.getWidth();
        rectDistSelect = new Rect(0, 0, 0, 0);
        rectSrcSelect = new Rect(0, 0, bitmapSelect.getWidth(), bitmapSelect.getHeight());
        //
        paintSelct = new Paint();
        paintSelct.setColor(Color.BLUE);
        //引入心跳
        callback.addHeartUnit(heartUnit);
    }

    private boolean needRefresh;
    private int heartCount = 0;
    //动画心跳回调
    private FeHeartUnit heartUnit = new FeHeartUnit(FeHeart.TYPE_FRAME_HEART, new FeHeartUnit.TimeOutTask(){
        public void run(int count){
            //移动框图
            rectSrcSelect.left = 0;
            rectSrcSelect.right = bitmapSelect.getWidth();
            //
            needRefresh = true;
            //记数0,1,2,3,
            if(heartCount == 1)
                rectSrcSelect.top = bitmapSelectFrameHeight * 3;
            else if(heartCount == 0 || heartCount == 2)
                rectSrcSelect.top = bitmapSelectFrameHeight * 2;
            else {
                if(rectSrcSelect.top == bitmapSelectFrameHeight)
                    needRefresh = false;
                else
                    rectSrcSelect.top = bitmapSelectFrameHeight;
            }
            if(++heartCount > 6)
                heartCount = 0;
            //
            rectSrcSelect.bottom = rectSrcSelect.top + bitmapSelectFrameHeight;
            //调用一次onDrow
            if(needRefresh)
                FeViewSelect.this.invalidate();
        }
    });

    //绘图回调
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);

        //移动中不绘制
        if(callback.checkClickState(FeLayoutSection.ON_MOVE))
            return;

        //画选中框
        if(callback.checkClickState(FeLayoutSection.ON_HIT_MAP) &&
                !callback.checkClickState(FeLayoutSection.ON_HIT_UNIT)) {
            //计算输出位置
            rectDistSelect.left = callback.getSectionMap().selectSite.rect.left - callback.getSectionMap().selectSite.rect.width()/4;
            rectDistSelect.right = callback.getSectionMap().selectSite.rect.right + callback.getSectionMap().selectSite.rect.width()/4;
            rectDistSelect.top = callback.getSectionMap().selectSite.rect.top - callback.getSectionMap().selectSite.rect.height()/4;
            rectDistSelect.bottom = callback.getSectionMap().selectSite.rect.bottom + callback.getSectionMap().selectSite.rect.height()/4;
            //抗锯齿
            canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG|Paint.FILTER_BITMAP_FLAG));
            //画图
//            canvas.drawPath(callback.getSectionMap().selectSite.path, paintSelct);
            canvas.drawBitmap(bitmapSelect, rectSrcSelect, rectDistSelect, paintSelct);
        }
        else if(callback.checkClickState(FeLayoutSection.ON_HIT_UNIT)){
            //计算输出位置
            rectDistSelect.left = callback.getSectionUnit().selectSite.rect.left - callback.getSectionUnit().selectSite.rect.width()/4;
            rectDistSelect.right = callback.getSectionUnit().selectSite.rect.right + callback.getSectionUnit().selectSite.rect.width()/4;
            rectDistSelect.top = callback.getSectionUnit().selectSite.rect.top - callback.getSectionUnit().selectSite.rect.height()/2;
            rectDistSelect.bottom = callback.getSectionUnit().selectSite.rect.bottom;
            //使用大框
            rectSrcSelect.top = 0;
            rectSrcSelect.bottom = bitmapSelectFrameHeight;
            //抗锯齿
            canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG|Paint.FILTER_BITMAP_FLAG));
            //画图
//            canvas.drawPath(callback.getSectionMap().selectSite.path, paintSelct);
            canvas.drawBitmap(bitmapSelect, rectSrcSelect, rectDistSelect, paintSelct);
        }
    }
}
