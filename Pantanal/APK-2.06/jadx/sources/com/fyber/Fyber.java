package com.fyber;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.util.Log;
import android.webkit.CookieSyncManager;
import com.fyber.annotations.SDKFeatures;
import com.fyber.b.e;
import com.fyber.cache.CacheManager;
import com.fyber.exceptions.IdException;
import com.fyber.mediation.annotations.MediationAPI;
import com.fyber.reporters.a.b;
import com.fyber.requesters.RequestError;
import com.fyber.utils.FyberLogger;
import com.fyber.utils.StringUtils;
import com.fyber.utils.d;
import com.fyber.utils.f;
import com.fyber.utils.h;
import com.fyber.utils.i;
import com.fyber.utils.m;
import com.fyber.utils.n;
import com.fyber.utils.v;
import com.tapjoy.TJAdUnitConstants;
import java.lang.ref.WeakReference;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/* JADX INFO: loaded from: classes.dex */
@SDKFeatures({"banners"})
@MediationAPI(5)
public class Fyber {
    public static final String RELEASE_VERSION_STRING = String.format(Locale.ENGLISH, "%s", "8.5.5");
    private static Fyber a;
    private final Context b;
    private final WeakReference<Activity> c;
    private a d;
    private boolean e = false;

    private Fyber(String str, Activity activity) {
        this.d = new a(str, activity.getApplicationContext(), (byte) 0);
        this.b = activity.getApplicationContext();
        this.c = new WeakReference<>(activity);
    }

    public static Fyber with(@NonNull String str, @NonNull Activity activity) throws IllegalArgumentException {
        if (a != null) {
            if (!a.e) {
                a.d.g.a(str);
            }
        } else {
            if (activity == null) {
                throw new IllegalArgumentException("Activity cannot be null.");
            }
            if (StringUtils.nullOrEmpty(str)) {
                throw new IllegalArgumentException("App id cannot be null nor empty.");
            }
            if (!com.fyber.a.a.b(str)) {
                synchronized (Fyber.class) {
                    if (a == null) {
                        a = new Fyber(str, activity);
                    }
                }
            } else {
                throw new IllegalArgumentException("Advertisers cannot start the sdk");
            }
        }
        return a;
    }

    public Fyber withUserId(String str) {
        if (!this.e && StringUtils.notNullNorEmpty(str)) {
            this.d.g.b(str);
        }
        return this;
    }

    public Fyber withManualPrecaching() {
        if (!this.e) {
            CacheManager.pauseDownloads(this.b);
        }
        return this;
    }

    public Fyber withSecurityToken(String str) {
        if (!this.e) {
            this.d.g.c(str);
        }
        return this;
    }

    public Fyber withParameters(Map<String, String> map) {
        if (!this.e) {
            this.d.c.addParameters(map);
        }
        return this;
    }

    public Settings start() {
        if (!this.e) {
            com.fyber.a.a aVarA = this.d.g.a();
            this.e = true;
            this.d.f = aVarA;
            try {
                b.a(aVarA.a()).report(this.b);
            } catch (IdException e) {
            }
            if (h.e()) {
                Activity activity = this.c.get();
                if (activity != null) {
                    com.fyber.mediation.b.a.a(activity);
                } else {
                    FyberLogger.d("Fyber", "There was an issue starting the adapters - the activity might have been closed.");
                }
                CacheManager.a().a(this.b);
            }
        }
        return this.d.c;
    }

    public static class Settings {
        public static Settings a = new Settings();
        private Map<String, String> b;
        private boolean c;
        private boolean d;
        private boolean e;
        private EnumMap<UIStringIdentifier, String> f;

        public enum UIStringIdentifier {
            ERROR_DIALOG_TITLE,
            DISMISS_ERROR_DIALOG,
            GENERIC_ERROR,
            ERROR_LOADING_OFFERWALL,
            ERROR_LOADING_OFFERWALL_NO_INTERNET_CONNECTION,
            LOADING_INTERSTITIAL,
            LOADING_OFFERWALL,
            ERROR_PLAY_STORE_UNAVAILABLE,
            RV_REWARD_NOTIFICATION,
            VCS_COINS_NOTIFICATION,
            VCS_DEFAULT_CURRENCY,
            RV_ERROR_DIALOG_TITLE,
            RV_ERROR_DIALOG_MESSAGE_DEFAULT,
            RV_ERROR_DIALOG_MESSAGE_OFFLINE,
            RV_ERROR_DIALOG_BUTTON_TITLE_DISMISS,
            RV_FORFEIT_DIALOG_TITLE,
            RV_CLICKTHROUGH_HINT,
            RV_ALERT_DIALOG_EXIT_VIDEO_TEXT,
            RV_ALERT_DIALOG_CLOSE_VIDEO_TEXT,
            RV_ALERT_DIALOG_RESUME_VIDEO_TEXT,
            RV_ALERT_DIALOG_TITLE,
            RV_ALERT_DIALOG_MESSAGE,
            RV_LOADING_MESSAGE,
            RV_REDIRECT_DIALOG_TITLE,
            RV_REDIRECT_DIALOG_MESSAGE_MARKET,
            RV_REDIRECT_DIALOG_MESSAGE_DEFAULT,
            RV_REDIRECT_ERROR,
            INT_VIDEO_DIALOG_CLOSE
        }

