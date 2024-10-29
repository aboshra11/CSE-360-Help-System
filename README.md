# CSE360_Project: Help System Phase 1

## Project Overview
This project focuses on establishing a secure and private identity mechanism for an application that supports multiple user roles such as students, admin, and instructional team members. The system allows for flexible role assignments (e.g., an admin might also be a student) and includes account management features like one-time passwords, user topics, and more.

## Team Members
- Mansi Bhanushali
- Vishwesh Kartikeyan
- Dhruv Bhatt    
- Nicholas Trinidad
- Amir Boshra 
## Project Requirements
Each user account includes the following details:
- **Email address**
- **Username**
- **Password** (stored as a non-string data type for security)
- **One-time password flag** indicating whether the password requires renewal
- **Expiration date and time** for the one-time password
- **Name details**: first, middle, last, and preferred name
- **Topic expertise levels**: beginner, intermediate (default), advanced, expert, for system-recognized topics

## Key Features
- **Role Management**: Users can be assigned multiple roles, such as a user being both a student and an admin.
- **One-Time Passwords**: Temporary passwords can be set to expire after a certain date and time.
- **Topic Expertise Levels**: Each user is categorized by topic expertise, which are beginner, intermediate, advanced and expert, defaulting to "intermediate."

## Architecture & Design
- **User Account Management**: Secure storage of user credentials, password renewal mechanisms, and role assignment.
- **Role-Based Access Control (RBAC)**: Custom user roles for students, admins, and instructional team members, with flexible role overlap support.
- **Help System**: The application will integrate a help system that provides tailored assistance to different user roles.
  
## Installation Instructions
1. Clone the repository:
   ```bash
   git clone https://github.com/mansii-28/CSE360_Project.git
