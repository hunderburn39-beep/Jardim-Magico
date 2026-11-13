package com.chartboost.sdk.impl;

import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import com.chartboost.sdk.Libraries.CBLogging;
import com.chartboost.sdk.Libraries.CBUtility;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.util.concurrent.atomic.AtomicReference;

/* JADX INFO: loaded from: classes.dex */
public class bc {
    private final AtomicReference<com.chartboost.sdk.Model.c> a;
    private final com.chartboost.sdk.e b;

    public interface a {
        void a(com.chartboost.sdk.Model.a aVar);
    }

    public enum b {
        CBAnimationTypePerspectiveRotate,
        CBAnimationTypeBounce,
        CBAnimationTypePerspectiveZoom,
        CBAnimationTypeSlideFromTop,
        CBAnimationTypeSlideFromBottom,
        CBAnimationTypeFade,
        CBAnimationTypeNone,
        CBAnimationTypeSlideFromLeft,
        CBAnimationTypeSlideFromRight;

        public static b a(int i) {
            if (i != 0 && i > 0 && i <= values().length) {
                return values()[i - 1];
            }
            return null;
        }
    }

    public bc(AtomicReference<com.chartboost.sdk.Model.c> atomicReference, com.chartboost.sdk.e eVar) {
        this.a = atomicReference;
        this.b = eVar;
    }

    public void a(b bVar, com.chartboost.sdk.Model.a aVar, a aVar2) {
        b(bVar, aVar, aVar2, true);
    }

    public void b(b bVar, com.chartboost.sdk.Model.a aVar, a aVar2) {
        a(bVar, aVar, aVar2, false);
    }

