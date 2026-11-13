package com.adcolony.sdk;

import android.media.MediaPlayer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import org.anddev.andengine.entity.layer.tiled.tmx.util.constants.TMXConstants;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
class y {
    final String a;
    final int b;
    HashMap<Integer, MediaPlayer> c = new HashMap<>();
    HashMap<Integer, a> d = new HashMap<>();
    HashMap<Integer, Boolean> e = new HashMap<>();
    HashMap<Integer, Boolean> f = new HashMap<>();
    ArrayList<MediaPlayer> g = new ArrayList<>();

    y(String str, int i) {
        this.a = str;
        this.b = i;
    }

    void a(z zVar) {
        MediaPlayer mediaPlayer = new MediaPlayer();
        int iC = s.c(zVar.b, TMXConstants.TAG_TILE_ATTRIBUTE_ID);
        a aVar = new a(iC, s.d(zVar.b, "repeats"));
        this.c.put(Integer.valueOf(iC), mediaPlayer);
        this.d.put(Integer.valueOf(iC), aVar);
        this.e.put(Integer.valueOf(iC), false);
        this.f.put(Integer.valueOf(iC), false);
        mediaPlayer.setOnErrorListener(aVar);
        mediaPlayer.setOnPreparedListener(aVar);
        try {
            mediaPlayer.setDataSource(s.b(zVar.b, "filepath"));
        } catch (Exception e) {
            JSONObject jSONObjectA = s.a();
            s.b(jSONObjectA, TMXConstants.TAG_TILE_ATTRIBUTE_ID, iC);
            s.a(jSONObjectA, "ad_session_id", this.a);
            new z("AudioPlayer.on_error", this.b, jSONObjectA).b();
        }
        mediaPlayer.prepareAsync();
    }

    void a() {
        this.g.clear();
        for (MediaPlayer mediaPlayer : this.c.values()) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
                this.g.add(mediaPlayer);
            }
        }
    }

    void b() {
        Iterator<MediaPlayer> it = this.g.iterator();
        while (it.hasNext()) {
            it.next().start();
        }
        this.g.clear();
    }

    void b(z zVar) {
        int iC = s.c(zVar.b, TMXConstants.TAG_TILE_ATTRIBUTE_ID);
        if (this.f.get(Integer.valueOf(iC)).booleanValue()) {
            this.c.get(Integer.valueOf(iC)).pause();
        }
    }

    void c(z zVar) {
        int iC = s.c(zVar.b, TMXConstants.TAG_TILE_ATTRIBUTE_ID);
        if (this.e.get(Integer.valueOf(iC)).booleanValue()) {
            this.c.get(Integer.valueOf(iC)).start();
            this.f.put(Integer.valueOf(iC), true);
        }
    }

    void d(z zVar) {
        this.c.remove(Integer.valueOf(s.c(zVar.b, TMXConstants.TAG_TILE_ATTRIBUTE_ID))).release();
    }

    void e(z zVar) {
        int iC = s.c(zVar.b, TMXConstants.TAG_TILE_ATTRIBUTE_ID);
        if (this.f.get(Integer.valueOf(iC)).booleanValue()) {
            MediaPlayer mediaPlayer = this.c.get(Integer.valueOf(iC));
            mediaPlayer.pause();
            mediaPlayer.seekTo(0);
        }
    }

    private class a implements MediaPlayer.OnErrorListener, MediaPlayer.OnPreparedListener {
        int a;
        boolean b;

        a(int i, boolean z) {
            this.a = i;
            this.b = z;
        }

        @Override // android.media.MediaPlayer.OnErrorListener
        public boolean onError(MediaPlayer mp, int what, int extra) {
            JSONObject jSONObjectA = s.a();
            s.b(jSONObjectA, TMXConstants.TAG_TILE_ATTRIBUTE_ID, this.a);
            s.a(jSONObjectA, "ad_session_id", y.this.a);
            new z("AudioPlayer.on_error", y.this.b, jSONObjectA).b();
            return true;
        }

        @Override // android.media.MediaPlayer.OnPreparedListener
        public void onPrepared(MediaPlayer mp) {
            mp.setLooping(this.b);
            y.this.e.put(Integer.valueOf(this.a), true);
            JSONObject jSONObjectA = s.a();
            s.b(jSONObjectA, TMXConstants.TAG_TILE_ATTRIBUTE_ID, this.a);
            s.a(jSONObjectA, "ad_session_id", y.this.a);
            new z("AudioPlayer.on_ready", y.this.b, jSONObjectA).b();
        }
    }
}
