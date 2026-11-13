package com.google.android.gms.common.data;

import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public abstract class d<T> extends DataBuffer<T> {
    private boolean BW;
    private ArrayList<Integer> BX;

    protected d(DataHolder dataHolder) {
        super(dataHolder);
        this.BW = false;
    }

    private void eu() {
        synchronized (this) {
            if (!this.BW) {
                int count = this.BB.getCount();
                this.BX = new ArrayList<>();
                if (count > 0) {
                    this.BX.add(0);
                    String primaryDataMarkerColumn = getPrimaryDataMarkerColumn();
                    String string = this.BB.getString(primaryDataMarkerColumn, 0, this.BB.G(0));
                    int i = 1;
                    while (i < count) {
                        String string2 = this.BB.getString(primaryDataMarkerColumn, i, this.BB.G(i));
                        if (string2.equals(string)) {
                            string2 = string;
                        } else {
                            this.BX.add(Integer.valueOf(i));
                        }
                        i++;
                        string = string2;
                    }
                }
                this.BW = true;
            }
        }
    }

    int H(int i) {
        if (i < 0 || i >= this.BX.size()) {
            throw new IllegalArgumentException("Position " + i + " is out of bounds for this buffer");
        }
        return this.BX.get(i).intValue();
    }

    protected int I(int i) {
        if (i < 0 || i == this.BX.size()) {
            return 0;
        }
        return i == this.BX.size() + (-1) ? this.BB.getCount() - this.BX.get(i).intValue() : this.BX.get(i + 1).intValue() - this.BX.get(i).intValue();
    }

    protected abstract T c(int i, int i2);

    @Override // com.google.android.gms.common.data.DataBuffer
    public final T get(int position) {
        eu();
        return c(H(position), I(position));
    }

    @Override // com.google.android.gms.common.data.DataBuffer
    public int getCount() {
        eu();
        return this.BX.size();
    }

    protected abstract String getPrimaryDataMarkerColumn();
}
