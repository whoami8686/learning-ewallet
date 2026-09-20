# E-Wallet REST API

Simple E-Wallet REST API built using Spring Boot with two main features:

* Check Balance
* Transfer Balance

## Requirements

* Java 25
* MariaDB

## Database Setup

Run the `database.sql` file located in the project root.

Using MySQL/MariaDB command line:

```bash
mysql -u root -p < database.sql
```

Or execute `database.sql` manually using tools such as DBeaver, DataGrip, HeidiSQL, or phpMyAdmin.

The script will create the database, tables, and initial data.

Initial users:

| ID | Name | Balance |
| -: | ---- | ------: |
|  1 | Ana  | 1000.00 |
|  2 | Budi | 1000.00 |
|  3 | Cici | 1000.00 |

Default database configuration:

```properties
spring.datasource.url=jdbc:mariadb://localhost:3306/learning_ewallet
spring.datasource.username=root
spring.datasource.password=
```

Update `src/main/resources/application.properties` if your local database configuration is different.

## Run Application

### Windows

```bash
mvnw.cmd spring-boot:run
```

### Linux / macOS

```bash
./mvnw spring-boot:run
```

Application will run at:

```text
http://localhost:8080
```

---

# API

## 1. Check Balance

### Request

```http
GET /api/v1/wallets/1/balance
```

Example:

```http
GET http://localhost:8080/api/v1/wallets/1/balance
```

### Success Response

```json
{
  "data": {
    "userId": 1,
    "userName": "Ana",
    "balance": 1000.00
  },
  "error": null
}
```

### Failed Response

Example when wallet/user does not exist:

```http
GET /api/v1/wallets/99/balance
```

```json
{
  "data": null,
  "error": {
    "status": 404,
    "code": "RESOURCE_NOT_FOUND",
    "message": "Wallet not found for userId: 99"
  }
}
```

---

## 2. Transfer

### Request

```http
POST /api/v1/transfers
Content-Type: application/json
```

```json
{
  "senderId": 1,
  "receiverId": 2,
  "amount": 100.00
}
```

### Success Response

```json
{
  "data": {
    "reference": "xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx",
    "senderId": 1,
    "receiverId": 2,
    "amount": 100.00,
    "createdAt": "2026-09-20T20:00:00"
  },
  "error": null
}
```

After the transfer:

```text
Ana  : 900.00
Budi : 1100.00
```

### Failed Response — Insufficient Balance

Request:

```json
{
  "senderId": 3,
  "receiverId": 1,
  "amount": 1500.00
}
```

Response:

```json
{
  "data": null,
  "error": {
    "status": 400,
    "code": "INSUFFICIENT_BALANCE",
    "message": "Insufficient balance"
  }
}
```

### Failed Response — Same Sender and Receiver

Request:

```json
{
  "senderId": 1,
  "receiverId": 1,
  "amount": 100.00
}
```

Response:

```json
{
  "data": null,
  "error": {
    "status": 400,
    "code": "BUSINESS_ERROR",
    "message": "Sender and receiver must be different"
  }
}
```

### Failed Response — Invalid Amount

Request:

```json
{
  "senderId": 1,
  "receiverId": 2,
  "amount": 0
}
```

The API will return `400 Bad Request` because the minimum transfer amount is `0.01`.

## API Testing

Example requests are also available in:

```text
API-Testing.http
```

The file can be executed directly using IntelliJ IDEA HTTP Client.
