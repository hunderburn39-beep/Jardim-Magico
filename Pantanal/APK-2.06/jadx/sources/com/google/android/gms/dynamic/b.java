package com.google.android.gms.dynamic;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/* JADX INFO: loaded from: classes.dex */
public final class b extends c.a {
    private Fragment Hv;

    private b(Fragment fragment) {
        this.Hv = fragment;
    }

    public static b a(Fragment fragment) {
        if (fragment != null) {
            return new b(fragment);
        }
        return null;
    }

    @Override // com.google.android.gms.dynamic.c
    public void b(d dVar) {
        this.Hv.registerForContextMenu((View) e.d(dVar));
    }

    @Override // com.google.android.gms.dynamic.c
    public void c(d dVar) {
        this.Hv.unregisterForContextMenu((View) e.d(dVar));
    }

    @Override // com.google.android.gms.dynamic.c
    public d fX() {
        return e.h(this.Hv.getActivity());
    }

    @Override // com.google.android.gms.dynamic.c
    public c fY() {
        return a(this.Hv.getParentFragment());
    }

    @Override // com.google.android.gms.dynamic.c
    public d fZ() {
        return e.h(this.Hv.getResources());
    }

    @Override // com.google.android.gms.dynamic.c
    public c ga() {
        return a(this.Hv.getTargetFragment());
    }

    @Override // com.google.android.gms.dynamic.c
    public Bundle getArguments() {
        return this.Hv.getArguments();
    }

    @Override // com.google.android.gms.dynamic.c
    public int getId() {
        return this.Hv.getId();
    }

    @Override // com.google.android.gms.dynamic.c
    public boolean getRetainInstance() {
        return this.Hv.getRetainInstance();
    }

    @Override // com.google.android.gms.dynamic.c
    public String getTag() {
        return this.Hv.getTag();
    }

    @Override // com.google.android.gms.dynamic.c
    public int getTargetRequestCode() {
        return this.Hv.getTargetRequestCode();
    }

    @Override // com.google.android.gms.dynamic.c
    public boolean getUserVisibleHint() {
        return this.Hv.getUserVisibleHint();
    }

    @Override // com.google.android.gms.dynamic.c
    public d getView() {
        return e.h(this.Hv.getView());
    }

    @Override // com.google.android.gms.dynamic.c
    public boolean isAdded() {
        return this.Hv.isAdded();
    }

    @Override // com.google.android.gms.dynamic.c
    public boolean isDetached() {
        return this.Hv.isDetached();
    }

    @Override // com.google.android.gms.dynamic.c
    public boolean isHidden() {
        return this.Hv.isHidden();
    }

    @Override // com.google.android.gms.dynamic.c
    public boolean isInLayout() {
        return this.Hv.isInLayout();
    }

    @Override // com.google.android.gms.dynamic.c
    public boolean isRemoving() {
        return this.Hv.isRemoving();
    }

    @Override // com.google.android.gms.dynamic.c
    public boolean isResumed() {
        return this.Hv.isResumed();
    }

    @Override // com.google.android.gms.dynamic.c
    public boolean isVisible() {
        return this.Hv.isVisible();
    }

    @Override // com.google.android.gms.dynamic.c
    public void setHasOptionsMenu(boolean hasMenu) {
        this.Hv.setHasOptionsMenu(hasMenu);
    }

    @Override // com.google.android.gms.dynamic.c
    public void setMenuVisibility(boolean menuVisible) {
        this.Hv.setMenuVisibility(menuVisible);
    }

    @Override // com.google.android.gms.dynamic.c
    public void setRetainInstance(boolean retain) {
        this.Hv.setRetainInstance(retain);
    }

    @Override // com.google.android.gms.dynamic.c
    public void setUserVisibleHint(boolean isVisibleToUser) {
        this.Hv.setUserVisibleHint(isVisibleToUser);
    }

    @Override // com.google.android.gms.dynamic.c
    public void startActivity(Intent intent) {
        this.Hv.startActivity(intent);
    }

    @Override // com.google.android.gms.dynamic.c
    public void startActivityForResult(Intent intent, int requestCode) {
        this.Hv.startActivityForResult(intent, requestCode);
    }
}
