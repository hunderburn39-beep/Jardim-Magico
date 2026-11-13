package com.adcolony.sdk;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.os.Vibrator;
import android.view.View;
import android.widget.Toast;
import com.google.android.gms.plus.PlusShare;
import com.tapjoy.TJAdUnitConstants;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
class ao {
    ao() {
    }

    void a() {
        a.a("System.open_store", new ac() { // from class: com.adcolony.sdk.ao.1
            @Override // com.adcolony.sdk.ac
            public void a(z zVar) {
                ao.this.b(zVar);
            }
        });
        a.a("System.save_screenshot", new ac() { // from class: com.adcolony.sdk.ao.8
            @Override // com.adcolony.sdk.ac
            public void a(z zVar) {
                ao.this.c(zVar);
            }
        });
        a.a("System.telephone", new ac() { // from class: com.adcolony.sdk.ao.9
            @Override // com.adcolony.sdk.ac
            public void a(z zVar) {
                ao.this.d(zVar);
            }
        });
        a.a("System.sms", new ac() { // from class: com.adcolony.sdk.ao.10
            @Override // com.adcolony.sdk.ac
            public void a(z zVar) {
                ao.this.e(zVar);
            }
        });
        a.a("System.vibrate", new ac() { // from class: com.adcolony.sdk.ao.11
            @Override // com.adcolony.sdk.ac
            public void a(z zVar) {
                ao.this.f(zVar);
            }
        });
        a.a("System.open_browser", new ac() { // from class: com.adcolony.sdk.ao.12
            @Override // com.adcolony.sdk.ac
            public void a(z zVar) {
                ao.this.g(zVar);
            }
        });
        a.a("System.mail", new ac() { // from class: com.adcolony.sdk.ao.13
            @Override // com.adcolony.sdk.ac
            public void a(z zVar) {
                ao.this.h(zVar);
            }
        });
        a.a("System.launch_app", new ac() { // from class: com.adcolony.sdk.ao.14
            @Override // com.adcolony.sdk.ac
            public void a(z zVar) {
                ao.this.i(zVar);
            }
        });
        a.a("System.create_calendar_event", new ac() { // from class: com.adcolony.sdk.ao.15
            @Override // com.adcolony.sdk.ac
            public void a(z zVar) {
                ao.this.j(zVar);
            }
        });
        a.a("System.check_app_presence", new ac() { // from class: com.adcolony.sdk.ao.2
            @Override // com.adcolony.sdk.ac
            public void a(z zVar) {
                ao.this.k(zVar);
            }
        });
        a.a("System.check_social_presence", new ac() { // from class: com.adcolony.sdk.ao.3
            @Override // com.adcolony.sdk.ac
            public void a(z zVar) {
                ao.this.l(zVar);
            }
        });
        a.a("System.social_post", new ac() { // from class: com.adcolony.sdk.ao.4
            @Override // com.adcolony.sdk.ac
            public void a(z zVar) {
                ao.this.m(zVar);
            }
        });
        a.a("System.make_in_app_purchase", new ac() { // from class: com.adcolony.sdk.ao.5
            @Override // com.adcolony.sdk.ac
            public void a(z zVar) {
                ao.this.a(zVar);
            }
        });
    }

    boolean a(z zVar) {
        String strB = s.b(zVar.b, "ad_session_id");
        AdColonyInterstitial adColonyInterstitial = a.m.g.b.get(strB);
        ax axVar = a.m.g.e.get(strB);
        if ((adColonyInterstitial == null || adColonyInterstitial.a == null || adColonyInterstitial.b == null) && (axVar == null || axVar.c == null || axVar.b == null)) {
            return false;
        }
        if (axVar == null) {
            new z("AdUnit.make_in_app_purchase", adColonyInterstitial.b.l).b();
        } else {
            new z("AdUnit.make_in_app_purchase", axVar.b.l).b();
        }
        return true;
    }

