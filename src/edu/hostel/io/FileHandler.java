package edu.hostel.io;

import edu.hostel.domain.Fee;
import edu.hostel.domain.Room;
import edu.hostel.domain.Student;
import edu.hostel.service.HostelServiceImpl;

import java.io.*;
import java.util.List;

public class FileHandler {

    private static final String DATA_DIR     = "data/";
    private static final String STUDENTS_FILE = DATA_DIR + "students.csv";
    private static final String ROOMS_FILE    = DATA_DIR + "rooms.csv";
    private static final String FEES_FILE     = DATA_DIR + "fees.csv";

    public static void saveStudents(List<Student> students) {
        ensureDir();
        try (PrintWriter pw = new PrintWriter(new FileWriter(STUDENTS_FILE))) {
            pw.println("studentId,name,email,phone,course,enrollmentDate,roomNumber");
            for (Student s : students) pw.println(s.toCSV());
            System.out.println("  [✓] Students saved to " + STUDENTS_FILE);
        } catch (IOException e) {
            System.out.println("  [!] Error saving students: " + e.getMessage());
        }
    }

    public static void loadStudents(HostelServiceImpl service) {
        File f = new File(STUDENTS_FILE);
        if (!f.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line = br.readLine(); // header
            while ((line = br.readLine()) != null) {
                String[] p = line.split(",", -1);
                if (p.length < 7) continue;
                Student s = new Student(p[0], p[1], p[2], p[3], p[4], p[5]);
                int room = Integer.parseInt(p[6]);
                if (room != -1) s.setRoomNumber(room);
                service.addStudent(s);
            }
            System.out.println("  [✓] Students loaded from file.");
        } catch (IOException | NumberFormatException e) {
            System.out.println("  [!] Error loading students: " + e.getMessage());
        }
    }

    public static void saveRooms(List<Room> rooms) {
        ensureDir();
        try (PrintWriter pw = new PrintWriter(new FileWriter(ROOMS_FILE))) {
            pw.println("roomNumber,type,status,price,occupants");
            for (Room r : rooms) pw.println(r.toCSV());
            System.out.println("  [✓] Rooms saved to " + ROOMS_FILE);
        } catch (IOException e) {
            System.out.println("  [!] Error saving rooms: " + e.getMessage());
        }
    }

    public static void loadRooms(HostelServiceImpl service) {
        File f = new File(ROOMS_FILE);
        if (!f.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line = br.readLine(); // header
            while ((line = br.readLine()) != null) {
                String[] p = line.split(",", -1);
                if (p.length < 4) continue;
                Room r = new Room(Integer.parseInt(p[0]),
                        Room.RoomType.valueOf(p[1]),
                        Double.parseDouble(p[3]));
                r.setStatus(Room.RoomStatus.valueOf(p[2]));
                if (p.length > 4 && !p[4].isEmpty()) {
                    for (String id : p[4].split(";")) {
                        if (!id.isBlank()) r.addOccupant(id);
                    }
                }
                service.addRoom(r);
            }
            System.out.println("  [✓] Rooms loaded from file.");
        } catch (IOException | IllegalArgumentException e) {
            System.out.println("  [!] Error loading rooms: " + e.getMessage());
        }
    }

    public static void saveFees(List<Fee> fees) {
        ensureDir();
        try (PrintWriter pw = new PrintWriter(new FileWriter(FEES_FILE))) {
            pw.println("feeId,studentId,amount,dueDate,paymentDate,status,description");
            for (Fee fee : fees) pw.println(fee.toCSV());
            System.out.println("  [✓] Fees saved to " + FEES_FILE);
        } catch (IOException e) {
            System.out.println("  [!] Error saving fees: " + e.getMessage());
        }
    }

    public static void loadFees(HostelServiceImpl service) {
        File f = new File(FEES_FILE);
        if (!f.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line = br.readLine(); // header
            while ((line = br.readLine()) != null) {
                String[] p = line.split(",", -1);
                if (p.length < 7) continue;
                Fee fee = new Fee(p[0], p[1], Double.parseDouble(p[2]), p[3], p[6]);
                fee.setStatus(Fee.PaymentStatus.valueOf(p[5]));
                if (!p[4].equals("N/A")) fee.markAsPaid(p[4]);
                service.addFee(fee);
            }
            System.out.println("  [✓] Fees loaded from file.");
        } catch (IOException | IllegalArgumentException e) {
            System.out.println("  [!] Error loading fees: " + e.getMessage());
        }
    }

    private static void ensureDir() {
        File dir = new File(DATA_DIR);
        if (!dir.exists()) dir.mkdirs();
    }
}
