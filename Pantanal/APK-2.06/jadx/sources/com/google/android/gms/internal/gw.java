package com.google.android.gms.internal;

import android.accounts.Account;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.identity.intents.AddressConstants;
import com.google.android.gms.identity.intents.UserAddressRequest;

/* JADX INFO: loaded from: classes.dex */
public class gw extends ff<gy> {
    private a NA;
    private final int mTheme;
    private Activity nS;
    private final String wG;

    public static final class a extends gx.a {
        private final int CV;
        private Activity nS;

        public a(int i, Activity activity) {
            this.CV = i;
            this.nS = activity;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setActivity(Activity activity) {
            this.nS = activity;
        }

        @Override // com.google.android.gms.internal.gx
        public void d(int i, Bundle bundle) {
            if (i == 1) {
                Intent intent = new Intent();
                intent.putExtras(bundle);
                PendingIntent pendingIntentCreatePendingResult = this.nS.createPendingResult(this.CV, intent, 1073741824);
                if (pendingIntentCreatePendingResult == null) {
                    return;
                }
                try {
                    pendingIntentCreatePendingResult.send(1);
                    return;
                } catch (PendingIntent.CanceledException e) {
                    Log.w("AddressClientImpl", "Exception settng pending result", e);
                    return;
                }
            }
            ConnectionResult connectionResult = new ConnectionResult(i, bundle != null ? (PendingIntent) bundle.getParcelable("com.google.android.gms.identity.intents.EXTRA_PENDING_INTENT") : null);
            if (connectionResult.hasResolution()) {
                try {
                    connectionResult.startResolutionForResult(this.nS, this.CV);
                    return;
                } catch (IntentSender.SendIntentException e2) {
                    Log.w("AddressClientImpl", "Exception starting pending intent", e2);
                    return;
                }
            }
            try {
                PendingIntent pendingIntentCreatePendingResult2 = this.nS.createPendingResult(this.CV, new Intent(), 1073741824);
                if (pendingIntentCreatePendingResult2 != null) {
                    pendingIntentCreatePendingResult2.send(1);
                }
            } catch (PendingIntent.CanceledException e3) {
                Log.w("AddressClientImpl", "Exception setting pending result", e3);
            }
        }
    }

    public gw(Activity activity, Looper looper, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener, String str, int i) {
        super(activity, looper, connectionCallbacks, onConnectionFailedListener, new String[0]);
        this.wG = str;
        this.nS = activity;
        this.mTheme = i;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.android.gms.internal.ff
    /* JADX INFO: renamed from: R, reason: merged with bridge method [inline-methods] */
    public gy r(IBinder iBinder) {
        return gy.a.T(iBinder);
    }

    public void a(UserAddressRequest userAddressRequest, int i) {
        hO();
        this.NA = new a(i, this.nS);
        try {
            Bundle bundle = new Bundle();
            bundle.putString("com.google.android.gms.identity.intents.EXTRA_CALLING_PACKAGE_NAME", getContext().getPackageName());
            if (!TextUtils.isEmpty(this.wG)) {
                bundle.putParcelable("com.google.android.gms.identity.intents.EXTRA_ACCOUNT", new Account(this.wG, GoogleAuthUtil.GOOGLE_ACCOUNT_TYPE));
            }
            bundle.putInt("com.google.android.gms.identity.intents.EXTRA_THEME", this.mTheme);
            hN().a(this.NA, userAddressRequest, bundle);
        } catch (RemoteException e) {
            Log.e("AddressClientImpl", "Exception requesting user address", e);
            Bundle bundle2 = new Bundle();
            bundle2.putInt(AddressConstants.Extras.EXTRA_ERROR_CODE, AddressConstants.ErrorCodes.ERROR_CODE_NO_APPLICABLE_ADDRESSES);
            this.NA.d(1, bundle2);
        }
    }

    @Override // com.google.android.gms.internal.ff
    protected void a(fm fmVar, ff.e eVar) throws RemoteException {
        fmVar.d(eVar, GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_VERSION_CODE, getContext().getPackageName());
    }

    @Override // com.google.android.gms.internal.ff
    protected String bg() {
        return "com.google.android.gms.identity.service.BIND";
    }

    @Override // com.google.android.gms.internal.ff
    protected String bh() {
        return "com.google.android.gms.identity.intents.internal.IAddressService";
    }

    @Override // com.google.android.gms.internal.ff, com.google.android.gms.common.GooglePlayServicesClient, com.google.android.gms.common.api.Api.a
    public void disconnect() {
        super.disconnect();
        if (this.NA != null) {
            this.NA.setActivity(null);
            this.NA = null;
        }
    }

    protected gy hN() {
        return (gy) super.eM();
    }

    protected void hO() {
        super.bT();
    }
}
