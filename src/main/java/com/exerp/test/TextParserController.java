package com.exerp.test;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.exerp.test.model.ParseResult;
import com.exerp.test.service.TextParseService;

/**
 * This class is the REST controller, and exposes some REST endpoints.
 * 
 * @author rab
 *
 */
@Controller
public class TextParserController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    // filename, result_size can be set up to be read from the #HTTPRequest.
    private static final String FILENAME = "tempest.txt";
    private static final Integer RESULT_SIZE = 10000;

    @Autowired
    private TextParseService parserService;

    @RequestMapping("/parse")
    @ResponseBody
    public String parse() {
        ParseResult result = parserService.parse(FILENAME);
        ParseResult sortedResult = sortEntries(result);
        log.info("Final parsed result: [{}]", sortedResult.toString());
        return sortedResult.toString();
    }

    private ParseResult sortEntries(ParseResult result) {
        // use #LinkedHashMap because it guarantees order
        Map<String, Integer> sortedWordsMap = new LinkedHashMap<String, Integer>();

        Map<String, Integer> wordsMap = result.getWordOccurrences();
        Set<Entry<String, Integer>> wordsEntry = wordsMap.entrySet();
        Stream<Entry<String, Integer>> wordsStream = wordsEntry.stream();

        // sort, reverse, limit and copy the elements of the stream to new
        // #LinkedHashMap
        wordsStream.sorted(Collections.reverseOrder(Comparator.comparing(e -> e.getValue()))).limit(RESULT_SIZE)
                .forEachOrdered(e -> sortedWordsMap.put(e.getKey(), e.getValue()));

        ParseResult sortedResult = new ParseResult();
        sortedResult.setParseResultName(result.getParseResultName());
        sortedResult.setWordOccurrences(sortedWordsMap);

        return sortedResult;
    }
}
