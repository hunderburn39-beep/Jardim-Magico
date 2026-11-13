package com.r7games.slotpantanal;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Random;

/* JADX INFO: loaded from: classes.dex */
public class GeneratorNew {
    public int aposta;
    public int bloqueado;
    Context context;
    public int credito;
    public Database dbase;
    SharedPreferences.Editor ed;
    public int[] fech;
    public int linhas;
    public int numSlots;
    public Random rand;
    public int[][] slotVal;
    SharedPreferences sp;
    public int[] tabAcum;
    public int[] tabela;
    int statusReplay = 0;
    int limitador = 44000;
    Boolean dowDificult = false;
    Boolean max = false;
    Boolean med = false;
    public long jogadas = 0;
    public double ajuste = 1.0E-4d;
    public int nBonus = 0;
    public boolean caldeirao = false;
    public boolean Morango = false;
    public boolean roleta = false;
    public boolean dado = false;
    public int sorteio = 0;
    public int numSorteio = 200;
    boolean podePagar = false;

    public void Generate(int paramInt1, int paramInt2, int paramInt5, int paramInt4, String[] replay, int paramInt3, boolean b) {
        Log.d("ADebugTag", "Iniciou: " + this.nBonus);
        this.aposta = paramInt1;
        this.linhas = paramInt2;
        this.credito = paramInt5;
        this.bloqueado = paramInt4;
        this.nBonus = 0;
        this.caldeirao = false;
        this.Morango = false;
        this.dado = false;
        this.roleta = false;
        this.podePagar = b;
        this.dowDificult = Boolean.valueOf(downDificult(0));
        for (int i = 1; i < 4; i++) {
            this.fech[i] = this.rand.nextInt(84);
            if (!this.dowDificult.booleanValue()) {
                faixa(i, 0, 2, 7, 11, 19, 31, 50, 75, 77, 79, 83);
            } else {
                Log.d("ADebugTag", "Modo Pagamento");
            }
            faixa(i, 0, 2, 10, 18, 29, 42, 57, 74, 77, 80, 85);
        }
        for (int i2 = 1; i2 < 4; i2++) {
            this.fech[i2] = this.rand.nextInt(85);
            if (!this.dowDificult.booleanValue()) {
                faixa(i2, 3, 3, 8, 12, 20, 32, 51, 76, 78, 80, 84);
            } else {
                faixa(i2, 3, 3, 11, 19, 30, 43, 58, 75, 78, 81, 86);
            }
        }
        for (int i3 = 1; i3 < 4; i3++) {
            this.fech[i3] = this.rand.nextInt(86);
            if (!this.dowDificult.booleanValue()) {
                faixa(i3, 6, 4, 9, 13, 21, 33, 52, 77, 79, 81, 85);
            } else {
                faixa(i3, 6, 4, 12, 20, 31, 44, 59, 76, 79, 82, 87);
            }
        }
        for (int i4 = 1; i4 < 4; i4++) {
            this.fech[i4] = this.rand.nextInt(85);
            if (!this.dowDificult.booleanValue()) {
                faixa(i4, 9, 3, 8, 12, 20, 32, 51, 74, 78, 80, 84);
            } else {
                faixa(i4, 9, 3, 11, 19, 30, 43, 58, 75, 78, 81, 86);
            }
        }
        for (int i5 = 1; i5 < 4; i5++) {
            this.fech[i5] = this.rand.nextInt(84);
            if (!this.dowDificult.booleanValue()) {
                faixa(i5, 12, 2, 7, 11, 19, 31, 50, 75, 77, 79, 83);
            } else {
                faixa(i5, 12, 2, 10, 18, 29, 42, 57, 74, 77, 80, 85);
            }
        }
        Calcpre1(1, 2, 5, 8, 11, 14);
        Calcpre1(2, 1, 4, 7, 10, 13);
        Calcpre1(3, 3, 6, 9, 12, 15);
        Calcpre1(4, 1, 5, 9, 11, 13);
        Calcpre1(5, 3, 5, 7, 11, 15);
        Calcpre1(6, 1, 4, 8, 12, 15);
        Calcpre1(7, 3, 6, 8, 10, 13);
        Calcpre1(8, 2, 6, 9, 12, 14);
        Calcpre1(9, 2, 4, 7, 10, 14);
        Calcpre1(10, 2, 4, 8, 12, 14);
        Calcpre1(11, 2, 6, 8, 10, 14);
        Calcpre1(12, 3, 5, 7, 10, 13);
        Calcpre1(13, 1, 5, 9, 12, 15);
        Calcpre1(14, 1, 4, 7, 11, 15);
        Calcpre1(15, 3, 6, 9, 11, 13);
        Calcpre1(16, 1, 4, 7, 10, 14);
        Calcpre1(17, 3, 6, 9, 12, 14);
        Calcpre1(18, 2, 4, 7, 10, 13);
        Calcpre1(19, 2, 6, 9, 12, 15);
        Calcpre1(20, 3, 5, 8, 11, 13);
        Calcpre1(20, 3, 5, 8, 11, 13);
        Calcpre1(21, 1, 2, 3, 0, 0);
        Calcpre1(22, 4, 5, 6, 0, 0);
        Calcpre1(23, 7, 8, 9, 0, 0);
        Calcpre1(24, 10, 11, 12, 0, 0);
        Calcpre1(25, 13, 14, 15, 0, 0);
        if (this.caldeirao) {
            this.nBonus++;
        }
        if (this.Morango) {
            this.nBonus++;
        }
        if (this.nBonus > 1) {
            Log.d("ADebugTag", "Entrou: " + this.nBonus);
            this.tabela[this.rand.nextInt(15) + 1] = 4;
            this.tabela[this.rand.nextInt(15) + 1] = 9;
            this.tabela[this.rand.nextInt(15) + 1] = 4;
            this.tabela[this.rand.nextInt(15) + 1] = 6;
            this.tabela[this.rand.nextInt(15) + 1] = 5;
            this.tabela[this.rand.nextInt(15) + 1] = 6;
            this.tabela[this.rand.nextInt(15) + 1] = 4;
            this.tabela[this.rand.nextInt(15) + 1] = 1;
            this.tabela[this.rand.nextInt(15) + 1] = 4;
            this.tabela[this.rand.nextInt(15) + 1] = 6;
            this.tabela[this.rand.nextInt(15) + 1] = 1;
            this.tabela[this.rand.nextInt(15) + 1] = 1;
            this.tabela[this.rand.nextInt(15) + 1] = 6;
            this.tabela[this.rand.nextInt(15) + 1] = 1;
            this.tabela[this.rand.nextInt(15) + 1] = 4;
            Log.d("ADebugTag", "Saiu: " + this.nBonus);
        }
        if (this.dowDificult.booleanValue() && this.jogadas < 200) {
            this.sorteio = this.rand.nextInt(this.numSorteio + 200) + 1;
            if (this.sorteio == 30) {
                this.tabela[2] = 10;
                this.tabela[5] = 10;
                this.tabela[8] = 10;
            }
            if (this.sorteio == 40) {
                this.tabela[1] = 1;
                this.tabela[4] = 1;
                this.tabela[7] = 1;
                this.tabela[10] = 1;
                this.tabela[13] = 1;
            }
            if (this.sorteio == 42) {
                this.tabela[7] = 1;
                this.tabela[10] = 1;
                this.tabela[13] = 1;
            }
            if (this.sorteio == 43) {
                this.tabela[7] = 9;
                this.tabela[10] = 9;
                this.tabela[13] = 9;
            }
        }
        if (this.linhas < 20 || this.aposta == 1 || this.sorteio < 350 || !this.dowDificult.booleanValue()) {
            travaMin(7);
        }
        if (!this.med.booleanValue() || !this.podePagar || this.linhas < 20 || this.aposta == 1) {
            travaAcum(8);
        } else {
            this.sorteio = this.rand.nextInt(this.numSorteio) + 1;
            Log.i("MATEMATICA", "Sorteio Med : " + this.sorteio);
            if (this.sorteio == 1) {
                this.med = false;
                this.tabela[1] = 8;
                this.tabela[5] = 8;
                this.tabela[9] = 8;
                this.tabela[11] = 8;
                this.tabela[13] = 8;
            } else if (this.sorteio == 2) {
                this.med = false;
                this.tabela[3] = 8;
                this.tabela[5] = 8;
                this.tabela[7] = 8;
                this.tabela[11] = 8;
                this.tabela[15] = 8;
            } else if (this.sorteio == 3) {
                this.med = false;
                this.tabela[1] = 8;
                this.tabela[4] = 8;
                this.tabela[7] = 8;
                this.tabela[10] = 8;
                this.tabela[13] = 8;
            } else if (this.sorteio == 4) {
                this.med = false;
                this.tabela[2] = 8;
                this.tabela[5] = 8;
                this.tabela[8] = 8;
                this.tabela[11] = 8;
                this.tabela[14] = 8;
            } else if (this.sorteio == 5) {
                this.med = false;
                this.tabela[3] = 8;
                this.tabela[6] = 8;
                this.tabela[9] = 8;
                this.tabela[12] = 8;
                this.tabela[15] = 8;
            } else if (this.sorteio == 6) {
                this.med = false;
                this.tabela[3] = 9;
                this.tabela[5] = 9;
                this.tabela[7] = 9;
                this.tabela[11] = 9;
                this.tabela[15] = 9;
            } else if (this.sorteio == 7) {
                this.med = false;
                this.tabela[1] = 9;
                this.tabela[4] = 9;
                this.tabela[7] = 9;
                this.tabela[10] = 9;
                this.tabela[13] = 9;
            } else if (this.sorteio == 8) {
                this.med = false;
                this.tabela[2] = 9;
                this.tabela[5] = 9;
                this.tabela[8] = 9;
                this.tabela[11] = 9;
                this.tabela[14] = 9;
            } else if (this.sorteio == 9) {
                this.med = false;
                this.tabela[3] = 9;
                this.tabela[6] = 9;
                this.tabela[9] = 9;
                this.tabela[12] = 9;
                this.tabela[15] = 9;
            } else if (this.sorteio == 10) {
                this.med = false;
                this.tabela[3] = 5;
                this.tabela[5] = 5;
                this.tabela[7] = 5;
                this.tabela[11] = 5;
                this.tabela[15] = 5;
            } else if (this.sorteio == 11) {
                this.med = false;
                this.tabela[1] = 5;
                this.tabela[4] = 5;
                this.tabela[7] = 5;
                this.tabela[10] = 5;
                this.tabela[13] = 5;
            } else if (this.sorteio == 12) {
                this.med = false;
                this.tabela[2] = 5;
                this.tabela[5] = 5;
                this.tabela[8] = 5;
                this.tabela[11] = 5;
                this.tabela[14] = 5;
            } else if (this.sorteio == 13) {
                this.med = false;
                this.tabela[3] = 5;
                this.tabela[6] = 5;
                this.tabela[9] = 5;
                this.tabela[12] = 5;
                this.tabela[15] = 5;
            }
        }
        if (!this.max.booleanValue() || !this.podePagar || (this.linhas < 20 && this.aposta == 1)) {
            travaAcum(7);
            Log.i("MATEMATICA", "Sorteio Max : " + this.sp.getBoolean("max", false));
        } else {
            this.sorteio = this.rand.nextInt(this.numSorteio) + 1;
            Log.i("MATEMATICA", "Sorteio Max : " + this.sorteio);
            if (this.sorteio == 1) {
                this.max = false;
                this.tabela[1] = 7;
                this.tabela[5] = 7;
                this.tabela[9] = 7;
                this.tabela[11] = 7;
                this.tabela[13] = 7;
            } else if (this.sorteio == 2) {
                this.max = false;
                this.tabela[3] = 7;
                this.tabela[5] = 7;
                this.tabela[7] = 7;
                this.tabela[11] = 7;
                this.tabela[15] = 7;
            } else if (this.sorteio == 3) {
                this.max = false;
                this.tabela[1] = 7;
                this.tabela[4] = 7;
                this.tabela[7] = 7;
                this.tabela[10] = 7;
                this.tabela[13] = 7;
            } else if (this.sorteio == 4) {
                this.max = false;
                this.tabela[2] = 7;
                this.tabela[5] = 7;
                this.tabela[8] = 7;
                this.tabela[11] = 7;
                this.tabela[14] = 7;
            } else if (this.sorteio == 5) {
                this.max = false;
                this.tabela[3] = 7;
                this.tabela[6] = 7;
                this.tabela[9] = 7;
                this.tabela[12] = 7;
                this.tabela[15] = 7;
            }
        }
        if (!this.dowDificult.booleanValue()) {
            double[] tabSlots = {1.0d, 3.0d, 4.0d, 5.0d, 6.0d, 7.0d, 8.0d, 9.0d, 10.0d};
            int troca = this.rand.nextInt(9);
            if (this.tabela[1] == 2 && this.tabela[2] == 2 && this.tabela[3] == 2) {
                this.tabela[1] = (int) tabSlots[troca];
                if (this.tabela[4] == 2 && this.tabela[5] == 2 && this.tabela[6] == 2) {
                    this.tabela[4] = (int) tabSlots[troca];
                    if (this.tabela[7] == 2 && this.tabela[8] == 2 && this.tabela[9] == 2) {
                        this.tabela[7] = (int) tabSlots[troca];
                        if (this.tabela[10] == 2 && this.tabela[11] == 2 && this.tabela[12] == 2) {
                            this.tabela[10] = (int) tabSlots[troca];
                            if (this.tabela[13] == 2 && this.tabela[14] == 2 && this.tabela[15] == 2) {
                                this.tabela[13] = (int) tabSlots[troca];
                            }
                        }
                    }
                }
            }
        }
        if (paramInt3 == 1 && this.statusReplay == 1) {
            for (int x = 1; x < 16; x++) {
                this.tabela[x] = Integer.valueOf(replay[x]).intValue();
            }
        }
        if (paramInt3 == 0) {
            String str = "";
            for (int slot : this.tabela) {
                str = String.valueOf(str) + slot + "#";
            }
            String str2 = String.valueOf(String.valueOf(str) + this.aposta) + "#" + this.linhas;
            Log.d("REPLAY SLOTS", str2);
            this.dbase.SetReplaySlots(str2);
            if (this.statusReplay == 0) {
                this.dbase.SetReplayStatus(1);
            }
        }
        this.slotVal[0][0] = this.tabela[1] - 1;
        this.slotVal[0][1] = this.tabela[2] - 1;
        this.slotVal[0][2] = this.tabela[3] - 1;
        this.slotVal[1][0] = this.tabela[4] - 1;
        this.slotVal[1][1] = this.tabela[5] - 1;
        this.slotVal[1][2] = this.tabela[6] - 1;
        this.slotVal[2][0] = this.tabela[7] - 1;
        this.slotVal[2][1] = this.tabela[8] - 1;
        this.slotVal[2][2] = this.tabela[9] - 1;
        this.slotVal[3][0] = this.tabela[10] - 1;
        this.slotVal[3][1] = this.tabela[11] - 1;
        this.slotVal[3][2] = this.tabela[12] - 1;
        this.slotVal[4][0] = this.tabela[13] - 1;
        this.slotVal[4][1] = this.tabela[14] - 1;
        this.slotVal[4][2] = this.tabela[15] - 1;
    }

