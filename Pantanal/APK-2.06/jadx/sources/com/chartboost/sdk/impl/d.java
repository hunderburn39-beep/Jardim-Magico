package com.chartboost.sdk.impl;

import com.chartboost.sdk.Libraries.CBLogging;
import java.io.File;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import org.anddev.andengine.entity.layer.tiled.tmx.util.constants.TMXConstants;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class d {
    final ak a;
    final AtomicReference<com.chartboost.sdk.Model.c> b;
    private final b c;
    private final af d;
    private final com.chartboost.sdk.Tracking.a e;
    private int g = 1;
    private c h = null;
    private final com.chartboost.sdk.Libraries.g f = new com.chartboost.sdk.Libraries.g(true);
    private final PriorityQueue<a> i = new PriorityQueue<>();

    public d(b bVar, af afVar, ak akVar, AtomicReference<com.chartboost.sdk.Model.c> atomicReference, com.chartboost.sdk.Tracking.a aVar) {
        this.c = bVar;
        this.d = afVar;
        this.a = akVar;
        this.b = atomicReference;
        this.e = aVar;
    }

    public synchronized void a(int i, JSONArray jSONArray, AtomicInteger atomicInteger) {
        int length = jSONArray.length();
        for (int i2 = 0; i2 < length; i2++) {
            try {
                JSONObject jSONObject = jSONArray.getJSONObject(i2);
                this.i.add(new a(i, jSONObject.getString(TMXConstants.TAG_TILE_ATTRIBUTE_ID), jSONObject.getString("video"), "videos", atomicInteger));
            } catch (JSONException e) {
                com.chartboost.sdk.Tracking.a.a(getClass(), "downloadNativeVideos", e);
            }
        }
        if (this.g == 1 || this.g == 2) {
            d();
        }
    }

    public synchronized void a(int i, JSONObject jSONObject, AtomicInteger atomicInteger) {
        try {
            JSONArray jSONArray = jSONObject.getJSONArray("templates");
            int iMin = Math.min(jSONArray.length(), this.b.get().r);
            for (int i2 = 0; i2 < iMin; i2++) {
                try {
                    JSONObject jSONObject2 = jSONArray.getJSONObject(i2);
                    this.f.a(this.f.d().f, jSONObject2.getString("template") + ".json", jSONObject2.toString().getBytes());
                    this.c.a(jSONObject2);
                    JSONArray jSONArray2 = jSONObject2.getJSONArray("elements");
                    int length = jSONArray2.length();
                    for (int i3 = 0; i3 < length; i3++) {
                        JSONObject jSONObject3 = jSONArray2.getJSONObject(i3);
                        String string = jSONObject3.getString("type");
                        if (!string.equals("param")) {
                            this.i.add(new a(i, jSONObject3.getString("name"), jSONObject3.getString(TMXConstants.TAG_PROPERTY_ATTRIBUTE_VALUE), string, atomicInteger));
                        }
                    }
                } catch (JSONException e) {
                    com.chartboost.sdk.Tracking.a.a(getClass(), "downloadWebViewAssets(templates)", e);
                }
            }
            Iterator<String> itKeys = jSONObject.keys();
            while (itKeys.hasNext()) {
                String next = itKeys.next();
                if (!next.equals("templates")) {
                    JSONArray jSONArray3 = jSONObject.getJSONArray(next);
                    int length2 = jSONArray3.length();
                    for (int i4 = 0; i4 < length2; i4++) {
                        try {
                            JSONObject jSONObject4 = jSONArray3.getJSONObject(i4);
                            this.i.add(new a(i, jSONObject4.getString("name"), jSONObject4.getString(TMXConstants.TAG_PROPERTY_ATTRIBUTE_VALUE), next, atomicInteger));
                        } catch (Exception e2) {
                            com.chartboost.sdk.Tracking.a.a(getClass(), "downloadWebViewAssets(" + next + ")", e2);
                        }
                    }
                }
            }
        } catch (JSONException e3) {
            com.chartboost.sdk.Tracking.a.a(getClass(), "downloadWebViewAssets", e3);
        }
        if (this.g == 1 || this.g == 2) {
            d();
        }
    }

    public synchronized void a(com.chartboost.sdk.Model.a aVar, JSONObject jSONObject, int i) {
        JSONObject jSONObjectD = g.a().d();
        JSONArray jSONArrayE = g.a().e();
        jSONArrayE.put(jSONObject);
        com.chartboost.sdk.Libraries.d.a(jSONObjectD, "templates", jSONArrayE);
        this.c.a(aVar, jSONObject);
        a(i, jSONObjectD, new AtomicInteger(0));
    }

    public synchronized void a(AtomicInteger atomicInteger) {
        atomicInteger.set(-10000);
        switch (this.g) {
            case 2:
                if ((this.h.a.e == atomicInteger) && this.h.c()) {
                    this.h = null;
                    d();
                    break;
                }
            case 1:
            case 3:
            default:
                break;
        }
        throw th;
    }

    public synchronized void a() {
        switch (this.g) {
            case 1:
                CBLogging.a("Downloader", "Change state to PAUSED");
                this.g = 4;
                break;
            case 2:
                if (this.h.c()) {
                    this.i.add(this.h.a);
                    this.h = null;
                    CBLogging.a("Downloader", "Change state to PAUSED");
                    this.g = 4;
                } else {
                    CBLogging.a("Downloader", "Change state to PAUSING");
                    this.g = 3;
                }
                break;
        }
    }

    public synchronized void b() {
        switch (this.g) {
            case 3:
                CBLogging.a("Downloader", "Change state to DOWNLOADING");
                this.g = 2;
                break;
            case 4:
                if (this.i.isEmpty()) {
                    CBLogging.a("Downloader", "Change state to IDLE");
                }
                this.g = 1;
                d();
                break;
        }
    }

    synchronized void a(c cVar, ad adVar) {
        switch (this.g) {
            case 2:
            case 3:
                if (cVar == this.h) {
                    a aVar = cVar.a;
                    this.h = null;
                    aVar.e.decrementAndGet();
                    String string = Long.toString(TimeUnit.NANOSECONDS.toSeconds(System.nanoTime() - cVar.g));
                    if (adVar == null) {
                        this.c.a(aVar.d, aVar.b);
                        this.e.e(aVar.c, aVar.b, string);
                        CBLogging.a("Downloader", "Downloaded " + aVar.c);
                    } else {
                        String strA = adVar.a();
                        this.e.c(aVar.c, aVar.b, string, strA);
                        CBLogging.a("Downloader", "Failed to download " + aVar.c + (adVar.a != null ? " Status code=" + adVar.a.b() : "") + (strA != null ? " Error message=" + strA : ""));
                    }
                    if (this.g == 3) {
                        CBLogging.a("Downloader", "Change state to PAUSED");
                        this.g = 4;
                        if (this.i.isEmpty()) {
                            this.c.a();
                        }
                    } else {
                        d();
                    }
                }
                break;
        }
    }

    private void d() throws Throwable {
        a aVarPoll;
        a aVarPeek;
        if (this.h != null && (aVarPeek = this.i.peek()) != null && this.h.a.a < aVarPeek.a && this.h.c()) {
            this.i.add(this.h.a);
            this.h = null;
        }
        boolean z = !this.i.isEmpty();
        while (this.h == null && (aVarPoll = this.i.poll()) != null) {
            if (aVarPoll.e.get() > 0) {
                File file = new File(this.f.d().a, aVarPoll.d);
                if (!file.exists() && !file.mkdir()) {
                    CBLogging.b("Downloader", "Unable to create directory " + file.getPath());
                    aVarPoll.e.decrementAndGet();
                } else {
                    File file2 = new File(file, aVarPoll.b);
                    if (file2.exists()) {
                        com.chartboost.sdk.Libraries.g.d(file2);
                        aVarPoll.e.decrementAndGet();
                    } else {
                        this.h = new c(this, aVarPoll, file2);
                        this.d.a(this.h);
                        this.e.a(aVarPoll.c, aVarPoll.b);
                    }
                }
            }
        }
        if (this.h != null) {
            CBLogging.a("Downloader", "Change state to DOWNLOADING");
            this.g = 2;
        } else if (this.g != 1 || z) {
            CBLogging.a("Downloader", "Change state to IDLE");
            this.g = 1;
            this.c.a();
        }
    }

    public synchronized void c() {
        if (this.g == 1) {
            this.c.d();
        }
    }
}
