package com.ixaris.twitterlite.module.bd;

import java.util.Date;
import java.util.List;


public interface Message{
	
	public String getUsername();
	public String getContent();
	public long getDate();
	public List<String> getHashtags();
	public List<String> getMentions();
	public long getMessageId();
	
}
