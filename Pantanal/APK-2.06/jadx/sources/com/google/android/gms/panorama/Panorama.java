package com.google.android.gms.panorama;

import android.content.Context;
import android.os.Looper;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.internal.fc;
import com.google.android.gms.internal.hw;
import com.google.android.gms.internal.hx;

/* JADX INFO: loaded from: classes.dex */
public final class Panorama {
    public static final Api.c<hx> wx = new Api.c<>();
    static final Api.b<hx, Api.ApiOptions.NoOptions> wy = new Api.b<hx, Api.ApiOptions.NoOptions>() { // from class: com.google.android.gms.panorama.Panorama.1
        @Override // com.google.android.gms.common.api.Api.b
        /* JADX INFO: renamed from: c, reason: merged with bridge method [inline-methods] */
        public hx a(Context context, Looper looper, fc fcVar, Api.ApiOptions.NoOptions noOptions, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
            return new hx(context, looper, connectionCallbacks, onConnectionFailedListener);
        }

        @Override // com.google.android.gms.common.api.Api.b
        public int getPriority() {
            return Integer.MAX_VALUE;
        }
    };
    public static final Api<Api.ApiOptions.NoOptions> API = new Api<>(wy, wx, new Scope[0]);
    public static final PanoramaApi PanoramaApi = new hw();

    private Panorama() {
    }
}
