package com.google.android.gms.plus;

import android.content.Context;
import android.net.Uri;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.plus.internal.e;
import com.google.android.gms.plus.internal.i;
import com.google.android.gms.plus.model.moments.Moment;
import com.google.android.gms.plus.model.moments.MomentBuffer;
import com.google.android.gms.plus.model.people.Person;
import com.google.android.gms.plus.model.people.PersonBuffer;
import java.util.Collection;

/* JADX INFO: loaded from: classes.dex */
@Deprecated
public class PlusClient implements GooglePlayServicesClient {
    final e TL;

    @Deprecated
    public static class Builder {
        private final GooglePlayServicesClient.ConnectionCallbacks TQ;
        private final GooglePlayServicesClient.OnConnectionFailedListener TR;
        private final i TS;
        private final Context mContext;

        public Builder(Context context, GooglePlayServicesClient.ConnectionCallbacks connectionCallbacks, GooglePlayServicesClient.OnConnectionFailedListener connectionFailedListener) {
            this.mContext = context;
            this.TQ = connectionCallbacks;
            this.TR = connectionFailedListener;
            this.TS = new i(this.mContext);
        }

        public PlusClient build() {
            return new PlusClient(new e(this.mContext, this.TQ, this.TR, this.TS.iZ()));
        }

        public Builder clearScopes() {
            this.TS.iY();
            return this;
        }

        public Builder setAccountName(String accountName) {
            this.TS.bh(accountName);
            return this;
        }

        public Builder setActions(String... actions) {
            this.TS.f(actions);
            return this;
        }

        public Builder setScopes(String... scopes) {
            this.TS.e(scopes);
            return this;
        }
    }

    @Deprecated
    public interface OnAccessRevokedListener {
        void onAccessRevoked(ConnectionResult connectionResult);
    }

    @Deprecated
    public interface OnMomentsLoadedListener {
        @Deprecated
        void onMomentsLoaded(ConnectionResult connectionResult, MomentBuffer momentBuffer, String str, String str2);
    }

    @Deprecated
    public interface OnPeopleLoadedListener {
        void onPeopleLoaded(ConnectionResult connectionResult, PersonBuffer personBuffer, String str);
    }

    @Deprecated
    public interface OrderBy {

        @Deprecated
        public static final int ALPHABETICAL = 0;

        @Deprecated
        public static final int BEST = 1;
    }

    PlusClient(e plusClientImpl) {
        this.TL = plusClientImpl;
    }

    @Deprecated
    public void clearDefaultAccount() {
        this.TL.clearDefaultAccount();
    }

    @Override // com.google.android.gms.common.GooglePlayServicesClient, com.google.android.gms.common.api.Api.a
    @Deprecated
    public void connect() {
        this.TL.connect();
    }

    @Override // com.google.android.gms.common.GooglePlayServicesClient, com.google.android.gms.common.api.Api.a
    @Deprecated
    public void disconnect() {
        this.TL.disconnect();
    }

    @Deprecated
    public String getAccountName() {
        return this.TL.getAccountName();
    }

    @Deprecated
    public Person getCurrentPerson() {
        return this.TL.getCurrentPerson();
    }

    e iI() {
        return this.TL;
    }

    @Override // com.google.android.gms.common.GooglePlayServicesClient, com.google.android.gms.common.api.Api.a, com.google.android.gms.internal.fg.b
    @Deprecated
    public boolean isConnected() {
        return this.TL.isConnected();
    }

    @Override // com.google.android.gms.common.GooglePlayServicesClient
    @Deprecated
    public boolean isConnecting() {
        return this.TL.isConnecting();
    }

    @Override // com.google.android.gms.common.GooglePlayServicesClient
    @Deprecated
    public boolean isConnectionCallbacksRegistered(GooglePlayServicesClient.ConnectionCallbacks listener) {
        return this.TL.isConnectionCallbacksRegistered(listener);
    }

    @Override // com.google.android.gms.common.GooglePlayServicesClient
    @Deprecated
    public boolean isConnectionFailedListenerRegistered(GooglePlayServicesClient.OnConnectionFailedListener listener) {
        return this.TL.isConnectionFailedListenerRegistered(listener);
    }

    @Deprecated
    public void loadMoments(final OnMomentsLoadedListener listener) {
        this.TL.l(new com.google.android.gms.common.api.a.d<Moments.LoadMomentsResult>() { // from class: com.google.android.gms.plus.PlusClient.1
            @Override // com.google.android.gms.common.api.a.d
            /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
            public void b(Moments.LoadMomentsResult loadMomentsResult) {
                listener.onMomentsLoaded(loadMomentsResult.getStatus().eq(), loadMomentsResult.getMomentBuffer(), loadMomentsResult.getNextPageToken(), loadMomentsResult.getUpdated());
            }
        });
    }

