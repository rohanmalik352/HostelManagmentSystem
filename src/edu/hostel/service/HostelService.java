package edu.hostel.service;

import edu.hostel.domain.Fee;
import edu.hostel.domain.Room;
import edu.hostel.domain.Student;
import edu.hostel.exception.RoomFullException;

import java.util.List;

public interface HostelService {

    // --- Student Operations ---
    void addStudent(Student student);
    Student getStudentById(String studentId);
    List<Student> getAllStudents();
    boolean updateStudent(Student student);
    boolean deleteStudent(String studentId);
    List<Student> searchStudents(String keyword);

    // --- Room Operations ---
    void addRoom(Room room);
    Room getRoomByNumber(int roomNumber);
    List<Room> getAllRooms();
    List<Room> getAvailableRooms();
    boolean assignRoom(String studentId, int roomNumber) throws RoomFullException;
    boolean vacateRoom(String studentId);
    boolean setRoomMaintenance(int roomNumber, boolean maintenance);

    // --- Fee Operations ---
    void addFee(Fee fee);
    List<Fee> getFeesByStudentId(String studentId);
    List<Fee> getAllFees();
    List<Fee> getPendingFees();
    boolean payFee(String feeId, String paymentDate);
    double getTotalPendingAmount();
    double getTotalCollectedAmount();

    // --- Reports ---
    void printStudentReport();
    void printRoomReport();
    void printFeeReport();
    void printDashboard();
}