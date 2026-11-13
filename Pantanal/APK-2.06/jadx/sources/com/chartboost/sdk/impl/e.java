package com.chartboost.sdk.impl;

import com.chartboost.sdk.Libraries.CBLogging;
import com.chartboost.sdk.Model.CBError;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class e implements aj.a {
    private final b a;
    private final d b;
    private final ak c;
    private final AtomicReference<com.chartboost.sdk.Model.c> d;
    private int e = 1;
    private int f = 0;
    private long g = 0;
    private aj h = null;
    private AtomicInteger i = null;

    public e(b bVar, d dVar, ak akVar, AtomicReference<com.chartboost.sdk.Model.c> atomicReference) {
        this.a = bVar;
        this.b = dVar;
        this.c = akVar;
        this.d = atomicReference;
    }

    /* JADX WARN: Code duplicated, block: B:20:0x0040 A[Catch: Exception -> 0x0053, all -> 0x006f, TryCatch #0 {Exception -> 0x0053, blocks: (B:4:0x0006, B:6:0x0015, B:8:0x0019, B:13:0x0022, B:15:0x0027, B:17:0x002f, B:18:0x003c, B:20:0x0040, B:22:0x004b, B:33:0x0072, B:34:0x0083, B:36:0x0087, B:38:0x008b, B:39:0x00c5, B:40:0x00cc, B:42:0x00d0, B:43:0x0109, B:10:0x001d), top: B:45:0x0006, outer: #1 }] */
    /* JADX WARN: Code duplicated, block: B:22:0x004b A[Catch: Exception -> 0x0053, all -> 0x006f, TRY_LEAVE, TryCatch #0 {Exception -> 0x0053, blocks: (B:4:0x0006, B:6:0x0015, B:8:0x0019, B:13:0x0022, B:15:0x0027, B:17:0x002f, B:18:0x003c, B:20:0x0040, B:22:0x004b, B:33:0x0072, B:34:0x0083, B:36:0x0087, B:38:0x008b, B:39:0x00c5, B:40:0x00cc, B:42:0x00d0, B:43:0x0109, B:10:0x001d), top: B:45:0x0006, outer: #1 }] */
    /* JADX WARN: Code duplicated, block: B:33:0x0072 A[Catch: Exception -> 0x0053, all -> 0x006f, TRY_ENTER, TryCatch #0 {Exception -> 0x0053, blocks: (B:4:0x0006, B:6:0x0015, B:8:0x0019, B:13:0x0022, B:15:0x0027, B:17:0x002f, B:18:0x003c, B:20:0x0040, B:22:0x004b, B:33:0x0072, B:34:0x0083, B:36:0x0087, B:38:0x008b, B:39:0x00c5, B:40:0x00cc, B:42:0x00d0, B:43:0x0109, B:10:0x001d), top: B:45:0x0006, outer: #1 }] */
    /* JADX WARN: Code duplicated, block: B:34:0x0083 A[Catch: Exception -> 0x0053, all -> 0x006f, TryCatch #0 {Exception -> 0x0053, blocks: (B:4:0x0006, B:6:0x0015, B:8:0x0019, B:13:0x0022, B:15:0x0027, B:17:0x002f, B:18:0x003c, B:20:0x0040, B:22:0x004b, B:33:0x0072, B:34:0x0083, B:36:0x0087, B:38:0x008b, B:39:0x00c5, B:40:0x00cc, B:42:0x00d0, B:43:0x0109, B:10:0x001d), top: B:45:0x0006, outer: #1 }] */
    /* JADX WARN: Code duplicated, block: B:36:0x0087 A[Catch: Exception -> 0x0053, all -> 0x006f, TryCatch #0 {Exception -> 0x0053, blocks: (B:4:0x0006, B:6:0x0015, B:8:0x0019, B:13:0x0022, B:15:0x0027, B:17:0x002f, B:18:0x003c, B:20:0x0040, B:22:0x004b, B:33:0x0072, B:34:0x0083, B:36:0x0087, B:38:0x008b, B:39:0x00c5, B:40:0x00cc, B:42:0x00d0, B:43:0x0109, B:10:0x001d), top: B:45:0x0006, outer: #1 }] */
    /* JADX WARN: Code duplicated, block: B:38:0x008b A[Catch: Exception -> 0x0053, all -> 0x006f, TryCatch #0 {Exception -> 0x0053, blocks: (B:4:0x0006, B:6:0x0015, B:8:0x0019, B:13:0x0022, B:15:0x0027, B:17:0x002f, B:18:0x003c, B:20:0x0040, B:22:0x004b, B:33:0x0072, B:34:0x0083, B:36:0x0087, B:38:0x008b, B:39:0x00c5, B:40:0x00cc, B:42:0x00d0, B:43:0x0109, B:10:0x001d), top: B:45:0x0006, outer: #1 }] */
    /* JADX WARN: Code duplicated, block: B:40:0x00cc A[Catch: Exception -> 0x0053, all -> 0x006f, TryCatch #0 {Exception -> 0x0053, blocks: (B:4:0x0006, B:6:0x0015, B:8:0x0019, B:13:0x0022, B:15:0x0027, B:17:0x002f, B:18:0x003c, B:20:0x0040, B:22:0x004b, B:33:0x0072, B:34:0x0083, B:36:0x0087, B:38:0x008b, B:39:0x00c5, B:40:0x00cc, B:42:0x00d0, B:43:0x0109, B:10:0x001d), top: B:45:0x0006, outer: #1 }] */
    /* JADX WARN: Code duplicated, block: B:42:0x00d0 A[Catch: Exception -> 0x0053, all -> 0x006f, TryCatch #0 {Exception -> 0x0053, blocks: (B:4:0x0006, B:6:0x0015, B:8:0x0019, B:13:0x0022, B:15:0x0027, B:17:0x002f, B:18:0x003c, B:20:0x0040, B:22:0x004b, B:33:0x0072, B:34:0x0083, B:36:0x0087, B:38:0x008b, B:39:0x00c5, B:40:0x00cc, B:42:0x00d0, B:43:0x0109, B:10:0x001d), top: B:45:0x0006, outer: #1 }] */
    /* JADX WARN: Code duplicated, block: B:43:0x0109 A[Catch: Exception -> 0x0053, all -> 0x006f, TRY_LEAVE, TryCatch #0 {Exception -> 0x0053, blocks: (B:4:0x0006, B:6:0x0015, B:8:0x0019, B:13:0x0022, B:15:0x0027, B:17:0x002f, B:18:0x003c, B:20:0x0040, B:22:0x004b, B:33:0x0072, B:34:0x0083, B:36:0x0087, B:38:0x008b, B:39:0x00c5, B:40:0x00cc, B:42:0x00d0, B:43:0x0109, B:10:0x001d), top: B:45:0x0006, outer: #1 }] */
    public synchronized void a() {
        try {
            com.chartboost.sdk.Model.c cVar = this.d.get();
            a(cVar);
            if (cVar.c || cVar.b || !com.chartboost.sdk.i.v) {
                b();
            } else if (this.e == 3) {
                if (this.i.get() <= 0) {
                    CBLogging.a("Prefetcher", "Change state to COOLDOWN");
                    this.e = 4;
                    this.i = null;
                    if (this.e != 4) {
                        if (this.e == 1) {
                            if (cVar.u) {
                                an anVar = new an(cVar.E, this.c);
                                anVar.a("cache_assets", this.a.b(), an.a.AD);
                                anVar.a(com.chartboost.sdk.Model.b.g);
                                CBLogging.a("Prefetcher", "Change state to AWAIT_PREFETCH_RESPONSE");
                                this.e = 2;
                                this.f = 2;
                                this.g = System.nanoTime() + TimeUnit.MINUTES.toNanos(cVar.A);
                                this.h = anVar;
                            } else if (cVar.e) {
                                aj ajVar = new aj("/api/video-prefetch", this.c);
                                ajVar.a("local-videos", this.a.c());
                                ajVar.a(com.chartboost.sdk.Model.b.f);
                                CBLogging.a("Prefetcher", "Change state to AWAIT_PREFETCH_RESPONSE");
                                this.e = 2;
                                this.f = 1;
                                this.g = System.nanoTime() + TimeUnit.MINUTES.toNanos(cVar.j);
                                this.h = ajVar;
                            } else {
                                CBLogging.b("Prefetcher", "Did not prefetch because neither native nor webview are enabled.");
                            }
                            this.h.a(this);
                        }
                    } else if (this.g - System.nanoTime() > 0) {
                        CBLogging.a("Prefetcher", "Prefetch session is still active. Won't be making any new prefetch until the prefetch session expires");
                    } else {
                        CBLogging.a("Prefetcher", "Change state to IDLE");
                        this.e = 1;
                        this.f = 0;
                        this.g = 0L;
                        if (this.e == 1) {
                            if (cVar.u) {
                                an anVar2 = new an(cVar.E, this.c);
                                anVar2.a("cache_assets", this.a.b(), an.a.AD);
                                anVar2.a(com.chartboost.sdk.Model.b.g);
                                CBLogging.a("Prefetcher", "Change state to AWAIT_PREFETCH_RESPONSE");
                                this.e = 2;
                                this.f = 2;
                                this.g = System.nanoTime() + TimeUnit.MINUTES.toNanos(cVar.A);
                                this.h = anVar2;
                            } else if (cVar.e) {
                                aj ajVar2 = new aj("/api/video-prefetch", this.c);
                                ajVar2.a("local-videos", this.a.c());
                                ajVar2.a(com.chartboost.sdk.Model.b.f);
                                CBLogging.a("Prefetcher", "Change state to AWAIT_PREFETCH_RESPONSE");
                                this.e = 2;
                                this.f = 1;
                                this.g = System.nanoTime() + TimeUnit.MINUTES.toNanos(cVar.j);
                                this.h = ajVar2;
                            } else {
                                CBLogging.b("Prefetcher", "Did not prefetch because neither native nor webview are enabled.");
                            }
                            this.h.a(this);
                        }
                    }
                }
            } else if (this.e != 4) {
                if (this.e == 1) {
                    if (cVar.u) {
                        an anVar3 = new an(cVar.E, this.c);
                        anVar3.a("cache_assets", this.a.b(), an.a.AD);
                        anVar3.a(com.chartboost.sdk.Model.b.g);
                        CBLogging.a("Prefetcher", "Change state to AWAIT_PREFETCH_RESPONSE");
                        this.e = 2;
                        this.f = 2;
                        this.g = System.nanoTime() + TimeUnit.MINUTES.toNanos(cVar.A);
                        this.h = anVar3;
                    } else if (cVar.e) {
                        aj ajVar3 = new aj("/api/video-prefetch", this.c);
                        ajVar3.a("local-videos", this.a.c());
                        ajVar3.a(com.chartboost.sdk.Model.b.f);
                        CBLogging.a("Prefetcher", "Change state to AWAIT_PREFETCH_RESPONSE");
                        this.e = 2;
                        this.f = 1;
                        this.g = System.nanoTime() + TimeUnit.MINUTES.toNanos(cVar.j);
                        this.h = ajVar3;
                    } else {
                        CBLogging.b("Prefetcher", "Did not prefetch because neither native nor webview are enabled.");
                    }
                    this.h.a(this);
                }
            } else if (this.g - System.nanoTime() > 0) {
                CBLogging.a("Prefetcher", "Prefetch session is still active. Won't be making any new prefetch until the prefetch session expires");
            } else {
                CBLogging.a("Prefetcher", "Change state to IDLE");
                this.e = 1;
                this.f = 0;
                this.g = 0L;
                if (this.e == 1) {
                    if (cVar.u) {
                        an anVar4 = new an(cVar.E, this.c);
                        anVar4.a("cache_assets", this.a.b(), an.a.AD);
                        anVar4.a(com.chartboost.sdk.Model.b.g);
                        CBLogging.a("Prefetcher", "Change state to AWAIT_PREFETCH_RESPONSE");
                        this.e = 2;
                        this.f = 2;
                        this.g = System.nanoTime() + TimeUnit.MINUTES.toNanos(cVar.A);
                        this.h = anVar4;
                    } else if (cVar.e) {
                        aj ajVar4 = new aj("/api/video-prefetch", this.c);
                        ajVar4.a("local-videos", this.a.c());
                        ajVar4.a(com.chartboost.sdk.Model.b.f);
                        CBLogging.a("Prefetcher", "Change state to AWAIT_PREFETCH_RESPONSE");
                        this.e = 2;
                        this.f = 1;
                        this.g = System.nanoTime() + TimeUnit.MINUTES.toNanos(cVar.j);
                        this.h = ajVar4;
                    } else {
                        CBLogging.b("Prefetcher", "Did not prefetch because neither native nor webview are enabled.");
                    }
                    this.h.a(this);
                }
            }
        } catch (Exception e) {
            if (this.e == 2) {
                CBLogging.a("Prefetcher", "Change state to COOLDOWN");
                this.e = 4;
                this.h = null;
            }
            com.chartboost.sdk.Tracking.a.a(getClass(), "prefetch", e);
        }
    }

    private void a(com.chartboost.sdk.Model.c cVar) {
        boolean z = cVar.u;
        if ((this.f == 1 && !(!z && cVar.e)) || (this.f == 2 && !z)) {
            CBLogging.a("Prefetcher", "Change state to IDLE");
            this.e = 1;
            this.f = 0;
            this.g = 0L;
            this.h = null;
            AtomicInteger atomicInteger = this.i;
            this.i = null;
            if (atomicInteger != null) {
                this.b.a(atomicInteger);
            }
        }
    }

    public synchronized void b() {
        if (this.e == 2) {
            CBLogging.a("Prefetcher", "Change state to COOLDOWN");
            this.e = 4;
            this.h = null;
        } else if (this.e == 3) {
            CBLogging.a("Prefetcher", "Change state to COOLDOWN");
            this.e = 4;
            AtomicInteger atomicInteger = this.i;
            this.i = null;
            if (atomicInteger != null) {
                this.b.a(atomicInteger);
            }
        }
    }

    @Override // com.chartboost.sdk.impl.aj.a
    public synchronized void a(JSONObject jSONObject, aj ajVar) {
        try {
            if (this.e == 2 && ajVar == this.h) {
                CBLogging.a("Prefetcher", "Change state to DOWNLOAD_ASSETS");
                this.e = 3;
                this.h = null;
                this.i = new AtomicInteger();
                if (jSONObject != null) {
                    CBLogging.a("Prefetcher", "Got Asset list for Prefetch from server :)" + jSONObject);
                    if (this.f == 1) {
                        this.b.a(0, jSONObject.getJSONArray("videos"), this.i);
                    } else if (this.f == 2) {
                        this.b.a(0, jSONObject.getJSONObject("cache_assets"), this.i);
                    }
                }
            }
        } catch (Exception e) {
            com.chartboost.sdk.Tracking.a.a(getClass(), "onSuccess", e);
        }
    }

    @Override // com.chartboost.sdk.impl.aj.a
    public synchronized void a(JSONObject jSONObject, aj ajVar, CBError cBError) {
        if (this.e == 2 && ajVar == this.h) {
            this.h = null;
            CBLogging.a("Prefetcher", "Change state to COOLDOWN");
            this.e = 4;
        }
    }
}
