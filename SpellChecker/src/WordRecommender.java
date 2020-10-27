public class WordRecommender {

    private String dictionary;
    private String[] dictionaryWords;

    public WordRecommender(String fileName){
        ReadWrite rw = new ReadWrite();
        dictionary = fileName;
        dictionaryWords = rw.readDictionary(fileName);
    }


}
