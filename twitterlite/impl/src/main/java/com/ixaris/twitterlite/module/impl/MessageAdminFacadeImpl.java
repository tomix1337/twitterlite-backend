package com.ixaris.twitterlite.module.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ixaris.twitterlite.module.bd.Message;
import com.ixaris.twitterlite.module.bd.MessagesAdminFacade;

@Service
@Transactional
public class MessageAdminFacadeImpl implements MessagesAdminFacade {

	
	  MessageImpl currentMessage = new MessageImpl();

	
	  private static MessageAdminFacadeImpl instance = null;
	  
	  @PersistenceContext
	  private EntityManager em;
	  
	  private MessageAdminFacadeImpl(){
	  }
	  
	  
	  public static MessageAdminFacadeImpl getInstance() {
	      if(instance == null) {
	         instance = new MessageAdminFacadeImpl();
	      }
	      return instance;
	   }

	@Override
	public boolean addMessage(String username, String content) throws IllegalArgumentException{		
		if(username==null || content==null){
			throw new IllegalArgumentException("nulls");
		}else if(username.length()==0 || content.length()==0){
			throw new IllegalArgumentException("length 0");
		}
		List<String> extractedMentions = extractContentByDelimiter(content,"@");
		List<String> extractedHashtags = extractContentByDelimiter(content,"#");

		
		Message message = new MessageImpl(username,content,extractedHashtags,extractedMentions);
		
		try{
		em.persist(message);
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}

	
	
	@Override
	public Set<Message> lookupMessagesByHashtags(List<String> hashtags, int offset,	int limit)throws IllegalArgumentException {
		Set<Message> result = new HashSet<Message>();
		
		if(hashtags == null || hashtags.size()==0 || offset<0 || limit <1){
			throw new IllegalArgumentException();
		}

		Query q = em.createNamedQuery("messageByHashtag",Message.class).setParameter("htList", hashtags);
		q.setFirstResult(offset);
        q.setMaxResults(limit);
		result = new HashSet(q.getResultList());
		
		return result;
	}

	@Override
	public Set<Message> lookupMessages(int offset, int limit)throws IllegalArgumentException {
		if(offset<0 || limit <=0){
			throw new IllegalArgumentException("offset or limit too small");
		}
		
		HashSet<Message> result = new HashSet<Message>();
			
		
		Query q = em.createNamedQuery("messages",Message.class);
		q.setFirstResult(offset);
        q.setMaxResults(limit);
		result = new HashSet(q.getResultList());
		
		return result;
	}

	@Override
	public long countMessagesByUser(String username)throws IllegalArgumentException {
		if(username==null || username.length()==0){
			throw new IllegalArgumentException();
		}
	
		   Query q = em.createNamedQuery("countMessage", Long.class);
		   q.setParameter("username", username);

		   return (Long)q.getSingleResult();
	}

	@Override
	public List<Message> lookupMessagesByUser(String username, int offset, int limit) {
		List<Message> result;
		
		Query q = em.createNamedQuery("messageByUser",Message.class).setParameter("username", username);
		q.setFirstResult(offset);
        q.setMaxResults(limit);

		result = q.getResultList();
		return result;
	}

	@Override
	public List<Message> lookupMessagesMentioningUser(String username, int offset,	int limit) throws IllegalArgumentException {
		if(username==null || username.length()==0 || limit<1 || offset<0){
			throw new IllegalArgumentException();
		}
		//LinkedList<Message> result = new LinkedList<Message>();
		//result = dbBymentions.get(username);
		
		List<Message> result;
		
		Query q = em.createNamedQuery("messageByMention",Message.class);
		q.setParameter("username", username);
		q.setFirstResult(offset);
        q.setMaxResults(limit);

		result = q.getResultList();
		return result;
	}


	
	public List<String> extractContentByDelimiter(String content,String delimiter){
		//String content = "#boq testing #swag #life sasasdas #tag3";

		String[] hashtagsarray = content.split(delimiter);
		
		if(hashtagsarray!=null){
			ArrayList<String> hashtags = new ArrayList<String>(Arrays.asList(hashtagsarray));;
	
		
			
			if (hashtags.size() > 0) {
				hashtags.remove(0);
			}
			
			for (int i = 0; i < hashtags.size(); i++) {
				String current = hashtags.get(i);
				if (current.contains(" ")) {
					hashtags.set(i, current.split(" ")[0]);
				}
			}
	
			/*for (String hashtag : hashtags) {
				System.out.println(hashtag);
			}*/
			
			return hashtags;
		}else{
			return new ArrayList<String>();
		}
	}
	

}
