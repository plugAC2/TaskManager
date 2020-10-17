package pl.coderslab;

import java.util.Scanner;

public class CheckNumber {
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
