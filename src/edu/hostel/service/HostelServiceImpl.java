package edu.hostel.service;

import edu.hostel.domain.Fee;
import edu.hostel.domain.Room;
import edu.hostel.domain.Student;
import edu.hostel.exception.RoomFullException;

import java.util.*;
import java.util.stream.Collectors;

public class HostelServiceImpl implements HostelService {

    private Map<String, Student> students = new LinkedHashMap<>();
    private Map<Integer, Room>   rooms    = new TreeMap<>();
    private Map<String, Fee>     fees     = new LinkedHashMap<>();
    private int feeCounter = 1;

    // ======================= STUDENT =======================

    @Override
    public void addStudent(Student student) {
        if (students.containsKey(student.getStudentId())) {
            System.out.println("  [!] Student ID already exists: " + student.getStudentId());
            return;
        }
        students.put(student.getStudentId(), student);
        System.out.println("  [✓] Student added: " + student.getName());
    }

    @Override
    public Student getStudentById(String studentId) {
        return students.get(studentId);
    }

    @Override
    public List<Student> getAllStudents() {
        return new ArrayList<>(students.values());
    }

    @Override
    public boolean updateStudent(Student student) {
        if (!students.containsKey(student.getStudentId())) return false;
        students.put(student.getStudentId(), student);
        System.out.println("  [✓] Student updated: " + student.getName());
        return true;
    }

    @Override
    public boolean deleteStudent(String studentId) {
        Student s = students.get(studentId);
        if (s == null) return false;
        if (s.isRoomAssigned()) {
            vacateRoom(studentId);
        }
        students.remove(studentId);
        // Remove associated fees
        fees.entrySet().removeIf(e -> e.getValue().getStudentId().equals(studentId));
        System.out.println("  [✓] Student deleted: " + studentId);
        return true;
    }

    @Override
    public List<Student> searchStudents(String keyword) {
        String kw = keyword.toLowerCase();
        return students.values().stream()
                .filter(s -> s.getName().toLowerCase().contains(kw)
                          || s.getStudentId().toLowerCase().contains(kw)
                          || s.getEmail().toLowerCase().contains(kw)
                          || s.getCourse().toLowerCase().contains(kw))
                .collect(Collectors.toList());
    }

    // ======================= ROOM =======================

    @Override
    public void addRoom(Room room) {
        if (rooms.containsKey(room.getRoomNumber())) {
            System.out.println("  [!] Room already exists: " + room.getRoomNumber());
            return;
        }
        rooms.put(room.getRoomNumber(), room);
        System.out.println("  [✓] Room added: " + room.getRoomNumber() + " (" + room.getType() + ")");
    }

    @Override
    public Room getRoomByNumber(int roomNumber) {
        return rooms.get(roomNumber);
    }

    @Override
    public List<Room> getAllRooms() {
        return new ArrayList<>(rooms.values());
    }

    @Override
    public List<Room> getAvailableRooms() {
        return rooms.values().stream()
                .filter(r -> r.getStatus() == Room.RoomStatus.AVAILABLE && !r.isFull())
                .collect(Collectors.toList());
    }

    @Override
    public boolean assignRoom(String studentId, int roomNumber) throws RoomFullException {
        Student student = students.get(studentId);
        if (student == null) {
            System.out.println("  [!] Student not found: " + studentId);
            return false;
        }
        Room room = rooms.get(roomNumber);
        if (room == null) {
            System.out.println("  [!] Room not found: " + roomNumber);
            return false;
        }
        if (room.getStatus() == Room.RoomStatus.MAINTENANCE) {
            System.out.println("  [!] Room " + roomNumber + " is under maintenance.");
            return false;
        }
        if (room.isFull()) {
            throw new RoomFullException(roomNumber);
        }
        // Vacate previous room if any
        if (student.isRoomAssigned()) {
            vacateRoom(studentId);
        }
        room.addOccupant(studentId);
        student.setRoomNumber(roomNumber);
        System.out.println("  [✓] Room " + roomNumber + " assigned to " + student.getName());
        return true;
    }

    @Override
    public boolean vacateRoom(String studentId) {
        Student student = students.get(studentId);
        if (student == null || !student.isRoomAssigned()) return false;
        Room room = rooms.get(student.getRoomNumber());
        if (room != null) {
            room.removeOccupant(studentId);
        }
        student.setRoomNumber(-1);
        System.out.println("  [✓] Room vacated for student: " + studentId);
        return true;
    }

    @Override
    public boolean setRoomMaintenance(int roomNumber, boolean maintenance) {
        Room room = rooms.get(roomNumber);
        if (room == null) return false;
        room.setStatus(maintenance ? Room.RoomStatus.MAINTENANCE : Room.RoomStatus.AVAILABLE);
        System.out.println("  [✓] Room " + roomNumber + " status: " + room.getStatus());
        return true;
    }

    // ======================= FEE =======================

    @Override
    public void addFee(Fee fee) {
        fees.put(fee.getFeeId(), fee);
        System.out.println("  [✓] Fee record added: " + fee.getFeeId());
    }

    public String generateFeeId() {
        return String.format("FEE%04d", feeCounter++);
    }

