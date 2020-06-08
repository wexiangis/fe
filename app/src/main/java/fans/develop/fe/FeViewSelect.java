package fans.develop.fe;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Rect;
import android.view.View;

/*
    光标动画
 */
public class FeViewSelect extends View {

    private FeSectionMap sectionMap;
    private FeSectionUnit sectionUnit;

    //选中框图片
    private Bitmap bitmapSelect;
    //
    private Rect rectSrcSelect, rectDistSelect;
    //
    private int bitmapSelectFrameHeight;
    //
    private Paint paintSelct;

    public FeViewSelect(Context context){
        super(context);
        sectionMap = FeData.section.sectionMap;
        sectionUnit = FeData.section.sectionUnit;
        //
        bitmapSelect = FeData.assets.menu.getMapSelect();
        bitmapSelectFrameHeight = bitmapSelect.getWidth();
        rectDistSelect = new Rect(0, 0, 0, 0);
        rectSrcSelect = new Rect(0, 0, bitmapSelect.getWidth(), bitmapSelect.getHeight());
        //
        paintSelct = new Paint();
        paintSelct.setColor(Color.BLUE);
        //引入心跳
        FeData.addHeartUnit(heartUnit);
    }

    //删除人物,之后需自行 removeView()
    public void delete(){
        //解除心跳注册
        FeData.removeHeartUnit(heartUnit);
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
        if(FeData.section.checkClickState(FeSection.ON_MOVE))
            return;

        //画选中框
        if(FeData.section.checkClickState(FeSection.ON_HIT_MAP) &&
                !FeData.section.checkClickState(FeSection.ON_HIT_UNIT)) {
            //计算输出位置
            rectDistSelect.left = sectionMap.selectSite.rect.left - sectionMap.selectSite.rect.width()/4;
            rectDistSelect.right = sectionMap.selectSite.rect.right + sectionMap.selectSite.rect.width()/4;
            rectDistSelect.top = sectionMap.selectSite.rect.top - sectionMap.selectSite.rect.height()/4;
            rectDistSelect.bottom = sectionMap.selectSite.rect.bottom + sectionMap.selectSite.rect.height()/4;
            //抗锯齿
            canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG|Paint.FILTER_BITMAP_FLAG));
            //画图
//            canvas.drawPath(sectionMap.selectSite.path, paintSelct);
            canvas.drawBitmap(bitmapSelect, rectSrcSelect, rectDistSelect, paintSelct);
        }
        else if(FeData.section.checkClickState(FeSection.ON_HIT_UNIT)){
            //计算输出位置
            rectDistSelect.left = sectionUnit.selectSite.rect.left - sectionUnit.selectSite.rect.width()/4;
            rectDistSelect.right = sectionUnit.selectSite.rect.right + sectionUnit.selectSite.rect.width()/4;
            rectDistSelect.top = sectionUnit.selectSite.rect.top - sectionUnit.selectSite.rect.height()/2;
            rectDistSelect.bottom = sectionUnit.selectSite.rect.bottom;
            //使用大框
            rectSrcSelect.top = 0;
            rectSrcSelect.bottom = bitmapSelectFrameHeight;
            //抗锯齿
            canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG|Paint.FILTER_BITMAP_FLAG));
            //画图
//            canvas.drawPath(sectionMap.selectSite.path, paintSelct);
            canvas.drawBitmap(bitmapSelect, rectSrcSelect, rectDistSelect, paintSelct);
        }
    }
}