    public void travaMin(int nPar1) {
        this.tabAcum = new int[20];
        this.numSlots = 0;
        for (int contador1 = 0; contador1 < 16; contador1++) {
            if (this.tabela[contador1] == nPar1) {
                this.numSlots++;
                this.tabAcum[this.numSlots] = contador1;
            }
        }
        if (this.numSlots == 4 && nPar1 == 7) {
            this.tabela[this.tabAcum[3]] = this.rand.nextInt(5) + 1;
        }
    }

    public void travaAcum(int nPar1) {
        this.tabAcum = new int[7];
        this.numSlots = 0;
        for (int contador1 = 0; contador1 < 16; contador1++) {
            if (this.tabela[contador1] == nPar1) {
                this.numSlots++;
                this.tabAcum[this.numSlots] = contador1;
            }
        }
        if (this.numSlots == 5) {
            this.tabela[this.tabAcum[2]] = this.rand.nextInt(5) + 1;
            this.tabela[this.tabAcum[5]] = this.rand.nextInt(5) + 1;
        } else if (this.numSlots == 6) {
            this.tabela[this.tabAcum[1]] = this.rand.nextInt(5) + 1;
            this.tabela[this.tabAcum[3]] = this.rand.nextInt(5) + 1;
            this.tabela[this.tabAcum[5]] = this.rand.nextInt(5) + 1;
        }
    }

