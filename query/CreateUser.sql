CREATE USER 'jtransfer'@'localhost' IDENTIFIED BY 'pass';
CREATE DATABASE jtransfer;
GRANT ALL PRIVILEGES ON jtransfer.* TO jtransfer@localhost IDENTIFIED BY 'pass';
CREATE TABLE `jtransfer`.`logging_general` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `timestamp` BIGINT NOT NULL,
  `log` VARCHAR(200) NOT NULL,
  PRIMARY KEY (`id`))
  ENGINE = InnoDB;
CREATE TABLE jtransfer.logging_session(
  id BIGINT(20) PRIMARY KEY NOT NULL AUTO_INCREMENT,
  timestamp BIGINT(20) NOT NULL,
  session_id VARCHAR(32) NOT NULL,
  log VARCHAR(200)
) ENGINE = InnoDB;