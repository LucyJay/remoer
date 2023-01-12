package com.remoer.main;

import java.util.Scanner;

public class In {

	private static Scanner scanner = new Scanner(System.in);

	public static String getStr() {
		return scanner.nextLine();
	}

	public static String getStr(String msg) {
		System.out.print(msg + " ▶ ");
		return scanner.nextLine();
	}

	public static Long getLong() {
		while (true) {
			try {
				return Long.parseLong(scanner.nextLine());
			} catch (Exception e) {
				System.out.println("[안내]정수만 입력 가능합니다. 다시 입력해 주세요.");
			}
		}
	}

	public static Long getLong(String msg) {
		while (true) {
			try {
				System.out.print(msg + " ▶ ");
				return Long.parseLong(scanner.nextLine());
			} catch (Exception e) {
				System.out.println("[안내]정수만 입력 가능합니다. 다시 입력해 주세요.");
			}
		}
	}
	public static int getInt() {
		while (true) {
			try {
				return Integer.parseInt(scanner.nextLine());
			} catch (Exception e) {
				System.out.println("[안내]정수만 입력 가능합니다. 다시 입력해 주세요.");
			}
		}
	}

	public static int getInt(String msg) {
		while (true) {
			try {
				System.out.print(msg + " ▶ ");
				return Integer.parseInt(scanner.nextLine());
			} catch (Exception e) {
				System.out.println("[안내]범위 내의 정수만 입력 가능합니다. 다시 입력해 주세요.");
			}
		}
	}
}
