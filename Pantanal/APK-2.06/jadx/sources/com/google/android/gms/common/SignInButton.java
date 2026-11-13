package com.google.android.gms.common;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import com.google.android.gms.dynamic.g;
import com.google.android.gms.internal.fq;
import com.google.android.gms.internal.fr;
import com.google.android.gms.internal.fs;

/* JADX INFO: loaded from: classes.dex */
public final class SignInButton extends FrameLayout implements View.OnClickListener {
    public static final int COLOR_DARK = 0;
    public static final int COLOR_LIGHT = 1;
    public static final int SIZE_ICON_ONLY = 2;
    public static final int SIZE_STANDARD = 0;
    public static final int SIZE_WIDE = 1;
    private int Av;
    private View Aw;
    private View.OnClickListener Ax;
    private int mSize;

    public SignInButton(Context context) {
        this(context, null);
    }

    public SignInButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SignInButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.Ax = null;
        setStyle(0, 0);
    }

    private static Button a(Context context, int i, int i2) {
        fs fsVar = new fs(context);
        fsVar.a(context.getResources(), i, i2);
        return fsVar;
    }

    private void v(Context context) {
        if (this.Aw != null) {
            removeView(this.Aw);
        }
        try {
            this.Aw = fr.b(context, this.mSize, this.Av);
        } catch (g.a e) {
            Log.w("SignInButton", "Sign in button not found, using placeholder instead");
            this.Aw = a(context, this.mSize, this.Av);
        }
        addView(this.Aw);
        this.Aw.setEnabled(isEnabled());
        this.Aw.setOnClickListener(this);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        if (this.Ax == null || view != this.Aw) {
            return;
        }
        this.Ax.onClick(this);
    }

    public void setColorScheme(int colorScheme) {
        setStyle(this.mSize, colorScheme);
    }

    @Override // android.view.View
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        this.Aw.setEnabled(enabled);
    }

    @Override // android.view.View
    public void setOnClickListener(View.OnClickListener listener) {
        this.Ax = listener;
        if (this.Aw != null) {
            this.Aw.setOnClickListener(this);
        }
    }

    public void setSize(int buttonSize) {
        setStyle(buttonSize, this.Av);
    }

    public void setStyle(int buttonSize, int colorScheme) {
        fq.a(buttonSize >= 0 && buttonSize < 3, "Unknown button size " + buttonSize);
        fq.a(colorScheme >= 0 && colorScheme < 2, "Unknown color scheme " + colorScheme);
        this.mSize = buttonSize;
        this.Av = colorScheme;
        v(getContext());
    }
}
