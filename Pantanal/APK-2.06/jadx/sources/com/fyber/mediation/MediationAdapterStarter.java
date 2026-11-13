package com.fyber.mediation;

import android.app.Activity;
import android.util.Log;
import com.fyber.mediation.test.data.MediationStatusScanner;
import com.fyber.utils.FyberLogger;
import com.heyzap.common.concurrency.ExecutorPool;
import com.heyzap.common.concurrency.FutureUtils;
import com.heyzap.common.concurrency.SettableFuture;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/* JADX INFO: loaded from: classes.dex */
public class MediationAdapterStarter {
    private static final String TAG = "MediationAdapterStarter";
    private static final Set<Class<? extends MediationAdapter>> adapterClasses = new HashSet();
    public static final SettableFuture<Void> adaptersInitializedFuture = SettableFuture.create();
    public static SettableFuture<Void> delayedAdaptersInitialzedFuture = FutureUtils.addDelay(adaptersInitializedFuture, ExecutorPool.getInstance(), 1, TimeUnit.SECONDS);
    private static AtomicReference<Future> initFutureRef = new AtomicReference<>(null);

    public static void init() {
        SettableFuture<Void> initFuture = SettableFuture.create();
        if (initFutureRef.compareAndSet(null, initFuture)) {
            MediationStatusScanner.initializationStarted = true;
            Log.d("MediationTestActivity", "init - called");
            for (MediationStatusScanner.AdapterDescription adapterDescription : MediationStatusScanner.getAdapterClasses()) {
                if (adapterDescription != null) {
                    adapterClasses.add(adapterDescription.adapterClass);
                }
            }
            if (adapterClasses.size() == 0) {
                adaptersInitializedFuture.set(null);
            }
            initFuture.set(null);
            return;
        }
        try {
            initFutureRef.get().get();
        } catch (Exception e) {
            Log.d(TAG, "init aborted: " + e);
        }
    }

    public static int getAdaptersCount() {
        init();
        return adapterClasses.size();
    }

    public static Map<String, MediationAdapter> startAdapters(Activity activity, Future<Map<String, Map<String, Object>>> futureConfigs) {
        init();
        Log.d("MediationTestActivity", "startAdapters - called");
        Map<String, Map<String, Object>> configs = getConfigs(futureConfigs);
        Map<String, MediationAdapter> map = new HashMap<>();
        for (MediationStatusScanner.AdapterDescription adapterDescription : MediationStatusScanner.getAdapterClasses()) {
            String className = adapterDescription.className;
            if (adapterDescription.adapterClass == null) {
                if (adapterDescription.listInTestActivity.booleanValue()) {
                    FyberLogger.d(TAG, "No adapter found for network " + adapterDescription.name);
                }
            } else {
                try {
                    MediationAdapter adapter = adapterDescription.adapterClass.newInstance();
                    String version = adapter.getVersion();
                    String name = adapter.getName();
                    Map<String, Object> networkConfig = getConfigs(name, configs);
                    if (networkConfig == null) {
                        networkConfig = new HashMap<>();
                    }
                    FyberLogger.d(TAG, String.format("Starting adapter %s version %s", name, version));
                    if (adapter.startAdapter(activity, networkConfig)) {
                        FyberLogger.d(TAG, "Adapter has been started successfully");
                        map.put(name, adapter);
                    } else {
                        FyberLogger.w(TAG, "Warning - the adapter has NOT been started.");
                    }
                } catch (IllegalAccessException e) {
                    FyberLogger.e(TAG, "An error occurred", e);
                } catch (InstantiationException e2) {
                    FyberLogger.e(TAG, "An error occurred while trying to instantiate " + className, e2);
                } catch (Throwable throwable) {
                    FyberLogger.e(TAG, "An error occurred while trying to instantiate " + className + "\n" + throwable.getMessage());
                }
            }
        }
        FyberLogger.d(TAG, "Initialization complete...");
        adaptersInitializedFuture.set(null);
        Log.d("MediationTestActivity", "startAdapters - finished");
        return map;
    }

    private static boolean hasConfigs(String name, Map<String, Map<String, Object>> configs) {
        return false;
    }

    private static Map<String, Map<String, Object>> mergeConfigs(Map<String, Map<String, Object>> intoConfigs, Map<String, Map<String, Object>> fromConfigs) {
        if (fromConfigs != null && !fromConfigs.isEmpty()) {
            for (Map.Entry<String, Map<String, Object>> entry : fromConfigs.entrySet()) {
                String network = entry.getKey();
                Map<String, Object> adapterIntoConfigs = entry.getValue();
                Map<String, Object> adapterFromConfigs = intoConfigs.get(network);
                if (adapterFromConfigs != null) {
                    adapterIntoConfigs.putAll(adapterFromConfigs);
                }
                intoConfigs.put(network, adapterIntoConfigs);
            }
        } else {
            FyberLogger.d(TAG, "There were no configurations to override");
        }
        return intoConfigs;
    }

    private static Map<String, Object> getConfigs(String name, Map<String, Map<String, Object>> configs) {
        String lowerCasedName = name.toLowerCase();
        if (configs.containsKey(lowerCasedName)) {
            return configs.get(lowerCasedName);
        }
        return null;
    }

    private static Map<String, Map<String, Object>> getConfigs(Future<Map<String, Map<String, Object>>> futureConfig) {
        Map<String, Map<String, Object>> configs = MediationConfigProvider.getConfigs();
        Map<String, Map<String, Object>> runtimeConfigs = MediationConfigProvider.getRuntimeConfigs();
        if (runtimeConfigs != null) {
            configs = mergeConfigs(configs, runtimeConfigs);
        }
        if (futureConfig != null) {
            try {
                Map<String, Map<String, Object>> serverConfigs = futureConfig.get();
                configs = mergeConfigs(configs, serverConfigs);
                MediationStatusScanner.setConfigs(configs);
                return configs;
            } catch (InterruptedException | ExecutionException e) {
                FyberLogger.e(TAG, "Exception occurred", e);
                return configs;
            }
        }
        return configs;
    }
}
