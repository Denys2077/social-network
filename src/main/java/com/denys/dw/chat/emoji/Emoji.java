package com.denys.dw.chat.emoji;

import java.util.Objects;

public class Emoji {

	private long id;
	private String emoji_dec;
	
	public Emoji() {}
	
	public Emoji(String emoji_dec) {
		super();
		this.emoji_dec = emoji_dec;
	}
	
	public Emoji(long id, String emoji_dec) {
		super();
		this.id = id;
		this.emoji_dec = emoji_dec;
	}

	public long getId() {
		return id;
	}

	public String getEmoji_dec() {
		return emoji_dec;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setEmoji_dec(String emoji_dec) {
		this.emoji_dec = emoji_dec;
	}

	@Override
	public int hashCode() {
		return Objects.hash(emoji_dec, id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Emoji other = (Emoji) obj;
		return Objects.equals(emoji_dec, other.emoji_dec) && id == other.id;
	}

	@Override
	public String toString() {
		return "Emoji [id=" + id + ", emoji_dec=" + emoji_dec + "]";
	}
	
	
}
