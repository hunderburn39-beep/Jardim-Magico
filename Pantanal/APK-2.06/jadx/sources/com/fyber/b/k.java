package com.fyber.b;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;
import com.fyber.Fyber;
import com.fyber.currency.VirtualCurrencyErrorResponse;
import com.fyber.currency.VirtualCurrencyResponse;
import com.fyber.requesters.RequestError;
import com.fyber.requesters.VirtualCurrencyCallback;
import com.fyber.utils.FyberLogger;
import com.fyber.utils.StringUtils;
import com.fyber.utils.r;
import com.fyber.utils.s;
import com.tapjoy.TJAdUnitConstants;
import com.tapjoy.TapjoyConstants;
import java.io.IOException;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.Callable;
import org.json.JSONObject;

/* JADX INFO: compiled from: VirtualCurrencyNetworkOperation.java */
/* JADX INFO: loaded from: classes.dex */
public final class k extends j<a, Void> {
    private static final com.fyber.currency.a.a b = new com.fyber.currency.a.a();
    private final VirtualCurrencyCallback e;
    private boolean f;
    private String g;
    private String h;
    private String i;
    private Context j;
    private Handler k;
    private boolean l;

    /* JADX INFO: compiled from: VirtualCurrencyNetworkOperation.java */
    public interface a {
    }

    @Override // com.fyber.b.j
    protected final /* synthetic */ Void a(a aVar) {
        boolean z;
        a aVar2 = aVar;
        if (aVar2 instanceof VirtualCurrencyResponse) {
            VirtualCurrencyResponse virtualCurrencyResponse = (VirtualCurrencyResponse) aVar2;
            com.fyber.currency.a.b bVarA = com.fyber.currency.a.b.a(this.j);
            String strA = bVarA.a();
            String currencyId = virtualCurrencyResponse.getCurrencyId();
            if (StringUtils.nullOrEmpty(this.g) && StringUtils.notNullNorEmpty(strA) && !strA.equalsIgnoreCase(currencyId)) {
                bVarA.a(currencyId);
                String strB = bVarA.b(currencyId);
                k kVar = new k(this);
                kVar.d(strB);
                kVar.d.a(TapjoyConstants.TJC_CURRENCY_ID, this.g);
                Fyber.getConfigs().a((Callable) kVar);
                z = false;
            } else {
                z = true;
            }
            if (!z) {
                return null;
            }
            String strA2 = com.fyber.currency.a.b.a(this.j).a();
            String currencyId2 = virtualCurrencyResponse.getCurrencyId();
            b.a(new VirtualCurrencyResponse(0.0d, virtualCurrencyResponse.getLatestTransactionId(), currencyId2, virtualCurrencyResponse.getCurrencyName(), virtualCurrencyResponse.isDefault()), currencyId2, strA2);
            com.fyber.currency.a.b.a(this.j).a(virtualCurrencyResponse);
            if (virtualCurrencyResponse.getDeltaOfCoins() > 0.0d && this.f) {
                String currencyName = virtualCurrencyResponse.getCurrencyName();
                if (!StringUtils.notNullNorEmpty(currencyName)) {
                    currencyName = r.a(Fyber.Settings.UIStringIdentifier.VCS_DEFAULT_CURRENCY);
                }
                final String str = String.format(Locale.ENGLISH, r.a(Fyber.Settings.UIStringIdentifier.VCS_COINS_NOTIFICATION), Double.valueOf(virtualCurrencyResponse.getDeltaOfCoins()), currencyName);
                a(new com.fyber.utils.d() { // from class: com.fyber.b.k.2
                    @Override // com.fyber.utils.d
                    public final void a() {
                        Toast.makeText(k.this.j, str, 1).show();
                    }
                });
            }
            a(virtualCurrencyResponse);
            return null;
        }
        VirtualCurrencyErrorResponse virtualCurrencyErrorResponse = (VirtualCurrencyErrorResponse) aVar2;
        String strA3 = com.fyber.currency.a.b.a(this.j).a();
        b.a(virtualCurrencyErrorResponse, StringUtils.nullOrEmpty(this.g) ? strA3 : this.g, strA3);
        a(virtualCurrencyErrorResponse);
        return null;
    }

    @Override // com.fyber.b.j
    protected final /* synthetic */ a a(String str) {
        return e(str);
    }

    private k(k kVar) {
        super(kVar.d, kVar.a);
        this.l = true;
        this.e = kVar.e;
        this.j = kVar.j;
        this.f = kVar.f;
        this.g = kVar.g;
        this.h = kVar.h;
        this.i = kVar.i;
        this.k = kVar.k;
        this.l = false;
    }

    public k(s sVar, VirtualCurrencyCallback virtualCurrencyCallback, String str, Context context) {
        super(sVar, str);
        this.l = true;
        this.e = virtualCurrencyCallback;
        this.j = context.getApplicationContext();
    }

