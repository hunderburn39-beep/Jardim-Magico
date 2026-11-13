package org.anddev.andengine.util;

import android.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import java.util.GregorianCalendar;
import org.anddev.andengine.util.constants.Constants;

/* JADX INFO: loaded from: classes.dex */
public class BetaUtils implements Constants {
    private static final String PREFERENCES_BETAUTILS_ID = "preferences.betautils.lastuse";

    public static boolean finishWhenExpired(Activity pActivity, GregorianCalendar pExpirationDate, int pTitleResourceID, int pMessageResourceID) {
        return finishWhenExpired(pActivity, pExpirationDate, pTitleResourceID, pMessageResourceID, null, null);
    }

    public static boolean finishWhenExpired(final Activity pActivity, GregorianCalendar pExpirationDate, int pTitleResourceID, int pMessageResourceID, final Intent pOkIntent, final Intent pCancelIntent) {
        SharedPreferences spref = SimplePreferences.getInstance(pActivity);
        long now = System.currentTimeMillis();
        long lastuse = Math.max(now, spref.getLong(PREFERENCES_BETAUTILS_ID, -1L));
        spref.edit().putLong(PREFERENCES_BETAUTILS_ID, lastuse).commit();
        GregorianCalendar lastuseDate = new GregorianCalendar();
        lastuseDate.setTimeInMillis(lastuse);
        if (!lastuseDate.after(pExpirationDate)) {
            return false;
        }
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(pActivity).setTitle(pTitleResourceID).setIcon(R.drawable.ic_dialog_alert).setMessage(pMessageResourceID);
        alertDialogBuilder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() { // from class: org.anddev.andengine.util.BetaUtils.1
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface pDialog, int pWhich) {
                if (pOkIntent != null) {
                    pActivity.startActivity(pOkIntent);
                }
                pActivity.finish();
            }
        });
        alertDialogBuilder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() { // from class: org.anddev.andengine.util.BetaUtils.2
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface pDialog, int pWhich) {
                if (pCancelIntent != null) {
                    pActivity.startActivity(pCancelIntent);
                }
                pActivity.finish();
            }
        }).create().show();
        return true;
    }
}
