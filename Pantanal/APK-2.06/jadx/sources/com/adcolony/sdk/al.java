package com.adcolony.sdk;

import android.media.SoundPool;
import java.util.HashMap;
import org.anddev.andengine.entity.layer.tiled.tmx.util.constants.TMXConstants;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
class al {
    final String a;
    final int b;
    HashMap<Integer, Integer> c = new HashMap<>();
    HashMap<Integer, Integer> d = new HashMap<>();
    HashMap<Integer, Integer> f = new HashMap<>();
    HashMap<Integer, Boolean> e = new HashMap<>();
    HashMap<Integer, Integer> g = new HashMap<>();
    HashMap<String, Integer> h = new HashMap<>();
    SoundPool i = new SoundPool(50, 3, 0);

    al(final String str, final int i) {
        this.a = str;
        this.b = i;
        this.i.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() { // from class: com.adcolony.sdk.al.1
            @Override // android.media.SoundPool.OnLoadCompleteListener
            public void onLoadComplete(SoundPool sp, int id, int status) {
                JSONObject jSONObjectA = s.a();
                s.b(jSONObjectA, TMXConstants.TAG_TILE_ATTRIBUTE_ID, al.this.f.get(Integer.valueOf(id)).intValue());
                s.a(jSONObjectA, "ad_session_id", str);
                if (status == 0) {
                    new z("AudioPlayer.on_ready", i, jSONObjectA).b();
                    al.this.g.put(al.this.f.get(Integer.valueOf(id)), Integer.valueOf(id));
                } else {
                    new z("AudioPlayer.on_error", i, jSONObjectA).b();
                }
            }
        });
    }

    void a(z zVar) {
        int iLoad = this.i.load(s.b(zVar.b, "filepath"), 1);
        int i = s.d(zVar.b, "repeats") ? -1 : 0;
        this.f.put(Integer.valueOf(iLoad), Integer.valueOf(s.c(zVar.b, TMXConstants.TAG_TILE_ATTRIBUTE_ID)));
        u.d.a("Load audio with id = ").b(iLoad);
        this.d.put(Integer.valueOf(iLoad), Integer.valueOf(i));
        this.e.put(Integer.valueOf(iLoad), false);
    }

    void b(z zVar) {
        this.i.unload(this.g.get(Integer.valueOf(s.c(zVar.b, TMXConstants.TAG_TILE_ATTRIBUTE_ID))).intValue());
    }

    void c(z zVar) {
        int iIntValue = this.g.get(Integer.valueOf(s.c(zVar.b, TMXConstants.TAG_TILE_ATTRIBUTE_ID))).intValue();
        if (!this.e.get(Integer.valueOf(iIntValue)).booleanValue()) {
            int iPlay = this.i.play(iIntValue, 1.0f, 1.0f, 0, this.d.get(Integer.valueOf(iIntValue)).intValue(), 1.0f);
            if (iPlay != 0) {
                this.c.put(Integer.valueOf(iIntValue), Integer.valueOf(iPlay));
                return;
            }
            JSONObject jSONObjectA = s.a();
            s.b(jSONObjectA, TMXConstants.TAG_TILE_ATTRIBUTE_ID, s.c(zVar.b, TMXConstants.TAG_TILE_ATTRIBUTE_ID));
            s.a(jSONObjectA, "ad_session_id", this.a);
            new z("AudioPlayer.on_error", this.b, jSONObjectA).b();
            return;
        }
        this.i.resume(this.c.get(Integer.valueOf(iIntValue)).intValue());
    }

    void d(z zVar) {
        int iIntValue = this.g.get(Integer.valueOf(s.c(zVar.b, TMXConstants.TAG_TILE_ATTRIBUTE_ID))).intValue();
        this.i.pause(this.c.get(Integer.valueOf(iIntValue)).intValue());
        this.e.put(Integer.valueOf(iIntValue), true);
    }

    void e(z zVar) {
        this.i.stop(this.c.get(this.g.get(Integer.valueOf(s.c(zVar.b, TMXConstants.TAG_TILE_ATTRIBUTE_ID)))).intValue());
    }
}
