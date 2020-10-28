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
        Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
        File f = new File(path + "/" + dictionaryFileName);
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

    public String getDictionary(){
        return dictionary;
    }

    public String[] getDicWords(){
        return dictionaryWords;
    }
    /*** 
    public ArrayList<String> getWordSuggestions (String word, int tolerance, double commonPercent, int topN){
        
    }***/
}
