package com.xwc1125.chain5j.jsonrpc;

import com.xwc1125.chain5j.protocol.ObjectMapperFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xwc1125.chain5j.jsonrpc.core.Async;
import com.xwc1125.chain5j.protocol.entity.Request;
import com.xwc1125.chain5j.protocol.entity.Response;
import rx.Notification;
import rx.Observable;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.CompletableFuture;

/**
 * Description: <br>
 *
 * @author xwc1125 <br>
 * @date 2019-01-04  11:11 <br>
 * <p>
 * Copyright (c) 2019 <br>
 */
public abstract class Service implements RpcService {
    protected final ObjectMapper objectMapper;

    public Service(boolean includeRawResponses) {
        this.objectMapper = ObjectMapperFactory.getObjectMapper(includeRawResponses);
    }

    protected abstract InputStream performIO(String var1) throws IOException;

    @Override
    public <T extends Response> T send(Request request, Class<T> responseType) throws IOException {
        String payload = this.objectMapper.writeValueAsString(request);
        InputStream result = this.performIO(payload);
        Throwable throwable = null;

        Response response;
        try {
            if (result != null) {
                T t = this.objectMapper.readValue(result, responseType);
                response = (Response) t;
                return (T) response;
            }

            response = null;
        } catch (Throwable throwable1) {
            throwable = throwable1;
            throw throwable1;
        } finally {
            if (result != null) {
                if (throwable != null) {
                    try {
                        result.close();
                    } catch (Throwable throwable1) {
                        throwable.addSuppressed(throwable1);
                    }
                } else {
                    result.close();
                }
            }

        }

        return (T) response;
    }

    @Override
    public <T extends Response> CompletableFuture<T> sendAsync(Request jsonRpc20Request, Class<T> responseType) {
        return Async.run(() -> {
            return this.send(jsonRpc20Request, responseType);
        });
    }

    @Override
    public <T extends Notification<?>> Observable<T> subscribe(Request request, String unsubscribeMethod, Class<T> responseType) {
        throw new UnsupportedOperationException(String.format("Service %s does not support subscriptions", this.getClass().getSimpleName()));
    }
}

