package fans.develop.fe;

import android.content.*;
import android.graphics.*;
import android.service.quicksettings.*;

/*
    地图上的人物头像等信息框
 */
public class FeViewUnitMenu extends FeView {

    private FeAssetsCamps assetsCamps;
    private FeSectionCallback sectionCallback;
    //画笔: 背景, 框图背景, 内框图背景, label
    private Paint paintBg, paintFrame, paintFrame2;
    //
    private Paint paintLabel, paintValue, paintName, paintProfession;
    //背景和屏幕间隔, 框和背景间隔
    private int edgeBg = 5, edgeFrame = 8, edgeText = 10;
    //
    private Rect rectSrcHead;
    //输出范围: 背景, 上框, 右列表框, 主框
    private Rect rectBg, rectTop, rectRight, rectMain;
    //输出范围: lv,exp和hp 攻击,回避,必杀等 头像图片
    private Rect rectHp, rectAk, rectHead;
    //缓存当前人物,若变动则重新加载参数和头像
    private FeUnit unit = null;
    //人物头像
    private Bitmap bitmapHead;
    //矩形代替进度条, B为背景(成长率
    private Rect rectPro, rectProB;
    private Paint paintPro, paintProB;;

    public FeViewUnitMenu(Context context, FeAssetsCamps assetsCamps, int order, FeSectionCallback sectionCallback) {
        super(context);
        this.assetsCamps = assetsCamps;
        this.sectionCallback = sectionCallback;
        unit = new FeUnit(sectionCallback.getAssets(), assetsCamps, order);
        bitmapHead = unit.getHead();

        FeSectionMap sectionMap = sectionCallback.getSectionMap();
        //范围计算
        rectBg = new Rect(0, 0, sectionMap.screenWidth, sectionMap.screenHeight);
        rectTop = new Rect(
            edgeBg, edgeBg, 
            rectBg.right - (int)(rectBg.width() / 3.5),
            (int)(rectBg.height() / 3.5));
        rectRight = new Rect(
            rectTop.right + edgeBg,
            rectTop.top,
            rectBg.right - edgeBg,
            rectBg.bottom - edgeBg);
        rectMain = new Rect(
            rectTop.left,
            rectTop.bottom + edgeBg,
            rectTop.right,
            rectBg.bottom - edgeBg);
        rectSrcHead = new Rect(0, 0, 1, 1);
        //范围计算
        rectHp = new Rect(
            rectTop.left + edgeFrame,
            rectTop.top + edgeFrame,
            rectTop.left + edgeFrame + rectTop.width() / 4,
            rectTop.bottom - edgeFrame);
        rectHead = new Rect(
            rectTop.right - edgeFrame - rectTop.height() - edgeFrame * 2,
            rectTop.top + edgeFrame,
            rectTop.right - edgeFrame,
            rectTop.bottom - edgeFrame);
        rectAk = new Rect(
            rectHp.right + edgeFrame,
            rectHp.top,
            rectHead.left - edgeFrame,
            rectHp.bottom);

        rectPro = new Rect(0, 0, 0, 0);
        rectProB = new Rect(0, 0, 0, 0);

        //半透明背景
        paintBg = new Paint();
        paintBg.setColor(0x80404040);

        paintFrame = new Paint();
        paintFrame.setColor(0x80000080);

        paintFrame2 = new Paint();
        paintFrame2.setColor(0x80404040);

        paintLabel = new Paint();
        paintLabel.setColor(0xFF808080);
        paintLabel.setTextSize(rectHp.height() / 8);
        //paintLabel.setTypeface(Typeface.SANS_SERIF);
        paintLabel.setTextAlign(Paint.Align.LEFT);

        paintValue = new Paint();
        paintValue.setColor(0xFFBBBBBB);
        paintValue.setTextSize(rectHp.height() / 7);
        paintValue.setTypeface(Typeface.DEFAULT_BOLD);
        paintValue.setTextAlign(Paint.Align.LEFT);

        paintName = new Paint();
        paintName.setColor(0xFFFFFFFF);
        paintName.setTextSize(rectHp.height() / 6);
        paintName.setTypeface(Typeface.DEFAULT_BOLD);
        paintName.setTextAlign(Paint.Align.LEFT);
        
        paintProfession = new Paint();
        paintProfession.setColor(0x80FFFFFF);
        paintProfession.setTextSize(rectHp.height() / 7);
        //paintProfession.setTypeface(Typeface.DEFAULT_BOLD);
        paintProfession.setTextAlign(Paint.Align.LEFT);
        
        edgeText = (int)(paintLabel.getTextSize() / 2);

        paintPro = new Paint();
        paintPro.setColor(0x8000FF00);

        paintProB = new Paint();
        paintProB.setColor(0x80808080);
        
    }

