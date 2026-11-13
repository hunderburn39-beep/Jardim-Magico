package com.fyber.mediation.test.data;

import android.content.Context;
import android.support.v4.internal.view.SupportMenu;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import com.fyber.mediation.MediationAdapter;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class NetworkStatus {
    private int adapterStatus;
    private Map<String, Object> configs;
    private boolean listInTestActivity = true;
    private int localStatus;
    private String name;
    private MediationAdapter networkAdapter;
    private int remoteStatus;

    public NetworkStatus(String name) {
        this.name = name;
    }

    public NetworkStatus(String name, MediationAdapter adapter) {
        this.name = name;
        setNetworkAdapter(adapter);
    }

    public boolean listInTestActivity() {
        return this.listInTestActivity;
    }

    public void setListInTestActivity(boolean listInTestActivity) {
        this.listInTestActivity = listInTestActivity;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLocalStatus() {
        return this.localStatus;
    }

    public int getRemoteStatus() {
        return this.remoteStatus;
    }

    public void setRemoteStatus(int remoteStatus) {
        this.remoteStatus = remoteStatus;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public View getLocalTextView(Context c) {
        CheckBox output = new CheckBox(c);
        output.setTextSize(22.0f);
        output.setClickable(false);
        switch (this.localStatus) {
            case 0:
                output.setText("No SDK Detected");
                output.setBackgroundColor(SupportMenu.CATEGORY_MASK);
                return output;
            case 1:
                output.setText("SDK Detected");
                output.setChecked(true);
                output.setBackgroundColor(-16711936);
                return output;
            default:
                return output;
        }
    }

    public View getRemoteTextView(Context c) {
        LinearLayout output = new LinearLayout(c);
        output.setOrientation(1);
        CheckBox credentials = new CheckBox(c);
        credentials.setTextSize(20.0f);
        credentials.setClickable(false);
        CheckBox enabled = new CheckBox(c);
        enabled.setTextSize(20.0f);
        enabled.setClickable(false);
        switch (this.remoteStatus) {
            case 0:
                credentials.setText("No Credentials Found");
                enabled.setText("Network Not Enabled");
                credentials.setBackgroundColor(SupportMenu.CATEGORY_MASK);
                enabled.setBackgroundColor(SupportMenu.CATEGORY_MASK);
                break;
            case 1:
                credentials.setText("Credentials Found");
                credentials.setBackgroundColor(-16711936);
                credentials.setChecked(true);
                enabled.setText("Network Enabled");
                enabled.setBackgroundColor(-16711936);
                enabled.setChecked(true);
                break;
            case 2:
                credentials.setText("Credentials Found");
                credentials.setBackgroundColor(-16711936);
                credentials.setChecked(true);
                enabled.setText("Network Not Enabled");
                enabled.setBackgroundColor(SupportMenu.CATEGORY_MASK);
                break;
            case 3:
                credentials.setText("Network Error");
                credentials.setBackgroundColor(-7829368);
                enabled.setText("Network Error");
                enabled.setBackgroundColor(-7829368);
                break;
        }
        output.addView(credentials);
        output.addView(enabled);
        return output;
    }

    public boolean getNetworkStatus() {
        return this.localStatus == 1 && this.remoteStatus == 1 && this.adapterStatus == 1;
    }

    public MediationAdapter getNetworkAdapter() {
        return this.networkAdapter;
    }

    public void setNetworkAdapter(MediationAdapter networkAdapter) {
        this.networkAdapter = networkAdapter;
    }

    public void setConfigs(Map<String, Object> configs) {
        this.configs = configs;
        if (configs != null) {
            this.remoteStatus = 1;
        } else {
            this.remoteStatus = 0;
        }
    }

    public Map<String, Object> getConfigs() {
        return this.configs;
    }

    public void setLocalStatus(int localStatus) {
        this.localStatus = localStatus;
    }

    public int getAdapterStatus() {
        return this.adapterStatus;
    }

    public void setAdapterStatus(int adapterStatus) {
        this.adapterStatus = adapterStatus;
    }
}
