package com.mthree.aspire.flooringmastery.ui;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import org.springframework.stereotype.Component;

/**
 *
 * @author barin
 */
@Component
public class UserIoConsoleImpl implements UserIo {

    private final Scanner sc = new Scanner(System.in);

    @Override
    public void print(String message) {
        System.out.println(message);
    }

    @Override
    public String readString(String prompt) {
        System.out.println(prompt);
        String input = "";
        boolean isValid = false;
        while (!isValid) {
            input = sc.nextLine();
            if (input.trim().length() == 0) {
                System.out.println("Your input cannot be blank.");
            } else {
                isValid = true;
            }
        }
        return input;
    }

    @Override
    public int readInt(String prompt) {
        System.out.println(prompt);
        String stringInput = "";
        int input = 0;
        boolean isValid = false;
        while (!isValid) {
            try {
                stringInput = sc.nextLine();
                input = Integer.parseInt(stringInput);
                isValid = true;
            } catch (NumberFormatException e) {
                System.out.println("Make sure you enter an integer!");
            }
        }
        return input;
    }

    @Override
    public int readInt(String prompt, int min, int max) {
        System.out.println(prompt);
        String stringInput = "";
        int input = 0;
        boolean isValid = false;
        while (!isValid) {
            try {
                stringInput = sc.nextLine();
                input = Integer.parseInt(stringInput);
                if (input >= min && input <= max) {
                    isValid = true;
                } else {
                    System.out.println("Please make sure the integer entered "
                            + "is between " + min + " and " + max + "!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Make sure you enter an integer!");
            }
        }
        return input;
    }

    @Override
    public BigDecimal readBigDecimal(String prompt, BigDecimal min) {
        System.out.println(prompt);
        BigDecimal input = BigDecimal.ZERO;
        boolean isValid = false;
        while (!isValid) {
            try {
                input = new BigDecimal(sc.nextLine());
                if (input.compareTo(min) == -1) {
                    System.out.println("Please ensure you the number entered is"
                            + " at least " + min + ".");
                } else {
                    isValid = true;
                }
            } catch (NumberFormatException e) {
                System.out.println("Make sure you enter a number!\n");
            }
        }
        return input;
    }

    @Override
    public LocalDate readLocalDate() {
        System.out.println("Please enter a date in the form MM-dd-yyyy: ");
        String dateAsString = "";
        LocalDate ld = null;
        boolean isValid = false;
        while (!isValid) {
            try {
                dateAsString = sc.nextLine();
                DateTimeFormatter f = DateTimeFormatter.ofPattern("MM-dd-yyyy");
                ld = LocalDate.parse(dateAsString, f);
                isValid = true;
            } catch (DateTimeParseException e) {
                System.out.println("Please ensure you enter a date in the form"
                        + " MM-dd-yyyy.");
            }
        }
        return ld;
    }

    @Override
    public LocalDate readLocalDateInFuture() {
        System.out.println("Please enter a date from today onwards: ");
        String dateAsString = "";
        LocalDate ld = null;
        boolean isValid = false;
        while (!isValid) {
            try {
                dateAsString = sc.nextLine();
                DateTimeFormatter f = DateTimeFormatter.ofPattern("MM-dd-yyyy");
                ld = LocalDate.parse(dateAsString, f);
                if (ld.isBefore(LocalDate.now())) {
                    System.out.println("Please ensure the date you enter is "
                            + "today or after: ");
                } else {
                    isValid = true;
                }
            } catch (DateTimeParseException e) {
                System.out.println("Please ensure you enter a date in the form"
                        + " MM-dd-yyyy.");
            }
        }
        return ld;
    }

}