    boolean b(z zVar) {
        if (a.l == null) {
            return false;
        }
        JSONObject jSONObjectA = s.a();
        String strB = s.b(zVar.b, "product_id");
        if (strB.equals("")) {
            strB = s.b(zVar.b, "handle");
        }
        try {
            a.l.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(strB)));
            s.a(jSONObjectA, "success", true);
            zVar.a(jSONObjectA).b();
            a(s.b(zVar.b, "ad_session_id"));
            return true;
        } catch (Exception e) {
            a("Unable to open.", 0);
            s.a(jSONObjectA, "success", false);
            zVar.a(jSONObjectA).b();
            return false;
        }
    }

    boolean c(final z zVar) {
        if (a.l == null) {
            return false;
        }
        final JSONObject jSONObjectA = s.a();
        String str = Environment.getExternalStorageDirectory().toString() + "/Pictures/AdColony_Screenshots/AdColony_Screenshot_" + System.currentTimeMillis() + ".jpg";
        View rootView = a.l.getWindow().getDecorView().getRootView();
        rootView.setDrawingCacheEnabled(true);
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(rootView.getDrawingCache());
        rootView.setDrawingCacheEnabled(false);
        try {
            File file = new File(Environment.getExternalStorageDirectory().toString() + "/Pictures");
            File file2 = new File(Environment.getExternalStorageDirectory().toString() + "/Pictures/AdColony_Screenshots");
            file.mkdir();
            file2.mkdir();
        } catch (Exception e) {
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(new File(str));
            bitmapCreateBitmap.compress(Bitmap.CompressFormat.JPEG, 90, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            MediaScannerConnection.scanFile(a.l, new String[]{str}, null, new MediaScannerConnection.OnScanCompletedListener() { // from class: com.adcolony.sdk.ao.6
                @Override // android.media.MediaScannerConnection.OnScanCompletedListener
                public void onScanCompleted(String path, Uri uri) {
                    ao.this.a("Screenshot saved to Gallery!", 0);
                    s.a(jSONObjectA, "success", true);
                    zVar.a(jSONObjectA).b();
                }
            });
            return true;
        } catch (FileNotFoundException e2) {
            a("Error saving screenshot.", 0);
            s.a(jSONObjectA, "success", false);
            zVar.a(jSONObjectA).b();
            return false;
        } catch (IOException e3) {
            a("Error saving screenshot.", 0);
            s.a(jSONObjectA, "success", false);
            zVar.a(jSONObjectA).b();
            return false;
        }
    }

    boolean d(z zVar) {
        if (a.l == null) {
            return false;
        }
        JSONObject jSONObjectA = s.a();
        try {
            a.l.startActivity(new Intent("android.intent.action.DIAL").setData(Uri.parse("tel:" + s.b(zVar.b, "phone_number"))));
            s.a(jSONObjectA, "success", true);
            zVar.a(jSONObjectA).b();
            a(s.b(zVar.b, "ad_session_id"));
            return true;
        } catch (Exception e) {
            a("Failed to dial number.", 0);
            s.a(jSONObjectA, "success", false);
            zVar.a(jSONObjectA).b();
            return false;
        }
    }

    boolean e(z zVar) {
        if (a.l == null) {
            return false;
        }
        JSONObject jSONObjectA = s.a();
        JSONArray jSONArrayG = s.g(zVar.b, "recipients");
        String str = "";
        int i = 0;
        while (i < jSONArrayG.length()) {
            if (i != 0) {
                str = str + ";";
            }
            String str2 = str + s.b(jSONArrayG, i);
            i++;
            str = str2;
        }
        try {
            a.l.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("smsto:" + str)).putExtra("sms_body", s.b(zVar.b, "body")));
            s.a(jSONObjectA, "success", true);
            zVar.a(jSONObjectA).b();
            a(s.b(zVar.b, "ad_session_id"));
            return true;
        } catch (Exception e) {
            a("Failed to create sms.", 0);
            s.a(jSONObjectA, "success", false);
            zVar.a(jSONObjectA).b();
            return false;
        }
    }

    boolean f(z zVar) {
        if (a.l == null) {
            return false;
        }
        int iA = s.a(zVar.b, "length_ms", TJAdUnitConstants.DEFAULT_VOLUME_CHECK_INTERVAL);
        JSONObject jSONObjectA = s.a();
        JSONArray jSONArrayV = a.m.m.v();
        boolean z = false;
        for (int i = 0; i < jSONArrayV.length(); i++) {
            if (s.b(jSONArrayV, i).equals("android.permission.VIBRATE")) {
                z = true;
            }
        }
        if (!z) {
            u.e.b("No vibrate permission detected.");
            s.a(jSONObjectA, "success", false);
            zVar.a(jSONObjectA).b();
            return false;
        }
        try {
            ((Vibrator) a.l.getSystemService("vibrator")).vibrate(iA);
            s.a(jSONObjectA, "success", false);
            zVar.a(jSONObjectA).b();
            return true;
        } catch (Exception e) {
            u.e.b("Vibrate command failed.");
            s.a(jSONObjectA, "success", false);
            zVar.a(jSONObjectA).b();
            return false;
        }
    }

    boolean g(z zVar) {
        if (a.l == null) {
            return false;
        }
        JSONObject jSONObjectA = s.a();
        try {
            String strB = s.b(zVar.b, "url");
            if (strB.startsWith("browser")) {
                strB = strB.replaceFirst("browser", "http");
            }
            if (strB.startsWith("safari")) {
                strB = strB.replaceFirst("safari", "http");
            }
            a.l.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(strB)));
            s.a(jSONObjectA, "success", true);
            zVar.a(jSONObjectA).b();
            a(s.b(zVar.b, "ad_session_id"));
            return true;
        } catch (Exception e) {
            a("Failed to launch browser.", 0);
            s.a(jSONObjectA, "success", false);
            zVar.a(jSONObjectA).b();
            return false;
        }
    }

    boolean h(z zVar) {
        if (a.l == null) {
            return false;
        }
        JSONObject jSONObjectA = s.a();
        JSONArray jSONArrayG = s.g(zVar.b, "recipients");
        boolean zD = s.d(zVar.b, TJAdUnitConstants.String.HTML);
        String strB = s.b(zVar.b, "subject");
        String strB2 = s.b(zVar.b, "body");
        String[] strArr = new String[jSONArrayG.length()];
        for (int i = 0; i < jSONArrayG.length(); i++) {
            strArr[i] = s.b(jSONArrayG, i);
        }
        try {
            Intent intent = new Intent("android.intent.action.SEND");
            if (!zD) {
                intent.setType("plain/text");
            }
            intent.putExtra("android.intent.extra.SUBJECT", strB).putExtra("android.intent.extra.TEXT", strB2).putExtra("android.intent.extra.EMAIL", strArr);
            a.l.startActivity(intent);
            s.a(jSONObjectA, "success", true);
            zVar.a(jSONObjectA).b();
            a(s.b(zVar.b, "ad_session_id"));
            return true;
        } catch (Exception e) {
            a("Failed to send email.", 0);
            e.printStackTrace();
            s.a(jSONObjectA, "success", false);
            zVar.a(jSONObjectA).b();
            return false;
        }
    }

    boolean i(z zVar) {
        if (a.l == null) {
            return false;
        }
        JSONObject jSONObjectA = s.a();
        if (s.d(zVar.b, "deep_link")) {
            return b(zVar);
        }
        try {
            a.l.startActivity(a.l.getPackageManager().getLaunchIntentForPackage(s.b(zVar.b, "handle")));
            s.a(jSONObjectA, "success", true);
            zVar.a(jSONObjectA).b();
            a(s.b(zVar.b, "ad_session_id"));
            return true;
        } catch (Exception e) {
            a("Failed to launch external application.", 0);
            s.a(jSONObjectA, "success", false);
            zVar.a(jSONObjectA).b();
            return false;
        }
    }

    boolean j(z zVar) {
        Date date;
        Date date2;
        Date date3;
        long j;
        Intent intentPutExtra;
        String str;
        String str2;
        String str3;
        if (a.l == null) {
            return false;
        }
        JSONObject jSONObjectA = s.a();
        Date date4 = null;
        Date date5 = null;
        String strB = "";
        String strB2 = "";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mmZ");
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ssZ");
        SimpleDateFormat simpleDateFormat3 = new SimpleDateFormat("yyyy-MM-dd");
        JSONObject jSONObjectF = s.f(zVar.b, "params");
        JSONObject jSONObjectF2 = s.f(jSONObjectF, "recurrence");
        JSONArray jSONArrayB = s.b();
        JSONArray jSONArrayB2 = s.b();
        JSONArray jSONArrayB3 = s.b();
        String strB3 = s.b(jSONObjectF, PlusShare.KEY_CONTENT_DEEP_LINK_METADATA_DESCRIPTION);
        s.b(jSONObjectF, "location");
        String strB4 = s.b(jSONObjectF, TJAdUnitConstants.String.VIDEO_START);
        String strB5 = s.b(jSONObjectF, "end");
        String strB6 = s.b(jSONObjectF, "summary");
        if (jSONObjectF2 != null && jSONObjectF2.length() > 0) {
            strB2 = s.b(jSONObjectF2, "expires");
            strB = s.b(jSONObjectF2, "frequency");
            jSONArrayB = s.g(jSONObjectF2, "daysInWeek");
            jSONArrayB2 = s.g(jSONObjectF2, "daysInMonth");
            jSONArrayB3 = s.g(jSONObjectF2, "daysInYear");
        }
        String str4 = strB6.equals("") ? strB3 : strB6;
        try {
            date4 = simpleDateFormat.parse(strB4);
            date5 = simpleDateFormat.parse(strB5);
        } catch (Exception e) {
        }
        if (date4 == null) {
            try {
                date4 = simpleDateFormat2.parse(strB4);
                date5 = simpleDateFormat2.parse(strB5);
                date = date4;
            } catch (Exception e2) {
                date = date4;
            }
        } else {
            date = date4;
        }
        try {
            date2 = simpleDateFormat.parse(strB2);
        } catch (Exception e3) {
            date2 = null;
        }
        if (date2 == null) {
            try {
                date3 = simpleDateFormat3.parse(strB2);
            } catch (Exception e4) {
                date3 = date2;
            }
        } else {
            date3 = date2;
        }
        if (date == null) {
            a("Unable to create Calendar Event", 0);
            s.a(jSONObjectA, "success", false);
            zVar.a(jSONObjectA).b();
            return false;
        }
        long time = date.getTime();
        long time2 = date5.getTime();
        long time3 = 0;
        if (date3 != null) {
            time3 = (date3.getTime() - date.getTime()) / 1000;
        }
        if (strB.equals("DAILY")) {
            j = (time3 / 86400) + 1;
        } else if (strB.equals("WEEKLY")) {
            j = (time3 / 604800) + 1;
        } else if (strB.equals("MONTHLY")) {
            j = (time3 / 2629800) + 1;
        } else if (!strB.equals("YEARLY")) {
            j = 0;
        } else {
            j = (time3 / 31557600) + 1;
        }
        if (jSONObjectF2 != null && jSONObjectF2.length() > 0) {
            String str5 = "FREQ=" + strB + ";COUNT=" + j;
            try {
                if (jSONArrayB.length() != 0) {
                    String str6 = "";
                    for (int i = 0; i < jSONArrayB.length(); i++) {
                        if (i > 0) {
                            str6 = str6 + ",";
                        }
                        switch (jSONArrayB.getInt(i)) {
                            case 1:
                                str6 = str6 + "MO";
                                break;
                            case 2:
                                str6 = str6 + "TU";
                                break;
                            case 3:
                                str6 = str6 + "WE";
                                break;
                            case 4:
                                str6 = str6 + "TH";
                                break;
                            case 5:
                                str6 = str6 + "FR";
                                break;
                            case 6:
                                str6 = str6 + "SA";
                                break;
                            case 7:
                                str6 = str6 + "SU";
                                break;
                        }
                    }
                    str2 = str5 + ";BYDAY=" + str6;
                } else {
                    str2 = str5;
                }
                try {
                    if (jSONArrayB2.length() != 0) {
                        String str7 = "";
                        for (int i2 = 0; i2 < jSONArrayB2.length(); i2++) {
                            if (i2 > 0) {
                                str7 = str7 + ",";
                            }
                            str7 = str7 + jSONArrayB2.getInt(i2);
                        }
                        str3 = str2 + ";BYMONTHDAY=" + str7;
                    } else {
                        str3 = str2;
                    }
                    try {
                        if (jSONArrayB3.length() != 0) {
                            String str8 = "";
                            for (int i3 = 0; i3 < jSONArrayB3.length(); i3++) {
                                if (i3 > 0) {
                                    str8 = str8 + ",";
                                }
                                str8 = str8 + jSONArrayB3.getInt(i3);
                            }
                            str = str3 + ";BYYEARDAY=" + str8;
                        } else {
                            str = str3;
                        }
                    } catch (JSONException e5) {
                        str = str3;
                    }
                } catch (JSONException e6) {
                    str = str2;
                }
            } catch (JSONException e7) {
                str = str5;
            }
            intentPutExtra = new Intent("android.intent.action.EDIT").setType("vnd.android.cursor.item/event").putExtra("title", str4).putExtra(PlusShare.KEY_CONTENT_DEEP_LINK_METADATA_DESCRIPTION, strB3).putExtra("beginTime", time).putExtra("endTime", time2).putExtra("rrule", str);
        } else {
            intentPutExtra = new Intent("android.intent.action.EDIT").setType("vnd.android.cursor.item/event").putExtra("title", str4).putExtra(PlusShare.KEY_CONTENT_DEEP_LINK_METADATA_DESCRIPTION, strB3).putExtra("beginTime", time).putExtra("endTime", time2);
        }
        try {
            a.l.startActivity(intentPutExtra);
            s.a(jSONObjectA, "success", true);
            zVar.a(jSONObjectA).b();
            a(s.b(zVar.b, "ad_session_id"));
            return true;
        } catch (Exception e8) {
            a("Unable to create Calendar Event.", 0);
            s.a(jSONObjectA, "success", false);
            zVar.a(jSONObjectA).b();
            return false;
        }
    }

    boolean k(z zVar) {
        JSONObject jSONObjectA = s.a();
        String strB = s.b(zVar.b, "name");
        boolean zA = au.a(strB);
        s.a(jSONObjectA, "success", true);
        s.a(jSONObjectA, "result", zA);
        s.a(jSONObjectA, "name", strB);
        s.a(jSONObjectA, "service", strB);
        zVar.a(jSONObjectA).b();
        return true;
    }

    boolean l(z zVar) {
        return k(zVar);
    }

    boolean m(z zVar) {
        if (a.l == null) {
            return false;
        }
        JSONObject jSONObjectA = s.a();
        try {
            a.l.startActivity(Intent.createChooser(new Intent("android.intent.action.SEND").setType("text/plain").putExtra("android.intent.extra.TEXT", s.b(zVar.b, "text") + " " + s.b(zVar.b, "url")), "Share this post via..."));
            s.a(jSONObjectA, "success", true);
            zVar.a(jSONObjectA).b();
            a(s.b(zVar.b, "ad_session_id"));
            return true;
        } catch (Exception e) {
            a("Unable to create social post.", 0);
            s.a(jSONObjectA, "success", false);
            zVar.a(jSONObjectA).b();
            return false;
        }
    }

    void a(final String str, final int i) {
        if (a.l != null) {
            a.l.runOnUiThread(new Runnable() { // from class: com.adcolony.sdk.ao.7
                @Override // java.lang.Runnable
                public void run() {
                    Toast.makeText(a.l, str, i).show();
                }
            });
        }
    }

    void a(String str) {
        if (a.m != null && a.m.g != null) {
            AdColonyInterstitial adColonyInterstitial = a.m.g.b.get(str);
            if (adColonyInterstitial != null && adColonyInterstitial.a != null) {
                adColonyInterstitial.a.a(adColonyInterstitial);
            } else {
                if (a.m.g.e.get(str) != null) {
                }
            }
        }
    }
}
