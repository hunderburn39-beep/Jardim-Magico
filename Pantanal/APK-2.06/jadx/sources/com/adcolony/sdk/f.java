package com.adcolony.sdk;

import java.util.HashMap;
import org.anddev.andengine.entity.layer.tiled.tmx.util.constants.TMXConstants;

/* JADX INFO: loaded from: classes.dex */
class f {
    HashMap<Integer, Boolean> a = new HashMap<>();
    al b;
    y c;
    String d;
    int e;

    f(String str, int i) {
        this.d = str;
        this.e = i;
    }

    void a(z zVar) {
        if (this.b == null) {
            this.b = new al(this.d, this.e);
            this.c = new y(this.d, this.e);
        }
        int iC = s.c(zVar.b, TMXConstants.TAG_TILE_ATTRIBUTE_ID);
        if (s.d(zVar.b, "use_sound_pool")) {
            this.a.put(Integer.valueOf(iC), true);
            this.b.a(zVar);
        } else {
            this.a.put(Integer.valueOf(iC), false);
            this.c.a(zVar);
        }
    }

    void a() {
        this.b.i.autoPause();
        this.c.a();
    }

    void b() {
        this.b.i.autoResume();
        this.c.b();
    }

    void b(z zVar) {
        if (this.a.get(Integer.valueOf(s.c(zVar.b, TMXConstants.TAG_TILE_ATTRIBUTE_ID))).booleanValue()) {
            this.b.d(zVar);
        } else {
            this.c.b(zVar);
        }
    }

    void c(z zVar) {
        if (this.a.get(Integer.valueOf(s.c(zVar.b, TMXConstants.TAG_TILE_ATTRIBUTE_ID))).booleanValue()) {
            this.b.c(zVar);
        } else {
            this.c.c(zVar);
        }
    }

    void d(z zVar) {
        if (this.a.get(Integer.valueOf(s.c(zVar.b, TMXConstants.TAG_TILE_ATTRIBUTE_ID))).booleanValue()) {
            this.b.b(zVar);
        } else {
            this.c.d(zVar);
        }
    }

    void e(z zVar) {
        if (this.a.get(Integer.valueOf(s.c(zVar.b, TMXConstants.TAG_TILE_ATTRIBUTE_ID))).booleanValue()) {
            this.b.e(zVar);
        } else {
            this.c.e(zVar);
        }
    }
}
