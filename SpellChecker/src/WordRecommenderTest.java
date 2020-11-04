import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class WordRecommenderTest {

    WordRecommender wr;
    WordRecommender wrSimple;

    @BeforeEach
    void setUp() throws Exception{
         wr = new WordRecommender("engDictionary.txt");
         wrSimple = new WordRecommender("sampleDictionary.txt");
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
    void testGetCommon1(){
        String word1 = "committee";
        String word2 = "comet";

        assertEquals(5.0/6, wr.getCommon(word1,word2));
    }

    @Test
    void testGetCommon2(){
        String word1 = "garden";
        String word2 = "nerdi";

        assertEquals(4.0/7, wr.getCommon(word1,word2));
    }


    @Test
    void testGetCommon3(){
        String word1 = "built";
        String word2 = "billing";

        assertEquals(3.0/7, wr.getCommon(word1,word2));

    }

    @Test
    void testGetCommon4(){
        String word1 = "fille";
        String word2 = "filling";

        assertEquals(3.0/6, wr.getCommon(word1,word2));
    }

    @Test
    void testGetCommon5(){
        String word1 = "fille";
        String word2 = "billing";

        assertEquals(2.0/7, wr.getCommon(word1,word2));
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

    @Test
    void testGetWordSuggestionSimple0(){
        ArrayList<String> resultTest = wrSimple.getWordSuggestions("xixi",3,0.3,3);
        String[] test = new String[]{""};

        assertEquals("[]", Arrays.toString(resultTest.toArray()));
    }

    @Test
    void testGetWordSuggestionSimple1(){
        ArrayList<String> resultTest = wrSimple.getWordSuggestions("built",1,0.6,3);
        String[] test = new String[]{"build"};

        assertEquals(Arrays.toString(test), Arrays.toString(resultTest.toArray()));
    }


    @Test
    void testGetWordSuggestionSimple2(){
        ArrayList<String> resultTest = wrSimple.getWordSuggestions("built",3,0.4,3);
        String[] test = new String[]{"build", "building","billing"};

        assertEquals(Arrays.toString(test), Arrays.toString(resultTest.toArray()));
    }

    @Test
    void testGetWordSuggestionSimple3(){
        ArrayList<String> resultTest = wrSimple.getWordSuggestions("fille",3,0.3,10);
        String[] test = new String[]{"file", "filing"};

        assertEquals(Arrays.toString(test), Arrays.toString(resultTest.toArray()));
    }


    @Test
    void testGetWordSuggestion0(){
        ArrayList<String> resultTest = wr.getWordSuggestions("xixi",1,0.9,10);
        String[] test = new String[]{""};

        assertEquals(Arrays.toString(test), Arrays.toString(resultTest.toArray()));
    }

    @Test
    void testReadDictionary(){

    }

}