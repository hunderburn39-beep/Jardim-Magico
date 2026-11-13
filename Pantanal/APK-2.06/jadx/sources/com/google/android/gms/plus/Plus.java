package com.google.android.gms.plus;

import android.content.Context;
import android.os.Looper;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.internal.fc;
import com.google.android.gms.internal.fq;
import com.google.android.gms.internal.hy;
import com.google.android.gms.internal.hz;
import com.google.android.gms.internal.ia;
import com.google.android.gms.internal.ib;
import com.google.android.gms.plus.internal.PlusCommonExtras;
import com.google.android.gms.plus.internal.e;
import com.google.android.gms.plus.internal.h;
import java.util.HashSet;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public final class Plus {
    public static final Api.c<e> wx = new Api.c<>();
    static final Api.b<e, PlusOptions> wy = new Api.b<e, PlusOptions>() { // from class: com.google.android.gms.plus.Plus.1
        @Override // com.google.android.gms.common.api.Api.b
        public e a(Context context, Looper looper, fc fcVar, PlusOptions plusOptions, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
            if (plusOptions == null) {
                plusOptions = new PlusOptions();
            }
            return new e(context, looper, connectionCallbacks, onConnectionFailedListener, new h(fcVar.eC(), fcVar.eF(), (String[]) plusOptions.TK.toArray(new String[0]), new String[0], context.getPackageName(), context.getPackageName(), null, new PlusCommonExtras()));
        }

        @Override // com.google.android.gms.common.api.Api.b
        public int getPriority() {
            return 2;
        }
    };
    public static final Api<PlusOptions> API = new Api<>(wy, wx, new Scope[0]);
    public static final Scope SCOPE_PLUS_LOGIN = new Scope(Scopes.PLUS_LOGIN);
    public static final Scope SCOPE_PLUS_PROFILE = new Scope(Scopes.PLUS_ME);
    public static final Moments MomentsApi = new ia();
    public static final People PeopleApi = new ib();
    public static final Account AccountApi = new hy();
    public static final com.google.android.gms.plus.a TI = new hz();

    public static final class PlusOptions implements Api.ApiOptions.Optional {
        final String TJ;
        final Set<String> TK;

        public static final class Builder {
            String TJ;
            final Set<String> TK = new HashSet();

            public Builder addActivityTypes(String... activityTypes) {
                fq.b(activityTypes, "activityTypes may not be null.");
                for (String str : activityTypes) {
                    this.TK.add(str);
                }
                return this;
            }

            public PlusOptions build() {
                return new PlusOptions(this);
            }

            public Builder setServerClientId(String clientId) {
                this.TJ = clientId;
                return this;
            }
        }

        private PlusOptions() {
            this.TJ = null;
            this.TK = new HashSet();
        }

        private PlusOptions(Builder builder) {
            this.TJ = builder.TJ;
            this.TK = builder.TK;
        }

        public static Builder builder() {
            return new Builder();
        }
    }

    public static abstract class a<R extends Result> extends com.google.android.gms.common.api.a.b<R, e> {
        public a() {
            super(Plus.wx);
        }
    }

    private Plus() {
    }

    public static e a(GoogleApiClient googleApiClient, Api.c<e> cVar) {
        fq.b(googleApiClient != null, "GoogleApiClient parameter is required.");
        fq.a(googleApiClient.isConnected(), "GoogleApiClient must be connected.");
        e eVar = (e) googleApiClient.a(cVar);
        fq.a(eVar != null, "GoogleApiClient is not configured to use the Plus.API Api. Pass this into GoogleApiClient.Builder#addApi() to use this feature.");
        return eVar;
    }
}
