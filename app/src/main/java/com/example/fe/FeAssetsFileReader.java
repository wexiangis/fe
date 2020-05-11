package com.example.fe;

/*
    通用行文件管理工具, 继承使用
 */
public class FeAssetsFileReader {
    //文件路径和分隔符
    public String[] folderAndName;
    public String split;
    //链表数据结构,一行数据占用一个Data
    public Data data;
    public class Data{
        public String[] content;//原汁原味保留行数据,用split分隔而来
        public Data next = null;
        public Data(String[] content){
            this.content = content;
        }
    }
    //获取某一行数据格式: 数据 = 对象.getData(line).数据名称;
    public Data getData(int line){
        int count = 0;
        Data dat = data;
        while (dat != null && count++ != line)
            dat = dat.next;
        return dat;
    }
    //获取/设置指定行,指定序号的数据
    public String getString(int line, int count){
        Data d = getData(line);
        if(d != null || count < d.content.length)
            return d.content[count];
        return "";
    }
    public void setValue(String val, int line, int count){
        Data d = getData(line);
        if(d != null && count < d.content.length)
            d.content[count] = val;
    }
    //获取/设置指定行,指定序号的数据
    public int getInt(int line, int count){
        Data d = getData(line);
        if(d != null || count < d.content.length)
            return Integer.valueOf(d.content[count]);
        return -1;
    }
    public void setValue(int val, int line, int count){
        Data d = getData(line);
        if(d != null && count < d.content.length)
            d.content[count] = String.valueOf(val);
    }
    //从文件加载数据到data链表
    public void load(){
        FeFileRead ffr = new FeFileRead(folderAndName[0], folderAndName[1]);
        Data datNow = null;
        while(ffr.readLine(split)){
            //获取数据
            if(datNow == null) datNow = data = new Data(ffr.getContent());
            else datNow = datNow.next = new Data(ffr.getContent());
        }
        ffr.exit();
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
