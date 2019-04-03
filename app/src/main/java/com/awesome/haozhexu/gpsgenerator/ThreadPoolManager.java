package com.awesome.haozhexu.gpsgenerator;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public final class ThreadPoolManager {

    private static final int NUM_OF_CORES = Runtime.getRuntime().availableProcessors();

    private final ThreadPoolExecutor backgroundTaskExecutor;

    private final Executor mainThreadExecutor;

    private static ThreadPoolManager threadPoolManager;

    public static ThreadPoolManager getInstance() {
        if (threadPoolManager == null) {
            synchronized (ThreadPoolManager.class) {
                if (threadPoolManager == null) {
                    threadPoolManager = new ThreadPoolManager();
                }
            }
        }
        return threadPoolManager;
    }

    private ThreadPoolManager() {
        backgroundTaskExecutor = new ThreadPoolExecutor(
                NUM_OF_CORES,
                NUM_OF_CORES,
                60L,
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<Runnable>()
        );

        mainThreadExecutor = new MainThreadExecutor();
    }

    public ThreadPoolExecutor getBackgroundTaskExecutor() {
        return backgroundTaskExecutor;
    }

    public Executor getMainThreadExecutor() {
        return mainThreadExecutor;
    }

    private static class MainThreadExecutor implements Executor {

        private final Handler handler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(@NonNull Runnable command) {
            handler.post(command);
        }
    }
}
