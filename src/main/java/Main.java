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

    // for the digit class
    if (pattern.equals("\\d")) {
        for (int i = 0; i < inputLine.length(); i++) {
            char character = inputLine.charAt(i);
            if (Character.isDigit(character)) {
                return true;
            }
        }
        return false;
    }
    // so i can also do it with the same logic before

//    else if(pattern.equals("//w")){
//        for(int i = 0 ; i < pattern.length() ; i ++){
//            char character = inputLine.charAt(i);
//            if(Character.isAlphabetic(character)){
//                return true ;
//            }
//        }
//        return false ;
//
//    }



    // for the character class so i guess i can do it with regular expression
     else if ("\\w".equals(pattern)) {
        return inputLine.matches(".*\\w.*");

    //  for the positive character group
    }else if (pattern.startsWith("[^") && pattern.endsWith("]")) {
        String subStr2 = pattern.substring(2, pattern.length() - 1);
        for (int i = 0; i < inputLine.length(); i++) {
            char character2 = inputLine.charAt(i);
            if (subStr2.indexOf(character2) == -1) {
                return true;
            }
        }
        return false;


    }else if (pattern.startsWith("[") && pattern.endsWith("]")) {
         String subStr = pattern.substring(1, pattern.length() -1) ;
         for(int i = 0 ; i < inputLine.length() ; i++){
             char character = inputLine.charAt(i);
             if(subStr.indexOf(character) != -1){
                 return true ;
             }
         }
         return false ;

      //  for the negative character group ==> so i can just reverse the logic

    } else if (pattern.length() == 1) {
        return inputLine.contains(pattern) ;

    } else {
      throw new RuntimeException("Unhandled pattern: " + pattern);
    }
  }
}
