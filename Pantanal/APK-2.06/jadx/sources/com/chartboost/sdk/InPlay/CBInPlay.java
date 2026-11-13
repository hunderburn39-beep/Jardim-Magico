package com.chartboost.sdk.InPlay;

import android.graphics.Bitmap;
import android.text.TextUtils;
import com.chartboost.sdk.Libraries.CBLogging;
import com.chartboost.sdk.Model.CBError;
import com.chartboost.sdk.Model.c;
import com.chartboost.sdk.b;
import com.chartboost.sdk.h;
import com.chartboost.sdk.i;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public final class CBInPlay {
    private final a a;
    private String b;
    private Bitmap c;
    private String d;
    private JSONObject e;

    CBInPlay(a manager) {
        this.a = manager;
    }

    public void show() {
        this.a.a(this);
    }

    public void click() {
        this.a.b(this);
    }

    public String getLocation() {
        return this.b;
    }

    protected void a(String str) {
        this.b = str;
    }

    public Bitmap getAppIcon() {
        return this.c;
    }

    protected void a(Bitmap bitmap) {
        this.c = bitmap;
    }

    public String getAppName() {
        return this.d;
    }

    protected void b(String str) {
        this.d = str;
    }

    protected JSONObject a() {
        return this.e;
    }

    protected void a(JSONObject jSONObject) {
        this.e = jSONObject;
    }

    public static void cacheInPlay(String location) {
        if (b.a()) {
            a aVar = h.a().b;
            c cVar = aVar.c.get();
            if ((cVar.u && cVar.v) || (cVar.e && cVar.f)) {
                if (TextUtils.isEmpty(location)) {
                    CBLogging.b("CBInPlay", "Inplay location cannot be empty");
                    if (i.c != null) {
                        i.c.didFailToLoadInPlay(location, CBError.CBImpressionError.INVALID_LOCATION);
                        return;
                    }
                    return;
                }
                aVar.a(location);
            }
        }
    }

    public static boolean hasInPlay(String location) {
        if (!b.a()) {
            return false;
        }
        if (TextUtils.isEmpty(location)) {
            CBLogging.b("CBInPlay", "Inplay location cannot be empty");
            if (i.c == null) {
                return false;
            }
            i.c.didFailToLoadInPlay(location, CBError.CBImpressionError.INVALID_LOCATION);
            return false;
        }
        return h.a().b.b(location);
    }

    public static CBInPlay getInPlay(String location) {
        if (!b.a()) {
            return null;
        }
        a aVar = h.a().b;
        c cVar = aVar.c.get();
        if ((!cVar.u || !cVar.v) && (!cVar.e || !cVar.f)) {
            return null;
        }
        if (TextUtils.isEmpty(location)) {
            CBLogging.b("CBInPlay", "Inplay location cannot be empty");
            if (i.c != null) {
                i.c.didFailToLoadInPlay(location, CBError.CBImpressionError.INVALID_LOCATION);
            }
            return null;
        }
        return aVar.c(location);
    }
}
