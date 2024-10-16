<p align="center">
  <a href="https://spring.io/projects/spring-boot" target="blank">
    <img src="https://miro.medium.com/v2/resize:fit:1400/format:webp/1*BBQq8yCFxaqneypPPpx2Jw.png" height="60" width="250" alt="Java Logo" />
  </a>
  &nbsp;&nbsp;&nbsp;&nbsp; <!-- Adding 4 non-breaking spaces for separation -->
  <a  href="https://www.postgresql.org/" target="blank">
    <img src="https://upload.wikimedia.org/wikipedia/commons/thumb/2/29/Postgresql_elephant.svg/800px-Postgresql_elephant.svg.png" height="100" width="100" alt="PostgreSQL Logo">
  </a>
</p>

# E-Commerce Backend

This project is an e-commerce platform built using Spring Boot. It features JWT-based authentication with three roles: **Buyer**, **Seller**, and **Admin**. The platform allows users to manage products, place and manage orders, leave reviews, and make payments through **Stripe**.

## Technologies Used

- **Spring Boot**: Core application framework.
- **JWT**: For authentication and role-based access control.
- **Spring Security**: To manage security, including roles and permissions.
- **Stripe**: Payment processing integration.
- **Hibernate**: ORM framework to interact with the database.
- **PostgreSQL**: Relational database for storing e-commerce data.
- **Docker**: Containerized the application to ensure consistent environments across different systems.
- **Docker Compose**: Orchestrates multi-container setups, including PostgreSQL and MailDev, to streamline the development environment.

## Features

- **JWT Authentication**: Secure login and access control using JWT tokens.
- **Role-based Authorization**: Specific endpoints for Buyers, Sellers, and Admins.
- **Order Management**: Buyers can place, cancel, and return orders. Admins can mark orders as shipped or delivered.
- **Product Management**: Sellers and Admins can add, update, and delete products.
- **Review System**: Authenticated users can leave reviews for products.
- **Stripe Payment Integration**: Secure payment processing using Stripe.


## Roles and Permissions

The system supports three roles with distinct permissions:

- **Buyer**: 
  - View products.
  - Request orders.
  - Cancel or return orders.
  - View order history.
  - Submit product reviews.

- **Seller**:
  - Buyer priviliges. 
  - Add, update, and delete products.
  - Can only delete own products.
  
- **Admin**:
  - Capability to add products and delete other sellers products but not update them.
  - Mark orders as shipped or delivered.
  - Manage product categories.