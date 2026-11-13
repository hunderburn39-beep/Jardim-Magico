package org.anddev.andengine.entity.sprite.batch;

import javax.microedition.khronos.opengles.GL10;
import org.anddev.andengine.opengl.texture.ITexture;
import org.anddev.andengine.opengl.texture.region.buffer.SpriteBatchTextureRegionBuffer;
import org.anddev.andengine.opengl.vertex.SpriteBatchVertexBuffer;

/* JADX INFO: loaded from: classes.dex */
public abstract class DynamicSpriteBatch extends SpriteBatch {
    protected abstract boolean onUpdateSpriteBatch();

    public DynamicSpriteBatch(ITexture pTexture, int pCapacity) {
        super(pTexture, pCapacity);
    }

    public DynamicSpriteBatch(ITexture pTexture, int pCapacity, SpriteBatchVertexBuffer pSpriteBatchVertexBuffer, SpriteBatchTextureRegionBuffer pSpriteBatchTextureRegionBuffer) {
        super(pTexture, pCapacity, pSpriteBatchVertexBuffer, pSpriteBatchTextureRegionBuffer);
    }

    @Override // org.anddev.andengine.entity.sprite.batch.SpriteBatch
    protected void begin(GL10 pGL) {
        super.begin(pGL);
        if (onUpdateSpriteBatch()) {
            submit();
        }
    }
}
