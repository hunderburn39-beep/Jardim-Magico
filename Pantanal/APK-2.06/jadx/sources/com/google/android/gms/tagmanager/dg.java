package com.google.android.gms.tagmanager;

/* JADX INFO: loaded from: classes.dex */
class dg extends Number implements Comparable<dg> {
    private double aaC;
    private long aaD;
    private boolean aaE = false;

    private dg(double d) {
        this.aaC = d;
    }

    private dg(long j) {
        this.aaD = j;
    }

    public static dg a(Double d) {
        return new dg(d.doubleValue());
    }

    public static dg bW(String str) throws NumberFormatException {
        try {
            return new dg(Long.parseLong(str));
        } catch (NumberFormatException e) {
            try {
                return new dg(Double.parseDouble(str));
            } catch (NumberFormatException e2) {
                throw new NumberFormatException(str + " is not a valid TypedNumber");
            }
        }
    }

    public static dg w(long j) {
        return new dg(j);
    }

    @Override // java.lang.Comparable
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public int compareTo(dg dgVar) {
        return (lJ() && dgVar.lJ()) ? new Long(this.aaD).compareTo(Long.valueOf(dgVar.aaD)) : Double.compare(doubleValue(), dgVar.doubleValue());
    }

    @Override // java.lang.Number
    public byte byteValue() {
        return (byte) longValue();
    }

    @Override // java.lang.Number
    public double doubleValue() {
        return lJ() ? this.aaD : this.aaC;
    }

    public boolean equals(Object other) {
        return (other instanceof dg) && compareTo((dg) other) == 0;
    }

    @Override // java.lang.Number
    public float floatValue() {
        return (float) doubleValue();
    }

    public int hashCode() {
        return new Long(longValue()).hashCode();
    }

    @Override // java.lang.Number
    public int intValue() {
        return lL();
    }

    public boolean lI() {
        return !lJ();
    }

    public boolean lJ() {
        return this.aaE;
    }

    public long lK() {
        return lJ() ? this.aaD : (long) this.aaC;
    }

    public int lL() {
        return (int) longValue();
    }

    public short lM() {
        return (short) longValue();
    }

    @Override // java.lang.Number
    public long longValue() {
        return lK();
    }

    @Override // java.lang.Number
    public short shortValue() {
        return lM();
    }

    public String toString() {
        return lJ() ? Long.toString(this.aaD) : Double.toString(this.aaC);
    }
}
