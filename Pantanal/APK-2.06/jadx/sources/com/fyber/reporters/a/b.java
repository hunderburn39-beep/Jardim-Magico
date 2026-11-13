package com.fyber.reporters.a;

import android.support.annotation.NonNull;
import com.fyber.Fyber;
import com.fyber.exceptions.IdException;
import com.fyber.reporters.Reporter;
import com.fyber.utils.s;

/* JADX INFO: compiled from: AppStartReporter.java */
/* JADX INFO: loaded from: classes.dex */
public final class b extends Reporter {
    public static b a(@NonNull String str) throws IdException {
        if (com.fyber.a.a.b(str)) {
            throw new IdException("Advertiser AppID cannot be used to report an appstart");
        }
        return new b(str);
    }

    private b(@NonNull String str) {
        super(str);
    }

    @Override // com.fyber.reporters.Reporter
    protected final s a(s sVar) {
        return sVar.a(true);
    }

    @Override // com.fyber.reporters.Reporter
    protected final String c() {
        return "installs";
    }

    @Override // com.fyber.reporters.Reporter
    protected final String d() {
        return "InstallReporter";
    }

    @Override // com.fyber.reporters.Reporter
    protected final c e() {
        return new c() { // from class: com.fyber.reporters.a.b.1
            @Override // com.fyber.reporters.a.c
            public final void a() {
            }

            @Override // com.fyber.reporters.a.c
            protected final String b() {
                return "InstallReporter";
            }
        };
    }

    @Override // com.fyber.reporters.Reporter
    protected final com.fyber.a.a b() {
        return Fyber.getConfigs().e();
    }
}
