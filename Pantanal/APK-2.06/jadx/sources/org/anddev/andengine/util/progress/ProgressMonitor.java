package org.anddev.andengine.util.progress;

/* JADX INFO: loaded from: classes.dex */
public class ProgressMonitor implements IProgressListener {
    private final IProgressListener mListener;
    private final ProgressMonitor mParentProgressMonitor;
    private int mProgress;
    private int mSubMonitorRangeFrom;
    private int mSubMonitorRangeTo;

    public ProgressMonitor(IProgressListener pListener) {
        this.mSubMonitorRangeFrom = 0;
        this.mSubMonitorRangeTo = 100;
        this.mProgress = 0;
        this.mListener = pListener;
        this.mParentProgressMonitor = null;
    }

    public ProgressMonitor(ProgressMonitor pParent) {
        this.mSubMonitorRangeFrom = 0;
        this.mSubMonitorRangeTo = 100;
        this.mProgress = 0;
        this.mListener = null;
        this.mParentProgressMonitor = pParent;
    }

    public ProgressMonitor getParentProgressMonitor() {
        return this.mParentProgressMonitor;
    }

    public int getProgress() {
        return this.mProgress;
    }

    public void setSubMonitorRange(int pSubMonitorRangeFrom, int pSubMonitorRangeTo) {
        this.mSubMonitorRangeFrom = pSubMonitorRangeFrom;
        this.mSubMonitorRangeTo = pSubMonitorRangeTo;
    }

    @Override // org.anddev.andengine.util.progress.IProgressListener
    public void onProgressChanged(int pProgress) {
        this.mProgress = pProgress;
        if (this.mParentProgressMonitor != null) {
            this.mParentProgressMonitor.onSubProgressChanged(pProgress);
        } else {
            this.mListener.onProgressChanged(pProgress);
        }
    }

    private void onSubProgressChanged(int pSubProgress) {
        int subRange = this.mSubMonitorRangeTo - this.mSubMonitorRangeFrom;
        int subProgressInRange = this.mSubMonitorRangeFrom + ((int) ((subRange * pSubProgress) / 100.0f));
        if (this.mParentProgressMonitor != null) {
            this.mParentProgressMonitor.onSubProgressChanged(subProgressInRange);
        } else {
            this.mListener.onProgressChanged(subProgressInRange);
        }
    }
}
