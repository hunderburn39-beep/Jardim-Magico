package com.tapjoy;

import android.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.VideoView;
import com.tapjoy.internal.fi;
import com.tapjoy.internal.fu;
import com.tapjoy.mraid.view.BasicWebView;
import com.tapjoy.mraid.view.MraidView;
import java.util.Timer;
import java.util.TimerTask;

/* JADX INFO: loaded from: classes.dex */
public class TJAdUnitActivity extends Activity implements View.OnClickListener {
    private static TJAdUnitActivity a;
    private TJAdUnit b;
    private TJPlacementData c;
    private a d;
    private TJCloseButton g;
    private ProgressBar h;
    private TJAdUnitSaveStateData e = new TJAdUnitSaveStateData();
    private RelativeLayout f = null;
    private boolean i = false;

    @Override // android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        byte b = 0;
        TapjoyLog.d("TJAdUnitActivity", "TJAdUnitActivity onCreate: " + savedInstanceState);
        super.onCreate(savedInstanceState);
        a = this;
        if (savedInstanceState != null) {
            this.e = (TJAdUnitSaveStateData) savedInstanceState.getSerializable("ad_unit_bundle");
        }
        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.getSerializable(TJAdUnitConstants.EXTRA_TJ_PLACEMENT_DATA) != null) {
            this.c = (TJPlacementData) extras.getSerializable(TJAdUnitConstants.EXTRA_TJ_PLACEMENT_DATA);
            if (this.c.getContentViewId() != null) {
                TapjoyConnectCore.viewWillOpen(this.c.getContentViewId(), 1);
            }
            if (TJPlacementManager.a(this.c.getKey()) != null) {
                this.b = TJPlacementManager.a(this.c.getKey()).getAdUnit();
                this.b.setContext(this);
            } else {
                this.b = new TJAdUnit(this);
            }
            if (!this.b.isAdUnitConstructed()) {
                TapjoyLog.d("TJAdUnitActivity", "No content loaded for ad unit -- loading now");
                this.b.load(this.c);
            }
            if (Build.VERSION.SDK_INT < 11) {
                setTheme(R.style.Theme);
            } else {
                requestWindowFeature(1);
                getWindow().setFlags(1024, 1024);
                getWindow().setFlags(16777216, 16777216);
            }
            getWindow().setBackgroundDrawable(new ColorDrawable(0));
            if (!TapjoyConnectCore.isUnitTestMode()) {
                this.d = new a(this, b);
                registerReceiver(this.d, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
            }
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, -1);
            this.f = new RelativeLayout(this);
            this.f.setLayoutParams(layoutParams);
            this.f.setBackgroundColor(0);
            BasicWebView backgroundWebView = this.b.getBackgroundWebView();
            backgroundWebView.setLayoutParams(layoutParams);
            if (backgroundWebView.getParent() != null) {
                ((ViewGroup) backgroundWebView.getParent()).removeView(backgroundWebView);
            }
            MraidView webView = this.b.getWebView();
            webView.setLayoutParams(layoutParams);
            if (webView.getParent() != null) {
                ((ViewGroup) webView.getParent()).removeView(webView);
            }
            VideoView videoView = this.b.getVideoView();
            RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(-1, -1);
            layoutParams2.addRule(13);
            videoView.setLayoutParams(layoutParams2);
            if (videoView.getParent() != null) {
                ((ViewGroup) videoView.getParent()).removeView(videoView);
            }
            this.f.addView(backgroundWebView);
            this.f.addView(videoView);
            this.f.addView(webView);
            this.h = new ProgressBar(this, null, R.attr.progressBarStyleLarge);
            if (this.c.hasProgressSpinner()) {
                setProgressSpinnerVisibility(true);
            } else {
                setProgressSpinnerVisibility(false);
            }
            RelativeLayout.LayoutParams layoutParams3 = new RelativeLayout.LayoutParams(-2, -2);
            layoutParams3.addRule(13);
            this.h.setLayoutParams(layoutParams3);
            this.f.addView(this.h);
            if (!this.b.getWebView().isMraid()) {
                this.g = new TJCloseButton(this);
                this.g.setOnClickListener(this);
                this.f.addView(this.g);
            }
            setContentView(this.f);
            this.b.setVisible(true);
            TJCorePlacement tJCorePlacementA = TJPlacementManager.a(this.c.getKey());
            if (tJCorePlacementA != null) {
                tJCorePlacementA.f();
                return;
            }
            return;
        }
        TapjoyLog.e("TJAdUnitActivity", new TapjoyErrorMessage(TapjoyErrorMessage.ErrorType.SDK_ERROR, "Failed to launch AdUnit Activity"));
        finish();
    }

    public void setCloseButtonVisibility(boolean visibility) {
        if (visibility) {
            this.g.setVisibility(0);
        } else {
            this.g.setVisibility(4);
        }
    }

    public void setProgressSpinnerVisibility(boolean visibility) {
        if (visibility) {
            this.h.setVisibility(0);
        } else {
            this.h.setVisibility(4);
        }
    }

    @Override // android.app.Activity
    public void onBackPressed() {
        handleClose();
    }

    public void handleClose() {
        handleClose(false);
    }

    public void handleClose(boolean shouldForceClose) {
        if (!this.b.getCloseRequested()) {
            TapjoyLog.d("TJAdUnitActivity", TJAdUnitConstants.String.CLOSE_REQUESTED);
            this.b.closeRequested(shouldForceClose);
            new Timer().schedule(new TimerTask() { // from class: com.tapjoy.TJAdUnitActivity.1
                @Override // java.util.TimerTask, java.lang.Runnable
                public final void run() {
                    if (TJAdUnitActivity.this.b.getCloseRequested()) {
                        TapjoyLog.d("TJAdUnitActivity", "Did not receive callback from content. Closing ad.");
                        TJAdUnitActivity.this.finish();
                    }
                }
            }, 1000L);
        }
    }

    @Override // android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        a = null;
        TapjoyLog.d("TJAdUnitActivity", "onDestroy");
        if (this.b != null) {
            this.b.destroy();
        }
        if (this.d != null && !TapjoyConnectCore.isUnitTestMode()) {
            unregisterReceiver(this.d);
        }
        if (this.c.isBaseActivity()) {
            if (this.c.getContentViewId() != null) {
                TapjoyConnectCore.viewDidClose(this.c.getContentViewId());
            }
            TJCorePlacement tJCorePlacementA = TJPlacementManager.a(this.c.getKey());
            if (tJCorePlacementA != null) {
                tJCorePlacementA.e();
            }
        }
    }

    @Override // android.app.Activity
    protected void onResume() {
        TapjoyLog.d("TJAdUnitActivity", "onResume");
        super.onResume();
        if (this.b.isLockedOrientation()) {
            setRequestedOrientation(this.b.getOrientation());
        }
        this.b.resume(this.e);
    }

    @Override // android.app.Activity
    protected void onStart() {
        super.onStart();
        if (fu.a().n) {
            this.i = true;
            fi.a(this);
        }
        if (!this.c.isBaseActivity()) {
            setResult(-1, getIntent());
        }
    }

    @Override // android.app.Activity
    protected void onPause() {
        super.onPause();
        this.b.pauseVideo();
    }

    @Override // android.app.Activity
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        TapjoyLog.d("TJAdUnitActivity", "onSaveInstanceState");
        this.e.seekTime = this.b.getVideoSeekTime();
        outState.putSerializable("ad_unit_bundle", this.e);
    }

    @Override // android.app.Activity
    protected void onStop() {
        if (this.i) {
            this.i = false;
            fi.b(this);
        }
        super.onStop();
    }

    class a extends BroadcastReceiver {
        private a() {
        }

        /* synthetic */ a(TJAdUnitActivity tJAdUnitActivity, byte b) {
            this();
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            if (intent.getBooleanExtra("noConnectivity", false)) {
                TapjoyLog.i("TJAdUnitActivity", "Network connectivity lost during ad unit activity");
                TJAdUnitActivity.this.showErrorDialog();
            }
        }
    }

    public void showErrorDialog() {
        if (!isFinishing()) {
            new AlertDialog.Builder(this).setMessage("An error occured. Please try again later.").setPositiveButton("OK", new DialogInterface.OnClickListener() { // from class: com.tapjoy.TJAdUnitActivity.2
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialog, int which) {
                    TJAdUnitActivity.this.handleClose();
                    dialog.cancel();
                }
            }).create().show();
        }
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v) {
        handleClose();
    }

    @Override // android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 327 && data != null && data.hasExtra(TJAdUnitConstants.EXTRA_TJ_PLACEMENT_DATA) && this.b != null) {
            this.b.invokeBridgeCallback(((TJPlacementData) data.getSerializableExtra(TJAdUnitConstants.EXTRA_TJ_PLACEMENT_DATA)).getCallbackID(), Boolean.TRUE);
        }
    }

    static void a() {
        TJAdUnitActivity tJAdUnitActivity = a;
        if (tJAdUnitActivity != null) {
            tJAdUnitActivity.handleClose(true);
        }
    }
}
