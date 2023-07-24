# Identity Reconciliation Web Service

This repository contains a web service that performs identity reconciliation for a customer across multiple purchases. The service is designed to be integrated with FluxKart.com, an online store that uses different contact information for each purchase to maintain customer privacy. The service links orders made with different email addresses or phone numbers to the same customer, allowing FluxKart to provide a personalized customer experience.

## How It Works

The web service exposes an endpoint at `/bitespeed/identity-reconsiliation/identify` to receive HTTP POST requests with JSON bodies. The JSON body should contain either an `email` or a `phoneNumber` (or both) for identification. The service then performs the identity reconciliation and returns a JSON payload containing the consolidated contact information.

### Example Request:

```json
{
    "email": "lorraine@hillvalley.edu",
    "phoneNumber": "1234567890"
}
```

### Example Response:

```json
{
    "contact": {
        "primaryContactId": 1,
        "emails": ["mcfly@hillvalley.edu", "lorraine@hillvalley.edu", "george@hillvalley.edu"],
        "phoneNumbers": ["1234567890"],
        "secondaryContactIds": [2, 3]
    }
}
```

## How to Use
This application has been containerized and image is published to `mr33325/identity-reconsiliation-app` repository

There are two methods two run this application:

### Method 1

1. Navigate into the folder `Final-docker-file`.

2. Open `cmd` in that folder.

3. Run `docker-compose up`.

4. The web service will be accessible at `http://localhost:8080/bitespeed/identity-reconsiliation/identify`.

5. To test if the service is running, ping to `http://localhost:8080/bitespeed/identity-reconsiliation/ping`. You will get `pong` as response.


### Method 2

If you are facing any issue you can follow the second method.

1. Navigate to `Identity-Reconciliation\identity-reconciliation` folder.
  
2. Run `Maven install` in eclipse or intellij disabling tests off. It will generate jar file in `target` folder.

3. You will also find a `docker-compose.yaml` file in that folder. Open cmd and run `docker-compose up`.

4.  You can access the endpoints same as mentioned in method 1.

## Endpoint Documentation

### Identify Endpoint

- **URL:** `identity-reconsiliation/identify`
- **Method:** POST
- **Request Body:** JSON object with optional `email` and `phoneNumber` fields.
- **Response:** JSON object with consolidated contact information.

```JavaScript

var requestOptions = {
  method: 'GET',
  redirect: 'follow'
};

fetch("http://localhost:8080/bitespeed/identity-reconsiliation/ping", requestOptions)
  .then(response => response.text())
  .then(result => console.log(result))
  .catch(error => console.log('error', error));

```

```JavaScript

var myHeaders = new Headers();
myHeaders.append("Content-Type", "application/json");

var raw = JSON.stringify({
  "email": "lorraine@hillvalley.edu",
  "phoneNumber": null
});

var requestOptions = {
  method: 'POST',
  headers: myHeaders,
  body: raw,
  redirect: 'follow'
};

fetch("http://localhost:8080/bitespeed/identity-reconsiliation/identify", requestOptions)
  .then(response => response.text())
  .then(result => console.log(result))
  .catch(error => console.log('error', error));

```

### Ping Endpoint

- **URL:** `/identity-reconciliation/ping`
- **Method:** GET
- **Request Body:** None.
- **Response:** "pong".

## Dependencies

The web service uses the following dependencies:

- Spring Boot: Framework for building web applications with minimal configuration.
- Hibernate: Object-relational mapping library for database interactions.
- Spring Data JPA: Provides support for data access using the Java Persistence API (JPA).
- MySQL (or any other SQL database): Used for storing customer contact information.

## Contact

If you have any questions or need further assistance, please contact us at [rana.maheesh@gmail.com](mailto:rana.maheesh@gmail.com).
