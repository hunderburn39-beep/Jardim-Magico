package org.anddev.andengine.util.levelstats;

import android.content.Context;
import android.content.SharedPreferences;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.anddev.andengine.util.Callback;
import org.anddev.andengine.util.Debug;
import org.anddev.andengine.util.MathUtils;
import org.anddev.andengine.util.SimplePreferences;
import org.anddev.andengine.util.StreamUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

/* JADX INFO: loaded from: classes.dex */
public class LevelStatsDBConnector {
    private static final String PREFERENCES_LEVELSTATSDBCONNECTOR_PLAYERID_ID = "preferences.levelstatsdbconnector.playerid";
    private final int mPlayerID;
    private final String mSecret;
    private final String mSubmitURL;

    public LevelStatsDBConnector(Context pContext, String pSecret, String pSubmitURL) {
        this.mSecret = pSecret;
        this.mSubmitURL = pSubmitURL;
        SharedPreferences simplePreferences = SimplePreferences.getInstance(pContext);
        int playerID = simplePreferences.getInt(PREFERENCES_LEVELSTATSDBCONNECTOR_PLAYERID_ID, -1);
        if (playerID != -1) {
            this.mPlayerID = playerID;
        } else {
            this.mPlayerID = MathUtils.random(1000000000, Integer.MAX_VALUE);
            SimplePreferences.getEditorInstance(pContext).putInt(PREFERENCES_LEVELSTATSDBCONNECTOR_PLAYERID_ID, this.mPlayerID).commit();
        }
    }

    public void submitAsync(int pLevelID, boolean pSolved, int pSecondsElapsed) {
        submitAsync(pLevelID, pSolved, pSecondsElapsed, null);
    }

    public void submitAsync(final int pLevelID, final boolean pSolved, final int pSecondsElapsed, final Callback<Boolean> pCallback) {
        new Thread(new Runnable() { // from class: org.anddev.andengine.util.levelstats.LevelStatsDBConnector.1
            @Override // java.lang.Runnable
            public void run() {
                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(LevelStatsDBConnector.this.mSubmitURL);
                    List<NameValuePair> nameValuePairs = new ArrayList<>(5);
                    nameValuePairs.add(new BasicNameValuePair("level_id", String.valueOf(pLevelID)));
                    nameValuePairs.add(new BasicNameValuePair("solved", pSolved ? "1" : "0"));
                    nameValuePairs.add(new BasicNameValuePair("secondsplayed", String.valueOf(pSecondsElapsed)));
                    nameValuePairs.add(new BasicNameValuePair("player_id", String.valueOf(LevelStatsDBConnector.this.mPlayerID)));
                    nameValuePairs.add(new BasicNameValuePair("secret", String.valueOf(LevelStatsDBConnector.this.mSecret)));
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    HttpResponse httpResponse = httpClient.execute(httpPost);
                    int statusCode = httpResponse.getStatusLine().getStatusCode();
                    if (statusCode == 200) {
                        String response = StreamUtils.readFully(httpResponse.getEntity().getContent());
                        if (response.equals("<success/>")) {
                            if (pCallback != null) {
                                pCallback.onCallback(true);
                            }
                        } else if (pCallback != null) {
                            pCallback.onCallback(false);
                        }
                    } else if (pCallback != null) {
                        pCallback.onCallback(false);
                    }
                } catch (IOException e) {
                    Debug.e(e);
                    if (pCallback != null) {
                        pCallback.onCallback(false);
                    }
                }
            }
        }).start();
    }
}
