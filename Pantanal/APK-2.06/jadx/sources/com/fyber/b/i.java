package com.fyber.b;

import android.support.annotation.NonNull;
import com.fyber.utils.FyberLogger;
import com.fyber.utils.s;
import java.io.IOException;

/* JADX INFO: compiled from: ReporterOperation.java */
/* JADX INFO: loaded from: classes.dex */
public final class i implements Runnable {
    private s a;
    private com.fyber.reporters.a.c b;

    public i(@NonNull s sVar, com.fyber.reporters.a.c cVar) {
        this.a = sVar;
        this.b = cVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        try {
            String strG = this.a.g();
            FyberLogger.d("ReporterOperation", "event will be sent to " + strG);
            int iB = com.fyber.utils.i.b(strG).a().b();
            FyberLogger.d("ReporterOperation", "Server returned status code: " + iB);
            if (iB == 200) {
                this.b.a();
            } else {
                this.b.a(iB);
            }
        } catch (IOException e) {
            FyberLogger.e("ReporterOperation", "An error occurred", e);
        }
    }
}
