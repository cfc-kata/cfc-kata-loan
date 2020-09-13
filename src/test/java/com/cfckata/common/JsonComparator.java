package com.cfckata.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.meixuesong.aggregatepersistence.deepequals.DeepEquals;
import com.github.meixuesong.aggregatepersistence.deepequals.DeepEqualsDefaultOption;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

    public static void assertEqualsObjects(Object expected, Object actual, Map<Class, Set<String>> ignoreFieldNames) {
        DeepEqualsDefaultOption deepEqualsDefaultOption = new DeepEqualsDefaultOption();
        DeepEquals deepEquals = new DeepEquals(deepEqualsDefaultOption);
        deepEqualsDefaultOption.getIgnoreFieldNames().putAll(ignoreFieldNames);

        boolean isEquals = deepEquals.isDeepEquals(expected, actual);
        if (!isEquals) {
            JsonComparator.assertEqualsObjects(expected, actual);
        }
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

    public static class IgnoreFieldSettings {
        Map<Class, Set<String>> classIgnoreFieldNames = new HashMap<>();

        public IgnoreFieldSettings addField(Class claz, String fieldName) {
            Set<String> ignoreFields;
            if (classIgnoreFieldNames.containsKey(claz)) {
                ignoreFields = classIgnoreFieldNames.get(claz);
            } else {
                ignoreFields = new HashSet<>();
                classIgnoreFieldNames.put(claz, ignoreFields);
            }
            ignoreFields.add(fieldName);

            return this;
        }

        public Map<Class, Set<String>> getClassIgnoreFieldNames() {
            return classIgnoreFieldNames;
        }
    }
}