    @Override
    public List<Fee> getFeesByStudentId(String studentId) {
        return fees.values().stream()
                .filter(f -> f.getStudentId().equals(studentId))
                .collect(Collectors.toList());
    }

    @Override
    public List<Fee> getAllFees() {
        return new ArrayList<>(fees.values());
    }

    @Override
    public List<Fee> getPendingFees() {
        return fees.values().stream()
                .filter(f -> f.getStatus() != Fee.PaymentStatus.PAID)
                .collect(Collectors.toList());
    }

    @Override
    public boolean payFee(String feeId, String paymentDate) {
        Fee fee = fees.get(feeId);
        if (fee == null) {
            System.out.println("  [!] Fee not found: " + feeId);
            return false;
        }
        if (fee.getStatus() == Fee.PaymentStatus.PAID) {
            System.out.println("  [!] Fee already paid: " + feeId);
            return false;
        }
        fee.markAsPaid(paymentDate);
        System.out.println("  [✓] Payment recorded for fee: " + feeId);
        return true;
    }

    @Override
    public double getTotalPendingAmount() {
        return fees.values().stream()
                .filter(f -> f.getStatus() != Fee.PaymentStatus.PAID)
                .mapToDouble(Fee::getAmount).sum();
    }

    @Override
    public double getTotalCollectedAmount() {
        return fees.values().stream()
                .filter(f -> f.getStatus() == Fee.PaymentStatus.PAID)
                .mapToDouble(Fee::getAmount).sum();
    }

    // ======================= REPORTS =======================

    @Override
    public void printDashboard() {
        long totalStudents = students.size();
        long assignedStudents = students.values().stream().filter(Student::isRoomAssigned).count();
        long totalRooms = rooms.size();
        long availableRooms = getAvailableRooms().size();
        long fullRooms = rooms.values().stream().filter(Room::isFull).count();
        long maintenanceRooms = rooms.values().stream().filter(r -> r.getStatus() == Room.RoomStatus.MAINTENANCE).count();
        long pendingFees = getPendingFees().size();

        System.out.println("\n╔══════════════════════════════════════════════════════════╗");
        System.out.println("║              HOSTEL MANAGEMENT DASHBOARD                  ║");
        System.out.println("╠══════════════════════════════════════════════════════════╣");
        System.out.printf("║  Total Students   : %-5d   Assigned to Rooms : %-5d    ║%n", totalStudents, assignedStudents);
        System.out.printf("║  Total Rooms      : %-5d   Available Rooms  : %-5d    ║%n", totalRooms, availableRooms);
        System.out.printf("║  Full Rooms       : %-5d   Under Maintenance: %-5d    ║%n", fullRooms, maintenanceRooms);
        System.out.printf("║  Pending Fees     : %-5d   Amount Pending   : Rs.%-5.0f  ║%n", pendingFees, getTotalPendingAmount());
        System.out.printf("║  Total Collected  : Rs.%-5.0f                             ║%n", getTotalCollectedAmount());
        System.out.println("╚══════════════════════════════════════════════════════════╝");
    }

    @Override
    public void printStudentReport() {
        System.out.println("\n┌────────────┬──────────────────────┬───────────────────────────┬──────────────┬─────────────────┬───────┐");
        System.out.println("│ Student ID │ Name                 │ Email                     │ Phone        │ Course          │ Room  │");
        System.out.println("├────────────┼──────────────────────┼───────────────────────────┼──────────────┼─────────────────┼───────┤");
        if (students.isEmpty()) {
            System.out.println("│                          No students found.                                                        │");
        } else {
            students.values().forEach(System.out::println);
        }
        System.out.println("└────────────┴──────────────────────┴───────────────────────────┴──────────────┴─────────────────┴───────┘");
        System.out.println("  Total Students: " + students.size());
    }

    @Override
    public void printRoomReport() {
        System.out.println("\n┌────────┬──────────┬──────────────┬───────────┬────────────┐");
        System.out.println("│  Room  │   Type   │    Status    │ Occupancy │   Price    │");
        System.out.println("├────────┼──────────┼──────────────┼───────────┼────────────┤");
        if (rooms.isEmpty()) {
            System.out.println("│                 No rooms found.                          │");
        } else {
            rooms.values().forEach(System.out::println);
        }
        System.out.println("└────────┴──────────┴──────────────┴───────────┴────────────┘");
        System.out.println("  Total Rooms: " + rooms.size() + "  |  Available: " + getAvailableRooms().size());
    }

    @Override
    public void printFeeReport() {
        System.out.println("\n┌────────────┬──────────────┬───────────┬──────────────┬──────────────┬──────────┐");
        System.out.println("│  Fee ID    │  Student ID  │  Amount   │   Due Date   │  Paid Date   │  Status  │");
        System.out.println("├────────────┼──────────────┼───────────┼──────────────┼──────────────┼──────────┤");
        if (fees.isEmpty()) {
            System.out.println("│                         No fee records found.                               │");
        } else {
            fees.values().forEach(System.out::println);
        }
        System.out.println("└────────────┴──────────────┴───────────┴──────────────┴──────────────┴──────────┘");
        System.out.printf("  Total Collected: Rs.%.0f  |  Total Pending: Rs.%.0f%n",
                getTotalCollectedAmount(), getTotalPendingAmount());
    }
}