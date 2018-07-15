package com.sinoservices.codehelper.util;

public class CharacterUtils {
	public static String replaceUnderlineAndfirstToUpper(String oldStr) {
		String newStr = "";
		oldStr = oldStr.toLowerCase();
		int first = 0;
		while (oldStr.indexOf("_") != -1) {
			first = oldStr.indexOf("_");
			if (first != oldStr.length()) {
				newStr = newStr + oldStr.substring(0, first);
				oldStr = oldStr
						.substring(first + "_".length(), oldStr.length());
				oldStr = oldStr.substring(0, 1).toUpperCase()
						+ oldStr.substring(1);
			}
		}
		newStr = newStr + oldStr;
		newStr = newStr.replaceFirst(newStr.substring(0, 1),
				newStr.substring(0, 1).toUpperCase());
		return newStr;
	}

	public static String replaceUnderlineAndfirstToLower(String oldStr) {
		String newStr = "";
		oldStr = oldStr.toLowerCase();
		int first = 0;
		while (oldStr.indexOf("_") != -1) {
			first = oldStr.indexOf("_");
			if (first != oldStr.length()) {
				newStr = newStr + oldStr.substring(0, first);
				oldStr = oldStr
						.substring(first + "_".length(), oldStr.length());
				oldStr = oldStr.substring(0, 1).toUpperCase()
						+ oldStr.substring(1);
			}
		}
		newStr = newStr + oldStr;
		return newStr;
	}

	public static String replaceUnderline(String oldStr) {
		if (!"".equals(oldStr)) {
			oldStr = oldStr.toLowerCase();
			oldStr = oldStr.replaceAll("_", "");
			if (Character.isUpperCase(oldStr.charAt(0))) {
				return oldStr;
			}
			return Character.toUpperCase(oldStr.charAt(0))
					+ oldStr.substring(1);
		}
		return "";
	}

	public static String replaceUnderlineAndToLower(String oldStr) {
		oldStr = oldStr.toLowerCase();
		oldStr = oldStr.replaceAll("_", "");
		return oldStr;
	}
}
