# Wisdom School Web Application
<h1 align="center">
  <br>
  <img src="https://user-images.githubusercontent.com/33577947/180727734-36effef4-9847-4689-bb29-a52ffd319bd5.png" alt="Foco" height="160" width="160">
</h1>

## Getting Started
This app is mainly categorised into three major sections:
* ### Admin Section: [Role: ADMIN]
  - **Contact Messages:** Admin can see all the open contact messages submitted by contact page form. Once he contacts the user he can close that particular ticket. 
  - **Career:** This page categorizes all the jobs applied at school into three sections (open [Neither hired nor rejected], hired, rejected) candidate. The admin can see all the job profiles and open any indivisual profile. Based on the profile he can either hire that candidate (Sending interview mail invitation) or reject that candidate (Sending rejection mail).
  - **Classes:** Admin can add any number of classes (eg., Class 9, Class 10). In the class, he can add any number of students by filtering them with e-mail.
  - **Courses:** Admin can add courses for their student which comprises of title, description, no. of lessons, price, preview image and many more. Also, he has the ability to see the enrolled students under any particular course. 
* ### Student Section: [Role: STUDENT]
  - **Profile:** Student can add/edit his name, email, mobile number, address etc. 
  - **Courses:** Student can see all the courses added by admin. He has the ability to enroll in his favorite course. Once he enrolls, he can see those enrolled courses under My Courses Section. If he wants, he can also Unenroll from that course.
  - **Holiday:** Student can see all the Federal/Festival holidays of the School. These holidays are fetched from database so admin can add/remove any of the holiday.
* ### Any anonymous User: [Role: NONE]
  - **Website Page:** Any anonymous user can see our website. He has access to Contact Us, About Us and Apply for Job Pages.
  - **Login/Register/Forgot Password**: Users can also Register to Website, Login into the website. Or, in case they forgot their password they can reset it easily.

## Video Demo
[![Wisdom School Spring Project](https://img.youtube.com/vi/9x5NrEKExTI/0.jpg)](https://www.youtube.com/watch?v=9x5NrEKExTI)

## Features
* Different User Profiles/Roles: ADMIN, STUDENT.
* Sign In, Register & Reset Password functionlity with Spring Security.
* Storing and displaying holidays from MySQL Database with power of JPA & Hibernate.
* Apply for Job and hiring/rejecting the profile with sending mail using Java Mail Sender.
* Clean & Simple UI with Dark Mode [intergation with backend using Thyemeleaf template Engine].
* Submit your queries in Contact Form. Admin can see all queries and contact the user and close ticket.
* Adding courses dynamically with enroll/unenroll feature for students.
* JPA Pagination & Sorting for simplifying the user experience.
* Adding students to Class. Also, showing user in which class they are studying.
* Validating each and every field in form with @Valid annotation of Lombok Library.
* Giving the ability to user to edit/add things in his own profile.
* Auditing the user activity with JPA Auditing (Created By, Created Date, Updated By, Updated Date).
* Securing & adding Autorization to HTML Pages with Spring Security.
* Given access to HAL Explorer and Data Rest for Admin.
* Spring Boot Logging functionality: All the logs are stored in separate folder.
* Segregation of code into multiple packages like Controller, Service, Model, Security, Repository, Constants etc., for better understanding.

## Built With
- [Spring Boot](https://spring.io/projects/spring-boot)
- [Spring MVC](https://docs.spring.io/spring-framework/docs/3.2.x/spring-framework-reference/html/mvc.html)
- [Spring Security](https://spring.io/projects/spring-security)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
- [Lombok](https://projectlombok.org/)
- [Spring Dev Tools](https://docs.spring.io/spring-boot/docs/1.5.16.RELEASE/reference/html/using-boot-devtools.html)
- [Spring Java Mail Sender](https://docs.spring.io/spring-framework/docs/3.0.x/spring-framework-reference/html/mail.html)
- [Data Rest & Hal Explorer](https://docs.spring.io/spring-data/rest/docs/current/reference/html/)
- [Spring Logging](https://docs.spring.io/spring-boot/docs/2.1.13.RELEASE/reference/html/boot-features-logging.html)
- [Thymeleaf Template Engine](https://www.thymeleaf.org/)
- [Bootstrap](https://getbootstrap.com/)
- [MySQL Database](https://www.mysql.com/)

## Database Schema Diagram

![Schema](https://user-images.githubusercontent.com/33577947/180804066-a2dbb8fb-ad1c-4992-af1b-73b9462402a4.png)

## Visit our Website
[Click here to Visit Wisdom School Website](https://wisdomschool-spring-shubhamygs.herokuapp.com/)
- Cloud Used: [Heroku](https://www.heroku.com/)
- Online Database Used (Limit 5MB): [FreeMySQLHosting](https://www.freemysqlhosting.net/)
- **Note:** Currently, uploaded images/pdf are not shown into the website as we have stored them in folder (which Heroku doesn't supports) and not on any cloud storage (eg., AWS S3).

## Contributing
1. Fork it (<https://github.com/ShubhamYGS/Wisdom-School-Spring-Project/fork>)
2. Create your feature branch (`git checkout -b feature/fooBar`)
3. Commit your changes (`git commit -am 'Add some fooBar'`)
4. Push to the branch (`git push origin feature/fooBar`)
5. Create a new Pull Request :D

<br>
<div align="center">
  <h3>Proudly :muscle: made with <b><a href="https://spring.io/">Spring</a> :sparkling_heart:</b></h3>
</div>
<br>

## License
-------

    The MIT License (MIT)
    
    Copyright (c) 2022 Shubham Patil
    
    Permission is hereby granted, free of charge, to any person obtaining a copy
    of this software and associated documentation files (the "Software"), to deal
    in the Software without restriction, including without limitation the rights
    to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
    copies of the Software, and to permit persons to whom the Software is
    furnished to do so, subject to the following conditions:

    The above copyright notice and this permission notice shall be included in all
    copies or substantial portions of the Software.

    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
    FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
    AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
    LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
    OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
    SOFTWARE.
