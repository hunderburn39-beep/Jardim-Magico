package com.fyber.utils;

import java.util.HashMap;
import java.util.Map;

/* JADX INFO: compiled from: FyberBaseUrlProvider.java */
/* JADX INFO: loaded from: classes.dex */
public final class e {
    private static e a = new e();
    private t b;
    private Map<String, String> c = new HashMap<String, String>() { // from class: com.fyber.utils.e.1
        {
            put("actions", "https://service.fyber.com/actions/v2");
            put("installs", "https://service.fyber.com/installs/v2");
            put("vcs", "https://api.fyber.com/vcs/v1/new_credit.json");
            put("videos", "https://video.fyber.com");
            put("ofw", "https://offer.fyber.com/mobile");
            put("interstitial", "https://interstitial.fyber.com/interstitial");
            put("interstitial_tracking", "https://interstitial.fyber.com/tracker");
            put("config", "https://engine.fyber.com/sdk-config");
            put("precaching", "https://engine.fyber.com/video-cache");
            put("banner", "https://banner.fyber.com/banner");
            put("banner_tracking", "https://banner.fyber.com/tracker");
        }
    };

    private e() {
    }

    public static String a(String str) {
        e eVar = a;
        String strA = null;
        if (eVar.b != null) {
            strA = eVar.b.a();
        }
        if (!StringUtils.nullOrEmpty(strA)) {
            return strA;
        }
        return eVar.c.get(str);
    }
}
