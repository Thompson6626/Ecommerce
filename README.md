# E-Commerce Backend

This project is an e-commerce platform built using Spring Boot. It features JWT-based authentication with three roles: **Buyer**, **Seller**, and **Admin**. The platform allows users to manage products, place and manage orders, leave reviews, and make payments through **Stripe**.

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

## Technologies Used

- **Spring Boot**: Core application framework.
- **JWT**: For authentication and role-based access control.
- **Spring Security**: To manage security, including roles and permissions.
- **Stripe**: Payment processing integration.
- **Hibernate**: ORM framework to interact with the database.
- **PostgreSQL**: Relational database for storing e-commerce data.