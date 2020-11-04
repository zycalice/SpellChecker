import java.util.*;

public class WordRecommender {

    private Dictionary dict;

    /**
     * constructor with input dictionary
     * @param fileName, which is a dictionary
     */
    public WordRecommender(String fileName){
        dict= new Dictionary(fileName);
        dict.readDict();
    }

    /**
     * getter for dict
     */
    public Dictionary getDict() {
        return dict;
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
        for (String dictionaryWord : dict.getDictionaryWords()) {
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

}
