package main.java.com.airtribe.studentmanagement.util;

import java.time.LocalDate;
import java.util.Scanner;
public class InputValidator {

    public static String readNonEmptyString(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = scanner.nextLine().trim();
            if (!value.isEmpty()) {
                return value;
            }
            System.out.println("Value cannot be empty. Please try again.");
        }
    }

    public static int readIntInRange(Scanner scanner, String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            String raw = scanner.nextLine().trim();
            try {
                int value = Integer.parseInt(raw);
                if (value < min || value > max) {
                    System.out.printf("Please enter a number between %d and %d.%n", min, max);
                } else {
                    return value;
                }
            } catch (NumberFormatException ex) {
                System.out.println("Invalid number. Please try again.");
            }
        }
    }

    public static LocalDate readDate(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt + " (yyyy-MM-dd, leave empty to skip): ");
            String raw = scanner.nextLine().trim();
            if (raw.isEmpty()) {
                return null;
            }
            LocalDate parsed = DateUtil.parseDateOrNull(raw);
            if (parsed != null) {
                return parsed;
            }
            System.out.println("Could not understand that date, please use yyyy-MM-dd.");
        }
    }
}
