--
-- Dumping test data for table `user`
--
-- NOTE: The passwords are encrypted using BCrypt
--
-- Default passwords are the same as the usernames (i.e. password for username admin is also admin)
--

INSERT INTO `user` (username,password,email,phone,first_name,last_name,enabled)
VALUES
('admin','$2a$10$QKYlOtxRkjx5KZb/8WDJMeCokhYjwyn1XFAXrLSnTDnENe15LfaEi','adam@themerae.com','123-456-7890','Adam','Min',1),
('designer','$2a$10$WVcx0cGpEIv/TOmlcSznqe0SxzQAYgpr9BFRg460wQPZPhyvUNUCa','des@themerae.com','987-654-3210','Des','Sign',1);

--
-- Dumping data for table `roles`
--

INSERT INTO `role` (name)
VALUES 
('ROLE_ADMIN'),('ROLE_DESIGNER');

--
-- Dumping test data for table `user_role`
--

INSERT INTO `user_role` (user_id,role_id)
VALUES 
(1,1),
(1,2),
(2,2);

--
-- Dumping test data for table `project`
-- Invoice links are random urls out of privacy reasons
-- You may enter your own
--

-- For unassigned projects
INSERT INTO `project` (company,paid,invoice,type,description,print,status,due_date)
VALUES
('Banana Inc',0,'https://www.bananainc.com/','Logo','Simple,clean,two-toned',0,'N/A','2019-05-01'),
('Ultra Technologies',1,'https://www.ulttech.com/','Double Sided Business Card','For Kathy Smith, recruiter',1,'N/A','2019-06-05');

-- For assigned projects
INSERT INTO `project` (company,paid,invoice,assigned_date,type,description,print,status,due_date,user_id)
VALUES
('Welcome Cafe',0,'https://www.welcomecafe.com','2019-03-20','Email Signature','Cursive',1,'Second','2019-12-01',2),
('The Pet Shop',1,'https://www.thepetshop.com','2019-01-10','SquareSpace Web Design','Add huskies',1,'N/A','2019-05-01',1);

-- For completed projects
INSERT INTO `project` (company,paid,invoice,assigned_date,type,description,print,status,due_date,submitted_date,user_id)
VALUES
('Tennis Blaze',1,'https://www.tennisblaze.com','2018-12-03','Double Sided Brochure','Glossy finish',1,'Completed','2018-12-30','2018-12-28',1);

-- For cancelled projects
INSERT INTO `project` (company,paid,invoice,assigned_date,type,description,print,status,due_date,cancelled_date,user_id)
VALUES
('Bulgogi BBQ',0,'https://www.thebulgogibbq.com','2019-01-02','Paper Menu','Double-sided',1,'Cancelled','2019-01-20','2019-01-05',2);