        /* synthetic */ Settings(byte b) {
            this();
        }

        private Settings() {
            this.c = true;
            this.d = true;
            this.e = false;
            this.f = new EnumMap<>(UIStringIdentifier.class);
            this.f.put(UIStringIdentifier.ERROR_DIALOG_TITLE, "Error");
            this.f.put(UIStringIdentifier.DISMISS_ERROR_DIALOG, "Dismiss");
            this.f.put(UIStringIdentifier.GENERIC_ERROR, "An error happened when performing this operation");
            this.f.put(UIStringIdentifier.ERROR_LOADING_OFFERWALL, "An error happened when loading the offer wall");
            this.f.put(UIStringIdentifier.ERROR_LOADING_OFFERWALL_NO_INTERNET_CONNECTION, "An error happened when loading the offer wall (no internet connection)");
            this.f.put(UIStringIdentifier.LOADING_INTERSTITIAL, TJAdUnitConstants.SPINNER_TITLE);
            this.f.put(UIStringIdentifier.LOADING_OFFERWALL, TJAdUnitConstants.SPINNER_TITLE);
            this.f.put(UIStringIdentifier.ERROR_PLAY_STORE_UNAVAILABLE, "You don't have the Google Play Store application on your device to complete App Install offers.");
            this.f.put(UIStringIdentifier.RV_REWARD_NOTIFICATION, "Thanks! Your reward will be paid out shortly");
            this.f.put(UIStringIdentifier.VCS_COINS_NOTIFICATION, "Congratulations! You've earned %.0f %s!");
            this.f.put(UIStringIdentifier.VCS_DEFAULT_CURRENCY, "coins");
            this.f.put(UIStringIdentifier.RV_ERROR_DIALOG_TITLE, "Error");
            this.f.put(UIStringIdentifier.RV_ERROR_DIALOG_MESSAGE_DEFAULT, "We're sorry, something went wrong. Please try again.");
            this.f.put(UIStringIdentifier.RV_ERROR_DIALOG_MESSAGE_OFFLINE, "Your Internet connection has been lost. Please try again later.");
            this.f.put(UIStringIdentifier.RV_ERROR_DIALOG_BUTTON_TITLE_DISMISS, "Dismiss");
            this.f.put(UIStringIdentifier.RV_FORFEIT_DIALOG_TITLE, "");
            this.f.put(UIStringIdentifier.RV_CLICKTHROUGH_HINT, "Tap anywhere to discover more about this ad");
            this.f.put(UIStringIdentifier.RV_ALERT_DIALOG_EXIT_VIDEO_TEXT, "Exit Video");
            this.f.put(UIStringIdentifier.RV_ALERT_DIALOG_CLOSE_VIDEO_TEXT, "Close Video");
            this.f.put(UIStringIdentifier.RV_ALERT_DIALOG_RESUME_VIDEO_TEXT, "Resume Video");
            this.f.put(UIStringIdentifier.RV_ALERT_DIALOG_TITLE, "Error");
            this.f.put(UIStringIdentifier.RV_ALERT_DIALOG_MESSAGE, "An error has occurred while trying to load the video");
            this.f.put(UIStringIdentifier.RV_LOADING_MESSAGE, TJAdUnitConstants.SPINNER_TITLE);
            this.f.put(UIStringIdentifier.RV_REDIRECT_DIALOG_TITLE, "Warning");
            this.f.put(UIStringIdentifier.RV_REDIRECT_DIALOG_MESSAGE_MARKET, "You will now be redirected to the play store, do you wish to forfeit your reward?");
            this.f.put(UIStringIdentifier.RV_REDIRECT_DIALOG_MESSAGE_DEFAULT, "Do you wish to forfeit your reward?");
            this.f.put(UIStringIdentifier.RV_REDIRECT_ERROR, "Sorry, we cannot redirect you to the desired application");
            this.f.put(UIStringIdentifier.INT_VIDEO_DIALOG_CLOSE, "Do you really want to skip the video?");
        }

        public String getUserId() {
            return Fyber.getConfigs().e().b();
        }

        public void updateUserId(String str) throws IllegalStateException, IllegalArgumentException {
            Fyber.getConfigs().e().a(str);
        }

