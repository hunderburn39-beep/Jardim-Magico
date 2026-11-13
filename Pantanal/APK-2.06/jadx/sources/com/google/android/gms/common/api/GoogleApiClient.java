package com.google.android.gms.common.api;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.internal.fc;
import com.google.android.gms.internal.fq;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes.dex */
public interface GoogleApiClient {

    public static final class Builder {
        private Looper AS;
        private final Set<String> AT;
        private int AU;
        private View AV;
        private String AW;
        private final Map<Api<?>, Api.ApiOptions> AX;
        private final Set<ConnectionCallbacks> AY;
        private final Set<OnConnectionFailedListener> AZ;
        private final Context mContext;
        private String wG;

        public Builder(Context context) {
            this.AT = new HashSet();
            this.AX = new HashMap();
            this.AY = new HashSet();
            this.AZ = new HashSet();
            this.mContext = context;
            this.AS = context.getMainLooper();
            this.AW = context.getPackageName();
        }

        public Builder(Context context, ConnectionCallbacks connectedListener, OnConnectionFailedListener connectionFailedListener) {
            this(context);
            fq.b(connectedListener, "Must provide a connected listener");
            this.AY.add(connectedListener);
            fq.b(connectionFailedListener, "Must provide a connection failed listener");
            this.AZ.add(connectionFailedListener);
        }

        public Builder addApi(Api<? extends Api.ApiOptions.NotRequiredOptions> api) {
            this.AX.put(api, null);
            List<Scope> listDZ = api.dZ();
            int size = listDZ.size();
            for (int i = 0; i < size; i++) {
                this.AT.add(listDZ.get(i).en());
            }
            return this;
        }

        public <O extends Api.ApiOptions.HasOptions> Builder addApi(Api<O> api, O options) {
            fq.b(options, "Null options are not permitted for this Api");
            this.AX.put(api, options);
            List<Scope> listDZ = api.dZ();
            int size = listDZ.size();
            for (int i = 0; i < size; i++) {
                this.AT.add(listDZ.get(i).en());
            }
            return this;
        }

        public Builder addConnectionCallbacks(ConnectionCallbacks listener) {
            this.AY.add(listener);
            return this;
        }

        public Builder addOnConnectionFailedListener(OnConnectionFailedListener listener) {
            this.AZ.add(listener);
            return this;
        }

        public Builder addScope(Scope scope) {
            this.AT.add(scope.en());
            return this;
        }

        public GoogleApiClient build() {
            return new b(this.mContext, this.AS, eh(), this.AX, this.AY, this.AZ);
        }

        public fc eh() {
            return new fc(this.wG, this.AT, this.AU, this.AV, this.AW);
        }

        public Builder setAccountName(String accountName) {
            this.wG = accountName;
            return this;
        }

        public Builder setGravityForPopups(int gravityForPopups) {
            this.AU = gravityForPopups;
            return this;
        }

        public Builder setHandler(Handler handler) {
            fq.b(handler, "Handler must not be null");
            this.AS = handler.getLooper();
            return this;
        }

        public Builder setViewForPopups(View viewForPopups) {
            this.AV = viewForPopups;
            return this;
        }

        public Builder useDefaultAccount() {
            return setAccountName("<<default account>>");
        }
    }

    public interface ConnectionCallbacks {
        public static final int CAUSE_NETWORK_LOST = 2;
        public static final int CAUSE_SERVICE_DISCONNECTED = 1;

        void onConnected(Bundle bundle);

        void onConnectionSuspended(int i);
    }

    public interface OnConnectionFailedListener extends GooglePlayServicesClient.OnConnectionFailedListener {
        @Override // com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener
        void onConnectionFailed(ConnectionResult connectionResult);
    }

    <C extends Api.a> C a(Api.c<C> cVar);

    <A extends Api.a, T extends a.b<? extends Result, A>> T a(T t);

    <A extends Api.a, T extends a.b<? extends Result, A>> T b(T t);

    ConnectionResult blockingConnect(long j, TimeUnit timeUnit);

    void connect();

    void disconnect();

    Looper getLooper();

    boolean isConnected();

    boolean isConnecting();

    boolean isConnectionCallbacksRegistered(ConnectionCallbacks connectionCallbacks);

    boolean isConnectionFailedListenerRegistered(OnConnectionFailedListener onConnectionFailedListener);

    void reconnect();

    void registerConnectionCallbacks(ConnectionCallbacks connectionCallbacks);

    void registerConnectionFailedListener(OnConnectionFailedListener onConnectionFailedListener);

    void unregisterConnectionCallbacks(ConnectionCallbacks connectionCallbacks);

    void unregisterConnectionFailedListener(OnConnectionFailedListener onConnectionFailedListener);
}
