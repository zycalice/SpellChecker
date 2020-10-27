import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class ReadWrite {

    /**
     * default constructor
     */
    public ReadWrite(){
    }

    /**
     * method to read in the dictionary
     * @return string array of all the words in the dictionary
     */
    public String[] readDictionary(){
        File f = new File("engDictionary.txt");
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


    //main class for testing
    public static void main(String[] args) {
        ReadWrite rw = new ReadWrite();
        System.out.println(rw.readDictionary()[200]);
    }
}
