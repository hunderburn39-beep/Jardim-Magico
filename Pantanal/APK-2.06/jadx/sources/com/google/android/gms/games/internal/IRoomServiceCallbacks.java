package com.google.android.gms.games.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;

/* JADX INFO: loaded from: classes.dex */
public interface IRoomServiceCallbacks extends IInterface {

    public static abstract class Stub extends Binder implements IRoomServiceCallbacks {

        private static class Proxy implements IRoomServiceCallbacks {
            private IBinder kn;

            Proxy(IBinder remote) {
                this.kn = remote;
            }

            @Override // com.google.android.gms.games.internal.IRoomServiceCallbacks
            public void P(IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IRoomServiceCallbacks");
                    parcelObtain.writeStrongBinder(iBinder);
                    this.kn.transact(1021, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.games.internal.IRoomServiceCallbacks
            public void a(ParcelFileDescriptor parcelFileDescriptor, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IRoomServiceCallbacks");
                    if (parcelFileDescriptor != null) {
                        parcelObtain.writeInt(1);
                        parcelFileDescriptor.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    parcelObtain.writeInt(i);
                    this.kn.transact(1024, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.games.internal.IRoomServiceCallbacks
            public void a(ConnectionInfo connectionInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IRoomServiceCallbacks");
                    if (connectionInfo != null) {
                        parcelObtain.writeInt(1);
                        connectionInfo.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    this.kn.transact(1022, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.games.internal.IRoomServiceCallbacks
            public void a(String str, byte[] bArr, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IRoomServiceCallbacks");
                    parcelObtain.writeString(str);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeInt(i);
                    this.kn.transact(1002, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.games.internal.IRoomServiceCallbacks
            public void a(String str, String[] strArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IRoomServiceCallbacks");
                    parcelObtain.writeString(str);
                    parcelObtain.writeStringArray(strArr);
                    this.kn.transact(1008, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.games.internal.IRoomServiceCallbacks
            public void aO(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IRoomServiceCallbacks");
                    parcelObtain.writeString(str);
                    this.kn.transact(1003, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.games.internal.IRoomServiceCallbacks
            public void aP(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IRoomServiceCallbacks");
                    parcelObtain.writeString(str);
                    this.kn.transact(1004, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.games.internal.IRoomServiceCallbacks
            public void aQ(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IRoomServiceCallbacks");
                    parcelObtain.writeString(str);
                    this.kn.transact(1005, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.games.internal.IRoomServiceCallbacks
            public void aR(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IRoomServiceCallbacks");
                    parcelObtain.writeString(str);
                    this.kn.transact(1006, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.games.internal.IRoomServiceCallbacks
            public void aS(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IRoomServiceCallbacks");
                    parcelObtain.writeString(str);
                    this.kn.transact(1007, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.games.internal.IRoomServiceCallbacks
            public void aT(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IRoomServiceCallbacks");
                    parcelObtain.writeString(str);
                    this.kn.transact(1018, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.games.internal.IRoomServiceCallbacks
            public void aU(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IRoomServiceCallbacks");
                    parcelObtain.writeString(str);
                    this.kn.transact(1019, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.kn;
            }

            @Override // com.google.android.gms.games.internal.IRoomServiceCallbacks
            public void b(String str, String[] strArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IRoomServiceCallbacks");
                    parcelObtain.writeString(str);
                    parcelObtain.writeStringArray(strArr);
                    this.kn.transact(1009, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.games.internal.IRoomServiceCallbacks
            public void bb(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IRoomServiceCallbacks");
                    parcelObtain.writeInt(i);
                    this.kn.transact(1020, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.games.internal.IRoomServiceCallbacks
            public void c(int i, int i2, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IRoomServiceCallbacks");
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeString(str);
                    this.kn.transact(1001, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.games.internal.IRoomServiceCallbacks
            public void c(String str, String[] strArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IRoomServiceCallbacks");
                    parcelObtain.writeString(str);
                    parcelObtain.writeStringArray(strArr);
                    this.kn.transact(1010, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.games.internal.IRoomServiceCallbacks
            public void d(String str, String[] strArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IRoomServiceCallbacks");
                    parcelObtain.writeString(str);
                    parcelObtain.writeStringArray(strArr);
                    this.kn.transact(1011, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.games.internal.IRoomServiceCallbacks
            public void e(String str, String[] strArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IRoomServiceCallbacks");
                    parcelObtain.writeString(str);
                    parcelObtain.writeStringArray(strArr);
                    this.kn.transact(1012, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.games.internal.IRoomServiceCallbacks
            public void f(String str, String[] strArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IRoomServiceCallbacks");
                    parcelObtain.writeString(str);
                    parcelObtain.writeStringArray(strArr);
                    this.kn.transact(1013, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.games.internal.IRoomServiceCallbacks
            public void g(String str, String[] strArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IRoomServiceCallbacks");
                    parcelObtain.writeString(str);
                    parcelObtain.writeStringArray(strArr);
                    this.kn.transact(1017, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.games.internal.IRoomServiceCallbacks
            public void gQ() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IRoomServiceCallbacks");
                    this.kn.transact(1016, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.games.internal.IRoomServiceCallbacks
            public void gR() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IRoomServiceCallbacks");
                    this.kn.transact(1023, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.games.internal.IRoomServiceCallbacks
            public void onP2PConnected(String participantId) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IRoomServiceCallbacks");
                    parcelObtain.writeString(participantId);
                    this.kn.transact(1014, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.games.internal.IRoomServiceCallbacks
            public void onP2PDisconnected(String participantId) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IRoomServiceCallbacks");
                    parcelObtain.writeString(participantId);
                    this.kn.transact(1015, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.games.internal.IRoomServiceCallbacks
            public void r(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IRoomServiceCallbacks");
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.kn.transact(1025, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, "com.google.android.gms.games.internal.IRoomServiceCallbacks");
        }

        public static IRoomServiceCallbacks Q(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.games.internal.IRoomServiceCallbacks");
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IRoomServiceCallbacks)) ? new Proxy(iBinder) : (IRoomServiceCallbacks) iInterfaceQueryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            switch (i) {
                case 1001:
                    parcel.enforceInterface("com.google.android.gms.games.internal.IRoomServiceCallbacks");
                    c(parcel.readInt(), parcel.readInt(), parcel.readString());
                    return true;
                case 1002:
                    parcel.enforceInterface("com.google.android.gms.games.internal.IRoomServiceCallbacks");
                    a(parcel.readString(), parcel.createByteArray(), parcel.readInt());
                    return true;
                case 1003:
                    parcel.enforceInterface("com.google.android.gms.games.internal.IRoomServiceCallbacks");
                    aO(parcel.readString());
                    return true;
                case 1004:
                    parcel.enforceInterface("com.google.android.gms.games.internal.IRoomServiceCallbacks");
                    aP(parcel.readString());
                    return true;
                case 1005:
                    parcel.enforceInterface("com.google.android.gms.games.internal.IRoomServiceCallbacks");
                    aQ(parcel.readString());
                    return true;
                case 1006:
                    parcel.enforceInterface("com.google.android.gms.games.internal.IRoomServiceCallbacks");
                    aR(parcel.readString());
                    return true;
                case 1007:
                    parcel.enforceInterface("com.google.android.gms.games.internal.IRoomServiceCallbacks");
                    aS(parcel.readString());
                    return true;
                case 1008:
                    parcel.enforceInterface("com.google.android.gms.games.internal.IRoomServiceCallbacks");
                    a(parcel.readString(), parcel.createStringArray());
                    return true;
                case 1009:
                    parcel.enforceInterface("com.google.android.gms.games.internal.IRoomServiceCallbacks");
                    b(parcel.readString(), parcel.createStringArray());
                    return true;
                case 1010:
                    parcel.enforceInterface("com.google.android.gms.games.internal.IRoomServiceCallbacks");
                    c(parcel.readString(), parcel.createStringArray());
                    return true;
                case 1011:
                    parcel.enforceInterface("com.google.android.gms.games.internal.IRoomServiceCallbacks");
                    d(parcel.readString(), parcel.createStringArray());
                    return true;
                case 1012:
                    parcel.enforceInterface("com.google.android.gms.games.internal.IRoomServiceCallbacks");
                    e(parcel.readString(), parcel.createStringArray());
                    return true;
                case 1013:
                    parcel.enforceInterface("com.google.android.gms.games.internal.IRoomServiceCallbacks");
                    f(parcel.readString(), parcel.createStringArray());
                    return true;
                case 1014:
                    parcel.enforceInterface("com.google.android.gms.games.internal.IRoomServiceCallbacks");
                    onP2PConnected(parcel.readString());
                    return true;
                case 1015:
                    parcel.enforceInterface("com.google.android.gms.games.internal.IRoomServiceCallbacks");
                    onP2PDisconnected(parcel.readString());
                    return true;
                case 1016:
                    parcel.enforceInterface("com.google.android.gms.games.internal.IRoomServiceCallbacks");
                    gQ();
                    return true;
                case 1017:
                    parcel.enforceInterface("com.google.android.gms.games.internal.IRoomServiceCallbacks");
                    g(parcel.readString(), parcel.createStringArray());
                    return true;
                case 1018:
                    parcel.enforceInterface("com.google.android.gms.games.internal.IRoomServiceCallbacks");
                    aT(parcel.readString());
                    return true;
                case 1019:
                    parcel.enforceInterface("com.google.android.gms.games.internal.IRoomServiceCallbacks");
                    aU(parcel.readString());
                    return true;
                case 1020:
                    parcel.enforceInterface("com.google.android.gms.games.internal.IRoomServiceCallbacks");
                    bb(parcel.readInt());
                    return true;
                case 1021:
                    parcel.enforceInterface("com.google.android.gms.games.internal.IRoomServiceCallbacks");
                    P(parcel.readStrongBinder());
                    return true;
                case 1022:
                    parcel.enforceInterface("com.google.android.gms.games.internal.IRoomServiceCallbacks");
                    a(parcel.readInt() != 0 ? ConnectionInfo.CREATOR.createFromParcel(parcel) : null);
                    return true;
                case 1023:
                    parcel.enforceInterface("com.google.android.gms.games.internal.IRoomServiceCallbacks");
                    gR();
                    return true;
                case 1024:
                    parcel.enforceInterface("com.google.android.gms.games.internal.IRoomServiceCallbacks");
                    a(parcel.readInt() != 0 ? (ParcelFileDescriptor) ParcelFileDescriptor.CREATOR.createFromParcel(parcel) : null, parcel.readInt());
                    return true;
                case 1025:
                    parcel.enforceInterface("com.google.android.gms.games.internal.IRoomServiceCallbacks");
                    r(parcel.readString(), parcel.readInt());
                    return true;
                case 1598968902:
                    parcel2.writeString("com.google.android.gms.games.internal.IRoomServiceCallbacks");
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }
    }

    void P(IBinder iBinder) throws RemoteException;

    void a(ParcelFileDescriptor parcelFileDescriptor, int i) throws RemoteException;

    void a(ConnectionInfo connectionInfo) throws RemoteException;

    void a(String str, byte[] bArr, int i) throws RemoteException;

    void a(String str, String[] strArr) throws RemoteException;

    void aO(String str) throws RemoteException;

    void aP(String str) throws RemoteException;

    void aQ(String str) throws RemoteException;

    void aR(String str) throws RemoteException;

    void aS(String str) throws RemoteException;

    void aT(String str) throws RemoteException;

    void aU(String str) throws RemoteException;

    void b(String str, String[] strArr) throws RemoteException;

    void bb(int i) throws RemoteException;

    void c(int i, int i2, String str) throws RemoteException;

    void c(String str, String[] strArr) throws RemoteException;

    void d(String str, String[] strArr) throws RemoteException;

    void e(String str, String[] strArr) throws RemoteException;

    void f(String str, String[] strArr) throws RemoteException;

    void g(String str, String[] strArr) throws RemoteException;

    void gQ() throws RemoteException;

    void gR() throws RemoteException;

    void onP2PConnected(String str) throws RemoteException;

    void onP2PDisconnected(String str) throws RemoteException;

    void r(String str, int i) throws RemoteException;
}
