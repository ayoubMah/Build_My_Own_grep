import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        if (args.length != 2 || !args[0].equals("-E")) {
            System.out.println("Usage: ./your_program.sh -E <pattern>");
            System.exit(1);
        }

        String pattern = args[1];
        Scanner scanner = new Scanner(System.in);
        String inputLine = scanner.nextLine();

        System.err.println("Logs from your program will appear here! :) ");


        if (matchPattern(inputLine, pattern)) {
            System.exit(0);
        } else {
            System.exit(1);
        }
    }


    public static boolean matchPattern(String inputLine, String pattern) {
        if (pattern.length() == 1) {
            return inputLine.contains(pattern);
        } else {
            for (int i = 0; i < inputLine.length(); i++) {
                if (matchPattern(inputLine, pattern, i)) {
                    return true;
                }
            }
        }
        return false;
    }
    private static boolean checkDigit(String inputLine, int index) {
        return inputLine.charAt(index) > '0' && inputLine.charAt(index) < '9';
    }
    private static boolean checkAlphaNumeric(String inputLine, int index) {
        return Character.isLetterOrDigit(inputLine.charAt(index));
    }
    private static boolean checkPositiveChar(String inputLine, int index,
                                             String charGroup) {
        return charGroup.contains(inputLine.substring(index, index + 1));
    }
    private static boolean checkNegativeChar(String inputLine, int index,
                                             String charGroup) {
        return !charGroup.contains(inputLine.substring(index, index + 1));
    }
    public static boolean matchPattern(String inputLine, String pattern,
                                       int index) {
        int inputLinePtr = index, patternPtr = 0;
        while (inputLinePtr < inputLine.length()) {
            boolean matched = false;
            String patternRegex = getRegex(pattern, patternPtr);
            patternPtr += patternRegex.length();
            if (patternRegex.equals("\\d")) {
                matched = checkDigit(inputLine, inputLinePtr);
            } else if (patternRegex.equals("\\w")) {
                matched = checkAlphaNumeric(inputLine, inputLinePtr);
            } else if (patternRegex.contains("[^") && patternRegex.contains("]")) {
                matched = checkNegativeChar(
                        inputLine, inputLinePtr,
                        patternRegex.substring(2, patternRegex.length() - 1));
            } else if (patternRegex.contains("[") && patternRegex.contains("]")) {
                matched = checkPositiveChar(
                        inputLine, inputLinePtr,
                        patternRegex.substring(1, patternRegex.length() - 1));
            } else if (patternRegex.length() == 1) {
                matched = inputLine.charAt(inputLinePtr) == patternRegex.charAt(0);
            } else {
                throw new RuntimeException("Unhandled pattern: " + pattern);
            }
            if (!matched) {
                return false;
            }
            if (patternPtr == pattern.length()) {
                return true;
            }
            inputLinePtr++;
        }
        return false;
    }
    private static String getRegex(String pattern, int index) {
        if (pattern.charAt(index) == '\\' && index < pattern.length() - 1) {
            return pattern.substring(index, index + 2);
        } else if (pattern.charAt(index) == '[' && index < pattern.length() - 2) {
            return pattern.substring(index, pattern.indexOf("]", index) + 1);
        } else {
            return pattern.substring(index, index + 1);
        }
    }
}

