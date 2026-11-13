package org.anddev.andengine.entity.scene.menu.item;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.opengl.texture.region.TextureRegion;

/* JADX INFO: loaded from: classes.dex */
public class SpriteMenuItem extends Sprite implements IMenuItem {
    private final int mID;

    public SpriteMenuItem(int pID, TextureRegion pTextureRegion) {
        super(BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, pTextureRegion);
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
