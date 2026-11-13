package com.google.android.gms.tagmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.text.TextUtils;
import com.google.android.gms.internal.gl;
import com.google.android.gms.internal.gn;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import org.apache.http.impl.client.DefaultHttpClient;

/* JADX INFO: loaded from: classes.dex */
class ca implements at {
    private static final String vx = String.format("CREATE TABLE IF NOT EXISTS %s ( '%s' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, '%s' INTEGER NOT NULL, '%s' TEXT NOT NULL,'%s' INTEGER NOT NULL);", "gtm_hits", "hit_id", "hit_time", "hit_url", "hit_first_send_time");
    private gl Wv;
    private final b YI;
    private volatile ab YJ;
    private final au YK;
    private final Context mContext;
    private final String vA;
    private long vC;
    private final int vD;

    class a implements da.a {
        a() {
        }

        @Override // com.google.android.gms.tagmanager.da.a
        public void a(ap apVar) {
            ca.this.v(apVar.cP());
        }

        @Override // com.google.android.gms.tagmanager.da.a
        public void b(ap apVar) {
            ca.this.v(apVar.cP());
            bh.y("Permanent failure dispatching hitId: " + apVar.cP());
        }

        @Override // com.google.android.gms.tagmanager.da.a
        public void c(ap apVar) {
            long jKD = apVar.kD();
            if (jKD == 0) {
                ca.this.c(apVar.cP(), ca.this.Wv.currentTimeMillis());
            } else if (jKD + 14400000 < ca.this.Wv.currentTimeMillis()) {
                ca.this.v(apVar.cP());
                bh.y("Giving up on failed hitId: " + apVar.cP());
            }
        }
    }

    class b extends SQLiteOpenHelper {
        private boolean vF;
        private long vG;

        b(Context context, String str) {
            super(context, str, (SQLiteDatabase.CursorFactory) null, 1);
            this.vG = 0L;
        }

