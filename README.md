# About
This project is built with a Maven structure. Use the standard Maven commands to build, run, and interact with the application.

# Instructions Summary
1. Reserve a Book
2. Retrieve Reservation (By ID or User)
3. Cancel a Book Reservation
4. Explore Database or API Documentation

# How-Books Are Populated in the System
Books are preloaded at the API runtime using an `import.sql` file. The table starts with five books. Here are five sample book UUIDs you can use:

- **BOOK_1**: `c1ffe15d-1fca-4ff1-936c-8fbb0714f448` (10 copies available)
- **BOOK_2**: `a5413653-cfc6-446e-90e7-1f5c7366e19f` (0 copies available)
- **BOOK_3**: `f823034a-445e-4608-ac8e-2ddefb9d982f` (1 copy available)
- **BOOK_4**: `00c42ad2-86d9-402d-b6aa-3db2c43e016a` (5 copies available)
- **BOOK_5**: `9ff3c249-2b27-49a5-a920-306e6ff60d3b` (5 copies available)

---

## How to Reserve a Book
To reserve a book, send a POST request as shown below:

```bash
curl -X 'POST' \
  'http://localhost:8080/reservation' \
  -H 'accept: application/json' \
  -H 'Content-Type: application/json' \
  -d '{
    "userId": "USER_ID_PLACEHOLDER",       # Replace with the actual User UUID
    "bookId": "BOOK_1_ID"                 # Replace with the Book UUID
  }'
```

---

## Retrieve Existing Reservation by ID
To fetch a reservation, send a GET request with the Reservation ID:

```bash
curl -X 'GET' \
  'http://localhost:8080/reservation/RESERVATION_ID_PLACEHOLDER' \
  -H 'accept: application/json'
```

---

## Retrieve All Reservations for a Specific User
To fetch all reservations for a user, send a GET request:

```bash
curl -X 'GET' \
  'http://localhost:8080/reservation/users/USER_ID_PLACEHOLDER' \
  -H 'accept: application/json'
```

---

## Cancel a Reservation
To cancel a reservation, send a PATCH request using the Reservation ID:

```bash
curl -X 'PATCH' \
  'http://localhost:8080/reservation/RESERVATION_ID_PLACEHOLDER/CANCELED' \
  -H 'accept: application/json'
```

---

# Useful Links
- **PgAdmin**: [Access](http://localhost:8081/browser/)
  - UserName = admin@admin.com
  - Password = admin
- **Swagger API Documentation**: [Access](http://localhost:8080/library/swagger-ui/index.html#/)