package edu.hostel.domain;

public class Student {
    private String studentId;
    private String name;
    private String email;
    private String phone;
    private int roomNumber;
    private String course;
    private String enrollmentDate;

    public Student(String studentId, String name, String email, String phone, String course, String enrollmentDate) {
        this.studentId = studentId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.course = course;
        this.enrollmentDate = enrollmentDate;
        this.roomNumber = -1; // -1 means not assigned
    }

    // Getters and Setters
    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public int getRoomNumber() { return roomNumber; }
    public void setRoomNumber(int roomNumber) { this.roomNumber = roomNumber; }

    public String getCourse() { return course; }
    public void setCourse(String course) { this.course = course; }

    public String getEnrollmentDate() { return enrollmentDate; }
    public void setEnrollmentDate(String enrollmentDate) { this.enrollmentDate = enrollmentDate; }

    public boolean isRoomAssigned() { return roomNumber != -1; }

    public String toCSV() {
        return studentId + "," + name + "," + email + "," + phone + "," + course + "," + enrollmentDate + "," + roomNumber;
    }

    @Override
    public String toString() {
        return String.format("| %-10s | %-20s | %-25s | %-12s | %-15s | %-5s |",
                studentId, name, email, phone, course, roomNumber == -1 ? "None" : String.valueOf(roomNumber));
    }
}