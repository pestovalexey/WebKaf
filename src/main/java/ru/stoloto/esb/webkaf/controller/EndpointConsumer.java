package ru.stoloto.esb.webkaf.controller;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * Consumer
 */
public interface EndpointConsumer {

    /**
     * @param request in
     * @param response ou
     * @return offset
     * @throws Exception if any
     */
    Long consume(ServletRequest request, ServletResponse response) throws Exception;
}
