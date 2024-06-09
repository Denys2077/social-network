package com.denys.dw.user.addition;

import java.time.LocalDate;

public class Birth {

	private Sex sex;
	private LocalDate date;
	private long user_id;
	
	public Sex getSex() {
		return sex;
	}
	public LocalDate getDate() {
		return date;
	}
	public long getUser_id() {
		return user_id;
	}
	public void setSex(Sex sex) {
		this.sex = sex;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}	
	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(200);
		sb.append("sex: ").append(sex);
		sb.append(", birth day: ").append(date);
		return sb.toString();
	}
}
