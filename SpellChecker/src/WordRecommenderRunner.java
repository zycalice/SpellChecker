
public class WordRecommenderRunner {

    //main class to run
    public static void main(String[] args) {
        String fileToCheck = "misspell.txt";
        int dot = fileToCheck.indexOf(".");
        WordRecommender wr = new WordRecommender("engDictionary.txt");
        wr.scanWordSuggestionInputs();
        wr.runRecommender(fileToCheck,fileToCheck.substring(0, dot)+"_chk.txt");

    }
}f
