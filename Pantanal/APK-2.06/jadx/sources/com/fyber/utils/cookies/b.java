package com.fyber.utils.cookies;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.HttpCookie;

/* JADX INFO: compiled from: SerializableHttpCookie.java */
/* JADX INFO: loaded from: classes.dex */
public class b implements Serializable {
    private static final long serialVersionUID = -6051428667568260064L;
    private transient HttpCookie a;

    public final HttpCookie a() {
        return this.a;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.writeObject(this.a.getName());
        objectOutputStream.writeObject(this.a.getValue());
        objectOutputStream.writeObject(this.a.getComment());
        objectOutputStream.writeObject(this.a.getCommentURL());
        objectOutputStream.writeBoolean(this.a.getDiscard());
        objectOutputStream.writeObject(this.a.getDomain());
        objectOutputStream.writeLong(this.a.getMaxAge());
        objectOutputStream.writeObject(this.a.getPath());
        objectOutputStream.writeObject(this.a.getPortlist());
        objectOutputStream.writeBoolean(this.a.getSecure());
        objectOutputStream.writeInt(this.a.getVersion());
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        this.a = new HttpCookie((String) objectInputStream.readObject(), (String) objectInputStream.readObject());
        this.a.setComment((String) objectInputStream.readObject());
        this.a.setCommentURL((String) objectInputStream.readObject());
        this.a.setDiscard(objectInputStream.readBoolean());
        this.a.setDomain((String) objectInputStream.readObject());
        this.a.setMaxAge(objectInputStream.readLong());
        this.a.setPath((String) objectInputStream.readObject());
        this.a.setPortlist((String) objectInputStream.readObject());
        this.a.setSecure(objectInputStream.readBoolean());
        this.a.setVersion(objectInputStream.readInt());
    }
}
