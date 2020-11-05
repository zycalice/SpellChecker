/**
 * CIT 591 HW5, written by
 * Xinyi (Viola) Li
 * Yuchen Zhang
 *
 * Please note that if you open the project from on level above, you would need to
 * put dictionary files in this folder to run the program.
 * It is better if you open the project from the SpellChecker folder,
 * which already contains engDictionary and a couple other files.
 *
 */

public class WordRecommenderRunner {

    //main class to run
    public static void main(String[] args) {
        String fileToCheck = "testFile1.txt";
        int dot = fileToCheck.indexOf(".");
        String fileToWrite = fileToCheck.substring(0, dot)+"_chk.txt";
        String dictToRead = "engDictionary.txt";

        //Call user interaction for to run the program
        UserInteraction ui = new UserInteraction(dictToRead, fileToCheck, fileToWrite);
        ui.scanWordSuggestionInputs();
        ui.runInteraction();

    }
}
