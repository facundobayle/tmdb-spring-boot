package com.despegar.dasboot.connector;

import com.despegar.dasboot.controller.context.Context;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

public class ContextHeadersInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(HttpRequest httpRequest, byte[] bytes, ClientHttpRequestExecution clientHttpRequestExecution) throws IOException {
        Context.getRequestContext().getHeaders().forEach((k, v) -> httpRequest.getHeaders().add(k, v));
        return clientHttpRequestExecution.execute(httpRequest, bytes);
    }
}
