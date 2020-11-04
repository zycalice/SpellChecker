/*
  CIT 591 HW5, written by
  Xinyi (Viola) Li
  Yuchen Zhang
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.io.IOException;
import java.io.PrintWriter;

public class UserInteraction{

    private final WordRecommender wr;
    private int toleranceInput;
    private double commonPercentInput;
    private int topNInput;
    private final String spellCheckFileName;
    private final String outputFileName;

    /**
     * constructor with input dictionary, file to read and file to write
     * @param dictToRead, which is a dictionary txt file 
     * @param spellCheckFileName, which is a txt file to read input
     * @param outputFileName, which is a txt file to write output
     */
    public UserInteraction(String dictToRead, String spellCheckFileName, String outputFileName){
        wr = new WordRecommender(dictToRead);
        this.spellCheckFileName = spellCheckFileName;
        this.outputFileName = outputFileName;
    }

    /**
     * scan user inputs for tolerance, commonPercent and topN
     */
    public void scanWordSuggestionInputs(){

        //tolerance
        System.out.println("Please specify tolerance level (0 or positive integer)");
        toleranceInput = nonNegativeIntegerEnforceInput();

        //commonPercent
        System.out.println("Please specify common percentage of characters (a double between 0-1)");
        commonPercentInput = percentEnforceInput();

        //topN
        System.out.println("Please specify number of word suggestions (positive integer)");
        topNInput = positiveIntegerEnforceInput();

    }


    /**
     * ask user what to do if this word is not in the dictionary list
     * @param word from a input with misspells
     * @return a string that need to replace word with
     */
    public String userDecision(String word){
        Scanner scan = new Scanner(System.in);
        ArrayList<String> wordSuggestions = wr.getWordSuggestions(word, toleranceInput, commonPercentInput, topNInput);

        //print instructions for the user
        System.out.println("The word '" + word + "' is misspelled");
        if (wordSuggestions.size()==0){
            System.out.println("Sorry there are no suggestions available");
            System.out.println("Press ‘a’ for accept as is or ‘t’ for type in manually.");
        } else{
            System.out.println("The following suggestions are available");
            System.out.println(wr.prettyPrint(wordSuggestions)); //to edit
            System.out.println("Press ‘r’ for replace, ‘a’ for accept as is, ‘t’ for type in manually.");
        }   
    
        //intake values conditionally and error checking
        String instruction = choiceEnforceInput(wordSuggestions.size());
        switch (instruction){
            case "r":
                System.out.println("Your word will now be replaced with one of the suggestions\n" +
                        "Enter the number corresponding to the word that you want to use for replacement.");
                int replaceIndex = replaceEnforceInput();
                return wordSuggestions.get(replaceIndex-1);
            case "a":
                return word;
            case "t":
                System.out.println("Please type the word that will be used as the replacement in the output file.");
                return scan.nextLine();
        }
        return word;
    }

    /**
     * check if an input is an integer
     * @param input a string
     * @return true if it is an integer
     */
    private boolean nonNegativeIntegerCheck(String input){
        try {
                int i = Integer.parseInt(input);
                if (i<0){
                    return false;
                }
            } catch(NumberFormatException e) {
                return false;
            }
        return true;
    }

    /**
     * guarantees a non-negative integer input
     * @return a non-negative integer
     */
    private int nonNegativeIntegerEnforceInput(){
        Scanner scan = new Scanner(System.in);
        String inputString = scan.nextLine();
        while (!nonNegativeIntegerCheck(inputString)){
            System.out.println("Not a non-negative integer. Enter a non-negative integer.");
            inputString = scan.nextLine();
        }
        return Integer.parseInt(inputString);
    }

    /**
     * guarantees a positive integer input
     * @return a positive integer
     */
    private int positiveIntegerEnforceInput(){
        int inputNonNeg = nonNegativeIntegerEnforceInput();
        while (inputNonNeg == 0){
            System.out.println("Not a positive integer. Enter a positive integer.");
            inputNonNeg = nonNegativeIntegerEnforceInput();
        }
        return inputNonNeg;
    }

    /**
     * check if an input is a double
     * @param input a string
     * @return a double
     */
    private boolean percentCheck(String input){
        try{
            double percent = Double.parseDouble(input);
            if (percent <0 || percent >1){
                return false;
            }
        } catch(NumberFormatException e) {
            return false;
        }
        return true;
    }

    /**
     * guarantees a percentage input
     * @return a double
     */
    private double percentEnforceInput(){
        Scanner scan = new Scanner(System.in);
        String inputString = scan.nextLine();
        while (!percentCheck(inputString)){
            System.out.println("Not a percentage. Enter a double between 0 and 1.");
            inputString = scan.nextLine();
        }
        return Double.parseDouble(inputString);
    }

    /**
     * a function that will continue until the input is "r","a" or "t"
     * @return r, a, or t
     */
    private String choiceEnforceInput(int outputLen){
        Scanner scan = new Scanner(System.in);
        String[] possibleChoices;
        String input = scan.nextLine();
        if (outputLen>0){
            possibleChoices = new String[]{"r", "a", "t"};
            //while loop to take input until a string is "r", "a" or "t"
            while (!Arrays.asList(possibleChoices).contains(input)){
                System.out.println("Invalid input; enter 'r','a', or 't'");
                input = scan.nextLine();
            }
        } else {
            possibleChoices = new String[]{"a", "t"};
            //while loop to take input until a string is "a" or "t"
            while (!Arrays.asList(possibleChoices).contains(input)){
                System.out.println("Invalid input; enter 'a' or 't'");
                input = scan.nextLine();
            }
        }
        return input;
    }

    /**
     * a function that will continue until the input is from top N
     * @return an integer in top N
     */
    private int replaceEnforceInput(){
        int input = nonNegativeIntegerEnforceInput();
        //while loop until this number is in topN
        while((input <= 0) || (input > topNInput)){
            System.out.println("Please enter an integer within the range (top N).");
            input = nonNegativeIntegerEnforceInput();
        }
        return input;
    }

    /**
     * run the recommender file
     */
    public void runInteraction(){
        //read dictionary as ArrayList first
        File f = new File (spellCheckFileName);
        Scanner fileScanner;

        //prepare new file for output
        File myObj = new File(outputFileName);

        //read, update, and write each word
        try {
            //create a new file
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
                PrintWriter clear = new PrintWriter(outputFileName);
                clear.print("");
                clear.close();
            }

            //read, update and write the word to the new file
            PrintWriter pw = new PrintWriter(outputFileName);
            try {
                fileScanner = new Scanner(f);
                while (fileScanner.hasNext()) {
                    String word = fileScanner.next();
                    String newWord = word;
                    if (!wr.getDict().getDictionaryWords().contains(word)){
                        newWord = userDecision(word);
                    }
                    pw.write(newWord + " ");
                }
                pw.close();
            } catch (FileNotFoundException e) {
                System.out.println("bad file name; current path is" + f.getAbsolutePath());
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
