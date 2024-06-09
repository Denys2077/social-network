package com.denys.dw.user.addition;

public class SocialMedia {
	private String linkedInURL;
	private String instagramURL;
	private String facebookURL;
	private String telegramURL;
	private long user_id;
	
	public String getLinkedInURL() {
		return linkedInURL;
	}
	
	public String getInstagramURL() {
		return instagramURL;
	}
	
	public String getFacebookURL() {
		return facebookURL;
	}
	
	public String getTelegramURL() {
		return telegramURL;
	}
	
	public long getUser_id() {
		return user_id;
	}
	
	public void setLinkedInURL(String linkedInURL) {
		this.linkedInURL = linkedInURL;
	}
	
	public void setInstagramURL(String instagramURL) {
		this.instagramURL = instagramURL;
	}
	
	public void setFacebookURL(String facebookURL) {
		this.facebookURL = facebookURL;
	}
	
	
	public void setTelegramURL(String telegramURL) {
		this.telegramURL = telegramURL;
	}
	
	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(200);
		sb.append("LinkedIn URL: ").append(linkedInURL);
		sb.append(", Instagram URL: ").append(instagramURL);
		sb.append(", Facebook URL: ").append(facebookURL);
		sb.append(", Telegram URL: ").append(telegramURL);
		return sb.toString();
	}
}
