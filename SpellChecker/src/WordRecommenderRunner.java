
public class WordRecommenderRunner {

    //main class to run
    public static void main(String[] args) {
        String fileToCheck = "testFile1.txt";
        int dot = fileToCheck.indexOf(".");
        String fileToWrite = fileToCheck.substring(0, dot)+"_chk.txt";
        String dictToRead = "engDictionary.txt";
        UserInteraction ui = new UserInteraction(dictToRead, fileToCheck,fileToWrite);
        ui.scanWordSuggestionInputs();
        ui.runInteraction();

    }
}
