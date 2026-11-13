package com.fyber.requesters;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import com.fyber.Fyber;
import com.fyber.utils.d;
import com.fyber.utils.h;
import com.fyber.utils.m;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public abstract class Requester<T> {
    public static final String EXTRA_AD_FORMAT = "EXTRA_AD_FORMAT";
    protected Callback a;
    protected Map<String, Object> b;
    protected Handler c;

    protected abstract boolean a();

    protected abstract T b();

    public abstract void request(Context context);

    protected Requester(@NonNull Callback callback) {
        if (callback == null) {
            throw new IllegalArgumentException("callback cannot be null");
        }
        this.a = callback;
    }

    protected Requester(@NonNull Requester requester) {
        if (requester == null) {
            throw new IllegalArgumentException("requester cannot be null");
        }
        if (m.b(requester.b)) {
            this.b = new HashMap(requester.b);
        }
        this.a = requester.a;
        this.c = requester.c;
    }

    protected final Object a(@NonNull String str) {
        return (this.b == null || this.b.get(str) == null) ? Fyber.getConfigs().a(str) : this.b.get(str);
    }

    protected final Map<String, Object> c() {
        if (this.b == null) {
            this.b = new HashMap();
        }
        return this.b;
    }

    private HashMap<String, String> d() {
        if (this.b != null) {
            return (HashMap) c().get("CUSTOM_PARAMS_KEY");
        }
        return null;
    }

    public T addParameters(Map<String, String> map) {
        if (m.b(map)) {
            HashMap<String, String> mapD = d();
            if (mapD == null) {
                mapD = new HashMap<>();
                c().put("CUSTOM_PARAMS_KEY", mapD);
            }
            mapD.putAll(map);
        }
        return b();
    }

    public T addParameter(String str, String str2) {
        HashMap<String, String> mapD = d();
        if (mapD == null) {
            mapD = new HashMap<>();
            c().put("CUSTOM_PARAMS_KEY", mapD);
        }
        mapD.put(str, str2);
        return b();
    }

    public T clearParameters() {
        HashMap<String, String> mapD = d();
        if (mapD != null) {
            mapD.clear();
        }
        return b();
    }

    public T removeParameter(String str) {
        HashMap<String, String> mapD = d();
        if (mapD != null) {
            mapD.remove(str);
        }
        return b();
    }

    public T withPlacementId(String str) {
        c().put("PLACEMENT_ID_KEY", str);
        return b();
    }

    public T withCallback(@NonNull Callback callback) {
        if (callback == null) {
            throw new IllegalArgumentException("callback cannot be null");
        }
        this.a = callback;
        return b();
    }

    public T invokeCallbackOnHandler(Handler handler) {
        this.c = handler;
        return b();
    }

    protected final boolean a(@NonNull Context context) {
        if (context == null) {
            a(RequestError.NULL_CONTEXT_REFERENCE);
            return false;
        }
        if (!h.e()) {
            a(RequestError.DEVICE_NOT_SUPPORTED);
            return false;
        }
        if (!Fyber.getConfigs().d()) {
            a(RequestError.SDK_NOT_STARTED);
            return false;
        }
        if (!a()) {
            a(RequestError.MISMATCH_CALLBACK_TYPE);
            return false;
        }
        return true;
    }

    protected final void a(final RequestError requestError) {
        a(new d() { // from class: com.fyber.requesters.Requester.1
            @Override // com.fyber.utils.d
            public final void a() {
                Requester.this.a.onRequestError(requestError);
            }
        });
    }

    protected final String b(String str) {
        Object objA = a(str);
        if (objA == null) {
            return null;
        }
        return objA.toString();
    }

    protected final Boolean c(String str) {
        return (Boolean) a(str);
    }

    protected final HashMap<String, String> d(String str) {
        return (HashMap) a(str);
    }

    protected final void a(d dVar) {
        if (this.c != null) {
            this.c.post(dVar);
        } else {
            Fyber.getConfigs();
            Fyber.a.a(dVar);
        }
    }
}
