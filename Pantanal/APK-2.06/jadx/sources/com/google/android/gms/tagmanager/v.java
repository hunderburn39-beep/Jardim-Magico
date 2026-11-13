package com.google.android.gms.tagmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.text.TextUtils;
import com.google.android.gms.internal.gl;
import com.google.android.gms.internal.gn;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import org.anddev.andengine.entity.layer.tiled.tmx.util.constants.TMXConstants;

/* JADX INFO: loaded from: classes.dex */
class v implements DataLayer.c {
    private static final String XB = String.format("CREATE TABLE IF NOT EXISTS %s ( '%s' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, '%s' STRING NOT NULL, '%s' BLOB NOT NULL, '%s' INTEGER NOT NULL);", "datalayer", "ID", "key", TMXConstants.TAG_PROPERTY_ATTRIBUTE_VALUE, "expires");
    private gl Wv;
    private final Executor XC;
    private a XD;
    private int XE;
    private final Context mContext;

    class a extends SQLiteOpenHelper {
        a(Context context, String str) {
            super(context, str, (SQLiteDatabase.CursorFactory) null, 1);
        }

        private void a(SQLiteDatabase sQLiteDatabase) {
            Cursor cursorRawQuery = sQLiteDatabase.rawQuery("SELECT * FROM datalayer WHERE 0", null);
            HashSet hashSet = new HashSet();
            try {
                for (String str : cursorRawQuery.getColumnNames()) {
                    hashSet.add(str);
                }
                cursorRawQuery.close();
                if (!hashSet.remove("key") || !hashSet.remove(TMXConstants.TAG_PROPERTY_ATTRIBUTE_VALUE) || !hashSet.remove("ID") || !hashSet.remove("expires")) {
                    throw new SQLiteException("Database column missing");
                }
                if (!hashSet.isEmpty()) {
                    throw new SQLiteException("Database has extra columns");
                }
            } catch (Throwable th) {
                cursorRawQuery.close();
                throw th;
            }
        }

