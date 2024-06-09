package com.denys.dw.user.addition;

public class ContactInfo {
	private String email;
	private String telephone;
	private String position;
	private String department;
	private long user_id;
	
	public String getEmail() {
		return email;
	}
	
	public String getTelephone() {
		return telephone;
	}
	
	public String getPosition() {
		return position;
	}
	
	public String getDepartment() {
		return department;
	}
	
	public long getUserId() {
		return user_id;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	
	public void setPosition(String position) {
		this.position = position;
	}
	
	public void setDepartment(String department) {
		this.department = department;
	}	
	
	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(200);
		sb.append("email address: ").append(email);
		sb.append(", telephone: ").append(telephone);
		sb.append(", position: ").append(position);
		sb.append(", department: ").append(department);
		return sb.toString();
	}
}