    @Deprecated
    public void loadMoments(final OnMomentsLoadedListener listener, int maxResults, String pageToken, Uri targetUrl, String type, String userId) {
        this.TL.a(new com.google.android.gms.common.api.a.d<Moments.LoadMomentsResult>() { // from class: com.google.android.gms.plus.PlusClient.2
            @Override // com.google.android.gms.common.api.a.d
            /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
            public void b(Moments.LoadMomentsResult loadMomentsResult) {
                listener.onMomentsLoaded(loadMomentsResult.getStatus().eq(), loadMomentsResult.getMomentBuffer(), loadMomentsResult.getNextPageToken(), loadMomentsResult.getUpdated());
            }
        }, maxResults, pageToken, targetUrl, type, userId);
    }

    @Deprecated
    public void loadPeople(final OnPeopleLoadedListener listener, Collection<String> personIds) {
        this.TL.a(new com.google.android.gms.common.api.a.d<People.LoadPeopleResult>() { // from class: com.google.android.gms.plus.PlusClient.5
            @Override // com.google.android.gms.common.api.a.d
            /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
            public void b(People.LoadPeopleResult loadPeopleResult) {
                listener.onPeopleLoaded(loadPeopleResult.getStatus().eq(), loadPeopleResult.getPersonBuffer(), loadPeopleResult.getNextPageToken());
            }
        }, personIds);
    }

    @Deprecated
    public void loadPeople(final OnPeopleLoadedListener listener, String... personIds) {
        this.TL.d(new com.google.android.gms.common.api.a.d<People.LoadPeopleResult>() { // from class: com.google.android.gms.plus.PlusClient.6
            @Override // com.google.android.gms.common.api.a.d
            /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
            public void b(People.LoadPeopleResult loadPeopleResult) {
                listener.onPeopleLoaded(loadPeopleResult.getStatus().eq(), loadPeopleResult.getPersonBuffer(), loadPeopleResult.getNextPageToken());
            }
        }, personIds);
    }

    @Deprecated
    public void loadVisiblePeople(final OnPeopleLoadedListener listener, int orderBy, String pageToken) {
        this.TL.a(new com.google.android.gms.common.api.a.d<People.LoadPeopleResult>() { // from class: com.google.android.gms.plus.PlusClient.3
            @Override // com.google.android.gms.common.api.a.d
            /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
            public void b(People.LoadPeopleResult loadPeopleResult) {
                listener.onPeopleLoaded(loadPeopleResult.getStatus().eq(), loadPeopleResult.getPersonBuffer(), loadPeopleResult.getNextPageToken());
            }
        }, orderBy, pageToken);
    }

    @Deprecated
    public void loadVisiblePeople(final OnPeopleLoadedListener listener, String pageToken) {
        this.TL.o(new com.google.android.gms.common.api.a.d<People.LoadPeopleResult>() { // from class: com.google.android.gms.plus.PlusClient.4
            @Override // com.google.android.gms.common.api.a.d
            /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
            public void b(People.LoadPeopleResult loadPeopleResult) {
                listener.onPeopleLoaded(loadPeopleResult.getStatus().eq(), loadPeopleResult.getPersonBuffer(), loadPeopleResult.getNextPageToken());
            }
        }, pageToken);
    }

    @Override // com.google.android.gms.common.GooglePlayServicesClient
    @Deprecated
    public void registerConnectionCallbacks(GooglePlayServicesClient.ConnectionCallbacks listener) {
        this.TL.registerConnectionCallbacks(listener);
    }

    @Override // com.google.android.gms.common.GooglePlayServicesClient
    @Deprecated
    public void registerConnectionFailedListener(GooglePlayServicesClient.OnConnectionFailedListener listener) {
        this.TL.registerConnectionFailedListener(listener);
    }

    @Deprecated
    public void removeMoment(String momentId) {
        this.TL.removeMoment(momentId);
    }

    @Deprecated
    public void revokeAccessAndDisconnect(final OnAccessRevokedListener listener) {
        this.TL.n(new com.google.android.gms.common.api.a.d<Status>() { // from class: com.google.android.gms.plus.PlusClient.7
            @Override // com.google.android.gms.common.api.a.d
            /* JADX INFO: renamed from: Y, reason: merged with bridge method [inline-methods] */
            public void b(Status status) {
                listener.onAccessRevoked(status.getStatus().eq());
            }
        });
    }

    @Override // com.google.android.gms.common.GooglePlayServicesClient
    @Deprecated
    public void unregisterConnectionCallbacks(GooglePlayServicesClient.ConnectionCallbacks listener) {
        this.TL.unregisterConnectionCallbacks(listener);
    }

    @Override // com.google.android.gms.common.GooglePlayServicesClient
    @Deprecated
    public void unregisterConnectionFailedListener(GooglePlayServicesClient.OnConnectionFailedListener listener) {
        this.TL.unregisterConnectionFailedListener(listener);
    }

    @Deprecated
    public void writeMoment(Moment moment) {
        this.TL.a((com.google.android.gms.common.api.a.d<Status>) null, moment);
    }
}
