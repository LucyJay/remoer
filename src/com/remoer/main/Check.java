package com.remoer.main;

public class Check {

	public static boolean isAllKor(String str) {
		for (int i = 0; i < str.length(); i++) {
			char ch = str.charAt(i);
			if (!('가' <= ch && ch <= '힣')) {
				return false;
			}
		}
		return true;
	}

}
