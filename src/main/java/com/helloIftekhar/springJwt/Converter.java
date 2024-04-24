package com.helloIftekhar.springJwt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;

public class Converter {

    public static <T> String convertToString(T items) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(items);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static <T> String convertListToString(List<T> items) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(items);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