    public void Init(Context paramContext) {
        this.context = paramContext;
        this.dbase = new Database(paramContext);
        this.statusReplay = this.dbase.GetReplayStatus();
        if (this.ed == null && this.sp == null) {
            this.sp = PreferenceManager.getDefaultSharedPreferences(paramContext);
            this.ed = this.sp.edit();
        }
        this.med = Boolean.valueOf(this.sp.getBoolean("med", false));
        this.max = Boolean.valueOf(this.sp.getBoolean("max", false));
        this.numSorteio = this.sp.getInt("sorteio", 200);
        this.dbase.GetAdmin();
        this.rand = new Random(System.nanoTime());
        int[] arrayOfInt = {5, 3};
        this.slotVal = (int[][]) Array.newInstance((Class<?>) Integer.TYPE, arrayOfInt);
        this.tabela = new int[16];
        this.fech = new int[4];
        Arrays.fill(this.tabela, 0);
        Arrays.fill(this.fech, 0);
        this.tabela[1] = 1;
        this.tabela[2] = 2;
        this.tabela[3] = 3;
        this.tabela[4] = 4;
        this.tabela[5] = 5;
        this.tabela[6] = 6;
        this.tabela[7] = 7;
        this.tabela[8] = 8;
        this.tabela[9] = 9;
        this.tabela[10] = 10;
        this.tabela[11] = 1;
        this.tabela[12] = 2;
        this.tabela[13] = 3;
        this.tabela[14] = 4;
        this.tabela[15] = 5;
    }

