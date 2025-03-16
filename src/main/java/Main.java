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
                    char nextPatternChar = pattern.charAt(patternIndex);
                    if (nextPatternChar == 'd') {
                        if (Character.isDigit(inputLine.charAt(inputIndex))) {
                            inputIndex ++ ;
                        }
                        else {
                            return false ;
                        }
                    } else if (nextPatternChar == 'w') {
                        if (Character.isDigit(inputLine.charAt(inputIndex))){
                            inputIndex ++ ;
                        }
                        else {
                            return false ;
                        }
                    }else {
                        throw new RuntimeException("Unhandled escape sequence: \\" + nextPatternChar);
                    }
                }else{
                    return false ;
                }
            } else if (patternChar == '[') {
                int closingBracketIndex = pattern.indexOf(']', patternIndex + 1);
                if (closingBracketIndex == -1) {
                    throw new RuntimeException("Unterminated character group");
                }

                String group = pattern.substring(patternIndex + 1 , closingBracketIndex) ;
                boolean negate = false ;
                if(group.startsWith("^")){
                    negate = true ;
                    group = group.substring(1);
                }

                boolean matchFound = false ;
                if (negate) {
                    if (group.indexOf(inputLine.charAt(inputIndex)) == -1){
                        matchFound =  true;
                    }
                }else {
                    if (group.indexOf(inputLine.charAt(inputIndex)) != -1){
                        matchFound = true;
                    }
                }

                if (matchFound){
                    inputIndex ++ ;
                }
                else {
                    return false ;
                }

                patternIndex = closingBracketIndex ;
            }
            else {
                if (patternChar == inputLine.charAt(inputIndex)){
                    inputIndex ++ ;
                }
                else {
                    return false ;
                }
            }

            patternIndex ++ ;
        }

        if (patternIndex != pattern.length() || inputIndex != inputLine.length()){
            return false ;
        }

        return true ;

    }
}
