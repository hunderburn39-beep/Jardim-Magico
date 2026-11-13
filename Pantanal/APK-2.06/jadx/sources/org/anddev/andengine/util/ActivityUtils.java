package org.anddev.andengine.util;

import android.R;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.view.Window;
import android.view.WindowManager;
import org.anddev.andengine.ui.activity.BaseActivity;
import org.anddev.andengine.util.progress.IProgressListener;
import org.anddev.andengine.util.progress.ProgressCallable;

/* JADX INFO: loaded from: classes.dex */
public class ActivityUtils {
    public static void requestFullscreen(Activity pActivity) {
        Window window = pActivity.getWindow();
        window.addFlags(1024);
        window.clearFlags(2048);
        window.requestFeature(1);
    }

    public static void setScreenBrightness(Activity pActivity, float pScreenBrightness) {
        Window window = pActivity.getWindow();
        WindowManager.LayoutParams windowLayoutParams = window.getAttributes();
        windowLayoutParams.screenBrightness = pScreenBrightness;
        window.setAttributes(windowLayoutParams);
    }

    public static void keepScreenOn(Activity pActivity) {
        pActivity.getWindow().addFlags(128);
    }

    public static <T> void doAsync(Context pContext, int pTitleResID, int pMessageResID, java.util.concurrent.Callable<T> pCallable, Callback<T> pCallback) {
        doAsync(pContext, pTitleResID, pMessageResID, (java.util.concurrent.Callable) pCallable, (Callback) pCallback, (Callback<Exception>) null, false);
    }

    public static <T> void doAsync(Context pContext, CharSequence pTitle, CharSequence pMessage, java.util.concurrent.Callable<T> pCallable, Callback<T> pCallback) {
        doAsync(pContext, pTitle, pMessage, (java.util.concurrent.Callable) pCallable, (Callback) pCallback, (Callback<Exception>) null, false);
    }

    public static <T> void doAsync(Context pContext, int pTitleResID, int pMessageResID, java.util.concurrent.Callable<T> pCallable, Callback<T> pCallback, boolean pCancelable) {
        doAsync(pContext, pTitleResID, pMessageResID, pCallable, pCallback, (Callback<Exception>) null, pCancelable);
    }

    public static <T> void doAsync(Context pContext, CharSequence pTitle, CharSequence pMessage, java.util.concurrent.Callable<T> pCallable, Callback<T> pCallback, boolean pCancelable) {
        doAsync(pContext, pTitle, pMessage, pCallable, pCallback, (Callback<Exception>) null, pCancelable);
    }

    public static <T> void doAsync(Context pContext, int pTitleResID, int pMessageResID, java.util.concurrent.Callable<T> pCallable, Callback<T> pCallback, Callback<Exception> pExceptionCallback) {
        doAsync(pContext, pTitleResID, pMessageResID, (java.util.concurrent.Callable) pCallable, (Callback) pCallback, pExceptionCallback, false);
    }

    public static <T> void doAsync(Context pContext, CharSequence pTitle, CharSequence pMessage, java.util.concurrent.Callable<T> pCallable, Callback<T> pCallback, Callback<Exception> pExceptionCallback) {
        doAsync(pContext, pTitle, pMessage, (java.util.concurrent.Callable) pCallable, (Callback) pCallback, pExceptionCallback, false);
    }

    public static <T> void doAsync(Context pContext, int pTitleResID, int pMessageResID, java.util.concurrent.Callable<T> pCallable, Callback<T> pCallback, Callback<Exception> pExceptionCallback, boolean pCancelable) {
        doAsync(pContext, pContext.getString(pTitleResID), pContext.getString(pMessageResID), pCallable, pCallback, pExceptionCallback, pCancelable);
    }

