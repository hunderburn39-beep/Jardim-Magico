package com.google.android.gms.tagmanager;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.google.android.gms.common.api.Status;

/* JADX INFO: loaded from: classes.dex */
class n implements ContainerHolder {
    private final Looper AS;
    private Container WR;
    private Container WS;
    private b WT;
    private a WU;
    private boolean WV;
    private TagManager WW;
    private Status wJ;

    public interface a {
        void br(String str);

        String ke();

        void kg();
    }

    private class b extends Handler {
        private final ContainerHolder.ContainerAvailableListener WX;

        public b(ContainerHolder.ContainerAvailableListener containerAvailableListener, Looper looper) {
            super(looper);
            this.WX = containerAvailableListener;
        }

        public void bs(String str) {
            sendMessage(obtainMessage(1, str));
        }

        protected void bt(String str) {
            this.WX.onContainerAvailable(n.this, str);
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    bt((String) msg.obj);
                    break;
                default:
                    bh.w("Don't know how to handle this message.");
                    break;
            }
        }
    }

    public n(Status status) {
        this.wJ = status;
        this.AS = null;
    }

    public n(TagManager tagManager, Looper looper, Container container, a aVar) {
        this.WW = tagManager;
        this.AS = looper == null ? Looper.getMainLooper() : looper;
        this.WR = container;
        this.WU = aVar;
        this.wJ = Status.Bv;
        tagManager.a(this);
    }

    private void kf() {
        if (this.WT != null) {
            this.WT.bs(this.WS.kc());
        }
    }

    public synchronized void a(Container container) {
        if (!this.WV) {
            if (container == null) {
                bh.w("Unexpected null container.");
            } else {
                this.WS = container;
                kf();
            }
        }
    }

    public synchronized void bp(String str) {
        if (!this.WV) {
            this.WR.bp(str);
        }
    }

    void br(String str) {
        if (this.WV) {
            bh.w("setCtfeUrlPathAndQuery called on a released ContainerHolder.");
        } else {
            this.WU.br(str);
        }
    }

    @Override // com.google.android.gms.tagmanager.ContainerHolder
    public synchronized Container getContainer() {
        Container container = null;
        synchronized (this) {
            if (this.WV) {
                bh.w("ContainerHolder is released.");
            } else {
                if (this.WS != null) {
                    this.WR = this.WS;
                    this.WS = null;
                }
                container = this.WR;
            }
        }
        return container;
    }

    String getContainerId() {
        if (!this.WV) {
            return this.WR.getContainerId();
        }
        bh.w("getContainerId called on a released ContainerHolder.");
        return "";
    }

    @Override // com.google.android.gms.common.api.Result
    public Status getStatus() {
        return this.wJ;
    }

    String ke() {
        if (!this.WV) {
            return this.WU.ke();
        }
        bh.w("setCtfeUrlPathAndQuery called on a released ContainerHolder.");
        return "";
    }

    @Override // com.google.android.gms.tagmanager.ContainerHolder
    public synchronized void refresh() {
        if (this.WV) {
            bh.w("Refreshing a released ContainerHolder.");
        } else {
            this.WU.kg();
        }
    }

    @Override // com.google.android.gms.common.api.Releasable
    public synchronized void release() {
        if (this.WV) {
            bh.w("Releasing a released ContainerHolder.");
        } else {
            this.WV = true;
            this.WW.b(this);
            this.WR.release();
            this.WR = null;
            this.WS = null;
            this.WU = null;
            this.WT = null;
        }
    }

    @Override // com.google.android.gms.tagmanager.ContainerHolder
    public synchronized void setContainerAvailableListener(ContainerHolder.ContainerAvailableListener listener) {
        if (this.WV) {
            bh.w("ContainerHolder is released.");
        } else if (listener == null) {
            this.WT = null;
        } else {
            this.WT = new b(listener, this.AS);
            if (this.WS != null) {
                kf();
            }
        }
    }
}
