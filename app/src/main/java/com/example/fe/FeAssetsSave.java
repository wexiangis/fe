package com.example.fe;


/*
    /assets/save/sX 文件夹资源管理器
 */
public class FeAssetsSave {

    public FeAssetsUnit _unit;
    public int sXCurrent = 0;//当前档位

    public FeAssetsSave(FeAssetsUnit unit){
        this._unit = unit;
        //从 /assets/save/lastOne.txt 读取最后存档位置
        sXCurrent = 0;
    }

    /* ---------- 存档槽位检查 ---------- */

    //存档槽sX存在
    public Boolean getSxExists(int sX){
        return false;
    }
    //存档槽sX章节, 0/表示没有
    public int getSxSection(int sX){
        return 0;
    }
    //存档槽sX是否中断状态
    public Boolean getSxIpt(int sX){
        return false;
    }

    /* ---------- 存档槽位操作 ---------- */

    //创建新存档
    public void newSx(int sX){
        sXCurrent = sX;
    }

    //删除存档
    public void delSx(int sX){
    }

    //清空全部存档
    public void delAllSx(int sX){
    }

    //复制存档sXSrc到sXDist
    public void copySx(int sXDist, int sXSrc){
        sXCurrent = sX;
    }

    /* ---------- 读取数据 ---------- */

}
