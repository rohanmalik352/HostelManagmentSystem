#  Hostel Management System

A simple and efficient command-line Java application is used to manage a hostel’s operations, including maintaining student records, room allotments, and fees. Data storage employs CSV files. Thus, this removes the need for external dependencies making it lightweight and easy to run. 

---

##  Description

Hostel Management System is to make the hostel administration simple. By using a menu-driven system the user can manage fees, allot rooms and manage student information. All data is stored in CSV files. When the program terminates, these files are saved automatically, and when it is restarted, they are read in. In this way, data loss is avoided and consistency of the used system is sustained. The implementation of basic Java concepts is used in the project. It is easy to use and understand because it does not require external libraries. 


---

##  Project Structure

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

##  How to Compile and Run

### Step 1: Compile the project

```
Javac -d bin @sources.txt find src -name "*.java" > sources.txt
```

### Step 2: Run the application

```
java -cp bin edu.hostel.Main
```

---

##  Features

### Student Management


* Add new students
* Change student information 
* Remove student records
* Search students by name, id, email, or course 
* Assign rooms and vacate rooms 

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

##  Demo Data

Once you run the application for the first time, sample data is loaded automatically. This lets you try out all the features without having to enter any data yourself. 

---

##  Requirements

* Java 17 or higher
* No external libraries required

---

##  Future Improvements

* Add graphical user interface
* Integrate database (MySQL or MongoDB)
* Develop web or mobile version
* Add login and authentication system

---

## Author

Rohan Malik
24BAI10613

---

##  License

This project is for academic and learning purposes.
