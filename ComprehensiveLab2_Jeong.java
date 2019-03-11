/*********************************************************************
 * CHALLENGE LAB ON methods, repetition with loops and recursion,
 * arrays (1D and 2D)
 * 
 * Instructor:  Villanueva
 * Challenge Lab 1
 * Modified and submitted by: [Christina Jeong]
 *
 *********************************************************************/
import java.io.*;
import java.util.*;
import java.util.Scanner;

public class ComprehensiveLab2_Jeong {
 
    /************* ACTIVITY 1 ****************************************
     * Implement method Luhn
     * input: an integer (usually 16-digit integer if it checks the 
     *          validity of a credit card)
     * output: a boolean value -- true if the (credit card) number is
     *          valid, false otherwise
     ******************************************************************/
    public static boolean Luhn(long creditCard) {
        //calls sumOfEvenDoubles method and sumOfOdds method and adds the two results together
        //if mod 10 equals zero, it will return true
        return ((sumOfEvenDoubles(creditCard) +  
               sumOfOdds(creditCard)) % 10 == 0);
        
    }
    
    public static int sumOfEvenDoubles(long creditCard) {
        //this method will calculate the sum of the evens place integers after they’ve been doubled and after the doubled integers with two places have been added
        int sum = 0;
        String cardNum = creditCard + "";
        
        for (int i = 0; i < cardNum.length(); i += 2){ //start at 0 for evens
            sum += doubles(Integer.parseInt(cardNum.charAt(i) + "") * 2);
        }
        return sum;
    }

     public static int doubles(int evenDoublesCheck) { 
        //this method will double the evens place integers and add them together if the result results in an integer greater than 9
        if (evenDoublesCheck < 9) 
           return evenDoublesCheck; 
        return ((evenDoublesCheck / 10) + (evenDoublesCheck % 10)); 
    } 
        
    public static int sumOfOdds(long creditCard){
        //method will calculate the sum of the odds place integers 
        int sum2 = 0;
        String cardNum = creditCard + "";
        
        for (int i = 1; i < cardNum.length(); i += 2){ //start at 1 for odds
            sum2 += Integer.parseInt(cardNum.charAt(i) + "");
        }
        return sum2;
    }
    
    
    /************* ACTIVITY 2 ****************************************
     * Implement method checkCustomers
     * input: a 2D array of Strings (n lines and 2 columns; each line 
     *          contains a name and a credit card number)
     * output: nothing but it creates and writes in a file, where it
     *          it stores the names and corresponding credit card numbers
     *          of customers with fake credit card numbers
     * You should implement two such methods:
     *      1/ an iterative version: checkCustomersIter
     *      2/ a recursive version: checkCustomerRec
     ******************************************************************/
    public static void checkCustomersIter() throws IOException{

        FileInputStream fstream = new FileInputStream("ccandnamesExamples.txt");
        //Reading from this file
        DataInputStream dins = new DataInputStream(fstream);

        BufferedReader br = new BufferedReader(new InputStreamReader(dins));
  
        String strLine; //initializing variable to store each line
        int arraySize = 11; //number of rows to be read
        
        String customersInfo[][] = new String[arraySize][]; //creating space for 2D array
        int index = 0;
                
        while ((strLine = br.readLine()) != null) { //while line isn't empty
          customersInfo[index] = strLine.split(" ");
          index++;
        }

        StringBuilder builder = new StringBuilder();
        int j = 0;
        for (int i = 0; i < customersInfo.length; i++) { //for each row
          if (customersInfo[i] != null) {
            for (j = 0; j < customersInfo[i].length; j++) { //for each column
              if (Luhn(Long.parseLong(customersInfo[i][2])) == false){
                builder.append("Iterative: " + customersInfo[i][j]+"");//append to the output string
                  if(j < customersInfo.length - 1){//if this is not the last row element
                    builder.append(" ");//then add spaces
                  }
              }
            }
            builder.append("\n");//append new line at the end of the row
          }
        }
        
        BufferedWriter writer = new BufferedWriter(new   FileWriter("customers_error_Jeong.txt")); //
        //Creating a file of that name to write to
        writer.write(builder.toString());//save the string representation of the array
        
        writer.close();
    }

    /*----------------------------------------------------------------*/
    //public static void checkCustomersRec(String[][] customersInfo) {
     
        // your code goes here        
    //}
    
    /************* BONUS ACTIVITY **************************************
     * Implement method generateCCard
     * input: nothing
     * output: a credit card number randomly generated but valid 
     ******************************************************************/
     public static String generateCCard() {
         
        // your code goes here
        long leftLimit = 1000000000000000L; //smallest 16 digit number
        long rightLimit = 9999999999999999L; //largest 16 digit number
         
        long generatedLong = leftLimit + (long) (Math.random() * (rightLimit - leftLimit)); //generate random long using Math.random and those two longs i initialized as lower and upper limits
        
        String s = String.valueOf(generatedLong); //stored that value into a string because that's how i wrote my Luhn method to work
         
        if (Luhn(Long.parseLong(s)) == true){ //only print if the random number is true
          return s + " is valid"; 
        }
        return generateCCard(); //else, using recursion, keep calling itself until a random number that is valid is generated
     }