    public void move(float xErr, float yErr) {
        ;
    }
    
    public void click(float x, float y) {
        ;
    }

    public void order(int order) {
        //更新unit
        if(unit == null || unit.order() != order) {
            unit = new FeUnit(sectionCallback.getAssets(), assetsCamps, order);
            bitmapHead = unit.getHead();
        }
    }

    /*
        rectMain可以通过左右滑动切换页面
        alpha: 透明度0.0~1.0
        xErr: 左右偏移量
     */
    public void drawPageBase(Canvas canvas, float alpha, int xErr) {
        paintLabel.setTextAlign(Paint.Align.CENTER);
        paintValue.setTextAlign(Paint.Align.CENTER);

        FeAssetsLabel assetsLabel = sectionCallback.getAssets().label;
        String[] lab = new String[]{
            assetsLabel.str(),
            assetsLabel.mag(),
            assetsLabel.skill(),
            assetsLabel.spe(),
            assetsLabel.luk(),
            assetsLabel.def(),
            assetsLabel.mde()
        };
        //第一纵列的x值和往右间隔
        int x = rectMain.width() / 8, xDiv = rectMain.width() / 8;
        //
        int y1 = rectMain.top + edgeText + (int)(paintLabel.getTextSize() * 1.5);
        int y2 = y1 + (int)(paintLabel.getTextSize() / 2);
        int y3 = rectMain.bottom - edgeText - (int)(paintLabel.getTextSize() * 2);
        int y4 = rectMain.bottom - edgeText - (int)(paintLabel.getTextSize() * 0.5);
        //进度条宽、高、起始x值、边沿缩进
        float pW = paintValue.getTextSize() * 2;
        int pH = y3 - y2;
        float pX = x - pW / 2;
        int pEdge = 5;
        for(int i = 0; i < lab.length; i += 1, x += xDiv, pX += xDiv)
        {
            //label
            canvas.drawText("999", x, y1, paintValue);
            //draw processBar bg
            rectProB.left = (int)pX;
            rectProB.top = y2;
            rectProB.right = (int)(pX + pW);
            rectProB.bottom = y3;
            canvas.drawRect(rectProB, paintProB);
            //group
            rectProB.top = y3 - (int)(pH * 0.75);
            canvas.drawRect(rectProB, paintProB);
            //ability
            rectPro.left = rectProB.left + pEdge;
            rectPro.right = rectProB.right - pEdge;
            rectPro.bottom = rectProB.bottom- pEdge;
            rectPro.top = y3 - (int)(pH * 0.5);
            canvas.drawRect(rectProB, paintProB);
            canvas.drawRect(rectPro, paintPro);
            //draw value
            canvas.drawText(lab[i], x, y4, paintValue);
        }
    }
    public void drawPageSkill(Canvas canvas, float alpha, int xErr) {
        
        ;
    }
    public void drawPageSupport(Canvas canvas, float alpha, int xErr) {
        ;
    }
    public void drawPageState(Canvas canvas, float alpha, int xErr) {
        ;
    }

