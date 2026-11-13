package com.fyber.ads.banners;

import org.anddev.andengine.entity.layer.tiled.tmx.util.constants.TMXConstants;

/* JADX INFO: loaded from: classes.dex */
public class BannerSize {
    public static final int AUTO_SIZE = -2;
    public static final int FULL_SIZE = -1;
    private int height;
    private int width;
    public static final BannerSize FIXED_SIZE_320_50 = Builder.newBuilder().withWidth(320).withHeight(50).build();
    public static final BannerSize FIXED_HEIGHT_50 = Builder.newBuilder().withHeight(50).build();
    public static final BannerSize FIXED_HEIGHT_90 = Builder.newBuilder().withHeight(90).build();
    public static final BannerSize FLUID_SIZE = Builder.newBuilder().withHeight(-1).build();
    public static final BannerSize SMART_SIZE = Builder.newBuilder().build();

    private BannerSize(Builder builder) {
        this.width = builder.a;
        this.height = builder.b;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public static class Builder {
        private int a = -1;
        private int b = -2;

        private Builder() {
        }

        public static Builder newBuilder() {
            return new Builder();
        }

        public Builder withWidth(int i) {
            this.a = i;
            return this;
        }

        public Builder withHeight(int i) {
            this.b = i;
            return this;
        }

        public BannerSize build() {
            return new BannerSize(this);
        }
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        BannerSize bannerSize = (BannerSize) obj;
        return this.width == bannerSize.width && this.height == bannerSize.height;
    }

    public int hashCode() {
        return (this.width * 31) + this.height;
    }

    public String toString() {
        String strValueOf;
        String strValueOf2;
        if (this.width == -1) {
            strValueOf = "full_width ";
        } else if (this.width == -2) {
            strValueOf = "smart_width ";
        } else {
            strValueOf = String.valueOf(this.width);
        }
        if (this.height == -1) {
            strValueOf2 = " full_height";
        } else if (this.height == -2) {
            strValueOf2 = " smart_height";
        } else {
            strValueOf2 = String.valueOf(this.height);
        }
        return "(" + strValueOf + TMXConstants.TAG_OBJECT_ATTRIBUTE_X + strValueOf2 + ")";
    }
}
