package com.fyber.mediation.test.data;

import android.util.Log;
import com.fyber.mediation.MediationAdapter;
import com.fyber.mediation.MediationAdapterStarter;
import com.fyber.mediation.b;
import com.fyber.utils.FyberLogger;
import com.heyzap.common.concurrency.AbstractFuture;
import com.heyzap.common.concurrency.ExecutorPool;
import com.heyzap.common.concurrency.SettableFuture;
import com.heyzap.internal.Logger;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class MediationStatusScanner {
    private static final List<AdapterDescription> adapterDescriptions;
    private static ScheduledFuture<?> adapterPoll;
    public static boolean initializationStarted = false;
    private static Map<String, Map<String, Object>> configs = null;
    private static SettableFuture<MediationStatus> mediationStatusFuture = SettableFuture.create();
    private static AtomicBoolean pollInitialized = new AtomicBoolean(false);

    static {
        boolean z = true;
        adapterDescriptions = new ArrayList(Arrays.asList(new AdapterDescription("com.fyber.mediation.adcolony.AdColonyMediationAdapter"), new AdapterDescription("com.fyber.mediation.admob.AdMobMediationAdapter"), new AdapterDescription("com.fyber.mediation.applovin.AppLovinMediationAdapter"), new AdapterDescription("com.fyber.mediation.chartboost.ChartboostMediationAdapter"), new AdapterDescription("com.fyber.mediation.facebook.FacebookMediationAdapter", "FacebookAudienceNetwork", z), new AdapterDescription("com.fyber.mediation.flurry.FlurryMediationAdapter", false), new AdapterDescription("com.fyber.mediation.inmobi.InMobiMediationAdapter"), new AdapterDescription("com.fyber.mediation.hyprmx.HyprMXMediationAdapter"), new AdapterDescription("com.fyber.mediation.tapjoy.TapjoyMediationAdapter"), new AdapterDescription("com.fyber.mediation.mediabrix.MediaBrixMediationAdapter", false), new AdapterDescription("com.fyber.mediation.millennial.MillennialMediationAdapter", false), new AdapterDescription("com.fyber.mediation.mopub.MopubMediationAdapter", false), new AdapterDescription("com.fyber.mediation.tremor.TremorMediationAdapter", false), new AdapterDescription("com.fyber.mediation.unityads.UnityAdsMediationAdapter", "Applifier", z), new AdapterDescription("com.fyber.mediation.vungle.VungleMediationAdapter")));
    }

    public static void setConfigs(Map<String, Map<String, Object>> pconfigs) {
        configs = pconfigs;
    }

    public static List<AdapterDescription> getAdapterClasses() {
        return adapterDescriptions;
    }

    public static Map<String, MediationAdapter> getAdapters() {
        b coordinatorInstance = b.a;
        for (Field field : Arrays.asList(b.class.getDeclaredFields())) {
            Log.d("MediationStatusScanner", "Found field: " + field.getName() + " - " + field.getType());
            if (field.getType() == Map.class) {
                Log.d("MediationStatusScanner", "Found Map, assuming adapters");
                field.setAccessible(true);
                try {
                    Map<String, MediationAdapter> adapters = (Map) field.get(coordinatorInstance);
                    Log.d("MediationStatusScanner", "Look at these adapters we found: " + adapters);
                    return adapters;
                } catch (IllegalAccessException e) {
                    Log.d("MediationStatusScanner", "Could not access adapters: " + e);
                }
            }
        }
        return null;
    }

    public static MediationStatus getMediationStatus() {
        List<NetworkStatus> networkStatuses = new ArrayList<>();
        Map<String, MediationAdapter> adapters = getAdapters();
        Map<Class, MediationAdapter> adaptersByClass = new HashMap<>();
        if (adapters != null) {
            for (MediationAdapter adapter : adapters.values()) {
                adaptersByClass.put(adapter.getClass(), adapter);
            }
        }
        Log.d("MediationStatusScanner", "Configs: " + configs);
        Log.d("MediationStatusScanner", "Adapters: " + adaptersByClass);
        for (AdapterDescription description : adapterDescriptions) {
            NetworkStatus status = new NetworkStatus(description.name);
            status.setListInTestActivity(description.listInTestActivity.booleanValue());
            MediationAdapter adapter2 = adaptersByClass.get(description.adapterClass);
            Map<String, Object> config = configs == null ? null : configs.get(description.configName.toLowerCase());
            if (description.listInTestActivity.booleanValue()) {
                Log.d("MediationStatusScanner", "Network config for " + description.name + " : " + config);
            }
            status.setLocalStatus(description.adapterClass == null ? 0 : 1);
            status.setAdapterStatus(adapter2 == null ? 0 : 1);
            status.setRemoteStatus(config == null ? 0 : 1);
            status.setNetworkAdapter(adapter2);
            status.setConfigs(config);
            networkStatuses.add(status);
        }
        MediationStatus mediationStatus = new MediationStatus(networkStatuses, configs != null);
        return mediationStatus;
    }

    public static AbstractFuture<MediationStatus> getMediationStatusFuture() {
        Log.d("MediationStatusScanner", "getMediationStatusFuture - called");
        if (pollInitialized.compareAndSet(false, true) && !mediationStatusFuture.isDone()) {
            Log.d("MediationStatusScanner", "getMediationStatusFuture - scheduling poll");
            MediationAdapterStarter.delayedAdaptersInitialzedFuture.addListener(new Runnable() { // from class: com.fyber.mediation.test.data.MediationStatusScanner.1
                @Override // java.lang.Runnable
                public void run() {
                    ScheduledFuture unused = MediationStatusScanner.adapterPoll = ExecutorPool.getInstance().scheduleAtFixedRate(new Runnable() { // from class: com.fyber.mediation.test.data.MediationStatusScanner.1.1
                        @Override // java.lang.Runnable
                        public void run() {
                            Log.d("MediationStatusScanner", "Polling adapters");
                            if (MediationStatusScanner.getAdapters() != null) {
                                Log.d("MediationStatusScanner", "Adapters found, setting");
                                MediationStatusScanner.mediationStatusFuture.set(MediationStatusScanner.getMediationStatus());
                                if (MediationStatusScanner.adapterPoll != null) {
                                    MediationStatusScanner.adapterPoll.cancel(false);
                                }
                            }
                        }
                    }, 0L, 3L, TimeUnit.SECONDS);
                }
            }, ExecutorPool.getInstance());
        }
        return mediationStatusFuture;
    }

    public MediationStatus scan() {
        return null;
    }

    public static class AdapterDescription {
        public final Class<? extends MediationAdapter> adapterClass;
        public final String className;
        private final String configName;
        public final Boolean listInTestActivity;
        public final String name;

        private AdapterDescription(String className) {
            this(className, true);
        }

        private AdapterDescription(String className, String configName, boolean listInTestActivity) {
            String[] parts = className.split("\\.");
            String lastPart = parts[parts.length - 1];
            this.name = lastPart.replace("MediationAdapter", "");
            this.configName = configName == null ? this.name : configName;
            this.className = className;
            this.adapterClass = findClass(className);
            this.listInTestActivity = Boolean.valueOf(listInTestActivity);
        }

        public AdapterDescription(String className, boolean listInTestSuite) {
            this(className, null, listInTestSuite);
        }

        private static Class<? extends MediationAdapter> findClass(String className) {
            try {
                return Class.forName(className);
            } catch (ClassNotFoundException e) {
                Logger.error("Adapter not integrated: " + className);
                return null;
            }
        }
    }

    public static void addAdaptersByJson(String jsonString) throws JSONException {
        if (initializationStarted) {
            FyberLogger.d("FyberSDK", String.format("Adapters cannot be added, initialization has already begun", new Object[0]));
            return;
        }
        JSONObject json = new JSONObject(jsonString);
        JSONArray array = json.getJSONArray("adapters");
        for (int i = 0; i < array.length(); i++) {
            JSONObject object = array.getJSONObject(i);
            String className = object.getString("qualifiedName");
            adapterDescriptions.add(new AdapterDescription(className));
        }
    }

    public static void addAdapterByClassName(String className) {
        if (initializationStarted) {
            FyberLogger.d("FyberSDK", String.format("Adapter %s cannot be added, initialization has already begun", className));
        } else {
            adapterDescriptions.add(new AdapterDescription(className));
        }
    }
}
