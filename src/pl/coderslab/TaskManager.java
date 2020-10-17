package pl.coderslab;

import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.nio.file.Path;
import java.nio.file.Paths;


public class TaskManager {

    public static void main(String[] args) {
        String pathName = "./tasks.csv";
        String[] options = {"add", "remove", "list", "exit"};
        List<String> lines = tasks(pathName);
        while (true) {
            optionSelected(options, pathName, lines);
        }
    }

    public static void optionsToSelect(String[] options) {
        System.out.println("_   _   _   _   _   _   _   _   _   _   _   _   _   _   _   _   _   _   _   _   _   _   _   _   \n" + ConsoleColors.BLUE + "Please select an option:" + ConsoleColors.RESET);
        for (String s : options) {
            System.out.println(s);
        }
        System.out.print(": ");
    }

    public static void optionSelected(String[] options, String pathName, List<String> lines) {
        Scanner scanner = new Scanner(System.in);
        optionsToSelect(options);


        switch (scanner.next()) {
            case "add":
                add(lines);
                break;
            case "remove":
                remove(lines);
                break;
            case "list":
                list(pathName, lines);
                break;
            case "exit":
                exit(pathName, lines);
                break;
            default:
                System.out.println("Wrong function! Choose a correct function.");
        }
    }

    public static List<String> add(List<String> lines) {
        Scanner scanner = new Scanner(System.in);

        //pobieranie danych do zapisu
        System.out.println("Please add task description");
        String taskDescription = scanner.nextLine().trim() + ", ";
        System.out.println("Please add task due date. Date format: year-month-day");
        String dueDate = dueDate();// scanner.nextLine().trim() + ", ";
        System.out.println("Is your task important: true/false");
        boolean importance = false;
        while (scanner.hasNext()) {
            if (scanner.hasNextBoolean()) {
                importance = scanner.nextBoolean();
                break;
            } else if (scanner.hasNext()) {
                System.out.println("true/false");
                scanner.next();
            }
        }
        String impotranceString = Boolean.toString(importance);


        lines.add(taskDescription + dueDate + impotranceString);
        return lines;
    }

    public static void remove(List<String> lines) {
        Scanner scanner = new Scanner(System.in);
        int lineNumber = 0;
        System.out.println("Please select line number to remove");
        while (scanner.hasNextInt()) {
            lineNumber = scanner.nextInt();
            if (lineNumber < 0 || lineNumber > lines.size() - 1) {
                System.out.println("Incorrect argument passed. Please give number greater or equal 0");
            } else {
                System.out.println("Line to delete: " + lineNumber + "\nAre you certain? Yes/No");
                while (scanner.hasNext()) {
                    String confirm = scanner.next();
                    if (confirm.equalsIgnoreCase("no")) {
                        return;
                    } else if (confirm.equalsIgnoreCase("yes")) {
                        break;
                    } else {
                        System.out.println("Yes/No");
                    }
                }
                break;
            }
        }

        lines.remove(lineNumber);
        System.out.println("Value was successfully deleted");
    }

    public static void list(String pathName, List<String> lines) {
        System.out.println("_   _   _   _   _   _   _   _   _   _   _   _   _   _   _   _   _   _   _   _   _   _   _   _   \n");
        for (int i = 0; i < lines.size(); i++) {
            //wersja String
            System.out.print(i + " : ");
            String taskLine = lines.get(i);
            String[] taskLineSplitted = StringUtils.split(taskLine, ",");
            for (String elements : taskLineSplitted) {
                System.out.print(elements + "  ");
            }
            System.out.println();
            //wersja List
            // System.out.println(i + " : " + lines.get(i));
        }
    }

    public static void exit(String pathName, List<String> linesToSave) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Are you sure you want to exit?\n Yes/No");
        String yesNo = scanner.next();
        if (yesNo.equalsIgnoreCase("Yes")) {
            saveToFile(pathName, linesToSave);
            System.out.println(ConsoleColors.RED + "Bye, bye" + ConsoleColors.RESET);
            System.exit(0);
        } else if (yesNo.equalsIgnoreCase("No")) {
            System.out.println("Good!");
        } else {
            System.out.println("Yes/No");
        }
    }

    public static List<String> tasks(String pathName) {

        Path pathFile = Paths.get(pathName); //ładowanie pliku
        List<String> lines = new ArrayList<>();
        if (!Files.exists(pathFile)) {
            System.out.println("Plik nie istnieje!");
            System.exit(0);
        }
        try {
            for (String line : Files.readAllLines(pathFile)) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(lines);
        return lines;

    }

    public static void saveToFile(String pathName, List<String> dataToSave) {
        Path pathFile = Paths.get(pathName); //ładowanie pliku
        if (!Files.exists(pathFile)) {
            System.out.println("Plik nie istnieje!");
            System.exit(0);
        }
        try {
            Files.write(pathFile, dataToSave);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String dueDate() {
        String result;
        System.out.println("Year (2020-2099): ");
        int year = checkNumber(2020, 2099);
        System.out.println("Month (1-12): ");
        int month = checkNumber(1, 12);
        System.out.println("Day (1-31): ");
        int day = checkNumber(1,31);
        result = year + "-" + month + "-" + day + ", ";
        return result;
    }

    public static int checkNumber(int min, int max){
        Scanner scanner = new Scanner(System.in);
        int numberCheked = 0;
        while (scanner.hasNextInt()) {
            numberCheked = scanner.nextInt();
            if (numberCheked >= min && numberCheked <= max){
                break;
            } else {
                System.out.println("Number must be between " + min + "-" + max);
                scanner.hasNext();
            }
        }
        return numberCheked;
    }
}

