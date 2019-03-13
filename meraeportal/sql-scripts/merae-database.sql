DROP DATABASE  IF EXISTS `merae-database`;

CREATE DATABASE  IF NOT EXISTS `merae-database`;
USE `merae-database`;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `password` char(68) NOT NULL,
  `email` varchar(50) NOT NULL,
  `phone` varchar(10) NOT NULL,
  `first_name` varchar(45) NOT NULL,
  `last_name` varchar(45) NOT NULL,
  `enabled` tinyint(1) NOT NULL,
  
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping test data for table `user`
--
-- NOTE: The passwords are encrypted using BCrypt
--
-- Default passwords are the same as the usernames (i.e. password for username admin is also admin)
--

INSERT INTO `user` (username,password,email,phone,first_name,last_name,enabled)
VALUES
('admin','$2a$04$EtyjEjPeneLdsjdtDDvacO8eoiAammHx4BvAeFNKT3WVWLWfEJtrG','adam@merae.com','1234567890','Adam','Min',1),
('designer','$2a$04$Biv.pNdXLbi/9dSfEi2eI.hKx2abCrboqnIucTijLmDVr.jwgi41G','des@merae.com','9876543210','Des','Sign',1);

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `roles`
--

INSERT INTO `role` (name)
VALUES 
('ROLE_DESIGNER'),('ROLE_ADMIN');

--
-- Table structure for table `user_role`
--

DROP TABLE IF EXISTS `user_role`;

CREATE TABLE `user_role` (
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  
  PRIMARY KEY (`user_id`,`role_id`),
  
  KEY `FK_ROLE_idx` (`role_id`),
  
  CONSTRAINT `FK_USER_05` FOREIGN KEY (`user_id`) 
  REFERENCES `user` (`id`) 
  ON DELETE NO ACTION ON UPDATE NO ACTION,
  
  CONSTRAINT `FK_ROLE` FOREIGN KEY (`role_id`) 
  REFERENCES `role` (`id`) 
  ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping test data for table `user_role`
--

INSERT INTO `user_role` (user_id,role_id)
VALUES 
(1,1),
(1,2),
(2,1);

--
-- Table structure for table `project`
--

DROP TABLE IF EXISTS `project`;

CREATE TABLE `project` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `company` varchar(100) NOT NULL,
  `paid` tinyint(1) NOT NULL,
  `invoice` varchar(255) NOT NULL,
  `assigned_date` DATE DEFAULT NULL,
  `type` varchar(80) NOT NULL,
  `description` varchar(255) NOT NULL,
  `print` tinyint(1) NOT NULL,
  `status` varchar(80) NOT NULL,
  `due_date` DATE NOT NULL,
  `submitted_date` DATE DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  
  PRIMARY KEY (`id`),
  
  KEY `FK_USER_idx` (`user_id`),
  CONSTRAINT `FK_USER`
  FOREIGN KEY (`user_id`)
  REFERENCES `user` (`id`)
  ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping test data for table `project`
--

INSERT INTO `project` (company,paid,invoice,type,description,print,due_date)
VALUES
('Banana Inc',0,'https://www.google.com/','Logo Design','Simple,clean,two-toned',0,'2019-05-01'),
('Ultra Technologies',1,'https://www.yahoo.com/','Business Card','For Kathy Smith, recruiter',1,'2019-06-05');