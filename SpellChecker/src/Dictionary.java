/*
  CIT 591 HW5, written by
  Xinyi (Viola) Li
  Yuchen Zhang
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Dictionary {

    private final String dictionaryName;
    private ArrayList<String> dictionaryWords;

    Dictionary(String fileName){
        this.dictionaryName = fileName;
    }

    /**
     * method to read in the dictionary; make sure the file path exist
     * make sure the dictionaryFileName input is in the right path; if error, check the current path
     */
    public void readDict(){
        //read dictionary as ArrayList first
        File f = new File (dictionaryName);
        Scanner fileScanner;
        ArrayList<String> dictList = new ArrayList<>();

        //try reading the file; if file does not exist, print a msg and print the current path
        {
            try {
                fileScanner = new Scanner(f);
                while (fileScanner.hasNextLine()){
                    dictList.add(fileScanner.nextLine());
                }
            } catch (FileNotFoundException e) {
                System.out.println("bad file name; current path is" + f.getAbsolutePath());
                e.printStackTrace();
            }
        }

        //convert to string array
        this.dictionaryWords = dictList;
    }

    /**
     *
     * @return filename of the dictionary
     */
    public String getDictionaryName(){
        return dictionaryName;
    }

    /**
     * get dictionary words arraylist
     * @return dictionary words arraylist of strings
     */
    public ArrayList<String> getDictionaryWords(){
        return dictionaryWords;
    }

    /**
     * get dictionary size/number of words in the dictionary
     * @return (integer) number of words in the dictionary
     */
    public int getDictionaryLen(){
        return getDictionaryWords().size();
    }


}
