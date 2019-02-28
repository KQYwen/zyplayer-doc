package com.zyplayer.doc.test;

/**
 * 生成6位字符的ID
 *
 * @author 暮光：城中城
 * @since 2019年2月17日
 */
public class CreateIdTest {
	private static String convertStr = "";
	private static String[] convertArr = {"e", "6", "1", "5", "r", "Q", "W", "j", "Y", "d", "D", "m", "a", "p", "B", "C", "l", "8", "E", "t", "h", "s", "S", "u", "M", "R", "z", "g", "H", "X", "y", "L", "T", "7", "K", "I", "O", "4", "k", "o", "b", "P", "i", "U", "n", "v", "f", "3", "Z", "9", "c", "G", "w", "A", "N", "0", "x", "q", "J", "V", "F", "2"};
	// 可到这个网站把顺序打乱，就成一个新的了，http://www.fuhaoku.com/tool/daluan.html
	
	public static void main(String[] args) {
		long baseNum = 916132832L + 100000000;
		convertStr = String.join("", convertArr);
		// 1  6位数
		String to62 = convert10To62(1L);
		System.out.println((baseNum + 1L) + "：" + to62);
		System.out.println(to62 + "：" + convert62To10(to62));
		// 555亿  6位数
		to62 = convert10To62(55555555555L);
		System.out.println((baseNum + 55555555555L) + "：" + to62);
		System.out.println(to62 + "：" + convert62To10(to62));
		// 5555亿  7位数
		to62 = convert10To62(555555555555L);
		System.out.println((baseNum + 555555555555L) + "：" + to62);
		System.out.println(to62 + "：" + convert62To10(to62));
	}
	
	private static String convert10To62(Long num) {
		long base = 916132832L + 100000000 + num;
		StringBuilder sb = new StringBuilder();
		while (base != 0) {
			sb.append(convertArr[(int) (base % 62)]);
			base = base / 62;
		}
		return sb.reverse().toString();
	}
	
	private static long convert62To10(String number) {
		char[] charArr = number.toCharArray();
		int len = charArr.length;
		long result = 0;
		long base = 1;
		for (int i = len - 1; i >= 0; i--) {
			int index = convertStr.indexOf(charArr[i]);
			result += index * base;
			base *= 62;
		}
		return result;
	}
	
}
