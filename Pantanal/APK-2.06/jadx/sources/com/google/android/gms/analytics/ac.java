package com.google.android.gms.analytics;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.text.TextUtils;
import com.google.android.gms.internal.ef;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.http.impl.client.DefaultHttpClient;

/* JADX INFO: loaded from: classes.dex */
class ac implements d {
    private static final String vx = String.format("CREATE TABLE IF NOT EXISTS %s ( '%s' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, '%s' INTEGER NOT NULL, '%s' TEXT NOT NULL, '%s' TEXT NOT NULL, '%s' INTEGER);", "hits2", "hit_id", "hit_time", "hit_url", "hit_string", "hit_app_id");
    private final Context mContext;
    private final e sO;
    private i tg;
    private final String vA;
    private ab vB;
    private long vC;
    private final int vD;
    private final a vy;
    private volatile n vz;

    class a extends SQLiteOpenHelper {
        private boolean vF;
        private long vG;

        a(Context context, String str) {
            super(context, str, (SQLiteDatabase.CursorFactory) null, 1);
            this.vG = 0L;
        }

        private void a(SQLiteDatabase sQLiteDatabase) {
            Cursor cursorRawQuery = sQLiteDatabase.rawQuery("SELECT * FROM hits2 WHERE 0", null);
            HashSet hashSet = new HashSet();
            try {
                for (String str : cursorRawQuery.getColumnNames()) {
                    hashSet.add(str);
                }
                cursorRawQuery.close();
                if (!hashSet.remove("hit_id") || !hashSet.remove("hit_url") || !hashSet.remove("hit_string") || !hashSet.remove("hit_time")) {
                    throw new SQLiteException("Database column missing");
                }
                boolean z = hashSet.remove("hit_app_id") ? false : true;
                if (!hashSet.isEmpty()) {
                    throw new SQLiteException("Database has extra columns");
                }
                if (z) {
                    sQLiteDatabase.execSQL("ALTER TABLE hits2 ADD COLUMN hit_app_id");
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
                        aa.z("Error querying for table " + str);
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
            if (this.vF && this.vG + 3600000 > ac.this.tg.currentTimeMillis()) {
                throw new SQLiteException("Database creation failed");
            }
            SQLiteDatabase writableDatabase = null;
            this.vF = true;
            this.vG = ac.this.tg.currentTimeMillis();
            try {
                writableDatabase = super.getWritableDatabase();
            } catch (SQLiteException e) {
                ac.this.mContext.getDatabasePath(ac.this.vA).delete();
            }
            if (writableDatabase == null) {
                writableDatabase = super.getWritableDatabase();
            }
            this.vF = false;
            return writableDatabase;
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public void onCreate(SQLiteDatabase db) {
            p.G(db.getPath());
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
            if (a("hits2", db)) {
                a(db);
            } else {
                db.execSQL(ac.vx);
            }
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }
    }

    ac(e eVar, Context context) {
        this(eVar, context, "google_analytics_v4.db", 2000);
    }

    ac(e eVar, Context context, String str, int i) {
        this.mContext = context.getApplicationContext();
        this.vA = str;
        this.sO = eVar;
        this.tg = new i() { // from class: com.google.android.gms.analytics.ac.1
            @Override // com.google.android.gms.analytics.i
            public long currentTimeMillis() {
                return System.currentTimeMillis();
            }
        };
        this.vy = new a(this.mContext, this.vA);
        this.vz = new ah(new DefaultHttpClient(), this.mContext);
        this.vC = 0L;
        this.vD = i;
    }

    private SQLiteDatabase L(String str) {
        try {
            return this.vy.getWritableDatabase();
        } catch (SQLiteException e) {
            aa.z(str);
            return null;
        }
    }

    private void a(Map<String, String> map, long j, String str) {
        long j2;
        SQLiteDatabase sQLiteDatabaseL = L("Error opening database for putHit");
        if (sQLiteDatabaseL == null) {
            return;
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put("hit_string", w(map));
        contentValues.put("hit_time", Long.valueOf(j));
        if (map.containsKey("AppUID")) {
            try {
                j2 = Long.parseLong(map.get("AppUID"));
            } catch (NumberFormatException e) {
                j2 = 0;
            }
        } else {
            j2 = 0;
        }
        contentValues.put("hit_app_id", Long.valueOf(j2));
        if (str == null) {
            str = "http://www.google-analytics.com/collect";
        }
        if (str.length() == 0) {
            aa.z("Empty path: not sending hit");
            return;
        }
        contentValues.put("hit_url", str);
        try {
            sQLiteDatabaseL.insert("hits2", null, contentValues);
            this.sO.r(false);
        } catch (SQLiteException e2) {
            aa.z("Error storing hit");
        }
    }

    private void a(Map<String, String> map, Collection<ef> collection) {
        String strSubstring = "&_v".substring(1);
        if (collection != null) {
            for (ef efVar : collection) {
                if ("appendVersion".equals(efVar.getId())) {
                    map.put(strSubstring, efVar.getValue());
                    return;
                }
            }
        }
    }

    private void cV() throws Throwable {
        int iCX = (cX() - this.vD) + 1;
        if (iCX > 0) {
            List<String> listS = s(iCX);
            aa.y("Store full, deleting " + listS.size() + " hits to make room.");
            a((String[]) listS.toArray(new String[0]));
        }
    }

    static String w(Map<String, String> map) {
        ArrayList arrayList = new ArrayList(map.size());
        for (Map.Entry<String, String> entry : map.entrySet()) {
            arrayList.add(y.encode(entry.getKey()) + "=" + y.encode(entry.getValue()));
        }
        return TextUtils.join("&", arrayList);
    }

    @Override // com.google.android.gms.analytics.d
    public void a(Map<String, String> map, long j, String str, Collection<ef> collection) throws Throwable {
        cW();
        cV();
        a(map, collection);
        a(map, j, str);
    }

    void a(String[] strArr) {
        if (strArr == null || strArr.length == 0) {
            aa.z("Empty hitIds passed to deleteHits.");
            return;
        }
        SQLiteDatabase sQLiteDatabaseL = L("Error opening database for deleteHits.");
        if (sQLiteDatabaseL != null) {
            try {
                sQLiteDatabaseL.delete("hits2", String.format("HIT_ID in (%s)", TextUtils.join(",", Collections.nCopies(strArr.length, "?"))), strArr);
                this.sO.r(cX() == 0);
            } catch (SQLiteException e) {
                aa.z("Error deleting hits " + strArr);
            }
        }
    }

    @Deprecated
    void b(Collection<x> collection) {
        if (collection == null || collection.isEmpty()) {
            aa.z("Empty/Null collection passed to deleteHits.");
            return;
        }
        String[] strArr = new String[collection.size()];
        Iterator<x> it = collection.iterator();
        int i = 0;
        while (it.hasNext()) {
            strArr[i] = String.valueOf(it.next().cP());
            i++;
        }
        a(strArr);
    }

    @Override // com.google.android.gms.analytics.d
    public void bW() throws Throwable {
        aa.y("Dispatch running...");
        if (this.vz.ch()) {
            List<x> listT = t(40);
            if (listT.isEmpty()) {
                aa.y("...nothing to dispatch");
                this.sO.r(true);
                return;
            }
            if (this.vB == null) {
                this.vB = new ab("_t=dispatch&_v=ma4.0.1", true);
            }
            int iA = this.vz.a(listT, this.vB, cX() <= listT.size());
            aa.y("sent " + iA + " of " + listT.size() + " hits");
            b(listT.subList(0, Math.min(iA, listT.size())));
            if (iA != listT.size() || cX() <= 0) {
                this.vB = null;
            } else {
                GoogleAnalytics.getInstance(this.mContext).dispatchLocalHits();
            }
        }
    }

    @Override // com.google.android.gms.analytics.d
    public n bX() {
        return this.vz;
    }

    int cW() {
        long jCurrentTimeMillis = this.tg.currentTimeMillis();
        if (jCurrentTimeMillis <= this.vC + 86400000) {
            return 0;
        }
        this.vC = jCurrentTimeMillis;
        SQLiteDatabase sQLiteDatabaseL = L("Error opening database for deleteStaleHits.");
        if (sQLiteDatabaseL == null) {
            return 0;
        }
        int iDelete = sQLiteDatabaseL.delete("hits2", "HIT_TIME < ?", new String[]{Long.toString(this.tg.currentTimeMillis() - 2592000000L)});
        this.sO.r(cX() == 0);
        return iDelete;
    }

    int cX() {
        Cursor cursorRawQuery = null;
        int i = 0;
        SQLiteDatabase sQLiteDatabaseL = L("Error opening database for getNumStoredHits.");
        try {
            if (sQLiteDatabaseL != null) {
                try {
                    cursorRawQuery = sQLiteDatabaseL.rawQuery("SELECT COUNT(*) from hits2", null);
                    i = cursorRawQuery.moveToFirst() ? (int) cursorRawQuery.getLong(0) : 0;
                } catch (SQLiteException e) {
                    aa.z("Error getting numStoredHits");
                }
            }
            return i;
        } finally {
            if (cursorRawQuery != null) {
                cursorRawQuery.close();
            }
        }
    }

    @Override // com.google.android.gms.analytics.d
    public void j(long j) {
        SQLiteDatabase sQLiteDatabaseL = L("Error opening database for clearHits");
        if (sQLiteDatabaseL != null) {
            if (j == 0) {
                sQLiteDatabaseL.delete("hits2", null, null);
            } else {
                sQLiteDatabaseL.delete("hits2", "hit_app_id = ?", new String[]{Long.valueOf(j).toString()});
            }
            this.sO.r(cX() == 0);
        }
    }

    /* JADX WARN: Code duplicated, block: B:25:0x0082  */
    List<String> s(int i) throws Throwable {
        Cursor cursorQuery;
        ArrayList arrayList = new ArrayList();
        if (i <= 0) {
            aa.z("Invalid maxHits specified. Skipping");
            return arrayList;
        }
        SQLiteDatabase sQLiteDatabaseL = L("Error opening database for peekHitIds.");
        if (sQLiteDatabaseL == null) {
            return arrayList;
        }
        try {
            cursorQuery = sQLiteDatabaseL.query("hits2", new String[]{"hit_id"}, null, null, null, null, String.format("%s ASC", "hit_id"), Integer.toString(i));
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
                    aa.z("Error in peekHits fetching hitIds: " + e.getMessage());
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

    /* JADX WARN: Code duplicated, block: B:35:0x00fc  */
    public List<x> t(int i) throws Throwable {
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
            Cursor cursorQuery = sQLiteDatabaseL.query("hits2", new String[]{"hit_id", "hit_time"}, null, null, null, null, String.format("%s ASC", "hit_id"), Integer.toString(i));
            try {
                try {
                    ArrayList<x> arrayList3 = new ArrayList();
                    try {
                        if (cursorQuery.moveToFirst()) {
                            do {
                                arrayList3.add(new x(null, cursorQuery.getLong(0), cursorQuery.getLong(1)));
                            } while (cursorQuery.moveToNext());
                        }
                        if (cursorQuery != null) {
                            cursorQuery.close();
                        }
                        try {
                            try {
                                Cursor cursorQuery2 = sQLiteDatabaseL.query("hits2", new String[]{"hit_id", "hit_string", "hit_url"}, null, null, null, null, String.format("%s ASC", "hit_id"), Integer.toString(i));
                                try {
                                    if (cursorQuery2.moveToFirst()) {
                                        int i2 = 0;
                                        while (true) {
                                            if (((SQLiteCursor) cursorQuery2).getWindow().getNumRows() > 0) {
                                                ((x) arrayList3.get(i2)).J(cursorQuery2.getString(1));
                                                ((x) arrayList3.get(i2)).K(cursorQuery2.getString(2));
                                            } else {
                                                aa.z(String.format("HitString for hitId %d too large.  Hit will be deleted.", Long.valueOf(((x) arrayList3.get(i2)).cP())));
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
                                    aa.z("Error in peekHits fetching hitString: " + e.getMessage());
                                    ArrayList arrayList4 = new ArrayList();
                                    boolean z = false;
                                    for (x xVar : arrayList3) {
                                        if (TextUtils.isEmpty(xVar.cO())) {
                                            if (z) {
                                                break;
                                            }
                                            z = true;
                                        }
                                        arrayList4.add(xVar);
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
                            aa.z("Error in peekHits fetching hitIds: " + sQLiteException.getMessage());
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
                } catch (Throwable th4) {
                    th = th4;
                    cursor2 = cursorQuery;
                    if (cursor2 != null) {
                        cursor2.close();
                    }
                    throw th;
                }
            } catch (SQLiteException e4) {
                sQLiteException = e4;
                cursor = cursorQuery;
                arrayList = arrayList2;
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
