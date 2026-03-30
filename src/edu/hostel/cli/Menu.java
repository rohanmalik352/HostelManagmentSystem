package edu.hostel.cli;

import edu.hostel.domain.Fee;
import edu.hostel.domain.Room;
import edu.hostel.domain.Student;
import edu.hostel.exception.RoomFullException;
import edu.hostel.io.FileHandler;
import edu.hostel.service.HostelServiceImpl;
import edu.hostel.util.InputUtil;

import java.time.LocalDate;
import java.util.List;

public class Menu {

    private final HostelServiceImpl service;

    public Menu(HostelServiceImpl service) {
        this.service = service;
    }

    public void start() {
        printBanner();
        while (true) {
            printMainMenu();
            int choice = InputUtil.readInt("Enter choice");
            switch (choice) {
                case 1 -> studentMenu();
                case 2 -> roomMenu();
                case 3 -> feeMenu();
                case 4 -> reportsMenu();
                case 5 -> saveData();
                case 0 -> {
                    saveData();
                    System.out.println("\n  Goodbye! Data saved.\n");
                    return;
                }
                default -> System.out.println("  [!] Invalid choice.");
            }
        }
    }

    // ======================== MAIN MENU ========================

    private void printBanner() {
        System.out.println("\n╔══════════════════════════════════════════════════════╗");
        System.out.println("║                                                      ║");
        System.out.println("║        HOSTEL MANAGEMENT SYSTEM  v1.0               ║");
        System.out.println("║                                                      ║");
        System.out.println("╚══════════════════════════════════════════════════════╝");
        service.printDashboard();
    }

    private void printMainMenu() {
        System.out.println("\n══════════════════ MAIN MENU ══════════════════");
        System.out.println("  1. Student Management");
        System.out.println("  2. Room Management");
        System.out.println("  3. Fee Management");
        System.out.println("  4. Reports");
        System.out.println("  5. Save Data");
        System.out.println("  0. Exit");
        System.out.println("═══════════════════════════════════════════════");
    }

    // ======================== STUDENT ========================

    private void studentMenu() {
        while (true) {
            System.out.println("\n────── STUDENT MANAGEMENT ──────");
            System.out.println("  1. Add Student");
            System.out.println("  2. View All Students");
            System.out.println("  3. Search Student");
            System.out.println("  4. Update Student");
            System.out.println("  5. Delete Student");
            System.out.println("  6. Assign Room to Student");
            System.out.println("  7. Vacate Room");
            System.out.println("  0. Back");
            int choice = InputUtil.readInt("Enter choice");
            switch (choice) {
                case 1 -> addStudent();
                case 2 -> service.printStudentReport();
                case 3 -> searchStudent();
                case 4 -> updateStudent();
                case 5 -> deleteStudent();
                case 6 -> assignRoom();
                case 7 -> vacateRoom();
                case 0 -> { return; }
                default -> System.out.println("  [!] Invalid choice.");
            }
        }
    }

    private void addStudent() {
        System.out.println("\n  --- Add New Student ---");
        String id   = InputUtil.readString("Student ID (e.g. S001)");
        String name = InputUtil.readString("Full Name");
        String email= InputUtil.readString("Email");
        String phone= InputUtil.readString("Phone");
        String course=InputUtil.readString("Course");
        String date = LocalDate.now().toString();
        service.addStudent(new Student(id, name, email, phone, course, date));
        InputUtil.pressEnterToContinue();
    }

    private void searchStudent() {
        String kw = InputUtil.readString("Search (name/ID/email/course)");
        List<Student> results = service.searchStudents(kw);
        if (results.isEmpty()) {
            System.out.println("  No students found for: " + kw);
        } else {
            System.out.println("\n  Search Results (" + results.size() + "):");
            System.out.println("┌────────────┬──────────────────────┬───────────────────────────┬──────────────┬─────────────────┬───────┐");
            System.out.println("│ Student ID │ Name                 │ Email                     │ Phone        │ Course          │ Room  │");
            System.out.println("├────────────┼──────────────────────┼───────────────────────────┼──────────────┼─────────────────┼───────┤");
            results.forEach(System.out::println);
            System.out.println("└────────────┴──────────────────────┴───────────────────────────┴──────────────┴─────────────────┴───────┘");
        }
        InputUtil.pressEnterToContinue();
    }

