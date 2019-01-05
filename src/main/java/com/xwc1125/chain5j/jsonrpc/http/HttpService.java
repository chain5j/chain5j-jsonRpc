package com.xwc1125.chain5j.jsonrpc.http;

import com.xwc1125.chain5j.jsonrpc.Service;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.Buffer;
import okio.BufferedSource;
import org.slf4j.Logger;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Description: <br>
 *
 * @author xwc1125 <br>
 * @date 2019-01-04  11:23 <br>
 * <p>
 * Copyright (c) 2019 <br>
 */
@Slf4j
public class HttpService extends Service {
    public static final MediaType JSON_MEDIA_TYPE = MediaType.parse("application/json; charset=utf-8");
    public static final String DEFAULT_URL = "http://localhost:5545/";
    private OkHttpClient httpClient;
    private final String url;
    private final boolean includeRawResponse;
    private HashMap<String, String> headers;

    public HttpService(String url, OkHttpClient httpClient, boolean includeRawResponses) {
        super(includeRawResponses);
        this.headers = new HashMap();
        this.url = url;
        this.httpClient = httpClient;
        this.includeRawResponse = includeRawResponses;
    }

    public HttpService(OkHttpClient httpClient, boolean includeRawResponses) {
        this(DEFAULT_URL, httpClient, includeRawResponses);
    }

    private HttpService(String url, OkHttpClient httpClient) {
        this(url, httpClient, false);
    }

    public HttpService(String url) {
        this(url, createOkHttpClient());
    }

    public HttpService(String url, boolean includeRawResponse) {
        this(url, createOkHttpClient(), includeRawResponse);
    }

    public HttpService(OkHttpClient httpClient) {
        this(DEFAULT_URL, httpClient);
    }

    public HttpService(boolean includeRawResponse) {
        this(DEFAULT_URL, includeRawResponse);
    }

    public HttpService() {
        this(DEFAULT_URL);
    }

    private static OkHttpClient createOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        configureLogging(builder);
        return builder.build();
    }

    private static void configureLogging(OkHttpClient.Builder builder) {
        if (log.isDebugEnabled()) {
            Logger var10002 = log;
            var10002.getClass();
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor(var10002::debug);
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(logging);
        }

    }

    @Override
    protected InputStream performIO(String request) throws IOException {
        RequestBody requestBody = RequestBody.create(JSON_MEDIA_TYPE, request);
        Headers headers = this.buildHeaders();
        Request httpRequest = (new okhttp3.Request.Builder()).url(this.url).headers(headers).post(requestBody).build();
        Response response = this.httpClient.newCall(httpRequest).execute();
        ResponseBody responseBody = response.body();
        if (response.isSuccessful()) {
            return responseBody != null ? this.buildInputStream(responseBody) : null;
        } else {
            int code = response.code();
            String text = responseBody == null ? "N/A" : responseBody.string();
            throw new IOException("Invalid response received: " + code + "; " + text);
        }
    }

    private InputStream buildInputStream(ResponseBody responseBody) throws IOException {
        InputStream inputStream = responseBody.byteStream();
        if (this.includeRawResponse) {
            BufferedSource source = responseBody.source();
            source.request(9223372036854775807L);
            Buffer buffer = source.buffer();
            long size = buffer.size();
            if (size > 2147483647L) {
                throw new UnsupportedOperationException("Non-integer input buffer size specified: " + size);
            } else {
                int bufferSize = (int) size;
                BufferedInputStream bufferedinputStream = new BufferedInputStream(inputStream, bufferSize);
                bufferedinputStream.mark(inputStream.available());
                return bufferedinputStream;
            }
        } else {
            return inputStream;
        }
    }

    private Headers buildHeaders() {
        return Headers.of(this.headers);
    }

    public void addHeader(String key, String value) {
        this.headers.put(key, value);
    }

    public void addHeaders(Map<String, String> headersToAdd) {
        this.headers.putAll(headersToAdd);
    }

    public HashMap<String, String> getHeaders() {
        return this.headers;
    }

    @Override
    public void close() throws IOException {
    }
}
