package com.google.android.gms.common.data;

import android.content.ContentValues;
import android.database.AbstractWindowedCursor;
import android.database.CharArrayBuffer;
import android.database.CursorIndexOutOfBoundsException;
import android.database.CursorWindow;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.util.Log;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.internal.fb;
import com.google.android.gms.internal.fo;
import com.google.android.gms.internal.fq;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public final class DataHolder implements SafeParcelable {
    private final int Ah;
    private final String[] BH;
    Bundle BI;
    private final CursorWindow[] BJ;
    private final Bundle BK;
    int[] BL;
    int BM;
    private Object BN;
    private boolean BO;
    boolean mClosed;
    private final int xH;
    public static final DataHolderCreator CREATOR = new DataHolderCreator();
    private static final Builder BP = new Builder(new String[0], null) { // from class: com.google.android.gms.common.data.DataHolder.1
        @Override // com.google.android.gms.common.data.DataHolder.Builder
        public Builder withRow(ContentValues values) {
            throw new UnsupportedOperationException("Cannot add data to empty builder");
        }

        @Override // com.google.android.gms.common.data.DataHolder.Builder
        public Builder withRow(HashMap<String, Object> row) {
            throw new UnsupportedOperationException("Cannot add data to empty builder");
        }
    };

    public static class Builder {
        private final String[] BH;
        private final ArrayList<HashMap<String, Object>> BQ;
        private final String BR;
        private final HashMap<Object, Integer> BS;
        private boolean BT;
        private String BU;

        private Builder(String[] columns, String uniqueColumn) {
            this.BH = (String[]) fq.f(columns);
            this.BQ = new ArrayList<>();
            this.BR = uniqueColumn;
            this.BS = new HashMap<>();
            this.BT = false;
            this.BU = null;
        }

        private void a(HashMap<String, Object> map) {
            Object obj = map.get(this.BR);
            if (obj == null) {
                return;
            }
            Integer numRemove = this.BS.remove(obj);
            if (numRemove != null) {
                this.BQ.remove(numRemove.intValue());
            }
            this.BS.put(obj, Integer.valueOf(this.BQ.size()));
        }

        private void et() {
            if (this.BR != null) {
                this.BS.clear();
                int size = this.BQ.size();
                for (int i = 0; i < size; i++) {
                    Object obj = this.BQ.get(i).get(this.BR);
                    if (obj != null) {
                        this.BS.put(obj, Integer.valueOf(i));
                    }
                }
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        public DataHolder build(int i) {
            return new DataHolder(this, i, (Bundle) null);
        }

        public DataHolder build(int statusCode, Bundle metadata) {
            return new DataHolder(this, statusCode, metadata, -1);
        }

        public DataHolder build(int statusCode, Bundle metadata, int maxResults) {
            return new DataHolder(this, statusCode, metadata, maxResults);
        }

        public int getCount() {
            return this.BQ.size();
        }

        public Builder removeRowsWithValue(String column, Object value) {
            for (int size = this.BQ.size() - 1; size >= 0; size--) {
                if (fo.equal(this.BQ.get(size).get(column), value)) {
                    this.BQ.remove(size);
                }
            }
            return this;
        }

        public Builder sort(String sortColumn) {
            fb.d(sortColumn);
            if (!this.BT || !sortColumn.equals(this.BU)) {
                Collections.sort(this.BQ, new a(sortColumn));
                et();
                this.BT = true;
                this.BU = sortColumn;
            }
            return this;
        }

        public Builder withRow(ContentValues values) {
            fb.d(values);
            HashMap<String, Object> map = new HashMap<>(values.size());
            for (Map.Entry<String, Object> entry : values.valueSet()) {
                map.put(entry.getKey(), entry.getValue());
            }
            return withRow(map);
        }

        public Builder withRow(HashMap<String, Object> row) {
            fb.d(row);
            if (this.BR != null) {
                a(row);
            }
            this.BQ.add(row);
            this.BT = false;
            return this;
        }
    }

    private static final class a implements Comparator<HashMap<String, Object>> {
        private final String BV;

        a(String str) {
            this.BV = (String) fq.f(str);
        }

        @Override // java.util.Comparator
        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
        public int compare(HashMap<String, Object> map, HashMap<String, Object> map2) {
            Object objF = fq.f(map.get(this.BV));
            Object objF2 = fq.f(map2.get(this.BV));
            if (objF.equals(objF2)) {
                return 0;
            }
            if (objF instanceof Boolean) {
                return ((Boolean) objF).compareTo((Boolean) objF2);
            }
            if (objF instanceof Long) {
                return ((Long) objF).compareTo((Long) objF2);
            }
            if (objF instanceof Integer) {
                return ((Integer) objF).compareTo((Integer) objF2);
            }
            if (objF instanceof String) {
                return ((String) objF).compareTo((String) objF2);
            }
            throw new IllegalArgumentException("Unknown type for lValue " + objF);
        }
    }

    DataHolder(int versionCode, String[] columns, CursorWindow[] windows, int statusCode, Bundle metadata) {
        this.mClosed = false;
        this.BO = true;
        this.xH = versionCode;
        this.BH = columns;
        this.BJ = windows;
        this.Ah = statusCode;
        this.BK = metadata;
    }

    public DataHolder(AbstractWindowedCursor cursor, int statusCode, Bundle metadata) {
        this(cursor.getColumnNames(), a(cursor), statusCode, metadata);
    }

    private DataHolder(Builder builder, int statusCode, Bundle metadata) {
        this(builder.BH, a(builder, -1), statusCode, metadata);
    }

    private DataHolder(Builder builder, int statusCode, Bundle metadata, int maxResults) {
        this(builder.BH, a(builder, maxResults), statusCode, metadata);
    }

    public DataHolder(String[] columns, CursorWindow[] windows, int statusCode, Bundle metadata) {
        this.mClosed = false;
        this.BO = true;
        this.xH = 1;
        this.BH = (String[]) fq.f(columns);
        this.BJ = (CursorWindow[]) fq.f(windows);
        this.Ah = statusCode;
        this.BK = metadata;
        validateContents();
    }

    private static CursorWindow[] a(AbstractWindowedCursor abstractWindowedCursor) {
        int numRows;
        ArrayList arrayList = new ArrayList();
        try {
            int count = abstractWindowedCursor.getCount();
            CursorWindow window = abstractWindowedCursor.getWindow();
            if (window == null || window.getStartPosition() != 0) {
                numRows = 0;
            } else {
                window.acquireReference();
                abstractWindowedCursor.setWindow(null);
                arrayList.add(window);
                numRows = window.getNumRows();
            }
            while (numRows < count && abstractWindowedCursor.moveToPosition(numRows)) {
                CursorWindow window2 = abstractWindowedCursor.getWindow();
                if (window2 != null) {
                    window2.acquireReference();
                    abstractWindowedCursor.setWindow(null);
                } else {
                    window2 = new CursorWindow(false);
                    window2.setStartPosition(numRows);
                    abstractWindowedCursor.fillWindow(numRows, window2);
                }
                if (window2.getNumRows() == 0) {
                    break;
                }
                arrayList.add(window2);
                numRows = window2.getNumRows() + window2.getStartPosition();
            }
            abstractWindowedCursor.close();
            return (CursorWindow[]) arrayList.toArray(new CursorWindow[arrayList.size()]);
        } catch (Throwable th) {
            abstractWindowedCursor.close();
            throw th;
        }
    }

    private static CursorWindow[] a(Builder builder, int i) {
        int i2;
        int i3;
        int i4;
        CursorWindow cursorWindow;
        if (builder.BH.length == 0) {
            return new CursorWindow[0];
        }
        List listSubList = (i < 0 || i >= builder.BQ.size()) ? builder.BQ : builder.BQ.subList(0, i);
        int size = listSubList.size();
        CursorWindow cursorWindow2 = new CursorWindow(false);
        ArrayList arrayList = new ArrayList();
        arrayList.add(cursorWindow2);
        cursorWindow2.setNumColumns(builder.BH.length);
        int i5 = 0;
        int i6 = 0;
        while (i5 < size) {
            try {
                if (cursorWindow2.allocRow()) {
                    i2 = i6;
                } else {
                    Log.d("DataHolder", "Allocating additional cursor window for large data set (row " + i5 + ")");
                    cursorWindow2 = new CursorWindow(false);
                    cursorWindow2.setStartPosition(i5);
                    cursorWindow2.setNumColumns(builder.BH.length);
                    arrayList.add(cursorWindow2);
                    if (!cursorWindow2.allocRow()) {
                        Log.e("DataHolder", "Unable to allocate row to hold data.");
                        arrayList.remove(cursorWindow2);
                        return (CursorWindow[]) arrayList.toArray(new CursorWindow[arrayList.size()]);
                    }
                    i2 = 0;
                }
                Map map = (Map) listSubList.get(i5);
                boolean zPutDouble = true;
                for (int i7 = 0; i7 < builder.BH.length && zPutDouble; i7++) {
                    String str = builder.BH[i7];
                    Object obj = map.get(str);
                    if (obj == null) {
                        zPutDouble = cursorWindow2.putNull(i2, i7);
                    } else if (obj instanceof String) {
                        zPutDouble = cursorWindow2.putString((String) obj, i2, i7);
                    } else if (obj instanceof Long) {
                        zPutDouble = cursorWindow2.putLong(((Long) obj).longValue(), i2, i7);
                    } else if (obj instanceof Integer) {
                        zPutDouble = cursorWindow2.putLong(((Integer) obj).intValue(), i2, i7);
                    } else if (obj instanceof Boolean) {
                        zPutDouble = cursorWindow2.putLong(((Boolean) obj).booleanValue() ? 1L : 0L, i2, i7);
                    } else if (obj instanceof byte[]) {
                        zPutDouble = cursorWindow2.putBlob((byte[]) obj, i2, i7);
                    } else {
                        if (!(obj instanceof Double)) {
                            throw new IllegalArgumentException("Unsupported object for column " + str + ": " + obj);
                        }
                        zPutDouble = cursorWindow2.putDouble(((Double) obj).doubleValue(), i2, i7);
                    }
                }
                if (zPutDouble) {
                    i3 = i2 + 1;
                    i4 = i5;
                    cursorWindow = cursorWindow2;
                } else {
                    Log.d("DataHolder", "Couldn't populate window data for row " + i5 + " - allocating new window.");
                    cursorWindow2.freeLastRow();
                    CursorWindow cursorWindow3 = new CursorWindow(false);
                    cursorWindow3.setNumColumns(builder.BH.length);
                    arrayList.add(cursorWindow3);
                    i4 = i5 - 1;
                    cursorWindow = cursorWindow3;
                    i3 = 0;
                }
                cursorWindow2 = cursorWindow;
                i5 = i4 + 1;
                i6 = i3;
            } catch (RuntimeException e) {
                int size2 = arrayList.size();
                for (int i8 = 0; i8 < size2; i8++) {
                    ((CursorWindow) arrayList.get(i8)).close();
                }
                throw e;
            }
        }
        return (CursorWindow[]) arrayList.toArray(new CursorWindow[arrayList.size()]);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static Builder builder(String[] strArr) {
        return new Builder(strArr, null);
    }

    public static Builder builder(String[] columns, String uniqueColumn) {
        fq.f(uniqueColumn);
        return new Builder(columns, uniqueColumn);
    }

    private void e(String str, int i) {
        if (this.BI == null || !this.BI.containsKey(str)) {
            throw new IllegalArgumentException("No such column: " + str);
        }
        if (isClosed()) {
            throw new IllegalArgumentException("Buffer is closed.");
        }
        if (i < 0 || i >= this.BM) {
            throw new CursorIndexOutOfBoundsException(i, this.BM);
        }
    }

    public static DataHolder empty(int statusCode) {
        return empty(statusCode, null);
    }

    public static DataHolder empty(int statusCode, Bundle metadata) {
        return new DataHolder(BP, statusCode, metadata);
    }

    public int G(int i) {
        int i2 = 0;
        fq.x(i >= 0 && i < this.BM);
        while (i2 < this.BL.length) {
            if (i < this.BL[i2]) {
                i2--;
                break;
            }
            i2++;
        }
        return i2 == this.BL.length ? i2 - 1 : i2;
    }

    public void c(Object obj) {
        this.BN = obj;
    }

    public void close() {
        synchronized (this) {
            if (!this.mClosed) {
                this.mClosed = true;
                for (int i = 0; i < this.BJ.length; i++) {
                    this.BJ[i].close();
                }
            }
        }
    }

    public void copyToBuffer(String column, int row, int windowIndex, CharArrayBuffer dataOut) {
        e(column, row);
        this.BJ[windowIndex].copyStringToBuffer(row, this.BI.getInt(column), dataOut);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    String[] er() {
        return this.BH;
    }

    CursorWindow[] es() {
        return this.BJ;
    }

    protected void finalize() throws Throwable {
        try {
            if (this.BO && this.BJ.length > 0 && !isClosed()) {
                Log.e("DataBuffer", "Internal data leak within a DataBuffer object detected!  Be sure to explicitly call close() on all DataBuffer extending objects when you are done with them. (" + (this.BN == null ? "internal object: " + toString() : this.BN.toString()) + ")");
                close();
            }
        } finally {
            super.finalize();
        }
    }

    public boolean getBoolean(String column, int row, int windowIndex) {
        e(column, row);
        return Long.valueOf(this.BJ[windowIndex].getLong(row, this.BI.getInt(column))).longValue() == 1;
    }

    public byte[] getByteArray(String column, int row, int windowIndex) {
        e(column, row);
        return this.BJ[windowIndex].getBlob(row, this.BI.getInt(column));
    }

    public int getCount() {
        return this.BM;
    }

    public double getDouble(String column, int row, int windowIndex) {
        e(column, row);
        return this.BJ[windowIndex].getDouble(row, this.BI.getInt(column));
    }

    public int getInteger(String column, int row, int windowIndex) {
        e(column, row);
        return this.BJ[windowIndex].getInt(row, this.BI.getInt(column));
    }

    public long getLong(String column, int row, int windowIndex) {
        e(column, row);
        return this.BJ[windowIndex].getLong(row, this.BI.getInt(column));
    }

    public Bundle getMetadata() {
        return this.BK;
    }

    public int getStatusCode() {
        return this.Ah;
    }

    public String getString(String column, int row, int windowIndex) {
        e(column, row);
        return this.BJ[windowIndex].getString(row, this.BI.getInt(column));
    }

    int getVersionCode() {
        return this.xH;
    }

    public boolean hasColumn(String column) {
        return this.BI.containsKey(column);
    }

    public boolean hasNull(String column, int row, int windowIndex) {
        e(column, row);
        return this.BJ[windowIndex].isNull(row, this.BI.getInt(column));
    }

    public boolean isClosed() {
        boolean z;
        synchronized (this) {
            z = this.mClosed;
        }
        return z;
    }

    public Uri parseUri(String column, int row, int windowIndex) {
        String string = getString(column, row, windowIndex);
        if (string == null) {
            return null;
        }
        return Uri.parse(string);
    }

    public void validateContents() {
        this.BI = new Bundle();
        for (int i = 0; i < this.BH.length; i++) {
            this.BI.putInt(this.BH[i], i);
        }
        this.BL = new int[this.BJ.length];
        int numRows = 0;
        for (int i2 = 0; i2 < this.BJ.length; i2++) {
            this.BL[i2] = numRows;
            numRows += this.BJ[i2].getNumRows() - (numRows - this.BJ[i2].getStartPosition());
        }
        this.BM = numRows;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        DataHolderCreator.a(this, dest, flags);
    }
}
