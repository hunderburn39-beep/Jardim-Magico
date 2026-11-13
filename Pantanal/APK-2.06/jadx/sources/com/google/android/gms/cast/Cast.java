package com.google.android.gms.cast;

import android.content.Context;
import android.os.Looper;
import android.os.RemoteException;
import android.text.TextUtils;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.en;
import com.google.android.gms.internal.fc;
import com.google.android.gms.internal.fq;
import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
public final class Cast {
    public static final String EXTRA_APP_NO_LONGER_RUNNING = "com.google.android.gms.cast.EXTRA_APP_NO_LONGER_RUNNING";
    public static final int MAX_MESSAGE_LENGTH = 65536;
    public static final int MAX_NAMESPACE_LENGTH = 128;
    static final Api.c<en> wx = new Api.c<>();
    private static final Api.b<en, CastOptions> wy = new Api.b<en, CastOptions>() { // from class: com.google.android.gms.cast.Cast.1
        @Override // com.google.android.gms.common.api.Api.b
        public en a(Context context, Looper looper, fc fcVar, CastOptions castOptions, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
            fq.b(castOptions, "Setting the API options is required.");
            return new en(context, looper, castOptions.xT, castOptions.xV, castOptions.xU, connectionCallbacks, onConnectionFailedListener);
        }

        @Override // com.google.android.gms.common.api.Api.b
        public int getPriority() {
            return Integer.MAX_VALUE;
        }
    };
    public static final Api<CastOptions> API = new Api<>(wy, wx, new Scope[0]);
    public static final CastApi CastApi = new CastApi.a();

    public interface ApplicationConnectionResult extends Result {
        ApplicationMetadata getApplicationMetadata();

        String getApplicationStatus();

        String getSessionId();

        boolean getWasLaunched();
    }

    public interface CastApi {

        public static final class a implements CastApi {
            @Override // com.google.android.gms.cast.Cast.CastApi
            public ApplicationMetadata getApplicationMetadata(GoogleApiClient client) throws IllegalStateException {
                return ((en) client.a(Cast.wx)).getApplicationMetadata();
            }

            @Override // com.google.android.gms.cast.Cast.CastApi
            public String getApplicationStatus(GoogleApiClient client) throws IllegalStateException {
                return ((en) client.a(Cast.wx)).getApplicationStatus();
            }

            @Override // com.google.android.gms.cast.Cast.CastApi
            public double getVolume(GoogleApiClient client) throws IllegalStateException {
                return ((en) client.a(Cast.wx)).dI();
            }

            @Override // com.google.android.gms.cast.Cast.CastApi
            public boolean isMute(GoogleApiClient client) throws IllegalStateException {
                return ((en) client.a(Cast.wx)).isMute();
            }

            @Override // com.google.android.gms.cast.Cast.CastApi
            public PendingResult<ApplicationConnectionResult> joinApplication(GoogleApiClient client) {
                return client.b(new c() { // from class: com.google.android.gms.cast.Cast.CastApi.a.6
                    /* JADX INFO: Access modifiers changed from: protected */
                    @Override // com.google.android.gms.common.api.a.b
                    public void a(en enVar) throws RemoteException {
                        try {
                            enVar.b(null, null, this);
                        } catch (IllegalStateException e) {
                            x(2001);
                        }
                    }
                });
            }

            @Override // com.google.android.gms.cast.Cast.CastApi
            public PendingResult<ApplicationConnectionResult> joinApplication(GoogleApiClient client, final String applicationId) {
                return client.b(new c() { // from class: com.google.android.gms.cast.Cast.CastApi.a.5
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super();
                    }

                    /* JADX INFO: Access modifiers changed from: protected */
                    @Override // com.google.android.gms.common.api.a.b
                    public void a(en enVar) throws RemoteException {
                        try {
                            enVar.b(applicationId, null, this);
                        } catch (IllegalStateException e) {
                            x(2001);
                        }
                    }
                });
            }

            @Override // com.google.android.gms.cast.Cast.CastApi
            public PendingResult<ApplicationConnectionResult> joinApplication(GoogleApiClient client, final String applicationId, final String sessionId) {
                return client.b(new c() { // from class: com.google.android.gms.cast.Cast.CastApi.a.4
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super();
                    }

                    /* JADX INFO: Access modifiers changed from: protected */
                    @Override // com.google.android.gms.common.api.a.b
                    public void a(en enVar) throws RemoteException {
                        try {
                            enVar.b(applicationId, sessionId, this);
                        } catch (IllegalStateException e) {
                            x(2001);
                        }
                    }
                });
            }

