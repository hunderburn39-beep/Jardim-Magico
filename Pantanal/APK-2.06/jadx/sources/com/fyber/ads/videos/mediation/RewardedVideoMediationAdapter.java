package com.fyber.ads.videos.mediation;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.fyber.cache.CacheManager;
import com.fyber.mediation.MediationAdapter;
import com.fyber.utils.FyberLogger;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public abstract class RewardedVideoMediationAdapter<V extends MediationAdapter> {
    public static final int START_TIMEOUT_DELAY = 4500;
    public static final int VALIDATION_TIMEOUT_DELAY = 4500;
    private a a;
    private Map<String, String> b;
    private b c;
    private Map<String, String> d;
    private boolean e = false;
    private Handler f = new Handler(Looper.getMainLooper(), new Handler.Callback() { // from class: com.fyber.ads.videos.mediation.RewardedVideoMediationAdapter.1
        @Override // android.os.Handler.Callback
        public final boolean handleMessage(Message message) {
            switch (message.what) {
                case 1:
                    RewardedVideoMediationAdapter.this.sendValidationEvent(TPNVideoValidationResult.Timeout);
                    break;
                case 2:
                    RewardedVideoMediationAdapter.this.sendVideoEvent(TPNVideoEvent.Timeout);
                    break;
            }
            return true;
        }
    });
    protected V mAdapter;

    public abstract void startPrecaching();

    public abstract void startVideo(Activity activity);

    public abstract void videosAvailable(Context context);

    public RewardedVideoMediationAdapter(V v) {
        this.mAdapter = v;
    }

    public V getMediationAdapter() {
        return this.mAdapter;
    }

    public final void a(Context context, a aVar, Map<String, String> map) {
        this.a = aVar;
        this.b = map;
        this.f.sendEmptyMessageDelayed(1, 4500L);
        videosAvailable(context);
    }

    public final void a(Activity activity, b bVar, Map<String, String> map) {
        this.e = false;
        this.c = bVar;
        this.d = map;
        this.f.sendEmptyMessageDelayed(2, 4500L);
        startVideo(activity);
    }

    protected boolean isPrecachingDisabled() {
        return CacheManager.a().e();
    }

    protected void sendValidationEvent(TPNVideoValidationResult tPNVideoValidationResult) {
        if (this.a != null) {
            this.f.removeMessages(1);
            this.a.a(getName(), getVersion(), tPNVideoValidationResult, this.b);
            this.a = null;
            this.b = null;
            return;
        }
        FyberLogger.i("RewardedVideoMediationAdapter", "No validation event listener");
    }

    protected void sendVideoEvent(TPNVideoEvent tPNVideoEvent) {
        if (this.c != null) {
            if (tPNVideoEvent.equals(TPNVideoEvent.Started)) {
                this.f.removeMessages(2);
            }
            this.c.a(getName(), getVersion(), tPNVideoEvent, this.d);
            return;
        }
        FyberLogger.i("RewardedVideoMediationAdapter", "No video event listener");
    }

    protected void clearVideoEvent() {
        this.c = null;
        this.d = null;
    }

    protected void setVideoPlayed() {
        sendVideoEvent(TPNVideoEvent.Finished);
        this.e = true;
    }

    protected void notifyVideoStarted() {
        sendVideoEvent(TPNVideoEvent.Started);
    }

    protected void notifyCloseEngagement() {
        sendVideoEvent(this.e ? TPNVideoEvent.Closed : TPNVideoEvent.Aborted);
        clearVideoEvent();
    }

    protected void notifyVideoError() {
        sendVideoEvent(TPNVideoEvent.Error);
        clearVideoEvent();
    }

    protected String getName() {
        return this.mAdapter.getName();
    }

    protected String getVersion() {
        return this.mAdapter.getVersion();
    }
}
