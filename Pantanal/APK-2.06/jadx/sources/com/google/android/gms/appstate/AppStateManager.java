package com.google.android.gms.appstate;

import android.content.Context;
import android.os.Looper;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Releasable;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.ei;
import com.google.android.gms.internal.fc;
import com.google.android.gms.internal.fq;

/* JADX INFO: loaded from: classes.dex */
public final class AppStateManager {
    static final Api.c<ei> wx = new Api.c<>();
    private static final Api.b<ei, Api.ApiOptions.NoOptions> wy = new Api.b<ei, Api.ApiOptions.NoOptions>() { // from class: com.google.android.gms.appstate.AppStateManager.1
        @Override // com.google.android.gms.common.api.Api.b
        public ei a(Context context, Looper looper, fc fcVar, Api.ApiOptions.NoOptions noOptions, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
            return new ei(context, looper, connectionCallbacks, onConnectionFailedListener, fcVar.eC(), (String[]) fcVar.eE().toArray(new String[0]));
        }

        @Override // com.google.android.gms.common.api.Api.b
        public int getPriority() {
            return Integer.MAX_VALUE;
        }
    };
    public static final Scope SCOPE_APP_STATE = new Scope(Scopes.APP_STATE);
    public static final Api<Api.ApiOptions.NoOptions> API = new Api<>(wy, wx, SCOPE_APP_STATE);

    public interface StateConflictResult extends Releasable, Result {
        byte[] getLocalData();

        String getResolvedVersion();

        byte[] getServerData();

        int getStateKey();
    }

    public interface StateDeletedResult extends Result {
        int getStateKey();
    }

    public interface StateListResult extends Result {
        AppStateBuffer getStateBuffer();
    }

    public interface StateLoadedResult extends Releasable, Result {
        byte[] getLocalData();

        int getStateKey();
    }

    public interface StateResult extends Releasable, Result {
        StateConflictResult getConflictResult();

        StateLoadedResult getLoadedResult();
    }

    public static abstract class a<R extends Result> extends com.google.android.gms.common.api.a.b<R, ei> {
        public a() {
            super(AppStateManager.wx);
        }
    }

    private static abstract class b extends a<StateDeletedResult> {
        private b() {
        }
    }

    private static abstract class c extends a<StateListResult> {
        private c() {
        }

        @Override // com.google.android.gms.common.api.a.AbstractC0032a
        /* JADX INFO: renamed from: e, reason: merged with bridge method [inline-methods] */
        public StateListResult d(final Status status) {
            return new StateListResult() { // from class: com.google.android.gms.appstate.AppStateManager.c.1
                @Override // com.google.android.gms.appstate.AppStateManager.StateListResult
                public AppStateBuffer getStateBuffer() {
                    return new AppStateBuffer(null);
                }

                @Override // com.google.android.gms.common.api.Result
                public Status getStatus() {
                    return status;
                }
            };
        }
    }

    private static abstract class d extends a<Status> {
        private d() {
        }

        @Override // com.google.android.gms.common.api.a.AbstractC0032a
        /* JADX INFO: renamed from: f, reason: merged with bridge method [inline-methods] */
        public Status d(Status status) {
            return status;
        }
    }

    private static abstract class e extends a<StateResult> {
        private e() {
        }

        @Override // com.google.android.gms.common.api.a.AbstractC0032a
        /* JADX INFO: renamed from: g, reason: merged with bridge method [inline-methods] */
        public StateResult d(Status status) {
            return AppStateManager.a(status);
        }
    }

    private AppStateManager() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static StateResult a(final Status status) {
        return new StateResult() { // from class: com.google.android.gms.appstate.AppStateManager.2
            @Override // com.google.android.gms.appstate.AppStateManager.StateResult
            public StateConflictResult getConflictResult() {
                return null;
            }

            @Override // com.google.android.gms.appstate.AppStateManager.StateResult
            public StateLoadedResult getLoadedResult() {
                return null;
            }

            @Override // com.google.android.gms.common.api.Result
            public Status getStatus() {
                return status;
            }

            @Override // com.google.android.gms.common.api.Releasable
            public void release() {
            }
        };
    }

