package com.remoer.main;

import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

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
	public static boolean isNum(char ch) {
		return '0' <= ch && ch <= '9';
	}

	public static boolean isEng(char ch) {
		return ('a' <= ch && ch <= 'z') || ('A' <= ch && ch <= 'Z');
	}

	public static boolean isPossibleSpecialChar(char ch) {
		return ch == '!' || ch == '@' || ch == '*';
	}

	public static boolean isKor(char ch) {
		return '가' <= ch && ch <= '힣';
	}

	public static boolean hasNum(String str) {
		for (int i = 0; i < str.length(); i++) {
			if (isNum(str.charAt(i))) {
				return true;
			}
		}
		return false;
	}

	public static boolean hasEng(String str) {
		for (int i = 0; i < str.length(); i++) {
			if (isEng(str.charAt(i))) {
				return true;
			}
		}
		return false;
	}

	public static boolean hasNotOnlyKorEng(String str) {
		for (int i = 0; i < str.length(); i++) {
			char ch = str.charAt(i);
			if (!isKor(ch) && !isEng(ch)) {
				return true;
			}
		}
		return false;
	}

	public static boolean hasNotOnlyEngNum(String str) {
		for (int i = 0; i < str.length(); i++) {
			char ch = str.charAt(i);
			if (!isEng(ch) && !isNum(ch)) {
				return true;
			}
		}
		return false;
	}

	public static boolean hasImpossibleChar(String str) {
		for (int i = 0; i < str.length(); i++) {
			char ch = str.charAt(i);
			if (!isEng(ch) && !isNum(ch) && !isPossibleSpecialChar(ch)) {
				return true;
			}
		}
		return false;
	}

	public static boolean hasSpecialChar(String str) {
		for (int i = 0; i < str.length(); i++) {
			if (isPossibleSpecialChar(str.charAt(i))) {
				return true;
			}
		}
		return false;
	}

	public static boolean checkLength(String str, int sh, int l) {
		return sh <= str.length() && str.length() <= l;
	}

	public static boolean checkId(String id) {
		if (!checkLength(id, 4, 20)) {
			Out.sys("아이디는 4자 이상 20자 이하로 입력하셔야 합니다.");
			return false;
		} else if (!isEng(id.charAt(0))) {
			Out.sys("아이디는 영문 대소문자로 시작해야 합니다.");
			return false;
		} else if (!hasNum(id)) {
			Out.sys("아이디는 숫자를 포함해야 합니다.");
			return false;
		} else if (hasNotOnlyEngNum(id)) {
			Out.sys("아이디는 영문 또는 숫자만 입력 가능합니다.");
			return false;
		} else
			return true;
	}

	public static String newPw(boolean isJoin) {
		while (true) {
			String newPw = In.getStr(isJoin ? "비밀번호" : "새로운 비밀번호를 입력해 주세요");
			if (!Check.checkPw(newPw)) {
			} else {
				if (In.getStr("비밀번호 확인").equals(newPw)) {
					return newPw;
				} else {
					Out.sys("비밀번호가 일치하지 않습니다.");
				}
			}
		}
	}

	public static boolean checkPw(String pw) {
		if (!checkLength(pw, 4, 20)) {
			Out.sys("비밀번호는 4자 이상 20자 이하로 입력하셔야 합니다.");
			return false;
		} else if (!hasNum(pw) || !hasEng(pw)) {
			Out.sys("비밀번호는 영문과 숫자를 모두 포함해야 합니다.(특수문자는 ! @ * 사용 가능)");
			return false;
		} else if (hasImpossibleChar(pw)) {
			Out.sys("특수문자는 ! @ * 만 사용할 수 있습니다.");
			return false;
		} else
			return true;
	}

	public static boolean checkName(String name) {
		if (!checkLength(name, 2, 10)) {
			Out.sys("이름은 2자 이상 10자 이하로 입력하셔야 합니다.");
			return false;
		} else if (hasNotOnlyKorEng(name)) {
			Out.sys("이름은 한글 또는 영문만 입력할 수 있습니다.");
			return false;
		} else
			return true;
	}

	public static boolean checkDate(String date) {
		try {
			DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			sdf.parse(date);
			return true;
		} catch (Exception e) {
			Out.sys("날짜 형식(yyyy-mm-dd)에 맞게 입력해 주세요.");
			return false;
		}
	}

	public static String checkAge() {
		while (true) {
			String birth = In.getStr("생년월일(yyyy-mm-dd)");
			if (checkDate(birth)) {
				DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				try {
					Date date = sdf.parse(birth);
					Calendar birthCal = Calendar.getInstance();
					birthCal.setTime(date);
					Calendar rule = Calendar.getInstance();
					rule.add(Calendar.YEAR, -14);
					if (rule.getTimeInMillis() - birthCal.getTimeInMillis() >= 0) {
						return birth;
					} else
						return null;
				} catch (ParseException e) {
				}

			}
		}
	}
}
