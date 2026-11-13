package com.heyzap.sdk.ads;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import com.fyber.mediation.test.data.MediationStatus;
import com.fyber.mediation.test.data.MediationStatusScanner;
import com.fyber.mediation.test.data.NetworkStatus;
import com.fyber.mediation.test.view.NetworkDetailView;
import com.fyber.mediation.test.view.NetworkListView;
import com.heyzap.common.concurrency.FutureHandler;
import com.heyzap.common.concurrency.HandlerExecutorService;

/* JADX INFO: loaded from: classes.dex */
public class MediationTestActivity extends Activity {
    private MediationStatus mediationStatus;
    private NetworkDetailView networkDetailView;
    NetworkListView networkListView = null;

    @Override // android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView loadingView = new TextView(this);
        this.networkListView = new NetworkListView(this);
        loadingView.setText("Searching for third party network adapters");
        loadingView.setTextColor(-16777216);
        MediationStatusScanner.getMediationStatusFuture().addHandler(new FutureHandler<MediationStatus, Void>() { // from class: com.heyzap.sdk.ads.MediationTestActivity.1
            @Override // com.heyzap.common.concurrency.FutureHandler
            public Void handle(MediationStatus val, Exception e) {
                MediationTestActivity.this.mediationStatus = val;
                try {
                    Log.d("MediationTestActivity", "statusFuture2 - handler called");
                    MediationTestActivity.this.networkListView.setStatus(MediationTestActivity.this, val);
                    MediationTestActivity.this.setContentView(MediationTestActivity.this.networkListView);
                    Log.d("MediationTestActivity", "content view replaced");
                    return null;
                } catch (Throwable e2) {
                    e2.printStackTrace();
                    return null;
                }
            }
        }, HandlerExecutorService.getMainThreadExecutor());
        loadingView.setBackgroundColor(-1);
        this.networkListView.setBackgroundColor(-1);
        setContentView(loadingView);
    }

    public void showNetworkDetails(NetworkStatus networkStatus) {
        this.networkDetailView = new NetworkDetailView(this, this.mediationStatus, networkStatus);
        setContentView(this.networkDetailView);
    }

    @Override // android.app.Activity
    public void onBackPressed() {
        if (this.networkDetailView != null) {
            this.networkDetailView = null;
            setContentView(this.networkListView);
        } else {
            super.onBackPressed();
        }
    }
}
