# 🏨 Hostel Management System

A command-line Java application to manage hostel students, rooms, and fees — with CSV file persistence.

---

## 📁 Project Structure

```
hostel-management-system/
├── data/                    # Auto-created CSV files
│   ├── students.csv
│   ├── rooms.csv
│   └── fees.csv
├── bin/                     # Compiled .class files
├── src/
│   └── edu/hostel/
│       ├── Main.java
│       ├── cli/Menu.java
│       ├── domain/          Student, Room, Fee
│       ├── service/         HostelService, HostelServiceImpl
│       ├── exception/       RoomFullException
│       ├── util/            InputUtil
│       └── io/              FileHandler
└── README.md
```

---

## ⚙️ How to Compile & Run

### 1. Compile all Java files

```bash
find src -name "*.java" > sources.txt
javac -d bin @sources.txt
```

### 2. Run the program

```bash
java -cp bin edu.hostel.Main
```

---

## ✨ Features

### 👨‍🎓 Student Management
- Add / Update / Delete students
- Search by name, ID, email, or course
- Assign / Vacate rooms

### 🛏️ Room Management
- Add rooms: Single, Double, Triple
- View all rooms or available rooms
- View occupants per room
- Toggle maintenance mode

### 💰 Fee Management
- Add fee records (rent, mess, etc.)
- View all / pending fees
- View fees by student
- Mark fees as paid

### 📊 Reports & Dashboard
- Live dashboard: student count, room occupancy, fee totals
- Full tabular reports for students, rooms, and fees

### 💾 CSV Persistence
- Data auto-saves to `data/*.csv` on exit
- Auto-loads on next run

---

## 📌 Demo Data

On first run, sample students, rooms, and fees are loaded automatically so you can explore all features immediately.

---

## 🔧 Requirements

- Java 17+ (uses switch expressions)
- No external libraries needed