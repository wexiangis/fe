package com.example.fe;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.util.Log;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


/*
    加载地图后所产生的一系列关键参数,用于传递给地图上的人物动画
 */
public class FeParamMap {

    private Activity activity;
    public int section;

    //
    public Bitmap bitmap = null, tBitmap = null;

    public FeParamMap(Activity act, int feSection)
    {
        activity = act;
        section = feSection;
        //获取屏幕参数
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        //初始化map参数结构体
        loadMap(section);
        init(dm.widthPixels, dm.heightPixels, xGrid, yGrid, piexlPerGrid);
        //两参数分别为xy缩放比例
        float xp = (float)width/tBitmap.getWidth()/2;
        if(xp > 1.5f)
            xp = 1.5f;
        float yp = (float)height/tBitmap.getHeight()/2;
        if(yp > 1.5f)
            yp = 1.5f;
        matrix.postScale(xp, yp);
        bitmap = Bitmap.createBitmap(tBitmap, 0, 0, (int)tBitmap.getWidth(), (int)tBitmap.getHeight(), matrix, true);
        //释放
        tBitmap.recycle();
        tBitmap = null;
    }

    //----- 地图基本信息 -----

    //屏幕实际宽高
    public int screenWidth = 1920, screenHeight = 1080;

    //地图实际显示区域
    public Rect mapDist = null;
    //地图移动格子数
    public int xGridErr = 0, yGridErr = 0;

    //地图实际显示宽高像素
    public int width = 1920, height = 1080;
    //地图横纵向格数
    public int xGrid = 20, yGrid = 10;
    //屏幕横纵向格数
    public int screenXGrid = 20, screenYGrid = 10;

    //横纵向每格像素
    public float xGridPixel = 96, yGridPixel = 108;
    //横纵向动画方块像素大小
    public int xAnimGridPixel = 192, yAnimGridPixel = 216;
    //横纵向动画初始偏移
    public int xAnimOffsetPixel = 48, yAnimOffsetPixel = 54;

    //地图适配屏幕
    public void init(int screenXSixe, int screenYSize, int mapXGrid, int mapYGrid, int piexlPG){
        //比较屏幕和地图长和高比例
        float screenXDivY = (float)screenXSixe/screenYSize;
        float mapXDivY = (float)mapXGrid/mapYGrid;
        //限制屏幕最大显示格数
        screenXGrid = screenXSixe/piexlPG;
        screenYGrid = screenYSize/piexlPG;
        //关键参数备份
        piexlPerGrid = piexlPG;
        screenWidth = screenXSixe;
        screenHeight = screenYSize;
        xGrid = mapXGrid;
        yGrid = mapYGrid;
        //屏幕的长高比例大于地图,地图参照屏幕长来缩放
        if(screenXDivY > mapXDivY){
            //得到屏幕横向实际显示格数
            if(mapXGrid < screenXGrid)
                screenXGrid = mapXGrid;
            //得到屏幕竖向实际显示格数
            screenYGrid = (int)((float)screenXGrid/screenWidth*screenHeight);
        }
        //其他时候,地图参照屏幕高来缩放
        else{
            //得到屏幕竖向实际显示格数
            if(mapYGrid < screenYGrid)
                screenYGrid = mapYGrid;
            //得到屏幕横向实际显示格数
            screenXGrid = (int)((float)screenYGrid/screenHeight*screenWidth);
        }
        //横纵向每格像素
        xGridPixel = (float)screenWidth/screenXGrid;
        yGridPixel = (float)screenHeight/screenYGrid;
        //关联参数
        xAnimGridPixel = (int)(xGridPixel*2);
        yAnimGridPixel = (int)(yGridPixel*2);
        xAnimOffsetPixel = (int)(-xGridPixel/2);
        yAnimOffsetPixel = (int)(-yGridPixel);
        //得到地图实际显示大小
        width = (int)(xGridPixel*mapXGrid);
        height = (int)(yGridPixel*mapYGrid);
        //和原大小进行比较后中心缩放
        if(mapDist == null)
            mapDist = new Rect(0, 0, width, height);
        else{
            if(mapDist.left + width < screenWidth)
                mapDist.left = screenWidth - width;
            else{
                mapDist.left -= (width - (mapDist.right - mapDist.left))/2;
                mapDist.left = (int)((int)((float)mapDist.left/width*xGrid)*xGridPixel);
            }
            mapDist.right = width - mapDist.left;
            //
            if(mapDist.top + height < screenHeight)
                mapDist.top = screenHeight - height;
            else{
                mapDist.top -= (height - (mapDist.bottom - mapDist.top))/2;
                mapDist.top = (int)((int)((float)mapDist.top/height*yGrid)*yGridPixel);
            }
            mapDist.bottom = height - mapDist.top;
        }
        //
        xGridErr = yGridErr = 0;
    }

//    public boolean fileExist(String name)
//    {
//        try{
//            File file = new File(name);
//            if(!file.exists())
//                return false;
//        }
//        catch (Exception e) {
//            return false;
//        }
//        return true;
//    }

