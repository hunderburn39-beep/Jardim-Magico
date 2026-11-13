package com.adcolony.sdk;

import java.util.List;
import java.util.Map;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
class l implements ADCDownload.Listener {
    l() {
    }

    public void a() {
        a.a("WebServices.download", new ac() { // from class: com.adcolony.sdk.l.1
            @Override // com.adcolony.sdk.ac
            public void a(z zVar) {
                new ADCDownload(zVar, l.this);
            }
        });
        a.a("WebServices.get", new ac() { // from class: com.adcolony.sdk.l.2
            @Override // com.adcolony.sdk.ac
            public void a(z zVar) {
                new ADCDownload(zVar, l.this);
            }
        });
        a.a("WebServices.post", new ac() { // from class: com.adcolony.sdk.l.3
            @Override // com.adcolony.sdk.ac
            public void a(z zVar) {
                new ADCDownload(zVar, l.this);
            }
        });
    }

    @Override // com.adcolony.sdk.ADCDownload.Listener
    public void on_download_finished(ADCDownload download, z message, Map<String, List<String>> headers) {
        JSONObject jSONObjectA = s.a();
        s.a(jSONObjectA, "url", download.a);
        s.a(jSONObjectA, "success", download.e);
        s.b(jSONObjectA, "status", download.g);
        s.a(jSONObjectA, "body", download.b);
        s.b(jSONObjectA, "size", download.f);
        if (headers != null) {
            JSONObject jSONObjectA2 = s.a();
            for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
                String string = entry.getValue().toString();
                String strSubstring = string.substring(1, string.length() - 1);
                if (entry.getKey() != null) {
                    s.a(jSONObjectA2, entry.getKey(), strSubstring);
                }
            }
            s.a(jSONObjectA, "headers", jSONObjectA2);
        }
        message.a(jSONObjectA).b();
        download.h.shutdown();
    }
}
