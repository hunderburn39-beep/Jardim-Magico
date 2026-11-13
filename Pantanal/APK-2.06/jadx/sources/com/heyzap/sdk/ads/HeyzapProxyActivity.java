package com.heyzap.sdk.ads;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.heyzap.internal.ProxyActivity;

/* JADX INFO: loaded from: classes.dex */
public class HeyzapProxyActivity extends Activity {
    public static ProxyActivity SHADOW_CONTEXT = null;

    @Override // android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SHADOW_CONTEXT.onCreate(savedInstanceState);
        Intent intent = getIntent();
        Intent parentIntent = (Intent) intent.getExtras().getParcelable("parent_intent");
        Integer parentRequestCode = Integer.valueOf(intent.getExtras().getInt("parent_request_code"));
        startActivityForResult(parentIntent, parentRequestCode.intValue());
    }

    @Override // android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        SHADOW_CONTEXT.onActivityResult(requestCode, resultCode, data);
        SHADOW_CONTEXT = null;
        setResult(resultCode, data);
        finish();
    }

    @Override // android.app.Activity
    public void onBackPressed() {
        super.onBackPressed();
        SHADOW_CONTEXT.onBackPressed();
    }
}