    //----- 地图方格信息 -----

    //
    public Matrix matrix = new Matrix();

    //方格显示时可以接受的最小像素值
    private int piexlPerGrid = 104;

    //
    public class MapInfo{
        //方格矩阵信息
        public int w, h;
        public short[][] grid;//[line][count]
        //方格类型信息
        public int total;
        public String[] name;
        public short[] defend;
        public short[] avoid;
        public short[] plus;
        public short[] mov;
        public short[] type;
        public String[] info;
        //
        public MapInfo(int width, int height)
        {
            w = width;
            h = height;
            grid = new short[height][width];
            //
            total = 100;
            name = new String[100];
            defend = new short[100];
            avoid = new short[100];
            plus = new short[100];
            mov = new short[100];
            type = new short[100];
            info = new String[100];
        }
    }
    //
    public MapInfo mapInfo;

    //从assets文件夹加载map
    public void loadMap(int section)
    {
        String mapFolder = "/assets/map/map"+String.format("%02d",section);
        //
        String mapPath1 = mapFolder + "/map.png";
        String mapPath2 = mapFolder + "/map.jpg";
        String mapSize = mapFolder + "/size.txt";
        String mapGrid = mapFolder + "/grid.txt";
        String mapGridInfo = "/assets/map/grid_info.txt";
        // get bitmap
        try {
            InputStream is = getClass().getResourceAsStream(mapPath1);
            if(is == null)
                is = getClass().getResourceAsStream(mapPath2);
            //default
            if(is == null)
                tBitmap = BitmapFactory.decodeResource(activity.getResources(), R.drawable.map_xxx_30x30);
            else {
                tBitmap = BitmapFactory.decodeStream(is);
                is.close();
            }
        } catch (java.io.FileNotFoundException e) {
            Log.d("loadMap: get bitmap", "not found");
        } catch (IOException e) {
            Log.d("loadMap: get bitmap", e.getMessage());
        }
        // get size
        xGrid = yGrid = 30;//default
        piexlPerGrid = 104;//default
        try {
            InputStream is = getClass().getResourceAsStream(mapSize);
            if(is != null){
                byte[] buff = new byte[64];
                java.util.Arrays.fill(buff, (byte)',');
                if(is.read(buff) >= 4)
                {
                    String[] dat = new String(buff).split(",");
                    if(dat.length > 0) xGrid = Integer.parseInt(dat[0]);
                    if(dat.length > 1) yGrid = Integer.parseInt(dat[1]);
                    if(dat.length > 2) piexlPerGrid = Integer.parseInt(dat[2]);
                }
                is.close();
            }
        } catch (java.io.FileNotFoundException e) {
            Log.d("loadMap: get size", "not found");
        } catch (IOException e) {
            Log.d("loadMap: get size", e.getMessage());
        }
        // mapInfo init
        mapInfo = new MapInfo(xGrid, yGrid);
        // get grid
        try {
            int countLine = 0;
            InputStream is = getClass().getResourceAsStream(mapGrid);
            if (is != null) {
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                String line = null;
                //分行读取
                while ((line = br.readLine()) != null) {
                    String[] lineData = line.split(",");
                    //
                    for(int i = 0; i < lineData.length; i++)
                        mapInfo.grid[countLine][i] = (short)Integer.parseInt(lineData[i]);
                    //
                    countLine += 1;
                    if(countLine >= mapInfo.h)
                        break;
                }
                is.close();//关闭输入流
            }
            //default
            if(countLine == 0){
                for(int i = 0; i < mapInfo.h; i++)
                    java.util.Arrays.fill(mapInfo.grid[i], (short)0);
            }
        } catch (java.io.FileNotFoundException e) {
            Log.d("loadMap: get grid", "not found");
        } catch (IOException e) {
            Log.d("loadMap: get grid", e.getMessage());
        }
        //grid info
        try {
            int countLine = 0;
            InputStream is = getClass().getResourceAsStream(mapGridInfo);
            if (is != null) {
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                String line = null;
                //分行读取
                while ((line = br.readLine()) != null) {
                    String[] lineData = line.split(",");
                    //
                    if(lineData.length > 1)
                        mapInfo.name[countLine] = lineData[1];
                    if(lineData.length > 2)
                        mapInfo.defend[countLine] = (short)Integer.parseInt(lineData[2]);
                    if(lineData.length > 3)
                        mapInfo.avoid[countLine] = (short)Integer.parseInt(lineData[3]);
                    if(lineData.length > 4)
                        mapInfo.plus[countLine] = (short)Integer.parseInt(lineData[4]);
                    if(lineData.length > 5)
                        mapInfo.mov[countLine] = (short)Integer.parseInt(lineData[5]);
                    if(lineData.length > 6)
                        mapInfo.type[countLine] = (short)Integer.parseInt(lineData[6]);
                    if(lineData.length > 7)
                        mapInfo.info[countLine] = lineData[7];
                    //
                    countLine += 1;
                    if(countLine >= 100)
                        break;
                }
                //
                mapInfo.total = countLine;
                is.close();//关闭输入流
            }
            //default
            if(countLine == 0){
                mapInfo.name[0] = "无";
                mapInfo.defend[0] = 0;
                mapInfo.avoid[0] = 0;
                mapInfo.plus[0] = 0;
                mapInfo.mov[0] = 1;
                mapInfo.type[0] = 0;
                mapInfo.info[0] = "";
            }
        } catch (java.io.FileNotFoundException e) {
            Log.d("loadMap: get grid info", "not found");
        } catch (IOException e) {
            Log.d("loadMap: get grid info", e.getMessage());
        }
    }

