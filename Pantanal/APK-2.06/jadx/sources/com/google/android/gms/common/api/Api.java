package com.google.android.gms.common.api;

import android.content.Context;
import android.os.Looper;
import com.google.android.gms.common.api.Api.ApiOptions;
import com.google.android.gms.internal.fc;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public final class Api<O extends ApiOptions> {
    private final ArrayList<Scope> AA;
    private final b<?, O> Ay;
    private final c<?> Az;

    public interface ApiOptions {

        public interface HasOptions extends ApiOptions {
        }

        public static final class NoOptions implements NotRequiredOptions {
            private NoOptions() {
            }
        }

        public interface NotRequiredOptions extends ApiOptions {
        }

        public interface Optional extends HasOptions, NotRequiredOptions {
        }
    }

    public interface a {
        void connect();

        void disconnect();

        Looper getLooper();

        boolean isConnected();
    }

    public interface b<T extends a, O> {
        T a(Context context, Looper looper, fc fcVar, O o, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener);

        int getPriority();
    }

    public static final class c<C extends a> {
    }

    /* JADX WARN: Multi-variable type inference failed */
    public <C extends a> Api(b<C, O> bVar, c<C> cVar, Scope... impliedScopes) {
        this.Ay = bVar;
        this.Az = cVar;
        this.AA = new ArrayList<>(Arrays.asList(impliedScopes));
    }

    public b<?, O> dY() {
        return this.Ay;
    }

    public List<Scope> dZ() {
        return this.AA;
    }

    public c<?> ea() {
        return this.Az;
    }
}
