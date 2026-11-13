package com.fyber.c.c;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/* JADX INFO: compiled from: CountDownTimerLayout.java */
/* JADX INFO: loaded from: classes.dex */
public final class b extends com.fyber.c.a {
    private TextView b;
    private int c;
    private a d;
    private float e;

    public b(Context context, int i) {
        super(context);
        this.c = i;
        this.e = 360.0f / this.c;
        this.b = new TextView(context);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams.addRule(13, -1);
        this.b.setLayoutParams(layoutParams);
        this.b.setGravity(17);
        this.b.setText(b(this.c));
        this.b.setTextColor(-1);
        this.b.setContentDescription("countdownTextView");
        int iA = a(30);
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setColor(-16777216);
        gradientDrawable.setShape(1);
        gradientDrawable.setAlpha(128);
        gradientDrawable.setSize(iA, iA);
        gradientDrawable.setUseLevel(false);
        this.d = new a();
        this.d.a(360.0f);
        a(new LayerDrawable(new Drawable[]{this.d, gradientDrawable}));
        addView(this.b);
        setContentDescription("countdownView");
    }

    public final void a(long j) {
        long j2 = ((long) this.c) - j;
        if (j2 >= 0) {
            this.b.setText(b(j2));
            this.d.a(j2 * this.e);
        }
    }

    private static String b(long j) {
        long seconds = TimeUnit.MILLISECONDS.toSeconds(j + 850);
        return seconds > 59 ? String.format(Locale.ENGLISH, "%d:%02d", Long.valueOf(TimeUnit.MILLISECONDS.toMinutes(850 + j) % TimeUnit.HOURS.toMinutes(1L)), Long.valueOf(seconds % TimeUnit.MINUTES.toSeconds(1L))) : Long.valueOf(seconds).toString();
    }
}
