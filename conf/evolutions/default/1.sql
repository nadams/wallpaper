# user schema

# --- !Ups
CREATE TABLE user (
	id INT NOT NULL AUTO_INCREMENT,
	email VARCHAR(64) NOT NULL UNIQUE,
	password CHAR(64) NOT NULL,
	dateCreated DATETIME NOT NULL,

	CONSTRAINT pk_user PRIMARY KEY(id) ,
	CONSTRAINT uq_user_email UNIQUE(email)
) ;

# --- !Downs
DROP TABLE user ;
