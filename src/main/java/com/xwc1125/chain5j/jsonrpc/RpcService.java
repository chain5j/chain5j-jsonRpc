package com.xwc1125.chain5j.jsonrpc;

import com.xwc1125.chain5j.protocol.entity.Request;
import com.xwc1125.chain5j.protocol.entity.Response;
import rx.Notification;
import rx.Observable;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

/**
 * Description: <br>
 *
 * @author xwc1125 <br>
 * @date 2019-01-04  11:10 <br>
 * <p>
 * Copyright (c) 2019 <br>
 */
public interface RpcService {
    /**
     * 同步发送
     *
     * @param request
     * @param clz
     * @param <T>
     * @return
     * @throws IOException
     */
    <T extends Response> Response send(Request request, Class<T> clz) throws IOException;

    /**
     * 异步发送
     *
     * @param request
     * @param clz
     * @param <T>
     * @return
     */
    <T extends Response> CompletableFuture<T> sendAsync(Request request, Class<T> clz);

    /**
     * 订阅
     *
     * @param request
     * @param method
     * @param clz
     * @param <T>
     * @return
     */
    <T extends Notification<?>> Observable<T> subscribe(Request request, String method, Class<T> clz);

    /**
     * 关闭
     *
     * @throws IOException
     */
    void close() throws IOException;
}
