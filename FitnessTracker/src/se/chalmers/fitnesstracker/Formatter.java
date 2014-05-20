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
	public static Double parseDouble(String str){
		return Double.parseDouble(str.replaceAll(",", "."));
	}
	public static String doubleToString(Double d){
		 DecimalFormat twoDForm = new DecimalFormat("#.#");
		 return twoDForm.format(d);
	}
	
	
	
	

}
