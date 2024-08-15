# Backend Challenge - User Profile and Coin Data API

## Description

This project is a backend service that allows users to create and update their profiles, as well as fetch various cryptocurrency data from a third-party API. The service is built with Spring Boot, Hibernate, and MySQL, adhering to RESTful principles.

## Features

- **User Signup:** Allows users to register with a unique username and email.
- **User Login:** Secure login mechanism with session-based authentication.
- **Profile Management:** Users can update their profile information, including name, email, and mobile number.
- **3rd Party API Integration:** Fetches and stores cryptocurrency data for users based on the provided API key.

## Tech Stack

- **Spring Boot**
- **Spring Security**
- **Hibernate**
- **MySQL**

## Endpoints

1. **Signup User**
    - **URL:** `/signup`
    - **Method:** `POST`
    - **Description:** Registers a new user with basic validation and security.
    - **Headers:** `X-Api-Origin: "cashrich-predefined-header"`
    - **Fields:** `First Name`, `Last Name`, `Email`, `Mobile`, `Username`, `Password`

2. **Login User**
    - **URL:** `/login`
    - **Method:** `POST`
    - **Description:** Authenticates the user using session-based authentication and confirms API origin.
    - **Headers:** `X-Api-Origin: "cashrich-predefined-header"`
    - **Fields:** `Username`, `Password`

3. **Update User Profile**
    - **URL:** `/update`
    - **Method:** `PUT`
    - **Description:** Updates the user's profile information. Only logged-in users can update their data.
    - **Fields:** `First Name`, `Last Name`, `Mobile`, `Password`

4. **Fetch Coin Data**
    - **URL:** `/coin-data`
    - **Method:** `GET`
    - **Description:** Fetches cryptocurrency data using the CoinMarketCap API for logged in User and Saves in the DB.
    - **Headers:** `X-CMC_PRO_API_KEY: <Your-API-Key>`
    - **Response:** JSON data of cryptocurrency prices.

## Installation and Setup

1. **Clone the Repository:**
   ```bash
   git clone git@github.com:your-username/backend-challenge.git
   cd backend-challenge