    private void updateStudent() {
        String id = InputUtil.readString("Enter Student ID to update");
        Student s = service.getStudentById(id);
        if (s == null) { System.out.println("  [!] Student not found."); return; }
        System.out.println("  Current: " + s.getName() + " | " + s.getEmail() + " | " + s.getCourse());
        System.out.println("  (Press ENTER to keep existing value)");
        String name  = InputUtil.readString("New Name [" + s.getName() + "]");
        String email = InputUtil.readString("New Email [" + s.getEmail() + "]");
        String phone = InputUtil.readString("New Phone [" + s.getPhone() + "]");
        String course= InputUtil.readString("New Course [" + s.getCourse() + "]");
        if (!name.isEmpty())  s.setName(name);
        if (!email.isEmpty()) s.setEmail(email);
        if (!phone.isEmpty()) s.setPhone(phone);
        if (!course.isEmpty())s.setCourse(course);
        service.updateStudent(s);
        InputUtil.pressEnterToContinue();
    }

    private void deleteStudent() {
        String id = InputUtil.readString("Enter Student ID to delete");
        Student s = service.getStudentById(id);
        if (s == null) { System.out.println("  [!] Student not found."); return; }
        System.out.println("  About to delete: " + s.getName());
        boolean confirm = InputUtil.readYesNo("Confirm delete");
        if (confirm) service.deleteStudent(id);
        else System.out.println("  Cancelled.");
        InputUtil.pressEnterToContinue();
    }

    private void assignRoom() {
        String id = InputUtil.readString("Student ID");
        if (service.getStudentById(id) == null) {
            System.out.println("  [!] Student not found.");
            return;
        }
        service.printRoomReport();
        int roomNo = InputUtil.readInt("Room Number to assign");
        try {
            service.assignRoom(id, roomNo);
        } catch (RoomFullException e) {
            System.out.println("  [!] " + e.getMessage());
        }
        InputUtil.pressEnterToContinue();
    }

    private void vacateRoom() {
        String id = InputUtil.readString("Student ID to vacate room");
        service.vacateRoom(id);
        InputUtil.pressEnterToContinue();
    }

    // ======================== ROOM ========================

    private void roomMenu() {
        while (true) {
            System.out.println("\n────── ROOM MANAGEMENT ──────");
            System.out.println("  1. Add Room");
            System.out.println("  2. View All Rooms");
            System.out.println("  3. View Available Rooms");
            System.out.println("  4. View Room Occupants");
            System.out.println("  5. Set Room Maintenance");
            System.out.println("  0. Back");
            int choice = InputUtil.readInt("Enter choice");
            switch (choice) {
                case 1 -> addRoom();
                case 2 -> service.printRoomReport();
                case 3 -> viewAvailableRooms();
                case 4 -> viewRoomOccupants();
                case 5 -> setMaintenance();
                case 0 -> { return; }
                default -> System.out.println("  [!] Invalid choice.");
            }
        }
    }

    private void addRoom() {
        System.out.println("\n  --- Add New Room ---");
        int roomNo = InputUtil.readInt("Room Number");
        System.out.println("  Types: 1=SINGLE  2=DOUBLE  3=TRIPLE");
        int typeChoice = InputUtil.readInt("Room Type");
        Room.RoomType type = switch (typeChoice) {
            case 2 -> Room.RoomType.DOUBLE;
            case 3 -> Room.RoomType.TRIPLE;
            default -> Room.RoomType.SINGLE;
        };
        double price = InputUtil.readDouble("Monthly Rent (Rs.)");
        service.addRoom(new Room(roomNo, type, price));
        InputUtil.pressEnterToContinue();
    }

    private void viewAvailableRooms() {
        List<Room> available = service.getAvailableRooms();
        System.out.println("\n  Available Rooms (" + available.size() + "):");
        System.out.println("┌────────┬──────────┬──────────────┬───────────┬────────────┐");
        System.out.println("│  Room  │   Type   │    Status    │ Occupancy │   Price    │");
        System.out.println("├────────┼──────────┼──────────────┼───────────┼────────────┤");
        if (available.isEmpty()) System.out.println("│               No rooms available.                 │");
        else available.forEach(System.out::println);
        System.out.println("└────────┴──────────┴──────────────┴───────────┴────────────┘");
        InputUtil.pressEnterToContinue();
    }

    private void viewRoomOccupants() {
        int roomNo = InputUtil.readInt("Room Number");
        Room room = service.getRoomByNumber(roomNo);
        if (room == null) { System.out.println("  [!] Room not found."); return; }
        System.out.println("\n  Room " + roomNo + " [" + room.getType() + "] - Occupants:");
        if (room.getOccupantIds().isEmpty()) {
            System.out.println("  No occupants.");
        } else {
            for (String sid : room.getOccupantIds()) {
                Student s = service.getStudentById(sid);
                if (s != null) System.out.println("    - " + s.getStudentId() + " | " + s.getName() + " | " + s.getCourse());
            }
        }
        InputUtil.pressEnterToContinue();
    }

