package com.google.android.gms.games.internal.api;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.internal.GamesClientImpl;
import com.google.android.gms.games.request.GameRequest;
import com.google.android.gms.games.request.GameRequestBuffer;
import com.google.android.gms.games.request.OnRequestReceivedListener;
import com.google.android.gms.games.request.Requests;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public final class RequestsImpl implements Requests {

    /* JADX INFO: renamed from: com.google.android.gms.games.internal.api.RequestsImpl$4, reason: invalid class name */
    class AnonymousClass4 extends SendRequestImpl {
        final /* synthetic */ String JT;
        final /* synthetic */ String[] KS;
        final /* synthetic */ int KT;
        final /* synthetic */ byte[] KU;
        final /* synthetic */ int KV;

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.android.gms.common.api.a.b
        public void a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.a(this, this.JT, this.KS, this.KT, this.KU, this.KV);
        }
    }

    /* JADX INFO: renamed from: com.google.android.gms.games.internal.api.RequestsImpl$5, reason: invalid class name */
    class AnonymousClass5 extends SendRequestImpl {
        final /* synthetic */ String JT;
        final /* synthetic */ String[] KS;
        final /* synthetic */ int KT;
        final /* synthetic */ byte[] KU;
        final /* synthetic */ int KV;

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.android.gms.common.api.a.b
        public void a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.a(this, this.JT, this.KS, this.KT, this.KU, this.KV);
        }
    }

    /* JADX INFO: renamed from: com.google.android.gms.games.internal.api.RequestsImpl$6, reason: invalid class name */
    class AnonymousClass6 extends UpdateRequestsImpl {
        final /* synthetic */ String JT;
        final /* synthetic */ String[] KO;
        final /* synthetic */ String KW;

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.android.gms.common.api.a.b
        public void a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.a(this, this.JT, this.KW, this.KO);
        }
    }

    /* JADX INFO: renamed from: com.google.android.gms.games.internal.api.RequestsImpl$7, reason: invalid class name */
    class AnonymousClass7 extends LoadRequestsImpl {
        final /* synthetic */ String JT;
        final /* synthetic */ int KQ;
        final /* synthetic */ int KR;
        final /* synthetic */ String KW;
        final /* synthetic */ int Kk;

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.android.gms.common.api.a.b
        public void a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.a(this, this.JT, this.KW, this.KQ, this.KR, this.Kk);
        }
    }

    /* JADX INFO: renamed from: com.google.android.gms.games.internal.api.RequestsImpl$8, reason: invalid class name */
    class AnonymousClass8 extends LoadRequestSummariesImpl {
        final /* synthetic */ int KR;
        final /* synthetic */ String KW;

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.android.gms.common.api.a.b
        public void a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.d(this, this.KW, this.KR);
        }
    }

    private static abstract class LoadRequestSummariesImpl extends Games.BaseGamesApiMethodImpl<Requests.LoadRequestSummariesResult> {
        private LoadRequestSummariesImpl() {
        }

        @Override // com.google.android.gms.common.api.a.AbstractC0032a
        /* JADX INFO: renamed from: N, reason: merged with bridge method [inline-methods] */
        public Requests.LoadRequestSummariesResult d(final Status status) {
            return new Requests.LoadRequestSummariesResult() { // from class: com.google.android.gms.games.internal.api.RequestsImpl.LoadRequestSummariesImpl.1
                @Override // com.google.android.gms.common.api.Result
                public Status getStatus() {
                    return status;
                }

                @Override // com.google.android.gms.common.api.Releasable
                public void release() {
                }
            };
        }
    }

    private static abstract class LoadRequestsImpl extends Games.BaseGamesApiMethodImpl<Requests.LoadRequestsResult> {
        private LoadRequestsImpl() {
        }

        @Override // com.google.android.gms.common.api.a.AbstractC0032a
        /* JADX INFO: renamed from: O, reason: merged with bridge method [inline-methods] */
        public Requests.LoadRequestsResult d(final Status status) {
            return new Requests.LoadRequestsResult() { // from class: com.google.android.gms.games.internal.api.RequestsImpl.LoadRequestsImpl.1
                @Override // com.google.android.gms.games.request.Requests.LoadRequestsResult
                public GameRequestBuffer getRequests(int type) {
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
    }

    private static abstract class SendRequestImpl extends Games.BaseGamesApiMethodImpl<Requests.SendRequestResult> {
        private SendRequestImpl() {
        }

        @Override // com.google.android.gms.common.api.a.AbstractC0032a
        /* JADX INFO: renamed from: P, reason: merged with bridge method [inline-methods] */
        public Requests.SendRequestResult d(final Status status) {
            return new Requests.SendRequestResult() { // from class: com.google.android.gms.games.internal.api.RequestsImpl.SendRequestImpl.1
                @Override // com.google.android.gms.common.api.Result
                public Status getStatus() {
                    return status;
                }
            };
        }
    }

    private static abstract class UpdateRequestsImpl extends Games.BaseGamesApiMethodImpl<Requests.UpdateRequestsResult> {
        private UpdateRequestsImpl() {
        }

        @Override // com.google.android.gms.common.api.a.AbstractC0032a
        /* JADX INFO: renamed from: Q, reason: merged with bridge method [inline-methods] */
        public Requests.UpdateRequestsResult d(final Status status) {
            return new Requests.UpdateRequestsResult() { // from class: com.google.android.gms.games.internal.api.RequestsImpl.UpdateRequestsImpl.1
                @Override // com.google.android.gms.games.request.Requests.UpdateRequestsResult
                public Set<String> getRequestIds() {
                    return null;
                }

                @Override // com.google.android.gms.games.request.Requests.UpdateRequestsResult
                public int getRequestOutcome(String requestId) {
                    throw new IllegalArgumentException("Unknown request ID " + requestId);
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
    }

    @Override // com.google.android.gms.games.request.Requests
    public PendingResult<Requests.UpdateRequestsResult> acceptRequest(GoogleApiClient apiClient, String requestId) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(requestId);
        return acceptRequests(apiClient, arrayList);
    }

    @Override // com.google.android.gms.games.request.Requests
    public PendingResult<Requests.UpdateRequestsResult> acceptRequests(GoogleApiClient apiClient, List<String> requestIds) {
        final String[] strArr = requestIds == null ? null : (String[]) requestIds.toArray(new String[requestIds.size()]);
        return apiClient.b(new UpdateRequestsImpl() { // from class: com.google.android.gms.games.internal.api.RequestsImpl.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super();
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.google.android.gms.common.api.a.b
            public void a(GamesClientImpl gamesClientImpl) {
                gamesClientImpl.b(this, strArr);
            }
        });
    }

    @Override // com.google.android.gms.games.request.Requests
    public PendingResult<Requests.UpdateRequestsResult> dismissRequest(GoogleApiClient apiClient, String requestId) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(requestId);
        return dismissRequests(apiClient, arrayList);
    }

    @Override // com.google.android.gms.games.request.Requests
    public PendingResult<Requests.UpdateRequestsResult> dismissRequests(GoogleApiClient apiClient, List<String> requestIds) {
        final String[] strArr = requestIds == null ? null : (String[]) requestIds.toArray(new String[requestIds.size()]);
        return apiClient.b(new UpdateRequestsImpl() { // from class: com.google.android.gms.games.internal.api.RequestsImpl.2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super();
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.google.android.gms.common.api.a.b
            public void a(GamesClientImpl gamesClientImpl) {
                gamesClientImpl.c(this, strArr);
            }
        });
    }

    @Override // com.google.android.gms.games.request.Requests
    public ArrayList<GameRequest> getGameRequestsFromBundle(Bundle extras) {
        if (extras == null || !extras.containsKey(Requests.EXTRA_REQUESTS)) {
            return new ArrayList<>();
        }
        ArrayList arrayList = (ArrayList) extras.get(Requests.EXTRA_REQUESTS);
        ArrayList<GameRequest> arrayList2 = new ArrayList<>();
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            arrayList2.add((GameRequest) arrayList.get(i));
        }
        return arrayList2;
    }

    @Override // com.google.android.gms.games.request.Requests
    public ArrayList<GameRequest> getGameRequestsFromInboxResponse(Intent response) {
        return response == null ? new ArrayList<>() : getGameRequestsFromBundle(response.getExtras());
    }

    @Override // com.google.android.gms.games.request.Requests
    public Intent getInboxIntent(GoogleApiClient apiClient) {
        return Games.c(apiClient).gB();
    }

    @Override // com.google.android.gms.games.request.Requests
    public int getMaxLifetimeDays(GoogleApiClient apiClient) {
        return Games.c(apiClient).gD();
    }

    @Override // com.google.android.gms.games.request.Requests
    public int getMaxPayloadSize(GoogleApiClient apiClient) {
        return Games.c(apiClient).gC();
    }

    @Override // com.google.android.gms.games.request.Requests
    public Intent getSendIntent(GoogleApiClient apiClient, int type, byte[] payload, int requestLifetimeDays, Bitmap icon, String description) {
        return Games.c(apiClient).a(type, payload, requestLifetimeDays, icon, description);
    }

    @Override // com.google.android.gms.games.request.Requests
    public PendingResult<Requests.LoadRequestsResult> loadRequests(GoogleApiClient apiClient, final int requestDirection, final int types, final int sortOrder) {
        return apiClient.a(new LoadRequestsImpl() { // from class: com.google.android.gms.games.internal.api.RequestsImpl.3
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super();
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.google.android.gms.common.api.a.b
            public void a(GamesClientImpl gamesClientImpl) {
                gamesClientImpl.a(this, requestDirection, types, sortOrder);
            }
        });
    }

    @Override // com.google.android.gms.games.request.Requests
    public void registerRequestListener(GoogleApiClient apiClient, OnRequestReceivedListener listener) {
        Games.c(apiClient).a(listener);
    }

    @Override // com.google.android.gms.games.request.Requests
    public void unregisterRequestListener(GoogleApiClient apiClient) {
        Games.c(apiClient).gv();
    }
}