        public Settings notifyUserOnCompletion(boolean z) {
            this.c = z;
            return this;
        }

        public Settings notifyUserOnReward(boolean z) {
            this.d = z;
            return this;
        }

        public Settings closeOfferWallOnRedirect(boolean z) {
            this.e = z;
            return this;
        }

        public Settings addParameters(Map<String, String> map) {
            if (m.b(map)) {
                if (this.b == null) {
                    this.b = new HashMap();
                }
                this.b.putAll(map);
            }
            return this;
        }

        public Settings addParameter(String str, String str2) {
            if (this.b == null) {
                this.b = new HashMap();
            }
            this.b.put(str, str2);
            return this;
        }

        public Settings clearParameters() {
            if (this.b != null) {
                this.b.clear();
            }
            return this;
        }

        public Settings removeParameter(String str) {
            if (this.b != null) {
                this.b.remove(str);
            }
            return this;
        }

        public String getUIString(UIStringIdentifier uIStringIdentifier) {
            return this.f.get(uIStringIdentifier);
        }

        public Settings setCustomUIString(UIStringIdentifier uIStringIdentifier, String str) {
            this.f.put(uIStringIdentifier, str);
            return this;
        }

        public Settings setCustomUIStrings(EnumMap<UIStringIdentifier, String> enumMap) {
            Iterator it = enumMap.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                setCustomUIString((UIStringIdentifier) entry.getKey(), (String) entry.getValue());
            }
            return this;
        }

        public Settings setCustomUIStrings(EnumMap<UIStringIdentifier, Integer> enumMap, Context context) {
            Iterator it = enumMap.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                setCustomUIString((UIStringIdentifier) entry.getKey(), ((Integer) entry.getValue()).intValue(), context);
            }
            return this;
        }

        public Settings setCustomUIString(UIStringIdentifier uIStringIdentifier, int i, Context context) {
            setCustomUIString(uIStringIdentifier, context.getString(i));
            return this;
        }
    }

    public static class a {
        public static a a = new a();
        static final Handler b = new Handler(Looper.getMainLooper(), new Handler.Callback() { // from class: com.fyber.Fyber.a.1
            @Override // android.os.Handler.Callback
            public final boolean handleMessage(Message message) {
                switch (message.what) {
                    case 10:
                        ((d) message.obj).a();
                        return true;
                    default:
                        return false;
                }
            }
        });
        private final Settings c;
        private final h d;
        private final e e;
        private com.fyber.a.a f;
        private com.fyber.a.a.C0016a g;

        /* synthetic */ a(String str, Context context, byte b2) {
            this(str, context);
        }

        private a() {
            this.c = Settings.a;
            this.d = null;
            this.e = null;
            this.f = com.fyber.a.a.a;
        }

        private a(@NonNull String str, @NonNull Context context) {
            if (h.e()) {
                f.a(context);
                this.c = new Settings((byte) 0);
                this.e = new e();
                if (n.b(21)) {
                    CookieSyncManager.createInstance(context);
                }
                i.a(new com.fyber.utils.cookies.a(context));
            } else {
                if (FyberLogger.isLogging()) {
                    FyberLogger.i("Fyber", RequestError.DEVICE_NOT_SUPPORTED.getDescription());
                } else {
                    Log.i("Fyber", RequestError.DEVICE_NOT_SUPPORTED.getDescription());
                }
                this.c = Settings.a;
                this.e = null;
            }
            this.f = com.fyber.a.a.a;
            this.g = new com.fyber.a.a.C0016a(str).b(v.a(context));
            this.d = h.a(context);
        }

        public final h a() {
            return this.d;
        }

        public final Settings b() {
            return this.c;
        }

        public final Map<String, String> c() {
            return this.c.b;
        }

        public final <T> Future<T> a(Callable<T> callable) {
            return this.e.submit(callable);
        }

        public final void a(Runnable runnable) {
            this.e.execute(runnable);
        }

        public final boolean d() {
            return this.f != com.fyber.a.a.a;
        }

        public final com.fyber.a.a e() {
            return this.f;
        }

        public final Object a(String str) {
            switch (str) {
                case "CLOSE_ON_REDIRECT":
                    return Boolean.valueOf(this.c.e);
                case "NOTIFY_USER_ON_REWARD":
                    return Boolean.valueOf(this.c.d);
                case "SHOULD_NOTIFY_ON_USER_ENGAGED":
                    return Boolean.valueOf(this.c.c);
                default:
                    return null;
            }
        }

        public static void a(d dVar) {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                dVar.a();
            } else {
                b.obtainMessage(10, dVar).sendToTarget();
            }
        }
    }

    public static a getConfigs() {
        return a != null ? a.d : a.a;
    }
}
