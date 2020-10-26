import java.util.ArrayList;
import java.util.Arrays;

public class Word {
    String word;
    String[] chars;
    int numChars;

    /**
     * Word constructor
     * @param wordInput, a word in string format
     */
    Word(String wordInput){
        word = wordInput;
        chars = wordInput.split("");
        numChars = chars.length;
    }

    public static void main(String[] args) {
        Word w = new Word("vqqhahaha");
        System.out.println(Arrays.toString(w.chars));
        System.out.println(w.chars[0]);
        System.out.println(w.chars[w.numChars-1]);
    }
}
