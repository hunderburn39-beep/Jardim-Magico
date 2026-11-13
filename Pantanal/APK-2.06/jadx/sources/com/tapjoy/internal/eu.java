package com.tapjoy.internal;

import android.content.Context;
import android.content.SharedPreferences;
import com.tapjoy.TapjoyConstants;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

/* JADX INFO: loaded from: classes.dex */
public final class eu {
    private static final eu b;
    private static eu c;
    public final es a = new es();
    private Context d;

    static {
        eu euVar = new eu();
        b = euVar;
        c = euVar;
    }

    public static eu a() {
        return c;
    }

    public static es b() {
        return c.a;
    }

    eu() {
    }

    public final synchronized void a(Context context) {
        if (context != null) {
            if (this.d == null) {
                this.d = context;
                SharedPreferences sharedPreferencesC = c();
                String string = c().getString(TapjoyConstants.PREF_SERVER_PROVIDED_CONFIGURATIONS, null);
                if (string != null) {
                    try {
                        br brVarB = br.b(string);
                        try {
                            Map mapD = brVarB.d();
                            brVarB.close();
                            this.a.a(mapD);
                        } catch (Throwable th) {
                            brVarB.close();
                            throw th;
                        }
                    } catch (Exception e) {
                        sharedPreferencesC.edit().remove(TapjoyConstants.PREF_SERVER_PROVIDED_CONFIGURATIONS).commit();
                    }
                }
                Observer observer = new Observer() { // from class: com.tapjoy.internal.eu.1
                    @Override // java.util.Observer
                    public final void update(Observable observable, Object data) {
                        Object objCast;
                        fa.a(eu.this.a.a("usage_tracking_enabled", false));
                        Iterator it = eu.this.a.b.iterator();
                        while (it.hasNext()) {
                            Object objA = ((fe.a) it.next()).a("usage_tracking_exclude");
                            if (objA != null && List.class.isInstance(objA)) {
                                objCast = List.class.cast(objA);
                                fa.a((Collection) objCast);
                            }
                        }
                        objCast = null;
                        fa.a((Collection) objCast);
                    }
                };
                this.a.addObserver(observer);
                observer.update(this.a, null);
            }
        }
    }

    public final SharedPreferences c() {
        return this.d.getSharedPreferences(TapjoyConstants.TJC_PREFERENCE, 0);
    }
}
