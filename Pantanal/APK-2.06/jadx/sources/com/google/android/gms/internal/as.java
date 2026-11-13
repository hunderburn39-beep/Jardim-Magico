package com.google.android.gms.internal;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import com.google.ads.mediation.admob.AdMobAdapter;
import com.google.android.gms.ads.mediation.MediationAdapter;
import com.google.android.gms.ads.mediation.NetworkExtras;
import com.google.android.gms.ads.mediation.admob.AdMobExtras;
import com.google.android.gms.ads.search.SearchAdRequest;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public final class as {
    public static final String DEVICE_ID_EMULATOR = dv.u("emulator");
    private final Date d;
    private final Set<String> f;
    private final Location h;
    private final String lY;
    private final int lZ;
    private final boolean ma;
    private final Map<Class<? extends MediationAdapter>, Bundle> mb;
    private final Map<Class<? extends NetworkExtras>, NetworkExtras> mc;
    private final String md;
    private final SearchAdRequest me;
    private final int mf;
    private final Set<String> mg;

    public static final class a {
        private Date d;
        private Location h;
        private String lY;
        private String md;
        private final HashSet<String> mh = new HashSet<>();
        private final HashMap<Class<? extends MediationAdapter>, Bundle> mi = new HashMap<>();
        private final HashMap<Class<? extends NetworkExtras>, NetworkExtras> mj = new HashMap<>();
        private final HashSet<String> mk = new HashSet<>();
        private int lZ = -1;
        private boolean ma = false;
        private int mf = -1;

        public void a(Location location) {
            this.h = location;
        }

        @Deprecated
        public void a(NetworkExtras networkExtras) {
            if (networkExtras instanceof AdMobExtras) {
                a(AdMobAdapter.class, ((AdMobExtras) networkExtras).getExtras());
            } else {
                this.mj.put((Class<? extends NetworkExtras>) networkExtras.getClass(), networkExtras);
            }
        }

        public void a(Class<? extends MediationAdapter> cls, Bundle bundle) {
            this.mi.put(cls, bundle);
        }

        public void a(Date date) {
            this.d = date;
        }

        public void d(int i) {
            this.lZ = i;
        }

        public void f(boolean z) {
            this.ma = z;
        }

        public void g(String str) {
            this.mh.add(str);
        }

        public void g(boolean z) {
            this.mf = z ? 1 : 0;
        }

        public void h(String str) {
            this.mk.add(str);
        }

        public void i(String str) {
            this.lY = str;
        }

        public void j(String str) {
            this.md = str;
        }
    }

    public as(a aVar) {
        this(aVar, null);
    }

    public as(a aVar, SearchAdRequest searchAdRequest) {
        this.d = aVar.d;
        this.lY = aVar.lY;
        this.lZ = aVar.lZ;
        this.f = Collections.unmodifiableSet(aVar.mh);
        this.h = aVar.h;
        this.ma = aVar.ma;
        this.mb = Collections.unmodifiableMap(aVar.mi);
        this.mc = Collections.unmodifiableMap(aVar.mj);
        this.md = aVar.md;
        this.me = searchAdRequest;
        this.mf = aVar.mf;
        this.mg = Collections.unmodifiableSet(aVar.mk);
    }

    public SearchAdRequest aB() {
        return this.me;
    }

    public Map<Class<? extends NetworkExtras>, NetworkExtras> aC() {
        return this.mc;
    }

    public Map<Class<? extends MediationAdapter>, Bundle> aD() {
        return this.mb;
    }

    public int aE() {
        return this.mf;
    }

    public Date getBirthday() {
        return this.d;
    }

    public String getContentUrl() {
        return this.lY;
    }

    public int getGender() {
        return this.lZ;
    }

    public Set<String> getKeywords() {
        return this.f;
    }

    public Location getLocation() {
        return this.h;
    }

    public boolean getManualImpressionsEnabled() {
        return this.ma;
    }

    @Deprecated
    public <T extends NetworkExtras> T getNetworkExtras(Class<T> networkExtrasClass) {
        return (T) this.mc.get(networkExtrasClass);
    }

    public Bundle getNetworkExtrasBundle(Class<? extends MediationAdapter> adapterClass) {
        return this.mb.get(adapterClass);
    }

    public String getPublisherProvidedId() {
        return this.md;
    }

    public boolean isTestDevice(Context context) {
        return this.mg.contains(dv.l(context));
    }
}
