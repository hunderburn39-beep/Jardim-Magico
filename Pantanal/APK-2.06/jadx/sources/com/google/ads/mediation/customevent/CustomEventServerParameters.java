package com.google.ads.mediation.customevent;

import com.google.ads.mediation.MediationServerParameters;
import com.google.android.gms.plus.PlusShare;
import com.r7games.slotpantanal.utils.Base64;

/* JADX INFO: loaded from: classes.dex */
public final class CustomEventServerParameters extends MediationServerParameters {

    @MediationServerParameters.Parameter(name = "class_name", required = Base64.ENCODE)
    public String className;

    @MediationServerParameters.Parameter(name = PlusShare.KEY_CALL_TO_ACTION_LABEL, required = Base64.ENCODE)
    public String label;

    @MediationServerParameters.Parameter(name = "parameter", required = false)
    public String parameter = null;
}
