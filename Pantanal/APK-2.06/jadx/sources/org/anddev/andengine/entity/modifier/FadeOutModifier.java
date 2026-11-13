package org.anddev.andengine.entity.modifier;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import org.anddev.andengine.entity.IEntity;
import org.anddev.andengine.util.modifier.IModifier;
import org.anddev.andengine.util.modifier.ease.IEaseFunction;

/* JADX INFO: loaded from: classes.dex */
public class FadeOutModifier extends AlphaModifier {
    public FadeOutModifier(float pDuration) {
        super(pDuration, 1.0f, BitmapDescriptorFactory.HUE_RED, IEaseFunction.DEFAULT);
    }

    public FadeOutModifier(float pDuration, IEaseFunction pEaseFunction) {
        super(pDuration, 1.0f, BitmapDescriptorFactory.HUE_RED, pEaseFunction);
    }

    public FadeOutModifier(float pDuration, IEntityModifier.IEntityModifierListener pEntityModifierListener) {
        super(pDuration, 1.0f, BitmapDescriptorFactory.HUE_RED, pEntityModifierListener, IEaseFunction.DEFAULT);
    }

    public FadeOutModifier(float pDuration, IEntityModifier.IEntityModifierListener pEntityModifierListener, IEaseFunction pEaseFunction) {
        super(pDuration, 1.0f, BitmapDescriptorFactory.HUE_RED, pEntityModifierListener, pEaseFunction);
    }

    protected FadeOutModifier(FadeOutModifier pFadeOutModifier) {
        super(pFadeOutModifier);
    }

    @Override // org.anddev.andengine.entity.modifier.AlphaModifier, org.anddev.andengine.util.modifier.BaseModifier, org.anddev.andengine.util.modifier.IModifier, org.anddev.andengine.entity.modifier.IEntityModifier
    public IModifier<IEntity> deepCopy() {
        return new FadeOutModifier(this);
    }
}
