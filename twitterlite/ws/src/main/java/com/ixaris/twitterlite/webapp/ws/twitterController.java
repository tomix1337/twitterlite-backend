package com.ixaris.twitterlite.webapp.ws;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ixaris.twitterlite.module.bd.MessagesAdminFacade;

@Controller
public class twitterController {

	@Autowired
	MessagesAdminFacade twitterlite;

	MessageModelConverter converter = new MessageModelConverter();

	@RequestMapping(value = "/tweets", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<HttpStatus> addMessage(
			@RequestParam(value = "username") String username,
			@RequestParam(value = "content") String content) {

		twitterlite.addMessage(username, content);

		return new ResponseEntity<HttpStatus>(HttpStatus.OK);
	}

	@RequestMapping(value = "/count", method = RequestMethod.GET)
	@ResponseBody
	public long count(@RequestParam(value = "username") String username) {

		System.out.println(twitterlite.countMessagesByUser(username));
		return twitterlite.countMessagesByUser(username);
	}

	@RequestMapping(value = "/messages", method = RequestMethod.GET)
	@ResponseBody
	public List<MessageModel> getMessages(
			@RequestParam(value = "offset", required = false) Integer offset,
			@RequestParam(value = "limit", required = false) Integer limit) {

		if (offset == null) {
			offset = 0;
		}
		if (limit == null) {
			limit = 1;
		}

		return converter.convertMessagesReturnList(twitterlite.lookupMessages(
				offset, limit));
	}

	@RequestMapping(value = "/messages/hashtags", method = RequestMethod.GET)
	@ResponseBody
	public List<MessageModel> getMessagesWithHashTag(
			@RequestParam List<String> hashtags,
			@RequestParam(value = "offset", required = false) Integer offset,
			@RequestParam(value = "limit", required = false) Integer limit) {

		if (offset == null) {
			offset = 0;
		}
		if (limit == null) {
			limit = 20;
		}

		return converter.convertMessagesReturnList(twitterlite.lookupMessagesByHashtags(hashtags, offset, limit));
	}

	@RequestMapping(value = "/messages/mention", method = RequestMethod.GET)
	@ResponseBody
	public List<MessageModel> getMessagesWithMention(
			@RequestParam String mention,
			@RequestParam(value = "offset", required = false) Integer offset,
			@RequestParam(value = "limit", required = false) Integer limit) {

		if (offset == null) {
			offset = 0;
		}
		if (limit == null) {
			limit = 20;
		}

		return converter.convertMessagesReturnList(twitterlite.lookupMessagesMentioningUser(mention, offset, limit));
	}

	@RequestMapping(value = "/messages/user", method = RequestMethod.GET)
	@ResponseBody
	public List<MessageModel> getMessagesWithUser(@RequestParam String user,
			@RequestParam(value = "offset", required = false) Integer offset,
			@RequestParam(value = "limit", required = false) Integer limit) {

		if (offset == null) {
			offset = 0;
		}
		if (limit == null) {
			limit = 1;
		}

		return converter.convertMessagesReturnList(twitterlite.lookupMessagesByUser(user, offset, limit));
	}
}