    public boolean downDificult(int paramInt) {
        Boolean bool = false;
        int sorte = this.rand.nextInt(10) + 1;
        Log.d("MATEMATICA", "Sorte: " + sorte);
        if (this.bloqueado == 5) {
            this.limitador = 50000;
        } else if (this.bloqueado == 10) {
            this.limitador = 100000;
        } else if (this.bloqueado == 15) {
            this.limitador = 150000;
        } else if (this.bloqueado == 20) {
            this.limitador = 200000;
        } else if (this.bloqueado == 30) {
            this.limitador = 300000;
        } else {
            this.limitador = 42500;
        }
        if ((this.credito < this.limitador && this.jogadas < 500) || (sorte == 4 && this.jogadas < 239 && this.bloqueado == 50)) {
            bool = true;
        }
        this.jogadas++;
        Log.i("MATEMATICA", "jogadas: " + this.jogadas);
        Log.i("MATEMATICA", "bloqueado: " + this.bloqueado);
        Log.i("MATEMATICA", "dowDificult: " + bool);
        return bool.booleanValue();
    }

    public void Calcpre1(int nPar1, int nPar2, int nPar3, int nPar4, int nPar5, int nPar6) {
        if (this.tabela[nPar2] == 5 && this.tabela[nPar3] == 5 && this.tabela[nPar4] == 5 && this.tabela[nPar5] == 5 && this.tabela[nPar6] == 5) {
            this.Morango = true;
            return;
        }
        if (this.tabela[nPar2] == 5 && this.tabela[nPar3] == 5 && this.tabela[nPar4] == 5 && this.tabela[nPar5] == 5) {
            this.Morango = true;
            return;
        }
        if (this.tabela[nPar2] == 5 && this.tabela[nPar3] == 5 && this.tabela[nPar4] == 5 && this.tabela[nPar6] == 5) {
            this.Morango = true;
            return;
        }
        if (this.tabela[nPar2] == 5 && this.tabela[nPar3] == 5 && this.tabela[nPar5] == 5 && this.tabela[nPar6] == 5) {
            this.Morango = true;
            return;
        }
        if (this.tabela[nPar2] == 5 && this.tabela[nPar4] == 5 && this.tabela[nPar5] == 5 && this.tabela[nPar6] == 5) {
            this.Morango = true;
            return;
        }
        if (this.tabela[nPar3] == 5 && this.tabela[nPar4] == 5 && this.tabela[nPar5] == 5 && this.tabela[nPar6] == 5) {
            this.Morango = true;
            return;
        }
        if (this.tabela[nPar2] == 5 && this.tabela[nPar3] == 5 && this.tabela[nPar4] == 5) {
            this.Morango = true;
            return;
        }
        if (this.tabela[nPar3] == 5 && this.tabela[nPar4] == 5 && this.tabela[nPar5] == 5) {
            this.Morango = true;
            return;
        }
        if (this.tabela[nPar4] == 5 && this.tabela[nPar5] == 5 && this.tabela[nPar6] == 5) {
            this.Morango = true;
            return;
        }
        if (this.tabela[nPar2] == 10 && this.tabela[nPar3] == 10 && this.tabela[nPar4] == 10 && this.tabela[nPar5] == 10 && this.tabela[nPar6] == 10) {
            this.caldeirao = true;
            return;
        }
        if (this.tabela[nPar2] == 10 && this.tabela[nPar3] == 10 && this.tabela[nPar4] == 10 && this.tabela[nPar5] == 10) {
            this.caldeirao = true;
            return;
        }
        if (this.tabela[nPar3] == 10 && this.tabela[nPar4] == 10 && this.tabela[nPar5] == 10 && this.tabela[nPar6] == 10) {
            this.caldeirao = true;
            return;
        }
        if (this.tabela[nPar2] == 10 && this.tabela[nPar3] == 10 && this.tabela[nPar4] == 10) {
            this.caldeirao = true;
            return;
        }
        if (this.tabela[nPar4] == 10 && this.tabela[nPar5] == 10 && this.tabela[nPar6] == 10) {
            this.caldeirao = true;
            return;
        }
        if (this.tabela[nPar2] == 2 && this.tabela[nPar3] == 2 && this.tabela[nPar4] == 2 && nPar1 >= 21) {
            this.roleta = true;
            this.nBonus++;
        } else if (this.tabela[nPar2] == 3 && this.tabela[nPar3] == 3 && this.tabela[nPar4] == 3 && nPar1 >= 21) {
            this.dado = true;
            this.nBonus++;
        }
    }

