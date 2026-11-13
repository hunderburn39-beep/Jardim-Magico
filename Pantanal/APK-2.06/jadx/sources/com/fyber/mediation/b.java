package com.fyber.mediation;

import android.app.Activity;
import android.content.Context;
import com.fyber.Fyber;
import com.fyber.ads.banners.BannerSize;
import com.fyber.ads.banners.mediation.BannerMediationAdapter;
import com.fyber.ads.banners.mediation.BannerWrapper;
import com.fyber.ads.interstitials.InterstitialAd;
import com.fyber.ads.interstitials.mediation.InterstitialMediationAdapter;
import com.fyber.ads.videos.mediation.RewardedVideoMediationAdapter;
import com.fyber.ads.videos.mediation.TPNVideoEvent;
import com.fyber.ads.videos.mediation.TPNVideoValidationResult;
import com.fyber.b.f;
import com.fyber.utils.FyberLogger;
import com.fyber.utils.StringUtils;
import com.fyber.utils.d;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.Future;

/* JADX INFO: compiled from: MediationCoordinator.java */
/* JADX INFO: loaded from: classes.dex */
public final class b {
    public static final b a = new b();
    private boolean b = false;
    private Map<String, MediationAdapter> c = new HashMap<String, MediationAdapter>() { // from class: com.fyber.mediation.b.1
        private static final long serialVersionUID = 3512263289646462602L;

        @Override // java.util.HashMap, java.util.AbstractMap, java.util.Map
        public final /* synthetic */ Object put(Object obj, Object obj2) {
            return (MediationAdapter) super.put(((String) obj).toLowerCase(Locale.ENGLISH), (MediationAdapter) obj2);
        }

        @Override // java.util.HashMap, java.util.AbstractMap, java.util.Map
        public final /* synthetic */ Object get(Object obj) {
            return (MediationAdapter) super.get(obj.toString().toLowerCase(Locale.ENGLISH));
        }
    };

    private b() {
    }

    public final void a(final Activity activity) {
        if (!this.b) {
            this.b = true;
            Fyber.getConfigs().a((Runnable) new d() { // from class: com.fyber.mediation.b.2
                @Override // com.fyber.utils.d
                public final void a() {
                    try {
                        Class<?> cls = Class.forName("com.fyber.mediation.MediationAdapterStarter");
                        if (((Integer) cls.getMethod("getAdaptersCount", new Class[0]).invoke(null, new Object[0])).intValue() > 0) {
                            com.fyber.a.a aVarE = Fyber.getConfigs().e();
                            b.this.c.putAll((Map) cls.getMethod("startAdapters", Activity.class, Future.class).invoke(null, activity, StringUtils.notNullNorEmpty(aVarE.c()) ? f.a(aVarE, activity) : null));
                        } else {
                            FyberLogger.d("MediationCoordinator", "No mediation adapters to start");
                        }
                    } catch (ClassNotFoundException e) {
                        FyberLogger.d("MediationCoordinator", "There was an issue starting mediation for this session");
                        FyberLogger.d("MediationCoordinator", "MediationAdapterStarter class was not found. (if you're currently not using mediation, disregard this message)\nIt could mean that there's a proguard entry missing - \"-keep class com.fyber.mediation.MediationAdapterStarter { *;}\"\nOr the annotation processor did not run at compile time.");
                    } catch (IllegalAccessException e2) {
                        e = e2;
                        FyberLogger.d("MediationCoordinator", "There was an issue starting mediation for this session - " + e.getMessage());
                        FyberLogger.d("MediationCoordinator", "Make sure you're currently using 'fyber-annotations-compiler' 1.3.+ \nand that you have following proguard entry - \"-keep class com.fyber.mediation.MediationAdapterStarter { *;}\"");
                    } catch (NoSuchMethodException e3) {
                        e = e3;
                        FyberLogger.d("MediationCoordinator", "There was an issue starting mediation for this session - " + e.getMessage());
                        FyberLogger.d("MediationCoordinator", "Make sure you're currently using 'fyber-annotations-compiler' 1.3.+ \nand that you have following proguard entry - \"-keep class com.fyber.mediation.MediationAdapterStarter { *;}\"");
                    } catch (InvocationTargetException e4) {
                        e = e4;
                        FyberLogger.d("MediationCoordinator", "There was an issue starting mediation for this session - " + e.getMessage());
                        FyberLogger.d("MediationCoordinator", "Make sure you're currently using 'fyber-annotations-compiler' 1.3.+ \nand that you have following proguard entry - \"-keep class com.fyber.mediation.MediationAdapterStarter { *;}\"");
                    }
                    b.this.c.put("Fyber", new com.fyber.mediation.a.a());
                }
            });
        }
    }

