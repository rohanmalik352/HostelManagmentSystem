package edu.hostel.exception;

public class RoomFullException extends Exception {
    private int roomNumber;

    public RoomFullException(int roomNumber) {
        super("Room " + roomNumber + " is full. No vacancy available.");
        this.roomNumber = roomNumber;
    }

    public RoomFullException(String message) {
        super(message);
    }

    public int getRoomNumber() {
        return roomNumber;
    }
}