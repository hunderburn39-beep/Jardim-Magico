package org.anddev.andengine.entity.sprite.batch;

import java.util.ArrayList;
import org.anddev.andengine.entity.IEntity;
import org.anddev.andengine.entity.sprite.BaseSprite;
import org.anddev.andengine.opengl.texture.ITexture;
import org.anddev.andengine.opengl.texture.region.buffer.SpriteBatchTextureRegionBuffer;
import org.anddev.andengine.opengl.vertex.SpriteBatchVertexBuffer;
import org.anddev.andengine.util.SmartList;

/* JADX INFO: loaded from: classes.dex */
public class SpriteGroup extends DynamicSpriteBatch {
    public SpriteGroup(ITexture pTexture, int pCapacity) {
        super(pTexture, pCapacity);
        setChildrenVisible(false);
    }

    public SpriteGroup(ITexture pTexture, int pCapacity, SpriteBatchVertexBuffer pSpriteBatchVertexBuffer, SpriteBatchTextureRegionBuffer pSpriteBatchTextureRegionBuffer) {
        super(pTexture, pCapacity, pSpriteBatchVertexBuffer, pSpriteBatchTextureRegionBuffer);
        setChildrenVisible(false);
    }

    @Override // org.anddev.andengine.entity.Entity, org.anddev.andengine.entity.IEntity
    @Deprecated
    public void attachChild(IEntity pEntity) throws IllegalArgumentException {
        if (pEntity instanceof BaseSprite) {
            attachChild((BaseSprite) pEntity);
            return;
        }
        throw new IllegalArgumentException("A SpriteGroup can only handle children of type BaseSprite or subclasses of BaseSprite, like Sprite, TiledSprite or AnimatedSprite.");
    }

    public void attachChild(BaseSprite pBaseSprite) {
        assertCapacity();
        assertTexture(pBaseSprite.getTextureRegion());
        super.attachChild((IEntity) pBaseSprite);
    }

    public void attachChildren(ArrayList<? extends BaseSprite> pBaseSprites) {
        int baseSpriteCount = pBaseSprites.size();
        for (int i = 0; i < baseSpriteCount; i++) {
            attachChild(pBaseSprites.get(i));
        }
    }

    @Override // org.anddev.andengine.entity.sprite.batch.DynamicSpriteBatch
    protected boolean onUpdateSpriteBatch() {
        SmartList<IEntity> children = this.mChildren;
        if (children == null) {
            return false;
        }
        int childCount = children.size();
        for (int i = 0; i < childCount; i++) {
            super.drawWithoutChecks((BaseSprite) children.get(i));
        }
        return true;
    }

    private void assertCapacity() {
        if (getChildCount() >= this.mCapacity) {
            throw new IllegalStateException("This SpriteGroup has already reached its capacity (" + this.mCapacity + ") !");
        }
    }
}