    public static ei a(GoogleApiClient googleApiClient) {
        fq.b(googleApiClient != null, "GoogleApiClient parameter is required.");
        fq.a(googleApiClient.isConnected(), "GoogleApiClient must be connected.");
        ei eiVar = (ei) googleApiClient.a(wx);
        fq.a(eiVar != null, "GoogleApiClient is not configured to use the AppState API. Pass AppStateManager.API into GoogleApiClient.Builder#addApi() to use this feature.");
        return eiVar;
    }

    public static PendingResult<StateDeletedResult> delete(GoogleApiClient googleApiClient, final int stateKey) {
        return googleApiClient.b(new b() { // from class: com.google.android.gms.appstate.AppStateManager.5
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super();
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.google.android.gms.common.api.a.b
            public void a(ei eiVar) {
                eiVar.a(this, stateKey);
            }

            @Override // com.google.android.gms.common.api.a.AbstractC0032a
            /* JADX INFO: renamed from: c, reason: merged with bridge method [inline-methods] */
            public StateDeletedResult d(final Status status) {
                return new StateDeletedResult() { // from class: com.google.android.gms.appstate.AppStateManager.5.1
                    @Override // com.google.android.gms.appstate.AppStateManager.StateDeletedResult
                    public int getStateKey() {
                        return stateKey;
                    }

                    @Override // com.google.android.gms.common.api.Result
                    public Status getStatus() {
                        return status;
                    }
                };
            }
        });
    }

    public static int getMaxNumKeys(GoogleApiClient googleApiClient) {
        return a(googleApiClient).dw();
    }

    public static int getMaxStateSize(GoogleApiClient googleApiClient) {
        return a(googleApiClient).dv();
    }

    public static PendingResult<StateListResult> list(GoogleApiClient googleApiClient) {
        return googleApiClient.a(new c() { // from class: com.google.android.gms.appstate.AppStateManager.7
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.google.android.gms.common.api.a.b
            public void a(ei eiVar) {
                eiVar.a(this);
            }
        });
    }

    public static PendingResult<StateResult> load(GoogleApiClient googleApiClient, final int stateKey) {
        return googleApiClient.a(new e() { // from class: com.google.android.gms.appstate.AppStateManager.6
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super();
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.google.android.gms.common.api.a.b
            public void a(ei eiVar) {
                eiVar.b(this, stateKey);
            }
        });
    }

    public static PendingResult<StateResult> resolve(GoogleApiClient googleApiClient, final int stateKey, final String resolvedVersion, final byte[] resolvedData) {
        return googleApiClient.b(new e() { // from class: com.google.android.gms.appstate.AppStateManager.8
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super();
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.google.android.gms.common.api.a.b
            public void a(ei eiVar) {
                eiVar.a(this, stateKey, resolvedVersion, resolvedData);
            }
        });
    }

    public static PendingResult<Status> signOut(GoogleApiClient googleApiClient) {
        return googleApiClient.b(new d() { // from class: com.google.android.gms.appstate.AppStateManager.9
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.google.android.gms.common.api.a.b
            public void a(ei eiVar) {
                eiVar.b(this);
            }
        });
    }

    public static void update(GoogleApiClient googleApiClient, final int stateKey, final byte[] data) {
        googleApiClient.b(new e() { // from class: com.google.android.gms.appstate.AppStateManager.3
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super();
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.google.android.gms.common.api.a.b
            public void a(ei eiVar) {
                eiVar.a((com.google.android.gms.common.api.a.d<StateResult>) null, stateKey, data);
            }
        });
    }

    public static PendingResult<StateResult> updateImmediate(GoogleApiClient googleApiClient, final int stateKey, final byte[] data) {
        return googleApiClient.b(new e() { // from class: com.google.android.gms.appstate.AppStateManager.4
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super();
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.google.android.gms.common.api.a.b
            public void a(ei eiVar) {
                eiVar.a(this, stateKey, data);
            }
        });
    }
}
