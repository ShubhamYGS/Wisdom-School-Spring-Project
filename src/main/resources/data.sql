INSERT INTO `holidays` (`DAY`,`REASON`,`TYPE`,`CREATED_AT`, `CREATED_BY`)
 VALUES (' Jan 1','New Year','FEDERAL',CURDATE(),'DBA');

INSERT INTO `holidays` (`DAY`,`REASON`,`TYPE`,`CREATED_AT`, `CREATED_BY`)
 VALUES (' Jan 26 ','Republic Day','FEDERAL',CURDATE(),'DBA');

INSERT INTO `holidays` (`DAY`,`REASON`,`TYPE`,`CREATED_AT`, `CREATED_BY`)
 VALUES (' Feb 19','ShivJayanti','FEDERAL',CURDATE(),'DBA');

INSERT INTO `holidays` (`DAY`,`REASON`,`TYPE`,`CREATED_AT`, `CREATED_BY`)
 VALUES (' Mar 11','Mahashivratri','FESTIVAL',CURDATE(),'DBA');

INSERT INTO `holidays` (`DAY`,`REASON`,`TYPE`,`CREATED_AT`, `CREATED_BY`)
 VALUES (' Apr 2','Good Friday','FEDERAL',CURDATE(),'DBA');

INSERT INTO `holidays` (`DAY`,`REASON`,`TYPE`,`CREATED_AT`, `CREATED_BY`)
 VALUES (' Apr 13','Gudhi Padva','FESTIVAL',CURDATE(),'DBA');

INSERT INTO `holidays` (`DAY`,`REASON`,`TYPE`,`CREATED_AT`, `CREATED_BY`)
 VALUES (' May 1','Maharashtra Day','FEDERAL',CURDATE(),'DBA');

INSERT INTO `holidays` (`DAY`,`REASON`,`TYPE`,`CREATED_AT`, `CREATED_BY`)
 VALUES (' May 13','Ramzan Eid','FESTIVAL',CURDATE(),'DBA');

INSERT INTO `holidays` (`DAY`,`REASON`,`TYPE`,`CREATED_AT`, `CREATED_BY`)
 VALUES (' Jul 20','Bakri Eid','FESTIVAL',CURDATE(),'DBA');

INSERT INTO `holidays` (`DAY`,`REASON`,`TYPE`,`CREATED_AT`, `CREATED_BY`)
 VALUES (' Aug 15','Independence Day','FEDERAL',CURDATE(),'DBA');

INSERT INTO `holidays` (`DAY`,`REASON`,`TYPE`,`CREATED_AT`, `CREATED_BY`)
 VALUES (' Oct 2','Gandhi Jayanti','FEDERAL',CURDATE(),'DBA');

INSERT INTO `holidays` (`DAY`,`REASON`,`TYPE`,`CREATED_AT`, `CREATED_BY`)
 VALUES (' Nov 16','Diwali','FESTIVAL',CURDATE(),'DBA');

INSERT INTO `holidays` (`DAY`,`REASON`,`TYPE`,`CREATED_AT`, `CREATED_BY`)
 VALUES (' Dec 25','Christmas','FESTIVAL',CURDATE(),'DBA');

--Adding Two Roles (Admin & User)

INSERT INTO `roles` (`role_name`,`created_at`, `created_by`)
  VALUES ('ADMIN',CURDATE(),'DBA');

INSERT INTO `roles` (`role_name`,`created_at`, `created_by`)
  VALUES ('STUDENT',CURDATE(),'DBA');

--Add Admin Manually
--insert into person value (1, 'Admin', 'admin@gmail.com', 'admin', 1, null, curdate(), 'Shubham', curdate(), 'Shubham')
--Hashed Value of Password (Generated using BcryptPasswordEncoder)
insert into `person` (`name`,`email`,`pwd`,`role_id`,`created_at`,`created_by`,`updated_at`,`updated_by`)
 values ('Admin', 'admin@gmail.com', '$2a$12$YRI8MSjVpyV6xRWpRJubSuj5U15Pv0T9UFo2VABtAzXRzkqW.7osa', 1, curdate(), 'Shubham', curdate(), 'Shubham')

--Inserting contact msg for testing
INSERT INTO `contact_msg` (`name`,`email`,`subject`,`message`,`status`,`created_at`, `created_by`)
  VALUES ('Adam','zadam@gmail.com','Regarding a job','Wanted to join as teacher','Open',CURDATE(),'DBA');

