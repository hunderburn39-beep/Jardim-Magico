package com.google.android.gms.tagmanager;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import com.google.android.gms.common.api.PendingResult;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/* JADX INFO: loaded from: classes.dex */
public class TagManager {
    private static TagManager aay;
    private final DataLayer WK;
    private final r Zg;
    private final a aaw;
    private final ConcurrentMap<n, Boolean> aax;
    private final Context mContext;

    interface a {
        o a(Context context, TagManager tagManager, Looper looper, String str, int i, r rVar);
    }

    TagManager(Context context, a containerHolderLoaderProvider, DataLayer dataLayer) {
        if (context == null) {
            throw new NullPointerException("context cannot be null");
        }
        this.mContext = context.getApplicationContext();
        this.aaw = containerHolderLoaderProvider;
        this.aax = new ConcurrentHashMap();
        this.WK = dataLayer;
        this.WK.a(new DataLayer.b() { // from class: com.google.android.gms.tagmanager.TagManager.1
            @Override // com.google.android.gms.tagmanager.DataLayer.b
            public void y(Map<String, Object> map) {
                Object obj = map.get("event");
                if (obj != null) {
                    TagManager.this.bT(obj.toString());
                }
            }
        });
        this.WK.a(new d(this.mContext));
        this.Zg = new r();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void bT(String str) {
        Iterator<n> it = this.aax.keySet().iterator();
        while (it.hasNext()) {
            it.next().bp(str);
        }
    }

    public static TagManager getInstance(Context context) {
        TagManager tagManager;
        synchronized (TagManager.class) {
            if (aay == null) {
                if (context == null) {
                    bh.w("TagManager.getInstance requires non-null context.");
                    throw new NullPointerException();
                }
                aay = new TagManager(context, new a() { // from class: com.google.android.gms.tagmanager.TagManager.2
                    @Override // com.google.android.gms.tagmanager.TagManager.a
                    public o a(Context context2, TagManager tagManager2, Looper looper, String str, int i, r rVar) {
                        return new o(context2, tagManager2, looper, str, i, rVar);
                    }
                }, new DataLayer(new v(context)));
            }
            tagManager = aay;
        }
        return tagManager;
    }

    void a(n nVar) {
        this.aax.put(nVar, true);
    }

    boolean b(n nVar) {
        return this.aax.remove(nVar) != null;
    }

    synchronized boolean g(Uri uri) {
        boolean z;
        cd cdVarKT = cd.kT();
        if (cdVarKT.g(uri)) {
            String containerId = cdVarKT.getContainerId();
            switch (cdVarKT.kU()) {
                case NONE:
                    for (n nVar : this.aax.keySet()) {
                        if (nVar.getContainerId().equals(containerId)) {
                            nVar.br(null);
                            nVar.refresh();
                        }
                    }
                    break;
                case CONTAINER:
                case CONTAINER_DEBUG:
                    for (n nVar2 : this.aax.keySet()) {
                        if (nVar2.getContainerId().equals(containerId)) {
                            nVar2.br(cdVarKT.kV());
                            nVar2.refresh();
                        } else if (nVar2.ke() != null) {
                            nVar2.br(null);
                            nVar2.refresh();
                        }
                    }
                    break;
            }
            z = true;
        } else {
            z = false;
        }
        return z;
    }

    public DataLayer getDataLayer() {
        return this.WK;
    }

    public PendingResult<ContainerHolder> loadContainerDefaultOnly(String containerId, int defaultContainerResourceId) {
        o oVarA = this.aaw.a(this.mContext, this, null, containerId, defaultContainerResourceId, this.Zg);
        oVarA.kh();
        return oVarA;
    }

    public PendingResult<ContainerHolder> loadContainerDefaultOnly(String containerId, int defaultContainerResourceId, Handler handler) {
        o oVarA = this.aaw.a(this.mContext, this, handler.getLooper(), containerId, defaultContainerResourceId, this.Zg);
        oVarA.kh();
        return oVarA;
    }

    public PendingResult<ContainerHolder> loadContainerPreferFresh(String containerId, int defaultContainerResourceId) {
        o oVarA = this.aaw.a(this.mContext, this, null, containerId, defaultContainerResourceId, this.Zg);
        oVarA.kj();
        return oVarA;
    }

    public PendingResult<ContainerHolder> loadContainerPreferFresh(String containerId, int defaultContainerResourceId, Handler handler) {
        o oVarA = this.aaw.a(this.mContext, this, handler.getLooper(), containerId, defaultContainerResourceId, this.Zg);
        oVarA.kj();
        return oVarA;
    }

    public PendingResult<ContainerHolder> loadContainerPreferNonDefault(String containerId, int defaultContainerResourceId) {
        o oVarA = this.aaw.a(this.mContext, this, null, containerId, defaultContainerResourceId, this.Zg);
        oVarA.ki();
        return oVarA;
    }

    public PendingResult<ContainerHolder> loadContainerPreferNonDefault(String containerId, int defaultContainerResourceId, Handler handler) {
        o oVarA = this.aaw.a(this.mContext, this, handler.getLooper(), containerId, defaultContainerResourceId, this.Zg);
        oVarA.ki();
        return oVarA;
    }

    public void setVerboseLoggingEnabled(boolean enableVerboseLogging) {
        bh.setLogLevel(enableVerboseLogging ? 2 : 5);
    }
}
