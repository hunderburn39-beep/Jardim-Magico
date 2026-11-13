package com.r7games.slotpantanal;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/* JADX INFO: loaded from: classes.dex */
public class Database extends SQLiteOpenHelper {
    public static final String DB_NAME = "system.db";
    public static final int DB_VERSION = 15;
    public int acum1ini;
    public int acum1max;
    public int acum2ini;
    public int acum2max;
    public int acum3ini;
    public int acum3max;
    public double acumefect;
    public double acupor;
    Context context;
    public int count;
    public int dificul;
    public String hash;
    public String timeControl;
    public Utils util;

    public Database(Context paramContext) {
        super(paramContext, DB_NAME, (SQLiteDatabase.CursorFactory) null, 15);
        this.util = new Utils();
        this.context = paramContext;
    }

    public SQLiteDatabase getDatabase() {
        try {
            return getWritableDatabase();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public void EreaseLeitura() {
        GetAdmin();
        SQLiteDatabase localSQLiteDatabase = getDatabase();
        if (localSQLiteDatabase != null) {
            localSQLiteDatabase.execSQL("DELETE FROM leitura");
            localSQLiteDatabase.execSQL("DELETE FROM valores");
            localSQLiteDatabase.execSQL("UPDATE admin SET entrada_anterior = 0");
            localSQLiteDatabase.execSQL("INSERT INTO valores VALUES(0, " + this.acum1ini + ", " + this.acum2ini + ", " + this.acum3ini + ", 0.0)");
            localSQLiteDatabase.execSQL("INSERT INTO leitura VALUES(0, 0, '**/**/**** **:**:**', 0)");
            localSQLiteDatabase.execSQL("INSERT INTO leitura VALUES(1, 0, '**/**/**** **:**:**', 0)");
            localSQLiteDatabase.execSQL("INSERT INTO leitura VALUES(2, 0, 0, 0)");
            localSQLiteDatabase.execSQL("INSERT INTO leitura VALUES(3, 0, 0, 0)");
        }
    }

    public Double GetAcumefect() {
        SQLiteDatabase database = getDatabase();
        if (database == null) {
            Double n = Double.valueOf(0.0d);
            return n;
        }
        Cursor rawQuery = database.rawQuery("SELECT acumefect FROM valores", null);
        rawQuery.moveToFirst();
        this.acumefect = rawQuery.getDouble(0);
        Double n2 = Double.valueOf(this.acumefect);
        return n2;
    }

    public Double GetAcumulado(int n) {
        SQLiteDatabase database = getDatabase();
        if (database == null) {
            Double n2 = Double.valueOf(0.0d);
            return n2;
        }
        Cursor rawQuery = database.rawQuery("SELECT acum1, acum2, acum3 FROM valores", null);
        rawQuery.moveToFirst();
        Double n3 = Double.valueOf(rawQuery.getDouble(n));
        return n3;
    }

    public void GetAdmin() {
        SQLiteDatabase database = getDatabase();
        if (database != null) {
            Cursor rawQuery = database.rawQuery("SELECT * FROM admin", null);
            rawQuery.moveToFirst();
            this.dificul = rawQuery.getInt(0);
            this.acupor = rawQuery.getDouble(1);
            this.acum1ini = rawQuery.getInt(2);
            this.acum1max = rawQuery.getInt(3);
            this.acum2ini = rawQuery.getInt(4);
            this.acum2max = rawQuery.getInt(5);
            this.acum3ini = rawQuery.getInt(6);
            this.acum3max = rawQuery.getInt(7);
        }
    }

    public int GetCredito() {
        SQLiteDatabase database = getDatabase();
        if (database == null) {
            return 0;
        }
        Cursor rawQuery = database.rawQuery("SELECT credito FROM valores", null);
        rawQuery.moveToFirst();
        int int1 = rawQuery.getInt(0);
        return int1;
    }

    public int GetFlag() {
        SQLiteDatabase localSQLiteDatabase = getDatabase();
        if (localSQLiteDatabase == null) {
            return 0;
        }
        Cursor localCursor = localSQLiteDatabase.rawQuery("SELECT flag_erro FROM config", null);
        localCursor.moveToFirst();
        int i = localCursor.getInt(0);
        localCursor.close();
        return i;
    }

    public int GetAction() {
        SQLiteDatabase localSQLiteDatabase = getDatabase();
        if (localSQLiteDatabase == null) {
            return 0;
        }
        Cursor localCursor = localSQLiteDatabase.rawQuery("SELECT action FROM config", null);
        localCursor.moveToFirst();
        int i = localCursor.getInt(0);
        localCursor.close();
        return i;
    }

    public int GetBonusToCall() {
        SQLiteDatabase localSQLiteDatabase = getDatabase();
        if (localSQLiteDatabase == null) {
            return 0;
        }
        Cursor localCursor = localSQLiteDatabase.rawQuery("SELECT bonustocall FROM replay", null);
        localCursor.moveToFirst();
        int i = localCursor.getInt(0);
        localCursor.close();
        return i;
    }

    public int GetReplayStatus() {
        SQLiteDatabase localSQLiteDatabase = getDatabase();
        if (localSQLiteDatabase == null) {
            return 0;
        }
        Cursor localCursor = localSQLiteDatabase.rawQuery("SELECT status FROM replay", null);
        localCursor.moveToFirst();
        int i = localCursor.getInt(0);
        localCursor.close();
        return i;
    }

    public int GetPendrive() {
        SQLiteDatabase localSQLiteDatabase = getDatabase();
        if (localSQLiteDatabase == null) {
            return 0;
        }
        Cursor localCursor = localSQLiteDatabase.rawQuery("SELECT pendrive FROM config", null);
        localCursor.moveToFirst();
        int i = localCursor.getInt(0);
        localCursor.close();
        return i;
    }

    public int GetBloqueado() {
        SQLiteDatabase localSQLiteDatabase = getDatabase();
        if (localSQLiteDatabase == null) {
            return 0;
        }
        Cursor localCursor = localSQLiteDatabase.rawQuery("SELECT bloqueado FROM config", null);
        localCursor.moveToFirst();
        int i = localCursor.getInt(0);
        localCursor.close();
        return i;
    }

    public int GetScore() {
        SQLiteDatabase localSQLiteDatabase = getDatabase();
        if (localSQLiteDatabase == null) {
            return 0;
        }
        Cursor localCursor = localSQLiteDatabase.rawQuery("SELECT incremento_dificul FROM admin", null);
        localCursor.moveToFirst();
        int i = localCursor.getInt(0);
        localCursor.close();
        return i;
    }

    public int GetEntradaAnterior() {
        SQLiteDatabase localSQLiteDatabase = getDatabase();
        if (localSQLiteDatabase == null) {
            return 0;
        }
        Cursor localCursor = localSQLiteDatabase.rawQuery("SELECT entrada_anterior FROM admin", null);
        localCursor.moveToFirst();
        int i = localCursor.getInt(0);
        localCursor.close();
        return i;
    }

    public String GetDataControle() {
        SQLiteDatabase localSQLiteDatabase = getDatabase();
        if (localSQLiteDatabase == null) {
            return null;
        }
        Cursor localObject = localSQLiteDatabase.rawQuery("SELECT data_controle FROM config", null);
        localObject.moveToFirst();
        return localObject.getString(0);
    }

    public String GetExpDate() {
        SQLiteDatabase localSQLiteDatabase = getDatabase();
        if (localSQLiteDatabase == null) {
            return null;
        }
        Cursor localObject = localSQLiteDatabase.rawQuery("SELECT exp FROM admin", null);
        localObject.moveToFirst();
        return localObject.getString(0);
    }

    public String GetPwd() {
        SQLiteDatabase localSQLiteDatabase = getDatabase();
        if (localSQLiteDatabase == null) {
            return null;
        }
        Cursor localObject = localSQLiteDatabase.rawQuery("SELECT pwd FROM admin", null);
        localObject.moveToFirst();
        return localObject.getString(0);
    }

    public String GetSerial() {
        SQLiteDatabase localSQLiteDatabase = getDatabase();
        if (localSQLiteDatabase == null) {
            return null;
        }
        Cursor localObject = localSQLiteDatabase.rawQuery("SELECT serial FROM admin", null);
        localObject.moveToFirst();
        return localObject.getString(0);
    }

    public String GetDono() {
        SQLiteDatabase localSQLiteDatabase = getDatabase();
        if (localSQLiteDatabase == null) {
            return null;
        }
        Cursor localObject = localSQLiteDatabase.rawQuery("SELECT dono FROM config", null);
        localObject.moveToFirst();
        return localObject.getString(0);
    }

    public String GetDataCadastro() {
        SQLiteDatabase localSQLiteDatabase = getDatabase();
        if (localSQLiteDatabase == null) {
            return null;
        }
        Cursor localObject = localSQLiteDatabase.rawQuery("SELECT data_cadastro FROM config", null);
        localObject.moveToFirst();
        return localObject.getString(0);
    }

    public String GetSSID() {
        SQLiteDatabase localSQLiteDatabase = getDatabase();
        if (localSQLiteDatabase == null) {
            return null;
        }
        Cursor localObject = localSQLiteDatabase.rawQuery("SELECT ssid FROM config", null);
        localObject.moveToFirst();
        return localObject.getString(0);
    }

    public String GetMAC() {
        SQLiteDatabase localSQLiteDatabase = getDatabase();
        if (localSQLiteDatabase == null) {
            return null;
        }
        Cursor localObject = localSQLiteDatabase.rawQuery("SELECT mac FROM config", null);
        localObject.moveToFirst();
        return localObject.getString(0);
    }

    public String GetMenu() {
        SQLiteDatabase localSQLiteDatabase = getDatabase();
        if (localSQLiteDatabase == null) {
            return null;
        }
        Cursor localObject = localSQLiteDatabase.rawQuery("SELECT menu FROM config", null);
        localObject.moveToFirst();
        return localObject.getString(0);
    }

    public String GetErro() {
        SQLiteDatabase localSQLiteDatabase = getDatabase();
        if (localSQLiteDatabase == null) {
            return null;
        }
        Cursor localObject = localSQLiteDatabase.rawQuery("SELECT erro FROM config", null);
        localObject.moveToFirst();
        return localObject.getString(0);
    }

    public String GetReplaySlots() {
        SQLiteDatabase localSQLiteDatabase = getDatabase();
        if (localSQLiteDatabase == null) {
            return null;
        }
        Cursor localObject = localSQLiteDatabase.rawQuery("SELECT slots FROM replay", null);
        localObject.moveToFirst();
        return localObject.getString(0);
    }

    public void SetAcumefect(Double paramDouble) {
        SQLiteDatabase localSQLiteDatabase = getDatabase();
        if (localSQLiteDatabase != null) {
            localSQLiteDatabase.execSQL("UPDATE valores SET acumefect ='" + paramDouble + "'");
        }
    }

    public void SetAcumulado(int acumulado, Double n2) {
        SQLiteDatabase database = getDatabase();
        if (database != null) {
            switch (acumulado) {
                case 0:
                    database.execSQL("UPDATE valores SET acum1 = " + n2);
                    break;
                case 1:
                    database.execSQL("UPDATE valores SET acum2 = " + n2);
                    break;
                case 2:
                    database.execSQL("UPDATE valores SET acum3 = " + n2);
                    break;
            }
        }
    }

    public void SetAdmin(double paramDouble) {
        SQLiteDatabase localSQLiteDatabase = getDatabase();
        if (localSQLiteDatabase != null) {
            localSQLiteDatabase.execSQL("UPDATE admin SET acupor = acupor + " + paramDouble);
        }
    }

    public void SetScore(long score) {
        SQLiteDatabase localSQLiteDatabase = getDatabase();
        if (localSQLiteDatabase != null) {
            localSQLiteDatabase.execSQL("UPDATE admin SET incremento_dificul = " + score);
        }
    }

    public void SetAdmin(int paramInt) {
        SQLiteDatabase localSQLiteDatabase = getDatabase();
        if (localSQLiteDatabase != null) {
            localSQLiteDatabase.execSQL("UPDATE admin SET dificul = " + paramInt);
        }
    }

    public void SetAdminAcum(int acumulado, int min, int max) {
        SQLiteDatabase database = getDatabase();
        if (database != null) {
            switch (acumulado) {
                case 0:
                    database.execSQL("UPDATE admin SET acum1ini = " + min);
                    database.execSQL("UPDATE admin SET acum1max = " + max);
                    break;
                case 1:
                    database.execSQL("UPDATE admin SET acum2ini = " + min);
                    database.execSQL("UPDATE admin SET acum2max = " + max);
                    break;
                case 2:
                    database.execSQL("UPDATE admin SET acum3ini = " + min);
                    database.execSQL("UPDATE admin SET acum3max = " + max);
                    break;
            }
        }
    }

    public void SetCredito(int n) {
        SQLiteDatabase database = getDatabase();
        if (database != null) {
            database.execSQL("UPDATE valores SET credito = " + n);
        }
    }

    public void SetFlag(int paramInt) {
        SQLiteDatabase localSQLiteDatabase = getDatabase();
        if (localSQLiteDatabase != null) {
            localSQLiteDatabase.execSQL("UPDATE config SET flag_erro = " + paramInt);
        }
    }

    public void SetAction(int paramInt) {
        SQLiteDatabase localSQLiteDatabase = getDatabase();
        if (localSQLiteDatabase != null) {
            localSQLiteDatabase.execSQL("UPDATE config SET action = " + paramInt);
        }
    }

    public void SetBonusToCall(int paramInt) {
        SQLiteDatabase localSQLiteDatabase = getDatabase();
        if (localSQLiteDatabase != null) {
            localSQLiteDatabase.execSQL("UPDATE replay SET bonustocall = " + paramInt);
        }
    }

    public void SetReplayStatus(int paramInt) {
        SQLiteDatabase localSQLiteDatabase = getDatabase();
        if (localSQLiteDatabase != null) {
            localSQLiteDatabase.execSQL("UPDATE replay SET status = " + paramInt);
        }
    }

    public void SetPendrive(int paramInt) {
        SQLiteDatabase localSQLiteDatabase = getDatabase();
        if (localSQLiteDatabase != null) {
            localSQLiteDatabase.execSQL("UPDATE config SET pendrive = " + paramInt);
        }
    }

    public void SetBloqueado(int paramInt) {
        SQLiteDatabase localSQLiteDatabase = getDatabase();
        if (localSQLiteDatabase != null) {
            localSQLiteDatabase.execSQL("UPDATE config SET bloqueado = " + paramInt);
        }
    }

    public void SetIncrentoDificul(long jogadas) {
        SQLiteDatabase localSQLiteDatabase = getDatabase();
        if (localSQLiteDatabase != null) {
            localSQLiteDatabase.execSQL("UPDATE admin SET incremento_dificul = " + jogadas);
        }
    }

    public void SetEntradaAnterior(long saiujogada) {
        SQLiteDatabase localSQLiteDatabase = getDatabase();
        if (localSQLiteDatabase != null) {
            localSQLiteDatabase.execSQL("UPDATE admin SET entrada_anterior = " + saiujogada);
        }
    }

    public void SetSerial(String paramString) {
        SQLiteDatabase localSQLiteDatabase = getDatabase();
        if (localSQLiteDatabase != null) {
            localSQLiteDatabase.execSQL("UPDATE admin SET serial = '" + paramString + "'");
        }
    }

    public void SetDono(String paramString) {
        SQLiteDatabase localSQLiteDatabase = getDatabase();
        if (localSQLiteDatabase != null) {
            localSQLiteDatabase.execSQL("UPDATE config SET dono = '" + paramString + "'");
        }
    }

    public void SetDataCadastro(String paramString) {
        SQLiteDatabase localSQLiteDatabase = getDatabase();
        if (localSQLiteDatabase != null) {
            localSQLiteDatabase.execSQL("UPDATE config SET data_cadastro = '" + paramString + "'");
        }
    }

    public void SetMenu(String paramString) {
        SQLiteDatabase localSQLiteDatabase = getDatabase();
        if (localSQLiteDatabase != null) {
            localSQLiteDatabase.execSQL("UPDATE config SET menu = '" + paramString + "'");
        }
    }

    public void SetSSID(String paramString) {
        SQLiteDatabase localSQLiteDatabase = getDatabase();
        if (localSQLiteDatabase != null) {
            localSQLiteDatabase.execSQL("UPDATE config SET ssid = '" + paramString + "'");
        }
    }

    public void SetMAC(String paramString) {
        SQLiteDatabase localSQLiteDatabase = getDatabase();
        if (localSQLiteDatabase != null) {
            localSQLiteDatabase.execSQL("UPDATE config SET mac = '" + paramString + "'");
        }
    }

    public void SetErro(String paramString) {
        SQLiteDatabase localSQLiteDatabase = getDatabase();
        if (localSQLiteDatabase != null) {
            localSQLiteDatabase.execSQL("UPDATE config SET erro = '" + paramString + "'");
        }
    }

    public void SetReplaySlots(String paramString) {
        SQLiteDatabase localSQLiteDatabase = getDatabase();
        if (localSQLiteDatabase != null) {
            localSQLiteDatabase.execSQL("UPDATE replay SET slots = '" + paramString + "'");
        }
    }

    public void WriteExpDate(String paramString) {
        SQLiteDatabase localSQLiteDatabase = getDatabase();
        if (localSQLiteDatabase != null) {
            localSQLiteDatabase.execSQL("UPDATE admin SET exp='" + paramString + "'");
        }
    }

    public void WriteNewPasswd(String paramString) {
        SQLiteDatabase localSQLiteDatabase = getDatabase();
        if (localSQLiteDatabase != null) {
            localSQLiteDatabase.execSQL("UPDATE admin SET pwd = '" + paramString + "'");
        }
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onCreate(SQLiteDatabase paramSQLiteDatabase) {
        paramSQLiteDatabase.execSQL("CREATE TABLE valores(credito INTEGER,acum1 DOUBLE,acum2 DOUBLE,acum3 DOUBLE,acumefect DOUBLE)");
        paramSQLiteDatabase.execSQL("INSERT INTO valores VALUES(0, 500.0000, 700.0000, 1200.0000, 0.0)");
        paramSQLiteDatabase.execSQL("CREATE TABLE leitura(id INTEGER,valor INTEGER,data VARCHAR(25),data_order INTEGER)");
        paramSQLiteDatabase.execSQL("CREATE TABLE config (dono STRING, erro STRING, flag_erro INTEGER, menu STRING, ssid STRING, data_corte INTEGER, data_cadastro STRING, action INTEGER, data_controle STRING,bloqueado INTEGER,pendrive INTEGER, mac STRING)");
        paramSQLiteDatabase.execSQL("INSERT INTO config VALUES('default','',1,'01_','default',-1,'20141120',0,0,80,1,'default')");
        paramSQLiteDatabase.execSQL("INSERT INTO leitura VALUES(0, 0, '**/**/**** **:**:**', 0)");
        paramSQLiteDatabase.execSQL("INSERT INTO leitura VALUES(1, 0, '**/**/**** **:**:**', 0)");
        paramSQLiteDatabase.execSQL("INSERT INTO leitura VALUES(2, 0, 0, 0)");
        paramSQLiteDatabase.execSQL("INSERT INTO leitura VALUES(3, 0, 0, 0)");
        paramSQLiteDatabase.execSQL("CREATE TABLE admin(dificul INTEGER,acupor DOUBLE,acum1ini INTEGER,acum1max INTEGER,acum2ini INTEGER,acum2max INTEGER,acum3ini INTEGER,acum3max INTEGER,serial STRING,pwd STRING,exp INTEGER,incremento_dificul INTEGER, entrada_anterior INTEGER)");
        paramSQLiteDatabase.execSQL("INSERT INTO admin VALUES(60, 0.5, 500, 1000, 700, 1200, 1200, 2000, 'null', '13251aad66f09546d33aaac8222adbc0', 000000,0,0)");
        paramSQLiteDatabase.execSQL("CREATE TABLE replay(status INTEGER,slots STRING, bonustocall INTEGER)");
        paramSQLiteDatabase.execSQL("INSERT INTO replay VALUES(0,'default',0)");
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onUpgrade(SQLiteDatabase paramSQLiteDatabase, int oldVersion, int newVersion) {
        if (oldVersion <= 11) {
            paramSQLiteDatabase.execSQL("UPDATE admin SET acum1ini = 500");
            paramSQLiteDatabase.execSQL("UPDATE admin SET acum2ini = 700");
            paramSQLiteDatabase.execSQL("UPDATE admin SET acum3ini = 1200");
            paramSQLiteDatabase.execSQL("UPDATE admin SET acum1max = 1000");
            paramSQLiteDatabase.execSQL("UPDATE admin SET acum2max = 1200");
            paramSQLiteDatabase.execSQL("UPDATE admin SET acum3max = 2000");
            paramSQLiteDatabase.execSQL("UPDATE valores SET acum1 = 500.0");
            paramSQLiteDatabase.execSQL("UPDATE valores SET acum2 = 700.0");
            paramSQLiteDatabase.execSQL("UPDATE valores SET acum3 = 1200.0");
        }
        if (oldVersion <= 14) {
            paramSQLiteDatabase.execSQL("UPDATE admin SET incremento_dificul = 0");
            paramSQLiteDatabase.execSQL("UPDATE admin SET entrada_anterior = 0");
            paramSQLiteDatabase.execSQL("UPDATE admin SET acupor = 0.0");
        }
    }
}
