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

        int patternIndex = 0;
        int inputIndex = 0;

        while (patternIndex < pattern.length() && inputIndex < inputLine.length()) {

            char patternChar = pattern.charAt(patternIndex);

            if (patternChar == '\\') { // if our pattern start with a backslash \
                // handle special character \d  \w ...
                patternIndex++;
                if (patternIndex < pattern.length()) {
                    char nextPattern = pattern.charAt(patternIndex);
                    if (nextPattern == 'd') {
                        return inputLine.matches(".*\\d.*");
                    } else if (nextPattern == 'w') {
                        return inputLine.matches(".*\\w.*");
                    }
                }
            } else if (patternChar == '[') {
                // handle special characters groups, positive and negative
                patternIndex++;

                if (patternIndex < pattern.length()) {
                    char nextChar = pattern.charAt(inputIndex);
                    if (nextChar == '^') {
                        String subString = pattern.substring(2, (pattern.length() - 1));
                        for (int i = 0; i < inputLine.length(); i++) {
                            char character = inputLine.charAt(i);
                            if (subString.indexOf(character) == -1) {
                                return true;
                            }
                        }
                        return false;
                    } else {
                        String subStr = pattern.substring(1, pattern.length() - 1);
                        for (int i = 0; i < inputLine.length(); i++) {
                            char character = inputLine.charAt(i);
                            if (subStr.indexOf(character) != -1) {
                                return true;
                            }
                        }
                        return false;
                    }

                }
            } else if (pattern.length() == 1) {
                return inputLine.contains(pattern);

            } else {
                throw new RuntimeException("Unhandled pattern: " + pattern);
            }


            patternIndex++;
        }

        return patternIndex == pattern.length() && inputIndex == inputLine.length();


    }
}