    private void drawTopFrame(Canvas canvas) {
        paintLabel.setTextAlign(Paint.Align.LEFT);
        paintValue.setTextAlign(Paint.Align.LEFT);

        FeAssetsLabel assetsLabel = sectionCallback.getAssets().label;
        // --- draw hp lv ---
        int x1 = rectHp.left + edgeText;
        int x2 = rectHp.left + rectHp.width() / 3 + edgeText;
        int y1 = rectHp.top + rectHp.height() / 4 - edgeText / 2;
        int y2 = rectHp.top + rectHp.height() / 2 - edgeText;
        int y3 = rectHp.bottom - rectHp.height() / 4 - edgeText / 2 - (int)(paintLabel.getTextSize() / 2);
        int y4 = rectHp.bottom - edgeText - (int)(paintLabel.getTextSize() / 2);
        int xValLv = x1 + (int)(paintLabel.getTextSize() * 3 / 2);
        int xValExp = x2 + (int)(paintLabel.getTextSize() * 5 / 2);
        int xValHp = x1 + (int)(paintLabel.getTextSize() * 4 / 2);
        int xValName = x1;
        int xValProfession = x1;
        //label
        canvas.drawText(assetsLabel.level(), x1, y3, paintLabel);
        canvas.drawText(assetsLabel.exp(), x2, y3, paintLabel);
        canvas.drawText(assetsLabel.hp(), x1, y4, paintLabel);
        //value
        canvas.drawText(String.valueOf(unit.level()), xValLv, y3, paintValue);
        canvas.drawText(String.valueOf(unit.exp()), xValExp, y3, paintValue);
        canvas.drawText(String.format("%d / %d", unit.hpRes(), unit.hp()), xValHp, y4, paintValue);
        canvas.drawText(unit.getProfessionName(), xValProfession, y2, paintProfession);
        canvas.drawText(unit.getName(), xValName, y1, paintName);

        // --- draw attack critical ---
        x1 = rectAk.left + edgeText;
        x2 = rectAk.left + rectAk.width() / 3 + edgeText / 2;
        int x3 = rectAk.left + rectAk.width() * 2 / 3 + edgeText / 2;
        y1 = rectAk.top + rectAk.height() / 6 + (int)(paintLabel.getTextSize() / 2);
        y2 = rectAk.top + rectAk.height() * 3 / 6 + (int)(paintLabel.getTextSize() / 2);
        y3 = rectAk.top + rectAk.height() * 5 / 6 + (int)(paintLabel.getTextSize() / 2);
        int xErr = (int)(paintLabel.getTextSize() * 2.3);
        int yErr = 0;
        //label
        canvas.drawText(assetsLabel.hit(), x1, y1, paintLabel);
        canvas.drawText(assetsLabel.avoid(), x2, y1, paintLabel);
        canvas.drawText(assetsLabel.attackSpeed(), x3, y1, paintLabel);
        canvas.drawText(assetsLabel.critical(), x1, y2, paintLabel);
        canvas.drawText(assetsLabel.criticalAvoid(), x2, y2, paintLabel);
        canvas.drawText(assetsLabel.mov(), x3, y2, paintLabel);
        canvas.drawText(assetsLabel.body(), x1, y3, paintLabel);
        canvas.drawText(assetsLabel.partner(), x2, y3, paintLabel);
        //value
        canvas.drawText("999", x1 + xErr, y1 + yErr, paintValue);
        canvas.drawText("999", x2 + xErr, y1 + yErr, paintValue);
        canvas.drawText("999", x3 + xErr, y1 + yErr, paintValue);
        canvas.drawText("999", x1 + xErr, y2 + yErr, paintValue);
        canvas.drawText("999", x2 + xErr, y2 + yErr, paintValue);
        canvas.drawText("999", x3 + xErr, y2 + yErr, paintValue);
        canvas.drawText("999", x1 + xErr, y3 + yErr, paintValue);
        canvas.drawText("啊啊啊啊啊啊", x2 + xErr, y3 + yErr, paintValue);

        // --- draw head ---
        //确定头像源位置
        if(bitmapHead.getWidth() == bitmapHead.getHeight()){//杂兵的等比例头像,切掉底部一块使用
            rectSrcHead.right = bitmapHead.getWidth();
            rectSrcHead.left = 0;
            rectSrcHead.bottom = 73;
        }
        else{//特殊人物的头像,需抠图
            rectSrcHead.right = 95;
            rectSrcHead.left = 7;
            rectSrcHead.bottom = 79;
        }
        //rectHead.left = rectHead.right - rectSrcHead.height() * rectSrcHead.width() / rectHead.height();
        canvas.drawBitmap(bitmapHead, rectSrcHead, rectHead, paintValue);
    }
    private void drawMainFrame(Canvas canvas) {
        drawPageBase(canvas, 1, 0);
    }
    private void drawRightFrame(Canvas canvas) {
        ;
    }

    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(unit == null)
            return;

        BitmapShader bs = new BitmapShader(
            unit.getHead(),
            Shader.TileMode.REPEAT,
            Shader.TileMode.REPEAT
        );
        //paintFrame.setShader(bs);
            
        //画背景
        canvas.drawRect(rectBg, paintBg);
        //根据阵营更换底色
        paintFrame.setColor(FePallet.color(0xBB404080, unit.camp()));
        //画框图
        canvas.drawRect(rectTop, paintFrame);
        canvas.drawRect(rectRight, paintFrame);
        canvas.drawRect(rectMain, paintFrame);
        //画框图
        canvas.drawRect(rectHp, paintFrame2);
        canvas.drawRect(rectAk, paintFrame2);
        canvas.drawRect(rectHead, paintFrame2);

        drawTopFrame(canvas);
        drawMainFrame(canvas);
        drawRightFrame(canvas);
    }

    /* ---------- abstract interface ---------- */

    public void onDestory() {
        ;
    }
}
