package com.tapjoy.internal;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Region;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

/* JADX INFO: loaded from: classes.dex */
public class an extends ao {
    private int a;
    private final Matrix b;
    private final float[] c;

    public an(Context context) {
        super(context);
        this.a = 0;
        this.b = new Matrix();
        this.c = new float[2];
    }

    public int getRotationCount() {
        return this.a;
    }

    public void setRotationCount(int rotationCount) {
        this.a = rotationCount & 3;
    }

    @Override // com.tapjoy.internal.ao, android.view.View
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (this.a % 2 == 0) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        } else {
            super.onMeasure(heightMeasureSpec, widthMeasureSpec);
            setMeasuredDimension(getMeasuredHeight(), getMeasuredWidth());
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void dispatchDraw(Canvas canvas) {
        if (this.a == 0) {
            super.dispatchDraw(canvas);
            return;
        }
        canvas.save();
        int width = getWidth();
        int height = getHeight();
        canvas.clipRect(BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, width, height, Region.Op.REPLACE);
        try {
            ViewGroup viewGroup = (ViewGroup) getParent();
            try {
                ViewGroup viewGroup2 = (ViewGroup) viewGroup.getParent();
                if ((viewGroup2 instanceof ScrollView) || (viewGroup2 instanceof HorizontalScrollView)) {
                    viewGroup = viewGroup2;
                }
            } catch (Exception e) {
            }
            int left = getLeft() - viewGroup.getScrollX();
            int top = getTop() - viewGroup.getScrollY();
            canvas.clipRect(0 - left, 0 - top, viewGroup.getWidth() - left, viewGroup.getHeight() - top, Region.Op.INTERSECT);
        } catch (Exception e2) {
        }
        canvas.rotate(this.a * 90);
        switch (this.a) {
            case 1:
                canvas.translate(BitmapDescriptorFactory.HUE_RED, -width);
                break;
            case 2:
                canvas.translate(-width, -height);
                break;
            case 3:
                canvas.translate(-height, BitmapDescriptorFactory.HUE_RED);
                break;
            default:
                throw new IllegalStateException();
        }
        this.b.setRotate(this.a * (-90));
        switch (this.a) {
            case 1:
                this.b.postTranslate(BitmapDescriptorFactory.HUE_RED, width - 1);
                break;
            case 2:
                this.b.postTranslate(width - 1, height - 1);
                break;
            case 3:
                this.b.postTranslate(height - 1, BitmapDescriptorFactory.HUE_RED);
                break;
            default:
                throw new IllegalStateException();
        }
        super.dispatchDraw(canvas);
        canvas.restore();
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (this.a == 0) {
            return super.dispatchTouchEvent(event);
        }
        float[] fArr = this.c;
        fArr[0] = event.getX();
        fArr[1] = event.getY();
        this.b.mapPoints(fArr);
        event.setLocation(fArr[0], fArr[1]);
        return super.dispatchTouchEvent(event);
    }
}
