import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.nio.file.*;

public class worldClassRunner {

    
    /**
     * default constructor
     */
    public worldClassRunner(){
    }


    //main class for testing (temp)
    public static void main(String[] args) {
        Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
        WordRecommender wr = new WordRecommender("engDictionary.txt");
        String[] wr_DicW = wr.getDicWords();
        //System.out.println(wr_DicW.length);

    }
}