        /* JADX WARN: Code duplicated, block: B:16:0x0048  */
        private boolean a(String str, SQLiteDatabase sQLiteDatabase) throws Throwable {
            Cursor cursor;
            Cursor cursor2 = null;
            try {
                Cursor cursorQuery = sQLiteDatabase.query("SQLITE_MASTER", new String[]{"name"}, "name=?", new String[]{str}, null, null, null);
                try {
                    boolean zMoveToFirst = cursorQuery.moveToFirst();
                    if (cursorQuery == null) {
                        return zMoveToFirst;
                    }
                    cursorQuery.close();
                    return zMoveToFirst;
                } catch (SQLiteException e) {
                    cursor = cursorQuery;
                    try {
                        bh.z("Error querying for table " + str);
                        if (cursor != null) {
                            cursor.close();
                        }
                        return false;
                    } catch (Throwable th) {
                        cursor2 = cursor;
                        th = th;
                        if (cursor2 != null) {
                            cursor2.close();
                        }
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    cursor2 = cursorQuery;
                    if (cursor2 != null) {
                        cursor2.close();
                    }
                    throw th;
                }
            } catch (SQLiteException e2) {
                cursor = null;
            } catch (Throwable th3) {
                th = th3;
            }
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public SQLiteDatabase getWritableDatabase() {
            SQLiteDatabase writableDatabase = null;
            try {
                writableDatabase = super.getWritableDatabase();
            } catch (SQLiteException e) {
                v.this.mContext.getDatabasePath("google_tagmanager.db").delete();
            }
            return writableDatabase == null ? super.getWritableDatabase() : writableDatabase;
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public void onCreate(SQLiteDatabase db) {
            ak.G(db.getPath());
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public void onOpen(SQLiteDatabase db) {
            if (Build.VERSION.SDK_INT < 15) {
                Cursor cursorRawQuery = db.rawQuery("PRAGMA journal_mode=memory", null);
                try {
                    cursorRawQuery.moveToFirst();
                    cursorRawQuery.close();
                } catch (Throwable th) {
                    cursorRawQuery.close();
                    throw th;
                }
            }
            if (a("datalayer", db)) {
                a(db);
            } else {
                db.execSQL(v.XB);
            }
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }
    }

    private static class b {
        final byte[] XK;
        final String Xy;

        b(String str, byte[] bArr) {
            this.Xy = str;
            this.XK = bArr;
        }

        public String toString() {
            return "KeyAndSerialized: key = " + this.Xy + " serialized hash = " + Arrays.hashCode(this.XK);
        }
    }

    public v(Context context) {
        this(context, gn.ft(), "google_tagmanager.db", 2000, Executors.newSingleThreadExecutor());
    }

    v(Context context, gl glVar, String str, int i, Executor executor) {
        this.mContext = context;
        this.Wv = glVar;
        this.XE = i;
        this.XC = executor;
        this.XD = new a(this.mContext, str);
    }

    private SQLiteDatabase L(String str) {
        try {
            return this.XD.getWritableDatabase();
        } catch (SQLiteException e) {
            bh.z(str);
            return null;
        }
    }

    private List<DataLayer.a> b(List<b> list) {
        ArrayList arrayList = new ArrayList();
        for (b bVar : list) {
            arrayList.add(new DataLayer.a(bVar.Xy, j(bVar.XK)));
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void b(List<b> list, long j) {
        try {
            long jCurrentTimeMillis = this.Wv.currentTimeMillis();
            u(jCurrentTimeMillis);
            cb(list.size());
            c(list, jCurrentTimeMillis + j);
            kv();
        } catch (Throwable th) {
            kv();
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void by(String str) {
        SQLiteDatabase sQLiteDatabaseL = L("Error opening database for clearKeysWithPrefix.");
        try {
            if (sQLiteDatabaseL == null) {
                return;
            }
            bh.y("Cleared " + sQLiteDatabaseL.delete("datalayer", "key = ? OR key LIKE ?", new String[]{str, str + ".%"}) + " items");
        } catch (SQLiteException e) {
            bh.z("Error deleting entries with key prefix: " + str + " (" + e + ").");
        } finally {
            kv();
        }
    }

    private List<b> c(List<DataLayer.a> list) {
        ArrayList arrayList = new ArrayList();
        for (DataLayer.a aVar : list) {
            arrayList.add(new b(aVar.Xy, j(aVar.Xz)));
        }
        return arrayList;
    }

    private void c(List<b> list, long j) {
        SQLiteDatabase sQLiteDatabaseL = L("Error opening database for writeEntryToDatabase.");
        if (sQLiteDatabaseL == null) {
            return;
        }
        for (b bVar : list) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("expires", Long.valueOf(j));
            contentValues.put("key", bVar.Xy);
            contentValues.put(TMXConstants.TAG_PROPERTY_ATTRIBUTE_VALUE, bVar.XK);
            sQLiteDatabaseL.insert("datalayer", null, contentValues);
        }
    }

    private void cb(int i) throws Throwable {
        int iKu = (ku() - this.XE) + i;
        if (iKu > 0) {
            List<String> listCc = cc(iKu);
            bh.x("DataLayer store full, deleting " + listCc.size() + " entries to make room.");
            g((String[]) listCc.toArray(new String[0]));
        }
    }

    /* JADX WARN: Code duplicated, block: B:25:0x0082  */
    private List<String> cc(int i) throws Throwable {
        Cursor cursorQuery;
        ArrayList arrayList = new ArrayList();
        if (i <= 0) {
            bh.z("Invalid maxEntries specified. Skipping.");
            return arrayList;
        }
        SQLiteDatabase sQLiteDatabaseL = L("Error opening database for peekEntryIds.");
        if (sQLiteDatabaseL == null) {
            return arrayList;
        }
        try {
            cursorQuery = sQLiteDatabaseL.query("datalayer", new String[]{"ID"}, null, null, null, null, String.format("%s ASC", "ID"), Integer.toString(i));
            try {
                try {
                    if (cursorQuery.moveToFirst()) {
                        do {
                            arrayList.add(String.valueOf(cursorQuery.getLong(0)));
                        } while (cursorQuery.moveToNext());
                    }
                    if (cursorQuery != null) {
                        cursorQuery.close();
                    }
                } catch (SQLiteException e) {
                    e = e;
                    bh.z("Error in peekEntries fetching entryIds: " + e.getMessage());
                    if (cursorQuery != null) {
                        cursorQuery.close();
                    }
                }
            } catch (Throwable th) {
                th = th;
                if (cursorQuery != null) {
                    cursorQuery.close();
                }
                throw th;
            }
        } catch (SQLiteException e2) {
            e = e2;
            cursorQuery = null;
        } catch (Throwable th2) {
            th = th2;
            cursorQuery = null;
            if (cursorQuery != null) {
                cursorQuery.close();
            }
            throw th;
        }
        return arrayList;
    }

    private void g(String[] strArr) {
        SQLiteDatabase sQLiteDatabaseL;
        if (strArr == null || strArr.length == 0 || (sQLiteDatabaseL = L("Error opening database for deleteEntries.")) == null) {
            return;
        }
        try {
            sQLiteDatabaseL.delete("datalayer", String.format("%s in (%s)", "ID", TextUtils.join(",", Collections.nCopies(strArr.length, "?"))), strArr);
        } catch (SQLiteException e) {
            bh.z("Error deleting entries " + Arrays.toString(strArr));
        }
    }

    private Object j(byte[] bArr) throws Throwable {
        ObjectInputStream objectInputStream;
        Throwable th;
        Object object = null;
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bArr);
        try {
            objectInputStream = new ObjectInputStream(byteArrayInputStream);
            try {
                object = objectInputStream.readObject();
                if (objectInputStream != null) {
                    try {
                        objectInputStream.close();
                    } catch (IOException e) {
                    }
                }
                byteArrayInputStream.close();
            } catch (IOException e2) {
                if (objectInputStream != null) {
                    try {
                        objectInputStream.close();
                    } catch (IOException e3) {
                    }
                }
                byteArrayInputStream.close();
            } catch (ClassNotFoundException e4) {
                if (objectInputStream != null) {
                    try {
                        objectInputStream.close();
                    } catch (IOException e5) {
                    }
                }
                byteArrayInputStream.close();
            } catch (Throwable th2) {
                th = th2;
                if (objectInputStream != null) {
                    try {
                        objectInputStream.close();
                    } catch (IOException e6) {
                        throw th;
                    }
                }
                byteArrayInputStream.close();
                throw th;
            }
        } catch (IOException e7) {
            objectInputStream = null;
        } catch (ClassNotFoundException e8) {
            objectInputStream = null;
        } catch (Throwable th3) {
            objectInputStream = null;
            th = th3;
        }
        return object;
    }

    private byte[] j(Object obj) throws Throwable {
        ObjectOutputStream objectOutputStream;
        Throwable th;
        byte[] byteArray = null;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            try {
                objectOutputStream.writeObject(obj);
                byteArray = byteArrayOutputStream.toByteArray();
                if (objectOutputStream != null) {
                    try {
                        objectOutputStream.close();
                    } catch (IOException e) {
                    }
                }
                byteArrayOutputStream.close();
            } catch (IOException e2) {
                if (objectOutputStream != null) {
                    try {
                        objectOutputStream.close();
                    } catch (IOException e3) {
                    }
                }
                byteArrayOutputStream.close();
            } catch (Throwable th2) {
                th = th2;
                if (objectOutputStream != null) {
                    try {
                        objectOutputStream.close();
                    } catch (IOException e4) {
                        throw th;
                    }
                }
                byteArrayOutputStream.close();
                throw th;
            }
        } catch (IOException e5) {
            objectOutputStream = null;
        } catch (Throwable th3) {
            objectOutputStream = null;
            th = th3;
        }
        return byteArray;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public List<DataLayer.a> ks() {
        try {
            u(this.Wv.currentTimeMillis());
            return b(kt());
        } finally {
            kv();
        }
    }

    private List<b> kt() {
        SQLiteDatabase sQLiteDatabaseL = L("Error opening database for loadSerialized.");
        ArrayList arrayList = new ArrayList();
        if (sQLiteDatabaseL == null) {
            return arrayList;
        }
        Cursor cursorQuery = sQLiteDatabaseL.query("datalayer", new String[]{"key", TMXConstants.TAG_PROPERTY_ATTRIBUTE_VALUE}, null, null, null, null, "ID", null);
        while (cursorQuery.moveToNext()) {
            try {
                arrayList.add(new b(cursorQuery.getString(0), cursorQuery.getBlob(1)));
            } catch (Throwable th) {
                cursorQuery.close();
                throw th;
            }
        }
        cursorQuery.close();
        return arrayList;
    }

    private int ku() {
        Cursor cursorRawQuery = null;
        int i = 0;
        SQLiteDatabase sQLiteDatabaseL = L("Error opening database for getNumStoredEntries.");
        try {
            if (sQLiteDatabaseL != null) {
                try {
                    cursorRawQuery = sQLiteDatabaseL.rawQuery("SELECT COUNT(*) from datalayer", null);
                    i = cursorRawQuery.moveToFirst() ? (int) cursorRawQuery.getLong(0) : 0;
                } catch (SQLiteException e) {
                    bh.z("Error getting numStoredEntries");
                }
            }
            return i;
        } finally {
            if (cursorRawQuery != null) {
                cursorRawQuery.close();
            }
        }
    }

    private void kv() {
        try {
            this.XD.close();
        } catch (SQLiteException e) {
        }
    }

    private void u(long j) {
        SQLiteDatabase sQLiteDatabaseL = L("Error opening database for deleteOlderThan.");
        if (sQLiteDatabaseL == null) {
            return;
        }
        try {
            bh.y("Deleted " + sQLiteDatabaseL.delete("datalayer", "expires <= ?", new String[]{Long.toString(j)}) + " expired items");
        } catch (SQLiteException e) {
            bh.z("Error deleting old entries.");
        }
    }

    @Override // com.google.android.gms.tagmanager.DataLayer.c
    public void a(final DataLayer.c.a aVar) {
        this.XC.execute(new Runnable() { // from class: com.google.android.gms.tagmanager.v.2
            @Override // java.lang.Runnable
            public void run() {
                aVar.a(v.this.ks());
            }
        });
    }

    @Override // com.google.android.gms.tagmanager.DataLayer.c
    public void a(List<DataLayer.a> list, final long j) {
        final List<b> listC = c(list);
        this.XC.execute(new Runnable() { // from class: com.google.android.gms.tagmanager.v.1
            @Override // java.lang.Runnable
            public void run() {
                v.this.b(listC, j);
            }
        });
    }

    @Override // com.google.android.gms.tagmanager.DataLayer.c
    public void bx(final String str) {
        this.XC.execute(new Runnable() { // from class: com.google.android.gms.tagmanager.v.3
            @Override // java.lang.Runnable
            public void run() {
                v.this.by(str);
            }
        });
    }
}
