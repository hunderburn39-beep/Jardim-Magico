package com.fyber.reporters;

import android.content.Context;
import android.support.annotation.NonNull;
import com.fyber.reporters.a.a;
import com.fyber.utils.StringUtils;
import com.fyber.utils.s;

/* JADX INFO: loaded from: classes.dex */
public abstract class AdvertiserReporter extends Reporter {
    protected a a;

    protected abstract String a();

    protected AdvertiserReporter(@NonNull String str) {
        super(str);
    }

    @Override // com.fyber.reporters.Reporter
    public boolean report(Context context) {
        this.a = new a(context);
        return super.report(context);
    }

    @Override // com.fyber.reporters.Reporter
    protected s a(s sVar) {
        String strA = this.a.a();
        if (StringUtils.notNullNorEmpty(strA)) {
            sVar.a("subid", strA);
        }
        String strB = this.a.b();
        if (StringUtils.notNullNorEmpty(strB)) {
            sVar.a("install_referrer", strB);
        }
        return sVar.a("answer_received", a()).a(false);
    }

    @Override // com.fyber.reporters.Reporter
    protected final com.fyber.a.a b() {
        return new com.fyber.a.a.C0016a(this.b).a();
    }
}
