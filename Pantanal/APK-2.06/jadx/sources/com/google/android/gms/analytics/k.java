package com.google.android.gms.analytics;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.text.TextUtils;
import com.google.android.gms.analytics.j;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes.dex */
abstract class k<T extends j> {
    Context mContext;
    a<T> sy;

    public interface a<U extends j> {
        void a(String str, int i);

        void a(String str, String str2);

        void b(String str, String str2);

        void c(String str, boolean z);

        U cg();
    }

    public k(Context context, a<T> aVar) {
        this.mContext = context;
        this.sy = aVar;
    }

    private T a(XmlResourceParser xmlResourceParser) {
        try {
            xmlResourceParser.next();
            int eventType = xmlResourceParser.getEventType();
            while (eventType != 1) {
                if (xmlResourceParser.getEventType() == 2) {
                    String lowerCase = xmlResourceParser.getName().toLowerCase();
                    if (lowerCase.equals("screenname")) {
                        String attributeValue = xmlResourceParser.getAttributeValue(null, "name");
                        String strTrim = xmlResourceParser.nextText().trim();
                        if (!TextUtils.isEmpty(attributeValue) && !TextUtils.isEmpty(strTrim)) {
                            this.sy.a(attributeValue, strTrim);
                        }
                    } else if (lowerCase.equals("string")) {
                        String attributeValue2 = xmlResourceParser.getAttributeValue(null, "name");
                        String strTrim2 = xmlResourceParser.nextText().trim();
                        if (!TextUtils.isEmpty(attributeValue2) && strTrim2 != null) {
                            this.sy.b(attributeValue2, strTrim2);
                        }
                    } else if (lowerCase.equals("bool")) {
                        String attributeValue3 = xmlResourceParser.getAttributeValue(null, "name");
                        String strTrim3 = xmlResourceParser.nextText().trim();
                        if (!TextUtils.isEmpty(attributeValue3) && !TextUtils.isEmpty(strTrim3)) {
                            try {
                                this.sy.c(attributeValue3, Boolean.parseBoolean(strTrim3));
                            } catch (NumberFormatException e) {
                                aa.w("Error parsing bool configuration value: " + strTrim3);
                            }
                        }
                    } else if (lowerCase.equals("integer")) {
                        String attributeValue4 = xmlResourceParser.getAttributeValue(null, "name");
                        String strTrim4 = xmlResourceParser.nextText().trim();
                        if (!TextUtils.isEmpty(attributeValue4) && !TextUtils.isEmpty(strTrim4)) {
                            try {
                                this.sy.a(attributeValue4, Integer.parseInt(strTrim4));
                            } catch (NumberFormatException e2) {
                                aa.w("Error parsing int configuration value: " + strTrim4);
                            }
                        }
                    }
                }
                eventType = xmlResourceParser.next();
            }
        } catch (IOException e3) {
            aa.w("Error parsing tracker configuration file: " + e3);
        } catch (XmlPullParserException e4) {
            aa.w("Error parsing tracker configuration file: " + e4);
        }
        return (T) this.sy.cg();
    }

    public T p(int i) {
        try {
            return (T) a(this.mContext.getResources().getXml(i));
        } catch (Resources.NotFoundException e) {
            aa.z("inflate() called with unknown resourceId: " + e);
            return null;
        }
    }
}
