package com.despegar.dasboot.connector.interceptor;

import com.despegar.dasboot.context.Context;
import com.despegar.dasboot.context.RequestContext;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;
import java.util.Optional;

public class ContextHeadersInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(HttpRequest httpRequest, byte[] bytes, ClientHttpRequestExecution clientHttpRequestExecution) throws IOException {
        Optional.ofNullable(Context.getRequestContext())
                .map(RequestContext::getHeaders)
                .ifPresent(headers -> httpRequest.getHeaders().setAll(headers));
        return clientHttpRequestExecution.execute(httpRequest, bytes);
    }
}
