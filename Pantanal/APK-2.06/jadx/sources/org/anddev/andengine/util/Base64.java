package org.anddev.andengine.util;

import java.io.UnsupportedEncodingException;

/* JADX INFO: loaded from: classes.dex */
public class Base64 {
    static final /* synthetic */ boolean $assertionsDisabled;
    public static final int CRLF = 4;
    public static final int DEFAULT = 0;
    public static final int NO_CLOSE = 16;
    public static final int NO_PADDING = 1;
    public static final int NO_WRAP = 2;
    public static final int URL_SAFE = 8;

    static {
        $assertionsDisabled = !Base64.class.desiredAssertionStatus();
    }

    static abstract class Coder {
        public int op;
        public byte[] output;

        public abstract int maxOutputSize(int i);

        public abstract boolean process(byte[] bArr, int i, int i2, boolean z);

        Coder() {
        }
    }

    public static byte[] decode(String str, int flags) {
        return decode(str.getBytes(), flags);
    }

    public static byte[] decode(byte[] input, int flags) {
        return decode(input, 0, input.length, flags);
    }

    public static byte[] decode(byte[] input, int offset, int len, int flags) {
        Decoder decoder = new Decoder(flags, new byte[(len * 3) / 4]);
        if (!decoder.process(input, offset, len, true)) {
            throw new IllegalArgumentException("bad base-64");
        }
        if (decoder.op == decoder.output.length) {
            return decoder.output;
        }
        byte[] temp = new byte[decoder.op];
        System.arraycopy(decoder.output, 0, temp, 0, decoder.op);
        return temp;
    }

    static class Decoder extends Coder {
        private static final int[] DECODE = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, -1, 63, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -2, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
        private static final int[] DECODE_WEBSAFE = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -2, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, 63, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
        private static final int EQUALS = -2;
        private static final int SKIP = -1;
        private final int[] alphabet;
        private int state;
        private int value;

        public Decoder(int flags, byte[] output) {
            this.output = output;
            this.alphabet = (flags & 8) == 0 ? DECODE : DECODE_WEBSAFE;
            this.state = 0;
            this.value = 0;
        }

        @Override // org.anddev.andengine.util.Base64.Coder
        public int maxOutputSize(int len) {
            return ((len * 3) / 4) + 10;
        }

        @Override // org.anddev.andengine.util.Base64.Coder
        public boolean process(byte[] input, int offset, int len, boolean finish) {
            int op;
            int op2;
            if (this.state == 6) {
                return false;
            }
            int p = offset;
            int len2 = len + offset;
            int state = this.state;
            int value = this.value;
            int op3 = 0;
            byte[] output = this.output;
            int[] alphabet = this.alphabet;
            while (true) {
                if (p < len2) {
                    if (state == 0) {
                        while (p + 4 <= len2 && (value = (alphabet[input[p] & 255] << 18) | (alphabet[input[p + 1] & 255] << 12) | (alphabet[input[p + 2] & 255] << 6) | alphabet[input[p + 3] & 255]) >= 0) {
                            output[op3 + 2] = (byte) value;
                            output[op3 + 1] = (byte) (value >> 8);
                            output[op3] = (byte) (value >> 16);
                            op3 += 3;
                            p += 4;
                        }
                        if (p >= len2) {
                            op = op3;
                        }
                    }
                    p++;
                    int d = alphabet[input[p] & 255];
                    switch (state) {
                        case 0:
                            if (d >= 0) {
                                value = d;
                                state++;
                            } else if (d != -1) {
                                this.state = 6;
                                return false;
                            }
                            break;
                        case 1:
                            if (d >= 0) {
                                value = (value << 6) | d;
                                state++;
                            } else if (d != -1) {
                                this.state = 6;
                                return false;
                            }
                            break;
                        case 2:
                            if (d >= 0) {
                                value = (value << 6) | d;
                                state++;
                            } else if (d == -2) {
                                output[op3] = (byte) (value >> 4);
                                state = 4;
                                op3++;
                            } else if (d != -1) {
                                this.state = 6;
                                return false;
                            }
                            break;
                        case 3:
                            if (d >= 0) {
                                value = (value << 6) | d;
                                output[op3 + 2] = (byte) value;
                                output[op3 + 1] = (byte) (value >> 8);
                                output[op3] = (byte) (value >> 16);
                                op3 += 3;
                                state = 0;
                            } else if (d == -2) {
                                output[op3 + 1] = (byte) (value >> 2);
                                output[op3] = (byte) (value >> 10);
                                op3 += 2;
                                state = 5;
                            } else if (d != -1) {
                                this.state = 6;
                                return false;
                            }
                            break;
                        case 4:
                            if (d == -2) {
                                state++;
                            } else if (d != -1) {
                                this.state = 6;
                                return false;
                            }
                            break;
                        case 5:
                            if (d != -1) {
                                this.state = 6;
                                return false;
                            }
                            break;
                        default:
                            break;
                    }
                } else {
                    op = op3;
                }
            }
            if (!finish) {
                this.state = state;
                this.value = value;
                this.op = op;
                return true;
            }
            switch (state) {
                case 0:
                    op2 = op;
                    break;
                case 1:
                    this.state = 6;
                    return false;
                case 2:
                    op2 = op + 1;
                    output[op] = (byte) (value >> 4);
                    break;
                case 3:
                    int op4 = op + 1;
                    output[op] = (byte) (value >> 10);
                    output[op4] = (byte) (value >> 2);
                    op2 = op4 + 1;
                    break;
                case 4:
                    this.state = 6;
                    return false;
                default:
                    op2 = op;
                    break;
            }
            this.state = state;
            this.op = op2;
            return true;
        }
    }

