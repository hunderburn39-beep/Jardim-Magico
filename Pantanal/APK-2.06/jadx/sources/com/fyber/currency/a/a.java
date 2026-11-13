package com.fyber.currency.a;

import com.fyber.b.k;
import com.fyber.currency.VirtualCurrencyErrorResponse;
import com.fyber.utils.FyberLogger;
import java.util.Calendar;
import java.util.HashMap;

/* JADX INFO: compiled from: VirtualCurrencyCache.java */
/* JADX INFO: loaded from: classes.dex */
public final class a {
    public static final VirtualCurrencyErrorResponse a = new VirtualCurrencyErrorResponse(VirtualCurrencyErrorResponse.ErrorType.ERROR_OTHER, "", "Unknown error");
    private HashMap<String, C0024a> b = new HashMap<>();

    public final k.a a(String str, String str2) {
        byte b = 0;
        Calendar calendar = Calendar.getInstance();
        C0024a c0024a = this.b.get(str != null ? str : str2);
        if (c0024a == null) {
            C0024a c0024a2 = new C0024a(this, b);
            c0024a2.b = calendar;
            this.b.put(str != null ? str : str2, c0024a2);
            c0024a = c0024a2;
        }
        if (!calendar.before(c0024a.b)) {
            calendar.add(13, 15);
            C0024a c0024a3 = this.b.get(str != null ? str : str2);
            if (c0024a3 == null) {
                c0024a3 = new C0024a(this, b);
                HashMap<String, C0024a> map = this.b;
                if (str == null) {
                    str = str2;
                }
                map.put(str, c0024a3);
            }
            c0024a3.b = calendar;
            return null;
        }
        C0024a c0024a4 = this.b.get(str != null ? str : str2);
        if (c0024a4 == null) {
            c0024a4 = new C0024a(this, b);
            c0024a4.b = Calendar.getInstance();
            HashMap<String, C0024a> map2 = this.b;
            if (str == null) {
                str = str2;
            }
            map2.put(str, c0024a4);
        }
        k.a aVar = c0024a4.c;
        if (aVar == null) {
            return a;
        }
        FyberLogger.d("VCSCache", "The VCS was queried less than 15s ago.Replying with cached response");
        return aVar;
    }

    /* JADX INFO: renamed from: com.fyber.currency.a.a$a, reason: collision with other inner class name */
    /* JADX INFO: compiled from: VirtualCurrencyCache.java */
    private class C0024a {
        private Calendar b;
        private k.a c;

        private C0024a() {
        }

        /* synthetic */ C0024a(a aVar, byte b) {
            this();
        }
    }

    public final void a(k.a aVar, String str, String str2) {
        C0024a c0024a = this.b.get(str != null ? str : str2);
        if (c0024a == null) {
            c0024a = new C0024a(this, (byte) 0);
            HashMap<String, C0024a> map = this.b;
            if (str == null) {
                str = str2;
            }
            map.put(str, c0024a);
        }
        c0024a.c = aVar;
    }
}
