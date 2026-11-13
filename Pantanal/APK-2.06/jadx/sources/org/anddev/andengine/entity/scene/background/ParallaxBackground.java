package org.anddev.andengine.entity.scene.background;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.util.ArrayList;
import javax.microedition.khronos.opengles.GL10;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.entity.shape.Shape;

/* JADX INFO: loaded from: classes.dex */
public class ParallaxBackground extends ColorBackground {
    private final ArrayList<ParallaxEntity> mParallaxEntities;
    private int mParallaxEntityCount;
    protected float mParallaxValue;

    public ParallaxBackground(float pRed, float pGreen, float pBlue) {
        super(pRed, pGreen, pBlue);
        this.mParallaxEntities = new ArrayList<>();
    }

    public void setParallaxValue(float pParallaxValue) {
        this.mParallaxValue = pParallaxValue;
    }

    @Override // org.anddev.andengine.entity.scene.background.ColorBackground, org.anddev.andengine.opengl.IDrawable
    public void onDraw(GL10 pGL, Camera pCamera) {
        super.onDraw(pGL, pCamera);
        float parallaxValue = this.mParallaxValue;
        ArrayList<ParallaxEntity> parallaxEntities = this.mParallaxEntities;
        for (int i = 0; i < this.mParallaxEntityCount; i++) {
            parallaxEntities.get(i).onDraw(pGL, parallaxValue, pCamera);
        }
    }

    public void attachParallaxEntity(ParallaxEntity pParallaxEntity) {
        this.mParallaxEntities.add(pParallaxEntity);
        this.mParallaxEntityCount++;
    }

    public boolean detachParallaxEntity(ParallaxEntity pParallaxEntity) {
        this.mParallaxEntityCount--;
        boolean success = this.mParallaxEntities.remove(pParallaxEntity);
        if (!success) {
            this.mParallaxEntityCount++;
        }
        return success;
    }

    public static class ParallaxEntity {
        final float mParallaxFactor;
        final Shape mShape;

        public ParallaxEntity(float pParallaxFactor, Shape pShape) {
            this.mParallaxFactor = pParallaxFactor;
            this.mShape = pShape;
        }

        public void onDraw(GL10 pGL, float pParallaxValue, Camera pCamera) {
            pGL.glPushMatrix();
            float cameraWidth = pCamera.getWidth();
            float shapeWidthScaled = this.mShape.getWidthScaled();
            float baseOffset = (this.mParallaxFactor * pParallaxValue) % shapeWidthScaled;
            while (baseOffset > BitmapDescriptorFactory.HUE_RED) {
                baseOffset -= shapeWidthScaled;
            }
            pGL.glTranslatef(baseOffset, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED);
            float currentMaxX = baseOffset;
            do {
                this.mShape.onDraw(pGL, pCamera);
                pGL.glTranslatef(shapeWidthScaled, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED);
                currentMaxX += shapeWidthScaled;
            } while (currentMaxX < cameraWidth);
            pGL.glPopMatrix();
        }
    }
}
