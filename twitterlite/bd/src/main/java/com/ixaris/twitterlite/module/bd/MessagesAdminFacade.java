package com.ixaris.twitterlite.module.bd;

import java.util.List;

public interface MessagesAdminFacade {

	public boolean addMessage(String username, String content);

	public List<Message> lookupMessagesByHashtags(List<String> hashtags, int offset, int limit);
	
	public List<Message> lookupMessages(int offset, int limit);

	public long countMessagesByUser(String username);
	
	public List<Message> lookupMessagesByUser(String username,int offset, int limit);
	
	public List<Message> lookupMessagesMentioningUser(String username,int offset, int limit);

}
