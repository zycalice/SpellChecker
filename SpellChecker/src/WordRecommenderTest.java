import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class WordRecommenderTest {

    WordRecommender wr;

    @BeforeEach
    void setUp() throws Exception{
         wr = new WordRecommender("engDictionary.txt");
    }

    @Test
    void testGetWordLength0(){
        assertEquals(0, wr.getWordLength(" "));
    }

    @Test
    void testGetWordLength1(){
        assertEquals(1, wr.getWordLength("h"));
    }

    @Test
    void testGetWordLength4(){
        assertEquals(4, wr.getWordLength("haha"));
    }

    @Test
    void testGetWordLeftChars(){
        String [] expectation = {"h", "a", "h" ,"a"};
        assertEquals(Arrays.toString(expectation), Arrays.toString(wr.getWordLeftChars("haha")));
    }

    @Test
    void testGetWordRightChars(){
        String [] expectation = {"a", "h", "a" ,"h"};
        assertEquals(Arrays.toString(expectation), Arrays.toString(wr.getWordRightChars("haha")));
    }

    @Test
    void testGetNumSameCharsLeft(){
        String word1 = "oblige";
        String word2 = "oblivion";

        assertEquals(4, wr.getNumSameChars(wr.getWordLeftChars(word1),wr.getWordLeftChars(word2)));
    }

    @Test
    void testGetNumSameCharsRight(){
        String word1 = "oblige";
        String word2 = "oblivion";

        assertEquals(1, wr.getNumSameChars(wr.getWordRightChars(word1),wr.getWordRightChars(word2)));
    }

    @Test
    void testGetSimilarity1(){
        String word1 = "oblige";
        String word2 = "oblivion";

        assertEquals(2.5, wr.getSimilarity(word1,word2));
    }

    @Test
    void testGetSimilarity2(){
        String word1 = "aghast";
        String word2 = "gross";

        assertEquals(1.5, wr.getSimilarity(word1,word2));
    }

    @Test
    void testGetSimilaritySameWord(){
        String word1 = "haha";
        String word2 = "haha";

        assertEquals(4, wr.getSimilarity(word1,word2));
    }

    @Test
    void testPrettyPrint(){
        ArrayList<String> stringToPrint = new ArrayList<>();
        stringToPrint.add("biker");
        stringToPrint.add("tiger");
        stringToPrint.add("bigger");

        assertEquals("1. biker\n2. tiger\n3. bigger\n", wr.prettyPrint(stringToPrint));
        System.out.println(wr.prettyPrint(stringToPrint));
    }
}