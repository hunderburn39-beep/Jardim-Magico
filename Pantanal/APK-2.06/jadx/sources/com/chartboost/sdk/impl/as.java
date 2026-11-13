package com.chartboost.sdk.impl;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.PriorityBlockingQueue;

/* JADX INFO: loaded from: classes.dex */
public class as {
    public HttpURLConnection a(ac<?> acVar) throws IOException {
        return (HttpURLConnection) new URL(acVar.c).openConnection();
    }

    public ar a(at atVar, PriorityBlockingQueue<ac<?>> priorityBlockingQueue) {
        return new ar(this, priorityBlockingQueue);
    }
}
