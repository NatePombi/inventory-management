package util;

import exceptions.EmptyItemNameException;
import exceptions.InputEmptyException;

import java.util.InputMismatchException;
import java.util.Scanner;

public class UserPrompts {
    private final Scanner scanner;

    public UserPrompts(Scanner scanner) {
        this.scanner = scanner;
    }

    public String userPrompt(String message) {
        System.out.print(message);
        String input = scanner.nextLine().trim();

        if (input.isBlank()) {
            throw new InputEmptyException("Input cannot be empty");
        }

        return input;

    }

    public int userIntegerPrompt(String message) {
        System.out.print(message);
        int num = scanner.nextInt();
        scanner.nextLine();


        if (num <= 0) {
            throw new InputEmptyException("Input must be greater than zero.");
        }

        return num;

    }

    public double userDoublePrompt(String message) {
        System.out.print(message);

        String input = scanner.nextLine();
        try {
            double num = Double.parseDouble(input);
            if (num <= 0) {
                throw new InputEmptyException("Input must be greater than zero.");
            }
            return num;
        } catch (NumberFormatException e) {
            throw new InputMismatchException("Input is not a valid double.");
        }

    }
}
