package ru.stoloto.esb.webkaf.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.springframework.http.HttpEntity;
public class WebKafBaseTest {
    public synchronized void logJson(Logger log, HttpEntity<?> object, ObjectMapper jsonMapper) throws JsonProcessingException {
        log.info(jsonMapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(object.getBody())
        );
    }
}
