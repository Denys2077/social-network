package com.denys.dw.sax.read;

public enum Element {

	ID("id"),
	USERNAME("username"),
	FIRSTNAME("firstname"),
	LASTNAME("lastname"),
	PASSWORD("password"),
	ADMIN("admin"),
	CONTACTINFO("contactInfo"),
	EMAIL("email"),
	TELEPHONE("telephone"),
	POSITION("position"),
	DEPARTMENT("department"),
	ADDRESS("address"),
	COUNTRY("country"),
	CITY("city"),
	STREET("street"),
	BIRTH("birth"),
	SEX("sex"),
	DATE("date"),
	SOCIAL_MEDIA("socialMedia"),
	LINKEDINURL("linkedInURL"),
	INSTAGRAMURL("instagramURL"),
	FACEBOOKURL("facebookURL"),
	TELEGRAMURL("telegramURL");
	
	private String element;
	
	private Element(String element) {
		this.element = element;
	}
	
	public String getElement() {
		return element;
	}
}
