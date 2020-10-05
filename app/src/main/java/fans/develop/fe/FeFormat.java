package fans.develop.fe;

/*
    自己把控的数据类型转换
 */
public class FeFormat {

    /*
        安全的字符串转int
     */
    public static int StringToInt(String string) {
        try {
            return Integer.parseInt(string);
        } catch (java.lang.NumberFormatException e) {
            return 0;
        }
    }

    /*
        自定义的字符串编码为int
        格式: 0000000000 ~ 2099999999, 共10位数(且对4 bytes的int数来说这是一个正数)
              前2位表示字符串长度, 即字符串长度不能超过20
              再3位表示hex的异或校验和, 即000~255
              后5位表示这20个字符的代码(00~94)的加权(乘上位置序号)叠加和, 例如: 13*1 + 33*2 ... + 42*20
        关于字符的代码: 即用00~94来表示ascii字符空格到'~', 所以输入的String必须为这个范围内的字符
     */
    public static int StringEncode(String string){
        if(string.length() > 20)
            return 0;

        byte crc = 0;
        for(int i = 0; i < string.length(); i++)
            crc ^= string.charAt(i);

        int sum = 0;
        for(int i = 0; i < string.length(); i++)
            sum += (string.charAt(i) - ' ') * (i + 1);

        return string.length() * 100000000 + (crc & 0xFF) * 100000 + sum;
    }

    /*
        从hex字符串提取int数据
     */
    public static int HexStringToInt(String string) {
        int ret = 0;
        int count = 0;
        //过滤掉 "0x" "0X"
        if (string.length() > 1) {
            if (string.charAt(0) == '0' &&
                    (string.charAt(1) == 'x' || string.charAt(1) == 'X'))
                count += 2;
        }
        //提取hex数据
        for (; count < string.length(); count++) {
            char c = string.charAt(count);
            ret <<= 4;
            if (c >= '0' && c <= '9')
                ret += c - '0';
            else if (c >= 'a' && c <= 'z')
                ret += c - 'a' + 10;
            else if (c >= 'A' && c <= 'Z')
                ret += c - 'A' + 10;
            else
                break;
        }
        return ret;
    }
    
    /*
        获取物品id、使用次数和杀敌数
     */
    public static int itemId(int it){
        return it % 1000;
    }
    public static int itemUse(int it){
        return it / 1000 % 1000;
    }
    public static int itemKill(int it){
        return it / 1000000 % 1000;
    }
    /*
        使用次数和杀敌数操作
     */
    public static int itemUseAdd(int it){
        return it + 1000;
    }
    public static int itemUseReset(int it){
        return it % 1000 + it / 1000000 * 1000000;
    }
    public static int itemKillAdd(int it){
        return it + 1000000;
    }
}
