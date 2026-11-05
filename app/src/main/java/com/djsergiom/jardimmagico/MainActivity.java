package com.djsergiom.jardimmagico;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity {
    private WebView game;
    @Override public void onCreate(Bundle state) {
        super.onCreate(state);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        game = new WebView(this);
        WebSettings prefs = game.getSettings();
        prefs.setJavaScriptEnabled(true);
        prefs.setDomStorageEnabled(true);
        prefs.setAllowFileAccess(true);
        prefs.setMediaPlaybackRequiresUserGesture(false);
        game.setWebChromeClient(new WebChromeClient());
        game.setWebViewClient(new WebViewClient());
        setContentView(game);
        game.loadUrl("file:///android_asset/index.html");
    }
    @Override public void onBackPressed() {
        if(game != null && game.canGoBack()) game.goBack();
        else super.onBackPressed();
    }
    @Override protected void onDestroy() {
        if(game != null) { game.destroy(); game = null; }
        super.onDestroy();
    }
}
