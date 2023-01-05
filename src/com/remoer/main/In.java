package com.remoer.main;

import java.util.Scanner;

public class In {

	private static Scanner scanner = new Scanner(System.in);

	public static String inStr() {
		return scanner.nextLine();
	}

	public static String inStr(String msg) {
		System.out.print(msg + " ▶ ");
		return scanner.nextLine();
	}

	public static Long inLong() {
		while (true) {
			try {
				return Long.parseLong(scanner.nextLine());
			} catch (Exception e) {
				System.out.println("[안내]정수만 입력 가능합니다. 다시 입력해 주세요.");
			}
		}
	}

	public static Long inLong(String msg) {
		while (true) {
			try {
				System.out.print(msg + " ▶ ");
				return Long.parseLong(scanner.nextLine());
			} catch (Exception e) {
				System.out.println("[안내]정수만 입력 가능합니다. 다시 입력해 주세요.");
			}
		}
	}

}
