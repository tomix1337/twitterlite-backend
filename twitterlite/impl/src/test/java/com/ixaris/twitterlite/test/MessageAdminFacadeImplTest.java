package com.ixaris.twitterlite.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import static org.mockito.Mockito.*;

import com.ixaris.twitterlite.module.bd.Message;
import com.ixaris.twitterlite.module.impl.MessageAdminFacadeImpl;
import com.ixaris.twitterlite.module.impl.MessageImpl;

public class MessageAdminFacadeImplTest {

	@Autowired
	static MessageAdminFacadeImpl messagesAdminFacadeImpl;
	@Mock
	EntityManager em;
	@Rule
	public ExpectedException exception = ExpectedException.none();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		messagesAdminFacadeImpl = MessageAdminFacadeImpl.getInstance();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		messagesAdminFacadeImpl = CommonsTestUtils.initSingletonClassWithMocks(
				this, MessageAdminFacadeImpl.class);
	}

	@After
	public void tearDown() throws Exception {
	}

	// Tests
	// addMessage
	@Test
	public void testAddMessageSuccess() {
		assertTrue(messagesAdminFacadeImpl.addMessage("asda", "ddasda"));
	}

	@Test
	public void testAddMessageSuccess2() {
		String username = "Andrew";
		String content = "Hello @Francesco , #banoffee #pie";

		List<String> mentions = new ArrayList<String>();
		mentions.add("Francesco");
		List<String> hashtags = new ArrayList<String>();
		hashtags.add("banoffee");
		hashtags.add("pie");

		messagesAdminFacadeImpl.addMessage(username, content);

		ArgumentCaptor<MessageImpl> argument = ArgumentCaptor
				.forClass(MessageImpl.class);
		Mockito.verify(em).persist(argument.capture());

		Assert.assertEquals(username, argument.getValue().getUsername());
		Assert.assertEquals(content, argument.getValue().getContent());
		Assert.assertEquals(mentions, argument.getValue().getMentions());
		Assert.assertEquals(hashtags, argument.getValue().getHashtags());
	}

	@Test
	public void testAddMessageNulls() {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("nulls");
		messagesAdminFacadeImpl.addMessage(null, null);
	}

	@Test
	public void testAddMessageNull2() {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("nulls");
		messagesAdminFacadeImpl.addMessage(null, "ss");
	}

	@Test
	public void testAddMessageNull3() {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("nulls");
		messagesAdminFacadeImpl.addMessage("ss", null);
	}

	@Test
	public void testAddMessageES() {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("length 0");
		messagesAdminFacadeImpl.addMessage("", "");
	}

	@Test
	public void testAddMessageES3() {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("length 0");
		messagesAdminFacadeImpl.addMessage("aaa", "");
	}

	@Test
	public void testAddMessageES4() {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("length 0");
		messagesAdminFacadeImpl.addMessage("", "aaa");
	}

	@Test
	public void testAddMessageDbFail() {
		exception.expect(Exception.class);

		messagesAdminFacadeImpl.addMessage("ss", "aaa");

		ArgumentCaptor<MessageImpl> argument = ArgumentCaptor
				.forClass(MessageImpl.class);
		Mockito.verify(em).persist(argument.capture());

		doThrow(new Exception()).when(em).persist(argument);

		assertEquals(false, messagesAdminFacadeImpl.addMessage("ss", "aaa"));
	}

	// lookupMessages
	@Test
	public void testLookUpMessageSuccess() {

		TypedQuery<Message> query = Mockito.mock(TypedQuery.class);

		when(em.createNamedQuery("messages", Message.class)).thenReturn(query);
		Mockito.when(query.getResultList())
				.thenReturn(new ArrayList<Message>());

		List<Message> s = messagesAdminFacadeImpl.lookupMessages(2, 2);

		assertTrue(s != null && s.isEmpty());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testLookupMessageZero() {
		List<Message> s = messagesAdminFacadeImpl.lookupMessages(0, 0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testLookupMessageZero2() {
		List<Message> s = messagesAdminFacadeImpl.lookupMessages(5, 0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testLookupMessageNegative() {
		List<Message> s = messagesAdminFacadeImpl.lookupMessages(-5, 5);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testLookupMessageNegative2() {
		List<Message> s = messagesAdminFacadeImpl.lookupMessages(5, -5);
	}

	// Count Message
	@Test
	public void testCountMessageCorrectUsername() {
		TypedQuery<Long> query = Mockito.mock(TypedQuery.class);
		when(em.createNamedQuery("countMessage", Long.class)).thenReturn(query);
		Mockito.when(query.getSingleResult()).thenReturn(1l);

		long ans = messagesAdminFacadeImpl.countMessagesByUser("test");

		assertEquals(ans, 1l);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCountMessageNullUsername() {
		messagesAdminFacadeImpl.countMessagesByUser(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCountMessageEmptyString() {
		messagesAdminFacadeImpl.countMessagesByUser("");
	}

	// lookupMessagesByUser
	@Test
	public void testLookUpByMentioningUserOK() {
		TypedQuery<Message> query = Mockito.mock(TypedQuery.class);
		when(em.createNamedQuery("messageByMention", Message.class))
				.thenReturn(query);
		Mockito.when(query.getResultList())
				.thenReturn(new ArrayList<Message>());

		List<Message> ans = messagesAdminFacadeImpl
				.lookupMessagesMentioningUser("messageByMention", 0, 20);

		assertTrue(ans != null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testLookUpByMentioningUserNull() {
		messagesAdminFacadeImpl.lookupMessagesMentioningUser(null, 0, 20);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testLookUpByMentioningUserEs() {
		messagesAdminFacadeImpl.lookupMessagesMentioningUser("", 0, 20);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testLookUpByMentioningUserZero() {
		messagesAdminFacadeImpl.lookupMessagesMentioningUser("ass", 0, 0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testLookUpByMentioningUserNegative() {
		messagesAdminFacadeImpl.lookupMessagesMentioningUser("ass", -1, 5);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testLookUpByMentioningUserNegative2() {
		messagesAdminFacadeImpl.lookupMessagesMentioningUser("ass", 1, -5);
	}

	// lookup by hashtags
	public void testLookUpByHashtagsOK() {
		TypedQuery<Message> query = Mockito.mock(TypedQuery.class);
		when(em.createNamedQuery("messageByHashtag", Message.class))
				.thenReturn(query);
		Mockito.when(query.getResultList())
				.thenReturn(new ArrayList<Message>());

		ArrayList<String> list = new ArrayList<String>();
		list.add("messageByMention");

		List<Message> ans = messagesAdminFacadeImpl.lookupMessagesByHashtags(list, 0, 20);

		assertTrue(ans != null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testLookUpByHashTagNull() {
		messagesAdminFacadeImpl.lookupMessagesByHashtags(null, 0, 20);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testLookUpByHashTagEs() {
		messagesAdminFacadeImpl.lookupMessagesByHashtags(
				new ArrayList<String>(), 0, 20);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testLookUpByHashTagZero() {
		ArrayList<String> list = new ArrayList<String>();
		list.add("messageByMention");

		messagesAdminFacadeImpl.lookupMessagesByHashtags(list, 0, 0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testLookUpByHashTagNegative() {
		ArrayList<String> list = new ArrayList<String>();
		list.add("messageByMention");

		messagesAdminFacadeImpl.lookupMessagesByHashtags(list, -1, 5);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testLookUpByHashTagNegative2() {
		ArrayList<String> list = new ArrayList<String>();
		list.add("messageByMention");

		messagesAdminFacadeImpl.lookupMessagesByHashtags(list, 1, -5);
	}

	// parsing tests
	@Test
	public void testHashParsingOk() {
		List<String> s = messagesAdminFacadeImpl.extractContentByDelimiter(
				"#thomas huwa il-#king ! #test2", "#");
		List<String> expected = Arrays.asList("thomas", "king", "test2");
		assertEquals(s, expected);
	}

	@Test
	public void testHashParsingNotOk() {
		List<String> s = messagesAdminFacadeImpl.extractContentByDelimiter(
				"#thomas huwa il-#king ! #test2", "#");
		List<String> expected = Arrays.asList("thomas", "test2");
		assertNotEquals(s, expected);
	}

	@Test
	public void testHashMentionsOk() {
		List<String> s = messagesAdminFacadeImpl.extractContentByDelimiter(
				"@thomas huwa il-#king ! #test2", "@");
		List<String> expected = Arrays.asList("thomas");
		assertEquals(s, expected);
	}

}
