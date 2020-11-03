import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class WordRecommender {

    final String dictionary ;
    final ArrayList<String> dictionaryWords;
    private int toleranceInput;
    private double commonPercentInput;
    private int topNInput;

    /**
     * constructor with input dictionary
     * @param fileName, which is a dictionary
     */
    public WordRecommender(String fileName){
        dictionary = fileName;
        dictionaryWords = readDictionary(fileName);
    }

    /**
     * method to read in the dictionary; make sure the file path exist
     * make sure the dictionaryFileName input is in the right path; if error, check the current path
     * @return string array of all the words in the dictionary
     */    
    public ArrayList<String> readDictionary(String dictionaryFileName){
        //read dictionary as ArrayList first
        File f = new File (dictionaryFileName);
        Scanner fileScanner;
        ArrayList<String> dictList = new ArrayList<>();

        //try reading the file; if file does not exist, print a msg and print the current path
        {
            try {
                fileScanner = new Scanner(f);
                while (fileScanner.hasNextLine()){
                    dictList.add(fileScanner.nextLine());
                }
            } catch (FileNotFoundException e) {
                System.out.println("bad file name; current path is" + f.getAbsolutePath());
                e.printStackTrace();
            }
        }

        //convert to string array
        return dictList;
    }

    /**
     * get name of the dictionary file
     * @return the name of the dictionary file
     */
    public String getDictionary(){
        return dictionary;
    }

    /**
     * get all words in the dictionary file
     * @return dictionary in String array form
     */
    public ArrayList<String> getDicWords(){
        return dictionaryWords;
    }

    /**
     * get an string array for each character from the left
     * @return string array for each character from the left
     */
    public String [] getWordLeftChars(String word){
        return word.split("");
    }

    /**
     * right characters
     * @return string array of each character starting from the right
     */
    public String[] getWordRightChars(String word){
        String [] rightChars = new String[word.length()];

        //loop to get the reverse ordered characters
        for (int i = 0; i < word.length(); i++){
            rightChars[i] = getWordLeftChars(word)[word.length()-i-1];
        }
        return rightChars;
    }

    /**
     * get the number of same char and position
     * @param wordArray1 string array of each characters in a word 1
     * @param wordArray2 string array of each characters in a word 2
     * @return an integer - the number of same char and position
     */
    public int getNumSameChars(String[] wordArray1, String[] wordArray2){
        int numSame = 0;

        //loop over the min of the characters to count similarity
        for (int i = 0; i < Math.min(wordArray1.length, wordArray2.length);i++){
            if (wordArray1[i].equals(wordArray2[i])){
                numSame++;
            }
        }
        return numSame;
    }

    /**
     * return the avg similarity from left and right
     * @param word1 one word
     * @param word2 the second word
     * @return a double - the avg similarity on two words
     */
    public double getSimilarity(String word1, String word2){
        double leftSimilarity = getNumSameChars(getWordLeftChars(word1), getWordLeftChars(word2));
        double rightSimilarity = getNumSameChars(getWordRightChars(word1), getWordRightChars(word2));
        return  (leftSimilarity + rightSimilarity)/2;
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
     * return the %same character in two words
     * @param word1 one word
     * @param word2 the second word
     * @return a double - %same character in two words
     */
    public double getCommon(String word1, String word2){
        ArrayList<Character> s1 = new ArrayList<>();
        ArrayList<Character> s2 = new ArrayList<>();
        double same = 0.0;
        double union;

        //unique letters for word1
        for (int i = 0;i < word1.length();i++){
            if (!s1.contains(word1.charAt(i))){
                s1.add(word1.charAt(i));
            }
        }

        //unique letters for word2
        for (int i = 0;i < word2.length();i++){
            if (!s2.contains(word2.charAt(i))){
                s2.add(word2.charAt(i));
            }
        }

        //calculate same and union
        union = s1.size();
        for (Character character : s2) {
            if (s1.contains(character)) {
                same++;
            } else {
                union++;
            }
        }
        return same / union;
    }

    /**
     * get word suggestions for a single word
     * @param word the word that need to be compared
     * @param tolerance get from user input
     * @param commonPercent get from user input
     * @param topN get from user input
     * @return return an arraylist of word suggestions
     */
    public ArrayList<String> getWordSuggestions (String word, int tolerance, double commonPercent, int topN){
        ArrayList<String> sug = new ArrayList<>();
        ArrayList<Double> sugScore = new ArrayList<>();
        ArrayList<String> top = new ArrayList<>();
        int upper = word.length()+tolerance;
        int lower = Math.max(0,word.length()-tolerance);

        //filter for tolerance and commonPercent requirements
        for (String dictionaryWord : dictionaryWords) {
            double com = getCommon(word, dictionaryWord);
            int leng = dictionaryWord.length();
            if (leng <= upper && leng >= lower && com >= commonPercent) {
                sug.add(dictionaryWord);
            }
        }

        //for each filtered words, calculate similarity
        for (String s : sug) {
            sugScore.add(getSimilarity(s, word));
        }

        //top N is N or max of the suggestion (in this case, dealt with a min function)
        topN = Math.min(topN,sug.size());

        //while loop to obtain max and add to top until repeated topN times
        while (topN > 0){
            double maxi = 0;
            int index = 0;
            for (int i = 0; i<sugScore.size();i++){
                if (sugScore.get(i)>maxi){
                    maxi = sugScore.get(i);
                    index = i;
                }
            }
            top.add(sug.get(index));
            sug.remove(index);
            sugScore.remove(index);
            topN--;
        }
        return top;
    }

    /**
     * pretty print an array list
     * @param list input a list of strings
     * @return a string that can be printed pretty
     */
    public String prettyPrint(ArrayList<String> list){
        StringBuilder forPrint = new StringBuilder();

        for (int i = 0; i < list.size(); i++){
            forPrint.append(i+1).append(". ").append(list.get(i)).append("\n");
        }

        return forPrint.toString();
    }

    /**
     * ask user what to do if this word is not in the dictionary list
     * @param word from a input with misspells
     * @return a string that need to replace word with
     */
    public String userDecision(String word){
        Scanner scan = new Scanner(System.in);
        ArrayList<String> wordSuggestions = getWordSuggestions(word, toleranceInput, commonPercentInput, topNInput);

        //print instructions for the user
        System.out.println("The word '" + word + "' is misspelled");
        if (wordSuggestions.size()==0){
            System.out.println("Sorry there are no suggestions available");
            System.out.println("Press ‘a’ for accept as is or ‘t’ for type in manually.");
        } else{
            System.out.println("The following suggestions are available");
            System.out.println(prettyPrint(wordSuggestions)); //to edit
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
            System.out.println("Not a non-negative. Enter a non-negative integer.");
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
     * run the recommender
     * @param spellCheckFileName a file that needs to be checked for spelling, no punctuations
     */
    public void runRecommender(String spellCheckFileName, String outputFileName){
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
                    if (!dictionaryWords.contains(word)){
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
