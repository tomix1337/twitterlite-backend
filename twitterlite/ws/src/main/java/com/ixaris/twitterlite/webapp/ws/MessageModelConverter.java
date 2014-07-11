package com.ixaris.twitterlite.webapp.ws;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.ixaris.twitterlite.module.bd.Message;

public class MessageModelConverter {
	
	public Set<MessageModel> convertMessagesReturnSet(Set<Message> msgs){
		Set<MessageModel> toReturn = new HashSet<MessageModel>();
		
		for(Message msg:msgs){
			toReturn.add(new MessageModel(msg.getUsername(),msg.getContent(),msg.getDate(),msg.getHashtags(),msg.getMentions()));
		}
		
		return toReturn;
	}

}
