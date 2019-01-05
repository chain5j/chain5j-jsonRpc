package com.xwc1125.chain5j.protocol.entity;

import com.xwc1125.chain5j.jsonrpc.RpcService;
import com.xwc1125.chain5j.jsonrpc.core.RemoteCall;
import rx.Observable;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Description: <br>
 *
 * @author xwc1125 <br>
 * @date 2019-01-04  11:08 <br>
 * <p>
 * Copyright (c) 2019 <br>
 */
public class Request<S, T extends Response> {
    private static AtomicLong nextId = new AtomicLong(0L);
    private String jsonRpc = "2.0";
    private String method;
    private List<S> params;
    private long id;
    private RpcService rpcService;
    private Class<T> responseType;

    public Request() {
    }

    /**
     * 请求
     */
    public Request(String method, List<S> params, RpcService rpcService, Class<T> type) {
        this.method = method;
        this.params = params;
        this.id = nextId.getAndIncrement();
        this.rpcService = rpcService;
        this.responseType = type;
    }

    public String getJsonRpc() {
        return this.jsonRpc;
    }

    public void setJsonRpc(String jsonRpc) {
        this.jsonRpc = jsonRpc;
    }

    public String getMethod() {
        return this.method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public List<S> getParams() {
        return this.params;
    }

    public void setParams(List<S> params) {
        this.params = params;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    /**
     * 发送
     *
     * @return
     * @throws IOException
     */
    public T send() throws IOException {
        return (T) this.rpcService.send(this, this.responseType);
    }

    /**
     * 异步发送
     *
     * @return
     */
    public CompletableFuture<T> sendAsync() {
        return this.rpcService.sendAsync(this, this.responseType);
    }

    /**
     * 观察
     *
     * @return
     */
    public Observable<T> observable() {
        return (new RemoteCall(this::send)).observable();
    }
}

