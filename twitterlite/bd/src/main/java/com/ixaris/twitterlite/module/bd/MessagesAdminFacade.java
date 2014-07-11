package com.ixaris.twitterlite.module.bd;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public interface MessagesAdminFacade {

	public boolean addMessage(String username, String content);

	public Set<Message> lookupMessagesByHashtags(List<String> hashtags, int offset, int limit);
	
	public Set<Message> lookupMessages(int offset, int limit);

	public long countMessagesByUser(String username);
	
	public List<Message> lookupMessagesByUser(String username,int offset, int limit);
	
	public List<Message> lookupMessagesMentioningUser(String username,int offset, int limit);

}
