package com.fyber.reporters;

import android.support.annotation.NonNull;
import com.fyber.reporters.a.c;

/* JADX INFO: loaded from: classes.dex */
public class InstallReporter extends AdvertiserReporter {
    public static InstallReporter create(@NonNull String str) {
        return new InstallReporter(str);
    }

    private InstallReporter(@NonNull String str) {
        super(str);
    }

    @Override // com.fyber.reporters.Reporter
    protected final String c() {
        return "installs";
    }

    @Override // com.fyber.reporters.AdvertiserReporter
    protected final String a() {
        return this.a.b(null);
    }

    @Override // com.fyber.reporters.Reporter
    protected final String d() {
        return "InstallReporter";
    }

    @Override // com.fyber.reporters.Reporter
    protected final c e() {
        return new c() { // from class: com.fyber.reporters.InstallReporter.1
            @Override // com.fyber.reporters.a.c
            public final void a() {
                InstallReporter.this.a.a(null);
            }

            @Override // com.fyber.reporters.a.c
            protected final String b() {
                return "InstallReporter";
            }
        };
    }
}
