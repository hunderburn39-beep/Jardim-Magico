package com.google.android.gms.internal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.anddev.andengine.entity.layer.tiled.tmx.util.constants.TMXConstants;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public final class bi {
    public final String mW;
    public final String mX;
    public final List<String> mY;
    public final String mZ;
    public final List<String> na;
    public final String nb;

    public bi(JSONObject jSONObject) throws JSONException {
        this.mX = jSONObject.getString(TMXConstants.TAG_TILE_ATTRIBUTE_ID);
        JSONArray jSONArray = jSONObject.getJSONArray("adapters");
        ArrayList arrayList = new ArrayList(jSONArray.length());
        for (int i = 0; i < jSONArray.length(); i++) {
            arrayList.add(jSONArray.getString(i));
        }
        this.mY = Collections.unmodifiableList(arrayList);
        this.mZ = jSONObject.optString("allocation_id", null);
        this.na = bo.a(jSONObject, "imp_urls");
        JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject("ad");
        this.mW = jSONObjectOptJSONObject != null ? jSONObjectOptJSONObject.toString() : null;
        JSONObject jSONObjectOptJSONObject2 = jSONObject.optJSONObject("data");
        this.nb = jSONObjectOptJSONObject2 != null ? jSONObjectOptJSONObject2.toString() : null;
    }
}
