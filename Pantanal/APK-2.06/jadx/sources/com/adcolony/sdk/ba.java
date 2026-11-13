package com.adcolony.sdk;

import android.webkit.JavascriptInterface;

/* JADX INFO: loaded from: classes.dex */
class ba {
    cn a;

    public ba(cn cnVar) {
        this.a = null;
        this.a = cnVar;
    }

    @JavascriptInterface
    public void openCatalog(int callbackId) {
        cm.a(this.a, callbackId);
    }

    @JavascriptInterface
    public void openCatalogItem(String json, int callbackId) {
        cm.a(this.a, json, callbackId);
    }

    @JavascriptInterface
    public void openUrl(String json, int callbackId) {
        cm.a(json, callbackId);
    }

    @JavascriptInterface
    public void openIAP(int callbackId) {
        cm.b(this.a, callbackId);
    }

    @JavascriptInterface
    public void resetDeviceId(int callbackId) {
        cm.c(this.a, callbackId);
    }

    @JavascriptInterface
    public void logEvent(String json, int callbackId) {
        cm.b(this.a, json, callbackId);
    }

    @JavascriptInterface
    public void purgeFacebookCookiesCmd(int callbackId) {
        cm.d(this.a, callbackId);
    }

    @JavascriptInterface
    public void restoreNonConsumables(String args, int callbackId) {
        cm.c(this.a, args, callbackId);
    }

    @JavascriptInterface
    public void close(int callbackId) {
        cm.e(this.a, callbackId);
    }

    @JavascriptInterface
    public void consoleLog(String javascriptLog, int callbackId) {
        cm.d(this.a, javascriptLog, callbackId);
    }

    @JavascriptInterface
    public void setCloseButtonParams(String params, int callbackId) {
        cm.e(this.a, params, callbackId);
    }

    @JavascriptInterface
    public void suEraseBacklog(int callbackId) {
        cm.f(this.a, callbackId);
    }

    @JavascriptInterface
    public void suEraseAssets(int callbackId) {
        cm.g(this.a, callbackId);
    }

    @JavascriptInterface
    public void suEraseAll(int callbackId) {
        cm.h(this.a, callbackId);
    }

    @JavascriptInterface
    public void suInitialize(int callbackId) {
        cm.i(this.a, callbackId);
    }

    @JavascriptInterface
    public void suFlushLogs(int callbackId) {
        cm.j(this.a, callbackId);
    }

    @JavascriptInterface
    public void suRefreshDevice(int callbackId) {
        cm.k(this.a, callbackId);
    }

    @JavascriptInterface
    public void suGrantCustomReward(String rewardId, int callbackId) {
        cm.f(this.a, rewardId, callbackId);
    }

    @JavascriptInterface
    public void suSendCommand(String rewardId, int callbackId) {
        cm.f(this.a, rewardId, callbackId);
    }

    @JavascriptInterface
    public void suReInit(String json, int callbackId) {
        cm.g(this.a, json, callbackId);
    }

    @JavascriptInterface
    public void getServerRewardQueue(int callbackId) {
        cm.l(this.a, callbackId);
    }

    @JavascriptInterface
    public void claimServerReward(String transactionId, int callbackId) {
        cm.h(this.a, transactionId, callbackId);
    }

    @JavascriptInterface
    public void startPurchaseDigitalItem(String jsonArray, int callbackId) {
        cm.i(this.a, jsonArray, callbackId);
    }

    @JavascriptInterface
    public void purchaseDigitalItem(String jsonArray, String signature, int callbackId) {
        cm.a(this.a, jsonArray, signature, callbackId);
    }

    @JavascriptInterface
    public void getValueFromClientStorage(String value, int callbackId) {
        cm.j(this.a, value, callbackId);
    }

    @JavascriptInterface
    public void setValueFromClientStorage(String value, int callbackId) {
        cm.k(this.a, value, callbackId);
    }

    @JavascriptInterface
    public void removeValueFromClientStorage(String value, int callbackId) {
        cm.l(this.a, value, callbackId);
    }

    @JavascriptInterface
    public void clearClientStorage(int callbackId) {
        cm.n(this.a, callbackId);
    }
}
