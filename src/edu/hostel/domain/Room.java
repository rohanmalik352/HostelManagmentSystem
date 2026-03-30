package edu.hostel.domain;

import java.util.ArrayList;
import java.util.List;

public class Room {
    public enum RoomType { SINGLE, DOUBLE, TRIPLE }
    public enum RoomStatus { AVAILABLE, FULL, MAINTENANCE }

    private int roomNumber;
    private RoomType type;
    private RoomStatus status;
    private double pricePerMonth;
    private List<String> occupantIds; // student IDs

    public Room(int roomNumber, RoomType type, double pricePerMonth) {
        this.roomNumber = roomNumber;
        this.type = type;
        this.pricePerMonth = pricePerMonth;
        this.status = RoomStatus.AVAILABLE;
        this.occupantIds = new ArrayList<>();
    }

    public int getCapacity() {
        switch (type) {
            case SINGLE: return 1;
            case DOUBLE: return 2;
            case TRIPLE: return 3;
            default: return 1;
        }
    }

    public int getAvailableSeats() {
        return getCapacity() - occupantIds.size();
    }

    public boolean isFull() {
        return occupantIds.size() >= getCapacity();
    }

    public boolean addOccupant(String studentId) {
        if (isFull()) return false;
        occupantIds.add(studentId);
        if (isFull()) status = RoomStatus.FULL;
        return true;
    }

    public boolean removeOccupant(String studentId) {
        boolean removed = occupantIds.remove(studentId);
        if (removed && status == RoomStatus.FULL) {
            status = RoomStatus.AVAILABLE;
        }
        return removed;
    }

    // Getters and Setters
    public int getRoomNumber() { return roomNumber; }
    public RoomType getType() { return type; }
    public RoomStatus getStatus() { return status; }
    public void setStatus(RoomStatus status) { this.status = status; }
    public double getPricePerMonth() { return pricePerMonth; }
    public void setPricePerMonth(double price) { this.pricePerMonth = price; }
    public List<String> getOccupantIds() { return occupantIds; }

    public String toCSV() {
        return roomNumber + "," + type + "," + status + "," + pricePerMonth + "," + String.join(";", occupantIds);
    }

    @Override
    public String toString() {
        return String.format("| %-6d | %-8s | %-12s | %-4d/%-4d | Rs.%-8.0f |",
                roomNumber, type, status, occupantIds.size(), getCapacity(), pricePerMonth);
    }
}