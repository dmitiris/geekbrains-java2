import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// 1 Обязательно есть хоть 1 цифра
// 2 Не менее 8 символов и не более 20
// 3 Должны быть большие и маленькие буквы
// 4 Обязательно дожен быть спец символ

public class RegWord {
    public static void main(String[] args) {

        testRegWord();

        Scanner myObj = new Scanner(System.in);
        System.out.println("Enter password:");

        String newPassword = myObj.nextLine();
        if (checkRegWord(newPassword)) {
            System.out.println("Your password is valid");  // Output user input
        } else {
            System.out.println("Your password is not valid");
        }
    }

    private static boolean checkRegWord(String str){
        Pattern p = Pattern.compile("((?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[\\W]).{8,20})");
        Matcher m = p.matcher(str);

        return m.matches();
    }

    private static void testRegWord(){

        int counter = 0;
        System.out.println(++counter + " - (exp:fail) - " + myPrint(checkRegWord("aB3!")));  // too short
        System.out.println(++counter + " - (exp:fail) - " + myPrint(checkRegWord("1@Aabcdefghijklmnopqrst"))); // too long

        System.out.println(++counter + " - (exp:fail) - " + myPrint(checkRegWord("abcdefghijklm"))); // only small case
        System.out.println(++counter + " - (exp:fail) - " + myPrint(checkRegWord("84374887837483")));  // only numbers
        System.out.println(++counter + " - (exp:fail) - " + myPrint(checkRegWord("$#$#@!&$^#*")));  // only special symbols
        System.out.println(++counter + " - (exp:fail) - " + myPrint(checkRegWord("ABCDEFGHIJK")));  // only upper case

        System.out.println(++counter + " - (exp:fail) - " + myPrint(checkRegWord("abcdef123456"))); // lower case and numbers
        System.out.println(++counter + " - (exp:fail) - " + myPrint(    checkRegWord("abcdef#%#^@&"))); // lower case and special symbols
        System.out.println(++counter + " - (exp:fail) - " + myPrint(checkRegWord("abcdeFGHIJK"))); // lower case and upper case
        System.out.println(++counter + " - (exp:fail) - " + myPrint(checkRegWord("A1BC23DEF456"))); // upper case and numbers
        System.out.println(++counter + " - (exp:fail) - " + myPrint(checkRegWord("A#BC%#DEF^@&"))); // upper case and special symbols
        System.out.println(++counter + " - (exp:fail) - " + myPrint(checkRegWord("1#2%3#4^5@6&"))); // upper case and special symbols

        System.out.println(++counter + " - (exp:fail) - " + myPrint(checkRegWord("1!a2@b3#c4$d"))); // no upper case
        System.out.println(++counter + " - (exp:fail) - " + myPrint(checkRegWord("1!A2@B3#C4$D"))); // no lower case
        System.out.println(++counter + " - (exp:fail) - " + myPrint(checkRegWord("1aA2bB3cC4dD"))); // no special symbols
        System.out.println(++counter + " - (exp:fail) - " + myPrint(checkRegWord("a!Ab@Bc#Cd$D"))); // no numbers

        System.out.println(++counter + " - (exp:fail) - " + myPrint(checkRegWord("abcDef8hi^klm")));
    }

    private static String myPrint(boolean testPassed){
        String str;
        if (testPassed) {
            str = "ok";
        } else {
            str = "fail";
        }
        return str;
    }

}
