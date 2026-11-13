package com.fyber.mediation;

import com.fyber.utils.FyberLogger;
import com.fyber.utils.StringUtils;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class MediationConfigProvider {
    private static final String TAG = "MediationConfigProvider";
    private static String jsonString;
    private static Map<String, Map<String, Object>> runtimeConfigs = new HashMap();

    public static void setup(String json) {
        jsonString = json;
    }

    public static Map<String, Map<String, Object>> getRuntimeConfigs() {
        return runtimeConfigs;
    }

    public static Map<String, Map<String, Object>> getConfigs() {
        FyberLogger.d(TAG, "Reading config file");
        Map<String, Map<String, Object>> configurations = new HashMap<>();
        if (StringUtils.notNullNorEmpty(jsonString)) {
            try {
                FyberLogger.d(TAG, "Parsing configurations");
                JSONObject json = new JSONObject(jsonString);
                JSONArray configs = json.getJSONArray("adapters");
                for (int i = 0; i < configs.length(); i++) {
                    JSONObject config = configs.getJSONObject(i);
                    String adapterName = config.getString("name").toLowerCase();
                    if (config.has("settings")) {
                        JSONObject settings = config.getJSONObject("settings");
                        Map<String, Object> map = new HashMap<>(settings.length());
                        Iterator<String> itr = settings.keys();
                        while (itr.hasNext()) {
                            String key = itr.next();
                            Object value = settings.get(key);
                            map.put(key, value);
                        }
                        configurations.put(adapterName, map);
                    } else {
                        configurations.put(adapterName, Collections.emptyMap());
                    }
                }
                FyberLogger.d(TAG, "configuration loaded successfully");
            } catch (JSONException e) {
                FyberLogger.e(TAG, "A JSON error occurred while parsing the configuration file, there will be no mediation configurations.", e);
            }
        } else {
            FyberLogger.d(TAG, "Configuration was not found, there will be no mediation configurations.");
        }
        return configurations;
    }

    public static void addRuntimeConfigs(String adapterName, String key, Object value) {
        Map<String, Object> adapterExtras = runtimeConfigs.get(adapterName);
        if (adapterExtras == null) {
            adapterExtras = new HashMap<>();
            runtimeConfigs.put(adapterName, adapterExtras);
        }
        adapterExtras.put(key, value);
    }
}
