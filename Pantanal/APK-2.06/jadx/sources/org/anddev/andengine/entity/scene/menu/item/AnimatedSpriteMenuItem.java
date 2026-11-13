package org.anddev.andengine.entity.scene.menu.item;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

/* JADX INFO: loaded from: classes.dex */
public class AnimatedSpriteMenuItem extends AnimatedSprite implements IMenuItem {
    private final int mID;

    public AnimatedSpriteMenuItem(int pID, TiledTextureRegion pTiledTextureRegion) {
        super(BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, pTiledTextureRegion);
        this.mID = pID;
    }

    @Override // org.anddev.andengine.entity.scene.menu.item.IMenuItem
    public int getID() {
        return this.mID;
    }

    @Override // org.anddev.andengine.entity.scene.menu.item.IMenuItem
    public void onSelected() {
    }

    @Override // org.anddev.andengine.entity.scene.menu.item.IMenuItem
    public void onUnselected() {
    }
}
