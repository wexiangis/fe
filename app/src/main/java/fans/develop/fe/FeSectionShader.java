package fans.develop.fe;

import android.graphics.*;

/*
    各个图层画图所需着色器都从这里取(主要用于移动、攻击和特效范围的绘制)
 */
public class FeSectionShader {

    private FeSectionCallback sectionCallback;

    //3种颜色的渐变色着色器列表
    private FeShaderColor shaderR, shaderG, shaderB;
    //着色器当前位置
    private int shaderCount = 0;

    // ----------- 构造函数 -----------

    public FeSectionShader(FeSectionCallback sectionCallback) {
        this.sectionCallback = sectionCallback;
        //初始化着色器列表
        shaderR = new FeShaderColor(
                new RectF(0, 0, sectionCallback.getSectionMap().xGridPixel, sectionCallback.getSectionMap().yGridPixel),
                (int) (sectionCallback.getSectionMap().xGridPixel / 10), 1,
                20,
                new int[]{0x80FF8080, 0x80FF2020, 0x80FF8080},
                new float[]{0.25F, 0.5F, 7.5F},
                Shader.TileMode.REPEAT
        );
        shaderG = new FeShaderColor(
                new RectF(0, 0, sectionCallback.getSectionMap().xGridPixel, sectionCallback.getSectionMap().yGridPixel),
                (int) (sectionCallback.getSectionMap().xGridPixel / 10), 1,
                20,
                new int[]{0x8060FF60, 0x8020FF20, 0x8060F60F},
                new float[]{0.25F, 0.5F, 7.5F},
                Shader.TileMode.REPEAT
        );
        shaderB = new FeShaderColor(
                new RectF(0, 0, sectionCallback.getSectionMap().xGridPixel, sectionCallback.getSectionMap().yGridPixel),
                (int) (sectionCallback.getSectionMap().xGridPixel / 10), 1,
                20,
                new int[]{0x808080FF, 0x802020FF, 0x808080FF},
                new float[]{0.1F, 0.5F, 0.9F},
                Shader.TileMode.REPEAT
        );
        //引入心跳,让渐变色动起来
        sectionCallback.addHeartUnit(heartUnit);
    }

    //动画心跳回调
    private FeHeartUnit heartUnit = new FeHeartUnit(FeHeart.TYPE_FRAME_HEART, new FeHeartUnit.TimeOutTask() {
        public void run(int count) {
            //让渐变色光条循环动起来
            if (shaderCount + 1 < shaderR.xCount())
                shaderCount += 1;
            else
                shaderCount = 0;
        }
    });

    /*
        释放时记得移除心跳
     */
    public void release() {
        sectionCallback.removeHeartUnit(heartUnit);
    }

    // ----------- api -----------

    /*
        获得RGB三种颜色的动态、渐变颜色着色器
     */
    public LinearGradient getShaderR() {
        return shaderR.getLinearGradient(shaderCount, 0);
    }

    public LinearGradient getShaderG() {
        return shaderG.getLinearGradient(shaderCount, 0);
    }

    public LinearGradient getShaderB() {
        return shaderB.getLinearGradient(shaderCount, 0);
    }

    // ----------- class -----------

    /*
        基于LinearGradient封装的动态、渐变颜色着色器
        通过不断切换列表中的shader实现“渐变色”+“移动”的效果
        如: getLinearGradient(x++, y++)
    */
    class FeShaderColor {

        //动态移动时横、纵向循环移动量
        private int xLength, yLength;
        //着色器数组,根据当前移动位置,选择使用对应的着色器
        private LinearGradient[][] linearGradient = null;

        //根据位置取用数组中的着色器
        public LinearGradient getLinearGradient(int xCount, int yCount) {
            if (xCount < 0 || xCount > xLength)
                return linearGradient[0][0];
            if (yCount < 0 || yCount > yLength)
                return linearGradient[0][0];
            return linearGradient[xCount][yCount];
        }

        //当前移动位置
        public int xCount() {
            return xLength;
        }
        public int yCount() {
            return yLength;
        }

        /*
            rect: 填充区间
            xCount: 横向移动量
            yCount: 纵向移动量
            step: 步长
            colors[]: 颜色列表, 例如 new int[]{0xFF00FF00, 0xFFFF0000, 0xFF0000FF}
            positions[]: 指定colors[]里的颜色出现位置0.0~1.0, 例如 new float[]{0.25, 0.5, 0.75}
        */
        public FeShaderColor(RectF rect, int xCount, int yCount, int step, int colors[], float positions[], Shader.TileMode tile) {

            xLength = xCount;
            yLength = yCount;

            linearGradient = new LinearGradient[xCount][yCount];

            for (int x = 0, xStep = 0; x < xCount; x++) {
                for (int y = 0, yStep = 0; y < yCount; y++) {
                    linearGradient[x][y] = new LinearGradient(
                            rect.left + xStep,
                            rect.top + yStep,
                            rect.right + xStep,
                            rect.bottom + yStep,
                            colors,
                            positions,
                            tile
                    );
                    yStep += step;
                }
                xStep += step;
            }
        }
    }

}