    //----- 地图梯形变换 -----

    //梯形缩进的格数和像素
    public int reduceGrid = 0;
    public float reduce = 0;
    //梯形区域里的方格状态: 横纵向格数, 起始格子
    public int srcGridX = 0, srcGridY = 0, srcGridXStart = 0, srcGridYStart = 0;
    //中心甜区,用于判断是否需要挪动地图来让选中人物居中
    public Rect srcGridCenter = new Rect(0,0,0,0);
    //[n][0]:行高, [n][1]:总行高, [n][2]:横向offset, [n][3]:平均宽
    public float[][] srcGridLine = new float[64][4];
    //屏幕在地图上框了一个矩形,然后拉高拉宽上边两个点,框到一个倒梯形区域
    public float[] srcPoint = new float[8];
    //srcPoint的坐标数据按地图的实际分辨率转换之后的位置
    public float[] srcPointBitmap = new float[8];
    //上面框到的倒梯形输出到屏幕的位置
    public float[] distPoint = new float[8];

    //梯形变换缩进格数
    private int transferGrid = 4;

    //获取梯形转换矩阵,用于绘制
    public void getMatrix(){
        //
        srcPoint[0] = -mapDist.left;
        srcPoint[1] = -mapDist.top;
        srcPoint[2] = -mapDist.left;
        srcPoint[3] = -mapDist.top + screenHeight;
        srcPoint[4] = -mapDist.left + screenWidth;
        srcPoint[5] = -mapDist.top + screenHeight;
        srcPoint[6] = -mapDist.left + screenWidth;
        srcPoint[7] = -mapDist.top;
        //梯形左右和上边缩进格数
        reduce = xGridPixel*transferGrid;
        //地图靠近边界时逐渐恢复比例
        if(reduce > width - srcPoint[6])
            reduce = width - srcPoint[6];
        if(reduce > srcPoint[0])
            reduce = srcPoint[0];
        if(reduce > srcPoint[1])
            reduce = srcPoint[1];
        //梯形变换
        srcPoint[0] -= reduce;
        srcPoint[6] += reduce;
        srcPoint[1] -= reduce;
        srcPoint[7] -= reduce;
        //防止梯形出屏
//        if(srcPoint[0] < 0) srcPoint[0] = 0;
//        if(srcPoint[6] > width) srcPoint[6] = width;
//        if(srcPoint[1] < 0) srcPoint[1] = 0;
//        if(srcPoint[7] < 0) srcPoint[7] = 0;
        //
        float xPow = (float)bitmap.getWidth()/width;
        float yPow = (float)bitmap.getHeight()/height;
        //
        srcPointBitmap[0] = srcPoint[0]*xPow;
        srcPointBitmap[1] = srcPoint[1]*yPow;
        srcPointBitmap[2] = srcPoint[2]*xPow;
        srcPointBitmap[3] = srcPoint[3]*yPow;
        srcPointBitmap[4] = srcPoint[4]*xPow;
        srcPointBitmap[5] = srcPoint[5]*yPow;
        srcPointBitmap[6] = srcPoint[6]*xPow;
        srcPointBitmap[7] = srcPoint[7]*yPow;
        //
        distPoint[0] = 0;
        distPoint[1] = 0;
        distPoint[2] = 0;
        distPoint[3] = screenHeight;
        distPoint[4] = screenWidth;
        distPoint[5] = screenHeight;
        distPoint[6] = screenWidth;
        distPoint[7] = 0;
        //梯形变换
        matrix.setPolyToPoly(srcPointBitmap, 0, distPoint, 0, 4);

        //关键参数提取
        reduceGrid = Math.round(reduce/xGridPixel);
        srcGridX = reduceGrid*2 + screenXGrid;
        srcGridY = reduceGrid + screenYGrid;
        srcGridXStart = Math.round(srcPoint[0]/xGridPixel);
        srcGridYStart = Math.round(srcPoint[1]/yGridPixel);

        //中心甜区
        srcGridCenter.left = srcGridXStart + reduceGrid + 2;
        srcGridCenter.top = srcGridYStart + reduceGrid + 2;
        srcGridCenter.right = srcGridCenter.left + (screenXGrid - 4) - 1;
        srcGridCenter.bottom = srcGridCenter.top + (screenYGrid - 4) - 1;

        //第一行的高, 总高, 横向offset, 平均宽
        srcGridLine[0][0] = yGridPixel - reduce*1.5f/srcGridY - reduceGrid*3.0f;
        srcGridLine[0][1] = srcGridLine[0][0];
        srcGridLine[0][2] = srcGridLine[0][1]/screenHeight*reduce;
        srcGridLine[0][3] = (srcGridLine[0][2]*2 + screenWidth)/srcGridX;
        //最后一行的高, 总高, 横向offset, 平均宽
        srcGridLine[srcGridY-1][0] = yGridPixel + reduceGrid;
        srcGridLine[srcGridY-1][1] = screenHeight;
        srcGridLine[srcGridY-1][2] = srcGridLine[srcGridY-1][1]/screenHeight*reduce;
        srcGridLine[srcGridY-1][3] = (srcGridLine[srcGridY-1][2]*2 + screenWidth)/srcGridX;
        //最后一行和第一行高的差值
        float ySErr = srcGridLine[srcGridY-1][0]*srcGridY - screenHeight;
        float n = 0;//分母
        float[] m = new float[srcGridY];//分子
        //把分子数组累加到分母n
        float basePoint = 10000;
        float basePointCount = 0;
        float basePointPlusBase = 0.99f;
        for(int i = 0; i < srcGridY; i++) {
            basePointCount += basePoint;
            m[i] = basePointCount;
            n += m[i];
            //
            if(reduceGrid > 0) {
                basePoint *= basePointPlusBase;
                basePointPlusBase *= 0.99f;
            }
        }
        //统计每行信息
        for(int i = srcGridY-2; i >= 0; i--) {
            srcGridLine[i][0] = srcGridLine[srcGridY-1][0] - m[srcGridY-1-i]/n*ySErr;
            srcGridLine[i][1] = srcGridLine[i+1][1] - srcGridLine[i+1][0];
            srcGridLine[i][2] = srcGridLine[i][1]/screenHeight*reduce;
            srcGridLine[i][3] = (srcGridLine[i][2]*2 + screenWidth)/srcGridX;
        }
    }

