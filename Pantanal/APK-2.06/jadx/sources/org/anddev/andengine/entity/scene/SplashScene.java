package org.anddev.andengine.entity.scene;

import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.entity.modifier.ScaleModifier;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.util.modifier.ease.IEaseFunction;

/* JADX INFO: loaded from: classes.dex */
public class SplashScene extends Scene {
    public SplashScene(Camera pCamera, TextureRegion pTextureRegion) {
        this(pCamera, pTextureRegion, -1.0f, 1.0f, 1.0f);
    }

    public SplashScene(Camera pCamera, TextureRegion pTextureRegion, float pDuration, float pScaleFrom, float pScaleTo) {
        Sprite loadingScreenSprite = new Sprite(pCamera.getMinX(), pCamera.getMinY(), pCamera.getWidth(), pCamera.getHeight(), pTextureRegion);
        if (pScaleFrom != 1.0f || pScaleTo != 1.0f) {
            loadingScreenSprite.setScale(pScaleFrom);
            loadingScreenSprite.registerEntityModifier(new ScaleModifier(pDuration, pScaleFrom, pScaleTo, IEaseFunction.DEFAULT));
        }
        attachChild(loadingScreenSprite);
    }
}
