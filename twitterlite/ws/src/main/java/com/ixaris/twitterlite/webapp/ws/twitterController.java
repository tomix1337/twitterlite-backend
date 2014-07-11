package com.ixaris.twitterlite.webapp.ws;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.ixaris.twitterlite.module.bd.Message;
import com.ixaris.twitterlite.module.bd.MessagesAdminFacade;

@Controller
public class twitterController {
	
	@Autowired
	MessagesAdminFacade twitterlite;
	
	MessageModelConverter converter = new MessageModelConverter();
	
	@RequestMapping(value = "/tweets", method = RequestMethod.POST)
	 public @ResponseBody ResponseEntity addMessage(@RequestParam(value = "username") String username,
			 									 @RequestParam(value = "content") String content) {
	  
	   twitterlite.addMessage(username, content);
	    
	   return new ResponseEntity<HttpStatus>(HttpStatus.OK);
	 }
	
	@RequestMapping(value = "/count", method = RequestMethod.GET)
	@ResponseBody
	 public long count(@RequestParam(value = "username") String username) {
	  
	   System.out.println(twitterlite.countMessagesByUser(username));
	   
	   return twitterlite.countMessagesByUser(username);
	    
	  // return new ResponseEntity<HttpStatus>(HttpStatus.OK);
	 }
	
	@RequestMapping(value = "/messages", method = RequestMethod.GET)
	@ResponseBody
	   public Set<MessageModel> getMessages(@RequestParam(value = "offset", required = false) Integer offset,
			   						   @RequestParam(value = "limit", required = false) Integer limit){
			if(offset == null){
				offset = 0;
			}
			if(limit == null){
				limit = 1;
			}
			
			Set<Message> msgs = twitterlite.lookupMessages(offset, limit);
			return converter.convertMessagesReturnSet(msgs);
	   }
	
	@RequestMapping(value = "/messages/hashtags", method = RequestMethod.GET)
	@ResponseBody
	   public Set<MessageModel> getMessagesWithHashTag(@RequestParam List<String> hashtags, 
			   @RequestParam(value = "offset", required = false)  Integer offset,
			   @RequestParam(value = "limit", required = false)  Integer limit){
		
			if(offset == null){
				offset = 0;
			}
			if(limit == null){
				limit = 20;
			}
			
			Set<Message> msgs = new HashSet<Message>(twitterlite.lookupMessagesByHashtags(hashtags, offset, limit));
		
			return converter.convertMessagesReturnSet(msgs);
	   }

		@RequestMapping(value = "/messages/mention", method = RequestMethod.GET)
		@ResponseBody
		public LinkedList<Message> getMessagesWithMention(@RequestParam String mention,
														  @RequestParam(value = "offset", required = false)  Integer offset,
														  @RequestParam(value = "limit", required = false)  Integer limit) {
			
			if(offset == null){
				offset = 0;
			}
			if(limit == null){
				limit = 20;
			}
		
			LinkedList<Message> msgs = new LinkedList<Message>(twitterlite.lookupMessagesMentioningUser(mention, offset, limit));

			return msgs;
		}
	
		@RequestMapping(value = "/messages/user", method = RequestMethod.GET)
		@ResponseBody
		public LinkedList<Message> getMessagesWithUser(@RequestParam String user,
												       @RequestParam(value = "offset", required = false)  Integer offset,
													   @RequestParam(value = "limit", required = false)  Integer limit) {
			
			if(offset == null){
				offset = 0;
			}
			if(limit == null){
				limit = 1;
			}

			LinkedList<Message> msgs =  new LinkedList<Message>(twitterlite.lookupMessagesByUser(user, offset,limit));

		//	return null;
			return msgs;
		}
}
