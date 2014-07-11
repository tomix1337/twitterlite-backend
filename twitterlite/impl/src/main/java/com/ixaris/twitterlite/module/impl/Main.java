package com.ixaris.twitterlite.module.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Set;

import com.ixaris.twitterlite.module.bd.Message;

public class Main {

	public static void main(String args[]) {
		MessageAdminFacadeImpl twitterImpl = MessageAdminFacadeImpl.getInstance();
		
		twitterImpl.addMessage("thomas", "testing message 1 #tag1 @user1");
		twitterImpl.addMessage("thomas", "message #tag1 #tag2 @thomas");
		twitterImpl.addMessage("ric", "message 3 #tag2 #rica message haha @user2");
		ArrayList al = new ArrayList();
		//al.add("tag2");
		//al.add("rica");
		//LinkedList<Message> s = twitterImpl.lookupMessagesMentioningUser("user2",0, 2);
		//Set<Message> s = twitterImpl.lookupMessages(0, 3);
		
		//for(Message m : s){
		//	System.out.println(m);
		//}
	}
}