    public static String encodeToString(byte[] input, int flags) {
        try {
            return new String(encode(input, flags), "US-ASCII");
        } catch (UnsupportedEncodingException e) {
            throw new AssertionError(e);
        }
    }

    public static String encodeToString(byte[] input, int offset, int len, int flags) {
        try {
            return new String(encode(input, offset, len, flags), "US-ASCII");
        } catch (UnsupportedEncodingException e) {
            throw new AssertionError(e);
        }
    }

    public static byte[] encode(byte[] input, int flags) {
        return encode(input, 0, input.length, flags);
    }

    public static byte[] encode(byte[] input, int offset, int len, int flags) {
        Encoder encoder = new Encoder(flags, null);
        int output_len = (len / 3) * 4;
        if (encoder.do_padding) {
            if (len % 3 > 0) {
                output_len += 4;
            }
        } else {
            switch (len % 3) {
                case 1:
                    output_len += 2;
                    break;
                case 2:
                    output_len += 3;
                    break;
            }
        }
        if (encoder.do_newline && len > 0) {
            output_len += (encoder.do_cr ? 2 : 1) * (((len - 1) / 57) + 1);
        }
        encoder.output = new byte[output_len];
        encoder.process(input, offset, len, true);
        if ($assertionsDisabled || encoder.op == output_len) {
            return encoder.output;
        }
        throw new AssertionError();
    }

    static class Encoder extends Coder {
        static final /* synthetic */ boolean $assertionsDisabled;
        private static final byte[] ENCODE;
        private static final byte[] ENCODE_WEBSAFE;
        public static final int LINE_GROUPS = 19;
        private final byte[] alphabet;
        private int count;
        public final boolean do_cr;
        public final boolean do_newline;
        public final boolean do_padding;
        private final byte[] tail;
        int tailLen;

        static {
            $assertionsDisabled = !Base64.class.desiredAssertionStatus();
            ENCODE = new byte[]{65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 43, 47};
            ENCODE_WEBSAFE = new byte[]{65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 45, 95};
        }

        public Encoder(int flags, byte[] output) {
            this.output = output;
            this.do_padding = (flags & 1) == 0;
            this.do_newline = (flags & 2) == 0;
            this.do_cr = (flags & 4) != 0;
            this.alphabet = (flags & 8) == 0 ? ENCODE : ENCODE_WEBSAFE;
            this.tail = new byte[2];
            this.tailLen = 0;
            this.count = this.do_newline ? 19 : -1;
        }

        @Override // org.anddev.andengine.util.Base64.Coder
        public int maxOutputSize(int len) {
            return ((len * 8) / 5) + 10;
        }

