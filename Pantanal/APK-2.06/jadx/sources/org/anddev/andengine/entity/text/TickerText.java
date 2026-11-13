package org.anddev.andengine.entity.text;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import javax.microedition.khronos.opengles.GL10;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.opengl.font.Font;
import org.anddev.andengine.util.HorizontalAlign;

/* JADX INFO: loaded from: classes.dex */
public class TickerText extends Text {
    private float mCharactersPerSecond;
    private int mCharactersVisible;
    private float mDuration;
    private boolean mReverse;
    private float mSecondsElapsed;

    public TickerText(float pX, float pY, Font pFont, String pText, HorizontalAlign pHorizontalAlign, float pCharactersPerSecond) {
        super(pX, pY, pFont, pText, pHorizontalAlign);
        this.mCharactersVisible = 0;
        this.mSecondsElapsed = BitmapDescriptorFactory.HUE_RED;
        this.mReverse = false;
        setCharactersPerSecond(pCharactersPerSecond);
    }

    public boolean isReverse() {
        return this.mReverse;
    }

    public void setReverse(boolean pReverse) {
        this.mReverse = pReverse;
    }

    public float getCharactersPerSecond() {
        return this.mCharactersPerSecond;
    }

    public void setCharactersPerSecond(float pCharactersPerSecond) {
        this.mCharactersPerSecond = pCharactersPerSecond;
        this.mDuration = this.mCharactersMaximum * this.mCharactersPerSecond;
    }

    public int getCharactersVisible() {
        return this.mCharactersVisible;
    }

    @Override // org.anddev.andengine.entity.Entity
    protected void onManagedUpdate(float pSecondsElapsed) {
        super.onManagedUpdate(pSecondsElapsed);
        if (this.mReverse) {
            if (this.mCharactersVisible < this.mCharactersMaximum) {
                this.mSecondsElapsed = Math.max(BitmapDescriptorFactory.HUE_RED, this.mSecondsElapsed - pSecondsElapsed);
                this.mCharactersVisible = (int) (this.mSecondsElapsed * this.mCharactersPerSecond);
                return;
            }
            return;
        }
        if (this.mCharactersVisible < this.mCharactersMaximum) {
            this.mSecondsElapsed = Math.min(this.mDuration, this.mSecondsElapsed + pSecondsElapsed);
            this.mCharactersVisible = (int) (this.mSecondsElapsed * this.mCharactersPerSecond);
        }
    }

    @Override // org.anddev.andengine.entity.text.Text, org.anddev.andengine.entity.shape.RectangularShape, org.anddev.andengine.entity.shape.Shape
    protected void drawVertices(GL10 pGL, Camera pCamera) {
        pGL.glDrawArrays(4, 0, this.mCharactersVisible * 6);
    }

    @Override // org.anddev.andengine.entity.shape.RectangularShape, org.anddev.andengine.entity.shape.Shape, org.anddev.andengine.entity.Entity, org.anddev.andengine.engine.handler.IUpdateHandler
    public void reset() {
        super.reset();
        this.mCharactersVisible = 0;
        this.mSecondsElapsed = BitmapDescriptorFactory.HUE_RED;
        this.mReverse = false;
    }
}
