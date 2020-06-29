package fans.develop.fe;

/*
    场地及人员信息
 */
public class FeSectionOperation {

    private FeInfoMap map;

    //网格人员id, 无则0
    public short[][] unit;

    public FeSectionOperation(FeInfoMap map){
        this.map = map;
        this.unit = new short[map.xGrid][map.yGrid];
    }

}
