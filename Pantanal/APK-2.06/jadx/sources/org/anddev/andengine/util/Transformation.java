package org.anddev.andengine.util;

import android.util.FloatMath;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

/* JADX INFO: loaded from: classes.dex */
public class Transformation {
    private float a = 1.0f;
    private float b = BitmapDescriptorFactory.HUE_RED;
    private float c = BitmapDescriptorFactory.HUE_RED;
    private float d = 1.0f;
    private float tx = BitmapDescriptorFactory.HUE_RED;
    private float ty = BitmapDescriptorFactory.HUE_RED;

    public String toString() {
        return "Transformation{[" + this.a + ", " + this.c + ", " + this.tx + "][" + this.b + ", " + this.d + ", " + this.ty + "][0.0, 0.0, 1.0]}";
    }

    public void reset() {
        setToIdentity();
    }

    public void setToIdentity() {
        this.a = 1.0f;
        this.d = 1.0f;
        this.b = BitmapDescriptorFactory.HUE_RED;
        this.c = BitmapDescriptorFactory.HUE_RED;
        this.tx = BitmapDescriptorFactory.HUE_RED;
        this.ty = BitmapDescriptorFactory.HUE_RED;
    }

    public void setTo(Transformation pTransformation) {
        this.a = pTransformation.a;
        this.d = pTransformation.d;
        this.b = pTransformation.b;
        this.c = pTransformation.c;
        this.tx = pTransformation.tx;
        this.ty = pTransformation.ty;
    }

    public void preTranslate(float pX, float pY) {
        this.tx += (this.a * pX) + (this.c * pY);
        this.ty += (this.b * pX) + (this.d * pY);
    }

    public void postTranslate(float pX, float pY) {
        this.tx += pX;
        this.ty += pY;
    }

    public Transformation setToTranslate(float pX, float pY) {
        this.a = 1.0f;
        this.b = BitmapDescriptorFactory.HUE_RED;
        this.c = BitmapDescriptorFactory.HUE_RED;
        this.d = 1.0f;
        this.tx = pX;
        this.ty = pY;
        return this;
    }

    public void preScale(float pScaleX, float pScaleY) {
        this.a *= pScaleX;
        this.b *= pScaleX;
        this.c *= pScaleY;
        this.d *= pScaleY;
    }

    public void postScale(float pScaleX, float pScaleY) {
        this.a *= pScaleX;
        this.b *= pScaleY;
        this.c *= pScaleX;
        this.d *= pScaleY;
        this.tx *= pScaleX;
        this.ty *= pScaleY;
    }

    public Transformation setToScale(float pScaleX, float pScaleY) {
        this.a = pScaleX;
        this.b = BitmapDescriptorFactory.HUE_RED;
        this.c = BitmapDescriptorFactory.HUE_RED;
        this.d = pScaleY;
        this.tx = BitmapDescriptorFactory.HUE_RED;
        this.ty = BitmapDescriptorFactory.HUE_RED;
        return this;
    }

    public void preRotate(float pAngle) {
        float angleRad = MathUtils.degToRad(pAngle);
        float sin = FloatMath.sin(angleRad);
        float cos = FloatMath.cos(angleRad);
        float a = this.a;
        float b = this.b;
        float c = this.c;
        float d = this.d;
        this.a = (cos * a) + (sin * c);
        this.b = (cos * b) + (sin * d);
        this.c = (cos * c) - (sin * a);
        this.d = (cos * d) - (sin * b);
    }

    public void postRotate(float pAngle) {
        float angleRad = MathUtils.degToRad(pAngle);
        float sin = FloatMath.sin(angleRad);
        float cos = FloatMath.cos(angleRad);
        float a = this.a;
        float b = this.b;
        float c = this.c;
        float d = this.d;
        float tx = this.tx;
        float ty = this.ty;
        this.a = (a * cos) - (b * sin);
        this.b = (a * sin) + (b * cos);
        this.c = (c * cos) - (d * sin);
        this.d = (c * sin) + (d * cos);
        this.tx = (tx * cos) - (ty * sin);
        this.ty = (tx * sin) + (ty * cos);
    }

    public Transformation setToRotate(float pAngle) {
        float angleRad = MathUtils.degToRad(pAngle);
        float sin = FloatMath.sin(angleRad);
        float cos = FloatMath.cos(angleRad);
        this.a = cos;
        this.b = sin;
        this.c = -sin;
        this.d = cos;
        this.tx = BitmapDescriptorFactory.HUE_RED;
        this.ty = BitmapDescriptorFactory.HUE_RED;
        return this;
    }

    public void postConcat(Transformation pTransformation) {
        postConcat(pTransformation.a, pTransformation.b, pTransformation.c, pTransformation.d, pTransformation.tx, pTransformation.ty);
    }

    private void postConcat(float pA, float pB, float pC, float pD, float pTX, float pTY) {
        float a = this.a;
        float b = this.b;
        float c = this.c;
        float d = this.d;
        float tx = this.tx;
        float ty = this.ty;
        this.a = (a * pA) + (b * pC);
        this.b = (a * pB) + (b * pD);
        this.c = (c * pA) + (d * pC);
        this.d = (c * pB) + (d * pD);
        this.tx = (tx * pA) + (ty * pC) + pTX;
        this.ty = (tx * pB) + (ty * pD) + pTY;
    }

    public void preConcat(Transformation pTransformation) {
        preConcat(pTransformation.a, pTransformation.b, pTransformation.c, pTransformation.d, pTransformation.tx, pTransformation.ty);
    }

    private void preConcat(float pA, float pB, float pC, float pD, float pTX, float pTY) {
        float a = this.a;
        float b = this.b;
        float c = this.c;
        float d = this.d;
        float tx = this.tx;
        float ty = this.ty;
        this.a = (pA * a) + (pB * c);
        this.b = (pA * b) + (pB * d);
        this.c = (pC * a) + (pD * c);
        this.d = (pC * b) + (pD * d);
        this.tx = (pTX * a) + (pTY * c) + tx;
        this.ty = (pTX * b) + (pTY * d) + ty;
    }

    public void transform(float[] pVertices) {
        int count = pVertices.length >> 1;
        int j = 0;
        int i = 0;
        while (true) {
            count--;
            if (count >= 0) {
                int i2 = i + 1;
                float x = pVertices[i];
                i = i2 + 1;
                float y = pVertices[i2];
                int j2 = j + 1;
                pVertices[j] = (this.a * x) + (this.c * y) + this.tx;
                j = j2 + 1;
                pVertices[j2] = (this.b * x) + (this.d * y) + this.ty;
            } else {
                return;
            }
        }
    }
}
