UNLOCK TABLES;
DROP DATABASE IF EXISTS `voting_system`;
CREATE DATABASE IF NOT EXISTS `voting_system`;
USE `voting_system`;

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `email` varchar(255) PRIMARY KEY,
  `password` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  `role` varchar(255) NOT NULL
);

DROP TABLE IF EXISTS `story`;
CREATE TABLE `story` (
  `id` varchar(255) PRIMARY KEY,
  `name` varchar(255),
  `status` varchar(255) NOT NULL,
  `point` varchar(255) NOT NULL
);

LOCK TABLES `user` WRITE;
INSERT INTO `user` (`email`, `password`, `username`, `role`)
VALUES ('tech_lead@gmail.com', '123456', 'Tech Lead', 'Tech Lead'),
('developer1@gmail.com', '123456', 'Developer 1', 'Developer'),
('developer2@gmail.com', '123456', 'Developer 2', 'Developer'),
('developer3@gmail.com', '123456', 'Developer 3', 'Developer'),
('developer4@gmail.com', '123456', 'Developer 4', 'Developer'),
('developer5@gmail.com', '123456', 'Developer 5', 'Developer'),
('developer6@gmail.com', '123456', 'Developer 6', 'Developer');
UNLOCK TABLES;

LOCK TABLES `story` WRITE;
INSERT INTO `story` (`id`, `name`, `status`, `point`)
VALUES ('1', 'implement chat feature', 'Waiting', ""),
('2', 'add KYC UI to back office', 'Waiting', ""),
('3', 'create FX market stop order', 'Waiting', "");
UNLOCK TABLES;
