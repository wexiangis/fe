package com.example.fe;

import android.view.ViewGroup;
import android.graphics.LinearGradient;

public class FeParamUnit {

    //当前选中的unit的位置
    public FeInfoGrid selectSite = new FeInfoGrid();

    //当前选中的unit
    public FeViewUnit selectView = null;

    //当前unit数组
    public ViewGroup group = null;

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
}
