package com.heyzap.internal;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentCallbacks;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import com.google.android.gms.drive.DriveFile;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/* JADX INFO: loaded from: classes.dex */
public class ProxyActivity extends Activity {
    private final Activity context;

    public ProxyActivity(Activity context) {
        this.context = context;
    }

    public Context getRealContext() {
        return this.context;
    }

    @Override // android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
    }

    @Override // android.app.Activity
    public void startActivityForResult(Intent intent, int requestCode) {
        intent.setFlags(intent.getFlags() | DriveFile.MODE_READ_ONLY);
        this.context.startActivityForResult(intent, requestCode);
    }

    @Override // android.app.Activity
    @SuppressLint({"NewApi"})
    public void startActivityForResult(Intent intent, int requestCode, Bundle options) {
        intent.setFlags(intent.getFlags() | DriveFile.MODE_READ_ONLY);
        this.context.startActivityForResult(intent, requestCode, options);
    }

    @Override // android.app.Activity
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    }

    @Override // android.view.ContextThemeWrapper, android.content.ContextWrapper, android.content.Context
    public AssetManager getAssets() {
        return this.context.getAssets();
    }

    @Override // android.view.ContextThemeWrapper, android.content.ContextWrapper, android.content.Context
    public Resources getResources() {
        return this.context.getResources();
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public PackageManager getPackageManager() {
        return this.context.getPackageManager();
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public ContentResolver getContentResolver() {
        return this.context.getContentResolver();
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public Looper getMainLooper() {
        return this.context.getMainLooper();
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public Context getApplicationContext() {
        return this.context.getApplicationContext();
    }

    @Override // android.app.Activity, android.content.ContextWrapper, android.content.Context
    public void registerComponentCallbacks(ComponentCallbacks callback) {
        this.context.registerComponentCallbacks(callback);
    }

    @Override // android.app.Activity, android.content.ContextWrapper, android.content.Context
    public void unregisterComponentCallbacks(ComponentCallbacks callback) {
        this.context.unregisterComponentCallbacks(callback);
    }

    @Override // android.app.Activity, android.view.ContextThemeWrapper, android.content.ContextWrapper, android.content.Context
    public void setTheme(int resid) {
        this.context.setTheme(resid);
    }

    @Override // android.view.ContextThemeWrapper, android.content.ContextWrapper, android.content.Context
    public Resources.Theme getTheme() {
        return this.context.getTheme();
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public ClassLoader getClassLoader() {
        return this.context.getClassLoader();
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public String getPackageName() {
        return this.context.getPackageName();
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public ApplicationInfo getApplicationInfo() {
        return this.context.getApplicationInfo();
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public String getPackageResourcePath() {
        return this.context.getPackageResourcePath();
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public String getPackageCodePath() {
        return this.context.getPackageCodePath();
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public SharedPreferences getSharedPreferences(String name, int mode) {
        return this.context.getSharedPreferences(name, mode);
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public FileInputStream openFileInput(String name) throws FileNotFoundException {
        return this.context.openFileInput(name);
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public FileOutputStream openFileOutput(String name, int mode) throws FileNotFoundException {
        return this.context.openFileOutput(name, mode);
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public boolean deleteFile(String name) {
        return this.context.deleteFile(name);
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public File getFileStreamPath(String name) {
        return this.context.getFileStreamPath(name);
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public File getFilesDir() {
        return this.context.getFilesDir();
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public File getExternalFilesDir(String type) {
        return this.context.getExternalFilesDir(type);
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public File getObbDir() {
        return this.context.getObbDir();
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public File getCacheDir() {
        return this.context.getCacheDir();
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public File getExternalCacheDir() {
        return this.context.getExternalCacheDir();
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public String[] fileList() {
        return this.context.fileList();
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public File getDir(String name, int mode) {
        return this.context.getDir(name, mode);
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public SQLiteDatabase openOrCreateDatabase(String name, int mode, SQLiteDatabase.CursorFactory factory) {
        return this.context.openOrCreateDatabase(name, mode, factory);
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public SQLiteDatabase openOrCreateDatabase(String name, int mode, SQLiteDatabase.CursorFactory factory, DatabaseErrorHandler errorHandler) {
        return this.context.openOrCreateDatabase(name, mode, factory, errorHandler);
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public boolean deleteDatabase(String name) {
        return this.context.deleteDatabase(name);
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public File getDatabasePath(String name) {
        return this.context.getDatabasePath(name);
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public String[] databaseList() {
        return this.context.databaseList();
    }

    @Override // android.content.ContextWrapper, android.content.Context
    @Deprecated
    public Drawable getWallpaper() {
        return this.context.getWallpaper();
    }

    @Override // android.content.ContextWrapper, android.content.Context
    @Deprecated
    public Drawable peekWallpaper() {
        return this.context.peekWallpaper();
    }

    @Override // android.content.ContextWrapper, android.content.Context
    @Deprecated
    public int getWallpaperDesiredMinimumWidth() {
        return this.context.getWallpaperDesiredMinimumWidth();
    }

    @Override // android.content.ContextWrapper, android.content.Context
    @Deprecated
    public int getWallpaperDesiredMinimumHeight() {
        return this.context.getWallpaperDesiredMinimumHeight();
    }

    @Override // android.content.ContextWrapper, android.content.Context
    @Deprecated
    public void setWallpaper(Bitmap bitmap) throws IOException {
        this.context.setWallpaper(bitmap);
    }

    @Override // android.content.ContextWrapper, android.content.Context
    @Deprecated
    public void setWallpaper(InputStream data) throws IOException {
        this.context.setWallpaper(data);
    }

    @Override // android.content.ContextWrapper, android.content.Context
    @Deprecated
    public void clearWallpaper() throws IOException {
        this.context.clearWallpaper();
    }

    @Override // android.app.Activity, android.content.ContextWrapper, android.content.Context
    public void startActivity(Intent intent) {
        this.context.startActivity(intent);
    }

    @Override // android.app.Activity, android.content.ContextWrapper, android.content.Context
    public void startActivity(Intent intent, Bundle options) {
        this.context.startActivity(intent, options);
    }

    @Override // android.app.Activity, android.content.ContextWrapper, android.content.Context
    public void startActivities(Intent[] intents) {
        this.context.startActivities(intents);
    }

    @Override // android.app.Activity, android.content.ContextWrapper, android.content.Context
    public void startActivities(Intent[] intents, Bundle options) {
        this.context.startActivities(intents, options);
    }

    @Override // android.app.Activity, android.content.ContextWrapper, android.content.Context
    public void startIntentSender(IntentSender intent, Intent fillInIntent, int flagsMask, int flagsValues, int extraFlags) throws IntentSender.SendIntentException {
        this.context.startIntentSender(intent, fillInIntent, flagsMask, flagsValues, extraFlags);
    }

    @Override // android.app.Activity, android.content.ContextWrapper, android.content.Context
    public void startIntentSender(IntentSender intent, Intent fillInIntent, int flagsMask, int flagsValues, int extraFlags, Bundle options) throws IntentSender.SendIntentException {
        this.context.startIntentSender(intent, fillInIntent, flagsMask, flagsValues, extraFlags, options);
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public void sendBroadcast(Intent intent) {
        this.context.sendBroadcast(intent);
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public void sendBroadcast(Intent intent, String receiverPermission) {
        this.context.sendBroadcast(intent, receiverPermission);
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public void sendOrderedBroadcast(Intent intent, String receiverPermission) {
        this.context.sendOrderedBroadcast(intent, receiverPermission);
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public void sendOrderedBroadcast(Intent intent, String receiverPermission, BroadcastReceiver resultReceiver, Handler scheduler, int initialCode, String initialData, Bundle initialExtras) {
        this.context.sendOrderedBroadcast(intent, receiverPermission, resultReceiver, scheduler, initialCode, initialData, initialExtras);
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public void sendStickyBroadcast(Intent intent) {
        this.context.sendStickyBroadcast(intent);
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public void sendStickyOrderedBroadcast(Intent intent, BroadcastReceiver resultReceiver, Handler scheduler, int initialCode, String initialData, Bundle initialExtras) {
        this.context.sendStickyOrderedBroadcast(intent, resultReceiver, scheduler, initialCode, initialData, initialExtras);
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public void removeStickyBroadcast(Intent intent) {
        this.context.removeStickyBroadcast(intent);
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public Intent registerReceiver(BroadcastReceiver receiver, IntentFilter filter) {
        return this.context.registerReceiver(receiver, filter);
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public Intent registerReceiver(BroadcastReceiver receiver, IntentFilter filter, String broadcastPermission, Handler scheduler) {
        return this.context.registerReceiver(receiver, filter, broadcastPermission, scheduler);
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public void unregisterReceiver(BroadcastReceiver receiver) {
        this.context.unregisterReceiver(receiver);
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public ComponentName startService(Intent service) {
        return this.context.startService(service);
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public boolean stopService(Intent service) {
        return this.context.stopService(service);
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public boolean bindService(Intent service, ServiceConnection conn, int flags) {
        return this.context.bindService(service, conn, flags);
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public void unbindService(ServiceConnection conn) {
        this.context.unbindService(conn);
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public boolean startInstrumentation(ComponentName className, String profileFile, Bundle arguments) {
        return this.context.startInstrumentation(className, profileFile, arguments);
    }

    @Override // android.app.Activity, android.view.ContextThemeWrapper, android.content.ContextWrapper, android.content.Context
    public Object getSystemService(String name) {
        return this.context.getSystemService(name);
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public int checkPermission(String permission, int pid, int uid) {
        return this.context.checkPermission(permission, pid, uid);
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public int checkCallingPermission(String permission) {
        return this.context.checkCallingPermission(permission);
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public int checkCallingOrSelfPermission(String permission) {
        return this.context.checkCallingOrSelfPermission(permission);
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public void enforcePermission(String permission, int pid, int uid, String message) {
        this.context.enforcePermission(permission, pid, uid, message);
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public void enforceCallingPermission(String permission, String message) {
        this.context.enforceCallingPermission(permission, message);
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public void enforceCallingOrSelfPermission(String permission, String message) {
        this.context.enforceCallingOrSelfPermission(permission, message);
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public void grantUriPermission(String toPackage, Uri uri, int modeFlags) {
        this.context.grantUriPermission(toPackage, uri, modeFlags);
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public void revokeUriPermission(Uri uri, int modeFlags) {
        this.context.revokeUriPermission(uri, modeFlags);
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public int checkUriPermission(Uri uri, int pid, int uid, int modeFlags) {
        return this.context.checkUriPermission(uri, pid, uid, modeFlags);
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public int checkCallingUriPermission(Uri uri, int modeFlags) {
        return this.context.checkCallingUriPermission(uri, modeFlags);
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public int checkCallingOrSelfUriPermission(Uri uri, int modeFlags) {
        return this.context.checkCallingOrSelfUriPermission(uri, modeFlags);
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public int checkUriPermission(Uri uri, String readPermission, String writePermission, int pid, int uid, int modeFlags) {
        return this.context.checkUriPermission(uri, readPermission, writePermission, pid, uid, modeFlags);
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public void enforceUriPermission(Uri uri, int pid, int uid, int modeFlags, String message) {
        this.context.enforceUriPermission(uri, pid, uid, modeFlags, message);
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public void enforceCallingUriPermission(Uri uri, int modeFlags, String message) {
        this.context.enforceCallingUriPermission(uri, modeFlags, message);
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public void enforceCallingOrSelfUriPermission(Uri uri, int modeFlags, String message) {
        this.context.enforceCallingOrSelfUriPermission(uri, modeFlags, message);
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public void enforceUriPermission(Uri uri, String readPermission, String writePermission, int pid, int uid, int modeFlags, String message) {
        this.context.enforceUriPermission(uri, readPermission, writePermission, pid, uid, modeFlags, message);
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public Context createPackageContext(String packageName, int flags) throws PackageManager.NameNotFoundException {
        return this.context.createPackageContext(packageName, flags);
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public boolean isRestricted() {
        return this.context.isRestricted();
    }
}
