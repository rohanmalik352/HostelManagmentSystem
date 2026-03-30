# 🏨 Hostel Management System

A simple and efficient command-line Java application to manage hostel operations such as student records, room allocation, and fee tracking. The system uses CSV files for data storage, making it lightweight and easy to run without any external dependencies.

---

## 📌 Description

The Hostel Management System is designed to simplify the process of managing a hostel. It allows users to handle student information, assign rooms, and manage fees through a menu-driven interface.

All data is stored in CSV files, which are automatically saved when the program exits and loaded when it starts again. This ensures that no data is lost and the system remains consistent.

This project is built using core Java concepts and does not require any external libraries, making it easy to understand and use.

---

## 📁 Project Structure

```
hostel-management-system/
├── data/
│   ├── students.csv
│   ├── rooms.csv
│   └── fees.csv
├── bin/
├── src/
│   └── edu/hostel/
│       ├── Main.java
│       ├── cli/Menu.java
│       ├── domain/
│       ├── service/
│       ├── exception/
│       ├── util/
│       └── io/
└── README.md
```

---

## ⚙️ How to Compile and Run

### Step 1: Compile the project

```
find src -name "*.java" > sources.txt
javac -d bin @sources.txt
```

### Step 2: Run the application

```
java -cp bin edu.hostel.Main
```

---

## ✨ Features

### Student Management

* Add new students
* Update student details
* Delete student records
* Search students by name, ID, email, or course
* Assign and vacate rooms

### Room Management

* Add rooms (Single, Double, Triple)
* View all rooms or only available rooms
* Check occupants in each room
* Mark rooms under maintenance

### Fee Management

* Add fee records (rent, mess, etc.)
* View all fees or pending fees
* View fees by student
* Mark fees as paid

### Reports and Dashboard

* View total number of students
* Check room occupancy
* View fee summaries
* Display structured reports

### Data Persistence

* Automatically saves data in CSV files on exit
* Automatically loads data on startup

---

## 📌 Demo Data

When you run the application for the first time, sample data is automatically loaded. This helps you explore all features without entering data manually.

---

## 🔧 Requirements

* Java 17 or higher
* No external libraries required

---

## 🚀 Future Improvements

* Add graphical user interface
* Integrate database (MySQL or MongoDB)
* Develop web or mobile version
* Add login and authentication system

---

## 👨‍💻 Author

Rohan Malik
24BAI10613

---

## 📄 License

This project is for academic and learning purposes.
