package com.remoer.member.controller;

import java.sql.Date;
import java.util.Calendar;

import com.remoer.main.Execute;

public class MemberRestThread implements Runnable {

	@Override
	public void run() {
		while (true) {
			try {
				Calendar cal = Calendar.getInstance();
				cal.add(Calendar.MONTH, -3);
				Date date = new Date(cal.getTimeInMillis());
//				Execute.run(new MemberRestServiceImpl(), date);
				Thread.sleep(1000 * 60 * 5);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
