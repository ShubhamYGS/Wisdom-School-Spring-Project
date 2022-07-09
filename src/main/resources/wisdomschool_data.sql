INSERT INTO `HOLIDAYS` (`DAY`,`REASON`,`TYPE`,`CREATED_AT`, `CREATED_BY`)
 VALUES (' Jan 1','New Year','FEDERAL',CURDATE(),'DBA');

INSERT INTO `HOLIDAYS` (`DAY`,`REASON`,`TYPE`,`CREATED_AT`, `CREATED_BY`)
 VALUES (' Jan 26 ','Republic Day','FEDERAL',CURDATE(),'DBA');

INSERT INTO `HOLIDAYS` (`DAY`,`REASON`,`TYPE`,`CREATED_AT`, `CREATED_BY`)
 VALUES (' Feb 19','ShivJayanti','FEDERAL',CURDATE(),'DBA');

INSERT INTO `HOLIDAYS` (`DAY`,`REASON`,`TYPE`,`CREATED_AT`, `CREATED_BY`)
 VALUES (' Mar 11','Mahashivratri','FESTIVAL',CURDATE(),'DBA');

INSERT INTO `HOLIDAYS` (`DAY`,`REASON`,`TYPE`,`CREATED_AT`, `CREATED_BY`)
 VALUES (' Apr 2','Good Friday','FEDERAL',CURDATE(),'DBA');

INSERT INTO `HOLIDAYS` (`DAY`,`REASON`,`TYPE`,`CREATED_AT`, `CREATED_BY`)
 VALUES (' Apr 13','Gudhi Padva','FESTIVAL',CURDATE(),'DBA');

INSERT INTO `HOLIDAYS` (`DAY`,`REASON`,`TYPE`,`CREATED_AT`, `CREATED_BY`)
 VALUES (' May 1','Maharashtra Day','FEDERAL',CURDATE(),'DBA');

INSERT INTO `HOLIDAYS` (`DAY`,`REASON`,`TYPE`,`CREATED_AT`, `CREATED_BY`)
 VALUES (' May 13','Ramzan Eid','FESTIVAL',CURDATE(),'DBA');

INSERT INTO `HOLIDAYS` (`DAY`,`REASON`,`TYPE`,`CREATED_AT`, `CREATED_BY`)
 VALUES (' Jul 20','Bakri Eid','FESTIVAL',CURDATE(),'DBA');

INSERT INTO `HOLIDAYS` (`DAY`,`REASON`,`TYPE`,`CREATED_AT`, `CREATED_BY`)
 VALUES (' Aug 15','Independence Day','FEDERAL',CURDATE(),'DBA');

INSERT INTO `HOLIDAYS` (`DAY`,`REASON`,`TYPE`,`CREATED_AT`, `CREATED_BY`)
 VALUES (' Oct 2','Gandhi Jayanti','FEDERAL',CURDATE(),'DBA');

INSERT INTO `HOLIDAYS` (`DAY`,`REASON`,`TYPE`,`CREATED_AT`, `CREATED_BY`)
 VALUES (' Nov 16','Diwali','FESTIVAL',CURDATE(),'DBA');

INSERT INTO `HOLIDAYS` (`DAY`,`REASON`,`TYPE`,`CREATED_AT`, `CREATED_BY`)
 VALUES (' Dec 25','Christmas','FESTIVAL',CURDATE(),'DBA');

--Adding Two Roles (Admin & User)

INSERT INTO `roles` (`role_name`,`created_at`, `created_by`)
  VALUES ('ADMIN',CURDATE(),'DBA');

INSERT INTO `roles` (`role_name`,`created_at`, `created_by`)
  VALUES ('STUDENT',CURDATE(),'DBA');

--Add Admin Manually
insert into person value (1, 'Admin', 'admin@gmail.com', 'admin', 1, null, curdate(), 'Shubham', curdate(), 'Shubham')
--Hashed Value of Password (Generated using BcryptPasswordEncoder)
insert into person value (1, 'Admin', 'admin@gmail.com', '$2a$12$YRI8MSjVpyV6xRWpRJubSuj5U15Pv0T9UFo2VABtAzXRzkqW.7osa', 1, null, curdate(), 'Shubham', curdate(), 'Shubham')