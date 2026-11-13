package com.chartboost.sdk.impl;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.MediaPlayer;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import com.chartboost.sdk.Libraries.CBLogging;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
@SuppressLint({"ViewConstructor"})
public class v extends u {
    private final z c;
    private final Button d;
    private bb e;
    private JSONObject f;

    public v(z zVar, Context context) {
        super(zVar, context);
        this.c = zVar;
        this.d = new Button(context);
        this.d.setTextColor(-14571545);
        this.d.setText("Preview");
        this.d.setOnClickListener(new View.OnClickListener() { // from class: com.chartboost.sdk.impl.v.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                v.this.c();
            }
        });
        addView(this.d, 2);
    }

    @Override // com.chartboost.sdk.impl.u, com.chartboost.sdk.impl.s
    public void a(JSONObject jSONObject, int i) {
        super.a(jSONObject, i);
        this.f = jSONObject;
    }

    void c() {
        CBLogging.c(this, "play the video");
        if (this.e == null) {
            this.e = new bb(getContext());
            this.c.e().addView(this.e, new RelativeLayout.LayoutParams(-1, -1));
            this.e.setVisibility(8);
        }
        final bc bcVar = this.c.f.a.j.a;
        final bb bbVar = this.e;
        bbVar.a().a(new MediaPlayer.OnCompletionListener() { // from class: com.chartboost.sdk.impl.v.2
            @Override // android.media.MediaPlayer.OnCompletionListener
            public void onCompletion(MediaPlayer arg0) {
                bcVar.a(false, bbVar);
            }
        });
        bcVar.a(true, bbVar);
        bbVar.a().a();
    }
}
