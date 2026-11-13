package com.fyber.mediation;

import android.app.Activity;
import com.fyber.Fyber;
import com.fyber.ads.banners.mediation.BannerMediationAdapter;
import com.fyber.ads.interstitials.mediation.InterstitialMediationAdapter;
import com.fyber.ads.videos.mediation.RewardedVideoMediationAdapter;
import com.fyber.cache.CacheManager;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public abstract class MediationAdapter {
    public abstract BannerMediationAdapter<? extends MediationAdapter> getBannerMediationAdapter();

    public abstract InterstitialMediationAdapter<? extends MediationAdapter> getInterstitialMediationAdapter();

    protected abstract Set<?> getListeners();

    public abstract String getName();

    public abstract String getVersion();

    public abstract RewardedVideoMediationAdapter<? extends MediationAdapter> getVideoMediationAdapter();

    public abstract boolean startAdapter(Activity activity, Map<String, Object> map);

    /* JADX INFO: renamed from: com.fyber.mediation.MediationAdapter$2, reason: invalid class name */
    static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] a = new int[a.a().length];

        static {
            try {
                a[a.a - 1] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                a[a.b - 1] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                a[a.c - 1] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    public final boolean a(int i) {
        switch (AnonymousClass2.a[i - 1]) {
            case 1:
                return getVideoMediationAdapter() != null;
            case 2:
                return getInterstitialMediationAdapter() != null;
            case 3:
                return getBannerMediationAdapter() != null;
            default:
                return false;
        }
    }

    protected String getUserId() {
        return Fyber.getConfigs().e().b();
    }

    protected boolean isPrecachingDisabled() {
        return CacheManager.a().e();
    }

    public static <T> T getConfiguration(Map<String, Object> map, String str, Class<T> cls) {
        T t;
        if (map == null || map.isEmpty() || (t = (T) map.get(str)) == null || !t.getClass().isAssignableFrom(cls)) {
            return null;
        }
        return t;
    }

    public static <T> T getConfiguration(Map<String, Object> map, String str, T t, Class<T> cls) {
        T t2 = (T) getConfiguration(map, str, cls);
        return t2 == null ? t : t2;
    }

    protected void notifyListeners(Object[] objArr, Class[] clsArr) {
        a(objArr, clsArr);
    }

    protected void notifyListeners(Object... objArr) {
        Class[] clsArr;
        if (objArr != null) {
            Class[] clsArr2 = new Class[objArr.length];
            for (int i = 0; i < objArr.length; i++) {
                clsArr2[i] = objArr[i].getClass();
            }
            clsArr = clsArr2;
        } else {
            clsArr = new Class[0];
        }
        a(objArr, clsArr);
    }

    private void a(final Object[] objArr, final Class[] clsArr) {
        final String methodName = Thread.currentThread().getStackTrace()[4].getMethodName();
        Fyber.getConfigs().a(new Runnable() { // from class: com.fyber.mediation.MediationAdapter.1
            @Override // java.lang.Runnable
            public final void run() {
                for (Object obj : MediationAdapter.this.getListeners()) {
                    try {
                        obj.getClass().getDeclaredMethod(methodName, clsArr).invoke(obj, objArr);
                    } catch (IllegalAccessException e) {
                    } catch (IllegalArgumentException e2) {
                    } catch (NoSuchMethodException e3) {
                    } catch (SecurityException e4) {
                    } catch (InvocationTargetException e5) {
                    }
                }
            }
        });
    }
}
