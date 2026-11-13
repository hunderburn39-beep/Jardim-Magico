package com.moat.analytics.mobile.tjy;

import android.view.View;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class bd extends f implements ReactiveVideoTracker {
    private Integer j;

    public bd(String str, a aVar, ap apVar) {
        super(str, aVar, apVar);
    }

    @Override // com.moat.analytics.mobile.tjy.f
    protected Map a() {
        Integer numValueOf;
        Integer numValueOf2;
        HashMap map = new HashMap();
        View view = (View) this.g.get();
        if (view != null) {
            numValueOf2 = Integer.valueOf(view.getWidth());
            numValueOf = Integer.valueOf(view.getHeight());
        } else {
            numValueOf = 0;
            numValueOf2 = 0;
        }
        map.put("duration", this.j);
        map.put("width", numValueOf2);
        map.put("height", numValueOf);
        return map;
    }

    @Override // com.moat.analytics.mobile.tjy.f
    protected JSONObject a(MoatAdEvent moatAdEvent) {
        if (moatAdEvent.eventType == MoatAdEventType.AD_EVT_COMPLETE && !a(moatAdEvent.adPlayhead, this.j)) {
            moatAdEvent.eventType = MoatAdEventType.AD_EVT_STOPPED;
        }
        return super.a(moatAdEvent);
    }

    @Override // com.moat.analytics.mobile.tjy.f
    public /* bridge */ /* synthetic */ boolean a(Map map, Object obj, View view) {
        return super.a(map, obj, view);
    }

    @Override // com.moat.analytics.mobile.tjy.f, com.moat.analytics.mobile.tjy.NativeVideoTracker
    public /* bridge */ /* synthetic */ void changeTargetView(View view) {
        super.changeTargetView(view);
    }

    @Override // com.moat.analytics.mobile.tjy.f, com.moat.analytics.mobile.tjy.ReactiveVideoTracker
    public /* bridge */ /* synthetic */ void dispatchEvent(MoatAdEvent moatAdEvent) {
        super.dispatchEvent(moatAdEvent);
    }

    @Override // com.moat.analytics.mobile.tjy.f
    public /* bridge */ /* synthetic */ void dispatchEvent(Map map) {
        super.dispatchEvent(map);
    }

    @Override // com.moat.analytics.mobile.tjy.f, com.moat.analytics.mobile.tjy.ReactiveVideoTracker
    public /* bridge */ /* synthetic */ void setDebug(boolean z) {
        super.setDebug(z);
    }

    @Override // com.moat.analytics.mobile.tjy.ReactiveVideoTracker
    public boolean trackVideoAd(Map map, Integer num, View view) {
        if (num.intValue() < 1000) {
            a(String.format("Invalid duration = %d. Please make sure duration is in milliseconds.", num));
            return false;
        }
        this.j = num;
        return super.a(map, new Object(), view);
    }
}
