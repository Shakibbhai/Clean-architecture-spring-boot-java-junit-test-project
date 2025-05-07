
# 🛡️ User Management System (Clean Architecture)

This project is a simple **User Management and Role Assignment System** built with **Spring Boot** and adheres to **Robert C. Martin's Clean Architecture** principles. It demonstrates a layered approach to designing maintainable and testable enterprise applications. 

It provides functionality to:

- Create users and roles
- Assign/remove roles from users
- Retrieve user details with assigned roles
- Includes validation, DTOs, error handling, and unit testing

---

## 🚀 Features

- **Create User**: Add a new user with name and email
- **Create Role**: Add a new role with role name
- **Assign Role to User**
- **Remove Role from User**
- **Using Jakarta for Dto**
- **Fetch User Details** (with roles)
- **Validation**: `@Email`, `@NotBlank`, etc.
- **Error Handling**: Clean, HTTP-friendly errors
- **H2 Database**: In-memory DB with console access
- **Unit Tests** using JUnit and Mockito

---

## 🔧 Prerequisites

- Java 17 or higher
- Maven 3.8+
- Git (optional)

---

## ⚙️ Setup Instructions

```bash
# Clone (if applicable)
git clone <repository-url>
cd usermanagement

# Build
mvn clean install

# Run
mvn spring-boot:run
```

Visit: [http://localhost:8080](http://localhost:8080)

### 🛢️ Access H2 Console

- URL: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)
- JDBC URL: `jdbc:h2:mem:testdb`
- Username: `sa`
- Password: *(leave blank)*

---

## 📬 API Endpoints

| Method | Endpoint                              | Request Body                                  | Response         |
|--------|----------------------------------------|------------------------------------------------|------------------|
| POST   | `/users`                               | `{ "name": "John", "email": "john@example.com" }` | Created User ID  |
| POST   | `/roles`                               | `{ "roleName": "ADMIN" }`                     | Created Role ID  |
| POST   | `/users/{userId}/assign-role/{roleId}` | (empty)                                       | Success Message  |
| DELETE | `/users/{userId}/remove-role/{roleId}` | (empty)                                       | Success Message  |
| GET    | `/users/{id}`                          | -                                              | User 

---

## 🧪 Testing

```bash
mvn test
```

Runs unit tests for the service layer (mocked repositories with Mockito).

---

## 🧱 Project Structure (Clean Architecture)

```
src/
├── domain/ # Business logic (NO Spring/JPA here)
│ ├── Role.java
│ └── User.java
│
├── application/ # Use cases / Service layer
│ ├── UserService.java
│ ├── RoleService.java
│ └── interfaces/ # Repository interfaces (Ports)
│ ├── UserRepository.java
│ └── RoleRepository.java
│
├── infrastructure/
│ ├── controller/ # REST Controllers
│ │ ├── UserController.java
│ │ └── RoleController.java
| |
│ └── persistence/ # Database layer (Adapters)
│     ├── UserJpaEntity.java
│     ├── RoleJpaEntity.java
│     ├── UserJpaRepository.java
│     ├── RoleJpaRepository.java
│     ├── UserRepositoryImpl.java
│     └── RoleRepositoryImpl.java
│
├── config/
│ └── BeanConfig.java # Bean wiring for services
│
├── resources/
│ ├── application.properties
│ └── data.sql # Optional for seeding data

test/
└── java/com/example/usermanagement/application/
├── UserServiceTest.java # Unit tests
└── RoleServiceTest.java
```

---

## 📦 Dependencies

- Spring Boot 3.x
- Spring Data JPA
- H2 Database
- Hibernate Validator
- JUnit 4
- Mockito

---

## 📌 Notes

- Domain layer is framework-independent
- Repositories are ports; JPA implementations are adapters
- REST Controllers use DTOs to interact with services
- Validation via `@Valid`, `@NotBlank`, `@Email`
- Centralized error handling via `@ControllerAdvice`
- Entity IDs use `UUID.randomUUID()`
- Pagination supported via Spring's `Pageable`

---

## 🎁 Bonus Features

- Role removal endpoint
- Junit Test with mockito

---

## ❓ Troubleshooting

- **H2 Console not accessible**: Check URL and app status
- **Validation errors**: Use correct email format and non-blank values
- **404 errors**: Ensure user/role exists before acting
- **Pagination issues**: Use valid `page` and `size` params

---

## 👨‍💻 Author

Created by **Nazmus Shakib** for a Software Design course at IIT.

---

## 📜 License

Academic use only. Feel free to fork for learning or portfolio purposes.
