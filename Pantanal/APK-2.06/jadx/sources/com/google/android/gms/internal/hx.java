package com.google.android.gms.internal;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.panorama.PanoramaApi;

/* JADX INFO: loaded from: classes.dex */
public class hx extends ff<hv> {

    final class a extends ff<hv>.b<com.google.android.gms.common.api.a.d<PanoramaApi.a>> implements PanoramaApi.a {
        public final Status TC;
        public final Intent TD;
        public final int type;

        public a(com.google.android.gms.common.api.a.d<PanoramaApi.a> dVar, Status status, int i, Intent intent) {
            super(dVar);
            this.TC = status;
            this.type = i;
            this.TD = intent;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.android.gms.internal.ff.b
        /* JADX INFO: renamed from: c, reason: merged with bridge method [inline-methods] */
        public void a(com.google.android.gms.common.api.a.d<PanoramaApi.a> dVar) {
            dVar.b(this);
        }

        @Override // com.google.android.gms.internal.ff.b
        protected void dx() {
        }

        @Override // com.google.android.gms.common.api.Result
        public Status getStatus() {
            return this.TC;
        }

        @Override // com.google.android.gms.panorama.PanoramaApi.PanoramaResult
        public Intent getViewerIntent() {
            return this.TD;
        }
    }

    final class b extends hu.a {
        private final com.google.android.gms.common.api.a.d<PanoramaApi.a> TF;
        private final com.google.android.gms.common.api.a.d<PanoramaApi.PanoramaResult> TG;
        private final Uri TH;

        public b(com.google.android.gms.common.api.a.d<PanoramaApi.a> dVar, com.google.android.gms.common.api.a.d<PanoramaApi.PanoramaResult> dVar2, Uri uri) {
            this.TF = dVar;
            this.TG = dVar2;
            this.TH = uri;
        }

        @Override // com.google.android.gms.internal.hu
        public void a(int i, Bundle bundle, int i2, Intent intent) {
            if (this.TH != null) {
                hx.this.getContext().revokeUriPermission(this.TH, 1);
            }
            Status status = new Status(i, null, bundle != null ? (PendingIntent) bundle.getParcelable("pendingIntent") : null);
            if (this.TG != null) {
                hx.this.a(hx.this.new c(this.TG, status, intent));
            } else if (this.TF != null) {
                hx.this.a(hx.this.new a(this.TF, status, i2, intent));
            }
        }
    }

    final class c extends ff<hv>.b<com.google.android.gms.common.api.a.d<PanoramaApi.PanoramaResult>> implements PanoramaApi.PanoramaResult {
        private final Status TC;
        private final Intent TD;

        public c(com.google.android.gms.common.api.a.d<PanoramaApi.PanoramaResult> dVar, Status status, Intent intent) {
            super(dVar);
            this.TC = status;
            this.TD = intent;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.android.gms.internal.ff.b
        /* JADX INFO: renamed from: c, reason: merged with bridge method [inline-methods] */
        public void a(com.google.android.gms.common.api.a.d<PanoramaApi.PanoramaResult> dVar) {
            dVar.b(this);
        }

        @Override // com.google.android.gms.internal.ff.b
        protected void dx() {
        }

        @Override // com.google.android.gms.common.api.Result
        public Status getStatus() {
            return this.TC;
        }

        @Override // com.google.android.gms.panorama.PanoramaApi.PanoramaResult
        public Intent getViewerIntent() {
            return this.TD;
        }
    }

    public hx(Context context, Looper looper, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        super(context, looper, connectionCallbacks, onConnectionFailedListener, (String[]) null);
    }

    public void a(com.google.android.gms.common.api.a.d<PanoramaApi.PanoramaResult> dVar, Uri uri, boolean z) {
        a(new b(null, dVar, z ? uri : null), uri, null, z);
    }

    @Override // com.google.android.gms.internal.ff
    protected void a(fm fmVar, ff.e eVar) throws RemoteException {
        fmVar.a(eVar, GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_VERSION_CODE, getContext().getPackageName(), new Bundle());
    }

    public void a(b bVar, Uri uri, Bundle bundle, boolean z) {
        bT();
        if (z) {
            getContext().grantUriPermission(GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_PACKAGE, uri, 1);
        }
        try {
            eM().a(bVar, uri, bundle, z);
        } catch (RemoteException e) {
            bVar.a(8, null, 0, null);
        }
    }

    @Override // com.google.android.gms.internal.ff
    /* JADX INFO: renamed from: aN, reason: merged with bridge method [inline-methods] */
    public hv r(IBinder iBinder) {
        return hv.a.aM(iBinder);
    }

    @Override // com.google.android.gms.internal.ff
    protected String bg() {
        return "com.google.android.gms.panorama.service.START";
    }

    @Override // com.google.android.gms.internal.ff
    protected String bh() {
        return "com.google.android.gms.panorama.internal.IPanoramaService";
    }
}