    public static <T> void doAsync(final Context context, final CharSequence charSequence, final CharSequence charSequence2, final java.util.concurrent.Callable<T> callable, final Callback<T> callback, final Callback<Exception> callback2, final boolean z) {
        new AsyncTask<Void, Void, T>() { // from class: org.anddev.andengine.util.ActivityUtils.1
            private Exception mException = null;
            private ProgressDialog mPD;

            @Override // android.os.AsyncTask
            public void onPreExecute() {
                this.mPD = ProgressDialog.show(context, charSequence, charSequence2, true, z);
                if (z) {
                    ProgressDialog progressDialog = this.mPD;
                    final Callback callback3 = callback2;
                    progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() { // from class: org.anddev.andengine.util.ActivityUtils.1.1
                        @Override // android.content.DialogInterface.OnCancelListener
                        public void onCancel(DialogInterface pDialogInterface) {
                            callback3.onCallback(new BaseActivity.CancelledException());
                            pDialogInterface.dismiss();
                        }
                    });
                }
                super.onPreExecute();
            }

            @Override // android.os.AsyncTask
            public T doInBackground(Void... voidArr) {
                try {
                    return (T) callable.call();
                } catch (Exception e) {
                    this.mException = e;
                    return null;
                }
            }

            @Override // android.os.AsyncTask
            public void onPostExecute(T result) {
                try {
                    this.mPD.dismiss();
                } catch (Exception e) {
                    Debug.e("Error", e);
                }
                if (isCancelled()) {
                    this.mException = new BaseActivity.CancelledException();
                }
                if (this.mException == null) {
                    callback.onCallback(result);
                } else if (callback2 == null) {
                    Debug.e("Error", this.mException);
                } else {
                    callback2.onCallback(this.mException);
                }
                super.onPostExecute(result);
            }
        }.execute((Void[]) null);
    }

    public static <T> void doProgressAsync(Context pContext, int pTitleResID, ProgressCallable<T> pCallable, Callback<T> pCallback) {
        doProgressAsync(pContext, pTitleResID, pCallable, pCallback, null);
    }

    public static <T> void doProgressAsync(Context pContext, int pTitleResID, ProgressCallable<T> pCallable, Callback<T> pCallback, Callback<Exception> pExceptionCallback) {
        new AnonymousClass2(pContext, pTitleResID, pCallable, pCallback, pExceptionCallback).execute((Object[]) null);
    }

    /* JADX INFO: Add missing generic type declarations: [T] */
    /* JADX INFO: renamed from: org.anddev.andengine.util.ActivityUtils$2, reason: invalid class name */
    class AnonymousClass2<T> extends AsyncTask<Void, Integer, T> {
        private Exception mException = null;
        private ProgressDialog mPD;
        private final /* synthetic */ ProgressCallable val$pCallable;
        private final /* synthetic */ Callback val$pCallback;
        private final /* synthetic */ Context val$pContext;
        private final /* synthetic */ Callback val$pExceptionCallback;
        private final /* synthetic */ int val$pTitleResID;

        AnonymousClass2(Context context, int i, ProgressCallable progressCallable, Callback callback, Callback callback2) {
            this.val$pContext = context;
            this.val$pTitleResID = i;
            this.val$pCallable = progressCallable;
            this.val$pCallback = callback;
            this.val$pExceptionCallback = callback2;
        }

        @Override // android.os.AsyncTask
        public void onPreExecute() {
            this.mPD = new ProgressDialog(this.val$pContext);
            this.mPD.setTitle(this.val$pTitleResID);
            this.mPD.setIcon(R.drawable.ic_menu_save);
            this.mPD.setIndeterminate(false);
            this.mPD.setProgressStyle(1);
            this.mPD.show();
            super.onPreExecute();
        }

        @Override // android.os.AsyncTask
        public T doInBackground(Void... voidArr) {
            try {
                return (T) this.val$pCallable.call(new IProgressListener() { // from class: org.anddev.andengine.util.ActivityUtils.2.1
                    @Override // org.anddev.andengine.util.progress.IProgressListener
                    public void onProgressChanged(int pProgress) {
                        AnonymousClass2.this.onProgressUpdate(Integer.valueOf(pProgress));
                    }
                });
            } catch (Exception e) {
                this.mException = e;
                return null;
            }
        }

        @Override // android.os.AsyncTask
        public void onProgressUpdate(Integer... values) {
            this.mPD.setProgress(values[0].intValue());
        }

        @Override // android.os.AsyncTask
        public void onPostExecute(T result) {
            try {
                this.mPD.dismiss();
            } catch (Exception e) {
                Debug.e("Error", e);
            }
            if (isCancelled()) {
                this.mException = new BaseActivity.CancelledException();
            }
            if (this.mException == null) {
                this.val$pCallback.onCallback(result);
            } else if (this.val$pExceptionCallback == null) {
                Debug.e("Error", this.mException);
            } else {
                this.val$pExceptionCallback.onCallback(this.mException);
            }
            super.onPostExecute(result);
        }
    }

    public static <T> void doAsync(Context pContext, int pTitleResID, int pMessageResID, AsyncCallable<T> pAsyncCallable, final Callback<T> pCallback, Callback<Exception> pExceptionCallback) {
        final ProgressDialog pd = ProgressDialog.show(pContext, pContext.getString(pTitleResID), pContext.getString(pMessageResID));
        pAsyncCallable.call(new Callback<T>() { // from class: org.anddev.andengine.util.ActivityUtils.3
            @Override // org.anddev.andengine.util.Callback
            public void onCallback(T result) {
                try {
                    pd.dismiss();
                } catch (Exception e) {
                    Debug.e("Error", e);
                }
                pCallback.onCallback(result);
            }
        }, pExceptionCallback);
    }
}
