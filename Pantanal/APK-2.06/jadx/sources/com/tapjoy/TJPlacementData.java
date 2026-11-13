package com.tapjoy;

import com.tapjoy.internal.cr;
import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class TJPlacementData implements Serializable {
    private String a;
    private String b;
    private String c;
    private String d;
    private String e;
    private int f;
    private String g;
    private int h;
    private boolean i;
    private String j;
    private boolean k;
    private String l;
    private String m;
    private boolean n;
    private boolean o;

    public TJPlacementData(String key, String url) {
        this.n = true;
        this.o = false;
        setKey(key);
        updateUrl(url);
    }

    public TJPlacementData(String baseUrl, String httpResponse, String callbackID) {
        this.n = true;
        this.o = false;
        setBaseURL(baseUrl);
        setHttpResponse(httpResponse);
        this.l = callbackID;
        this.n = false;
    }

    public void resetPlacementRequestData() {
        setHttpResponse(null);
        setHttpStatusCode(0);
        setRedirectURL(null);
        setHasProgressSpinner(false);
        setPrerenderingRequested(false);
        setPreloadDisabled(false);
        setContentViewId(null);
    }

    public String getCallbackID() {
        return this.l;
    }

    public boolean isBaseActivity() {
        return this.n;
    }

    public void setKey(String key) {
        this.a = key;
    }

    public void setBaseURL(String baseURL) {
        this.c = baseURL;
    }

    public void setMediationURL(String mediationURL) {
        this.d = mediationURL;
    }

    public void setHttpResponse(String httpResponse) {
        this.e = httpResponse;
    }

    public void setHttpStatusCode(int httpStatusCode) {
        this.f = httpStatusCode;
    }

    public void setPlacementName(String placementName) {
        this.g = placementName;
    }

    public void setViewType(int viewType) {
        this.h = viewType;
    }

    public void setRedirectURL(String redirectURL) {
        this.j = redirectURL;
    }

    public void setHasProgressSpinner(boolean hasProgressSpinner) {
        this.i = hasProgressSpinner;
    }

    public void setContentViewId(String contentViewId) {
        this.m = contentViewId;
    }

    public String getUrl() {
        return this.b;
    }

    public String getKey() {
        return this.a;
    }

    public String getBaseURL() {
        return this.c;
    }

    public String getMediationURL() {
        return this.d;
    }

    public String getHttpResponse() {
        return this.e;
    }

    public int getHttpStatusCode() {
        return this.f;
    }

    public String getPlacementName() {
        return this.g;
    }

    public int getViewType() {
        return this.h;
    }

    public String getRedirectURL() {
        return this.j;
    }

    public String getContentViewId() {
        return this.m;
    }

    public boolean hasProgressSpinner() {
        return this.i;
    }

    public void setPreloadDisabled(boolean value) {
        this.o = value;
    }

    public boolean isPreloadDisabled() {
        return this.o;
    }

    public boolean isPrerenderingRequested() {
        return this.k;
    }

    public void setPrerenderingRequested(boolean prerenderingRequested) {
        this.k = prerenderingRequested;
    }

    public void updateUrl(String url) {
        this.b = url;
        if (!cr.c(url)) {
            setBaseURL(url.substring(0, url.indexOf(47, url.indexOf("//") + 3)));
        }
    }
}
