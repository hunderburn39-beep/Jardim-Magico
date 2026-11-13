package com.tapjoy;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

/* JADX INFO: loaded from: classes.dex */
public class TJCloseButton extends ImageButton {
    private ClosePosition a;

    public enum ClosePosition {
        TOP_LEFT(new int[]{10, 9}),
        TOP_CENTER(new int[]{10, 14}),
        TOP_RIGHT(new int[]{10, 11}),
        CENTER(new int[]{13}),
        BOTTOM_LEFT(new int[]{12, 9}),
        BOTTOM_CENTER(new int[]{12, 14}),
        BOTTOM_RIGHT(new int[]{12, 11});

        final RelativeLayout.LayoutParams a = new RelativeLayout.LayoutParams(-2, -2);

        ClosePosition(int[] layoutParams) {
            for (int i : layoutParams) {
                this.a.addRule(i);
            }
            int deviceScreenDensityScale = (int) ((-10.0f) * TapjoyConnectCore.getDeviceScreenDensityScale());
            this.a.setMargins(0, deviceScreenDensityScale, deviceScreenDensityScale, 0);
        }
    }

    public TJCloseButton(Context context) {
        this(context, ClosePosition.TOP_RIGHT);
    }

    public TJCloseButton(Context context, ClosePosition closePosition) {
        super(context);
        this.a = closePosition;
        setImageBitmap(TapjoyUtil.loadBitmapFromJar("tj_close_button.png", context));
        setBackgroundColor(16777215);
        setLayoutParams(this.a.a);
    }

    @Override // android.widget.ImageView, android.view.View
    @TargetApi(11)
    protected void onAttachedToWindow() {
        if (Build.VERSION.SDK_INT >= 12) {
            setAlpha(BitmapDescriptorFactory.HUE_RED);
            setVisibility(0);
            setClickable(false);
            new Handler().postDelayed(new Runnable() { // from class: com.tapjoy.TJCloseButton.1
                @Override // java.lang.Runnable
                @SuppressLint({"NewApi"})
                public final void run() {
                    TJCloseButton.this.animate().alpha(1.0f).setDuration(500L).setListener(new Animator.AnimatorListener() { // from class: com.tapjoy.TJCloseButton.1.1
                        @Override // android.animation.Animator.AnimatorListener
                        public final void onAnimationCancel(Animator arg0) {
                            TJCloseButton.this.setClickable(true);
                        }

                        @Override // android.animation.Animator.AnimatorListener
                        public final void onAnimationRepeat(Animator arg0) {
                        }

                        @Override // android.animation.Animator.AnimatorListener
                        public final void onAnimationStart(Animator arg0) {
                        }

                        @Override // android.animation.Animator.AnimatorListener
                        public final void onAnimationEnd(Animator arg0) {
                            TJCloseButton.this.setClickable(true);
                        }
                    });
                }
            }, 2000L);
        }
    }
}
