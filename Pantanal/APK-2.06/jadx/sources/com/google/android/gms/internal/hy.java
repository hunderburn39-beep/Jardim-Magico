package com.google.android.gms.internal;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.plus.Account;
import com.google.android.gms.plus.Plus;

/* JADX INFO: loaded from: classes.dex */
public final class hy implements Account {

    private static abstract class a extends Plus.a<Status> {
        private a() {
        }

        @Override // com.google.android.gms.common.api.a.AbstractC0032a
        /* JADX INFO: renamed from: f, reason: merged with bridge method [inline-methods] */
        public Status d(Status status) {
            return status;
        }
    }

    private static com.google.android.gms.plus.internal.e a(GoogleApiClient googleApiClient, Api.c<com.google.android.gms.plus.internal.e> cVar) {
        fq.b(googleApiClient != null, "GoogleApiClient parameter is required.");
        fq.a(googleApiClient.isConnected(), "GoogleApiClient must be connected.");
        com.google.android.gms.plus.internal.e eVar = (com.google.android.gms.plus.internal.e) googleApiClient.a(cVar);
        fq.a(eVar != null, "GoogleApiClient is not configured to use the Plus.API Api. Pass this into GoogleApiClient.Builder#addApi() to use this feature.");
        return eVar;
    }

    @Override // com.google.android.gms.plus.Account
    public void clearDefaultAccount(GoogleApiClient googleApiClient) {
        a(googleApiClient, Plus.wx).clearDefaultAccount();
    }

    @Override // com.google.android.gms.plus.Account
    public String getAccountName(GoogleApiClient googleApiClient) {
        return a(googleApiClient, Plus.wx).getAccountName();
    }

    @Override // com.google.android.gms.plus.Account
    public PendingResult<Status> revokeAccessAndDisconnect(GoogleApiClient googleApiClient) {
        return googleApiClient.b(new a() { // from class: com.google.android.gms.internal.hy.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.google.android.gms.common.api.a.b
            public void a(com.google.android.gms.plus.internal.e eVar) {
                eVar.n(this);
            }
        });
    }
}
