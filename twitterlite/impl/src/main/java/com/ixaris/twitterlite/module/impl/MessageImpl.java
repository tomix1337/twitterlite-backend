package com.ixaris.twitterlite.module.impl;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.ixaris.twitterlite.module.bd.Message;

@Entity
@Table(name="twit_message")
@NamedQueries({
@NamedQuery(name="messages",query="SELECT msgimpl FROM MessageImpl msgimpl ORDER BY msgimpl.date DESC"),
@NamedQuery(name="messageByUser",query = "SELECT msgimpl FROM MessageImpl msgimpl WHERE msgimpl.username = :username ORDER BY msgimpl.date DESC"),
@NamedQuery(name="messageByMention",query = "SELECT msg FROM MessageImpl msg JOIN msg.mentions mention WHERE mention=:username ORDER BY msg.date DESC"),
@NamedQuery(name="messageByHashtag",query = "SELECT msg FROM MessageImpl msg JOIN msg.hashtags ht WHERE ht IN :htList ORDER BY msg.date DESC"),
@NamedQuery(name="countMessage",query = "SELECT count(*) FROM MessageImpl tm WHERE tm.username=:username")
})
public class MessageImpl implements Message{
	

	@Column(name="username")
	private String username;
	@Column(name="content")
	private String content;
	
	@Column(name="TIMESTAMP")
	private long date;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private long messageId;

	 @ElementCollection(fetch=FetchType.EAGER)
	 @CollectionTable(name="twit_hashtag", joinColumns=@JoinColumn(name="messageId"))
	 @Column(name="hashtag")
	 private List<String> hashtags;
	
	 @ElementCollection(fetch=FetchType.EAGER)
	 @CollectionTable(name="twit_mention", joinColumns=@JoinColumn(name="messageId"))
	 @Column(name="mentionedUsername")
	 private List<String> mentions;
	
	public MessageImpl(){};


	public MessageImpl(String username, String content,
			 List<String> hashtags, List<String> mentions) {
		super();
		this.username = username;
		this.content = content;
		this.date = new Date().getTime()/1000;
		this.hashtags = hashtags;
		this.mentions = mentions;
	}

	@Override
	public String getUsername() {
		return username;
	}
	@Override
	public String getContent() {
		return content;
	}
	@Override
	public long getDate() {
		return date;
	}
	@Override
	public List<String> getHashtags() {
		return hashtags;
	}
	@Override
	public List<String> getMentions() {
		return mentions;
	}

	public long getMessageId() {
		return messageId;
	}

	@Override
    public boolean equals(Object o) {
		MessageImpl other = (MessageImpl)o;
		
		return (this.messageId == other.messageId);
	}

	 @Override
	    public int hashCode() {
	        return (int) this.messageId;
	    }
	
}
