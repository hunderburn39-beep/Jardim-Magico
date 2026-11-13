package com.heyzap.http;

import android.content.Context;
import com.heyzap.internal.Logger;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.conn.EofSensorInputStream;

/* JADX INFO: loaded from: classes.dex */
public abstract class FileAsyncHttpResponseHandler extends AsyncHttpResponseHandler {
    static final /* synthetic */ boolean $assertionsDisabled;
    private static final String LOG_TAG = "FileAsyncHttpResponseHandler";
    private File mFile;

    public abstract void onFailure(int i, Header[] headerArr, Throwable th, File file);

    public abstract void onSuccess(int i, Header[] headerArr, File file);

    static {
        $assertionsDisabled = !FileAsyncHttpResponseHandler.class.desiredAssertionStatus();
    }

    public FileAsyncHttpResponseHandler(File file) {
        if (!$assertionsDisabled && file == null) {
            throw new AssertionError();
        }
        this.mFile = file;
    }

    public FileAsyncHttpResponseHandler(Context context) {
        this.mFile = getTemporaryFile(context);
    }

    public boolean deleteTargetFile() {
        return getTargetFile() != null && getTargetFile().delete();
    }

    protected File getTemporaryFile(Context context) {
        if (!$assertionsDisabled && context == null) {
            throw new AssertionError();
        }
        try {
            return File.createTempFile("temp_", "_handled", context.getCacheDir());
        } catch (Throwable t) {
            Logger.error("Cannot create temporary file", t);
            return null;
        }
    }

    protected File getTargetFile() {
        if ($assertionsDisabled || this.mFile != null) {
            return this.mFile;
        }
        throw new AssertionError();
    }

    @Override // com.heyzap.http.AsyncHttpResponseHandler
    public final void onFailure(int statusCode, Header[] headers, byte[] responseBytes, Throwable throwable) {
        onFailure(statusCode, headers, throwable, getTargetFile());
    }

    @Override // com.heyzap.http.AsyncHttpResponseHandler
    public final void onSuccess(int statusCode, Header[] headers, byte[] responseBytes) {
        onSuccess(statusCode, headers, getTargetFile());
    }

    @Override // com.heyzap.http.AsyncHttpResponseHandler
    public byte[] getResponseData(HttpEntity entity) throws IOException {
        if (entity != null) {
            InputStream instream = entity.getContent();
            long contentLength = entity.getContentLength();
            FileOutputStream buffer = new FileOutputStream(getTargetFile());
            if (instream != null) {
                try {
                    byte[] tmp = new byte[4096];
                    int count = 0;
                    while (true) {
                        int l = instream.read(tmp);
                        if (l == -1 || Thread.currentThread().isInterrupted()) {
                            break;
                        }
                        count += l;
                        buffer.write(tmp, 0, l);
                        sendProgressMessage(count, (int) contentLength);
                    }
                    if (instream instanceof EofSensorInputStream) {
                        ((EofSensorInputStream) instream).abortConnection();
                    } else {
                        instream.close();
                    }
                    buffer.flush();
                    buffer.close();
                    return null;
                } catch (Throwable th) {
                    if (instream instanceof EofSensorInputStream) {
                        ((EofSensorInputStream) instream).abortConnection();
                    } else {
                        instream.close();
                    }
                    buffer.flush();
                    buffer.close();
                    throw th;
                }
            }
            return null;
        }
        return null;
    }
}
