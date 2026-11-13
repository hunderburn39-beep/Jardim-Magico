package org.anddev.andengine.ui.dialog;

import android.content.Context;
import android.content.DialogInterface;
import org.anddev.andengine.util.Callback;

/* JADX INFO: loaded from: classes.dex */
public class StringInputDialogBuilder extends GenericInputDialogBuilder<String> {
    public StringInputDialogBuilder(Context pContext, int pTitleResID, int pMessageResID, int pErrorResID, int pIconResID, Callback<String> pSuccessCallback, DialogInterface.OnCancelListener pOnCancelListener) {
        super(pContext, pTitleResID, pMessageResID, pErrorResID, pIconResID, pSuccessCallback, pOnCancelListener);
    }

    public StringInputDialogBuilder(Context pContext, int pTitleResID, int pMessageResID, int pErrorResID, int pIconResID, String pDefaultText, Callback<String> pSuccessCallback, DialogInterface.OnCancelListener pOnCancelListener) {
        super(pContext, pTitleResID, pMessageResID, pErrorResID, pIconResID, pDefaultText, pSuccessCallback, pOnCancelListener);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.anddev.andengine.ui.dialog.GenericInputDialogBuilder
    public String generateResult(String pInput) {
        return pInput;
    }
}
