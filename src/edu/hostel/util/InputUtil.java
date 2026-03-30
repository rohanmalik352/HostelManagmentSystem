package edu.hostel.util;

import java.util.Scanner;

public class InputUtil {
    private static final Scanner scanner = new Scanner(System.in);

    public static String readString(String prompt) {
        System.out.print("  " + prompt + ": ");
        return scanner.nextLine().trim();
    }

    public static int readInt(String prompt) {
        while (true) {
            try {
                System.out.print("  " + prompt + ": ");
                int val = Integer.parseInt(scanner.nextLine().trim());
                return val;
            } catch (NumberFormatException e) {
                System.out.println("  [!] Please enter a valid integer.");
            }
        }
    }

    public static double readDouble(String prompt) {
        while (true) {
            try {
                System.out.print("  " + prompt + ": ");
                double val = Double.parseDouble(scanner.nextLine().trim());
                return val;
            } catch (NumberFormatException e) {
                System.out.println("  [!] Please enter a valid number.");
            }
        }
    }

    public static boolean readYesNo(String prompt) {
        while (true) {
            System.out.print("  " + prompt + " (y/n): ");
            String input = scanner.nextLine().trim().toLowerCase();
            if (input.equals("y") || input.equals("yes")) return true;
            if (input.equals("n") || input.equals("no")) return false;
            System.out.println("  [!] Please enter y or n.");
        }
    }

    public static void pressEnterToContinue() {
        System.out.print("\n  Press ENTER to continue...");
        scanner.nextLine();
    }

    public static void close() {
        scanner.close();
    }
}