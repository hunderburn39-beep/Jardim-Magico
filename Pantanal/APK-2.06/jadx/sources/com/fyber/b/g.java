package com.fyber.b;

import android.support.annotation.NonNull;
import com.fyber.utils.FyberLogger;
import com.fyber.utils.s;
import java.io.IOException;
import java.util.concurrent.Callable;

/* JADX INFO: compiled from: NetworkOperation.java */
/* JADX INFO: loaded from: classes.dex */
public abstract class g<V> implements Runnable, Callable<V> {
    protected boolean c = true;
    protected s d;

    protected abstract V a(com.fyber.utils.i iVar) throws Exception;

    protected abstract V a(IOException iOException);

    protected abstract String a_();

    protected g(@NonNull s sVar) {
        this.d = sVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            c();
        } catch (IOException e) {
            FyberLogger.e(a_(), "An error occurred", e);
            a(e);
        } catch (Exception e2) {
            FyberLogger.e(a_(), "An error occurred", e2);
        }
    }

    @Override // java.util.concurrent.Callable
    public V call() throws Exception {
        if (this.c) {
            try {
                return c();
            } catch (IOException e) {
                FyberLogger.e(a_(), "An error occurred", e);
                return a(e);
            }
        }
        return c();
    }

    private V c() throws Exception {
        if (!a()) {
            return null;
        }
        String strG = this.d.g();
        FyberLogger.d(a_(), "sending request to " + strG);
        return a(com.fyber.utils.i.b(strG).a());
    }

    protected boolean a() {
        return true;
    }
}
