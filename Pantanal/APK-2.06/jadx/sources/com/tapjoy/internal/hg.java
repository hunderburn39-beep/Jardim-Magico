package com.tapjoy.internal;

import android.content.Context;
import android.view.ViewGroup;

/* JADX INFO: loaded from: classes.dex */
public final class hg extends an {
    private final gq a;
    private final hh b;
    private af c;

    public hg(Context context, gq gqVar, hh hhVar) {
        super(context);
        this.a = gqVar;
        this.b = hhVar;
        addView(hhVar, new ViewGroup.LayoutParams(-1, -1));
        this.c = null;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.tapjoy.internal.an, com.tapjoy.internal.ao, android.view.View
    public final void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        af afVar;
        af afVarA = af.a(getContext());
        if (this.a.a()) {
            if (this.a.b()) {
                if (afVarA.a()) {
                    afVar = af.PORTRAIT;
                } else if (!afVarA.b() && af.b(getContext()).a()) {
                    afVar = af.PORTRAIT;
                } else {
                    afVar = af.LANDSCAPE;
                }
                setRotationCount(0);
            } else {
                afVar = af.PORTRAIT;
                if (afVarA.b()) {
                    if (afVarA.c() == 3) {
                        setRotationCount(1);
                    } else {
                        setRotationCount(3);
                    }
                } else {
                    setRotationCount(0);
                }
            }
        } else {
            afVar = af.LANDSCAPE;
            if (afVarA.a()) {
                if (afVarA.c() == 3) {
                    setRotationCount(1);
                } else {
                    setRotationCount(1);
                }
            } else {
                setRotationCount(0);
            }
        }
        if (this.c != afVar) {
            this.c = afVar;
            this.b.setLandscape(this.c.b());
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