    //----- 选中类型记录 -----

    //选中事件状态
    private int click_type = 0;
    //select type
    public static short SELECT_MAP = 0x1;//选中地图
    public static short SELECT_UNIT = 0x2;//选中人物
    public static short SELECT_MAPINFO = 0x4;//选中地图信息
    public static short SELECT_UNIT_MENU = 0x8;//选中人物菜单
    public static short SELECT_SYS_MENU = 0x10;//选中系统菜单
    public static short SELECT_CHAT = 0x20;//对讲中
    public static short SELECT_MOVE = 0x40;//移动中
    public static short SELECT_MOVE_END = 0x80;//移动结束
    //
    public void cleanSelectType(short type){
        click_type &= (~type);
    }
    public void setSelectType(short type){
        click_type |= type;
    }
    public boolean checkSelectType(short type){
        if((click_type&type) == type)
            return true;
        else
            return false;
    }

    //----- 求梯形中的某一格子 -----

    public class GridInMap{
        //方格梯形
        public Path selectPath = new Path();
        //方格
        public Rect selectRect = new Rect(0,0,0,0);
        //方格横纵格数
        public int[] selectPoint = new int[2];
        //
        public void clean(){
            selectPath.reset();
        }
    }

    public GridInMap selectMap = new GridInMap();
    public GridInMap selectUnit = new GridInMap();

