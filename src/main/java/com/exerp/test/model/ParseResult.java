package com.exerp.test.model;

import java.util.HashMap;
import java.util.Map;

/**
 * This model class holds the result of a successful parse of a text file. 
 * It contains the name of the file that was parsed, and a collection of the words in the file, 
 * along with the number of occurrences of each word. 
 * The word is stored as the key while the number of occurrences is the value. 
 * 
 * @author rab
 *
 */
public class ParseResult {

    // reference for the parse result, in this case the name of the file.
    private String parseResultName;
    private Map<String, Integer> wordOccurrences = new HashMap<String, Integer>();

    private static final Integer INCREMENT_FACTOR = new Integer(1);

    public String getParseResultName() {
        return parseResultName;
    }

    public void setParseResultName(String fileName) {
        parseResultName = fileName;
    }

    public Map<String, Integer> getWordOccurrences() {
        return wordOccurrences;
    }

    public void setWordOccurrences(Map<String, Integer> occurrences) {
        wordOccurrences = occurrences;
    }

    @Override
    public String toString() {
        return "ParseResult [name=" + parseResultName + ", occurrences=" + wordOccurrences + "]";
    }

    // update the number of occurrences for the word in the map.
    public void compute(String word) {
        wordOccurrences.compute(word, ((k, v) -> v == null ? INCREMENT_FACTOR : v + INCREMENT_FACTOR));
    }

    // update the number of occurrences for the word in the map.
    // conventional way. not used, but had coded this before coding the compute
    // method.
    public void computeAlternative(String word) {
        Integer occurrences = wordOccurrences.get(word);
        if (occurrences == null) {
            occurrences = new Integer(0);
        }
        occurrences = occurrences + INCREMENT_FACTOR;
        wordOccurrences.put(word, occurrences);
    }
}
