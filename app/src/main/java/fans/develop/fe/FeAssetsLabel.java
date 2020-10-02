package fans.develop.fe;

/*
    /assets/label 文件夹资源管理器
 */
public class FeAssetsLabel {

    //----- file -----

    public FeReaderFile ability = new FeReaderFile("/label/", "ability.txt", ";");
    public FeReaderFile quality = new FeReaderFile("/label/", "quality.txt", ";");

    //----- api -----

    // ability.txt
    public String level() {
        return ability.getString(0, 0);
    }

    public String exp() {
        return ability.getString(1, 0);
    }

    public String hp() {
        return ability.getString(2, 0);
    }

    public String str() {
        return ability.getString(3, 0);
    }

    public String mag() {
        return ability.getString(4, 0);
    }

    public String skill() {
        return ability.getString(5, 0);
    }

    public String spe() {
        return ability.getString(6, 0);
    }

    public String luk() {
        return ability.getString(7, 0);
    }

    public String def() {
        return ability.getString(8, 0);
    }

    public String mde() {
        return ability.getString(9, 0);
    }

    public String body() {
        return ability.getString(10, 0);
    }

    public String mov() {
        return ability.getString(11, 0);
    }

    public String weig() {
        return ability.getString(12, 0);
    }

    // quality.txt.txt
    public String hit() {
        return quality.getString(0, 0);
    }

    public String avoid() {
        return quality.getString(1, 0);
    }

    public String attackSpeed() {
        return quality.getString(2, 0);
    }

    public String critical() {
        return quality.getString(3, 0);
    }

    public String criticalAvoid() {
        return quality.getString(4, 0);
    }

    public String partner() {
        return quality.getString(5, 0);
    }

    public String rescue() {
        return quality.getString(6, 0);
    }

    public String damage() {
        return quality.getString(7, 0);
    }

    public String defend() {
        return quality.getString(8, 0);
    }
}