package com.google.android.gms.dynamic;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

/* JADX INFO: loaded from: classes.dex */
public final class h extends c.a {
    private Fragment Hz;

    private h(Fragment fragment) {
        this.Hz = fragment;
    }

    public static h a(Fragment fragment) {
        if (fragment != null) {
            return new h(fragment);
        }
        return null;
    }

    @Override // com.google.android.gms.dynamic.c
    public void b(d dVar) {
        this.Hz.registerForContextMenu((View) e.d(dVar));
    }

    @Override // com.google.android.gms.dynamic.c
    public void c(d dVar) {
        this.Hz.unregisterForContextMenu((View) e.d(dVar));
    }

    @Override // com.google.android.gms.dynamic.c
    public d fX() {
        return e.h(this.Hz.getActivity());
    }

    @Override // com.google.android.gms.dynamic.c
    public c fY() {
        return a(this.Hz.getParentFragment());
    }

    @Override // com.google.android.gms.dynamic.c
    public d fZ() {
        return e.h(this.Hz.getResources());
    }

    @Override // com.google.android.gms.dynamic.c
    public c ga() {
        return a(this.Hz.getTargetFragment());
    }

    @Override // com.google.android.gms.dynamic.c
    public Bundle getArguments() {
        return this.Hz.getArguments();
    }

    @Override // com.google.android.gms.dynamic.c
    public int getId() {
        return this.Hz.getId();
    }

    @Override // com.google.android.gms.dynamic.c
    public boolean getRetainInstance() {
        return this.Hz.getRetainInstance();
    }

    @Override // com.google.android.gms.dynamic.c
    public String getTag() {
        return this.Hz.getTag();
    }

    @Override // com.google.android.gms.dynamic.c
    public int getTargetRequestCode() {
        return this.Hz.getTargetRequestCode();
    }

    @Override // com.google.android.gms.dynamic.c
    public boolean getUserVisibleHint() {
        return this.Hz.getUserVisibleHint();
    }

    @Override // com.google.android.gms.dynamic.c
    public d getView() {
        return e.h(this.Hz.getView());
    }

    @Override // com.google.android.gms.dynamic.c
    public boolean isAdded() {
        return this.Hz.isAdded();
    }

    @Override // com.google.android.gms.dynamic.c
    public boolean isDetached() {
        return this.Hz.isDetached();
    }

    @Override // com.google.android.gms.dynamic.c
    public boolean isHidden() {
        return this.Hz.isHidden();
    }

    @Override // com.google.android.gms.dynamic.c
    public boolean isInLayout() {
        return this.Hz.isInLayout();
    }

    @Override // com.google.android.gms.dynamic.c
    public boolean isRemoving() {
        return this.Hz.isRemoving();
    }

    @Override // com.google.android.gms.dynamic.c
    public boolean isResumed() {
        return this.Hz.isResumed();
    }

    @Override // com.google.android.gms.dynamic.c
    public boolean isVisible() {
        return this.Hz.isVisible();
    }

    @Override // com.google.android.gms.dynamic.c
    public void setHasOptionsMenu(boolean hasMenu) {
        this.Hz.setHasOptionsMenu(hasMenu);
    }

    @Override // com.google.android.gms.dynamic.c
    public void setMenuVisibility(boolean menuVisible) {
        this.Hz.setMenuVisibility(menuVisible);
    }

    @Override // com.google.android.gms.dynamic.c
    public void setRetainInstance(boolean retain) {
        this.Hz.setRetainInstance(retain);
    }

    @Override // com.google.android.gms.dynamic.c
    public void setUserVisibleHint(boolean isVisibleToUser) {
        this.Hz.setUserVisibleHint(isVisibleToUser);
    }

    @Override // com.google.android.gms.dynamic.c
    public void startActivity(Intent intent) {
        this.Hz.startActivity(intent);
    }

    @Override // com.google.android.gms.dynamic.c
    public void startActivityForResult(Intent intent, int requestCode) {
        this.Hz.startActivityForResult(intent, requestCode);
    }
}