    public void faixa(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, int paramInt10, int paramInt11, int paramInt12) {
        if (this.fech[paramInt1] >= paramInt3) {
            if (this.fech[paramInt1] >= paramInt4) {
                if (this.fech[paramInt1] >= paramInt5) {
                    if (this.fech[paramInt1] >= paramInt6) {
                        if (this.fech[paramInt1] >= paramInt7) {
                            if (this.fech[paramInt1] >= paramInt8) {
                                if (this.fech[paramInt1] >= paramInt9) {
                                    if (this.fech[paramInt1] >= paramInt10) {
                                        if (this.fech[paramInt1] >= paramInt11) {
                                            if (this.fech[paramInt1] < paramInt12) {
                                                this.tabela[paramInt1 + paramInt2] = 5;
                                                return;
                                            }
                                            return;
                                        }
                                        this.tabela[paramInt1 + paramInt2] = 8;
                                        return;
                                    }
                                    this.tabela[paramInt1 + paramInt2] = 9;
                                    return;
                                }
                                this.tabela[paramInt1 + paramInt2] = 4;
                                return;
                            }
                            this.tabela[paramInt1 + paramInt2] = 6;
                            return;
                        }
                        this.tabela[paramInt1 + paramInt2] = 3;
                        return;
                    }
                    this.tabela[paramInt1 + paramInt2] = 2;
                    return;
                }
                this.tabela[paramInt1 + paramInt2] = 1;
                return;
            }
            this.tabela[paramInt1 + paramInt2] = 10;
            return;
        }
        this.tabela[paramInt1 + paramInt2] = 7;
    }
}
