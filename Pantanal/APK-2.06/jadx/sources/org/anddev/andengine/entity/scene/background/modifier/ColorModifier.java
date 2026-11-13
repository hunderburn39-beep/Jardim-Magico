package org.anddev.andengine.entity.scene.background.modifier;

import org.anddev.andengine.entity.scene.background.IBackground;
import org.anddev.andengine.util.modifier.BaseTripleValueSpanModifier;
import org.anddev.andengine.util.modifier.IModifier;
import org.anddev.andengine.util.modifier.ease.IEaseFunction;

/* JADX INFO: loaded from: classes.dex */
public class ColorModifier extends BaseTripleValueSpanModifier<IBackground> implements IBackgroundModifier {
    public ColorModifier(float pDuration, float pFromRed, float pToRed, float pFromGreen, float pToGreen, float pFromBlue, float pToBlue) {
        this(pDuration, pFromRed, pToRed, pFromGreen, pToGreen, pFromBlue, pToBlue, null, IEaseFunction.DEFAULT);
    }

    public ColorModifier(float pDuration, float pFromRed, float pToRed, float pFromGreen, float pToGreen, float pFromBlue, float pToBlue, IEaseFunction pEaseFunction) {
        this(pDuration, pFromRed, pToRed, pFromGreen, pToGreen, pFromBlue, pToBlue, null, pEaseFunction);
    }

    public ColorModifier(float pDuration, float pFromRed, float pToRed, float pFromGreen, float pToGreen, float pFromBlue, float pToBlue, IBackgroundModifier.IBackgroundModifierListener pBackgroundModifierListener) {
        super(pDuration, pFromRed, pToRed, pFromGreen, pToGreen, pFromBlue, pToBlue, pBackgroundModifierListener, IEaseFunction.DEFAULT);
    }

    public ColorModifier(float pDuration, float pFromRed, float pToRed, float pFromGreen, float pToGreen, float pFromBlue, float pToBlue, IBackgroundModifier.IBackgroundModifierListener pBackgroundModifierListener, IEaseFunction pEaseFunction) {
        super(pDuration, pFromRed, pToRed, pFromGreen, pToGreen, pFromBlue, pToBlue, pBackgroundModifierListener, pEaseFunction);
    }

    protected ColorModifier(ColorModifier pColorModifier) {
        super(pColorModifier);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // org.anddev.andengine.util.modifier.BaseModifier, org.anddev.andengine.util.modifier.IModifier, org.anddev.andengine.entity.modifier.IEntityModifier
    public IModifier<IBackground> deepCopy() {
        return new ColorModifier(this);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.anddev.andengine.util.modifier.BaseTripleValueSpanModifier
    public void onSetInitialValues(IBackground pBackground, float pRed, float pGreen, float pBlue) {
        pBackground.setColor(pRed, pGreen, pBlue);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.anddev.andengine.util.modifier.BaseTripleValueSpanModifier
    public void onSetValues(IBackground pBackground, float pPerctentageDone, float pRed, float pGreen, float pBlue) {
        pBackground.setColor(pRed, pGreen, pBlue);
    }
}
