# Rotaract Fellowship Management System

The Fellowship Management System is a comprehensive web application designed to streamline the management of fellowship activities, including 
attendance tracking, club and member management, user authentication, email communication, and more. This system offers a centralized platform 
for organizing and coordinating various aspects of fellowship operations.

## Table of Contents

- [Features](#features)
- [Getting Started](#getting-started)
    - [Prerequisites](#prerequisites)
    - [Installation](#installation)
- [Usage](#usage)
- [Modules](#modules)
    - [Authentication](#authentication)
    - [Club Management](#club-management)
    - [Member Management](#member-management)
    - [Email Communication](#email-communication)
- [Contributing](#contributing)
- [License](#license)

## Overview
The Fellowship Management System serves as a comprehensive tool for managing various aspects of fellowship operations. It includes features for 
tracking attendance records for different events, managing fellowship clubs and members, ensuring secure user authentication, and facilitating 
effective communication through emails.

## Features

### Attendance Tracking: 
Record attendance for members in various fellowship clubs and events. Filter, search, and export attendance data for reporting purposes.

### Club Management: 
Manage club information, including names, descriptions, and logos. View and update club details as needed.

### Member Management: 
Maintain member details, including names, contact information, and roles. Add, edit, and remove member information.

### User Authentication: 
User registration and login functionality with secure access to attendance data and user-specific features.

### Communication: 
Send email notifications to members for event updates and announcements. Integration with email service for efficient communication.

## Getting Started

### Prerequisites

Before you begin, ensure you have the following installed:

- Java JDK (version 1.8)
- Spring Boot (version 2.5.6)
- MySQL database

### Installation

1. Clone this repository to your local machine using: git clone https://ochiengj22@bitbucket.org/ochiengj/racthk.git
2. Configure the database settings in `src/main/resources/application.properties`.
3. Build and run the Spring Boot application: ./mvnw spring-boot:run


## Usage

1. Access the application by navigating to `http://localhost:8065` in your web browser.
2. Use the interface to add, edit, and delete attendance records.
3. Use the filter options to narrow down attendance records based on member, club, and event type.
4. Export attendance data to Excel or PDF formats for reporting purposes.
5. Utilize the user authentication system to log in and manage attendance data.
6. Manage clubs and members through their respective modules.
7. Send email communications to members for event notifications and updates.

## Modules

### Authentication

- User registration and login functionality.
- Secure access to attendance data and user-specific features.

### Club Management

- Manage club information, including names, descriptions, and logos.
- View and update club details as needed.

### Member Management

- Maintain member details, including names, contact information, and roles.
- Add, edit, and remove member information.

### Email Communication

- Send email notifications to members for event updates and announcements.
- Integration with email service for efficient communication.

## Contributing

Contributions are welcome! If you'd like to contribute to this project, please follow these steps:

1. Fork the project.
2. Create a new branch with a descriptive name (`feature-name` or `bugfix-name`).
3. Commit your changes and push to your fork.
4. Submit a pull request to the original repository.

Please ensure your code follows the project's coding style and includes relevant documentation.

## License

This project is licensed under the [MIT License](LICENSE).