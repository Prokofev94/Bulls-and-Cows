package bullscows;

import java.math.BigInteger;
import java.util.*;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final List<Character> availableSymbols = new ArrayList<>
         (Arrays.asList('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h',
                    'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'));
    private static StringBuilder number = new StringBuilder();

    public static void main(String[] args) {
        startGame();
    }

    public static void startGame() {
        System.out.println("Input the length of the secret code:");
        String input = scanner.nextLine();
        int length = 0;
        try {
            length = Integer.parseInt(input);
        } catch (NumberFormatException exc) {
            System.out.printf("Error: \"%s\" isn't a valid number.", input);
            return;
        }
        System.out.println("Input the number of possible symbols in the code:");
        int range = Integer.parseInt(scanner.nextLine());
        if (range > 36) {
            System.out.println("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).");
        } else if (length < 1 || range < length) {
            System.out.printf("Error: it's not possible to generate a code with a length of %d with %d unique symbols.", length, range);
        } else {
            createNumber(length, range);
        }
    }

    public static void createNumber(int length, int range) {
        if (length > 36) {
            System.out.print("Error: can't generate a secret number with a length of ");
            System.out.printf("%d because there aren't enough unique digits.", length);
        } else {
            List<Character> rangeSymbol = new ArrayList<>(availableSymbols.subList(0, range));
            System.out.print("The secret is prepared: ");
            for (int i = 0; i < length; i++) {
                System.out.print("*");
            }
            BigInteger r = new BigInteger(String.valueOf(range - 1));
            System.out.printf(" (0-%d%s).\n", range > 9 ? 9 : range - 1, range > 9 ? String.format(", a-%s", r.toString(36)) : "");
            while (rangeSymbol.get(0) == 0) {
                Collections.shuffle(rangeSymbol);
            }
            for (Character digit : rangeSymbol) {
                number.append(digit);
            }
            number = new StringBuilder(number.substring(0, length));
            System.out.println("Okay, let's start a game!");
            nextTurn(number);
        }
    }

    public static void nextTurn(StringBuilder number) {
        int len = number.length();
        int turn = 1;
        int bulls;
        int cows;
        while (true) {
            System.out.printf("Turn %d.\n", turn);
            String answer = scanner.nextLine();
            bulls = 0;
            cows = 0;
            for (int i = 0; i < len; i++) {
                if (number.charAt(i) == answer.charAt(i)) {
                    bulls++;
                }
                char ch = answer.charAt(i);
                for (int j = 0; j < len; j++) {
                    if (number.charAt(j) == ch) {
                        cows++;
                    }
                }
            }
            cows -= bulls;
            String grade = "Grade: ";
            if (bulls + cows == 0) {
                grade += "None";
            } else {
                if (bulls > 0) {
                    grade += bulls + (bulls == 1 ? " bull" : " bulls");
                    if (cows > 0) {
                        grade += " and " + cows + (cows == 1 ? " cow" : " cows");
                    }
                } else {
                    grade += cows + (cows == 1 ? " cow" : " cows");
                }
            }
            System.out.println(grade);
            if (bulls == len) {
                System.out.println("Congratulations! You guessed the secret code.");
                break;
            }
            turn++;
        }
    }
}