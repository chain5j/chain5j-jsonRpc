package com.xwc1125.chain5j.jsonrpc.core;

import rx.Observable;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;

/**
 * Description: <br>
 *
 * @author xwc1125 <br>
 * @date 2019-01-04  11:15 <br>
 * <p>
 * Copyright (c) 2019 <br>
 */
public class RemoteCall<T> {
    private Callable<T> callable;

    public RemoteCall(Callable<T> callable) {
        this.callable = callable;
    }

    public T send() throws Exception {
        return this.callable.call();
    }

    public CompletableFuture<T> sendAsync() {
        return Async.run(this::send);
    }

    public Observable<T> observable() {
        return Observable.create((subscriber) -> {
            try {
                subscriber.onNext(this.send());
                subscriber.onCompleted();
            } catch (Exception var3) {
                subscriber.onError(var3);
            }

        });
    }
}
