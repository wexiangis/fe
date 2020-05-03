package com.example.fe;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;

import java.io.IOException;
import java.io.InputStream;

/*
    电影胶片式播放动画,针对地图人物动画管理而封装;
    统一管理人物的待机、选中、上下左右移动时的动画
 */
public class FeViewUnit extends View {

    private FeParamMap paramMap;

    //动画相对地图的偏移量
    private float leftMargin = 0, topMargin = 0;

    //传参备份
    private int frameHeight = 56;
    private int _id = 0, _animMode = 0, _colorMode = 0;
    public int _gridX = 0, _gridY = 0;

    //根据动画模式0~5,图像胶片上移帧数
    private final int[] frameSkipByAnimMode = new int[]{15, 12, 8, 4, 0, 0};

    //画图
    private Paint paint = new Paint();

    //原始图片
    private Bitmap bitmap = null;
    private Matrix matrix = new Matrix();

    //扣取图片位置
    private Rect bitmapBody = new Rect(0,0,0,0);

    //
    public Bitmap getAssetsUnitAnim(int id)
    {
        Bitmap ret = null;
        try {
            InputStream is = getClass().getResourceAsStream("/assets/unit/anim/"+String.format("%03d.png",id));
            if(is != null){
                ret = BitmapFactory.decodeStream(is);
                is.close();
            }
        } catch (java.io.FileNotFoundException e) {
            Log.d("getAssetsBitmap: ", "not found");
        } catch (IOException e) {
            Log.d("getAssetsBitmap: ", e.getMessage());
        }
        return ret;
    }

    //
    public FeViewUnit(Context context, 
        int id, int gridX, int gridY, 
        int animMode, int colorMode)
    {
        super(context);
        paramMap = FeData.feParamMap;
        //画笔初始化
        paint = new Paint();
        paint.setColor(Color.GREEN);
//        paint.setAntiAlias(true);
//        paint.setBitmapFilter(true);
        //图片加载和颜色变换
        bitmap = replaceBitmapColor(getAssetsUnitAnim(id), colorMode);
        matrix.postScale(-1, 1);
        //根据动画类型使用对应的心跳
        setAnimMode(animMode);
        //参数备份
        _colorMode = colorMode;
        _id = id;
        //
        frameHeight = bitmap.getWidth();
        //
        moveGridTo(gridX, gridY);
        //图片扣取位置计算
        bitmapBody.left = 0;
        bitmapBody.top = frameHeight*frameSkipByAnimMode[_animMode];
        bitmapBody.right = bitmap.getWidth();
        bitmapBody.bottom = bitmapBody.top + frameHeight;
        //引入心跳
        FeData.feHeart.addUnit(heartUnit);
        //类中类需有实例化的对象来new
        selectUnit = paramMap.new GridInMap();
    }

    //移动方格
    public void setGrid(int x, int y){
        _gridX += x;
        _gridY += y;
        leftMargin += x*paramMap.xGridPixel;
        topMargin += y*paramMap.yGridPixel;
    }

    //移动到方格
    public void moveGridTo(int x, int y){
        _gridX = x;
        _gridY = y;
        leftMargin = x*paramMap.xGridPixel;
        topMargin = y*paramMap.yGridPixel;
    }

    //颜色模式: 0/原色 1/绿色 2/红色
    public void setColorMode(int colorMode){
        if(_colorMode != colorMode) {
            synchronized (paint) {
                bitmap.recycle();
                bitmap = replaceBitmapColor(BitmapFactory.decodeResource(FeData.context.getResources(), _id), colorMode);
                _colorMode = colorMode;
            }
        }
    }
    
    //读颜色模式
    public int getColorMode(){
        return _colorMode;
    }

    //设置动画模式: 0/待机 1/选中 2/上 3/下 4/左 5/右
    public void setAnimMode(int animMode){
        if(_animMode != animMode){
            //镜像和恢复
            if(animMode == 5 || _animMode == 5) {
                bitmap = Bitmap.createBitmap(bitmap, 0, 0, (int) bitmap.getWidth(), (int) bitmap.getHeight(), matrix, true);
                matrix.postScale(1, 1);
            }
            //范围限制
            if(animMode < 0 || animMode > 5)
                animMode = 0;
            //
            _animMode = animMode;
            upgradeHeartType(_animMode);
            //马上重绘
            if(animMode == 1)
                ;//heartUnit.task.run(2);//选中动画比较特殊,需从第2帧开始
            else
                heartUnit.task.run(0);
        }
        else {
            _animMode = animMode;
            upgradeHeartType(_animMode);
        }
    }

    //读动画模式
    public int getAnimMode(){
        return _animMode;
    }

