package com.adcolony.sdk;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import com.google.android.gms.drive.DriveFile;
import java.util.Collections;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class MessagingLaunchActivity extends Activity {
    private static final String a = "MessagingLaunchActivity";

    @Override // android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        bw.b(a, "onCreate ->" + intent, true);
        bv.aN().a(intent);
        a();
    }

    @Override // android.app.Activity
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        bw.b(a, "onNewIntent ->" + intent, true);
        bv.aN().a(intent);
        a();
    }

    private void a() {
        bw.b(a, "start App", true);
        PackageManager packageManager = getPackageManager();
        Intent intent = new Intent("android.intent.action.MAIN", (Uri) null);
        intent.addCategory("android.intent.category.LAUNCHER");
        List<ResolveInfo> listQueryIntentActivities = packageManager.queryIntentActivities(intent, 0);
        Collections.sort(listQueryIntentActivities, new ResolveInfo.DisplayNameComparator(packageManager));
        String packageName = getApplicationContext().getPackageName();
        if (bv.aN().aM() == 0) {
            bw.b(a, "startApp stackSize == 0", true);
            for (ResolveInfo resolveInfo : listQueryIntentActivities) {
                if (packageName != null && resolveInfo.activityInfo.packageName.equals(packageName)) {
                    bw.b(a, "Found startupActivity=" + resolveInfo.activityInfo.name, true);
                    String str = resolveInfo.activityInfo.name;
                    String str2 = resolveInfo.activityInfo.packageName;
                    Intent intent2 = new Intent();
                    intent2.addFlags(DriveFile.MODE_READ_ONLY);
                    intent2.setComponent(new ComponentName(str2, str));
                    startActivity(intent2);
                }
            }
        } else {
            bw.b(a, "StartApp activityStackSize != 0. not starting a main activity", true);
        }
        finish();
    }
}