INSERT INTO `contact_msg` (`name`,`email`,`subject`,`message`,`status`,`created_at`, `created_by`)
  VALUES ('Zara','zarabaig@hotmail.com','Course Admission','Wanted to join a course','Open',CURDATE(),'DBA');

INSERT INTO `contact_msg` (`name`,`email`,`subject`,`message`,`status`,`created_at`, `created_by`)
  VALUES ('Marques','kmarques@yahoo.com','Course Review','Review of Development course','Open',CURDATE(),'DBA');

INSERT INTO `contact_msg` (`name`,`email`,`subject`,`message`,`status`,`created_at`, `created_by`)
  VALUES ('Shyam','gshyam@gmail.com','Admission Query','Need to talk about admission','Open',CURDATE(),'DBA');

INSERT INTO `contact_msg` (`name`,`email`,`subject`,`message`,`status`,`created_at`, `created_by`)
  VALUES ('John','doejohn@gmail.com','Holiday Query','Query on upcoming holidays','Open',CURDATE(),'DBA');

INSERT INTO `contact_msg` (`name`,`email`,`subject`,`message`,`status`,`created_at`, `created_by`)
  VALUES ('Taniya Bell','belltaniya@gmail.com','Child Scholarship','Can my child get scholarship?','Open',CURDATE(),'DBA');

INSERT INTO `contact_msg` (`name`,`email`,`subject`,`message`,`status`,`created_at`, `created_by`)
  VALUES ('Willie Lara','476lara@gmail.com','Need Admission','My son need an admission','Open',CURDATE(),'DBA');

INSERT INTO `contact_msg` (`name`,`email`,`subject`,`message`,`status`,`created_at`, `created_by`)
  VALUES ('Jonathan Parsons','jonathan.parsons@gmail.com','Course feedback','Music course is good','Open',CURDATE(),'DBA');

INSERT INTO `contact_msg` (`name`,`email`,`subject`,`message`,`status`,`created_at`, `created_by`)
  VALUES ('Cloe Rubio','rubio987@gmail.com','Correct Date of Birth','My Child DOB needs to be corrected','Open',CURDATE(),'DBA');

INSERT INTO `contact_msg` (`name`,`email`,`subject`,`message`,`status`,`created_at`, `created_by`)
  VALUES ('Camilla Stein','camillas@gmail.com','Transport Query','Is Transport provided?','Open',CURDATE(),'DBA');

INSERT INTO `contact_msg` (`name`,`email`,`subject`,`message`,`status`,`created_at`, `created_by`)
  VALUES ('Lizeth Gross','grossliz@yahoo.com','Progress report','Please send progress report','Open',CURDATE(),'DBA');

INSERT INTO `contact_msg` (`name`,`email`,`subject`,`message`,`status`,`created_at`, `created_by`)
  VALUES ('Yael Howe','howeyael@gmail.com','Certificate Query','Need Certificate hard copy','Open',CURDATE(),'DBA');

INSERT INTO `contact_msg` (`name`,`email`,`subject`,`message`,`status`,`created_at`, `created_by`)
  VALUES ('Ian Moreno','moreno.ian@gmail.com','Food feedback','Food quality can be improved','Open',CURDATE(),'DBA');

INSERT INTO `contact_msg` (`name`,`email`,`subject`,`message`,`status`,`created_at`, `created_by`)
  VALUES ('Desirae Ibarra','ibarrades@gmail.com','Traffic Complaint','Traffic around school can be controlled','Open',CURDATE(),'DBA');

INSERT INTO `contact_msg` (`name`,`email`,`subject`,`message`,`status`,`created_at`, `created_by`)
  VALUES ('Oswaldo Jarvis','jarvissmile@hotmail.com','Study Tour','Study tour details needed','Open',CURDATE(),'DBA');

INSERT INTO `contact_msg` (`name`,`email`,`subject`,`message`,`status`,`created_at`, `created_by`)
  VALUES ('Miah Perkins','perkinsmiah@hotmail.com','Vaccination Support','Vaccination center in the school','Open',CURDATE(),'DBA');

INSERT INTO `contact_msg` (`name`,`email`,`subject`,`message`,`status`,`created_at`, `created_by`)
  VALUES ('Zion Bolton','boltzion@gmail.com','Course fees','Pls share fees of music course','Open',CURDATE(),'DBA');

INSERT INTO `contact_msg` (`name`,`email`,`subject`,`message`,`status`,`created_at`, `created_by`)
  VALUES ('Dominik Tanner','tannerdominik@gmail.com','Games schedule','Provide Summer games schedule','Open',CURDATE(),'DBA');