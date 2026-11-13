package com.tapjoy;

import android.app.Activity;
import android.content.Context;
import android.opengl.GLSurfaceView;
import com.tapjoy.internal.ed;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public final class Tapjoy {
    public static final String INTENT_EXTRA_PUSH_PAYLOAD = "com.tapjoy.PUSH_PAYLOAD";

    public static String getVersion() {
        return ed.a().b();
    }

    public static void setDebugEnabled(boolean enable) {
        ed.a().a(enable);
    }

    public static boolean connect(Context context, String sdkKey) {
        return ed.a().a(context, sdkKey);
    }

    public static boolean connect(Context context, String sdkKey, Hashtable hashtable) {
        return ed.a().a(context, sdkKey, hashtable, (TJConnectListener) null);
    }

    public static synchronized boolean connect(Context applicationContext, String sdkKey, Hashtable hashtable, TJConnectListener publisherListener) {
        return ed.a().a(applicationContext, sdkKey, hashtable, publisherListener);
    }

    public static TJPlacement getPlacement(String placementName, TJPlacementListener listener) {
        return ed.a().a(placementName, listener);
    }

    public static void setActivity(Activity activity) {
        ed.a().a(activity);
    }

    public static void getCurrencyBalance(TJGetCurrencyBalanceListener listener) {
        ed.a().a(listener);
    }

    public static void spendCurrency(int amount, TJSpendCurrencyListener listener) {
        ed.a().a(amount, listener);
    }

    public static void awardCurrency(int amount, TJAwardCurrencyListener listener) {
        ed.a().a(amount, listener);
    }

    public static void setEarnedCurrencyListener(TJEarnedCurrencyListener listener) {
        ed.a().a(listener);
    }

    @Deprecated
    public static void setCurrencyMultiplier(float multiplier) {
        ed.a().a(multiplier);
    }

    @Deprecated
    public static float getCurrencyMultiplier() {
        return ed.a().c();
    }

    public static void trackPurchase(String productId, String currencyCode, double price, String campaignId) {
        ed.a().a(productId, currencyCode, price, campaignId);
    }

    public static void trackPurchase(String skuDetails, String purchaseData, String dataSignature, String campaignId) {
        ed.a().a(skuDetails, purchaseData, dataSignature, campaignId);
    }

    @Deprecated
    public static void trackPurchase(String skuDetails, String campaignId) {
        ed.a().a(skuDetails, campaignId);
    }

    public static void trackEvent(String name) {
        ed.a().a(name);
    }

    public static void trackEvent(String name, long value) {
        ed.a().a(name, value);
    }

    public static void trackEvent(String category, String name, long value) {
        ed.a().a(category, name, value);
    }

    public static void trackEvent(String category, String name, String parameter1, String parameter2) {
        ed.a().b(category, name, parameter1, parameter2);
    }

    public static void trackEvent(String category, String name, String parameter1, String parameter2, long value) {
        ed.a().a(category, name, parameter1, parameter2, value);
    }

    public static void trackEvent(String category, String name, String parameter1, String parameter2, String valueName, long value) {
        ed.a().a(category, name, parameter1, parameter2, valueName, value);
    }

    public static void trackEvent(String category, String name, String parameter1, String parameter2, String value1Name, long value1, String value2Name, long value2) {
        ed.a().a(category, name, parameter1, parameter2, value1Name, value1, value2Name, value2);
    }

    public static void trackEvent(String category, String name, String parameter1, String parameter2, String value1Name, long value1, String value2Name, long value2, String value3Name, long value3) {
        ed.a().a(category, name, parameter1, parameter2, value1Name, value1, value2Name, value2, value3Name, value3);
    }

    public static void trackEvent(String category, String name, String parameter1, String parameter2, Map map) {
        ed.a().a(category, name, parameter1, parameter2, map);
    }

    public static void startSession() {
        ed.a().d();
    }

    public static void endSession() {
        ed.a().e();
    }

    public static void onActivityStart(Activity activity) {
        ed.a().b(activity);
    }

    public static void onActivityStop(Activity activity) {
        ed.a().c(activity);
    }

    public static void setUserID(String userID) {
        setUserID(userID, null);
    }

    public static void setUserID(String userID, TJSetUserIDListener listener) {
        ed.a().a(userID, listener);
    }

    public static void setUserLevel(int userLevel) {
        ed.a().a(userLevel);
    }

    public static void setUserFriendCount(int friendCount) {
        ed.a().b(friendCount);
    }

    public static void setAppDataVersion(String dataVersion) {
        ed.a().b(dataVersion);
    }

    public static void setUserCohortVariable(int variableIndex, String value) {
        ed.a().a(variableIndex, value);
    }

    public static Set getUserTags() {
        return ed.a().f();
    }

    public static void setUserTags(Set set) {
        ed.a().a(set);
    }

    public static void clearUserTags() {
        ed.a().g();
    }

    public static void addUserTag(String tag) {
        ed.a().c(tag);
    }

    public static void removeUserTag(String tag) {
        ed.a().d(tag);
    }

    public static void setVideoListener(TJVideoListener listener) {
        ed.a().a(listener);
    }

    public static void enablePaidAppWithActionID(String paidAppPayPerActionID) {
        ed.a().e(paidAppPayPerActionID);
    }

    public static void actionComplete(String actionID) {
        ed.a().f(actionID);
    }

    public static void setGcmSender(String senderId) {
        ed.a().g(senderId);
    }

    public static boolean isPushNotificationDisabled() {
        return ed.a().h();
    }

    public static void setPushNotificationDisabled(boolean disabled) {
        ed.a().b(disabled);
    }

    public static void loadSharedLibrary() {
        try {
            System.loadLibrary("tapjoy");
        } catch (UnsatisfiedLinkError e) {
        }
    }

    public static void setGLSurfaceView(GLSurfaceView glSurfaceView) {
        ed.a().a(glSurfaceView);
    }

    public static boolean isConnected() {
        return ed.a().i();
    }
}