        private void a(SQLiteDatabase sQLiteDatabase) {
            Cursor cursorRawQuery = sQLiteDatabase.rawQuery("SELECT * FROM gtm_hits WHERE 0", null);
            HashSet hashSet = new HashSet();
            try {
                for (String str : cursorRawQuery.getColumnNames()) {
                    hashSet.add(str);
                }
                cursorRawQuery.close();
                if (!hashSet.remove("hit_id") || !hashSet.remove("hit_url") || !hashSet.remove("hit_time") || !hashSet.remove("hit_first_send_time")) {
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
            if (this.vF && this.vG + 3600000 > ca.this.Wv.currentTimeMillis()) {
                throw new SQLiteException("Database creation failed");
            }
            SQLiteDatabase writableDatabase = null;
            this.vF = true;
            this.vG = ca.this.Wv.currentTimeMillis();
            try {
                writableDatabase = super.getWritableDatabase();
            } catch (SQLiteException e) {
                ca.this.mContext.getDatabasePath(ca.this.vA).delete();
            }
            if (writableDatabase == null) {
                writableDatabase = super.getWritableDatabase();
            }
            this.vF = false;
            return writableDatabase;
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
            if (a("gtm_hits", db)) {
                a(db);
            } else {
                db.execSQL(ca.vx);
            }
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }
    }

    ca(au auVar, Context context) {
        this(auVar, context, "gtm_urls.db", 2000);
    }

    ca(au auVar, Context context, String str, int i) {
        this.mContext = context.getApplicationContext();
        this.vA = str;
        this.YK = auVar;
        this.Wv = gn.ft();
        this.YI = new b(this.mContext, this.vA);
        this.YJ = new da(new DefaultHttpClient(), this.mContext, new a());
        this.vC = 0L;
        this.vD = i;
    }

    private SQLiteDatabase L(String str) {
        try {
            return this.YI.getWritableDatabase();
        } catch (SQLiteException e) {
            bh.z(str);
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c(long j, long j2) {
        SQLiteDatabase sQLiteDatabaseL = L("Error opening database for getNumStoredHits.");
        if (sQLiteDatabaseL == null) {
            return;
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put("hit_first_send_time", Long.valueOf(j2));
        try {
            sQLiteDatabaseL.update("gtm_hits", contentValues, "hit_id=?", new String[]{String.valueOf(j)});
        } catch (SQLiteException e) {
            bh.z("Error setting HIT_FIRST_DISPATCH_TIME for hitId: " + j);
            v(j);
        }
    }

    private void cV() throws Throwable {
        int iCX = (cX() - this.vD) + 1;
        if (iCX > 0) {
            List<String> listS = s(iCX);
            bh.y("Store full, deleting " + listS.size() + " hits to make room.");
            a((String[]) listS.toArray(new String[0]));
        }
    }

    private void f(long j, String str) {
        SQLiteDatabase sQLiteDatabaseL = L("Error opening database for putHit");
        if (sQLiteDatabaseL == null) {
            return;
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put("hit_time", Long.valueOf(j));
        contentValues.put("hit_url", str);
        contentValues.put("hit_first_send_time", (Integer) 0);
        try {
            sQLiteDatabaseL.insert("gtm_hits", null, contentValues);
            this.YK.r(false);
        } catch (SQLiteException e) {
            bh.z("Error storing hit");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void v(long j) {
        a(new String[]{String.valueOf(j)});
    }

    void a(String[] strArr) {
        SQLiteDatabase sQLiteDatabaseL;
        if (strArr == null || strArr.length == 0 || (sQLiteDatabaseL = L("Error opening database for deleteHits.")) == null) {
            return;
        }
        try {
            sQLiteDatabaseL.delete("gtm_hits", String.format("HIT_ID in (%s)", TextUtils.join(",", Collections.nCopies(strArr.length, "?"))), strArr);
            this.YK.r(cX() == 0);
        } catch (SQLiteException e) {
            bh.z("Error deleting hits");
        }
    }

    @Override // com.google.android.gms.tagmanager.at
    public void bW() throws Throwable {
        bh.y("GTM Dispatch running...");
        if (this.YJ.ch()) {
            List<ap> listT = t(40);
            if (listT.isEmpty()) {
                bh.y("...nothing to dispatch");
                this.YK.r(true);
            } else {
                this.YJ.d(listT);
                if (kR() > 0) {
                    cx.lG().bW();
                }
            }
        }
    }

    int cW() {
        long jCurrentTimeMillis = this.Wv.currentTimeMillis();
        if (jCurrentTimeMillis <= this.vC + 86400000) {
            return 0;
        }
        this.vC = jCurrentTimeMillis;
        SQLiteDatabase sQLiteDatabaseL = L("Error opening database for deleteStaleHits.");
        if (sQLiteDatabaseL == null) {
            return 0;
        }
        int iDelete = sQLiteDatabaseL.delete("gtm_hits", "HIT_TIME < ?", new String[]{Long.toString(this.Wv.currentTimeMillis() - 2592000000L)});
        this.YK.r(cX() == 0);
        return iDelete;
    }

    int cX() {
        Cursor cursorRawQuery = null;
        int i = 0;
        SQLiteDatabase sQLiteDatabaseL = L("Error opening database for getNumStoredHits.");
        try {
            if (sQLiteDatabaseL != null) {
                try {
                    cursorRawQuery = sQLiteDatabaseL.rawQuery("SELECT COUNT(*) from gtm_hits", null);
                    i = cursorRawQuery.moveToFirst() ? (int) cursorRawQuery.getLong(0) : 0;
                } catch (SQLiteException e) {
                    bh.z("Error getting numStoredHits");
                }
            }
            return i;
        } finally {
            if (cursorRawQuery != null) {
                cursorRawQuery.close();
            }
        }
    }

    @Override // com.google.android.gms.tagmanager.at
    public void e(long j, String str) throws Throwable {
        cW();
        cV();
        f(j, str);
    }

    /* JADX WARN: Code duplicated, block: B:17:0x0040  */
    int kR() throws Throwable {
        Cursor cursor;
        int count;
        Cursor cursor2 = null;
        SQLiteDatabase sQLiteDatabaseL = L("Error opening database for getNumStoredHits.");
        if (sQLiteDatabaseL == null) {
            return 0;
        }
        try {
            Cursor cursorQuery = sQLiteDatabaseL.query("gtm_hits", new String[]{"hit_id", "hit_first_send_time"}, "hit_first_send_time=0", null, null, null, null);
            try {
                count = cursorQuery.getCount();
                if (cursorQuery != null) {
                    cursorQuery.close();
                }
            } catch (SQLiteException e) {
                cursor = cursorQuery;
                try {
                    bh.z("Error getting num untried hits");
                    if (cursor != null) {
                        cursor.close();
                        count = 0;
                    } else {
                        count = 0;
                    }
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
        return count;
    }

    /* JADX WARN: Code duplicated, block: B:25:0x0082  */
    List<String> s(int i) throws Throwable {
        Cursor cursorQuery;
        ArrayList arrayList = new ArrayList();
        if (i <= 0) {
            bh.z("Invalid maxHits specified. Skipping");
            return arrayList;
        }
        SQLiteDatabase sQLiteDatabaseL = L("Error opening database for peekHitIds.");
        if (sQLiteDatabaseL == null) {
            return arrayList;
        }
        try {
            cursorQuery = sQLiteDatabaseL.query("gtm_hits", new String[]{"hit_id"}, null, null, null, null, String.format("%s ASC", "hit_id"), Integer.toString(i));
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
                    bh.z("Error in peekHits fetching hitIds: " + e.getMessage());
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

    /* JADX WARN: Code duplicated, block: B:35:0x00f2  */
    public List<ap> t(int i) throws Throwable {
        SQLiteException sQLiteException;
        Cursor cursor;
        ArrayList arrayList;
        ArrayList arrayList2 = new ArrayList();
        SQLiteDatabase sQLiteDatabaseL = L("Error opening database for peekHits");
        if (sQLiteDatabaseL == null) {
            return arrayList2;
        }
        Cursor cursor2 = null;
        try {
            Cursor cursorQuery = sQLiteDatabaseL.query("gtm_hits", new String[]{"hit_id", "hit_time", "hit_first_send_time"}, null, null, null, null, String.format("%s ASC", "hit_id"), Integer.toString(i));
            try {
                try {
                    ArrayList<ap> arrayList3 = new ArrayList();
                    try {
                        if (cursorQuery.moveToFirst()) {
                            do {
                                arrayList3.add(new ap(cursorQuery.getLong(0), cursorQuery.getLong(1), cursorQuery.getLong(2)));
                            } while (cursorQuery.moveToNext());
                        }
                        if (cursorQuery != null) {
                            cursorQuery.close();
                        }
                        try {
                            try {
                                Cursor cursorQuery2 = sQLiteDatabaseL.query("gtm_hits", new String[]{"hit_id", "hit_url"}, null, null, null, null, String.format("%s ASC", "hit_id"), Integer.toString(i));
                                try {
                                    if (cursorQuery2.moveToFirst()) {
                                        int i2 = 0;
                                        while (true) {
                                            if (((SQLiteCursor) cursorQuery2).getWindow().getNumRows() > 0) {
                                                ((ap) arrayList3.get(i2)).K(cursorQuery2.getString(1));
                                            } else {
                                                bh.z(String.format("HitString for hitId %d too large.  Hit will be deleted.", Long.valueOf(((ap) arrayList3.get(i2)).cP())));
                                            }
                                            int i3 = i2 + 1;
                                            if (!cursorQuery2.moveToNext()) {
                                                break;
                                            }
                                            i2 = i3;
                                        }
                                    }
                                    if (cursorQuery2 != null) {
                                        cursorQuery2.close();
                                    }
                                    return arrayList3;
                                } catch (SQLiteException e) {
                                    e = e;
                                    cursorQuery = cursorQuery2;
                                    bh.z("Error in peekHits fetching hit url: " + e.getMessage());
                                    ArrayList arrayList4 = new ArrayList();
                                    boolean z = false;
                                    for (ap apVar : arrayList3) {
                                        if (TextUtils.isEmpty(apVar.kE())) {
                                            if (z) {
                                                break;
                                            }
                                            z = true;
                                        }
                                        arrayList4.add(apVar);
                                    }
                                    if (cursorQuery != null) {
                                        cursorQuery.close();
                                    }
                                    return arrayList4;
                                } catch (Throwable th) {
                                    th = th;
                                    cursorQuery = cursorQuery2;
                                    if (cursorQuery != null) {
                                        cursorQuery.close();
                                    }
                                    throw th;
                                }
                            } catch (SQLiteException e2) {
                                e = e2;
                            }
                        } catch (Throwable th2) {
                            th = th2;
                        }
                    } catch (SQLiteException e3) {
                        sQLiteException = e3;
                        cursor = cursorQuery;
                        arrayList = arrayList3;
                        try {
                            bh.z("Error in peekHits fetching hitIds: " + sQLiteException.getMessage());
                            if (cursor == null) {
                                return arrayList;
                            }
                            cursor.close();
                            return arrayList;
                        } catch (Throwable th3) {
                            th = th3;
                            cursor2 = cursor;
                            if (cursor2 != null) {
                                cursor2.close();
                            }
                            throw th;
                        }
                    }
                } catch (SQLiteException e4) {
                    sQLiteException = e4;
                    cursor = cursorQuery;
                    arrayList = arrayList2;
                }
            } catch (Throwable th4) {
                th = th4;
                cursor2 = cursorQuery;
                if (cursor2 != null) {
                    cursor2.close();
                }
                throw th;
            }
        } catch (SQLiteException e5) {
            sQLiteException = e5;
            cursor = null;
            arrayList = arrayList2;
        } catch (Throwable th5) {
            th = th5;
        }
    }
}
