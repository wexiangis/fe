package fans.develop.fe;

import android.view.ViewGroup;
import android.graphics.LinearGradient;

/*
    人物列表参数管理
 */
public class FeSectionUnit {

    // ----------- 选中信息 -----------

    //当前选中的unit的位置
    public FeInfoGrid selectSite = new FeInfoGrid();

    //当前选中的unit
    public FeViewUnit selectView = null;

    // ----------- 渐变色着色器 -----------

    //3种颜色的渐变色着色器列表
    public FeShader shaderR, shaderG, shaderB;
    //着色器当前位置
    public int shaderCount = 0;
    //获取当前着色器
    public LinearGradient getShaderR(){
        return shaderR.getLinearGradient(shaderCount, 0);
    }
    public LinearGradient getShaderG(){
        return shaderG.getLinearGradient(shaderCount, 0);
    }
    public LinearGradient getShaderB(){
        return shaderB.getLinearGradient(shaderCount, 0);
    }

    // ----------- unit集合 -----------

    public void addCamp(int camp, FeViewUnit view){
        ;
    }

    public void removeCamp(int camp, int order){
        ;
    }

    public class Camp{

        public Camp(){
            ;
        }
    }
}