    public final void a() {
        if (this.b) {
            Fyber.getConfigs().a(new Runnable() { // from class: com.fyber.mediation.b.3
                @Override // java.lang.Runnable
                public final void run() {
                    for (MediationAdapter mediationAdapter : b.this.c.values()) {
                        if (mediationAdapter.a(a.a)) {
                            mediationAdapter.getVideoMediationAdapter().startPrecaching();
                        }
                    }
                }
            });
        }
    }

    public final boolean a(String str, int i) {
        MediationAdapter mediationAdapter = this.c.get(str);
        if (mediationAdapter != null) {
            return mediationAdapter.a(i);
        }
        return false;
    }

    public final void a(Context context, String str, HashMap<String, String> map, com.fyber.ads.videos.mediation.a aVar) {
        if (!a(str, a.a)) {
            aVar.a(str, a(str), TPNVideoValidationResult.AdapterNotIntegrated, map);
            return;
        }
        RewardedVideoMediationAdapter<? extends MediationAdapter> videoMediationAdapter = this.c.get(str).getVideoMediationAdapter();
        if (videoMediationAdapter == null) {
            return;
        }
        videoMediationAdapter.a(context, aVar, map);
    }

    public final void a(Activity activity, String str, HashMap<String, String> map, com.fyber.ads.videos.mediation.b bVar) {
        if (a(str, a.a)) {
            MediationAdapter mediationAdapter = this.c.get(str);
            if (mediationAdapter.getVideoMediationAdapter() == null) {
                return;
            }
            mediationAdapter.getVideoMediationAdapter().a(activity, bVar, map);
            return;
        }
        bVar.a(str, a(str), TPNVideoEvent.AdapterNotIntegrated, map);
    }

    public final Future<InterstitialAd> a(Context context, com.fyber.ads.interstitials.b.a aVar) {
        InterstitialMediationAdapter<? extends MediationAdapter> interstitialMediationAdapter;
        String strB = aVar.b();
        if (a(strB, a.b) && (interstitialMediationAdapter = this.c.get(strB).getInterstitialMediationAdapter()) != null) {
            return interstitialMediationAdapter.a(context, aVar);
        }
        return null;
    }

    public final boolean a(Activity activity, com.fyber.ads.interstitials.b.a aVar) {
        String strB = aVar.b();
        if (!a(strB, a.b)) {
            return false;
        }
        InterstitialMediationAdapter<? extends MediationAdapter> interstitialMediationAdapter = this.c.get(strB).getInterstitialMediationAdapter();
        if (interstitialMediationAdapter != null) {
            interstitialMediationAdapter.a(activity);
        }
        return true;
    }

    public final Future<BannerWrapper> a(Context context, com.fyber.ads.banners.a.b bVar, List<BannerSize> list) {
        BannerMediationAdapter<? extends MediationAdapter> bannerMediationAdapter;
        String strB = bVar.b();
        if (a(strB, a.c) && (bannerMediationAdapter = this.c.get(strB).getBannerMediationAdapter()) != null) {
            return bannerMediationAdapter.a(context, list);
        }
        return null;
    }

    private String a(String str) {
        return this.c.get(str) != null ? this.c.get(str).getVersion() : "";
    }
}
