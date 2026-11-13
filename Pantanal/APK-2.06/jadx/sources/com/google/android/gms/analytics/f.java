package com.google.android.gms.analytics;

import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

/* JADX INFO: loaded from: classes.dex */
interface f {
    void bR();

    void bW();

    void bY();

    LinkedBlockingQueue<Runnable> bZ();

    Thread getThread();

    void q(Map<String, String> map);
}