    private void b(final b bVar, final com.chartboost.sdk.Model.a aVar, final a aVar2, final boolean z) {
        if (bVar == b.CBAnimationTypeNone) {
            if (aVar2 != null) {
                aVar2.a(aVar);
            }
        } else {
            if (aVar == null || aVar.p == null) {
                CBLogging.a("AnimationManager", "Transition of impression canceled due to lack of container");
                return;
            }
            final View viewF = aVar.p.f();
            if (viewF == null) {
                this.b.d(aVar);
                CBLogging.a("AnimationManager", "Transition of impression canceled due to lack of view");
            } else {
                ViewTreeObserver viewTreeObserver = viewF.getViewTreeObserver();
                if (viewTreeObserver.isAlive()) {
                    viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.chartboost.sdk.impl.bc.1
                        @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
                        public void onGlobalLayout() {
                            viewF.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                            bc.this.a(bVar, aVar, aVar2, z);
                        }
                    });
                }
            }
        }
    }

    void a(b bVar, final com.chartboost.sdk.Model.a aVar, final a aVar2, boolean z) {
        long j;
        AnimationSet animationSet;
        bh bhVar;
        ScaleAnimation scaleAnimation;
        TranslateAnimation translateAnimation;
        bh bhVar2;
        ScaleAnimation scaleAnimation2;
        TranslateAnimation translateAnimation2;
        AnimationSet animationSet2 = new AnimationSet(true);
        animationSet2.addAnimation(new AlphaAnimation(1.0f, 1.0f));
        if (aVar == null || aVar.p == null) {
            CBLogging.a("AnimationManager", "Transition of impression canceled due to lack of container");
            if (aVar2 != null) {
                aVar2.a(aVar);
                return;
            }
            return;
        }
        View viewF = aVar.p.f();
        if (viewF == null) {
            if (aVar2 != null) {
                aVar2.a(aVar);
            }
            CBLogging.a("AnimationManager", "Transition of impression canceled due to lack of view");
            return;
        }
        View view = (aVar.g == com.chartboost.sdk.Model.a.c.INTERSTITIAL_REWARD_VIDEO || aVar.g == com.chartboost.sdk.Model.a.c.INTERSTITIAL_VIDEO) ? aVar.p : viewF;
        float width = view.getWidth();
        float height = view.getHeight();
        float f = (1.0f - 0.4f) / 2.0f;
        if (aVar.b != com.chartboost.sdk.Model.a.b.WEB) {
            j = 500;
        } else {
            j = 500;
        }
        switch (bVar) {
            case CBAnimationTypeFade:
                AlphaAnimation alphaAnimation = z ? new AlphaAnimation(BitmapDescriptorFactory.HUE_RED, 1.0f) : new AlphaAnimation(1.0f, BitmapDescriptorFactory.HUE_RED);
                alphaAnimation.setDuration(j);
                alphaAnimation.setFillAfter(true);
                AnimationSet animationSet3 = new AnimationSet(true);
                animationSet3.addAnimation(alphaAnimation);
                animationSet = animationSet3;
                break;
            case CBAnimationTypePerspectiveZoom:
                if (z) {
                    bhVar2 = new bh(-60.0f, BitmapDescriptorFactory.HUE_RED, width / 2.0f, height / 2.0f, false);
                } else {
                    bhVar2 = new bh(BitmapDescriptorFactory.HUE_RED, 60.0f, width / 2.0f, height / 2.0f, false);
                }
                bhVar2.setDuration(j);
                bhVar2.setFillAfter(true);
                animationSet2.addAnimation(bhVar2);
                if (z) {
                    scaleAnimation2 = new ScaleAnimation(0.4f, 1.0f, 0.4f, 1.0f);
                } else {
                    scaleAnimation2 = new ScaleAnimation(1.0f, 0.4f, 1.0f, 0.4f);
                }
                scaleAnimation2.setDuration(j);
                scaleAnimation2.setFillAfter(true);
                animationSet2.addAnimation(scaleAnimation2);
                if (z) {
                    translateAnimation2 = new TranslateAnimation(width * f, BitmapDescriptorFactory.HUE_RED, (-height) * 0.4f, BitmapDescriptorFactory.HUE_RED);
                } else {
                    translateAnimation2 = new TranslateAnimation(BitmapDescriptorFactory.HUE_RED, width * f, BitmapDescriptorFactory.HUE_RED, height);
                }
                translateAnimation2.setDuration(j);
                translateAnimation2.setFillAfter(true);
                animationSet2.addAnimation(translateAnimation2);
                animationSet = animationSet2;
                break;
            case CBAnimationTypePerspectiveRotate:
                if (z) {
                    bhVar = new bh(-60.0f, BitmapDescriptorFactory.HUE_RED, width / 2.0f, height / 2.0f, true);
                } else {
                    bhVar = new bh(BitmapDescriptorFactory.HUE_RED, 60.0f, width / 2.0f, height / 2.0f, true);
                }
                bhVar.setDuration(j);
                bhVar.setFillAfter(true);
                animationSet2.addAnimation(bhVar);
                if (z) {
                    scaleAnimation = new ScaleAnimation(0.4f, 1.0f, 0.4f, 1.0f);
                } else {
                    scaleAnimation = new ScaleAnimation(1.0f, 0.4f, 1.0f, 0.4f);
                }
                scaleAnimation.setDuration(j);
                scaleAnimation.setFillAfter(true);
                animationSet2.addAnimation(scaleAnimation);
                if (z) {
                    translateAnimation = new TranslateAnimation((-width) * 0.4f, BitmapDescriptorFactory.HUE_RED, height * f, BitmapDescriptorFactory.HUE_RED);
                } else {
                    translateAnimation = new TranslateAnimation(BitmapDescriptorFactory.HUE_RED, width, BitmapDescriptorFactory.HUE_RED, height * f);
                }
                translateAnimation.setDuration(j);
                translateAnimation.setFillAfter(true);
                animationSet2.addAnimation(translateAnimation);
                animationSet = animationSet2;
                break;
            case CBAnimationTypeSlideFromBottom:
                TranslateAnimation translateAnimation3 = new TranslateAnimation(BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, z ? height : 0.0f, z ? BitmapDescriptorFactory.HUE_RED : height);
                translateAnimation3.setDuration(j);
                translateAnimation3.setFillAfter(true);
                animationSet2.addAnimation(translateAnimation3);
                animationSet = animationSet2;
                break;
            case CBAnimationTypeSlideFromTop:
                TranslateAnimation translateAnimation4 = new TranslateAnimation(BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, z ? -height : 0.0f, z ? BitmapDescriptorFactory.HUE_RED : -height);
                translateAnimation4.setDuration(j);
                translateAnimation4.setFillAfter(true);
                animationSet2.addAnimation(translateAnimation4);
                animationSet = animationSet2;
                break;
            case CBAnimationTypeSlideFromLeft:
                TranslateAnimation translateAnimation5 = new TranslateAnimation(z ? width : 0.0f, z ? BitmapDescriptorFactory.HUE_RED : width, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED);
                translateAnimation5.setDuration(j);
                translateAnimation5.setFillAfter(true);
                animationSet2.addAnimation(translateAnimation5);
                animationSet = animationSet2;
                break;
            case CBAnimationTypeSlideFromRight:
                TranslateAnimation translateAnimation6 = new TranslateAnimation(z ? -width : 0.0f, z ? BitmapDescriptorFactory.HUE_RED : -width, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED);
                translateAnimation6.setDuration(j);
                translateAnimation6.setFillAfter(true);
                animationSet2.addAnimation(translateAnimation6);
                animationSet = animationSet2;
                break;
            case CBAnimationTypeBounce:
                if (z) {
                    ScaleAnimation scaleAnimation3 = new ScaleAnimation(0.6f, 1.1f, 0.6f, 1.1f, 1, 0.5f, 1, 0.5f);
                    scaleAnimation3.setDuration(Math.round(j * 0.6f));
                    scaleAnimation3.setStartOffset(0L);
                    scaleAnimation3.setFillAfter(true);
                    animationSet2.addAnimation(scaleAnimation3);
                    ScaleAnimation scaleAnimation4 = new ScaleAnimation(1.0f, 0.81818175f, 1.0f, 0.81818175f, 1, 0.5f, 1, 0.5f);
                    scaleAnimation4.setDuration(Math.round(j * 0.19999999f));
                    scaleAnimation4.setStartOffset(Math.round(j * 0.6f));
                    scaleAnimation4.setFillAfter(true);
                    animationSet2.addAnimation(scaleAnimation4);
                    ScaleAnimation scaleAnimation5 = new ScaleAnimation(1.0f, 1.1111112f, 1.0f, 1.1111112f, 1, 0.5f, 1, 0.5f);
                    scaleAnimation5.setDuration(Math.round(j * 0.099999964f));
                    scaleAnimation5.setStartOffset(Math.round(j * 0.8f));
                    scaleAnimation5.setFillAfter(true);
                    animationSet2.addAnimation(scaleAnimation5);
                    animationSet = animationSet2;
                } else {
                    ScaleAnimation scaleAnimation6 = new ScaleAnimation(1.0f, BitmapDescriptorFactory.HUE_RED, 1.0f, BitmapDescriptorFactory.HUE_RED, 1, 0.5f, 1, 0.5f);
                    scaleAnimation6.setDuration(j);
                    scaleAnimation6.setStartOffset(0L);
                    scaleAnimation6.setFillAfter(true);
                    animationSet2.addAnimation(scaleAnimation6);
                    animationSet = animationSet2;
                }
                break;
            default:
                animationSet = animationSet2;
                break;
        }
        if (bVar == b.CBAnimationTypeNone) {
            if (aVar2 != null) {
                aVar2.a(aVar);
            }
        } else {
            if (aVar2 != null) {
                CBUtility.c().postDelayed(new Runnable() { // from class: com.chartboost.sdk.impl.bc.2
                    @Override // java.lang.Runnable
                    public void run() {
                        aVar2.a(aVar);
                    }
                }, j);
            }
            view.startAnimation(animationSet);
        }
    }

    public void a(boolean z, View view) {
        if (this.a.get().u) {
        }
        a(z, view, 500L);
    }

    public void a(boolean z, View view, long j) {
        view.clearAnimation();
        if (z) {
            view.setVisibility(0);
        }
        AlphaAnimation alphaAnimation = new AlphaAnimation(z ? 0.0f : 1.0f, z ? 1.0f : 0.0f);
        alphaAnimation.setDuration(j);
        alphaAnimation.setFillBefore(true);
        view.startAnimation(alphaAnimation);
    }
}
