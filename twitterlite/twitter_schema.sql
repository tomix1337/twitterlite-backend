DROP TABLE IF EXISTS twit_message;
CREATE TABLE twit_message (
    id BIGINT NOT NULL AUTO_INCREMENT,
	TIMESTAMP BIGINT NOT NULL,
    content VARCHAR(250) NOT NULL,
    username VARCHAR(100) NOT NULL,
    PRIMARY KEY (id),
    KEY IX_twit_message_username (username)
);

DROP TABLE IF EXISTS twit_mention;
CREATE TABLE twit_mention (
    messageId BIGINT NOT NULL AUTO_INCREMENT,
    mentionedUsername VARCHAR(100) NOT NULL,
    PRIMARY KEY (messageId, mentionedUsername),
    KEY IX_twit_mention_mentionedUsername (mentionedUsername),
    CONSTRAINT FK_twit_mention_timestamp FOREIGN KEY (messageId) REFERENCES twit_message(id) -- foreign key to message
);

DROP TABLE IF EXISTS twit_hashtag;
CREATE TABLE twit_hashtag (
    messageId BIGINT NOT NULL AUTO_INCREMENT,
    hashtag VARCHAR(50) NOT NULL,
    PRIMARY KEY (messageId, hashtag),
    KEY IX_twit_hashtag_hashtag (hashtag),
    CONSTRAINT FK_twit_hashtag_timestamp FOREIGN KEY (messageId) REFERENCES twit_message(id) -- foreign key to message
);

