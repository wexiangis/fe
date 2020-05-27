package com.example.fe;


/*
    /assets/save/sX 文件夹资源管理器
 */
public class FeAssetsSave {

    private int sXCurrent = 0;//当前档位
    private FeFile file;//文件操作工具

    public FeAssetsSave(){
        file = new FeFile();
        //从 /assets/save/last.txt 读取最后存档位置
        sXCurrent = Integer.valueOf(file.readFile("/save/last.txt", "0", 8));
    }

    /* ---------- 存档槽位检查 ---------- */

    /*
        获取最后存档位置
     */
    public int getLastSx(){
        return sXCurrent;
    }

    /*
        获取所有存档槽状态
        num: 存档槽总数
        返回: int[num][0], 0/表示空 其它表示章节
            int[num][1], 0/表示非中断状态 1/中断
     */
    public int[][] getSx(int num){
        int[][] ret = new int[num][2];
        //遍历num个sX文件夹中的info.txt文件
        for(int i = 0; i < num; i++)
        {
            //拼接路径
            String path = String.format("/save/s%d/info.txt", i);
            //默认值,无存档,非中断状态
            ret[i][0] = ret[i][1] = 0;
            //文件存在
            if(file.exists(path))
            {
                //读取文件
                String[] line = file.readFile(path, "0;0;0;", 16).split(";");
                if(line != null){
                    ret[i][0] = Integer.valueOf(line[0]);//得到章节数
                    if(line.length > 1)
                        ret[i][1] = Integer.valueOf(line[1]);//得到是否中断
                }
            }
        }
        return ret;
    }

    /* ---------- 存档槽位操作 ---------- */

    //创建新存档
    public void newSx(int sX){
        String rootPath = String.format("/save/s%d", sX);
        //删空档位
        file.delete(rootPath);
        //创建档位,章节0,非中断状态
        file.writeFile(rootPath, "info.txt", "1;0;0;");
        //更新最后存档位置
        sXCurrent = sX;
        file.writeFile("/save/", "last.txt", String.valueOf(sXCurrent));
    }

    //删除存档
    public void delSx(int sX){
        file.delete(String.format("/save/s%d", sX));
    }

    //复制存档sXSrc到sXDist
    public void copySx(int sXDist, int sXSrc){
        file.copy(String.format("/save/s%d", sXDist), String.format("/save/s%d", sXSrc));
    }
    
}
