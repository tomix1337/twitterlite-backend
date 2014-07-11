package com.ixaris.twitterlite.webapp.ws;

import java.util.Date;
import java.util.List;

public class MessageModel {
	
	private String username;
	private String content;
	private long date;
	private List<String> hashtags;
	private List<String> mentions;
	
	
	public MessageModel(String username, String content, long date,
			List<String> hashtags, List<String> mentions) {
		super();
		this.username = username;
		this.content = content;
		this.date = date;
		this.hashtags = hashtags;
		this.mentions = mentions;
	}
	public String getUsername() {
		return username;
	}

	public String getContent() {
		return content;
	}
	public long getDate() {
		return date;
	}
	public List<String> getHashtags() {
		return hashtags;
	}
	public List<String> getMentions() {
		return mentions;
	}

}
