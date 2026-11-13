package com.fyber.mediation.test.view;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.Date;

/* JADX INFO: loaded from: classes.dex */
public class LogView extends ScrollView {
    StringBuilder log;
    TextView txtLog;

    public LogView(Context context) {
        super(context);
        this.log = new StringBuilder();
        this.txtLog = new TextView(getContext());
        this.txtLog.setTextColor(-16777216);
        addView(this.txtLog, new LinearLayout.LayoutParams(-1, -2));
    }

    public void addMessage(final String message) {
        ((Activity) getContext()).runOnUiThread(new Runnable() { // from class: com.fyber.mediation.test.view.LogView.1
            @Override // java.lang.Runnable
            public void run() {
                Log.d("MediationTestActivity", "adding log: " + message);
                SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
                Date date = new Date();
                LogView.this.log.append("[" + dateFormat.format(date) + "] " + message + "\n");
                LogView.this.txtLog.setText(LogView.this.log.toString());
            }
        });
    }
}
