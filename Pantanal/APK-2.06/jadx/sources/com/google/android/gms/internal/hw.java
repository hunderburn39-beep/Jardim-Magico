package com.google.android.gms.internal;

import android.content.Intent;
import android.net.Uri;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.panorama.Panorama;
import com.google.android.gms.panorama.PanoramaApi;

/* JADX INFO: loaded from: classes.dex */
public class hw implements PanoramaApi {

    private static abstract class a extends com.google.android.gms.common.api.a.b<PanoramaApi.PanoramaResult, hx> {
        public a() {
            super(Panorama.wx);
        }

        @Override // com.google.android.gms.common.api.a.AbstractC0032a
        /* JADX INFO: renamed from: X, reason: merged with bridge method [inline-methods] */
        public PanoramaApi.PanoramaResult d(final Status status) {
            return new PanoramaApi.PanoramaResult() { // from class: com.google.android.gms.internal.hw.a.1
                @Override // com.google.android.gms.common.api.Result
                public Status getStatus() {
                    return status;
                }

                @Override // com.google.android.gms.panorama.PanoramaApi.PanoramaResult
                public Intent getViewerIntent() {
                    return null;
                }
            };
        }
    }

    @Override // com.google.android.gms.panorama.PanoramaApi
    public PendingResult<PanoramaApi.PanoramaResult> loadPanoramaInfo(GoogleApiClient client, final Uri uri) {
        return client.a(new a() { // from class: com.google.android.gms.internal.hw.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.google.android.gms.common.api.a.b
            public void a(hx hxVar) {
                hxVar.a((com.google.android.gms.common.api.a.d<PanoramaApi.PanoramaResult>) this, uri, false);
            }
        });
    }

    @Override // com.google.android.gms.panorama.PanoramaApi
    public PendingResult<PanoramaApi.PanoramaResult> loadPanoramaInfoAndGrantAccess(GoogleApiClient client, final Uri uri) {
        return client.a(new a() { // from class: com.google.android.gms.internal.hw.2
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.google.android.gms.common.api.a.b
            public void a(hx hxVar) {
                hxVar.a((com.google.android.gms.common.api.a.d<PanoramaApi.PanoramaResult>) this, uri, true);
            }
        });
    }
}
