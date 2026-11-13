package com.google.android.gms.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.wallet.fragment.WalletFragmentOptions;

/* JADX INFO: loaded from: classes.dex */
public interface jc extends IInterface {

    public static abstract class a extends Binder implements jc {

        /* JADX INFO: renamed from: com.google.android.gms.internal.jc$a$a, reason: collision with other inner class name */
        private static class C0080a implements jc {
            private IBinder kn;

            C0080a(IBinder iBinder) {
                this.kn = iBinder;
            }

            @Override // com.google.android.gms.internal.jc
            public iz a(com.google.android.gms.dynamic.d dVar, com.google.android.gms.dynamic.c cVar, WalletFragmentOptions walletFragmentOptions, ja jaVar) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.wallet.internal.IWalletDynamiteCreator");
                    parcelObtain.writeStrongBinder(dVar != null ? dVar.asBinder() : null);
                    parcelObtain.writeStrongBinder(cVar != null ? cVar.asBinder() : null);
                    if (walletFragmentOptions != null) {
                        parcelObtain.writeInt(1);
                        walletFragmentOptions.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    parcelObtain.writeStrongBinder(jaVar != null ? jaVar.asBinder() : null);
                    this.kn.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return iz.a.aS(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.kn;
            }
        }

        public static jc aV(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.wallet.internal.IWalletDynamiteCreator");
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof jc)) ? new C0080a(iBinder) : (jc) iInterfaceQueryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            switch (code) {
                case 1:
                    data.enforceInterface("com.google.android.gms.wallet.internal.IWalletDynamiteCreator");
                    iz izVarA = a(com.google.android.gms.dynamic.d.a.K(data.readStrongBinder()), com.google.android.gms.dynamic.c.a.J(data.readStrongBinder()), data.readInt() != 0 ? WalletFragmentOptions.CREATOR.createFromParcel(data) : null, ja.a.aT(data.readStrongBinder()));
                    reply.writeNoException();
                    reply.writeStrongBinder(izVarA != null ? izVarA.asBinder() : null);
                    return true;
                case 1598968902:
                    reply.writeString("com.google.android.gms.wallet.internal.IWalletDynamiteCreator");
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }
    }

    iz a(com.google.android.gms.dynamic.d dVar, com.google.android.gms.dynamic.c cVar, WalletFragmentOptions walletFragmentOptions, ja jaVar) throws RemoteException;
}
