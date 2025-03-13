import java.io.IOException;
import java.util.Scanner;

public class Main {
  public static void main(String[] args){
    if (args.length != 2 || !args[0].equals("-E")) {
      System.out.println("Usage: ./your_program.sh -E <pattern>");
      System.exit(1);
    }

    String pattern = args[1];  
    Scanner scanner = new Scanner(System.in);
    String inputLine = scanner.nextLine();

    // You can use print statements as follows for debugging, they'll be visible when running tests.
    System.err.println("Logs from your program will appear here! :) ");


     if (matchPattern(inputLine, pattern)) {
         System.exit(0);
     } else {
         System.exit(1);
     }
  }

  public static boolean matchPattern(String inputLine, String pattern) {
    if (pattern.equals("\\d")) {
        for(int i = 0 ; i < inputLine.length() ; i++){
            char character = inputLine.charAt(i);
            if(Character.isDigit(character)){
                return true ;
            }
        }
        return false ;
    } else if (pattern.length() == 1) {
        return inputLine.contains(pattern) ;

    } else {
      throw new RuntimeException("Unhandled pattern: " + pattern);
    }
  }
}
