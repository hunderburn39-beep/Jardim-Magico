package com.tapjoy.internal;

import android.view.animation.Animation;

/* JADX INFO: loaded from: classes.dex */
public class ai {
    protected final Animation a;

    /* JADX WARN: $VALUES field not found */
    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    public static final class a {
        public static final int a = 1;
        public static final int b = 2;
        public static final int c = 3;
        public static final int d = 4;
        private static final /* synthetic */ int[] e = {a, b, c, d};

        public static int[] a() {
            return (int[]) e.clone();
        }
    }

    /* JADX INFO: renamed from: com.tapjoy.internal.ai$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ int[] a = new int[a.a().length];

        static {
            try {
                a[a.a - 1] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                a[a.b - 1] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                a[a.c - 1] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                a[a.d - 1] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    public ai(Animation animation) {
        this.a = animation;
        animation.setDuration(400L);
    }

    public Animation a() {
        return this.a;
    }

    public final ai b() {
        this.a.setDuration(600L);
        return this;
    }
}
