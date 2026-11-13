package com.chartboost.sdk.impl;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.view.SurfaceView;
import android.view.View;
import android.widget.FrameLayout;
import com.chartboost.sdk.Libraries.CBLogging;

/* JADX INFO: loaded from: classes.dex */
public class bb extends FrameLayout {
    private View a;
    private boolean b;

    public interface a {
        void a();

        void a(int i);

        void a(int i, int i2);

        void a(MediaPlayer.OnCompletionListener onCompletionListener);

        void a(MediaPlayer.OnErrorListener onErrorListener);

        void a(MediaPlayer.OnPreparedListener onPreparedListener);

        void a(Uri uri);

        void b();

        int c();

        int d();

        boolean e();
    }

    public bb(Context context) {
        super(context);
        c();
    }

    private void c() {
        this.b = b();
        CBLogging.e("VideoInit", "Choosing " + (this.b ? "texture" : "surface") + " solution for video playback");
        if (this.b) {
            this.a = new ba(getContext());
        } else {
            this.a = new az(getContext());
        }
        addView(this.a, new FrameLayout.LayoutParams(-1, -1));
        if (!this.b) {
            ((SurfaceView) this.a).setZOrderMediaOverlay(true);
        }
    }

    public a a() {
        return (a) this.a;
    }

    @Override // android.view.View
    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        a().a(w, h);
    }

    public static boolean b() {
        return Build.VERSION.SDK_INT >= 14;
    }
}
