# 💈 BarberBook - Barbershop Appointment Booking App

<p align="center">
  <img src="screenshots/logo.png" alt="BarberBook Logo" width="150"/>
</p>

<p align="center">
  <b>Book Your Style</b><br>
  A complete Android application for barbershop appointment booking and management
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Platform-Android-green?style=flat-square" alt="Platform"/>
  <img src="https://img.shields.io/badge/Language-Java-orange?style=flat-square" alt="Language"/>
  <img src="https://img.shields.io/badge/Database-SQLite-blue?style=flat-square" alt="Database"/>
  <img src="https://img.shields.io/badge/Min%20SDK-24-yellow?style=flat-square" alt="Min SDK"/>
</p>

---

## 📖 Table of Contents

- [About The Project](#-about-the-project)
- [Features](#-features)
- [Screenshots](#-screenshots)
- [Tech Stack](#-tech-stack)
- [Project Requirements](#-project-requirements)
- [Project Structure](#-project-structure)
- [Database Schema](#-database-schema)
- [Installation](#-installation)
- [Usage](#-usage)
- [App Flow](#-app-flow)
- [Team Members](#-team-members)

---

## 📱 About The Project

**BarberBook** is a comprehensive mobile application designed to streamline the barbershop appointment booking process. The app provides a seamless experience for customers to book appointments while giving administrators full control over bookings and barber management.

### 🎯 Problem Statement
Traditional barbershop booking methods (phone calls, walk-ins) are inefficient and lead to:
- Long waiting times for customers
- Difficulty managing appointments for barbershop owners
- No visibility into barber availability

### 💡 Solution
BarberBook provides a digital platform that:
- Allows customers to book appointments anytime, anywhere
- Gives admins real-time control over bookings and barber availability
- Reduces waiting times and improves customer satisfaction

---

## ✨ Features

### 👤 Customer Features

| Feature | Description |
|---------|-------------|
| **User Registration** | Create account with email, phone, and profile details |
| **Secure Login** | Authentication with session management |
| **Service Selection** | Choose from multiple services (Haircut, Shaving, Hair Wash, Hair Coloring) |
| **Barber Selection** | View barber profiles, ratings, experience, and real-time availability |
| **Date & Time Booking** | Select preferred appointment slot |
| **Booking Confirmation** | Review and confirm booking with optional notes |
| **Booking History** | View all appointments with status filter (Upcoming/Completed/Cancelled) |
| **Cancel Booking** | Cancel upcoming appointments |
| **Profile Management** | Edit personal information |
| **Change Password** | Update account password securely |

### 👨‍💼 Admin Features

| Feature | Description |
|---------|-------------|
| **Admin Login** | Separate secure admin authentication |
| **Dashboard Statistics** | Real-time overview of bookings (Upcoming/Completed/Cancelled) |
| **View All Bookings** | See all customer bookings with details |
| **Filter Bookings** | Filter by status for easy management |
| **Complete Booking** | Mark appointments as completed |
| **Cancel Booking** | Cancel any booking with confirmation |
| **Manage Barbers** | Add, edit, and update barber information |
| **Availability Control** | Toggle barber availability in real-time |

---

## 📸 Screenshots

### Customer App

<p align="center">
  <img src="screenshots/splash.png" width="180" alt="Splash"/>
  <img src="screenshots/login.png" width="180" alt="Login"/>
  <img src="screenshots/register.png" width="180" alt="Register"/>
  <img src="screenshots/home.png" width="180" alt="Home"/>
</p>

<p align="center">
  <img src="screenshots/select_service.png" width="180" alt="Services"/>
  <img src="screenshots/select_barber.png" width="180" alt="Barbers"/>
  <img src="screenshots/confirmation.png" width="180" alt="Confirm"/>
  <img src="screenshots/my_appointments.png" width="180" alt="Appointments"/>
</p>

<p align="center">
  <img src="screenshots/profile.png" width="180" alt="Profile"/>
  <img src="screenshots/edit_profile.png" width="180" alt="Edit Profile"/>
</p>

### Admin Panel

<p align="center">
  <img src="screenshots/admin_login.png" width="180" alt="Admin Login"/>
  <img src="screenshots/admin_dashboard.png" width="180" alt="Dashboard"/>
  <img src="screenshots/manage_barbers.png" width="180" alt="Manage Barbers"/>
  <img src="screenshots/edit_barber.png" width="180" alt="Edit Barber"/>
</p>

---

## 🛠️ Tech Stack

| Category | Technology |
|----------|------------|
| **Language** | Java |
| **IDE** | Android Studio |
| **Database** | SQLite |
| **UI Framework** | Material Design Components |
| **Architecture** | MVC (Model-View-Controller) |
| **Min SDK** | API 24 (Android 7.0 Nougat) |
| **Target SDK** | API 34 (Android 14) |

### Libraries Used

- `androidx.appcompat` - Backward compatible UI components
- `androidx.cardview` - Card-based layouts
- `androidx.recyclerview` - Efficient list displays
- `com.google.android.material` - Material Design components

---

## 📋 Project Requirements

This project fulfills all requirements for the Wireless and Mobile Programming course:

| # | Requirement | Implementation | Status |
|---|-------------|----------------|--------|
| 1 | Intent and Activity | 13 Activities with proper navigation | ✅ |
| 2 | Text Input | Name, Email, Phone, Notes, Search fields | ✅ |
| 3 | Password Input | Password with visibility toggle | ✅ |
| 4 | Selection (Spinner) | Gender selection dropdown | ✅ |
| 5 | Checkbox | Terms & Conditions, Service selection | ✅ |
| 6 | Radio Button | Barber selection | ✅ |
| 7 | Button | Multiple action buttons throughout app | ✅ |
| 8 | SQLite Database | Full CRUD operations on 4 tables | ✅ |
| 9 | Application Workflow | Complete user & admin business flow | ✅ |
| 10 | Design Aesthetics | Professional barbershop theme with Material Design | ✅ |

---

## 🗂️ Project Structure
```
app/src/main/
├── java/com/example/barberbook/
│   ├── activities/
│   │   ├── SplashActivity.java
│   │   ├── LoginActivity.java
│   │   ├── RegisterActivity.java
│   │   ├── MainActivity.java
│   │   ├── SelectServiceActivity.java
│   │   ├── SelectBarberActivity.java
│   │   ├── ConfirmationActivity.java
│   │   ├── MyAppointmentsActivity.java
│   │   ├── ProfileActivity.java
│   │   ├── EditProfileActivity.java
│   │   ├── AdminLoginActivity.java
│   │   ├── AdminDashboardActivity.java
│   │   └── ManageBarbersActivity.java
│   ├── adapters/
│   │   ├── BookingAdapter.java
│   │   ├── AdminBookingAdapter.java
│   │   └── AdminBarberAdapter.java
│   ├── database/
│   │   └── DatabaseHelper.java
│   ├── models/
│   │   ├── User.java
│   │   ├── Service.java
│   │   ├── Barber.java
│   │   └── Booking.java
│   └── utils/
│       └── SessionManager.java
│
├── res/
│   ├── layout/
│   │   ├── activity_splash.xml
│   │   ├── activity_login.xml
│   │   ├── activity_register.xml
│   │   ├── activity_main.xml
│   │   ├── activity_select_service.xml
│   │   ├── activity_select_barber.xml
│   │   ├── activity_confirmation.xml
│   │   ├── activity_my_appointments.xml
│   │   ├── activity_profile.xml
│   │   ├── activity_edit_profile.xml
│   │   ├── activity_admin_login.xml
│   │   ├── activity_admin_dashboard.xml
│   │   ├── activity_manage_barbers.xml
│   │   ├── item_service.xml
│   │   ├── item_barber.xml
│   │   ├── item_booking.xml
│   │   ├── item_admin_booking.xml
│   │   ├── item_admin_barber.xml
│   │   └── dialog_edit_barber.xml
│   ├── values/
│   │   ├── colors.xml
│   │   ├── strings.xml
│   │   └── themes.xml
│   └── drawable/
│       └── spinner_background.xml
│
└── AndroidManifest.xml
```

---

## 🗄️ Database Schema

### Entity Relationship Diagram
```
┌─────────────┐     ┌─────────────┐     ┌─────────────┐
│   USERS     │     │  BOOKINGS   │     │   BARBERS   │
├─────────────┤     ├─────────────┤     ├─────────────┤
│ id (PK)     │────<│ user_id(FK) │     │ id (PK)     │
│ full_name   │     │ barber_id   │>────│ name        │
│ email       │     │ barber_name │     │ rating      │
│ phone       │     │ services    │     │ experience  │
│ gender      │     │ date        │     │ available   │
│ password    │     │ time        │     └─────────────┘
└─────────────┘     │ total_price │
                    │ status      │     ┌─────────────┐
                    │ notes       │     │  SERVICES   │
                    └─────────────┘     ├─────────────┤
                                        │ id (PK)     │
                                        │ name        │
                                        │ description │
                                        │ price       │
                                        └─────────────┘
```

### Table Details

#### Users Table
| Column | Type | Constraint | Description |
|--------|------|------------|-------------|
| id | INTEGER | PRIMARY KEY, AUTOINCREMENT | Unique user ID |
| full_name | TEXT | NOT NULL | User's full name |
| email | TEXT | UNIQUE, NOT NULL | User's email address |
| phone | TEXT | NOT NULL | Phone number |
| gender | TEXT | | Male/Female |
| password | TEXT | NOT NULL | Encrypted password |

#### Services Table
| Column | Type | Constraint | Description |
|--------|------|------------|-------------|
| id | INTEGER | PRIMARY KEY, AUTOINCREMENT | Unique service ID |
| name | TEXT | NOT NULL | Service name |
| description | TEXT | | Service description |
| price | INTEGER | NOT NULL | Price in Rupiah |

#### Barbers Table
| Column | Type | Constraint | Description |
|--------|------|------------|-------------|
| id | INTEGER | PRIMARY KEY, AUTOINCREMENT | Unique barber ID |
| name | TEXT | NOT NULL | Barber's name |
| rating | REAL | | Rating (1.0 - 5.0) |
| experience | INTEGER | | Years of experience |
| available | INTEGER | | 1 = Available, 0 = Not Available |

#### Bookings Table
| Column | Type | Constraint | Description |
|--------|------|------------|-------------|
| id | INTEGER | PRIMARY KEY, AUTOINCREMENT | Unique booking ID |
| user_id | INTEGER | FOREIGN KEY | Reference to Users |
| barber_id | INTEGER | FOREIGN KEY | Reference to Barbers |
| barber_name | TEXT | | Barber's name (denormalized) |
| services | TEXT | | Comma-separated service names |
| date | TEXT | | Booking date |
| time | TEXT | | Booking time |
| total_price | INTEGER | | Total price in Rupiah |
| status | TEXT | | Upcoming/Completed/Cancelled |
| notes | TEXT | | Optional customer notes |

---

## 🚀 Installation

### Prerequisites

- Android Studio (Arctic Fox or later)
- Android SDK API 24+
- Git

### Steps

1. **Clone the repository**
```bash
   git clone https://github.com/yourusername/BarberBook.git
   cd BarberBook
```

2. **Open in Android Studio**
    - Launch Android Studio
    - Select "Open an existing project"
    - Navigate to the BarberBook folder
    - Click "OK"

3. **Wait for Gradle sync**
    - Android Studio will automatically download dependencies
    - Wait for the sync to complete

4. **Build the project**
```
   Build → Rebuild Project
```

5. **Run the app**
    - Connect an Android device or start an emulator
    - Click the Run button (▶️) or press `Shift + F10`

---

## 📱 Usage

### Login Credentials

#### Customer Account
Register a new account through the app's registration screen.

#### Admin Account
| Field | Value |
|-------|-------|
| Username | `admin` |
| Password | `admin123` |

### Default Data

The app comes pre-loaded with:

**Services:**
| Service | Price |
|---------|-------|
| Haircut | Rp 35,000 |
| Shaving | Rp 15,000 |
| Hair Wash | Rp 20,000 |
| Hair Coloring | Rp 100,000 |

**Barbers:**
| Name | Rating | Experience | Availability |
|------|--------|------------|--------------|
| Mas Andi | 4.8 | 5 years | Available |
| Mas Budi | 4.5 | 3 years | Available |
| Mas Chandra | 4.9 | 7 years | Not Available |

---

## 🔄 App Flow

### Customer Journey
```
┌─────────────────────────────────────────────────────────────┐
│                      CUSTOMER FLOW                          │
└─────────────────────────────────────────────────────────────┘

    ┌──────────┐
    │  Splash  │ (2 seconds)
    └────┬─────┘
         │
         ▼
    ┌──────────┐     ┌────────────┐
    │  Login   │────>│  Register  │
    └────┬─────┘     └────────────┘
         │
         ▼
    ┌──────────┐
    │   Home   │
    └────┬─────┘
         │
    ┌────┴────┬────────────┐
    ▼         ▼            ▼
┌────────┐ ┌──────────┐ ┌─────────┐
│Book Now│ │Appointmts│ │ Profile │
└───┬────┘ └──────────┘ └────┬────┘
    │                        │
    ▼                        ▼
┌─────────────┐         ┌──────────┐
│Select Service│         │Edit Prof │
└──────┬──────┘         └──────────┘
       │
       ▼
┌─────────────┐
│Select Barber│
└──────┬──────┘
       │
       ▼
┌─────────────┐
│Confirmation │
└──────┬──────┘
       │
       ▼
   ┌───────┐
   │Success│──────> Back to Home
   └───────┘
```

### Admin Journey
```
┌─────────────────────────────────────────────────────────────┐
│                       ADMIN FLOW                            │
└─────────────────────────────────────────────────────────────┘

    ┌───────────────┐
    │  Admin Login  │
    └───────┬───────┘
            │
            ▼
    ┌───────────────┐
    │   Dashboard   │
    │  (Statistics) │
    └───────┬───────┘
            │
    ┌───────┴───────┐
    ▼               ▼
┌─────────┐   ┌───────────────┐
│ Manage  │   │ View Bookings │
│ Barbers │   │ (Filter/CRUD) │
└────┬────┘   └───────────────┘
     │
     ▼
┌──────────────┐
│Toggle Avail. │
│ Edit Details │
└──────────────┘
```

---

## 🎨 Design System

### Color Palette

| Color | Hex | Preview | Usage |
|-------|-----|---------|-------|
| Primary | `#1A1A2E` | ![#1A1A2E](https://via.placeholder.com/20/1A1A2E/1A1A2E) | Headers, Primary UI |
| Primary Dark | `#16213E` | ![#16213E](https://via.placeholder.com/20/16213E/16213E) | Status bar |
| Accent | `#E94560` | ![#E94560](https://via.placeholder.com/20/E94560/E94560) | Buttons, CTAs |
| Gold | `#D4AF37` | ![#D4AF37](https://via.placeholder.com/20/D4AF37/D4AF37) | Premium elements |
| Background | `#F5F5F5` | ![#F5F5F5](https://via.placeholder.com/20/F5F5F5/F5F5F5) | Screen backgrounds |
| Success | `#4CAF50` | ![#4CAF50](https://via.placeholder.com/20/4CAF50/4CAF50) | Upcoming status |
| Info | `#2196F3` | ![#2196F3](https://via.placeholder.com/20/2196F3/2196F3) | Completed status |
| Error | `#F44336` | ![#F44336](https://via.placeholder.com/20/F44336/F44336) | Cancelled status |

### Typography

- **Headers:** Bold, 20-28sp
- **Body:** Regular, 14-16sp
- **Caption:** Regular, 12sp

---

## 👥 Team Members

| Photo | Name | Role | Responsibilities |
|-------|------|------|------------------|
| 👤 | [Person 1] | UI/UX & Integration | Layouts, Java Integration, Documentation |
| 👤 | [Person 2] | Backend Developer | Database, Models, CRUD Operations |
| 👤 | [Person 3] | Logic Developer | Activities, Business Logic, Navigation |

---

## 📄 License

This project was created for educational purposes as part of the **Wireless and Mobile Programming** course.

**Institution:** [Your University Name]  
**Study Program:** Informatics  
**Semester:** 2024/2025

---

## 🙏 Acknowledgments

- Course Instructor for guidance and requirements
- Material Design for UI components
- Android Developer Documentation

---

<p align="center">
  <b>Made with ❤️ by Team BarberBook</b>
</p>

<p align="center">
  <a href="#-barberbook---barbershop-appointment-booking-app">Back to Top ↑</a>
</p>
