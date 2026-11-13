package com.google.android.gms.wallet;

import android.app.Activity;
import android.content.Context;
import android.os.Looper;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.fc;
import com.google.android.gms.internal.fq;
import com.google.android.gms.internal.iu;
import com.google.android.gms.internal.jf;
import com.google.android.gms.internal.jg;
import com.google.android.gms.internal.ji;
import com.google.android.gms.internal.jj;
import com.google.android.gms.internal.ka;
import java.util.Locale;

/* JADX INFO: loaded from: classes.dex */
public final class Wallet {
    private static final Api.c<jg> wx = new Api.c<>();
    private static final Api.b<jg, WalletOptions> wy = new Api.b<jg, WalletOptions>() { // from class: com.google.android.gms.wallet.Wallet.1
        @Override // com.google.android.gms.common.api.Api.b
        public jg a(Context context, Looper looper, fc fcVar, WalletOptions walletOptions, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
            fq.b(context instanceof Activity, "An Activity must be used for Wallet APIs");
            Activity activity = (Activity) context;
            if (walletOptions == null) {
                walletOptions = new WalletOptions();
            }
            return new jg(activity, looper, connectionCallbacks, onConnectionFailedListener, walletOptions.environment, fcVar.getAccountName(), walletOptions.theme);
        }

        @Override // com.google.android.gms.common.api.Api.b
        public int getPriority() {
            return Integer.MAX_VALUE;
        }
    };
    public static final Api<WalletOptions> API = new Api<>(wy, wx, new Scope[0]);
    public static final Payments Payments = new jf();
    public static final ka aco = new jj();
    public static final iu acp = new ji();

    public static final class WalletOptions implements Api.ApiOptions.HasOptions {
        public final int environment;
        public final int theme;

        public static final class Builder {
            private int acq = 0;
            private int mTheme = 0;

            public WalletOptions build() {
                return new WalletOptions(this);
            }

            public Builder setEnvironment(int environment) {
                if (environment != 0 && environment != 2 && environment != 1) {
                    throw new IllegalArgumentException(String.format(Locale.US, "Invalid environment value %d", Integer.valueOf(environment)));
                }
                this.acq = environment;
                return this;
            }

            public Builder setTheme(int theme) {
                if (theme != 0 && theme != 1) {
                    throw new IllegalArgumentException(String.format(Locale.US, "Invalid theme value %d", Integer.valueOf(theme)));
                }
                this.mTheme = theme;
                return this;
            }
        }

        private WalletOptions() {
            this(new Builder());
        }

        private WalletOptions(Builder builder) {
            this.environment = builder.acq;
            this.theme = builder.mTheme;
        }
    }

    public static abstract class a<R extends Result> extends com.google.android.gms.common.api.a.b<R, jg> {
        public a() {
            super(Wallet.wx);
        }
    }

    public static abstract class b extends a<Status> {
        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.android.gms.common.api.a.AbstractC0032a
        /* JADX INFO: renamed from: f, reason: merged with bridge method [inline-methods] */
        public Status d(Status status) {
            return status;
        }
    }

    private Wallet() {
    }

    @Deprecated
    public static void changeMaskedWallet(GoogleApiClient googleApiClient, String googleTransactionId, String merchantTransactionId, int requestCode) {
        Payments.changeMaskedWallet(googleApiClient, googleTransactionId, merchantTransactionId, requestCode);
    }

    @Deprecated
    public static void checkForPreAuthorization(GoogleApiClient googleApiClient, int requestCode) {
        Payments.checkForPreAuthorization(googleApiClient, requestCode);
    }

    @Deprecated
    public static void loadFullWallet(GoogleApiClient googleApiClient, FullWalletRequest request, int requestCode) {
        Payments.loadFullWallet(googleApiClient, request, requestCode);
    }

    @Deprecated
    public static void loadMaskedWallet(GoogleApiClient googleApiClient, MaskedWalletRequest request, int requestCode) {
        Payments.loadMaskedWallet(googleApiClient, request, requestCode);
    }

    @Deprecated
    public static void notifyTransactionStatus(GoogleApiClient googleApiClient, NotifyTransactionStatusRequest request) {
        Payments.notifyTransactionStatus(googleApiClient, request);
    }
}