            @Override // com.google.android.gms.cast.Cast.CastApi
            public PendingResult<ApplicationConnectionResult> launchApplication(GoogleApiClient client, final String applicationId) {
                return client.b(new c() { // from class: com.google.android.gms.cast.Cast.CastApi.a.2
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super();
                    }

                    /* JADX INFO: Access modifiers changed from: protected */
                    @Override // com.google.android.gms.common.api.a.b
                    public void a(en enVar) throws RemoteException {
                        try {
                            enVar.a(applicationId, false, (com.google.android.gms.common.api.a.d<ApplicationConnectionResult>) this);
                        } catch (IllegalStateException e) {
                            x(2001);
                        }
                    }
                });
            }

            @Override // com.google.android.gms.cast.Cast.CastApi
            public PendingResult<ApplicationConnectionResult> launchApplication(GoogleApiClient client, final String applicationId, final boolean relaunchIfRunning) {
                return client.b(new c() { // from class: com.google.android.gms.cast.Cast.CastApi.a.3
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super();
                    }

                    /* JADX INFO: Access modifiers changed from: protected */
                    @Override // com.google.android.gms.common.api.a.b
                    public void a(en enVar) throws RemoteException {
                        try {
                            enVar.a(applicationId, relaunchIfRunning, this);
                        } catch (IllegalStateException e) {
                            x(2001);
                        }
                    }
                });
            }

            @Override // com.google.android.gms.cast.Cast.CastApi
            public PendingResult<Status> leaveApplication(GoogleApiClient client) {
                return client.b(new b() { // from class: com.google.android.gms.cast.Cast.CastApi.a.7
                    /* JADX INFO: Access modifiers changed from: protected */
                    @Override // com.google.android.gms.common.api.a.b
                    public void a(en enVar) throws RemoteException {
                        try {
                            enVar.e(this);
                        } catch (IllegalStateException e) {
                            x(2001);
                        }
                    }
                });
            }

            @Override // com.google.android.gms.cast.Cast.CastApi
            public void removeMessageReceivedCallbacks(GoogleApiClient client, String namespace) throws IOException, IllegalArgumentException {
                try {
                    ((en) client.a(Cast.wx)).V(namespace);
                } catch (RemoteException e) {
                    throw new IOException("service error");
                }
            }

            @Override // com.google.android.gms.cast.Cast.CastApi
            public void requestStatus(GoogleApiClient client) throws IllegalStateException, IOException {
                try {
                    ((en) client.a(Cast.wx)).dH();
                } catch (RemoteException e) {
                    throw new IOException("service error");
                }
            }

            @Override // com.google.android.gms.cast.Cast.CastApi
            public PendingResult<Status> sendMessage(GoogleApiClient client, final String namespace, final String message) {
                return client.b(new b() { // from class: com.google.android.gms.cast.Cast.CastApi.a.1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super();
                    }

                    /* JADX INFO: Access modifiers changed from: protected */
                    @Override // com.google.android.gms.common.api.a.b
                    public void a(en enVar) throws RemoteException {
                        try {
                            enVar.a(namespace, message, this);
                        } catch (IllegalArgumentException e) {
                            x(2001);
                        } catch (IllegalStateException e2) {
                            x(2001);
                        }
                    }
                });
            }

            @Override // com.google.android.gms.cast.Cast.CastApi
            public void setMessageReceivedCallbacks(GoogleApiClient client, String namespace, MessageReceivedCallback callbacks) throws IllegalStateException, IOException {
                try {
                    ((en) client.a(Cast.wx)).a(namespace, callbacks);
                } catch (RemoteException e) {
                    throw new IOException("service error");
                }
            }

            @Override // com.google.android.gms.cast.Cast.CastApi
            public void setMute(GoogleApiClient client, boolean mute) throws IllegalStateException, IOException {
                try {
                    ((en) client.a(Cast.wx)).v(mute);
                } catch (RemoteException e) {
                    throw new IOException("service error");
                }
            }

            @Override // com.google.android.gms.cast.Cast.CastApi
            public void setVolume(GoogleApiClient client, double volume) throws IllegalStateException, IOException, IllegalArgumentException {
                try {
                    ((en) client.a(Cast.wx)).a(volume);
                } catch (RemoteException e) {
                    throw new IOException("service error");
                }
            }

            @Override // com.google.android.gms.cast.Cast.CastApi
            public PendingResult<Status> stopApplication(GoogleApiClient client) {
                return client.b(new b() { // from class: com.google.android.gms.cast.Cast.CastApi.a.8
                    /* JADX INFO: Access modifiers changed from: protected */
                    @Override // com.google.android.gms.common.api.a.b
                    public void a(en enVar) throws RemoteException {
                        try {
                            enVar.a("", this);
                        } catch (IllegalStateException e) {
                            x(2001);
                        }
                    }
                });
            }

            @Override // com.google.android.gms.cast.Cast.CastApi
            public PendingResult<Status> stopApplication(GoogleApiClient client, final String sessionId) {
                return client.b(new b() { // from class: com.google.android.gms.cast.Cast.CastApi.a.9
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super();
                    }

                    /* JADX INFO: Access modifiers changed from: protected */
                    @Override // com.google.android.gms.common.api.a.b
                    public void a(en enVar) throws RemoteException {
                        if (TextUtils.isEmpty(sessionId)) {
                            c(2001, "IllegalArgument: sessionId cannot be null or empty");
                            return;
                        }
                        try {
                            enVar.a(sessionId, this);
                        } catch (IllegalStateException e) {
                            x(2001);
                        }
                    }
                });
            }
        }

        ApplicationMetadata getApplicationMetadata(GoogleApiClient googleApiClient) throws IllegalStateException;

        String getApplicationStatus(GoogleApiClient googleApiClient) throws IllegalStateException;

        double getVolume(GoogleApiClient googleApiClient) throws IllegalStateException;

        boolean isMute(GoogleApiClient googleApiClient) throws IllegalStateException;

        PendingResult<ApplicationConnectionResult> joinApplication(GoogleApiClient googleApiClient);

        PendingResult<ApplicationConnectionResult> joinApplication(GoogleApiClient googleApiClient, String str);

        PendingResult<ApplicationConnectionResult> joinApplication(GoogleApiClient googleApiClient, String str, String str2);

        PendingResult<ApplicationConnectionResult> launchApplication(GoogleApiClient googleApiClient, String str);

        PendingResult<ApplicationConnectionResult> launchApplication(GoogleApiClient googleApiClient, String str, boolean z);

        PendingResult<Status> leaveApplication(GoogleApiClient googleApiClient);

        void removeMessageReceivedCallbacks(GoogleApiClient googleApiClient, String str) throws IOException, IllegalArgumentException;

        void requestStatus(GoogleApiClient googleApiClient) throws IllegalStateException, IOException;

        PendingResult<Status> sendMessage(GoogleApiClient googleApiClient, String str, String str2);

        void setMessageReceivedCallbacks(GoogleApiClient googleApiClient, String str, MessageReceivedCallback messageReceivedCallback) throws IllegalStateException, IOException;

        void setMute(GoogleApiClient googleApiClient, boolean z) throws IllegalStateException, IOException;

        void setVolume(GoogleApiClient googleApiClient, double d) throws IllegalStateException, IOException, IllegalArgumentException;

        PendingResult<Status> stopApplication(GoogleApiClient googleApiClient);

        PendingResult<Status> stopApplication(GoogleApiClient googleApiClient, String str);
    }

    public static final class CastOptions implements Api.ApiOptions.HasOptions {
        final CastDevice xT;
        final Listener xU;
        private final int xV;

        public static final class Builder {
            CastDevice xW;
            Listener xX;
            private int xY;

            private Builder(CastDevice castDevice, Listener castListener) {
                fq.b(castDevice, "CastDevice parameter cannot be null");
                fq.b(castListener, "CastListener parameter cannot be null");
                this.xW = castDevice;
                this.xX = castListener;
                this.xY = 0;
            }

            public CastOptions build() {
                return new CastOptions(this);
            }

            public Builder setVerboseLoggingEnabled(boolean enabled) {
                if (enabled) {
                    this.xY |= 1;
                } else {
                    this.xY &= -2;
                }
                return this;
            }
        }

        private CastOptions(Builder builder) {
            this.xT = builder.xW;
            this.xU = builder.xX;
            this.xV = builder.xY;
        }

        public static Builder builder(CastDevice castDevice, Listener castListener) {
            return new Builder(castDevice, castListener);
        }
    }

    public static abstract class Listener {
        public void onApplicationDisconnected(int statusCode) {
        }

        public void onApplicationStatusChanged() {
        }

        public void onVolumeChanged() {
        }
    }

    public interface MessageReceivedCallback {
        void onMessageReceived(CastDevice castDevice, String str, String str2);
    }

    protected static abstract class a<R extends Result> extends com.google.android.gms.common.api.a.b<R, en> {
        public a() {
            super(Cast.wx);
        }

        public void c(int i, String str) {
            b(d(new Status(i, str, null)));
        }

        public void x(int i) {
            b(d(new Status(i)));
        }
    }

    private static abstract class b extends a<Status> {
        private b() {
        }

        @Override // com.google.android.gms.common.api.a.AbstractC0032a
        /* JADX INFO: renamed from: f, reason: merged with bridge method [inline-methods] */
        public Status d(Status status) {
            return status;
        }
    }

    private static abstract class c extends a<ApplicationConnectionResult> {
        private c() {
        }

        @Override // com.google.android.gms.common.api.a.AbstractC0032a
        /* JADX INFO: renamed from: h, reason: merged with bridge method [inline-methods] */
        public ApplicationConnectionResult d(final Status status) {
            return new ApplicationConnectionResult() { // from class: com.google.android.gms.cast.Cast.c.1
                @Override // com.google.android.gms.cast.Cast.ApplicationConnectionResult
                public ApplicationMetadata getApplicationMetadata() {
                    return null;
                }

                @Override // com.google.android.gms.cast.Cast.ApplicationConnectionResult
                public String getApplicationStatus() {
                    return null;
                }

                @Override // com.google.android.gms.cast.Cast.ApplicationConnectionResult
                public String getSessionId() {
                    return null;
                }

                @Override // com.google.android.gms.common.api.Result
                public Status getStatus() {
                    return status;
                }

                @Override // com.google.android.gms.cast.Cast.ApplicationConnectionResult
                public boolean getWasLaunched() {
                    return false;
                }
            };
        }
    }

    private Cast() {
    }
}
