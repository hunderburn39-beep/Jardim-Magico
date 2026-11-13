package com.fyber.ads.videos;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Window;
import android.webkit.ValueCallback;
import com.fyber.mediation.MediationUserActivityListener;
import com.fyber.utils.FyberLogger;

/* JADX INFO: loaded from: classes.dex */
public class RewardedVideoActivity extends Activity implements c {
    public static final String ENGAGEMENT_STATUS = "ENGAGEMENT_STATUS";
    public static final String REQUEST_STATUS_PARAMETER_ABORTED_VALUE = "CLOSE_ABORTED";
    public static final String REQUEST_STATUS_PARAMETER_ERROR = "ERROR";
    public static final String REQUEST_STATUS_PARAMETER_FINISHED_VALUE = "CLOSE_FINISHED";
    private MediationUserActivityListener d;
    private boolean a = false;
    private boolean b = false;
    private boolean c = false;
    private boolean e = false;
    private IntentFilter f = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
    private BroadcastReceiver g = new BroadcastReceiver() { // from class: com.fyber.ads.videos.RewardedVideoActivity.1
        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            if (!(intent.getBooleanExtra("noConnectivity", false) ? false : true)) {
                b.a.f();
            }
        }
    };

    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) {
        b.a.b(true);
        super.onCreate(bundle);
        Window window = getWindow();
        window.requestFeature(1);
        window.setFlags(1024, 1024);
        window.addFlags(128);
        registerReceiver(this.g, this.f);
        if (b.a.c()) {
            if (bundle != null) {
                this.a = bundle.getBoolean("PENDING_CLOSE");
                this.b = bundle.getBoolean("ENGAGEMENT_ALREADY_CLOSE");
                this.c = bundle.getBoolean("PLAY_THROUGH_MEDIATION");
                a();
                return;
            }
            b.a.a(new ValueCallback<Boolean>() { // from class: com.fyber.ads.videos.RewardedVideoActivity.2
                @Override // android.webkit.ValueCallback
                public final /* synthetic */ void onReceiveValue(Boolean bool) {
                    RewardedVideoActivity.this.c = bool.booleanValue();
                    RewardedVideoActivity.this.a();
                }
            });
            return;
        }
        FyberLogger.d("RewardedVideoActivity", "Currently it is not possible to show offers. Make sure you have requested offers.");
        setResultAndClose(REQUEST_STATUS_PARAMETER_ERROR);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a() {
        if (!this.c) {
            setRequestedOrientation(6);
        }
        b.a.a(this);
        b.a.a(this, this.c);
    }

    @Override // android.app.Activity
    protected void onResume() {
        super.onResume();
        if (this.a) {
            b.a.a();
        } else if (!this.c) {
            b.a.a(this);
            b.a.e();
        }
    }

    @Override // android.app.Activity
    protected void onPause() {
        super.onPause();
        if (!this.a && !this.c && !this.b) {
            b.a.d();
            b.a.a();
            b.a.a((c) null);
        }
    }

    @Override // android.app.Activity
    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putBoolean("PENDING_CLOSE", this.a);
        bundle.putBoolean("ENGAGEMENT_ALREADY_CLOSE", this.b);
        bundle.putBoolean("PLAY_THROUGH_MEDIATION", this.c);
    }

    @Override // android.app.Activity
    protected void onDestroy() {
        unregisterReceiver(this.g);
        this.d = null;
        b.a.b(false);
        super.onDestroy();
    }

    protected void closeActivity() {
        this.b = true;
        b.a.a((c) null);
        finish();
    }

    @Override // com.fyber.ads.videos.c
    public void didReceiveOffers(boolean z) {
    }

    @Override // com.fyber.ads.videos.c
    public void didChangeStatus(c.a aVar) {
        switch (aVar) {
            case CLOSE_FINISHED:
                setResultAndClose(REQUEST_STATUS_PARAMETER_FINISHED_VALUE);
                break;
            case CLOSE_ABORTED:
                setResultAndClose(REQUEST_STATUS_PARAMETER_ABORTED_VALUE);
                break;
            case ERROR:
                setResultAndClose(REQUEST_STATUS_PARAMETER_ERROR);
                break;
            case PENDING_CLOSE:
                this.a = true;
                break;
            case STARTED:
                this.e = true;
                break;
        }
    }

    protected void setResultAndClose(String str) {
        Intent intent = new Intent();
        intent.putExtra(ENGAGEMENT_STATUS, str);
        setResult(-1, intent);
        closeActivity();
    }

    public void setRewardedVideoListener(MediationUserActivityListener mediationUserActivityListener) {
        if (this.d == null) {
            this.d = mediationUserActivityListener;
        }
    }

    @Override // android.app.Activity
    protected void onUserLeaveHint() {
        if (this.d != null) {
            this.d.notifyOnUserLeft();
        }
        this.e = true;
        super.onUserLeaveHint();
    }

    @Override // android.app.Activity
    public void onBackPressed() {
        if (this.e) {
            if (this.d == null || !this.d.notifyOnBackPressed()) {
                if (!this.c) {
                    b.a.a();
                } else {
                    super.onBackPressed();
                }
            }
        }
    }
}
