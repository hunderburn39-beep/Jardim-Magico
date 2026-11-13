package org.anddev.andengine.opengl.font;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;

/* JADX INFO: loaded from: classes.dex */
class Size {
    private float mHeight;
    private float mWidth;

    public Size() {
        this(BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED);
    }

    public Size(float pWidth, float pHeight) {
        setWidth(pWidth);
        setHeight(pHeight);
    }

    public void setWidth(float width) {
        this.mWidth = width;
    }

    public float getWidth() {
        return this.mWidth;
    }

    public void setHeight(float height) {
        this.mHeight = height;
    }

    public float getHeight() {
        return this.mHeight;
    }

    public void set(int pWidth, int pHeight) {
        setWidth(pWidth);
        setHeight(pHeight);
    }
}
