package com.xwc1125.chain5j.jsonrpc.core;

import java.util.concurrent.*;

/**
 * Description: <br>
 *
 * @author xwc1125 <br>
 * @Copyright: Copyright (c) 2019 <br>
 * @date 2019-01-04  11:15 <br>
 */
public class Async {
    private static final ExecutorService executor = Executors.newCachedThreadPool();

    public Async() {
    }

    public static <T> CompletableFuture<T> run(Callable<T> callable) {
        CompletableFuture<T> result = new CompletableFuture();
        CompletableFuture.runAsync(() -> {
            try {
                result.complete(callable.call());
            } catch (Throwable var3) {
                result.completeExceptionally(var3);
            }

        }, executor);
        return result;
    }

    private static int getCpuCount() {
        return Runtime.getRuntime().availableProcessors();
    }

    public static ScheduledExecutorService defaultExecutorService() {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(getCpuCount());
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            shutdown(scheduledExecutorService);
        }));
        return scheduledExecutorService;
    }

    private static void shutdown(ExecutorService executorService) {
        executorService.shutdown();

        try {
            if (!executorService.awaitTermination(60L, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
                if (!executorService.awaitTermination(60L, TimeUnit.SECONDS)) {
                    System.err.println("Thread pool did not terminate");
                }
            }
        } catch (InterruptedException var2) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }

    }

    static {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            shutdown(executor);
        }));
    }
}

