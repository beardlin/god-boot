package net.lantrack.framework.common.utils;

import java.text.DecimalFormat;

/**
 * @Description
 * @Author dahuzi
 * @Date 2019/11/24 23:41
 */
public class NumUtil {
    /**
     * 随机生成4位数字
     *
     * @return
     */
    public static int randomFour() {
        return (int) ((Math.random() * 9 + 1) * 1000);
    }

    /**
     * 随机生成5位数字
     *
     * @return
     */
    public static int randomFive() {
        return (int) ((Math.random() * 9 + 1) * 10000);
    }

    /**
     * 随机生成6位数字
     *
     * @return
     */
    public static int randomSix() {
        return (int) ((Math.random() * 9 + 1) * 100000);
    }

    /**
	 * 将double保留几位精度
	 * @param: Double d,int i
	 * @author:luoxiaolin
	 * @date:2017年8月3日
	 */
	public static String doubleToStr(Double d,int i){
		if(d==null){
			d=0D;
		}
		if(i==0){
			return d+"";
		}
		String format = "0.";
		for(int j=0;j<i;j++){
			format+="0";
		}
		DecimalFormat df = new DecimalFormat(format);
		return df.format(d);
	}
	/**
	 * 将double保留2位
	 * @param: Double d
	 * @author:luoxiaolin
	 * @date:2017年8月3日
	 */
	public static String doubleToStr(Double d){
		return doubleToStr(d,2);
	}
	/**
	 * String 转 Double
	 * @Return Double
	 */
	public static Double toDouble(String str){
	   try {
	     return  Double.valueOf(str);
        } catch (Exception e) {
            // TODO: handle exception
//            e.printStackTrace();
            return 0D;
        }
    }

}
