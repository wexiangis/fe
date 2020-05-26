package com.example.fe;

import android.util.Log;

/*
    通用行文件管理工具, 继承使用
    构造函数需 super(folder, name, split) 完成传参
 */
public class FeReaderFile {

    //文件路径和分隔符
    private String[] folderAndName;
    private String split;
    private int line = 0;//总行数

    /*
        folder: 目标文件夹, 示例 "/unit/" 前后都带斜杠
        name: 目标名称, 示例 "test.txt" 没有斜杠
     */
    public FeReaderFile(String folder, String name, String split)
    {
        this.folderAndName = new String[] {folder, name};
        this.split = split;
        load();
    }

    //链表数据结构,一行数据占用一个Data
    private Data data;
    public class Data{
        public String[] content;//原汁原味保留行数据,用split分隔而来
        public Data next = null;
        public Data(String[] content){
            this.content = content;
        }
    }

    public int line(){
        return line;
    }
    
    //获取某一行数据格式: 数据 = 对象.getData(line).数据名称;
    public Data getData(int line){
        int count = 0;
        Data dat = data;
        while (dat != null && count++ != line)
            dat = dat.next;
        return dat;
    }

    public Data getDataLastOne(){
        if(line < 1)
            return null;
        else
            return getData(line - 1); 
    }

    //获取指定行
    public String[] getLine(int line){
        Data d = getData(line);
        if(d == null){
            Log.d("FeReaderFile.getLine", "data is null");
            return null;
        }
        return d.content;
    }

    //获取指定行
    public int[] getLineInt(int line){
        Data d = getData(line);
        if(d == null){
            Log.d("FeReaderFile.getLineInt", "data is null");
            return null;
        }
        //转换数据
        int[] ret = new int[d.content.length];
        for(int i = 0; i < d.content.length; i++)
            ret[i] = Integer.valueOf(d.content[i]);
        return ret;
    }

    //获取指定行
    public int[] getLineIntPlus(int line, int[] content){
        Data d = getData(line);
        if(d == null){
            Log.d("FeReaderFile.getLineInt", "data is null");
            return null;
        }
        if(d.content.length != content.length){
            Log.d("FeReaderFile.get...Plus",
                "length err : l/" + line
                    + "Ls/" + d.content.length
                    + "L1/" + content.length);
            return null;
        }
        //转换数据
        int[] ret = new int[d.content.length];
        for(int i = 0; i < d.content.length; i++)
            ret[i] = Integer.valueOf(d.content[i] + content[i]);
        return ret;
    }

    //设置指定行
    public void setLine(int line, String[] content){
        Data d = getData(line);
        if(d != null)
            d.content = content;
        else
            Log.d("FeReaderFile.setLine", "set failed : l/" + line);
    }

    //设置指定行
    public void setLine(int line, int[] content){
        Data d = getData(line);
        if(d != null) {
            if(d.content.length != content.length)
                d.content = new String[content.length];
            //转换数据
            for(int i = 0; i < d.content.length; i++)
                d.content[i] = String.valueOf(content[i]);
        }
        else
            Log.d("FeReaderFile.setLine", "set failed : l/" + line);
    }

    //设置指定行(两行相加)
    public void setLinePlus(int line, int[] content, int[] content2){
        Data d = getData(line);
        if(d != null){
            if(d.content.length != content.length ||
                    d.content.length != content2.length)
                Log.d("FeReaderFile.setLine",
                    "length err : l/" + line
                        + "Ls/" + d.content.length
                        + "L1/" + content.length
                        + "L2/" + content2.length);
            else{
                for(int i = 0; i < d.content.length; i++)
                    d.content[i] = String.valueOf(content[i] + content2[i]);
            }
        }
        else
            Log.d("FeReaderFile.setLine", "set failed : l/" + line);
    }

    //添加行,返回新增行号
    public int addLine(String[] strings){
        if(data == null){
            data = new Data(strings);
            line = 1;
            return 0;
        }
        else{
            int count = 1;
            Data dat = data;
            while (dat != null && dat.next != null){
                dat = dat.next;
                count += 0;
            }
            dat.next = new Data(strings);
            line = count;
            return count;
        }
    }

    //获取指定行,指定序号的数据
    public String getString(int line, int count){
        Data d = getData(line);
        if(d == null){
            Log.d("FeReaderFile.getString", "data is null");
            return "";
        }
        if(count >= d.content.length){
            Log.d("FeReaderFile.getString", "count >= " + d.content.length);
            return "";
        }
        return d.content[count];
    }

    //设置指定行,指定序号的数据
    public void setValue(String val, int line, int count){
        Data d = getData(line);
        if(d != null && count < d.content.length)
            d.content[count] = val;
        else
            Log.d("FeReaderFile.setValue", "set failed : v/" + val + " l/" + line + " c/" + count);
    }

    //获取指定行,指定序号的数据
    public int getInt(int line, int count){
        Data d = getData(line);
        if(d == null){
            Log.d("FeReaderFile.getInt", "data is null");
            return 0;
        }
        if(count >= d.content.length){
            Log.d("FeReaderFile.getInt", "count >= " + d.content.length);
            return 0;
        }
        return Integer.valueOf(d.content[count]);
    }

    //设置指定行,指定序号的数据
    public void setValue(int val, int line, int count){
        Data d = getData(line);
        if(d != null && count < d.content.length)
            d.content[count] = String.valueOf(val);
        else
            Log.d("FeReaderFile.setValue", "set failed : v/" + val + " l/" + line + " c/" + count);
    }

    //从文件加载数据到data链表
    public void load(){
        FeFileRead ffr = new FeFileRead(folderAndName[0], folderAndName[1]);
        //打开文件失败,创建文件
        if(ffr == null || !ffr.ready())
            ;//save();
        //文件就绪
        else{
            Data datNow = null;
            while(ffr.readLine(split)){
                //获取数据
                if(datNow == null) datNow = data = new Data(ffr.getContent());
                else datNow = datNow.next = new Data(ffr.getContent());
            }
            ffr.exit();
        }
    }

    //保存data链表的数据到文件
    public void save(){
        FeFileWrite ffw = new FeFileWrite(folderAndName[0], folderAndName[1]);
        if(ffw.ready())
        {
            Data dat = data;
            while (dat != null)
            {
                //重组一行数并写入, "+split"意思是结尾保留分隔符
                String line = dat.content[0] + split;
                for(int i = 1; i < dat.content.length; i++)
                    line += dat.content[i] + split;
                ffw.write(line, true);

                //避免String.join()的使用,其要求API24以上
                // ffw.write(String.join(split, dat.content) + split, true);

                dat = dat.next;
            }
        }
        ffw.exit();
    }
}