        /* JADX WARN: Code duplicated, block: B:15:0x005a  */
        /* JADX WARN: Code duplicated, block: B:17:0x0062  */
        /* JADX WARN: Code duplicated, block: B:19:0x0067  */
        /* JADX WARN: Code duplicated, block: B:22:0x008e  */
        /* JADX WARN: Code duplicated, block: B:25:0x009f  */
        /* JADX WARN: Code duplicated, block: B:27:0x00a3  */
        /* JADX WARN: Code duplicated, block: B:31:0x00b5  */
        /* JADX WARN: Code duplicated, block: B:41:0x010b  */
        /* JADX WARN: Code duplicated, block: B:43:0x014e  */
        /* JADX WARN: Code duplicated, block: B:45:0x0152  */
        /* JADX WARN: Code duplicated, block: B:47:0x0164  */
        /* JADX WARN: Code duplicated, block: B:48:0x016a  */
        /* JADX WARN: Code duplicated, block: B:50:0x0172  */
        /* JADX WARN: Code duplicated, block: B:52:0x0178  */
        /* JADX WARN: Code duplicated, block: B:55:0x0188  */
        /* JADX WARN: Code duplicated, block: B:58:0x01ba  */
        /* JADX WARN: Code duplicated, block: B:61:0x01c5  */
        /* JADX WARN: Code duplicated, block: B:63:0x01c9  */
        /* JADX WARN: Code duplicated, block: B:65:0x01d9  */
        /* JADX WARN: Code duplicated, block: B:66:0x01de  */
        /* JADX WARN: Code duplicated, block: B:67:0x01e4  */
        /* JADX WARN: Code duplicated, block: B:69:0x01e8 A[ADDED_TO_REGION] */
        /* JADX WARN: Code duplicated, block: B:74:0x01f2  */
        /* JADX WARN: Code duplicated, block: B:79:0x0206  */
        /* JADX WARN: Code duplicated, block: B:83:0x0210  */
        /* JADX WARN: Code duplicated, block: B:85:0x0214  */
        /* JADX WARN: Code duplicated, block: B:88:0x0228  */
        /* JADX WARN: Code duplicated, block: B:90:0x022c  */
        /* JADX WARN: Code duplicated, block: B:92:0x0249  */
        /* JADX WARN: Code duplicated, block: B:93:0x024b A[PHI: r2 r3 r6
          0x024b: PHI (r2v1 'count' int) = (r2v0 'count' int), (r2v4 'count' int), (r2v6 'count' int) binds: [B:5:0x0012, B:94:0x024b, B:7:0x003c] A[DONT_GENERATE, DONT_INLINE]
          0x024b: PHI (r3v1 'op' int) = (r3v0 'op' int), (r3v23 'op' int), (r3v27 'op' int) binds: [B:5:0x0012, B:94:0x024b, B:7:0x003c] A[DONT_GENERATE, DONT_INLINE]
          0x024b: PHI (r6v4 'p' int) = (r6v3 'p' int), (r6v17 'p' int), (r6v3 'p' int) binds: [B:5:0x0012, B:94:0x024b, B:7:0x003c] A[DONT_GENERATE, DONT_INLINE]] */
        /* JADX WARN: Code duplicated, block: B:94:0x024b A[SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:95:0x0058 A[SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:97:0x0159 A[SYNTHETIC] */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:93:0x024b -> B:12:0x0052). Please report as a decompilation issue!!! */
        /*  JADX ERROR: JadxOverflowException in pass: RegionMakerVisitor
            jadx.core.utils.exceptions.JadxOverflowException: Regions count limit reached at block B:69:0x01e8
            	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
            	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
            	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
            */
        @Override // org.anddev.andengine.util.Base64.Coder
        public boolean process(byte[] r15, int r16, int r17, boolean r18) {
            /*
                Method dump skipped, instruction units count: 602
                To view this dump add '--comments-level debug' option
            */
            throw new UnsupportedOperationException("Method not decompiled: org.anddev.andengine.util.Base64.Encoder.process(byte[], int, int, boolean):boolean");
        }
    }

    private Base64() {
    }
}
