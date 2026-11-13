package com.fyber.reporters;

import android.support.annotation.NonNull;
import com.fyber.exceptions.IdException;
import com.fyber.reporters.a.c;
import com.fyber.utils.k;
import com.fyber.utils.s;

/* JADX INFO: loaded from: classes.dex */
public class RewardedActionReporter extends AdvertiserReporter {
    private String d;

    public static RewardedActionReporter create(@NonNull String str, @NonNull String str2) throws IdException {
        k.a(str2);
        return new RewardedActionReporter(str, str2);
    }

    private RewardedActionReporter(@NonNull String str, @NonNull String str2) {
        super(str);
        this.d = str2;
    }

    @Override // com.fyber.reporters.AdvertiserReporter
    protected final String a() {
        return this.a.b(this.d);
    }

    @Override // com.fyber.reporters.Reporter
    protected final String c() {
        return "actions";
    }

    @Override // com.fyber.reporters.AdvertiserReporter, com.fyber.reporters.Reporter
    protected final s a(s sVar) {
        return super.a(sVar.a("action_id", this.d));
    }

    @Override // com.fyber.reporters.Reporter
    protected final c e() {
        return new c() { // from class: com.fyber.reporters.RewardedActionReporter.1
            @Override // com.fyber.reporters.a.c
            public final void a() {
                RewardedActionReporter.this.a.a(RewardedActionReporter.this.d);
            }

            @Override // com.fyber.reporters.a.c
            protected final String b() {
                return "RewardedActionReporter";
            }
        };
    }

    @Override // com.fyber.reporters.Reporter
    protected final String d() {
        return "RewardedActionReporter";
    }
}
