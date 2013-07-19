# wallpaper schema

# --- !Ups
CREATE TABLE wallpaper(
  id INT NOT NULL AUTO_INCREMENT,
  originalFilename VARCHAR(128) CHARACTER SET utf8 NOT NULL,
  mimeType VARCHAR(64) NOT NULL,
  dateCreated DATETIME NOT NULL,

  CONSTRAINT pk_wallpaper PRIMARY KEY(id)
);

CREATE TABLE user_wallpaper(
  userId INT NOT NULL,
  wallpaperId INT NOT NULL,
  dateUploaded DATETIME NOT NULL,

  CONSTRAINT pk_user_wallpaper PRIMARY KEY(userId, wallpaperId),

  CONSTRAINT fk_user_wallpaper_user 
    FOREIGN KEY (userId) REFERENCES user(id),

  CONSTRAINT fk_user_wallpaper_wallpaper 
    FOREIGN KEY (wallpaperId) REFERENCES wallpaer(id)
);
