package com.tapjoy.internal;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
class fb extends fa {
    private final File b;
    private final ft c;
    private volatile SQLiteDatabase d;
    private long e;
    private long f;
    private long g;

    public fb(File file, ft ftVar) {
        this.b = file;
        this.c = ftVar;
    }

    protected void finalize() {
        if (this.d != null) {
            da.a(this.d);
            this.d = null;
        }
        super.finalize();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.tapjoy.internal.fa
    public void a(long j) {
        if (this.d == null) {
            this.d = SQLiteDatabase.openOrCreateDatabase(this.b, (SQLiteDatabase.CursorFactory) null);
            int version = this.d.getVersion();
            switch (version) {
                case 0:
                    this.d.beginTransaction();
                    try {
                        this.d.execSQL("CREATE TABLE IF NOT EXISTS UsageStats(name TEXT,dimensions TEXT,count INTEGER,first_time INTEGER,last_time INTEGER,PRIMARY KEY(name, dimensions))");
                        this.d.execSQL("CREATE TABLE IF NOT EXISTS UsageStatValues(stat_id LONG,name TEXT,count INTEGER,avg REAL,max INTEGER,PRIMARY KEY(stat_id, name))");
                        this.d.setVersion(1);
                        this.d.setTransactionSuccessful();
                        this.d.endTransaction();
                    } catch (Throwable th) {
                        this.d.endTransaction();
                        throw th;
                    }
                    break;
                case 1:
                    break;
                default:
                    throw new SQLException("Unknown database version: " + version);
            }
            Cursor cursorRawQuery = this.d.rawQuery("SELECT MIN(first_time), MAX(last_time) FROM UsageStats", null);
            try {
                if (cursorRawQuery.moveToNext()) {
                    this.f = cursorRawQuery.getLong(0);
                    this.g = cursorRawQuery.getLong(1);
                }
                cursorRawQuery.close();
                if (this.f > 0 && this.f + 86400000 <= j) {
                    b();
                }
            } catch (Throwable th2) {
                cursorRawQuery.close();
                throw th2;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.tapjoy.internal.fa
    public void a() {
        if (this.d != null) {
            da.a(this.d);
            this.d = null;
        }
        this.b.delete();
        this.g = 0L;
        this.f = 0L;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.tapjoy.internal.fa
    public void a(long j, String str, String str2, Map map) {
        long jInsert;
        if (this.d != null) {
            if (this.e == 0) {
                this.g = j;
                this.e = j;
            } else if (j < this.e || j >= this.e + 86400000) {
                if (j < this.e && this.g - j < 86400000) {
                    this.e = j;
                } else {
                    b();
                    this.g = j;
                    this.e = j;
                }
            } else if (j > this.g) {
                this.g = j;
            }
            if (str2 == null) {
                str2 = "";
            }
            Cursor cursorRawQuery = this.d.rawQuery("SELECT ROWID,count,first_time,last_time FROM UsageStats WHERE name = ? AND dimensions = ?", new String[]{str, str2});
            try {
                ContentValues contentValues = new ContentValues();
                if (cursorRawQuery.moveToNext()) {
                    long j2 = cursorRawQuery.getLong(0);
                    int i = cursorRawQuery.getInt(1);
                    long j3 = cursorRawQuery.getLong(2);
                    long j4 = cursorRawQuery.getLong(3);
                    contentValues.put("count", Integer.valueOf(i + 1));
                    if (j < j3) {
                        contentValues.put("first_time", Long.valueOf(j));
                    }
                    if (j > j4) {
                        contentValues.put("last_time", Long.valueOf(j));
                    }
                    this.d.update("UsageStats", contentValues, "ROWID = " + j2, null);
                    jInsert = j2;
                } else {
                    contentValues.put("name", str);
                    contentValues.put("dimensions", str2);
                    contentValues.put("count", (Integer) 1);
                    contentValues.put("first_time", Long.valueOf(j));
                    contentValues.put("last_time", Long.valueOf(j));
                    jInsert = this.d.insert("UsageStats", null, contentValues);
                }
                if (map != null && !map.isEmpty()) {
                    for (Map.Entry entry : map.entrySet()) {
                        if (entry.getValue() != null) {
                            String str3 = (String) entry.getKey();
                            long jLongValue = ((Long) entry.getValue()).longValue();
                            Cursor cursorRawQuery2 = this.d.rawQuery("SELECT ROWID, * FROM UsageStatValues WHERE stat_id = ? AND name = ?", new String[]{Long.toString(jInsert), str3});
                            try {
                                if (cursorRawQuery2.moveToNext()) {
                                    long j5 = cursorRawQuery2.getLong(0);
                                    int i2 = cursorRawQuery2.getInt(3);
                                    double d = cursorRawQuery2.getDouble(4);
                                    long j6 = cursorRawQuery2.getLong(5);
                                    contentValues.clear();
                                    contentValues.put("count", Integer.valueOf(i2 + 1));
                                    contentValues.put("avg", Double.valueOf(d + ((jLongValue - d) / ((double) (i2 + 1)))));
                                    if (jLongValue > j6) {
                                        contentValues.put("max", Long.valueOf(jLongValue));
                                    }
                                    this.d.update("UsageStatValues", contentValues, "ROWID = " + j5, null);
                                } else {
                                    contentValues.clear();
                                    contentValues.put("stat_id", Long.valueOf(jInsert));
                                    contentValues.put("name", str3);
                                    contentValues.put("count", (Integer) 1);
                                    contentValues.put("avg", Long.valueOf(jLongValue));
                                    contentValues.put("max", Long.valueOf(jLongValue));
                                    this.d.insert("UsageStatValues", null, contentValues);
                                }
                                cursorRawQuery2.close();
                            } catch (Throwable th) {
                                cursorRawQuery2.close();
                                throw th;
                            }
                        }
                    }
                }
                cursorRawQuery.close();
            } catch (Throwable th2) {
                cursorRawQuery.close();
                throw th2;
            }
        }
    }

    private void b() {
        Set set = fa.a;
        Cursor cursorRawQuery = this.d.rawQuery("SELECT ROWID, * FROM UsageStats ORDER BY ROWID ASC", null);
        try {
            Cursor cursorRawQuery2 = this.d.rawQuery("SELECT * FROM UsageStatValues ORDER BY stat_id ASC", null);
            try {
                cursorRawQuery2.moveToNext();
                while (cursorRawQuery.moveToNext()) {
                    long j = cursorRawQuery.getLong(0);
                    String string = cursorRawQuery.getString(1);
                    String string2 = cursorRawQuery.getString(2);
                    if (string2.isEmpty()) {
                        string2 = null;
                    }
                    int i = cursorRawQuery.getInt(3);
                    long j2 = cursorRawQuery.getLong(4);
                    long j3 = cursorRawQuery.getLong(5);
                    HashMap map = null;
                    if (!cursorRawQuery2.isAfterLast()) {
                        while (cursorRawQuery2.getLong(0) == j) {
                            if (map == null) {
                                map = new HashMap();
                            }
                            String string3 = cursorRawQuery2.getString(1);
                            long j4 = cursorRawQuery2.getLong(3);
                            long j5 = cursorRawQuery2.getLong(4);
                            map.put(string3, Long.valueOf(j4));
                            map.put(string3 + "_max", Long.valueOf(j5));
                            if (!cursorRawQuery2.moveToNext()) {
                                break;
                            }
                        }
                    }
                    if (set == null || !set.contains(string)) {
                        this.c.a(string, string2, i, j2, j3, map);
                    }
                }
                cursorRawQuery2.close();
                cursorRawQuery.close();
                this.d.execSQL("DELETE FROM UsageStats");
                this.d.execSQL("DELETE FROM UsageStatValues");
                this.g = 0L;
                this.f = 0L;
            } catch (Throwable th) {
                cursorRawQuery2.close();
                throw th;
            }
        } catch (Throwable th2) {
            cursorRawQuery.close();
            throw th2;
        }
    }
}
