package com.moat.analytics.mobile.tjy;

import android.support.v4.widget.ExploreByTouchHelper;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
abstract class i extends f {
    protected k j;
    protected int k;
    protected double l;
    protected int m;
    protected int n;
    private int o;

    public i(String str, a aVar, ap apVar) {
        super(str, aVar, apVar);
        this.m = ExploreByTouchHelper.INVALID_ID;
        this.n = ExploreByTouchHelper.INVALID_ID;
        this.k = ExploreByTouchHelper.INVALID_ID;
        this.l = Double.NaN;
        this.o = 0;
        this.j = k.UNINITIALIZED;
    }

    @Override // com.moat.analytics.mobile.tjy.f
    protected JSONObject a(MoatAdEvent moatAdEvent) {
        Integer numValueOf;
        if (moatAdEvent.adPlayhead.equals(MoatAdEvent.TIME_UNAVAILABLE)) {
            try {
                numValueOf = f();
            } catch (Exception e) {
                numValueOf = Integer.valueOf(this.k);
            }
            moatAdEvent.adPlayhead = numValueOf;
        } else {
            numValueOf = moatAdEvent.adPlayhead;
        }
        if (moatAdEvent.adPlayhead.intValue() < 0) {
            numValueOf = Integer.valueOf(this.k);
            moatAdEvent.adPlayhead = numValueOf;
        }
        if (moatAdEvent.eventType == MoatAdEventType.AD_EVT_COMPLETE) {
            if (numValueOf.intValue() == Integer.MIN_VALUE || this.n == Integer.MIN_VALUE || !a(numValueOf, Integer.valueOf(this.n))) {
                this.j = k.STOPPED;
                moatAdEvent.eventType = MoatAdEventType.AD_EVT_STOPPED;
            } else {
                this.j = k.COMPLETED;
            }
        }
        return super.a(moatAdEvent);
    }

    @Override // com.moat.analytics.mobile.tjy.f
    protected void b() {
        super.b();
        this.d.postDelayed(new j(this), 200L);
    }

    protected abstract Integer f();

    protected abstract boolean g();

    protected abstract Integer h();

    /* JADX WARN: Code duplicated, block: B:59:0x00e9  */
    protected boolean i() {
        MoatAdEventType moatAdEventType;
        if (this.f.get() == null || e()) {
            return false;
        }
        try {
            int iIntValue = f().intValue();
            if (this.k >= 0 && iIntValue < 0) {
                return false;
            }
            this.k = iIntValue;
            if (iIntValue == 0) {
                return true;
            }
            int iIntValue2 = h().intValue();
            boolean zG = g();
            double d = ((double) iIntValue2) / 4.0d;
            double d2 = d();
            MoatAdEventType moatAdEventType2 = null;
            if (iIntValue > this.m) {
                this.m = iIntValue;
            }
            if (this.n == Integer.MIN_VALUE) {
                this.n = iIntValue2;
            }
            if (zG) {
                if (this.j == k.UNINITIALIZED) {
                    moatAdEventType2 = MoatAdEventType.AD_EVT_START;
                    this.j = k.PLAYING;
                } else if (this.j == k.PAUSED) {
                    moatAdEventType2 = MoatAdEventType.AD_EVT_PLAYING;
                    this.j = k.PLAYING;
                } else {
                    int iFloor = ((int) Math.floor(((double) iIntValue) / d)) - 1;
                    if (iFloor < 0 || iFloor >= 3) {
                        moatAdEventType = null;
                    } else {
                        moatAdEventType = b[iFloor];
                        if (this.c.containsKey(moatAdEventType)) {
                            moatAdEventType = null;
                        } else {
                            this.c.put(moatAdEventType, 1);
                        }
                    }
                    moatAdEventType2 = moatAdEventType;
                }
            } else if (this.j != k.PAUSED) {
                moatAdEventType2 = MoatAdEventType.AD_EVT_PAUSED;
                this.j = k.PAUSED;
            }
            boolean z = moatAdEventType2 != null;
            if (!z && !Double.isNaN(this.l) && Math.abs(this.l - d2) > 0.05d) {
                moatAdEventType2 = MoatAdEventType.AD_EVT_VOLUME_CHANGE;
                z = true;
            }
            if (z) {
                dispatchEvent(new MoatAdEvent(moatAdEventType2, Integer.valueOf(iIntValue), Double.valueOf(d2)));
            }
            this.l = d2;
            this.o = 0;
            return true;
        } catch (Exception e) {
            int i = this.o;
            this.o = i + 1;
            return i < 5;
        }
    }
}
