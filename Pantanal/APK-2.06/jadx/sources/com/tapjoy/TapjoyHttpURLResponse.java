package com.tapjoy;

import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class TapjoyHttpURLResponse {
    public int contentLength;
    public long date;
    public long expires;
    public Map headerFields;
    public String redirectURL;
    public String response;
    public int statusCode;

    public String getHeaderFieldAsString(String headerKey) {
        List list;
        if (this.headerFields == null || (list = (List) this.headerFields.get(headerKey)) == null || list.get(0) == null) {
            return "";
        }
        return (String) list.get(0);
    }
}
