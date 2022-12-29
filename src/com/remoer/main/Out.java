package com.remoer.main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Out {

	public static void sys(String msg) {
		System.out.println(" ▷ " + msg);
	}

	public static void sysln(String msg) {
		sys(msg + "\n\n");
	}

	public static void line(String ch, int cnt) {
		for (int i = 1; i <= cnt; i++)
			System.out.print(ch);
	}

	public static void lineln(String ch, int cnt) {
		line(ch, cnt);
		System.out.println();
	}

	public static void err(String... msgs) {
		lineln("#", 40);
		for (String msg : msgs)
			sys(msg);
		lineln("#", 40);
		System.out.println();
		System.out.println();
	}

	public static void title(String title) {
		System.out.println();
		System.out.println();
		line("=", (40 - title.length()) / 2);
		System.out.print(" [" + title + "] ");
		lineln("=", (40 - title.length()) / 2);
	}

	public static void titleMini(String title, int num) {
		line("-", (num - title.length()) / 2);
		System.out.print(" << " + title + " >> ");
		lineln("-", (num - title.length()) / 2);
	}

	public static void lnTitleMini(String title, int num) {
		System.out.println();
		titleMini(title, num);
	}

	public static void menu(String ttl, int num, String zero, String[][] menus) {
		List<String> menuList = new ArrayList<>(Arrays.asList(menus[0]));
		if (Main.login != null) {
			menuList.addAll(Arrays.asList(menus[1]));
			if (Main.isAdmin())
				menuList.addAll(Arrays.asList(menus[2]));
		}
		title("레시피로 모인 사람들 REMOER");
		titleMini(ttl, 36);
		if (Main.login != null)
			System.out.println("[" + Main.login.getGrade_name() + "] " + Main.login.getName() + "(" + Main.login.getId()
					+ ")님 반갑습니다.");
		for (int i = 0, j = 1; i <= menuList.size(); i++) {
			if (i == menuList.size()) {
				System.out.println(" 0. " + zero);
			} else {
				System.out.print(" " + j + ". " + menuList.get(i) + " ");
				if (j++ % num == 0)
					System.out.println();
			}
		}
		lineln("=", 48);
	}

}
