import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

public class WordRecommender {

    private String dictionary ;
    private String[] dictionaryWords;

    public WordRecommender(String fileName){
        dictionary = fileName;
        dictionaryWords = readDictionary(fileName);
    }

    /**
     * method to read in the dictionary
     * @return string array of all the words in the dictionary
     */    
    public String[] readDictionary(String dictionaryFileName){

        File f = new File (dictionaryFileName);
        Scanner fileScanner;
        ArrayList<String> dictList = new ArrayList<>();

        {
            try {
                fileScanner = new Scanner(f);
                while (fileScanner.hasNextLine()){
                    dictList.add(fileScanner.nextLine());
                }
            } catch (FileNotFoundException e) {
                System.out.println("bad file name");
                e.printStackTrace();
            }
        }
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
    
    /*** 
    public ArrayList<String> getWordSuggestions (String word, int tolerance, double commonPercent, int topN){
        
    }***/

    public static void main(String[] args) {
        Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
        WordRecommender wr = new WordRecommender(path + "/" + "engDictionary.txt");
        String[] wr_DicW = wr.getDicWords();
        System.out.println(wr_DicW.length);

        System.out.println(new File("").getAbsolutePath());

    }
}
