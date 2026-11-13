package com.r7games.slotpantanal.google.services;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import com.google.android.gms.common.api.GoogleApiClient;
import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.ui.activity.LayoutGameActivity;

/* JADX INFO: loaded from: classes.dex */
public class GBaseGameActivity extends LayoutGameActivity implements GGameHelper.GameHelperListener {
    public static final int CLIENT_ALL = 7;
    public static final int CLIENT_APPSTATE = 4;
    public static final int CLIENT_GAMES = 1;
    public static final int CLIENT_PLUS = 2;
    private static final String TAG = "BaseGameActivity";
    protected GGameHelper mHelper;
    protected int mRequestedClients = 1;
    protected boolean mDebugLog = false;

    protected GBaseGameActivity() {
    }

    protected GBaseGameActivity(int requestedClients) {
        setRequestedClients(requestedClients);
    }

    protected void setRequestedClients(int requestedClients) {
        this.mRequestedClients = requestedClients;
    }

    public GGameHelper getGameHelper() {
        if (this.mHelper == null) {
            this.mHelper = new GGameHelper(this, this.mRequestedClients);
            this.mHelper.enableDebugLog(this.mDebugLog);
        }
        return this.mHelper;
    }

    @Override // org.anddev.andengine.ui.activity.BaseGameActivity, android.app.Activity
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        if (this.mHelper == null) {
            getGameHelper();
        }
        this.mHelper.setup(this);
    }

    @Override // android.app.Activity
    protected void onStart() {
        super.onStart();
        this.mHelper.onStart(this);
    }

    @Override // android.app.Activity
    protected void onStop() {
        super.onStop();
        this.mHelper.onStop();
    }

    @Override // android.app.Activity
    protected void onActivityResult(int request, int response, Intent data) {
        super.onActivityResult(request, response, data);
        this.mHelper.onActivityResult(request, response, data);
    }

    protected GoogleApiClient getApiClient() {
        return this.mHelper.getApiClient();
    }

    protected boolean isSignedIn() {
        return this.mHelper.isSignedIn();
    }

    protected void beginUserInitiatedSignIn() {
        this.mHelper.beginUserInitiatedSignIn();
    }

    protected void signOut() {
        this.mHelper.signOut();
    }

    protected void showAlert(String message) {
        this.mHelper.makeSimpleDialog(message).show();
    }

    protected void showAlert(String title, String message) {
        this.mHelper.makeSimpleDialog(title, message).show();
    }

    protected void enableDebugLog(boolean enabled) {
        this.mDebugLog = true;
        if (this.mHelper != null) {
            this.mHelper.enableDebugLog(enabled);
        }
    }

    @Deprecated
    protected void enableDebugLog(boolean enabled, String tag) {
        Log.w(TAG, "BaseGameActivity.enabledDebugLog(bool,String) is deprecated. Use enableDebugLog(boolean)");
        enableDebugLog(enabled);
    }

    protected String getInvitationId() {
        return this.mHelper.getInvitationId();
    }

    protected void reconnectClient() {
        this.mHelper.reconnectClient();
    }

    protected boolean hasSignInError() {
        return this.mHelper.hasSignInError();
    }

    protected GGameHelper.SignInFailureReason getSignInError() {
        return this.mHelper.getSignInError();
    }

    public void onSignInFailed() {
    }

    public void onSignInSucceeded() {
    }

    public void onLoadComplete() {
    }

    public Engine onLoadEngine() {
        return null;
    }

    public void onLoadResources() {
    }

    public Scene onLoadScene() {
        return null;
    }

    @Override // org.anddev.andengine.ui.activity.LayoutGameActivity
    protected int getLayoutID() {
        return 0;
    }

    @Override // org.anddev.andengine.ui.activity.LayoutGameActivity
    protected int getRenderSurfaceViewID() {
        return 0;
    }
}
