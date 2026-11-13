package org.anddev.andengine.entity.modifier;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import org.anddev.andengine.entity.IEntity;
import org.anddev.andengine.util.modifier.IModifier;
import org.anddev.andengine.util.modifier.ease.IEaseFunction;

/* JADX INFO: loaded from: classes.dex */
public class FadeInModifier extends AlphaModifier {
    public FadeInModifier(float pDuration) {
        super(pDuration, BitmapDescriptorFactory.HUE_RED, 1.0f, IEaseFunction.DEFAULT);
    }

    public FadeInModifier(float pDuration, IEaseFunction pEaseFunction) {
        super(pDuration, BitmapDescriptorFactory.HUE_RED, 1.0f, pEaseFunction);
    }

    public FadeInModifier(float pDuration, IEntityModifier.IEntityModifierListener pEntityModifierListener) {
        super(pDuration, BitmapDescriptorFactory.HUE_RED, 1.0f, pEntityModifierListener, IEaseFunction.DEFAULT);
    }

    public FadeInModifier(float pDuration, IEntityModifier.IEntityModifierListener pEntityModifierListener, IEaseFunction pEaseFunction) {
        super(pDuration, BitmapDescriptorFactory.HUE_RED, 1.0f, pEntityModifierListener, pEaseFunction);
    }

    protected FadeInModifier(FadeInModifier pFadeInModifier) {
        super(pFadeInModifier);
    }

    @Override // org.anddev.andengine.entity.modifier.AlphaModifier, org.anddev.andengine.util.modifier.BaseModifier, org.anddev.andengine.util.modifier.IModifier, org.anddev.andengine.entity.modifier.IEntityModifier
    public IModifier<IEntity> deepCopy() {
        return new FadeInModifier(this);
    }
}
