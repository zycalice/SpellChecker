import java.util.Arrays;

public class Word {
    private String word;
    private int numChars;
 
    /**
     * Word constructor
     * @param wordInput, a word in string format
     */
    public Word(String wordInput){
        //save word, array of characters, and number of characters
        word = wordInput;
    }

    /**
     * get an string array for each character from the left
     * @return string array for each character from the left
     */
    public String [] getLeftChars(){
        return word.split("");
    }

    /**
     * get an string array for each character from the right (reverse ordered)
     * @return string array for each character from the right (reverse ordered)
     */
    public String[] getRightChars(){
        numChars = getLeftChars().length;
        String [] rightChars = new String[numChars];

        for (int i = 0; i < numChars; i++){
            rightChars[i] = getLeftChars()[numChars-i-1];
        }
        return rightChars;
    }


    //main class for testing (temp)
    public static void main(String[] args) {
        Word w = new Word("vqqhahaha");
        System.out.println(Arrays.toString(w.getLeftChars()));
        System.out.println(Arrays.toString(w.getRightChars()));
        System.out.println(w.getLeftChars()[0]);
        System.out.println(w.getRightChars()[0]);
    }
}
