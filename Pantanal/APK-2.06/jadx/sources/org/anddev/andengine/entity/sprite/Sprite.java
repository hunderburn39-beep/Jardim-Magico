package org.anddev.andengine.entity.sprite;

import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.opengl.vertex.RectangleVertexBuffer;

/* JADX INFO: loaded from: classes.dex */
public class Sprite extends BaseSprite {
    public Sprite(float pX, float pY, TextureRegion pTextureRegion) {
        super(pX, pY, pTextureRegion.getWidth(), pTextureRegion.getHeight(), pTextureRegion);
    }

    public Sprite(float pX, float pY, float pWidth, float pHeight, TextureRegion pTextureRegion) {
        super(pX, pY, pWidth, pHeight, pTextureRegion);
    }

    public Sprite(float pX, float pY, TextureRegion pTextureRegion, RectangleVertexBuffer pRectangleVertexBuffer) {
        super(pX, pY, pTextureRegion.getWidth(), pTextureRegion.getHeight(), pTextureRegion, pRectangleVertexBuffer);
    }

    public Sprite(float pX, float pY, float pWidth, float pHeight, TextureRegion pTextureRegion, RectangleVertexBuffer pRectangleVertexBuffer) {
        super(pX, pY, pWidth, pHeight, pTextureRegion, pRectangleVertexBuffer);
    }

    @Override // org.anddev.andengine.entity.sprite.BaseSprite
    public TextureRegion getTextureRegion() {
        return (TextureRegion) this.mTextureRegion;
    }
}
