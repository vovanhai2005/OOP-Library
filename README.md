# Libary Management System
## Overview
LibroSync is an Library Management System (LMS) application designed to efficiently manage library resources. It provides a user-friendly interface for both library staff and users, facilitating all operations such as managing user and book information for librarians, as well as document transactions for both users and admin. With some innovations compared to tradditional library management system, LibroSync will offer smooth and efficient library experience for all skateholders.

## Table of Contents

- [Setup](#setup)
  - [Prerequisites](#prerequisites)
- [Installation](#installation)
  - [Download](#download)
  - [Configuration](#configuration)
- [Feature](#feature)
  - [Register](#register)
  - [Admin](#admin)
  - [User](#user)
- [Technology](#technology)

## Setup
### Prerequisites
  - Java JDK 23: https://www.oracle.com/java/technologies/downloads/#java23
  - Prefered IDE: Intellij IDEA 2024.2.3 (https://www.jetbrains.com/idea/download/?section=windows)
  - SQL Connector: https://mvnrepository.com/artifact/mysql/mysql-connector-java/8.0.33
  - GSON: https://github.com/google/gson/releases/tag/gson-parent-2.12.1
  - ControlFX: https://github.com/controlsfx/controlsfx
## Installation
  ### Download:
  - Access the project repository at https://github.com/vovanhai2005/OOP-Library and fork the repository to your personal computer
  - Download all the prerequisites application
  ### Configuration
  - Navigate to File > Project Structure > Libraries and add the jar file of ControlFX and GSON.
  - Navigate to SDKs to add JDK 23 to your project.
  ### Run
  - Run the project by running the Core > Main.java
## Feature
### Register
- Login scene
  
![Screenshot 2025-02-11 141555](https://github.com/user-attachments/assets/ed241670-8d5f-41dd-949e-f2d8f35262d9)

- Registration scene

![image](https://github.com/user-attachments/assets/bb48c8bc-7d77-446f-9a06-faf22c1d8fa7)

- Rules:
  - Password: Minimum 6 characters, at least one letter, one number, and one special character, no spaces allowed.
  - Phone Number: Must be exactly 10 digits, starting with '0', digits only.
  - Email: Must be in valid format (e.g., example@gmail.com), accepted domains: gmail.com, vnu.edu.vn.
  - Security Code: A 6-digit code sent via email.


### Admin
- Main menu view:

![image](https://github.com/user-attachments/assets/9b8c336c-db2f-440a-b3de-97099fdb122e)

- Dashboard view: Overview of application's level of activity

![image](https://github.com/user-attachments/assets/421c7fec-461e-4761-9fca-cc2e2f84c326)

- Book list view: Add book automatically

![image](https://github.com/user-attachments/assets/f79a3279-3c04-4f52-9862-33e69f0f1104)

![image](https://github.com/user-attachments/assets/af9a2784-864c-4ee1-ae2d-41c4c6af5158)

- Book request view: Add book request automatically with users and books information in local database

![image](https://github.com/user-attachments/assets/01e09db5-138c-45a3-b80d-dd1222121f37)

![image](https://github.com/user-attachments/assets/d15fc3d7-3f68-4dc3-b9f1-911e21929e22)

- Book return view: Add book return automatically with books currently rented by users.

![image](https://github.com/user-attachments/assets/e65455bd-fb95-43f4-a018-9fdbc9986b68)

![image](https://github.com/user-attachments/assets/9d8e3a1f-e182-4aa8-ba36-d44990c3d124)

### User
- Main menu view: User can access recently-add books and most favorable books
  
![image](https://github.com/user-attachments/assets/a0a9d619-2e67-4de1-a98c-d7f7d005c28e)

![image](https://github.com/user-attachments/assets/56510d33-66c8-424b-af11-41d2a857228e)

- Book list view: User can see all types of books with their authors and ratings.

![image](https://github.com/user-attachments/assets/130c6779-ee4e-4c85-b288-414d0805f00d)

- User info view: User can see their account's information including basic information and borrowed books. User can also change their basic information if necessary.

![image](https://github.com/user-attachments/assets/4a01d46f-aa1c-4f2a-97e2-36c3d2f0a3f2)

### Other features
- Applying a multi-language application including Vietnamese and English for users.
- Integrating Google Books API so that every documents' transactions will be automated, which ameliorate the process and enhance users' experience.
## Technology
- Java: The core programming language for the application.
- JavaFX: Framework for building the user interface.
- Scene Builder: A visual layout tool for designing JavaFX application UIs.
- Maven: Dependency management and build automation tool.
- XAMPP: Lightweight database for storing library data (or your preferred database).
- JUnit: Framework for unit testing to ensure code quality.
