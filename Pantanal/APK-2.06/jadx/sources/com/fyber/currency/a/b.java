package com.fyber.currency.a;

import android.content.Context;
import android.content.SharedPreferences;
import com.fyber.Fyber;
import com.fyber.currency.VirtualCurrencyResponse;
import com.fyber.utils.StringUtils;

/* JADX INFO: compiled from: VirtualCurrencyPrefManager.java */
/* JADX INFO: loaded from: classes.dex */
public class b {
    private static b c;
    private final com.fyber.a.a a = Fyber.getConfigs().e();
    private final SharedPreferences b;

    public static b a(Context context) {
        if (c == null) {
            synchronized (b.class) {
                if (c == null) {
                    c = new b(context);
                }
            }
        }
        return c;
    }

    private b(Context context) {
        this.b = context.getSharedPreferences("FyberPreferences", 0);
    }

    private String c(String str) {
        if (StringUtils.nullOrEmpty(str)) {
            str = "";
        }
        return "STATE_LATEST_CURRENCY_TRANSACTION_ID_" + this.a.a() + "_" + this.a.b() + "_STATE_LATEST_TRANSACTION_CURRENCY_ID_" + str;
    }

    public final String a() {
        return this.b.getString("DEFAULT_CURRENCY_ID_KEY_" + this.a.a(), "");
    }

    public final void a(String str) {
        this.b.edit().putString("DEFAULT_CURRENCY_ID_KEY_" + this.a.a(), str).commit();
    }

    public final String b(String str) {
        if (StringUtils.nullOrEmpty(str)) {
            str = a();
        }
        if (StringUtils.nullOrEmpty(str)) {
            return "NO_TRANSACTION";
        }
        return this.b.getString(c(str), "NO_TRANSACTION");
    }

    public final void a(VirtualCurrencyResponse virtualCurrencyResponse) {
        String latestTransactionId = virtualCurrencyResponse.getLatestTransactionId();
        if (StringUtils.notNullNorEmpty(latestTransactionId) && !latestTransactionId.equals("NO_TRANSACTION")) {
            SharedPreferences.Editor editorEdit = this.b.edit();
            editorEdit.putString(c(virtualCurrencyResponse.getCurrencyId()), latestTransactionId);
            editorEdit.commit();
        }
        if (virtualCurrencyResponse.isDefault()) {
            a(virtualCurrencyResponse.getCurrencyId());
        }
    }
}
