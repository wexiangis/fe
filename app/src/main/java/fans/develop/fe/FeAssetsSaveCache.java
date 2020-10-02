package fans.develop.fe;

/*
    /assets/save/sX/cache 文件夹资源管理器,章节运行信息缓存
 */
public class FeAssetsSaveCache extends FeAssetsCamps{

    private FeAssetsUnit assetsUnit;
    private int sX;
    private String folder;

    public FeAssetsSaveCache(FeAssetsUnit assetsUnit, int sX) {
        super(assetsUnit, sX);
        this.assetsUnit = assetsUnit;
        this.sX = sX;
        //sX
        folder = String.format("/save/s%d/cache/", sX);
        //file
        round = new Round(folder, "round.txt", ";");
    }

    //----- file -----

    public Round round;

    //----- api -----

    //----- class -----

    public class Round extends FeReaderFile {

        public int getTurn() {
            return getInt(0, 0);
        }

        public int getCamp() {
            return getInt(0, 1);
        }

        public int getOrder() {
            return getInt(0, 2);
        }

        public int getTime() {
            return getInt(0, 3);
        }

        public void setTurn(int turn) {
            setValue(turn, 0, 0);
        }

        public void setCamp(int camp) {
            setValue(camp, 0, 1);
        }

        public void setOrder(int order) {
            setValue(order, 0, 2);
        }

        public void setTime(int time) {
            setValue(time, 0, 3);
        }

        public Round(String folder, String name, String split) {
            super(folder, name, split);
            //文件没有加载,创建文件
            if (line() == 0) {
                addLine(new String[]{"0", "0", "0", "0", "回合/当前阵营/当前人物序号/章节时间"});
                save();
            }
        }
    }
}
