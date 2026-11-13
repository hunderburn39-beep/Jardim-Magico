package com.chartboost.sdk;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import com.chartboost.sdk.Libraries.CBLogging;
import com.chartboost.sdk.Libraries.CBUtility;
import com.chartboost.sdk.Libraries.j;
import com.chartboost.sdk.Model.CBError;

/* JADX INFO: loaded from: classes.dex */
@SuppressLint({"Registered"})
public class CBImpressionActivity extends Activity {
    final h a = h.a();
    private Activity b = null;
    private final PhoneStateListener c = new PhoneStateListener() { // from class: com.chartboost.sdk.CBImpressionActivity.1
        @Override // android.telephony.PhoneStateListener
        public void onCallStateChanged(int state, String incomingNumber) {
            if (state == 1) {
                CBLogging.a("CBImpressionActivity", "##### Phone call State: Ringing");
                CBLogging.a("CBImpressionActivity", "##### Pausing the impression");
                CBImpressionActivity.this.onPause();
            } else if (state == 0) {
                CBLogging.a("CBImpressionActivity", "##### Phone call State: Idle");
                CBLogging.a("CBImpressionActivity", "##### Resuming the impression");
                CBImpressionActivity.this.onResume();
            } else if (state == 2) {
                CBLogging.a("CBImpressionActivity", "##### Phone call State: OffHook");
                CBLogging.a("CBImpressionActivity", "##### Pausing the impression");
                CBImpressionActivity.this.onPause();
            }
            super.onCallStateChanged(state, incomingNumber);
        }
    };

    @Override // android.app.Activity, android.view.Window.Callback
    public boolean dispatchTouchEvent(MotionEvent event) {
        return this.b != null ? this.b.dispatchTouchEvent(event) : super.dispatchTouchEvent(event);
    }

    public void forwardTouchEvents(Activity host) {
        this.b = host;
    }

    @Override // android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if ((getIntent() != null && !getIntent().getBooleanExtra("isChartboost", false)) || this.a == null) {
            CBLogging.b("CBImpressionActivity", "This activity cannot be called from outside chartboost SDK");
            finish();
            return;
        }
        a();
        requestWindowFeature(1);
        getWindow().setWindowAnimations(0);
        this.a.a(this);
        setContentView(new RelativeLayout(this));
        b();
        CBLogging.a(CBImpressionActivity.class.getName(), "Impression Activity onCreate() called");
    }

    @TargetApi(11)
    private void a() {
        if (com.chartboost.sdk.impl.g.a().a(11)) {
            getWindow().setFlags(16777216, 16777216);
        }
    }

    @Override // android.app.Activity
    protected void onStart() {
        super.onStart();
        if (!this.a.o()) {
            this.a.d(this);
        }
        try {
            TelephonyManager telephonyManager = (TelephonyManager) getSystemService("phone");
            if (telephonyManager != null) {
                telephonyManager.listen(this.c, 32);
            }
        } catch (SecurityException e) {
            CBLogging.a("CBImpressionActivity", "Unable to register phone state listener", e);
        } catch (Exception e2) {
            com.chartboost.sdk.Tracking.a.a(getClass(), "onStart", e2);
        }
    }

    @Override // android.app.Activity
    protected void onResume() {
        super.onResume();
        if (!this.a.o()) {
            this.a.a(j.a(this));
        }
        CBUtility.a(com.chartboost.sdk.impl.g.a().a((Activity) this));
    }

    @Override // android.app.Activity
    protected void onPause() {
        super.onPause();
        if (!this.a.o()) {
            this.a.b(j.a(this));
        }
    }

    @Override // android.app.Activity
    protected void onStop() {
        super.onStop();
        if (!this.a.o()) {
            this.a.d(j.a(this));
        }
        try {
            TelephonyManager telephonyManager = (TelephonyManager) getSystemService("phone");
            if (telephonyManager != null) {
                telephonyManager.listen(this.c, 0);
            }
        } catch (SecurityException e) {
            CBLogging.a("CBImpressionActivity", "Unable to deregister phone state listener", e);
        } catch (Exception e2) {
            com.chartboost.sdk.Tracking.a.a(getClass(), "onStop", e2);
        }
    }

    @Override // android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        if (!this.a.o()) {
            this.a.i(this);
        }
    }

    @Override // android.app.Activity
    public void onBackPressed() {
        if (!this.a.f()) {
            super.onBackPressed();
        }
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (Build.VERSION.SDK_INT >= 14 && getWindow() != null && getWindow().getDecorView() != null && !getWindow().getDecorView().isHardwareAccelerated()) {
            CBLogging.b("CBImpressionActivity", "The activity passed down is not hardware accelerated, so Chartboost cannot show ads");
            c cVar = this.a.a;
            com.chartboost.sdk.Model.a aVarC = cVar.c();
            if (aVarC != null) {
                aVarC.a(CBError.CBImpressionError.HARDWARE_ACCELERATION_DISABLED);
                cVar.c.a(aVarC.q().e(), aVarC.f, aVarC.p(), CBError.CBImpressionError.HARDWARE_ACCELERATION_DISABLED);
            }
            finish();
        }
    }

    private void b() {
        if (!com.chartboost.sdk.impl.g.a().a(14)) {
            CBUtility.c().post(new Runnable() { // from class: com.chartboost.sdk.CBImpressionActivity.2
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        CBLogging.e("VideoInit", "preparing activity for video surface");
                        CBImpressionActivity.this.addContentView(new SurfaceView(CBImpressionActivity.this), new ViewGroup.LayoutParams(0, 0));
                    } catch (Exception e) {
                        com.chartboost.sdk.Tracking.a.a(CBImpressionActivity.class, "postCreateSurfaceView Runnable.run", e);
                    }
                }
            });
        }
    }
}
