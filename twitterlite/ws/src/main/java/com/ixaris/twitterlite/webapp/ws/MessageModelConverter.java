package com.ixaris.twitterlite.webapp.ws;

import java.util.ArrayList;
import java.util.List;

import com.ixaris.twitterlite.module.bd.Message;

public class MessageModelConverter {
	
	public List<MessageModel> convertMessagesReturnList(List<Message> msgs){
		List<MessageModel> toReturn = new ArrayList<MessageModel>();
		
		for(Message msg:msgs){
			toReturn.add(new MessageModel(msg.getUsername(),msg.getContent(),msg.getDate(),msg.getHashtags(),msg.getMentions()));
		}
		
		return toReturn;
	}

}
