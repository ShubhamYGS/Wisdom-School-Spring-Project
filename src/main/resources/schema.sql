CREATE TABLE IF NOT EXISTS `contact_msg` (
  `CONTACT_ID` INT NOT NULL AUTO_INCREMENT,
  `NAME` VARCHAR(100) NOT NULL,
  `EMAIL` VARCHAR(100) NOT NULL,
  `SUBJECT` VARCHAR(100) NOT NULL,
  `MESSAGE` VARCHAR(500) NOT NULL,
  `STATUS` VARCHAR(10) NOT NULL,
  `CREATED_AT` TIMESTAMP NOT NULL,
  `CREATED_BY` VARCHAR(50) NOT NULL,
  `UPDATED_AT` TIMESTAMP DEFAULT NULL,
  `UPDATED_BY` VARCHAR(50) DEFAULT NULL,
   PRIMARY KEY(`contact_id`)
);

CREATE TABLE IF NOT EXISTS `holidays` (
  `DAY` VARCHAR(20) NOT NULL,
  `REASON` VARCHAR(100) NOT NULL,
  `TYPE` VARCHAR(20) NOT NULL,
  `CREATED_AT` TIMESTAMP NOT NULL,
  `CREATED_BY` VARCHAR(50) NOT NULL,
  `UPDATED_AT` TIMESTAMP DEFAULT NULL,
  `UPDATED_BY` VARCHAR(50) DEFAULT NULL
);

CREATE TABLE IF NOT EXISTS `roles` (
  `role_id` int NOT NULL AUTO_INCREMENT,
  `role_name` varchar(50) NOT NULL,
  `created_at` TIMESTAMP NOT NULL,
  `created_by` varchar(50) NOT NULL,
  `updated_at` TIMESTAMP DEFAULT NULL,
  `updated_by` varchar(50) DEFAULT NULL,
   PRIMARY KEY (`role_id`)
);


CREATE TABLE IF NOT EXISTS `address` (
  `address_id` int NOT NULL AUTO_INCREMENT,
  `mobile_number` varchar(15) NOT NULL,
  `address1` varchar(200) NOT NULL,
  `city` varchar(50) NOT NULL,
  `state` varchar(50) NOT NULL,
  `zip_code` varchar(10) NOT NULL,
  `created_at` TIMESTAMP NOT NULL,
  `created_by` varchar(50) NOT NULL,
  `updated_at` TIMESTAMP DEFAULT NULL,
  `updated_by` varchar(50) DEFAULT NULL,
   PRIMARY KEY (`address_id`)
);

CREATE TABLE IF NOT EXISTS `class` (
  `class_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `created_at` TIMESTAMP NOT NULL,
  `created_by` varchar(50) NOT NULL,
  `updated_at` TIMESTAMP DEFAULT NULL,
  `updated_by` varchar(50) DEFAULT NULL,
   PRIMARY KEY (`class_id`)
);

CREATE TABLE IF NOT EXISTS `person` (
  `person_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `email` varchar(50) NOT NULL,
  `pwd` varchar(200) NOT NULL,
  `role_id` int NOT NULL,
  `address_id` int NULL,
  `class_id` int NULL,
  `reset_password_token` varchar(30) NULL,
  `created_at` TIMESTAMP NOT NULL,
  `created_by` varchar(50) NOT NULL,
  `updated_at` TIMESTAMP DEFAULT NULL,
  `updated_by` varchar(50) DEFAULT NULL,
   PRIMARY KEY (`person_id`),
   FOREIGN KEY (role_id) REFERENCES roles(role_id),
   FOREIGN KEY (address_id) REFERENCES address(address_id),
   FOREIGN KEY (`class_id`) REFERENCES class(class_id)
);

CREATE TABLE IF NOT EXISTS `courses` (
  `course_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `descrip` varchar(400) NOT NULL,
  `tags` varchar(100) NOT NULL,
  `lesson_no` varchar(10) NOT NULL,
  `rating` varchar(10) NOT NULL,
  `fees` varchar(10) NOT NULL,
  `courseimage` varchar(50) DEFAULT NULL,
  `created_at` TIMESTAMP NOT NULL,
  `created_by` varchar(50) NOT NULL,
  `updated_at` TIMESTAMP DEFAULT NULL,
  `updated_by` varchar(50) DEFAULT NULL,
   PRIMARY KEY (`course_id`)
);

CREATE TABLE IF NOT EXISTS `person_courses` (
  `person_id` int NOT NULL,
  `course_id` int NOT NULL,
  FOREIGN KEY (person_id) REFERENCES person(person_id),
  FOREIGN KEY (course_id) REFERENCES courses(course_id),
  PRIMARY KEY (`person_id`,`course_id`)
);

CREATE TABLE IF NOT EXISTS `career` (
  `job_id` int NOT NULL AUTO_INCREMENT,
  `first_name` varchar(20) NOT NULL,
  `last_name` varchar(20) NOT NULL,
  `email` varchar(50) NOT NULL,
  `address` varchar(250) NOT NULL,
  `city` varchar(50) NOT NULL,
  `state` varchar(50) NOT NULL,
  `zip_code` varchar(20) NOT NULL,
  `yourself` varchar(400),
  `college` varchar(100) NOT NULL,
  `degree` varchar(100) NOT NULL,
  `cgpa` varchar(20) NOT NULL,
  `job_title` varchar(100),
  `company` varchar(100),
  `working` boolean default false,
  `resume` varchar(50),
  `status` varchar(20) NOT NULL,
  `created_at` TIMESTAMP NOT NULL,
  `created_by` varchar(50) NOT NULL,
  `updated_at` TIMESTAMP DEFAULT NULL,
  `updated_by` varchar(50) DEFAULT NULL,
   PRIMARY KEY (`job_id`)
);