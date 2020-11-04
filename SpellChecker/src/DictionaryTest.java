import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DictionaryTest {

    Dictionary readEng;
    Dictionary readSample;

    @BeforeEach
    void setUp(){
        String dict1 = "engDictionary.txt";
        String dict2 = "SampleDictionary.txt";
        readEng = new Dictionary(dict1);
        readSample = new Dictionary(dict2);
        readEng.readDict();
        readSample.readDict();
    }

    @Test
    void testReadDict1(){
        assertEquals("aardvark", readEng.getDictionaryWords().get(0));
        assertEquals("zulus", readEng.getDictionaryWords().get(readEng.getDictionaryLen()-1));
        assertEquals(58110, readEng.getDictionaryLen());
    }

    @Test
    void testReadDict2(){
        assertEquals("build", readSample.getDictionaryWords().get(0));
        assertEquals("star", readSample.getDictionaryWords().get(readSample.getDictionaryLen()-1));
        assertEquals(9, readSample.getDictionaryLen());

    }


}