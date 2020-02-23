package com.example.fe;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

/*
    电影胶片式播放动画,针对地图人物动画管理而封装;
    统一管理人物的待机、选中、上下左右移动时的动画
 */
public class FeAnimFilm extends View {

    private Context _context;
    private FeMapParam _mapParam;
    private FeHeart _animHeart;

    //动画相对地图的偏移量
    private float leftMargin = 0, topMargin = 0;

    //传参备份
    private int frameHeight = 56;
    private int _id = 0, _animMode = 0, _colorMode = 0;
    private int _gridX = 0, _gridY = 0;

    //
    private final int[] frameSkipByAnimMode = new int[]{15, 12, 8, 4, 0, 0};

    //画图
    private Paint paint = new Paint();

    //原始图片
    private Bitmap bitmap = null;
    private Matrix matrix = new Matrix();

    //扣取图片位置
    private Rect bitmapBody = new Rect(0,0,0,0);

    /*
    id: drawable图片,如: R.drawable.xxx
    */
    public FeAnimFilm(Context context, FeHeart animHeart, FeMapParam mapParam, int id, int gridX, int gridY, int animMode, int colorMode)
    {
        super(context);
        _context = context;
        _animHeart = animHeart;
        _mapParam = mapParam;
        //画笔初始化
        paint = new Paint();
        paint.setColor(Color.GREEN);
//        paint.setAntiAlias(true);
        //图片加载和颜色变换
        bitmap = replaceBitmapColor(BitmapFactory.decodeResource(context.getResources(), id), colorMode);
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
        bitmapBody.top = frameHeight*frameSkipByAnimMode[animMode];
        bitmapBody.right = bitmap.getWidth();
        bitmapBody.bottom = bitmapBody.top + frameHeight;
        //引入心跳
        _animHeart.addUnit(heartUnit);
    }

    public void moveGrid(int x, int y){
        _gridX += x;
        _gridY += y;
        leftMargin += x*_mapParam.xGridPixel;
        topMargin += y*_mapParam.yGridPixel;
    }

    public void moveGridTo(int x, int y){
        _gridX = x;
        _gridY = y;
        leftMargin = x*_mapParam.xGridPixel;
        topMargin = y*_mapParam.yGridPixel;
    }

    //设置图片参数
    public void setColorMode(int colorMode){
        if(_colorMode != colorMode) {
            synchronized (paint) {
                bitmap.recycle();
                bitmap = replaceBitmapColor(BitmapFactory.decodeResource(_context.getResources(), _id), colorMode);
                _colorMode = colorMode;
            }
        }
    }

    public void setAnimMode(int animMode){
        if(_animMode != animMode){
            //镜像和恢复
            if(animMode == 5 || _animMode == 5)
                bitmap = Bitmap.createBitmap(bitmap, 0, 0, (int)bitmap.getWidth(), (int)bitmap.getHeight(), matrix, true);matrix.postScale(1, 1);
        }
        _animMode = animMode;
        upgradeHeartType(_animMode);
    }

    private void upgradeHeartType(int animMode){
        if(animMode == 0)
            heartUnit.type = FeHeart.TYPE_ANIM_STAY;
        else if(animMode == 1)
            heartUnit.type = FeHeart.TYPE_ANIM_SELECT;
        else
            heartUnit.type = FeHeart.TYPE_ANIM_MOVE;
    }

    private FeHeartUnit heartUnit = new FeHeartUnit(FeHeart.TYPE_ANIM_STAY, new FeHeartUnit.TimeOutTask(){
        public void run(int count){
            //移动框图
            bitmapBody.left = 0;
            bitmapBody.top = frameHeight*(frameSkipByAnimMode[_animMode] + count);
            bitmapBody.right = bitmap.getWidth();
            bitmapBody.bottom = bitmapBody.top + frameHeight;
            //调用一次onDrow
            FeAnimFilm.this.invalidate();
        }
    });

    private Rect bitmapDist = new Rect(0,0,0,0);
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        //相对布局位置偏移
        _mapParam.getRectByGrid(_gridX, _gridY, bitmapDist);
        int w = bitmapDist.width(), h = bitmapDist.height();
        bitmapDist.left -= w/2;
        bitmapDist.right += w/2;
        bitmapDist.top -= h;
//        bitmapDist.left = _mapParam.xAnimOffsetPixel + (int)this.getTranslationX() + _mapParam.mapDist.left + (int)leftMargin;
//        bitmapDist.top = _mapParam.yAnimOffsetPixel + (int)this.getTranslationY() + _mapParam.mapDist.top + (int)topMargin;
//        bitmapDist.right = bitmapDist.left + _mapParam.xAnimGridPixel;
//        bitmapDist.bottom = bitmapDist.top + _mapParam.yAnimGridPixel;
        //绘图
        canvas.drawBitmap(bitmap, bitmapBody, bitmapDist, paint);
    }

    public boolean checkHit(float x, float y){
        int xs = bitmapDist.left - _mapParam.xAnimOffsetPixel;
        int ys = bitmapDist.top - _mapParam.yAnimOffsetPixel;
        if(x > xs && x < xs + _mapParam.xGridPixel &&
            y > ys && y < ys + _mapParam.yGridPixel)
            return true;
        return false;
    }

    public Bitmap replaceBitmapColor(Bitmap oldBitmap, int type){
        if(type == 0)   //使用原色
            return oldBitmap;
        //相关说明可参考 http://xys289187120.blog.51cto.com/3361352/657590/
        Bitmap mBitmap = oldBitmap.copy(Bitmap.Config.ARGB_8888, true);
        //循环获得bitmap所有像素点
        int mBitmapWidth = mBitmap.getWidth();
        int mBitmapHeight = mBitmap.getHeight();
        int r,g,b,color,tcolor;

        for (int i = 0; i < mBitmapHeight; i++) {
            for (int j = 0; j < mBitmapWidth; j++) {
                //获得Bitmap 图片中每一个点的color颜色值
                //将需要填充的颜色值如果不是
                //在这说明一下 如果color 是全透明 或者全黑 返回值为 0
                //getPixel()不带透明通道 getPixel32()才带透明部分 所以全透明是0x00000000
                //而不透明黑色是0xFF000000 如果不计算透明部分就都是0了
                color = mBitmap.getPixel(j, i);
                //将颜色值存在一个数组中 方便后面修改
                /*if (color == oldColor) {
                    mBitmap.setPixel(j, i, newColor);  //将白色替换成透明色
                }
                mBitmap.setPixel(j, i, newColor);*/
                tcolor = color&0xff000000;
                r = (color&0x00ff0000)>>16;
                g = (color&0x0000ff00)>>8;
                b = color&0x000000ff;
                if(type == 1 && b > g){         //蓝色 换 绿色
                    tcolor |= (int)(r*0.8) << 16;
                    tcolor |= g << 0;
                    tcolor |= (int)(b*0.8) << 8;
                    mBitmap.setPixel(j, i, tcolor);
                }
                else if(type == 2 && b > r){    //蓝色 换 红色
                    tcolor |= r << 0;
                    tcolor |= (int)(g*0.8) << 8;
                    tcolor |= b << 16;
                    mBitmap.setPixel(j, i, tcolor);
                }
            }
        }
        return mBitmap;
    }

}

