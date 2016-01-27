package com.exerp.test.service.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import com.exerp.test.model.ParseResult;
import com.exerp.test.service.TextParseService;

@Controller
public class TextParseServiceImpl implements TextParseService {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private static final String SPLIT_WORDS_REGEX = "[^\\w'\\-]+";

    @Override
    public ParseResult parse(String fileName) {
        ParseResult result = null;
        String fileAsStr = readFileAsString(fileName);
        if (fileAsStr != null) {
            String[] words = fileAsStr.split(SPLIT_WORDS_REGEX);
            if (words != null) {
                result = parseWords(words);
            }
        }

        result.setParseResultName(fileName);
        log.debug("Parse result: [{}]", result);
        return result;
    }

    private String readFileAsString(String fileName) {
        String fileStr = null;
        try {
            InputStream in = getClass().getClassLoader().getResourceAsStream(fileName);
            fileStr = IOUtils.toString(in, "UTF-8");
            log.info("Reading file [{}] as string, completed.", fileName);
        } catch (FileNotFoundException fileNotFoundEx) {
            log.error("File not found error.", fileNotFoundEx.getMessage());
        } catch (IOException ioEx) {
            log.error("Error occurred while reading from file.", ioEx);
        }

        return fileStr;
    }

    private ParseResult parseWords(String[] words) {
        ParseResult result = new ParseResult();

        log.info("Number of words found: [{}]", words.length);
        for (String word : words) {
            log.trace("Next word: [{}]", word);
            if ((word != null) && (word.length() != 0)) {
                word = word.toLowerCase(Locale.ENGLISH);
                result.compute(word);
            }
        }

        return result;
    }

}
