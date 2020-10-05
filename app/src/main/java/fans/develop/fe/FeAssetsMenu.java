package fans.develop.fe;

import android.graphics.Bitmap;
import android.util.Log;

/*
    /assets/menu 文件夹资源管理器
 */
public class FeAssetsMenu {

    private FeReaderBitmap bitmapReader = new FeReaderBitmap();

    void FeDataMenu() {
        //初始化一个图片读取工具
        bitmapReader = new FeReaderBitmap();
    }

    // head bitmap chain
    public FeChain2<Bitmap> bitmapChain = new FeChain2<Bitmap>(-1, null);

    public Bitmap getSrc(String path, String name) {
        //把字符串转换为唯一id
        int id = FeFormat.StringEncode(path + name);
        //先从缓冲区(链表)中找
        Bitmap ret = bitmapChain.find(id);
        //没有再从assets中加载
        if(ret == null){
            ret = bitmapReader.load_bitmap(String.format("/menu/%s/", path), name + ".png");
            //防止加载失败
            if(ret == null)
                ret = bitmapReader.load_bitmap("/menu/item/", "items.png");
            //缓存到链表,下次可以省去加载的时间
            bitmapChain.add(id, ret);
        }
        return ret;
    }
}
