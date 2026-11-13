package com.adcolony.sdk;

import android.support.annotation.NonNull;
import com.tapjoy.TJAdUnitConstants;
import java.util.concurrent.RejectedExecutionException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class AdColonyCustomMessage {
    String a;
    String b;

    public AdColonyCustomMessage(@NonNull String type, @NonNull String message) {
        if (au.d(type) || au.d(message)) {
            this.a = type;
            this.b = message;
        }
    }

    public String getMessage() {
        return this.b;
    }

    public String getType() {
        return this.a;
    }

    public AdColonyCustomMessage set(String type, String message) {
        this.a = type;
        this.b = message;
        return this;
    }

    public void send() {
        try {
            AdColony.a.execute(new Runnable() { // from class: com.adcolony.sdk.AdColonyCustomMessage.1
                @Override // java.lang.Runnable
                public void run() {
                    AdColony.a();
                    JSONObject jSONObjectA = s.a();
                    s.a(jSONObjectA, "type", AdColonyCustomMessage.this.a);
                    s.a(jSONObjectA, TJAdUnitConstants.String.MESSAGE, AdColonyCustomMessage.this.b);
                    new z("CustomMessage.native_send", 1, jSONObjectA).b();
                }
            });
        } catch (RejectedExecutionException e) {
        }
    }
}
