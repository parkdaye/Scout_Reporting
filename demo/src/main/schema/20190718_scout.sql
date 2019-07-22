-- scout 컨텐츠를 위한 테이블들

-- Account
CREATE TABLE `account` (
  `team_id` int(11) NOT NULL,
  `ap` int(11) NOT NULL DEFAULT '0',
  `crt_date` datetime NOT NULL,
  `upd_date` datetime NOT NULL,
  PRIMARY KEY (`team_id`),
  KEY `fk$team$account$team_id` (`team_id`),
  CONSTRAINT `fk$team$account$team_id` FOREIGN KEY (`team_id`) REFERENCES `team` (`team_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin

-- Person
CREATE TABLE `person` (
  `person_id` int(11) NOT NULL,
  `name` varchar(45) COLLATE utf8_bin NOT NULL,
  `crt_date` datetime NOT NULL,
  `upd_date` datetime NOT NULL,
  PRIMARY KEY (`person_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin

-- Player
CREATE TABLE `player` (
  `player_id` int(11) NOT NULL,
  `person_id` int(11) NOT NULL,
  `team_code` varchar(10) COLLATE utf8_bin NOT NULL,
  `cost` int(11) NOT NULL,
  `crt_date` datetime NOT NULL,
  `upd_date` datetime NOT NULL,
  PRIMARY KEY (`player_id`),
  KEY `fk$person$player$person_id` (`person_id`),
  CONSTRAINT `fk$person$player$person_id` FOREIGN KEY (`person_id`) REFERENCES `person` (`person_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin

-- Team
CREATE TABLE `team` (
  `team_id` int(11) NOT NULL AUTO_INCREMENT,
  `team_name` varchar(45) COLLATE utf8_bin NOT NULL,
  `crt_date` datetime NOT NULL,
  `upd_date` datetime NOT NULL,
  PRIMARY KEY (`team_id`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8 COLLATE=utf8_bin

-- TeamPlayer
CREATE TABLE `team_player` (
  `team_id` int(11) NOT NULL,
  `player_id` int(11) NOT NULL,
  `level` int(11) NOT NULL,
  `exp` int(11) NOT NULL,
  `crt_date` datetime NOT NULL,
  `upd_date` datetime NOT NULL,
  PRIMARY KEY (`team_id`,`player_id`),
  KEY `fk$player$team_player$player_id` (`player_id`),
  CONSTRAINT `fk$player$team_player$player_id` FOREIGN KEY (`player_id`) REFERENCES `player` (`player_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk$team$team_player$team_id` FOREIGN KEY (`team_id`) REFERENCES `team` (`team_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin

-- TeamSrSlot
CREATE TABLE `team_sr_slot` (
  `team_id` int(11) NOT NULL,
  `slot_no` int(11) NOT NULL,
  `player_id` int(11) NOT NULL,
  `contract_yn` char(1) COLLATE utf8_bin NOT NULL DEFAULT 'N',
  `crt_date` datetime NOT NULL,
  `upd_date` datetime NOT NULL,
  PRIMARY KEY (`team_id`,`slot_no`),
  KEY `fk$player$team_sr_slot$player_id` (`player_id`),
  CONSTRAINT `fk$player$team_sr_slot$player_id` FOREIGN KEY (`player_id`) REFERENCES `player` (`player_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk$team$team_sr_slot&team_id` FOREIGN KEY (`team_id`) REFERENCES `team` (`team_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin


-- 테스트를 위한 임시세팅
insert