    public final k a(boolean z) {
        this.f = z;
        return this;
    }

    public final k a(Map<String, String> map) {
        this.d.a(map);
        return this;
    }

    public final k b(String str) {
        if (StringUtils.notNullNorEmpty(str)) {
            this.g = str;
            this.d.a(TapjoyConstants.TJC_CURRENCY_ID, str);
        }
        return this;
    }

    public final k c(String str) {
        this.h = str;
        this.d.b(str);
        return this;
    }

    public final k d(String str) {
        this.i = str;
        this.d.a("ltid", str);
        return this;
    }

    public final k a(Handler handler) {
        this.k = handler;
        return this;
    }

    private void a(com.fyber.utils.d dVar) {
        if (this.k != null) {
            this.k.post(dVar);
        } else {
            Fyber.getConfigs();
            Fyber.a.a(dVar);
        }
    }

    private static a e(String str) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            return new VirtualCurrencyResponse(jSONObject.getDouble("delta_of_coins"), jSONObject.getString("latest_transaction_id"), jSONObject.getString(TapjoyConstants.TJC_CURRENCY_ID), jSONObject.getString(TapjoyConstants.TJC_CURRENCY_NAME), jSONObject.getBoolean("is_default"));
        } catch (Exception e) {
            return new VirtualCurrencyErrorResponse(VirtualCurrencyErrorResponse.ErrorType.ERROR_INVALID_RESPONSE, null, e.getMessage());
        }
    }

    private static VirtualCurrencyErrorResponse f(String str) {
        Exception exc;
        String string;
        VirtualCurrencyErrorResponse.ErrorType errorType;
        String message;
        try {
            JSONObject jSONObject = new JSONObject(str);
            string = jSONObject.getString("code");
            try {
                message = jSONObject.getString(TJAdUnitConstants.String.MESSAGE);
                errorType = VirtualCurrencyErrorResponse.ErrorType.SERVER_RETURNED_ERROR;
            } catch (Exception e) {
                exc = e;
                FyberLogger.w("VirtualCurrencyNetworkOperation", "An exception was triggered while parsing error response", exc);
                errorType = VirtualCurrencyErrorResponse.ErrorType.ERROR_OTHER;
                message = exc.getMessage();
            }
        } catch (Exception e2) {
            exc = e2;
            string = null;
        }
        return new VirtualCurrencyErrorResponse(errorType, string, message);
    }

    @Override // com.fyber.b.g
    protected final boolean a() {
        if (this.l) {
            com.fyber.currency.a.b bVarA = com.fyber.currency.a.b.a(this.j);
            a aVarA = b.a(this.g, bVarA.a());
            if (aVarA != null && !aVarA.equals(com.fyber.currency.a.a.a)) {
                if (aVarA instanceof VirtualCurrencyResponse) {
                    a((VirtualCurrencyResponse) aVarA);
                } else {
                    a((VirtualCurrencyErrorResponse) aVarA);
                }
                return false;
            }
            String strB = this.i;
            if (StringUtils.nullOrEmpty(strB)) {
                strB = bVarA.b(this.g);
            }
            this.d.a("ltid", strB);
        }
        return true;
    }

    @Override // com.fyber.b.g
    protected final String a_() {
        return "VirtualCurrencyNetworkOperation";
    }

    private void a(final VirtualCurrencyErrorResponse virtualCurrencyErrorResponse) {
        a(new com.fyber.utils.d() { // from class: com.fyber.b.k.3
            @Override // com.fyber.utils.d
            public final void a() {
                k.this.e.onError(virtualCurrencyErrorResponse);
            }
        });
    }

    private void a(final VirtualCurrencyResponse virtualCurrencyResponse) {
        a(new com.fyber.utils.d() { // from class: com.fyber.b.k.4
            @Override // com.fyber.utils.d
            public final void a() {
                k.this.e.onSuccess(virtualCurrencyResponse);
            }
        });
    }

    @Override // com.fyber.b.j
    protected final /* synthetic */ a a(int i, String str, String str2) {
        if (a(i)) {
            return f(str);
        }
        return new VirtualCurrencyErrorResponse(VirtualCurrencyErrorResponse.ErrorType.ERROR_INVALID_RESPONSE_SIGNATURE, null, "The signature received in the request did not match the expected one");
    }

    @Override // com.fyber.b.g
    protected final /* synthetic */ Object a(IOException iOException) {
        a(new com.fyber.utils.d() { // from class: com.fyber.b.k.1
            @Override // com.fyber.utils.d
            public final void a() {
                k.this.e.onRequestError(RequestError.CONNECTION_ERROR);
            }
        });
        return null;
    }
}
