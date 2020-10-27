import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.nio.file.*;

public class ReadWrite {

    Path path = FileSystems.getDefault().getPath("").toAbsolutePath();

    /**
     * default constructor
     */
    public ReadWrite(){
    }

    /**
     * method to read in the dictionary
     * @return string array of all the words in the dictionary
     */
    public String[] readDictionary(String dictionaryFileName){
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


    //main class for testing (temp)
    public static void main(String[] args) {
        ReadWrite rw = new ReadWrite();
        System.out.println(rw.path);
//        System.out.println(rw.path);
        System.out.println(rw.readDictionary("engDictionary.txt")[200]);
    }
}
