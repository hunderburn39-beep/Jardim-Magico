package com.fyber.b;

import android.app.Activity;
import android.content.SharedPreferences;
import com.fyber.Fyber;
import com.fyber.utils.FyberLogger;
import com.fyber.utils.StringUtils;
import com.fyber.utils.s;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: compiled from: MediationConfigurationNetworkOperation.java */
/* JADX INFO: loaded from: classes.dex */
public final class f extends j<String, Map<String, Map<String, Object>>> {
    private SharedPreferences b;

    public static Future<Map<String, Map<String, Object>>> a(com.fyber.a.a aVar, Activity activity) {
        s sVarD = s.a(com.fyber.utils.e.a("config"), aVar).d();
        if (Fyber.getConfigs().d()) {
            return Fyber.getConfigs().a((Callable) new f(sVarD, aVar.c(), activity));
        }
        return null;
    }

    private f(s sVar, String str, Activity activity) {
        super(sVar, str);
        this.b = activity.getSharedPreferences("MediationConfigurationNetworkOperation", 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @Override // com.fyber.b.j
    /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
    public Map<String, Map<String, Object>> a(String str) {
        if (StringUtils.nullOrEmpty(str)) {
            FyberLogger.d("MediationConfigurationNetworkOperation", "No configs from the server, fallback to cached version.");
            str = this.b.getString("MediationConfigurationNetworkOperation", "");
            if (StringUtils.nullOrEmpty(str)) {
                FyberLogger.d("MediationConfigurationNetworkOperation", "There were no cached version to use.");
            } else {
                FyberLogger.d("MediationConfigurationNetworkOperation", "Using cached json file.");
            }
        }
        return c(str);
    }

    @Override // com.fyber.b.g
    protected final String a_() {
        return "MediationConfigurationNetworkOperation";
    }

    private static Map<String, Map<String, Object>> c(String str) {
        FyberLogger.d("MediationConfigurationNetworkOperation", "Reading config file");
        HashMap map = new HashMap();
        if (StringUtils.notNullNorEmpty(str)) {
            try {
                FyberLogger.d("MediationConfigurationNetworkOperation", "Parsing configurations");
                JSONArray jSONArray = new JSONObject(str).getJSONArray("adapters");
                for (int i = 0; i < jSONArray.length(); i++) {
                    JSONObject jSONObject = jSONArray.getJSONObject(i);
                    String lowerCase = jSONObject.getString("name").toLowerCase(Locale.ENGLISH);
                    if (jSONObject.has("settings")) {
                        JSONObject jSONObject2 = jSONObject.getJSONObject("settings");
                        HashMap map2 = new HashMap(jSONObject2.length());
                        Iterator<String> itKeys = jSONObject2.keys();
                        while (itKeys.hasNext()) {
                            String next = itKeys.next();
                            map2.put(next, jSONObject2.get(next));
                        }
                        map.put(lowerCase, map2);
                    } else {
                        map.put(lowerCase, Collections.emptyMap());
                    }
                }
                FyberLogger.d("MediationConfigurationNetworkOperation", "configuration loaded successfully");
            } catch (JSONException e) {
                FyberLogger.e("MediationConfigurationNetworkOperation", "A JSON error occurred while parsing the configuration file, there will be no mediation configurations.", e);
            }
        } else {
            FyberLogger.d("MediationConfigurationNetworkOperation", "Configuration was not found, there will be no mediation configurations.");
        }
        return map;
    }

    @Override // com.fyber.b.j
    /* JADX INFO: renamed from: a, reason: avoid collision after fix types in other method */
    protected final /* synthetic */ String a2(String str) {
        FyberLogger.d("MediationConfigurationNetworkOperation", "The signature is valid, proceeding...");
        if (!StringUtils.notNullNorEmpty(str)) {
            return "";
        }
        if (this.b.edit().putString("MediationConfigurationNetworkOperation", str).commit()) {
            FyberLogger.d("MediationConfigurationNetworkOperation", "Server Side Configuration has been saved successfully.");
            return str;
        }
        FyberLogger.d("MediationConfigurationNetworkOperation", "Failed to save Server Side Configuration.");
        return str;
    }

    @Override // com.fyber.b.j
    protected final /* synthetic */ String a(int i, String str, String str2) {
        if (!a(str, str2)) {
            FyberLogger.d("MediationConfigurationNetworkOperation", "Invalid signature, those configs will not be used.");
            return "";
        }
        return "";
    }

    @Override // com.fyber.b.g
    protected final /* synthetic */ Object a(IOException iOException) {
        FyberLogger.d("MediationConfigurationNetworkOperation", "Connection error - " + iOException.getMessage());
        return a("");
    }
}
