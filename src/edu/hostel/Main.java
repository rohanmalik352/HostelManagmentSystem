package edu.hostel;

import edu.hostel.cli.Menu;
import edu.hostel.domain.Room;
import edu.hostel.domain.Student;
import edu.hostel.io.FileHandler;
import edu.hostel.service.HostelServiceImpl;

public class Main {

    public static void main(String[] args) {
        HostelServiceImpl service = new HostelServiceImpl();

        // Load existing data
        System.out.println("\n  Loading data...");
        FileHandler.loadRooms(service);
        FileHandler.loadStudents(service);
        FileHandler.loadFees(service);

        // Seed demo data only if nothing was loaded
        if (service.getAllRooms().isEmpty()) {
            seedDemoData(service);
        }

        // Launch CLI
        Menu menu = new Menu(service);
        menu.start();
    }

    private static void seedDemoData(HostelServiceImpl service) {
        System.out.println("\n  [i] No existing data found. Loading demo data...");

        // Rooms
        service.addRoom(new Room(101, Room.RoomType.SINGLE, 5000));
        service.addRoom(new Room(102, Room.RoomType.DOUBLE, 3500));
        service.addRoom(new Room(103, Room.RoomType.DOUBLE, 3500));
        service.addRoom(new Room(104, Room.RoomType.TRIPLE, 2500));
        service.addRoom(new Room(105, Room.RoomType.SINGLE, 5000));
        service.addRoom(new Room(201, Room.RoomType.DOUBLE, 4000));
        service.addRoom(new Room(202, Room.RoomType.TRIPLE, 2800));

        // Students
        service.addStudent(new Student("S001", "Arjun Sharma",   "arjun@mail.com",   "9876543210", "B.Tech CSE",  "2024-07-01"));
        service.addStudent(new Student("S002", "Priya Patel",    "priya@mail.com",   "9876543211", "B.Tech ECE",  "2024-07-01"));
        service.addStudent(new Student("S003", "Rahul Verma",    "rahul@mail.com",   "9876543212", "MCA",         "2024-07-15"));
        service.addStudent(new Student("S004", "Sneha Gupta",    "sneha@mail.com",   "9876543213", "B.Sc Physics","2024-08-01"));
        service.addStudent(new Student("S005", "Aditya Kumar",   "aditya@mail.com",  "9876543214", "B.Tech ME",   "2024-08-01"));

        // Assign rooms
        try {
            service.assignRoom("S001", 101);
            service.assignRoom("S002", 102);
            service.assignRoom("S003", 102);
            service.assignRoom("S004", 104);
            service.assignRoom("S005", 201);
        } catch (Exception ignored) {}

        // Fees
        String today = java.time.LocalDate.now().toString();
        service.addFee(new edu.hostel.domain.Fee(service.generateFeeId(), "S001", 5000, "2025-03-31", "Monthly Rent March"));
        service.addFee(new edu.hostel.domain.Fee(service.generateFeeId(), "S002", 3500, "2025-03-31", "Monthly Rent March"));
        service.addFee(new edu.hostel.domain.Fee(service.generateFeeId(), "S003", 3500, "2025-03-31", "Monthly Rent March"));
        edu.hostel.domain.Fee paidFee = new edu.hostel.domain.Fee(service.generateFeeId(), "S001", 5000, "2025-02-28", "Monthly Rent Feb");
        paidFee.markAsPaid("2025-02-25");
        service.addFee(paidFee);

        System.out.println("  [✓] Demo data loaded.\n");
    }
}