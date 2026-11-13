package com.google.android.gms.ads;

import android.content.Context;
import com.google.android.gms.internal.ak;
import com.google.android.gms.internal.dv;
import org.anddev.andengine.entity.layer.tiled.tmx.util.constants.TMXConstants;

/* JADX INFO: loaded from: classes.dex */
public final class AdSize {
    public static final int AUTO_HEIGHT = -2;
    public static final int FULL_WIDTH = -1;
    private final int kr;
    private final int ks;
    private final String kt;
    public static final AdSize BANNER = new AdSize(320, 50, "320x50_mb");
    public static final AdSize FULL_BANNER = new AdSize(468, 60, "468x60_as");
    public static final AdSize LARGE_BANNER = new AdSize(320, 100, "320x100_as");
    public static final AdSize LEADERBOARD = new AdSize(728, 90, "728x90_as");
    public static final AdSize MEDIUM_RECTANGLE = new AdSize(300, 250, "300x250_as");
    public static final AdSize WIDE_SKYSCRAPER = new AdSize(160, 600, "160x600_as");
    public static final AdSize SMART_BANNER = new AdSize(-1, -2, "smart_banner");

    public AdSize(int width, int height) {
        this(width, height, (width == -1 ? "FULL" : String.valueOf(width)) + TMXConstants.TAG_OBJECT_ATTRIBUTE_X + (height == -2 ? "AUTO" : String.valueOf(height)) + "_as");
    }

    AdSize(int width, int height, String formatString) {
        if (width < 0 && width != -1) {
            throw new IllegalArgumentException("Invalid width for AdSize: " + width);
        }
        if (height < 0 && height != -2) {
            throw new IllegalArgumentException("Invalid height for AdSize: " + height);
        }
        this.kr = width;
        this.ks = height;
        this.kt = formatString;
    }

    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof AdSize)) {
            return false;
        }
        AdSize adSize = (AdSize) other;
        return this.kr == adSize.kr && this.ks == adSize.ks && this.kt.equals(adSize.kt);
    }

    public int getHeight() {
        return this.ks;
    }

    public int getHeightInPixels(Context context) {
        return this.ks == -2 ? ak.b(context.getResources().getDisplayMetrics()) : dv.a(context, this.ks);
    }

    public int getWidth() {
        return this.kr;
    }

    public int getWidthInPixels(Context context) {
        return this.kr == -1 ? ak.a(context.getResources().getDisplayMetrics()) : dv.a(context, this.kr);
    }

    public int hashCode() {
        return this.kt.hashCode();
    }

    public boolean isAutoHeight() {
        return this.ks == -2;
    }

    public boolean isFullWidth() {
        return this.kr == -1;
    }

    public String toString() {
        return this.kt;
    }
}
