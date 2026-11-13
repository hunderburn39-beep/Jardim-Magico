package com.tapjoy.mraid.util;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;
import com.tapjoy.TapjoyLog;
import com.tapjoy.mraid.controller.Abstract;
import com.tapjoy.mraid.listener.Player;

/* JADX INFO: loaded from: classes.dex */
public class MraidPlayer extends VideoView implements MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener, MediaPlayer.OnPreparedListener {
    private static String h = "Loading. Please Wait..";
    private static String i = "MRAID Player";
    private Abstract.PlayerProperties a;
    private AudioManager b;
    private Player c;
    private int d;
    private String e;
    private RelativeLayout f;
    private ImageButton g;
    private boolean j;

    public MraidPlayer(Context context) {
        super(context);
        this.b = (AudioManager) getContext().getSystemService("audio");
    }

    public void setPlayData(Abstract.PlayerProperties properties, String url) {
        this.j = false;
        this.a = properties;
        this.e = url;
    }

    public void playAudio() {
        a();
    }

    public ImageButton getCloseImageButton() {
        return this.g;
    }

    private void a() {
        this.e = this.e.trim();
        this.e = Utils.convert(this.e);
        if (this.e == null && this.c != null) {
            b();
            this.c.onError();
            return;
        }
        setVideoURI(Uri.parse(this.e));
        TapjoyLog.d("player", Uri.parse(this.e).toString());
        if (this.a.showControl()) {
            MediaController mediaController = new MediaController(getContext());
            setMediaController(mediaController);
            mediaController.setAnchorView(this);
        }
        setOnCompletionListener(this);
        setOnErrorListener(this);
        setOnPreparedListener(this);
        if (!this.a.inline && !this.a.inline) {
            this.f = new RelativeLayout(getContext());
            this.f.setLayoutParams(getLayoutParams());
            TextView textView = new TextView(getContext());
            textView.setText(h);
            textView.setTextColor(-1);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, -2);
            layoutParams.addRule(13);
            this.f.addView(textView, layoutParams);
            ((ViewGroup) getParent()).addView(this.f);
        }
        if (!this.a.isAutoPlay()) {
            return;
        }
        start();
    }

    public void playVideo() {
        if (this.a.doMute()) {
            this.d = this.b.getStreamVolume(3);
            this.b.setStreamVolume(3, 0, 4);
        }
        a();
    }

    public void setListener(Player listener) {
        this.c = listener;
    }

    @Override // android.media.MediaPlayer.OnCompletionListener
    public void onCompletion(MediaPlayer mp) {
        if (this.a.doLoop()) {
            start();
        } else if (this.a.exitOnComplete() || this.a.inline) {
            releasePlayer();
        }
    }

    @Override // android.media.MediaPlayer.OnErrorListener
    public boolean onError(MediaPlayer mp, int what, int extra) {
        TapjoyLog.i(i, "Player error : " + what);
        c();
        b();
        if (this.c != null) {
            this.c.onError();
            return false;
        }
        return false;
    }

    @Override // android.media.MediaPlayer.OnPreparedListener
    public void onPrepared(MediaPlayer mp) {
        c();
        if (this.c != null) {
            this.c.onPrepared();
        }
    }

    private void b() {
        ViewGroup viewGroup = (ViewGroup) getParent();
        if (viewGroup != null) {
            viewGroup.removeAllViews();
        }
    }

    public void releasePlayer() {
        if (!this.j) {
            this.j = true;
            stopPlayback();
            b();
            if (this.a != null && this.a.doMute()) {
                this.b.setStreamVolume(3, this.d, 4);
            }
            if (this.c != null) {
                this.c.onComplete();
            }
        }
    }

    private void c() {
        if (this.f != null) {
            ((ViewGroup) getParent()).removeView(this.f);
        }
    }
}
