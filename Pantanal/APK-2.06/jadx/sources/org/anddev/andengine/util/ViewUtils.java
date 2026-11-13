package org.anddev.andengine.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/* JADX INFO: loaded from: classes.dex */
public class ViewUtils {
    public static View inflate(Context pContext, int pLayoutID) {
        return LayoutInflater.from(pContext).inflate(pLayoutID, (ViewGroup) null);
    }

    public static View inflate(Context pContext, int pLayoutID, ViewGroup pViewGroup) {
        return LayoutInflater.from(pContext).inflate(pLayoutID, pViewGroup, true);
    }
}
