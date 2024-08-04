CREATE DATABASE IF NOT EXISTS `beerdispenser` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
GO
USE `beerdispenser`;
GO
CREATE TABLE dispenser (
  `id` varchar(36) NOT NULL,
  `flow_volumen` float NOT NULL,
  `price_per_liter` float NOT NULL,
  `status` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

GO

CREATE TABLE `dispenserusages` (
  `id` int NOT NULL AUTO_INCREMENT,
  `openet_at` datetime NOT NULL,
  `close_at` datetime DEFAULT NULL,
  `total_spent` float DEFAULT NULL,
  `dispenser_id` varchar(36) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `dispenser_id` (`dispenser_id`),
  CONSTRAINT `dispenserusages_ibfk_1` FOREIGN KEY (`dispenser_id`) REFERENCES `dispenser` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
