package com.tapjoy.internal;

import android.app.Activity;
import android.content.Context;
import android.opengl.GLSurfaceView;
import com.tapjoy.TJAwardCurrencyListener;
import com.tapjoy.TJConnectListener;
import com.tapjoy.TJEarnedCurrencyListener;
import com.tapjoy.TJGetCurrencyBalanceListener;
import com.tapjoy.TJPlacement;
import com.tapjoy.TJPlacementListener;
import com.tapjoy.TJSetUserIDListener;
import com.tapjoy.TJSpendCurrencyListener;
import com.tapjoy.TJVideoListener;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public abstract class ed {
    private static final ed b;
    private static ed c;
    protected volatile boolean a = false;

    public abstract TJPlacement a(String str, TJPlacementListener tJPlacementListener);

    public abstract void a(float f);

    public abstract void a(int i);

    public abstract void a(int i, TJAwardCurrencyListener tJAwardCurrencyListener);

    public abstract void a(int i, TJSpendCurrencyListener tJSpendCurrencyListener);

    public abstract void a(int i, String str);

    public abstract void a(Activity activity);

    public abstract void a(GLSurfaceView gLSurfaceView);

    public abstract void a(TJEarnedCurrencyListener tJEarnedCurrencyListener);

    public abstract void a(TJGetCurrencyBalanceListener tJGetCurrencyBalanceListener);

    public abstract void a(TJVideoListener tJVideoListener);

    public abstract void a(String str);

    public abstract void a(String str, long j);

    public abstract void a(String str, TJSetUserIDListener tJSetUserIDListener);

    public abstract void a(String str, String str2);

    public abstract void a(String str, String str2, double d, String str3);

    public abstract void a(String str, String str2, long j);

    public abstract void a(String str, String str2, String str3, String str4);

    public abstract void a(String str, String str2, String str3, String str4, long j);

    public abstract void a(String str, String str2, String str3, String str4, String str5, long j);

    public abstract void a(String str, String str2, String str3, String str4, String str5, long j, String str6, long j2);

    public abstract void a(String str, String str2, String str3, String str4, String str5, long j, String str6, long j2, String str7, long j3);

    public abstract void a(String str, String str2, String str3, String str4, Map map);

    public abstract void a(Set set);

    public abstract void a(boolean z);

    public abstract boolean a(Context context, String str);

    public abstract boolean a(Context context, String str, Hashtable hashtable, TJConnectListener tJConnectListener);

    public abstract String b();

    public abstract void b(int i);

    public abstract void b(Activity activity);

    public abstract void b(String str);

    public abstract void b(String str, String str2, String str3, String str4);

    public abstract void b(boolean z);

    public abstract float c();

    public abstract void c(Activity activity);

    public abstract void c(String str);

    public abstract void d();

    public abstract void d(String str);

    public abstract void e();

    public abstract void e(String str);

    public abstract Set f();

    public abstract void f(String str);

    public abstract void g();

    public abstract void g(String str);

    public abstract boolean h();

    public abstract boolean i();

    ed() {
    }

    static {
        er.a();
        em.a();
        ef efVar = new ef();
        b = efVar;
        c = efVar;
    }

    public static ed a() {
        return c;
    }
}
