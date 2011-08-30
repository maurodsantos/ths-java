package ths.core.security;

public class Validate {

	public static boolean isNumeric(String str) {
		return str.matches("^[0-9]+$");
	}
	
	public static boolean isEnglish(String str) {
		return str.matches("^[A-Za-z]+$");
	}
	
	/**
	 * 判断是否包含中文字符(含标点符号)
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isChinese(String str) {
		return str.matches("\"([^\"|[\u4e00-\u9fa5\ufe30-\uffa0]]+)\"");
	}
	
	public static boolean isQQ(String str) {
		return str.matches("^[1-9]\\d{4,8}$");
	}
	
	public static boolean isMobile(String str) {
		return str.matches("^1[3,5,8]\\d{9}$");
	}
	
	public static boolean isEmail(String str) {
		return str.matches("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");
	}
	
	public static boolean isIp(String str) {
		return str.matches("((1?\\d{1,2})|(2(([0-4]\\d)|(5[0-5])))\\.){3}((1?\\d{1,2})|(2(([0-4]\\d)|(5[0-5]))))");
	}
	
	public static boolean isDate(String str) {
		return str.matches("^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))");
	}
	
	public static boolean isDateTime(String str) {
		return str.matches("^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$");
	}
}
