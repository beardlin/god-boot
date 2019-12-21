package net.lantrack.framework.common.utils;

import org.apache.commons.lang.time.DateFormatUtils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

/**
 *@Description 丰富的日期控件
 *@Author dahuzi
 *@Date 2019/11/9  12:05
 */
public class DateUtil extends org.apache.commons.lang.time.DateUtils {

	private static DateUtil instance = null;

	public static DateUtil getDateUtilInstance(){
		if(instance == null){
		   instance = new DateUtil();
		}
		return instance;
	}
	//获取当期周第一天
	public static Date getFirstDayOfWeek(Date date) {
		Calendar cal = Calendar.getInstance();
		try {
			cal.setTime(date);
			cal.set(Calendar.DAY_OF_WEEK, 1);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return addDays(cal.getTime(),1);
	}
	//获取当前周最后一天
	public static Date getLastDayOfWeek(Date date) {
		Date firstDayOfWeek = getFirstDayOfWeek(date);
		return addDays(firstDayOfWeek,6);
	}

	/**
	 * 获取俩个时间段的时间
	 * @return
	 * @date 2019年1月18日
	 */
	public  static String getBetweenDate(Date d1,Date d2) {
		long time = d1.getTime()-d2.getTime();
		if(time<0) {
			time = -time;
		}
		long second = time/1000;
		return formatSecond((int)second);
	}
	/**
	 *
	 * metoodName: getNowDate
	 * @date:  2019年1月22日 下午3:24:14
	 * @author:lmy
	 * @param format 时间类型如"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM",
		"yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
		"yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM",
		"yyyy-MM-dd HH:mm:ss.S","yyyy/MM/dd HH:mm:ss.SSS"
	 * @return Timestamp
	 */
	public static Timestamp getNowDate(String format) {
		Date date = new Date();
		String nowTime = new SimpleDateFormat(format).format(date);
		return Timestamp.valueOf(nowTime);
	}

	public static String countdate(String str) {
		Date nowDate = getNowDate("yyyy-MM-dd HH:mm:ss");
		int nowYear = Integer.parseInt(nowDate.toString().substring(0,4));
        try {
            String ret = "";
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            long create = sdf.parse(str).getTime();
            Calendar now = Calendar.getInstance();
            long ms  = 1000*(now.get(Calendar.HOUR_OF_DAY)*3600+now.get(Calendar.MINUTE)*60+now.get(Calendar.SECOND));//毫秒数
            long ms_now = now.getTimeInMillis();
            int year = Integer.parseInt(str.substring(0,4));
            int hour = Integer.parseInt(str.substring(11,str.length()-6));
            if(ms_now-create<ms){
            	ret = str.substring(11,str.length()-3);
            	if(hour >= 5 && hour<9){
            		ret = "早上 "+str.substring(11,str.length()-3);
            	}
            	if(hour >= 9 && hour<12){
            		ret = "上午 "+str.substring(11,str.length()-3);
            	}
            	if(hour >= 12 && hour<18){
            		ret = "下午 "+str.substring(11,str.length()-3);
            	}
               // ret = "今天  "+ str.substring(11,str.length()-3);
            }else if(ms_now-create<(ms+24*3600*1000)){
                ret = "昨天";
            }
//            else if(ms_now-create<(ms+24*3600*1000*2)){
//                ret = str.substring(0,10);
//            }
            else{
            	if(nowYear == year){
            		ret= str.substring(5,7) + "月" + str.substring(8,10)+"日";
            	}else{
            		ret= str.substring(0,4)+"年"+str.substring(5,7)+"月"+str.substring(8,10)+"日";
            	}
            }
            return ret;
            } catch (Exception e) {
           // e.printStackTrace();
            }
        return null;
    }

	 /**
	  *
	  * metoodName: day1isAfterDay2
	  * 时间1是否在时间2 之前， 是返回 true
	  * @date:  2019年1月22日 下午3:38:42
	  * @author:lmy
	  * @param d1
	  * @param d2
	  * @param format
	  * @return
	  * @throws ParseException boolean
	  */
	 public static boolean day1isAfterDay2(String d1, String d2 ,String format) throws ParseException{
		 SimpleDateFormat df = new SimpleDateFormat(format);
		 Date date1 = df.parse(d1);
		 Date date2 = df.parse(d2);
	     return date2.after(date1);
	 }

	 public static String theMonthAddOne(String d1) throws ParseException{
		 SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
		 Date date1 = df.parse(d1);
	     Calendar calendar = new GregorianCalendar();
	     calendar.setTime(date1);
	     calendar.add(Calendar.MONTH,1);
	     date1=calendar.getTime();
	     return df.format(date1).toString();
	 }


	public  static Date beforeDay(int beforday) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - beforday);
		return calendar.getTime();
	}

	public  static String formatDate(String format,Date date) {
	    if(date==null){
	        return null;
	    }
		SimpleDateFormat sFormat = new SimpleDateFormat(format);
			return sFormat.format(date);
	}
	public  static Date str2Date(String format,String date) {
		SimpleDateFormat sFormat = new SimpleDateFormat(format);
		try {
			return sFormat.parse(date.trim());
		} catch (ParseException e) {
			return null;
		}
	}
	/**
	 *
	 *
	 * @description: 日期转化成毫秒
	 * @param date
	 * @return  long
	 *
	 */
	public static long date2MS(Date date){
		long millionSeconds = date.getTime();
		return millionSeconds;
	}

	private static String[] parsePatterns = {
		"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM",
		"yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
		"yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM",
		"yyyy-MM-dd HH:mm:ss.S","yyyy/MM/dd HH:mm:ss.SSS"
		};
	/**
	 * 将时间字符串格式化为标准格式
	 * @Return String
	 */
	public  static String formatDate(String date) {
        return formatDate("yyyy-MM-dd HH:mm:ss", parseDate(date));
    }
	/**
	 * 得到当前日期字符串 格式（yyyy-MM-dd）
	 */
	public static String getDate() {
		return getDate("yyyy-MM-dd");
	}

	/**
	 * 得到当前日期字符串 格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
	 */
	public static String getDate(String pattern) {
		return DateFormatUtils.format(new Date(), pattern);
	}

	/**
	 * 得到日期字符串 默认格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
	 */
	public static String formatDate(Date date, Object... pattern) {
		String formatDate = null;
		if (pattern != null && pattern.length > 0) {
			formatDate = DateFormatUtils.format(date, pattern[0].toString());
		} else {
			formatDate = DateFormatUtils.format(date, "yyyy-MM-dd");
		}
		return formatDate;
	}

	/**
	 * 得到日期时间字符串，转换格式（yyyy-MM-dd HH:mm:ss）
	 */
	public static String formatDateTime(Date date) {
		return formatDate(date, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 得到当前时间字符串 格式（HH:mm:ss）
	 */
	public static String getTime() {
		return formatDate(new Date(), "HH:mm:ss");
	}

	/**
	 * 得到当前日期和时间字符串 格式（yyyy-MM-dd HH:mm:ss）
	 */
	public static String getDateTime() {
		return formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 得到当前年份字符串 格式（yyyy）
	 */
	public static String getYear() {
		return formatDate(new Date(), "yyyy");
	}

	/**
	 * 得到当前月份字符串 格式（MM）
	 */
	public static String getMonth() {
		return formatDate(new Date(), "MM");
	}

	/**
	 * 得到当天字符串 格式（dd）
	 */
	public static String getDay() {
		return formatDate(new Date(), "dd");
	}

	/**
	 * 得到当前星期字符串 格式（E）星期几
	 */
	public static String getWeek() {
		return formatDate(new Date(), "E");
	}
	/**
	 * 日期型字符串转化为日期 格式
	 * { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm",
	 *   "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm",
	 *   "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm" }
	 */
	public static Date parseDate(Object str) {
		if (str == null){
			return null;
		}
		try {
			return parseDate(str.toString(), parsePatterns);
		} catch (ParseException e) {
//		    e.printStackTrace();
			return null;
		}
	}

	/**
	 * 获取过去的天数
	 * @param date
	 * @return
	 */
	public static long pastDays(Date date) {
		long t = new Date().getTime()-date.getTime();
		return t/(24*60*60*1000);
	}

	/**
	 * 获取过去的小时
	 * @param date
	 * @return
	 */
	public static long pastHour(Date date) {
		long t = new Date().getTime()-date.getTime();
		return t/(60*60*1000);
	}

	/**
	 * 获取过去的分钟
	 * @param date
	 * @return
	 */
	public static long pastMinutes(Date date) {
		long t = new Date().getTime()-date.getTime();
		return t/(60*1000);
	}

	/**
     * 获取过去的分钟
     * @param date
     * @return
     */
    public static long pastSeconds(Date date) {
        long t = new Date().getTime()-date.getTime();
        return t/1000;
    }

	/**
	 * 转换为时间（天,时:分:秒.毫秒）
	 * @param timeMillis
	 * @return
	 */
    public static String formatDateTime(long timeMillis){
		long day = timeMillis/(24*60*60*1000);
		long hour = (timeMillis/(60*60*1000)-day*24);
		long min = ((timeMillis/(60*1000))-day*24*60-hour*60);
		long s = (timeMillis/1000-day*24*60*60-hour*60*60-min*60);
		long sss = (timeMillis-day*24*60*60*1000-hour*60*60*1000-min*60*1000-s*1000);
		return (day>0?day+",":"")+hour+":"+min+":"+s+"."+sss;
    }

	/**
	 * 获取两个日期之间的天数
	 *
	 * @param before
	 * @param after
	 * @return
	 */
	public static double getDistanceOfTwoDate(Date before, Date after) {
		long beforeTime = before.getTime();
		long afterTime = after.getTime();
		return (afterTime - beforeTime) / (1000 * 60 * 60 * 24);
	}

	/**
	 * 传入yyyy-MM-dd,yyyy-MM-dd
	 * 返回俩个时间段之间的日期List
	 * @Return List<String>
	 */
	public static List<String> getDateListBetween(String date){
	    List<String> list = new ArrayList<String>();
	    if(date==null||!date.contains(",")){
	        return list;
	    }
	    String[] split = date.split(",");
	    if(split.length>2){
	        return list;
	    }
	    String start = "";
	    String end = "";
	    if(split.length==1){
	        start = split[0];
	        end = getDate();
	    }else{
	        start = split[0];
            end = split[1];
	    }
	    if(start==null||"".equals(start)){
	        return list;
	    }
	    Date dateStart = str2Date("yyyy-MM-dd",start);
	    if(dateStart==null){
	        return list;
	    }
	    if(dateStart.after(new Date())){
	        return list;
	    }
	    String begin=start;
	    list.add(begin);
	    while (!end.equals(begin)) {
	        dateStart = addDays(dateStart,1);
	        begin =formatDate("yyyy-MM-dd",dateStart);
	        list.add(begin);
        }
	    return list;
	}
	/**
     * 传入yyyy-MM-dd HH:mm:ss,yyyy-MM-dd HH:mm:ss
     * 间隔时间intervalTime
     * 返回俩个时间段之间的MinutesList,如：2017-08-12 09:15:00,2017-08-12 09:45:00
     * 间隔为30分钟
     * @Return List<String>
     */
    public static List<String> getMinutesListBetween(String dateTime,int intervalTime){
        List<String> list = new ArrayList<String>();
        if(dateTime==null||!dateTime.contains(",")){
            return list;
        }
        String[] split = dateTime.split(",");
        if(split.length>2){
            return list;
        }
        String start = "";
        String end = "";
        if(split.length==1){
            start = split[0];
            end = getDateTime();
        }else{
            start = split[0];
            end = split[1];
        }
        if(start==null||"".equals(start)){
            return list;
        }
        Date dateStart = str2Date("yyyy-MM-dd HH:mm:ss",start);
        Date dateEnd = str2Date("yyyy-MM-dd HH:mm:ss",end);
        if(dateStart==null||dateEnd==null){
            return list;
        }
        if(dateStart.after(new Date())){
            return list;
        }
        String begin=start;
        list.add(begin);
        while (dateStart.before(dateEnd)) {
            dateStart = addMinutes(dateStart, intervalTime);//增加时间
            begin =formatDate("yyyy-MM-dd HH:mm:ss",dateStart);
            list.add(begin);
        }
        return list;
    }
    /**
     * 将秒数格式化为  0：0：秒
     * @Return String
     */
    public static String formatNumberSecond(int second){
        int hour = (second/(60*60));
        int min = ((second/(60))-hour*60);
        int scd = (second-hour*60*60-min*60);

        String format="";
        if(hour>0){
            String h="0";
            if(hour<10){
                h+=hour;
            }else{
                h=hour+"";
            }
            format+=h+":";
        }
        String m="0";
        if(min<10){
            m+=min;
        }else{
            m=min+"";
        }
        format+=m+":";

        String s="0";
        if(scd<10){
            s+=scd;
        }else{
            s=scd+"";
        }
        format+=s;
        return format;
    }
    /**
     * 将秒数格式化为  天： 时：分：秒
     * @Return String
     */
    public static String formatSecond(int second){
    	int day = (second/(60*60*24));
        int hour = ((second/(60*60))-day*24);
        long min = ((second/(60))-day*24*60-hour*60);
        long scd = (second-day*24*60*60-hour*60*60-min*60);
        String format="";
        if(day>0){
            format+=day+"天";
        }
        if(hour>0){
            format+=hour+"小时";
        }
        if(min>0){
            format+=min+"分";
        }
        format+=scd+"秒";
        return format;
    }

    /**
     * 传入yyyy-MM-dd HH:mm:ss,yyyy-MM-dd HH:mm:ss
     * 间隔时间intervalTime
     * 返回俩个时间段之间的HoursList,如：2017-08-23 08:21:00, 2017-08-24 08:21:00
     * @date:2017年8月25日
     */
    public static List<String> getHoursListBetween(String dateTime, int intervalHour) {
        List<String> list = new ArrayList<String>();
        if (dateTime == null || !dateTime.contains(",")) {
            return list;
        }
        String[] split = dateTime.split(",");
        if (split.length > 2) {
            return list;
        }
        String start = "";
        String end = "";
        if (split.length == 1) {
            start = split[0];
            end = getDate();
        } else {
            start = split[0];
            end = split[1];
        }
        Date dateStart = str2Date("yyyy-MM-dd HH:mm:ss",start);
        Date dateEnd = str2Date("yyyy-MM-dd HH:mm:ss",end);
        if (dateStart == null || dateEnd == null) {
            return list;
        }
        if(dateStart.after(new Date())){
            return list;
        }
        String begin=start;
        list.add(begin);
        while (dateStart.before(dateEnd)) {
            dateStart = addHours(dateStart, intervalHour);
            begin =formatDate("yyyy-MM-dd HH:mm:ss",dateStart);
            list.add(begin);
        }
        return list;
    }

    /**
     * 得到一天的时间段
     * 传入  2017-08-24 08:21:00
     * 返回  2017-08-23 08:21:00,2017-08-24 08:21:00
     * @date:2017年8月25日
     */
    public static String getDay(String data_time) {
        String startTime = data_time;
        String endTime = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = sdf.parse(data_time);
            Date endDate = DateUtil.addDays(date, -1);
            endTime = sdf.format(endDate);
        } catch (ParseException e) {
            return "";
        }
        data_time = endTime + "," + startTime;
        return data_time;
    }
    /**
     * 根据标准的时间格式获取当天日期
     * @Return String
     */
    public static String getDayByDateTime(String datetime){
        datetime = formatDate(datetime)==null?DateUtil.getDateTime():formatDate(datetime);
        return datetime.substring(0, 10);
    }
    /**
     * 是否已经过时
     * @param minute 定义过时时间（分钟）
     * @Return boolean
     */
    public static boolean ifTimePast(String datetime,int minute){
        long minutes = DateUtil.pastMinutes(DateUtil.parseDate(datetime));
        return minutes>=minute;
    }
    /**
     * 获取两个时间段内的间隔秒
     * @Return long
     */
    public static long getSecondInterval(String startTime,String endTime){
        try {
            Date start = DateUtil.parseDate(startTime);
            Date end = DateUtil.parseDate(endTime);
            long interval = end.getTime()-start.getTime();
            return interval/1000;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }

    }
    /**
     * 获取一个月的所有天
     * @param  date yyyy-MM
     * @throws ParseException
     * @Return List<String>
     */
    public static List<String> getMonthDays(String date) throws ParseException{
        String start=date+"-01";
        String month = theMonthAddOne(date);
        String end=month+"-01";
        Date days = addDays(parseDate(end, new String[]{"yyyy-MM-dd"}), -1);
        String endday = formatDate("yyyy-MM-dd", days);
        return getDateListBetween(start+","+endday);
    }
    /**
     * 得到N天的日期集合
     * 传入  2017-08-23
     * 返回[2017-08-19, 2017-08-20, 2017-08-21, 2017-08-22, 2017-08-23]
     */
    public static List<String> getdateList(String date, int num) {
        String beforeDate = "";
        List<String> dateList = new ArrayList<String>();
        Date date2 = str2Date("yyyy-MM-dd", date);
        dateList.add(formatDate(date2, "yyyy-MM-dd"));//先加入当天

        for (int i = 0; i < num - 1; i++) {
            beforeDate = formatDate(addDays(date2, -1), "yyyy-MM-dd");
            date2 = addDays(date2, -1);
            dateList.add(beforeDate);
        }
        Collections.sort(dateList);//倒序处理
        return dateList;
    }
    /**
     * 返回实时时间：2011-11-17 00:50:00
     * @param: D:\\datafile\\000081620111117005000CLBJ (或者：000081620111117005000CLBJ)
     * @date:2017年10月23日
     */
    public static String parsePathDate(String filePath) {
        String fileName = filePath;
        int charPosition = filePath.lastIndexOf("\\");
        if (charPosition != 0) {
            charPosition = charPosition + 1;
            fileName = filePath.substring(charPosition);
        }
        String dateStr = fileName.substring(7, 21);
        // 20111117005000
        dateStr = formatPathDate(dateStr);
        return dateStr;
    }

    public static String formatPathDate(String dateStr) {

        HashMap<String, String> dateRegFormat = new HashMap<String, String>();
        // 20111117005000
        dateRegFormat.put("^\\d{14}$", "yyyyMMddHHmmss");
        // 2011111700
        dateRegFormat.put("^\\d{10}$", "yyyyMMddHH");
        // 20111117
        dateRegFormat.put("^\\d{8}$", "yyyyMMdd");
        // 2014年3月12日 13时5分34秒
        dateRegFormat.put("^\\d{4}\\D+\\d{1,2}\\D+\\d{1,2}\\D+\\d{1,2}\\D+\\d{1,2}\\D+\\d{1,2}\\D*$","yyyy-MM-dd-HH-mm-ss");
        // 2014-03-12
        dateRegFormat.put("^\\d{4}\\D+\\d{2}\\D+\\d{2}$", "yyyy-MM-dd");
        // 2014-03
        dateRegFormat.put("^\\d{4}\\D+\\d{2}$", "yyyy-MM");
        // 13:05:34
        dateRegFormat.put("^\\d{2}\\s*:\\s*\\d{2}\\s*:\\s*\\d{2}$", "yyyy-MM-dd-HH-mm-ss");

        String curDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        DateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        DateFormat formatter2;
        String dateReplace = "";
        String strSuccess = "";
        try {
            for (String key : dateRegFormat.keySet()) {
                if (Pattern.compile(key).matcher(dateStr).matches()) {
                    formatter2 = new SimpleDateFormat(dateRegFormat.get(key));
                    if (key.equals("^\\d{2}\\s*:\\s*\\d{2}\\s*:\\s*\\d{2}$")|| key.equals("^\\d{2}\\s*:\\s*\\d{2}$")) {
                        // 拼接年月日
                        dateStr = curDate + "-" + dateStr;
                    }
                    // 全部转成固定格式：yyyy-MM-dd-HH-mm-ss
                    dateReplace = dateStr.replaceAll("\\D+", "-");
                    strSuccess = formatter1.format(formatter2.parse(dateReplace));
                    break;
                }
            }
        } catch (Exception e) {
            System.err.println("日期格式不对");
        }
        return strSuccess;
    }
    /**
     * 获取yyyyMMddHHmmss格式的时间
     * @return
     * @date 2019年4月11日
     */
    public static String getNumDateFormat() {
    	SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
    	return format.format(new Date());
    }

	public static void main(String[] args) throws ParseException{
//	    System.out.println(DateUtil.formatDate("2017-12-18"));
//	    System.out.println(DateUtil.pastMinutes(DateUtil.parseDate("2017-12-06 17:48:34")));
//	    List<String> between = getDateListBetween("2017-08-22,2017-08-28");
//	    System.out.println(between);
//	    System.out.println(getdateList("2017-08-23", 30));
//	    System.out.println(getMonthDays("2017-10"));
//	    System.out.println(day1isAfterDay2("2019-01-21 16:06:19","2019-01-21 16:06:20","yyyy-MM-dd HH:mm:ss"));
//	    long l = 123;
//	    System.out.println((int)l);
//	    System.out.println(formatPathDate("20171031091512"));
//	    System.out.println(parsePathDate("D:\\share\\car\\test\\000081620171031091523CLBJ"));
//	    System.out.print(formatDate("2017/11/01 14:11:12.000"));
//	    int flux = Integer.parseInt("3123");
//	    System.out.print(formatNumberSecond(flux));
//	    String d = "2017/08/28 00:01:05.000";
//	    Date parseDate = DateUtil.parseDate(d);
//	    Date str2Date = DateUtil.str2Date("yyyy/MM/dd HH:mm:ss",d);
//	    System.out.println(str2Date);
//	    System.out.println(parseDate);
//		System.out.println(getNumDateFormat());
//
//		System.out.println(getDate("yyyyMMdd"));
		System.out.println(DateUtil.getDate("yyyyMMddHHmmss"));
    }


}
