/**
 * CIT 591 HW5, written by
 * Xinyi (Viola) Li
 * Yuchen Zhang
 */

public class WordRecommenderRunner {

    //main class to run
    public static void main(String[] args) {
        String fileToCheck = "testFile2.txt";
        int dot = fileToCheck.indexOf(".");
        String fileToWrite = fileToCheck.substring(0, dot)+"_chk.txt";
        String dictToRead = "engDictionary.txt";

        //Call user interaction for to run the program
        UserInteraction ui = new UserInteraction(dictToRead, fileToCheck, fileToWrite);
        ui.scanWordSuggestionInputs();
        ui.runInteraction();

    }
}
