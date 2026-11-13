package com.heyzap.wrapper.credentials;

import android.content.Context;
import android.content.SharedPreferences;
import com.heyzap.common.concurrency.ExecutorPool;
import com.heyzap.common.concurrency.FutureHandler;
import com.heyzap.common.concurrency.SettableFuture;
import com.heyzap.common.net.APIClient;
import com.heyzap.http.TextHttpResponseHandler;
import com.heyzap.internal.HeaderActions;
import com.heyzap.internal.Logger;
import com.heyzap.internal.RetryManager;
import com.heyzap.internal.Utils;
import com.tapjoy.TapjoyConstants;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class CredentialsLocator {
    final Context appContext;
    private HeaderActions headerActions;

    public CredentialsLocator(Context appContext) {
        this.appContext = appContext;
    }

    public SettableFuture<Credentials> locate() {
        final SettableFuture<Credentials> retFuture = SettableFuture.create();
        locateRemote().addHandler(new FutureHandler<Credentials, Object>() { // from class: com.heyzap.wrapper.credentials.CredentialsLocator.1
            @Override // com.heyzap.common.concurrency.FutureHandler
            public Object handle(Credentials value, Exception exception) {
                if (value != null) {
                    retFuture.set(value);
                    CredentialsLocator.this.saveLocal(value);
                    return null;
                }
                return null;
            }
        }, ExecutorPool.getInstance());
        return retFuture;
    }

    public SettableFuture<Credentials> locateRemote() {
        final SettableFuture<Credentials> retFuture = SettableFuture.create();
        final String platform = Utils.isAmazon() ? "amazon" : TapjoyConstants.TJC_DEVICE_PLATFORM_TYPE;
        final String packageName = Utils.getPackageName(this.appContext);
        this.headerActions = new HeaderActions(this.appContext);
        new RetryManager(new RetryManager.RetryableTask() { // from class: com.heyzap.wrapper.credentials.CredentialsLocator.2
            @Override // java.lang.Runnable
            public void run() {
                APIClient.get(CredentialsLocator.this.appContext, "http://fyc.heyzap.com/fyber/credentials/" + platform + "/" + packageName, new TextHttpResponseHandler() { // from class: com.heyzap.wrapper.credentials.CredentialsLocator.2.1
                    @Override // com.heyzap.http.TextHttpResponseHandler
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        Logger.debug("CredentialsLocator failed to find credentials, will retry later. Status: " + statusCode + " Message: " + responseString);
                        CredentialsLocator.this.headerActions.onHeaders(headers);
                        retry();
                    }

                    @Override // com.heyzap.http.TextHttpResponseHandler
                    public void onSuccess(int statusCode, Header[] headers, String responseString) {
                        if (statusCode != 200) {
                            Logger.debug("CredentialsLocator failed to find credentials, will retry later. Status: " + statusCode + " Message: " + responseString);
                            retry();
                        } else {
                            try {
                                JSONObject response = new JSONObject(responseString);
                                String appId = response.getString(TapjoyConstants.TJC_APP_ID);
                                String securityToken = response.getString("security_token");
                                JSONArray disabledTagsArray = response.optJSONArray("disabled_ad_tags");
                                Set<String> disabledTags = new HashSet<>();
                                if (disabledTagsArray != null) {
                                    for (int i = 0; i < disabledTagsArray.length(); i++) {
                                        disabledTags.add(disabledTagsArray.getString(i));
                                    }
                                }
                                retFuture.set(new Credentials(appId, securityToken, disabledTags));
                            } catch (JSONException e) {
                                Logger.debug("CredentialsLocator failed to parse credentials. Response: " + responseString + " Error: " + e.getMessage());
                            }
                        }
                        CredentialsLocator.this.headerActions.onHeaders(headers);
                    }
                });
            }
        }, new RetryManager.ExponentialSchedule(2.0d, 2L, TimeUnit.SECONDS), ExecutorPool.getInstance()).start();
        return retFuture;
    }

    public Credentials locateLocal() {
        SharedPreferences prefs = this.appContext.getSharedPreferences("credentials", 0);
        String appId = prefs.getString("appId", null);
        String securityToken = prefs.getString("securityToken", null);
        Set<String> disabledTags = prefs.getStringSet("disabledTags", new HashSet());
        if (appId == null || securityToken == null) {
            return null;
        }
        return new Credentials(appId, securityToken, disabledTags);
    }

    public void saveLocal(Credentials credentials) {
        SharedPreferences prefs = this.appContext.getSharedPreferences("credentials", 0);
        prefs.edit().putString("appId", credentials.appId).putString("securityToken", credentials.securityToken).putStringSet("disabledTags", credentials.disabledTags).commit();
    }
}
