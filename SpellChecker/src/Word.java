import java.util.Arrays;

public class Word {
    String word;
    String[] leftChars;
    String[] rightChars;
    int numChars;

    /**
     * Word constructor
     * @param wordInput, a word in string format
     */
    Word(String wordInput){
        //save word, array of characters, and number of characters
        word = wordInput;
        leftChars = wordInput.split("");
        numChars = leftChars.length;
    }

    /**
     * get an string array for each character from the right (reverse ordered)
     * @return string array for each character from the right (reverse ordered)
     */
    String[] getRightChars(){
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
    String [] getLeftChars(){
        return leftChars;
    }

    public static void main(String[] args) {
        Word w = new Word("vqqhahaha");
        System.out.println(Arrays.toString(w.getLeftChars()));
        System.out.println(Arrays.toString(w.getRightChars()));
        System.out.println(w.getLeftChars()[0]);
        System.out.println(w.getRightChars()[0]);
    }
}
