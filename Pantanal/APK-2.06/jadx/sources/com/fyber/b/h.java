package com.fyber.b;

import android.support.annotation.NonNull;
import com.fyber.Fyber;
import com.fyber.utils.FyberLogger;
import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/* JADX INFO: compiled from: RemoteFileOperation.java */
/* JADX INFO: loaded from: classes.dex */
public final class h implements Callable<String> {
    private String a;

    public static Future<String> a(@NonNull String str) {
        if (Fyber.getConfigs().d()) {
            return Fyber.getConfigs().a(new h(str));
        }
        return null;
    }

    private h(@NonNull String str) {
        this.a = str;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @Override // java.util.concurrent.Callable
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public String call() throws Exception {
        try {
            return com.fyber.utils.i.b(this.a).a().c();
        } catch (IOException e) {
            FyberLogger.e("RemoteFileOperation", e.getMessage(), e);
            return null;
        }
    }
}
