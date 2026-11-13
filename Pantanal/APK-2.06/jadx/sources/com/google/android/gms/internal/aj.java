package com.google.android.gms.internal;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import com.google.ads.mediation.admob.AdMobAdapter;
import com.google.android.gms.ads.search.SearchAdRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public class aj {
    public static final aj lR = new aj();

    private aj() {
    }

    public static aj az() {
        return lR;
    }

    public ah a(Context context, as asVar) {
        Date birthday = asVar.getBirthday();
        long time = birthday != null ? birthday.getTime() : -1L;
        String contentUrl = asVar.getContentUrl();
        int gender = asVar.getGender();
        Set<String> keywords = asVar.getKeywords();
        List listUnmodifiableList = !keywords.isEmpty() ? Collections.unmodifiableList(new ArrayList(keywords)) : null;
        boolean zIsTestDevice = asVar.isTestDevice(context);
        int iAE = asVar.aE();
        Location location = asVar.getLocation();
        Bundle networkExtrasBundle = asVar.getNetworkExtrasBundle(AdMobAdapter.class);
        boolean manualImpressionsEnabled = asVar.getManualImpressionsEnabled();
        String publisherProvidedId = asVar.getPublisherProvidedId();
        SearchAdRequest searchAdRequestAB = asVar.aB();
        return new ah(3, time, networkExtrasBundle, gender, listUnmodifiableList, zIsTestDevice, iAE, manualImpressionsEnabled, publisherProvidedId, searchAdRequestAB != null ? new av(searchAdRequestAB) : null, location, contentUrl);
    }
}
