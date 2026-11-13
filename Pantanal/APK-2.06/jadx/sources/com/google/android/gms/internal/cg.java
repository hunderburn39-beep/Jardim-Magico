package com.google.android.gms.internal;

import android.content.Context;
import android.media.MediaPlayer;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import android.widget.MediaController;
import android.widget.VideoView;
import com.tapjoy.TJAdUnitConstants;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public final class cg extends FrameLayout implements MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener, MediaPlayer.OnPreparedListener {
    private final dz lC;
    private final MediaController os;
    private final a ot;
    private final VideoView ou;
    private long ov;
    private String ow;

    private static final class a {
        private final Runnable kW;
        private volatile boolean ox = false;

        public a(final cg cgVar) {
            this.kW = new Runnable() { // from class: com.google.android.gms.internal.cg.a.1
                private final WeakReference<cg> oy;

                {
                    this.oy = new WeakReference<>(cgVar);
                }

                @Override // java.lang.Runnable
                public void run() {
                    cg cgVar2 = this.oy.get();
                    if (a.this.ox || cgVar2 == null) {
                        return;
                    }
                    cgVar2.aV();
                    a.this.aW();
                }
            };
        }

        public void aW() {
            dv.rp.postDelayed(this.kW, 250L);
        }

        public void cancel() {
            this.ox = true;
            dv.rp.removeCallbacks(this.kW);
        }
    }

    public cg(Context context, dz dzVar) {
        super(context);
        this.lC = dzVar;
        this.ou = new VideoView(context);
        addView(this.ou, new FrameLayout.LayoutParams(-1, -1, 17));
        this.os = new MediaController(context);
        this.ot = new a(this);
        this.ot.aW();
        this.ou.setOnCompletionListener(this);
        this.ou.setOnPreparedListener(this);
        this.ou.setOnErrorListener(this);
    }

    private static void a(dz dzVar, String str) {
        a(dzVar, str, new HashMap(1));
    }

    public static void a(dz dzVar, String str, String str2) {
        boolean z = str2 == null;
        HashMap map = new HashMap(z ? 2 : 3);
        map.put("what", str);
        if (!z) {
            map.put("extra", str2);
        }
        a(dzVar, TJAdUnitConstants.String.VIDEO_ERROR, map);
    }

    private static void a(dz dzVar, String str, String str2, String str3) {
        HashMap map = new HashMap(2);
        map.put(str2, str3);
        a(dzVar, str, map);
    }

    private static void a(dz dzVar, String str, Map<String, String> map) {
        map.put("event", str);
        dzVar.a("onVideoEvent", map);
    }

    public void aU() {
        if (TextUtils.isEmpty(this.ow)) {
            a(this.lC, "no_src", (String) null);
        } else {
            this.ou.setVideoPath(this.ow);
        }
    }

    public void aV() {
        long currentPosition = this.ou.getCurrentPosition();
        if (this.ov != currentPosition) {
            a(this.lC, "timeupdate", "time", String.valueOf(currentPosition / 1000.0f));
            this.ov = currentPosition;
        }
    }

    public void b(MotionEvent motionEvent) {
        this.ou.dispatchTouchEvent(motionEvent);
    }

    public void destroy() {
        this.ot.cancel();
        this.ou.stopPlayback();
    }

    public void k(boolean z) {
        if (z) {
            this.ou.setMediaController(this.os);
        } else {
            this.os.hide();
            this.ou.setMediaController(null);
        }
    }

    public void o(String str) {
        this.ow = str;
    }

    @Override // android.media.MediaPlayer.OnCompletionListener
    public void onCompletion(MediaPlayer mediaPlayer) {
        a(this.lC, "ended");
    }

    @Override // android.media.MediaPlayer.OnErrorListener
    public boolean onError(MediaPlayer mediaPlayer, int what, int extra) {
        a(this.lC, String.valueOf(what), String.valueOf(extra));
        return true;
    }

    @Override // android.media.MediaPlayer.OnPreparedListener
    public void onPrepared(MediaPlayer mediaPlayer) {
        a(this.lC, "canplaythrough", "duration", String.valueOf(this.ou.getDuration() / 1000.0f));
    }

    public void pause() {
        this.ou.pause();
    }

    public void play() {
        this.ou.start();
    }

    public void seekTo(int timeInMilliseconds) {
        this.ou.seekTo(timeInMilliseconds);
    }
}
