import java.util.Arrays;

public class Word {
    private String word;
    private String[] leftChars;
    private String[] rightChars;
    private int numChars;

    /**
     * Word constructor
     * @param wordInput, a word in string format
     */
    public Word(String wordInput){
        //save word, array of characters, and number of characters
        word = wordInput;
        leftChars = wordInput.split("");
        numChars = leftChars.length;
    }

    /**
     * get an string array for each character from the right (reverse ordered)
     * @return string array for each character from the right (reverse ordered)
     */
    public String[] getRightChars(){
        if (rightChars!=null){
            return rightChars;
        }
        rightChars = new String[numChars];

        for (int i = 0; i < numChars; i++){
            rightChars[i] = leftChars[numChars-i-1];
        }
        return rightChars;
    }

    /**
     * get an string array for each character from the left
     * @return string array for each character from the left
     */
    public String [] getLeftChars(){
        return leftChars;
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
