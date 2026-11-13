package com.fyber.requesters;

import android.content.Context;
import android.support.annotation.NonNull;
import com.fyber.Fyber;
import com.fyber.a.a;
import com.fyber.b.k;
import com.fyber.utils.StringUtils;
import com.fyber.utils.e;
import com.fyber.utils.s;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

/* JADX INFO: loaded from: classes.dex */
public class VirtualCurrencyRequester extends Requester<VirtualCurrencyRequester> {
    @Override // com.fyber.requesters.Requester
    protected final /* bridge */ /* synthetic */ VirtualCurrencyRequester b() {
        return this;
    }

    public static VirtualCurrencyRequester create(@NonNull VirtualCurrencyCallback virtualCurrencyCallback) {
        return new VirtualCurrencyRequester(virtualCurrencyCallback);
    }

    public static VirtualCurrencyRequester from(@NonNull Requester requester) {
        return new VirtualCurrencyRequester(requester);
    }

    public VirtualCurrencyRequester withTransactionId(String str) {
        c().put("TRANSACTION_ID", str);
        return this;
    }

    public VirtualCurrencyRequester forCurrencyId(String str) {
        c().put("CURRENCY_ID", str);
        return this;
    }

    public VirtualCurrencyRequester withVirtualCurrencyCallback(VirtualCurrencyCallback virtualCurrencyCallback) {
        return withCallback(virtualCurrencyCallback);
    }

    public VirtualCurrencyRequester notifyUserOnReward(boolean z) {
        c().put("NOTIFY_USER_ON_REWARD", Boolean.valueOf(z));
        return this;
    }

    @Override // com.fyber.requesters.Requester
    public void request(Context context) {
        if (a(context)) {
            a aVarE = Fyber.getConfigs().e();
            String strC = aVarE.c();
            if (StringUtils.nullOrEmpty(strC)) {
                a(RequestError.SECURITY_TOKEN_NOT_PROVIDED);
                return;
            }
            VirtualCurrencyCallback virtualCurrencyCallback = (VirtualCurrencyCallback) this.a;
            String strB = b("CURRENCY_ID");
            HashMap<String, String> mapD = d("CUSTOM_PARAMS_KEY");
            String strB2 = b("TRANSACTION_ID");
            Fyber.getConfigs().a((Callable) new k(s.a(e.a("vcs"), aVarE).a().d(), virtualCurrencyCallback, strC, context).a(c("NOTIFY_USER_ON_REWARD").booleanValue()).a((Map<String, String>) mapD).d(strB2).a(this.c).b(strB).c(b("PLACEMENT_ID_KEY")));
        }
    }

    private VirtualCurrencyRequester(Requester requester) {
        super(requester);
    }

    private VirtualCurrencyRequester(@NonNull VirtualCurrencyCallback virtualCurrencyCallback) {
        super(virtualCurrencyCallback);
    }

    @Override // com.fyber.requesters.Requester
    protected final boolean a() {
        return this.a instanceof VirtualCurrencyCallback;
    }
}
