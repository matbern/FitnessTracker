package se.chalmers.fitnesstracker;

import java.text.DecimalFormat;

public class Formatter {
	public static String padStringWithZero(int value) {
		if (value < 10) {
			return "0" + value;
		} else {
			return "" + value;
		}
	}

	public static Double parseDouble(String str) {
		return Double.parseDouble(str.replaceAll(",", "."));
	}

	public static String doubleToString(Double d) {
		DecimalFormat twoDForm = new DecimalFormat("#.#");
		return twoDForm.format(d);
	}

	public static String makeDateString(int y, int m, int d) {
		StringBuffer sb = new StringBuffer();
		String ye = "" + y;
		sb.append(ye + "-");
		if (m < 10) {
			String mo = "0" + m;
			sb.append(mo + "-");
		} else {
			sb.append(m + "-");
		}
		if (d < 10) {
			String dat = "0" + d;
			sb.append(dat);
		} else {
			sb.append(d);
		}
		return sb.toString();
	}

}
