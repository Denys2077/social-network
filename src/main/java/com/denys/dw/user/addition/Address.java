package com.denys.dw.user.addition;

public class Address {
	
	private String country;
	private String city;
	private String street;
	private long user_id;
	
	public String getCountry() {
		return country;
	}
	public String getCity() {
		return city;
	}
	public String getStreet() {
		return street;
	}
	public long getUser_id() {
		return user_id;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public void setUser_id(long id) {
		this.user_id = id;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(200);
		sb.append("country: ").append(country);
		sb.append(", city: ").append(city);
		sb.append(", street: ").append(street);
		return sb.toString();
	}
}
