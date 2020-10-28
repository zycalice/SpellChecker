import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class WordRecommender {

    private String dictionary ;
    private String[] dictionaryWords;
    public int toleranceInput;
    public double commonPercentInput;
    public int topNInput;


    public WordRecommender(String fileName){
        dictionary = fileName;
        dictionaryWords = readDictionary(fileName);
    }

    /**
     * method to read in the dictionary; make sure the file path exist
     * make sure the dictionaryFileName input is in the right path; if error, check the current path
     * @return string array of all the words in the dictionary
     */    
    public String[] readDictionary(String dictionaryFileName){
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
        return dictList.toArray(String[]::new);
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
    public String[] getDicWords(){
        return dictionaryWords;
    }

    public int getWordLength(String word){
        //empty word should have zero length
        if (word.trim().equals("")){
            return 0;
        }
        //otherwise, normal length
        return word.split("").length;
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
        String [] rightChars = new String[getWordLength(word)];

        //loop to get the reverse ordered characters
        for (int i = 0; i < getWordLength(word); i++){
            rightChars[i] = getWordLeftChars(word)[getWordLength(word)-i-1];
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
        Scanner scan = new Scanner(System.in);

        //tolerance
        System.out.println("Please specify tolerance level (0 or positive integer)");
        toleranceInput = scan.nextInt();

        //commonPercent
        System.out.println("Please specify common percentage of characters (a double between 0-1)");
        commonPercentInput = scan.nextDouble();

        //topN
        System.out.println("Please specify number of word suggestions (positive integer)");
        topNInput = scan.nextInt();

    }
    

    public ArrayList<String> getWordSuggestions (String word, int tolerance, double commonPercent, int topN){
        //TO DO:
        ArrayList<String> wordSuggestion = new ArrayList<>();
        return wordSuggestion;
    }

    /**
     *
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
    public String userInteraction(String word){
        Scanner scan = new Scanner(System.in);
        ArrayList<String> wordSuggestions = getWordSuggestions(word, toleranceInput, commonPercentInput, topNInput);

        //print instructions for the user
        System.out.println("The word '" + word + "' is misspelled");
        System.out.println("The following suggestions are available");
        System.out.println(prettyPrint(wordSuggestions)); //to edit
        System.out.println("Press ‘r’ for replace, ‘a’ for accept as is, ‘t’ for type in manually.");

        //intake values conditionally
        String instruction = scan.next();
        switch (instruction){
            case "r":
                System.out.println("Your word will now be replaced with one of the suggestions\n" +
                                    "Enter the number corresponding to the word that you want to use for replacement.");
                int replaceIndex = scan.nextInt();
                return wordSuggestions.get(replaceIndex);
            case "a":
                return word;
            case "t":
                System.out.println("Please type the word that will be used as the replacement in the output file.");
                String manualReplace = scan.nextLine();
                return manualReplace;
        }
        return word;
    }

//    public static void main(String[] args) {
//        Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
//        WordRecommender wr = new WordRecommender(path + "/" + "engDictionary.txt");
//        String[] wr_DicW = wr.getDicWords();
//        System.out.println(wr_DicW.length);
//
//    }

}
