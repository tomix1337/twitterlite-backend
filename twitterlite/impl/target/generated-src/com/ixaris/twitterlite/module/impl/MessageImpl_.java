package com.ixaris.twitterlite.module.impl;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(MessageImpl.class)
public abstract class MessageImpl_ {

	public static volatile SingularAttribute<MessageImpl, String> content;
	public static volatile SingularAttribute<MessageImpl, String> username;
	public static volatile ListAttribute<MessageImpl, String> hashtags;
	public static volatile SingularAttribute<MessageImpl, Long> messageId;
	public static volatile SingularAttribute<MessageImpl, Long> date;
	public static volatile ListAttribute<MessageImpl, String> mentions;

}

