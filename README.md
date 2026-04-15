# Question Microservice

The **Question Microservice** is responsible for managing the lifecycle of quiz questions.  
It provides REST APIs to perform CRUD operations on questions and serves as the central repository for the question bank in a quiz application.

---

## 🚀 Features
- Add new questions
- Update existing questions
- Delete questions
- Retrieve all questions
- Retrieve questions by category

---

## 🏗️ Tech Stack
- **Backend**: Java 17, Spring Boot
- **Database**: PostgreSQL/MySQL with JPA/Hibernate
- **Build Tool**: Maven

---

## 📂 Project Structure
question-microservice/
├── src/main/java/com/app/controller/   # REST Controllers
├── src/main/java/com/app/service/      # Business Logic
├── src/main/java/com/app/repository/   # JPA Repositories
├── src/main/java/com/app/entity/       # Question Entity
├── src/main/resources/
│    ├── application.properties         # DB & app configs
├── pom.xml
└── README.md

---

## ⚙️ Setup & Quickstart

### 1. Clone the repository
```bash
git clone https://github.com/your-username/question-microservice.git
cd question-microservice
```
### 2. Configure Database
Update src/main/resources/application.properties:
```
spring.datasource.url=jdbc:postgresql://localhost:5432/questiondb
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
```
### 3. Build & Run
mvn clean install
mvn spring-boot:run
### 4. Access the APIs
Base URL: http://localhost:8080/questions

---

##📌 API Endpoints
GET /questions/all → get all questions

GET /questions/category/{category} → get questions by category

POST /questions/add → add a new question

PUT /questions/update → update an existing question

DELETE /questions/delete/{id} → delete a question by ID

---

## ✅ Advantages
Independent microservice for question management

Can scale separately from other services

Provides clean APIs for quiz creation and evaluation

---

## 👨‍💻 Author
Developed by Balaji Suggana  
Senior Software Engineer | Java/Spring | Cloud & AI Integration
