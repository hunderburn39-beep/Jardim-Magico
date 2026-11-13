package com.heyzap.internal;

import android.app.Activity;
import android.content.Context;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;

/* JADX INFO: loaded from: classes.dex */
public class ContextReference {
    private Activity activity;
    private final Set<Runnable> activityChangeCallbacks = new HashSet();
    private Context applicationContext;

    public Context getApp() {
        return this.applicationContext;
    }

    public Activity getActivity() {
        return this.activity;
    }

    public void updateContext(Context context) {
        Activity activity;
        this.applicationContext = context.getApplicationContext();
        if ((context instanceof Activity) && (activity = (Activity) context) != this.activity) {
            this.activity = activity;
            for (Runnable callback : this.activityChangeCallbacks) {
                callback.run();
            }
        }
    }

    public boolean addActivityUpdateListener(Runnable callback, ExecutorService executorService) {
        return this.activityChangeCallbacks.add(new UpdateCallbackWrapper(callback, executorService));
    }

    public boolean removeActivityUpdateListener(Runnable callback) {
        return this.activityChangeCallbacks.remove(new UpdateCallbackWrapper(callback, null));
    }

    public static class UpdateCallbackWrapper implements Runnable {
        public final Runnable callback;
        public final ExecutorService executorService;

        public UpdateCallbackWrapper(Runnable callback, ExecutorService executorService) {
            this.executorService = executorService;
            this.callback = callback;
        }

        @Override // java.lang.Runnable
        public void run() {
            this.executorService.submit(this.callback);
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            UpdateCallbackWrapper that = (UpdateCallbackWrapper) o;
            return this.callback.equals(that.callback) && this.executorService.equals(that.executorService);
        }

        public int hashCode() {
            int result = this.executorService.hashCode();
            return (result * 31) + this.callback.hashCode();
        }
    }
}