    /************* MAIN METHOD *****************************************
     * where the above methods should be tested
     ******************************************************************/
    public static void main(String[] args) throws IOException {
         
        // your code goes here    
        Scanner in = new Scanner (System.in);
        
        int menu = 3;
        
        long creditCard = 8673123273510569L; //hardcoded this number to test valid
        long creditCard2 = 8273123273510569L; //hardcoded this number to test invalid
        
        //String file = "ccandnamesExamples.txt";

        while (menu <= 3 && menu >= 1){
            System.out.println("Select a menu option:");
            System.out.println("");
            System.out.println("Option 1: Test the method Luhn");
            System.out.println("Option 2: Test the method checkCustomers"); 
            System.out.println("Option 3: Test the bonus");
            
            menu = in.nextInt();
            System.out.println("-------------------------------------------------------------------------------");
            
            if (menu < 1 || menu > 3) {
                System.out.println("Error: Option not available. Please select again.");
                menu = 3;
                
                System.out.println("-------------------------------------------------------------------------------");
            }
            else if (menu == 1) {
                System.out.println("Option 1: Test the method Luhn"); 
                System.out.println("");
                System.out.println("To prove that the code works, the following credit card numbers will be tested:");
                System.out.println("");
                System.out.println("8673 1232 7351 0569");
                System.out.println("8273 1232 7351 0569");
                System.out.println("");
                System.out.println("");
                System.out.println(creditCard + " is " +  
                (Luhn(creditCard) ? "valid." : "invalid."));
                System.out.println(creditCard2 + " is " +  
                (Luhn(creditCard2) ? "valid." : "invalid."));
                
                System.out.println(" ");
                System.out.println("The following creditcard numbers displayed are the result of reading from a file and tested using the above Luhn method:"); 
                System.out.println("");
                
                FileInputStream fileByteStream = null; // File input stream
                Scanner inFS = null;                   // Scanner object
                String fileString;                           // Data value from file

                // Try to open file

                fileByteStream = new FileInputStream("ccexamples_Jeong.txt");
                inFS = new Scanner(fileByteStream);

                // File is open and valid if we got this far (otherwise exception thrown)
                fileString = inFS.nextLine();

                while (inFS.hasNextLine()) {
                     System.out.println(fileString + " is " + (Luhn(Long.parseLong(fileString)) ? "valid." : "invalid."));

                     fileString = inFS.nextLine();
                }

                // Done with file, so try to close it
                fileByteStream.close(); // close() may throw IOException if fails
                System.out.println("-------------------------------------------------------------------------------");
            }
            else if (menu == 2) {
               System.out.println("Option 2: Test the method checkCustomers"); 
               System.out.println("");
               System.out.println("The following names and creditcard numbers displayed are the result from reading from a file:"); 
               System.out.println("");
                
               FileInputStream fstream = new FileInputStream("ccandnamesExamples.txt");
               DataInputStream dins = new DataInputStream(fstream);
               BufferedReader br = new BufferedReader(new InputStreamReader(dins));

               String strLine;
               int arraySize = 11;
               String customersInfo[][] = new String[arraySize][];
               int index = 0;
                
               while ((strLine = br.readLine()) != null) {

                  customersInfo[index] = strLine.split(" ");
                  index++;
               }
               for (int i = 0; i < customersInfo.length; i++) {

                  if (customersInfo[i] != null) {
                    for (int j = 0; j < customersInfo[i].length; j++) {
                      System.out.print(customersInfo[i][j] + " ");
                    }
                    System.out.println(" ");
                  }
                }
                System.out.println("");
                System.out.println("The creditcard numbers from above have been tested using the Luhn method and the results are displayed below. The customer information of those with invalid cards will be stored in a new file named: customers_error_Jeong.txt");
                System.out.println("");
                for (int n = 0; n < customersInfo.length; n++) {
                    if (customersInfo[n] != null) {
                    System.out.print((customersInfo[n][2]) + " " + (Luhn(Long.parseLong(customersInfo[n][2])) ? "valid." : "invalid."));
                    System.out.println(" ");
                    }
                }
                System.out.println("-------------------------------------------------------------------------------");
                checkCustomersIter();
            }
            else if (menu == 3) {
                System.out.println("Option 3: Test the bonus");
                System.out.println("The following is a random number being generated and tested using the method Luhn:");
                System.out.println(generateCCard());
                System.out.println("-------------------------------------------------------------------------------"); 
            }
        }
      }
    }