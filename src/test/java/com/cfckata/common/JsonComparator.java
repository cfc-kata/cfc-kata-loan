package com.cfckata.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.text.SimpleDateFormat;

import static org.junit.Assert.assertEquals;

public class JsonComparator {

    private static ObjectMapper mapper;

    public static String toJson(Object sourceObject) {
        try {
            return getMapper().writeValueAsString(sourceObject);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void assertEqualsObjects(Object expected, Object actual) {
        String expectedJson = toJson(expected);
        String actualJson = toJson(actual);

        assertEquals(expectedJson, actualJson);
    }

    private static synchronized ObjectMapper getMapper() {
        if (mapper == null) {
            createObjectMapper();
        }

        return mapper;
    }

    private static void createObjectMapper() {
        if (mapper == null) {
            ObjectMapper newMapper = new ObjectMapper();
            newMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS"));
            newMapper.registerModule(new JavaTimeModule());
            newMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

            mapper = newMapper;
        }
    }
}
