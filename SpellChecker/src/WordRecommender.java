import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class WordRecommender {

    private String dictionary ;
    private String[] dictionaryWords;

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
     * return the %same character in two words
     * @param a one word
     * @param b the second word
     * @return a double - %same character in two words
     */
    public double getCommon(String a, String b){
        ArrayList<Character> sa = new ArrayList<>();
        ArrayList<Character> sb = new ArrayList<>();
        double same=0.0;
        double union = 0.0;
        for (int i=0;i<a.length();i++){
            if (!sa.contains(a.charAt(i))){
                sa.add(a.charAt(i));
            }
        }
        for (int i=0;i<b.length();i++){
            if (!sb.contains(b.charAt(i))){
                sb.add(b.charAt(i));
            }
        }
        union = sa.size();
        for (int i = 0; i < sb.size(); i++) {
            if (sa.contains(sb.get(i))) {
                same++;
            } else {
                union++;
            }
        }
        return same / union;
    }
    
    public ArrayList<String> getWordSuggestions (String word, int tolerance, double commonPercent, int topN){
        ArrayList<String> sug = new ArrayList<>();
        ArrayList<Double> sugScore = new ArrayList<>();
        ArrayList<String> top = new ArrayList<>();
        int upper = word.length()+tolerance;
        int lower = Math.max(0,word.length()-tolerance);
        for (int i = 0; i<dictionaryWords.length; i++){
            double com = getCommon(word,dictionaryWords[i]);
            int leng = dictionaryWords[i].length();
            if (leng<=upper && leng>=lower && com>=commonPercent){
                sug.add(dictionaryWords[i]);
            }
        }
        for (int i = 0; i<sug.size(); i++){
            sugScore.add(getSimilarity(sug.get(i), word));
        }    
        while (topN>0){
            double maxi = Collections.max(sugScore);
            int index = sugScore.indexOf(maxi);
            top.add(sug.get(index));
            sug.remove(index);
            sugScore.remove(index);
            topN--;
        }
        return top;
    }


    public static void main(String[] args) {
        Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
        WordRecommender wr = new WordRecommender(path + "/" + "engDictionary.txt");
        String[] wr_DicW = wr.getDicWords();
        /*
        System.out.println(wr_DicW.length);
        System.out.println(new File("").getAbsolutePath());
        System.out.println(wr.getSimilarity("haha","haha"));
        System.out.println(wr.getSimilarity("hahe","haha"));
        System.out.println(wr.getSimilarity("oblige","oblivion"));
        System.out.println(wr.getSimilarity("aghast","gross"));*/
        System.out.println(wr.getWordSuggestions("haha", 2, 0.5, 3));
    }
}
