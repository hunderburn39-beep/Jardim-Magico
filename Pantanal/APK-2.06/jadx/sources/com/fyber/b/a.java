package com.fyber.b;

import android.text.TextUtils;
import com.fyber.Fyber;
import com.fyber.utils.FyberLogger;
import com.fyber.utils.StringUtils;
import com.fyber.utils.m;
import com.fyber.utils.s;
import java.io.IOException;
import java.util.Locale;
import java.util.Map;

/* JADX INFO: compiled from: AdEventNetworkOperation.java */
/* JADX INFO: loaded from: classes.dex */
public abstract class a extends g<Void> {
    private final String a;

    protected a(AbstractC0017a abstractC0017a) {
        super(abstractC0017a.a);
        this.a = abstractC0017a.b;
    }

    @Override // com.fyber.b.g
    protected final boolean a() {
        FyberLogger.d(a_(), this.a);
        return true;
    }

    public final void b() {
        if (Fyber.getConfigs().d()) {
            Fyber.getConfigs().a((Runnable) this);
        } else {
            FyberLogger.d(a_(), "It appears that Fyber SDK has not been started yet.");
        }
    }

    /* JADX INFO: renamed from: com.fyber.b.a$a, reason: collision with other inner class name */
    /* JADX INFO: compiled from: AdEventNetworkOperation.java */
    public static abstract class AbstractC0017a<T extends a, U> {
        protected final s a;
        protected String b;
        protected String c = "";
        private final com.fyber.ads.a.a d;

        protected abstract String a();

        protected abstract String b();

        protected abstract String c();

        protected abstract T d();

        public AbstractC0017a(com.fyber.ads.a.a aVar) {
            this.d = aVar;
            this.a = s.a(com.fyber.utils.e.a(c()), Fyber.getConfigs().e()).a().b().a("event", aVar.toString()).a("ad_format", b()).a("rewarded", a()).c();
        }

        /* JADX WARN: Multi-variable type inference failed */
        public final U a(String str) {
            if (StringUtils.notNullNorEmpty(str)) {
                this.a.a(this.d.toString(), str);
                this.c += "\n\t\tEvent attribute: " + str;
            }
            return this;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public final U a(Map<String, String> map) {
            if (m.b(map)) {
                this.a.a(map);
                this.c += "\n\t\tAdditional parameters:\n\t\t\t" + TextUtils.join("\n\t\t\t", map.entrySet());
            }
            return this;
        }

        public final T b(String str) {
            this.a.a(str);
            this.b = String.format(Locale.ENGLISH, "Notifying tracker of event=%s with request_id=%s", this.d, str) + this.c;
            return (T) d();
        }

        public final T a(com.fyber.ads.a.c cVar) {
            this.a.a(cVar.f()).b(cVar.e()).a("ad_id", cVar.a()).a("provider_type", cVar.b()).a(cVar.d());
            this.b = String.format(Locale.ENGLISH, "Notifying tracker of event=%s with request_id=%s", this.d, cVar.f()) + "\n\t\t" + String.format(Locale.ENGLISH, "for ad_id=%s and provider_type=%s ", cVar.a(), cVar.b()) + this.c;
            return (T) d();
        }
    }

    @Override // com.fyber.b.g
    protected final /* synthetic */ Void a(com.fyber.utils.i iVar) throws Exception {
        FyberLogger.d(a_(), "Event communication successful - " + (iVar.b() == 200));
        return null;
    }

    @Override // com.fyber.b.g
    protected final /* synthetic */ Void a(IOException iOException) {
        FyberLogger.e(a_(), "An exception occurred when trying to send the tracking event: " + iOException);
        return null;
    }
}