    //输入格子求位置
    public void getRectByGrid(int xG, int yG, GridInMap gim){
        gim.selectPoint[0] = xG;
        gim.selectPoint[1] = yG;
        //
        if(xG >= srcGridXStart &&
                xG < srcGridXStart + srcGridX &&
                yG >= srcGridYStart &&
                yG < srcGridYStart + srcGridY)
        {
            int x = xG - srcGridXStart;
            int y = yG - srcGridYStart;
            //
            gim.selectRect.top = (int)(srcGridLine[y][1] - srcGridLine[y][0]);
            gim.selectRect.bottom = (int)(srcGridLine[y][1]);
            gim.selectRect.left = (int)(x*srcGridLine[y][3] - srcGridLine[y][2]);
            gim.selectRect.right = (int)(gim.selectRect.left + srcGridLine[y][3]);
            //
            gim.selectPath.reset();
            //
            if(y == 0){
                gim.selectPath.moveTo(x * screenWidth / srcGridX, 0);
                gim.selectPath.lineTo((x + 1) * screenWidth / srcGridX, 0);
            }else{
                gim.selectPath.moveTo(
                        x * srcGridLine[y-1][3] - srcGridLine[y-1][2],
                        srcGridLine[y-1][1]);
                gim.selectPath.lineTo(
                        (x + 1) * srcGridLine[y-1][3] - srcGridLine[y-1][2],
                        srcGridLine[y-1][1]);
            }
            gim.selectPath.lineTo(gim.selectRect.right, gim.selectRect.bottom);
            gim.selectPath.lineTo(gim.selectRect.left, gim.selectRect.bottom);
            gim.selectPath.close();
        }
        else{
            gim.selectRect.left = (int)(-xGridPixel)*2;
            gim.selectRect.right = (int)(-xGridPixel);
            gim.selectRect.top = (int)(-yGridPixel)*2;
            gim.selectRect.bottom = (int)(-yGridPixel);
        }
    }

    //输入坐标求格子位置
    public void getRectByLocation(float x, float y, GridInMap gim) {
        for(int yCount = 0; yCount < srcGridY; yCount++){
            if(y < srcGridLine[yCount][1]){
                gim.selectPoint[1] = yCount + srcGridYStart;
                gim.selectPoint[0] = (int)((x + srcGridLine[yCount][2])/srcGridLine[yCount][3]) + srcGridXStart;
                //
                getRectByGrid(gim.selectPoint[0], gim.selectPoint[1], gim);
                return;
            }
        }
        getRectByGrid(-1, -1 + srcGridXStart, gim);
    }
}

