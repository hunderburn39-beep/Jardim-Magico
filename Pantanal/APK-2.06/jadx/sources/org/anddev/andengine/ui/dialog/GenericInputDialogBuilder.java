package org.anddev.andengine.ui.dialog;

import android.R;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.EditText;
import android.widget.Toast;
import org.anddev.andengine.util.Callback;
import org.anddev.andengine.util.Debug;

/* JADX INFO: loaded from: classes.dex */
public abstract class GenericInputDialogBuilder<T> {
    protected final Context mContext;
    private final String mDefaultText;
    private final int mErrorResID;
    protected final int mIconResID;
    protected final int mMessageResID;
    protected final DialogInterface.OnCancelListener mOnCancelListener;
    protected final Callback<T> mSuccessCallback;
    protected final int mTitleResID;

    protected abstract T generateResult(String str);

    public GenericInputDialogBuilder(Context pContext, int pTitleResID, int pMessageResID, int pErrorResID, int pIconResID, Callback<T> pSuccessCallback, DialogInterface.OnCancelListener pOnCancelListener) {
        this(pContext, pTitleResID, pMessageResID, pErrorResID, pIconResID, "", pSuccessCallback, pOnCancelListener);
    }

    public GenericInputDialogBuilder(Context pContext, int pTitleResID, int pMessageResID, int pErrorResID, int pIconResID, String pDefaultText, Callback<T> pSuccessCallback, DialogInterface.OnCancelListener pOnCancelListener) {
        this.mContext = pContext;
        this.mTitleResID = pTitleResID;
        this.mMessageResID = pMessageResID;
        this.mErrorResID = pErrorResID;
        this.mIconResID = pIconResID;
        this.mDefaultText = pDefaultText;
        this.mSuccessCallback = pSuccessCallback;
        this.mOnCancelListener = pOnCancelListener;
    }

    public Dialog create() {
        final EditText etInput = new EditText(this.mContext);
        etInput.setText(this.mDefaultText);
        AlertDialog.Builder ab = new AlertDialog.Builder(this.mContext);
        if (this.mTitleResID != 0) {
            ab.setTitle(this.mTitleResID);
        }
        if (this.mMessageResID != 0) {
            ab.setMessage(this.mMessageResID);
        }
        if (this.mIconResID != 0) {
            ab.setIcon(this.mIconResID);
        }
        setView(ab, etInput);
        ab.setOnCancelListener(this.mOnCancelListener).setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() { // from class: org.anddev.andengine.ui.dialog.GenericInputDialogBuilder.1
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                try {
                    GenericInputDialogBuilder.this.mSuccessCallback.onCallback((T) GenericInputDialogBuilder.this.generateResult(etInput.getText().toString()));
                    dialogInterface.dismiss();
                } catch (IllegalArgumentException e) {
                    Debug.e("Error in GenericInputDialogBuilder.generateResult()", e);
                    Toast.makeText(GenericInputDialogBuilder.this.mContext, GenericInputDialogBuilder.this.mErrorResID, 0).show();
                }
            }
        }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() { // from class: org.anddev.andengine.ui.dialog.GenericInputDialogBuilder.2
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface pDialog, int pWhich) {
                GenericInputDialogBuilder.this.mOnCancelListener.onCancel(pDialog);
                pDialog.dismiss();
            }
        });
        return ab.create();
    }

    protected void setView(AlertDialog.Builder pBuilder, EditText pInputEditText) {
        pBuilder.setView(pInputEditText);
    }
}
