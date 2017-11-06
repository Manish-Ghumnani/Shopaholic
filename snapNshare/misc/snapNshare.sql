CREATE  TABLE IF NOT EXISTS `ejava`.`user`(
`user_id` varchar(100) NOT NULL, `created_at` datetime, PRIMARY KEY ( user_id )
)ENGINE = InnoDB;


CREATE  TABLE IF NOT EXISTS `ejava`.`friends` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `friend_id` varchar(100) ,
  `friend_ref` varchar(100),
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

CREATE  TABLE IF NOT EXISTS `ejava`.`photos`
(
 `id` int(10) unsigned NOT NULL AUTO_INCREMENT, `imgName` varchar(100),
`image` mediumblob, `comment` varchar(100), `postedBy` varchar(100), 
`postedTime` datetime, PRIMARY KEY (`id`)
)ENGINE = InnoDB;

