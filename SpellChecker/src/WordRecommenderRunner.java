
public class WordRecommenderRunner {

    //main class to run
    public static void main(String[] args) {
        WordRecommender wr = new WordRecommender("engDictionary.txt");
        wr.scanWordSuggestionInputs();
        wr.runRecommender("misspell.txt","clean_output.txt");

    }
}
