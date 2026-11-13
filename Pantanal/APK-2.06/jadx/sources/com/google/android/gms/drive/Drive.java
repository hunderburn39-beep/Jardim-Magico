package com.google.android.gms.drive;

import android.content.Context;
import android.os.Looper;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.drive.internal.l;
import com.google.android.gms.drive.internal.n;
import com.google.android.gms.drive.internal.p;
import com.google.android.gms.internal.fc;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public final class Drive {
    public static final Api.c<n> wx = new Api.c<>();
    public static final Api.b<n, Api.ApiOptions.NoOptions> wy = new Api.b<n, Api.ApiOptions.NoOptions>() { // from class: com.google.android.gms.drive.Drive.1
        @Override // com.google.android.gms.common.api.Api.b
        /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
        public n a(Context context, Looper looper, fc fcVar, Api.ApiOptions.NoOptions noOptions, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
            List<String> listEE = fcVar.eE();
            return new n(context, looper, fcVar, connectionCallbacks, onConnectionFailedListener, (String[]) listEE.toArray(new String[listEE.size()]));
        }

        @Override // com.google.android.gms.common.api.Api.b
        public int getPriority() {
            return Integer.MAX_VALUE;
        }
    };
    public static final Scope SCOPE_FILE = new Scope(Scopes.DRIVE_FILE);
    public static final Scope SCOPE_APPFOLDER = new Scope(Scopes.DRIVE_APPFOLDER);
    public static final Scope EE = new Scope(Scopes.DRIVE_FULL);
    public static final Scope EF = new Scope(Scopes.DRIVE_APPS);
    public static final Api<Api.ApiOptions.NoOptions> API = new Api<>(wy, wx, new Scope[0]);
    public static final DriveApi DriveApi = new l();
    public static final c EG = new p();

    private Drive() {
    }
}