    //根据动画模式,切换心跳类型
    private void upgradeHeartType(int animMode){
        if(animMode == 0)
            heartUnit.type = FeHeart.TYPE_ANIM_STAY;
        else if(animMode == 1)
            heartUnit.type = FeHeart.TYPE_ANIM_SELECT;
        else
            heartUnit.type = FeHeart.TYPE_ANIM_MOVE;
    }

    //动画心跳回调
    private FeHeartUnit heartUnit = new FeHeartUnit(FeHeart.TYPE_ANIM_STAY, new FeHeartUnit.TimeOutTask(){
        public void run(int count){
            //移动框图
            bitmapBody.left = 0;
            bitmapBody.top = frameHeight*(frameSkipByAnimMode[_animMode] + count);
            bitmapBody.right = bitmap.getWidth();
            bitmapBody.bottom = bitmapBody.top + frameHeight;
            //调用一次onDrow
            FeViewUnit.this.invalidate();
        }
    });

    //临时参数
    public FeParamMap.GridInMap selectUnit;
    private Rect bitmapDist = new Rect(0,0,0,0);

    //绘图回调
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        //跟地图要位置
        paramMap.getRectByGrid(_gridX, _gridY, selectUnit);
        bitmapDist.left = selectUnit.selectRect.left - selectUnit.selectRect.width()/2;
        bitmapDist.right = selectUnit.selectRect.right + selectUnit.selectRect.width()/2;
        bitmapDist.top = selectUnit.selectRect.bottom - selectUnit.selectRect.width()*2;
        bitmapDist.bottom = selectUnit.selectRect.bottom;
        //绘图
        canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG|Paint.FILTER_BITMAP_FLAG));//抗锯齿
        canvas.drawBitmap(bitmap, bitmapBody, bitmapDist, paint);
    }

    //检查坐标是否在当前人物上
    public boolean checkHit(float x, float y){
        if(selectUnit.selectRect.contains((int)x, (int)y))
            return true;
        return false;
    }

    //颜色替换工具
    //oldBitmap: 原图
    //type: 0/原色 1/绿色 2/红色
    public Bitmap replaceBitmapColor(Bitmap oldBitmap, int type){
        if(type == 0)   //使用原色
            return oldBitmap;
        //相关说明可参考 http://xys289187120.blog.51cto.com/3361352/657590/
        Bitmap mBitmap = oldBitmap.copy(Bitmap.Config.ARGB_8888, true);
        //循环获得bitmap所有像素点
        int mBitmapWidth = mBitmap.getWidth();
        int mBitmapHeight = mBitmap.getHeight();
        int r, g, b, color, tcolor, tmp;

        for (int i = 0; i < mBitmapHeight; i++) {
            for (int j = 0; j < mBitmapWidth; j++) {
                color = mBitmap.getPixel(j, i);
                tcolor = color&0xff000000;
                r = (color&0x00ff0000)>>16;
                g = (color&0x0000ff00)>>8;
                b = color&0x000000ff;
                if(b > g && b > r) {
                    if (type == 1) {         //蓝色 换 绿色
                        tcolor |= (int) (r * 0.5) << 16;
                        tcolor |= (int) (b * 0.9) << 8;
                        tcolor |= (int) (g * 0.5) << 0;
                        mBitmap.setPixel(j, i, tcolor);
                    }else if (type == 2) {    //蓝色 换 红色
                        tcolor |= (int) (b * 1.0) << 16;
                        tcolor |= (int) (g * 0.5) << 8;
                        tcolor |= (int) (r * 0.5) << 0;
                        mBitmap.setPixel(j, i, tcolor);
                    }else if (type == 3) {    //蓝色 换 黑色
                        tmp = r + g + b;
                        r = g = b = tmp/4;
                        tcolor |= (int) (r * 1.0) << 16;
                        tcolor |= (int) (g * 1.0) << 8;
                        tcolor |= (int) (b * 1.0) << 0;
                        mBitmap.setPixel(j, i, tcolor);
                    }else if (type == 4) {    //蓝色 换 橙色
                        tcolor |= (int) (b * 1.0) << 16;
                        tcolor |= (int) (g * 1.0) << 8;
                        tcolor |= (int) (r * 0.5) << 0;
                        mBitmap.setPixel(j, i, tcolor);
                    }else if (type == 5) {    //蓝色 换 红紫色
                        tcolor |= (int) (b * 0.8) << 16;
                        tcolor |= (int) (r * 0.5) << 8;
                        tcolor |= (int) (g * 1.0) << 0;
                        mBitmap.setPixel(j, i, tcolor);
                    }else if (type == 6) {    //蓝色 换 红紫色
                        tcolor |= (int) (r * 0.8) << 16;
                        tcolor |= (int) (b * 0.8) << 8;
                        tcolor |= (int) (b * 0.8) << 0;
                        mBitmap.setPixel(j, i, tcolor);
                    }
                }
            }
        }
        return mBitmap;
    }

}