    private void setMaintenance() {
        int roomNo = InputUtil.readInt("Room Number");
        Room room = service.getRoomByNumber(roomNo);
        if (room == null) { System.out.println("  [!] Room not found."); return; }
        System.out.println("  Current status: " + room.getStatus());
        boolean maintenance = InputUtil.readYesNo("Set to MAINTENANCE");
        service.setRoomMaintenance(roomNo, maintenance);
        InputUtil.pressEnterToContinue();
    }

    // ======================== FEE ========================

    private void feeMenu() {
        while (true) {
            System.out.println("\n────── FEE MANAGEMENT ──────");
            System.out.println("  1. Add Fee Record");
            System.out.println("  2. View All Fees");
            System.out.println("  3. View Pending Fees");
            System.out.println("  4. View Fees by Student");
            System.out.println("  5. Mark Fee as Paid");
            System.out.println("  0. Back");
            int choice = InputUtil.readInt("Enter choice");
            switch (choice) {
                case 1 -> addFee();
                case 2 -> service.printFeeReport();
                case 3 -> viewPendingFees();
                case 4 -> viewFeesByStudent();
                case 5 -> payFee();
                case 0 -> { return; }
                default -> System.out.println("  [!] Invalid choice.");
            }
        }
    }

    private void addFee() {
        System.out.println("\n  --- Add Fee Record ---");
        String studentId = InputUtil.readString("Student ID");
        if (service.getStudentById(studentId) == null) {
            System.out.println("  [!] Student not found.");
            return;
        }
        double amount  = InputUtil.readDouble("Amount (Rs.)");
        String dueDate = InputUtil.readString("Due Date (YYYY-MM-DD)");
        String desc    = InputUtil.readString("Description (e.g. Monthly Rent, Mess Fee)");
        String feeId   = service.generateFeeId();
        service.addFee(new Fee(feeId, studentId, amount, dueDate, desc));
        InputUtil.pressEnterToContinue();
    }

    private void viewPendingFees() {
        List<Fee> pending = service.getPendingFees();
        System.out.println("\n  Pending Fees (" + pending.size() + "):");
        System.out.println("┌────────────┬──────────────┬───────────┬──────────────┬──────────────┬──────────┐");
        System.out.println("│  Fee ID    │  Student ID  │  Amount   │   Due Date   │  Paid Date   │  Status  │");
        System.out.println("├────────────┼──────────────┼───────────┼──────────────┼──────────────┼──────────┤");
        if (pending.isEmpty()) System.out.println("│                      No pending fees.                               │");
        else pending.forEach(System.out::println);
        System.out.println("└────────────┴──────────────┴───────────┴──────────────┴──────────────┴──────────┘");
        System.out.printf("  Total Pending: Rs.%.0f%n", service.getTotalPendingAmount());
        InputUtil.pressEnterToContinue();
    }

    private void viewFeesByStudent() {
        String id = InputUtil.readString("Student ID");
        List<Fee> feeList = service.getFeesByStudentId(id);
        if (feeList.isEmpty()) {
            System.out.println("  No fee records for: " + id);
        } else {
            System.out.println("┌────────────┬──────────────┬───────────┬──────────────┬──────────────┬──────────┐");
            feeList.forEach(System.out::println);
            System.out.println("└────────────┴──────────────┴───────────┴──────────────┴──────────────┴──────────┘");
        }
        InputUtil.pressEnterToContinue();
    }

    private void payFee() {
        String feeId      = InputUtil.readString("Fee ID to mark as paid");
        String paymentDate= LocalDate.now().toString();
        System.out.println("  Payment date: " + paymentDate);
        boolean confirm = InputUtil.readYesNo("Confirm payment");
        if (confirm) service.payFee(feeId, paymentDate);
        else System.out.println("  Cancelled.");
        InputUtil.pressEnterToContinue();
    }

    // ======================== REPORTS ========================

    private void reportsMenu() {
        while (true) {
            System.out.println("\n────── REPORTS ──────");
            System.out.println("  1. Dashboard Summary");
            System.out.println("  2. All Students Report");
            System.out.println("  3. All Rooms Report");
            System.out.println("  4. All Fees Report");
            System.out.println("  0. Back");
            int choice = InputUtil.readInt("Enter choice");
            switch (choice) {
                case 1 -> service.printDashboard();
                case 2 -> service.printStudentReport();
                case 3 -> service.printRoomReport();
                case 4 -> service.printFeeReport();
                case 0 -> { return; }
                default -> System.out.println("  [!] Invalid choice.");
            }
            if (choice != 0) InputUtil.pressEnterToContinue();
        }
    }

    // ======================== SAVE ========================

    private void saveData() {
        FileHandler.saveStudents(service.getAllStudents());
        FileHandler.saveRooms(service.getAllRooms());
        FileHandler.saveFees(service.getAllFees());
    }
}