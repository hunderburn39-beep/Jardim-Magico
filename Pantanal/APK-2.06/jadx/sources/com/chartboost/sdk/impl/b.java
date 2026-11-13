package com.chartboost.sdk.impl;

import android.os.Handler;
import com.chartboost.sdk.Libraries.CBLogging;
import com.chartboost.sdk.Libraries.CBUtility;
import com.chartboost.sdk.Model.CBError;
import com.tapjoy.TJAdUnitConstants;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import org.anddev.andengine.entity.layer.tiled.tmx.util.constants.TMXConstants;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class b {
    private final Executor c;
    private final AtomicReference<com.chartboost.sdk.Model.c> d;
    private final com.chartboost.sdk.Tracking.a e;
    private boolean m = false;
    private final com.chartboost.sdk.Libraries.g a = new com.chartboost.sdk.Libraries.g(true);
    private final Handler b = CBUtility.c();
    private final Set<String> f = new HashSet();
    private final Map<String, Set<String>> g = new HashMap();
    private final Map<String, Set<String>> h = new HashMap();
    private final Map<com.chartboost.sdk.Model.a, Set<String>> i = new IdentityHashMap();
    private final Map<String, Set<com.chartboost.sdk.Model.a>> j = new HashMap();
    private final Map<com.chartboost.sdk.Model.a, f> k = new IdentityHashMap();
    private com.chartboost.sdk.Libraries.h l = this.a.d();

    public b(Executor executor, AtomicReference<com.chartboost.sdk.Model.c> atomicReference, com.chartboost.sdk.Tracking.a aVar) {
        this.c = executor;
        this.d = atomicReference;
        this.e = aVar;
        e();
    }

    synchronized void a(JSONObject jSONObject) {
        try {
            if (this.l != this.a.d()) {
                e();
            }
            String string = jSONObject.getString("template");
            HashSet<String> hashSet = new HashSet();
            JSONArray jSONArray = jSONObject.getJSONArray("elements");
            int length = jSONArray.length();
            for (int i = 0; i < length; i++) {
                JSONObject jSONObject2 = jSONArray.getJSONObject(i);
                String string2 = jSONObject2.getString("name");
                String string3 = jSONObject2.getString("type");
                if (!string3.equals("param") && !jSONObject2.has("param")) {
                    String str = string3 + "/" + string2;
                    if (!new File(this.l.a, str).exists()) {
                        hashSet.add(str);
                    }
                }
            }
            this.f.remove(string);
            Set<String> setRemove = this.g.remove(string);
            if (setRemove != null) {
                for (String str2 : setRemove) {
                    Set<String> set = this.h.get(str2);
                    if (set != null && set.remove(string) && set.isEmpty()) {
                        this.h.remove(str2);
                    }
                }
            }
            if (hashSet.isEmpty()) {
                this.f.add(string);
            } else {
                this.g.put(string, hashSet);
                for (String str3 : hashSet) {
                    Set<String> hashSet2 = this.h.get(str3);
                    if (hashSet2 == null) {
                        hashSet2 = new HashSet<>();
                        this.h.put(str3, hashSet2);
                    }
                    hashSet2.add(string);
                }
            }
        } catch (Exception e) {
            com.chartboost.sdk.Tracking.a.a(getClass(), "registerTemplateManifest", e);
        }
    }

    synchronized void a(String str, String str2) {
        try {
            if (this.l != this.a.d()) {
                e();
            }
            String str3 = str + "/" + str2;
            if (!new File(this.l.a, str3).exists()) {
                CBLogging.b("AssetManager", "Ignoring registerFile for " + str3 + " because it does not exist");
            } else {
                Set<String> setRemove = this.h.remove(str3);
                if (setRemove != null) {
                    for (String str4 : setRemove) {
                        Set<String> set = this.g.get(str4);
                        if (set != null && set.remove(str3) && set.isEmpty()) {
                            this.g.remove(str4);
                            this.f.add(str4);
                        }
                    }
                }
                Set<com.chartboost.sdk.Model.a> setRemove2 = this.j.remove(str3);
                if (setRemove2 != null) {
                    for (com.chartboost.sdk.Model.a aVar : setRemove2) {
                        Set<String> set2 = this.i.get(aVar);
                        if (set2 != null && set2.remove(str3) && set2.isEmpty()) {
                            this.i.remove(aVar);
                            f fVarRemove = this.k.remove(aVar);
                            if (fVarRemove != null) {
                                this.c.execute(fVarRemove);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            com.chartboost.sdk.Tracking.a.a(getClass(), "registerFile", e);
        }
    }

    /* JADX WARN: Not initialized variable reg: 7, insn: 0x01a0: IF  (r7 I:??[int, boolean, OBJECT, ARRAY, byte, short, char]) == (0 ??[int, boolean, OBJECT, ARRAY, byte, short, char])  -> B:109:?, block:B:78:0x01a0 */
    synchronized void a(com.chartboost.sdk.Model.a aVar, JSONObject jSONObject) {
        CBError.CBImpressionError cBImpressionError;
        String str;
        try {
            CBError.CBImpressionError cBImpressionError2 = CBError.CBImpressionError.ERROR_LOADING_WEB_VIEW;
            try {
                if (this.l != this.a.d()) {
                    e();
                }
                if (jSONObject.getString("template").equals("")) {
                    CBLogging.b("AssetManager", "Template manifest does not have a templateId");
                    if (cBImpressionError2 != null) {
                        f fVar = new f(aVar, jSONObject, null, Collections.emptyMap(), this.b);
                        fVar.c = cBImpressionError2;
                        this.b.post(fVar);
                    }
                } else {
                    JSONArray jSONArray = jSONObject.getJSONArray("elements");
                    if (jSONArray.length() == 0) {
                        CBLogging.b("AssetManager", "Template manifest does not have any elements");
                        if (cBImpressionError2 != null) {
                            f fVar2 = new f(aVar, jSONObject, null, Collections.emptyMap(), this.b);
                            fVar2.c = cBImpressionError2;
                            this.b.post(fVar2);
                        }
                    } else {
                        HashSet<String> hashSet = new HashSet();
                        HashMap map = new HashMap();
                        int i = 0;
                        String str2 = null;
                        while (i < jSONArray.length()) {
                            JSONObject jSONObject2 = jSONArray.getJSONObject(i);
                            String strOptString = jSONObject2.optString("type");
                            String strOptString2 = jSONObject2.optString("name");
                            String strOptString3 = jSONObject2.optString(TMXConstants.TAG_PROPERTY_ATTRIBUTE_VALUE);
                            String strOptString4 = jSONObject2.optString("param");
                            if (strOptString4.isEmpty()) {
                                str = strOptString.equals(TJAdUnitConstants.String.HTML) ? strOptString2 : str2;
                            } else if (strOptString.equals("param")) {
                                map.put(strOptString4, strOptString3);
                                str = str2;
                            } else {
                                map.put(strOptString4, strOptString2);
                            }
                            if (!strOptString.equals("param")) {
                                String str3 = strOptString + "/" + strOptString2;
                                if (!new File(this.l.a, str3).exists()) {
                                    hashSet.add(str3);
                                }
                            }
                            i++;
                            str2 = str;
                        }
                        if (str2 == null) {
                            CBLogging.b("AssetManager", "Template manifest does not have an html element");
                            if (cBImpressionError2 != null) {
                                f fVar3 = new f(aVar, jSONObject, null, Collections.emptyMap(), this.b);
                                fVar3.c = cBImpressionError2;
                                this.b.post(fVar3);
                            }
                        } else {
                            Set<String> setRemove = this.i.remove(aVar);
                            if (setRemove != null) {
                                for (String str4 : setRemove) {
                                    Set<com.chartboost.sdk.Model.a> set = this.j.get(str4);
                                    if (set != null && set.remove(aVar) && set.isEmpty()) {
                                        this.j.remove(str4);
                                    }
                                }
                            }
                            if (!hashSet.isEmpty()) {
                                for (String str5 : hashSet) {
                                    Set<com.chartboost.sdk.Model.a> setNewSetFromMap = this.j.get(str5);
                                    if (setNewSetFromMap == null) {
                                        setNewSetFromMap = Collections.newSetFromMap(new IdentityHashMap());
                                        this.j.put(str5, setNewSetFromMap);
                                    }
                                    setNewSetFromMap.add(aVar);
                                }
                                this.i.put(aVar, hashSet);
                            }
                            f fVar4 = new f(aVar, jSONObject, new File(this.l.c, str2), map, this.b);
                            if (hashSet.isEmpty()) {
                                this.c.execute(fVar4);
                            } else {
                                this.k.put(aVar, fVar4);
                            }
                            if (0 != 0) {
                                f fVar5 = new f(aVar, jSONObject, null, Collections.emptyMap(), this.b);
                                fVar5.c = null;
                                this.b.post(fVar5);
                            }
                        }
                    }
                }
            } catch (Exception e) {
                com.chartboost.sdk.Tracking.a.a(getClass(), "requestTemplate", e);
                if (cBImpressionError2 != null) {
                    f fVar6 = new f(aVar, jSONObject, null, Collections.emptyMap(), this.b);
                    fVar6.c = cBImpressionError2;
                    this.b.post(fVar6);
                }
            }
        } catch (Throwable th) {
            if (cBImpressionError == null) {
                throw th;
            }
            f fVar7 = new f(aVar, jSONObject, null, Collections.emptyMap(), this.b);
            fVar7.c = cBImpressionError;
            this.b.post(fVar7);
            throw th;
        }
    }

    synchronized void a() {
        for (f fVar : this.k.values()) {
            fVar.c = CBError.CBImpressionError.ASSETS_DOWNLOAD_FAILURE;
            this.b.post(fVar);
        }
        this.g.clear();
        this.h.clear();
        this.i.clear();
        this.j.clear();
        this.k.clear();
    }

    public synchronized JSONObject b() {
        JSONObject jSONObjectD;
        String[] list;
        try {
            jSONObjectD = g.a().d();
            try {
                if (this.l != this.a.d()) {
                    e();
                }
                JSONArray jSONArrayE = g.a().e();
                Iterator<String> it = this.f.iterator();
                while (it.hasNext()) {
                    jSONArrayE.put(it.next());
                }
                com.chartboost.sdk.Libraries.d.a(jSONObjectD, "templates", jSONArrayE);
                for (String str : this.d.get().t) {
                    if (!str.equals("templates")) {
                        File file = new File(this.a.d().a, str);
                        JSONArray jSONArrayE2 = g.a().e();
                        if (file.exists() && (list = file.list()) != null) {
                            for (String str2 : list) {
                                if (!str2.equals(".nomedia") && !str2.endsWith(".tmp")) {
                                    jSONArrayE2.put(str2);
                                }
                            }
                        }
                        com.chartboost.sdk.Libraries.d.a(jSONObjectD, str, jSONArrayE2);
                    }
                }
            } catch (Exception e) {
                com.chartboost.sdk.Tracking.a.a(getClass(), "getWebViewCacheAssets", e);
            }
        } catch (Throwable th) {
            throw th;
        }
        return jSONObjectD;
    }

    public boolean a(JSONObject jSONObject, File file) {
        try {
            JSONArray jSONArray = jSONObject.getJSONArray("elements");
            int length = jSONArray.length();
            for (int i = 0; i < length; i++) {
                JSONObject jSONObject2 = jSONArray.getJSONObject(i);
                String string = jSONObject2.getString("name");
                String string2 = jSONObject2.getString("type");
                if (!string2.equals("param")) {
                    String str = string2 + "/" + string;
                    if (!new File(file, str).exists()) {
                        CBLogging.b("AssetManager", "Asset for template does not exist: " + str);
                        return false;
                    }
                }
            }
            return true;
        } catch (Exception e) {
            com.chartboost.sdk.Tracking.a.a(getClass(), "allTemplateAssetsAvailable", e);
            return false;
        }
    }

    public synchronized JSONArray c() {
        JSONArray jSONArrayE;
        jSONArrayE = g.a().e();
        String[] list = this.a.d().g.list();
        if (list != null) {
            for (String str : list) {
                if (!str.equals(".nomedia") && !str.endsWith(".tmp")) {
                    jSONArrayE.put(str);
                }
            }
        }
        return jSONArrayE;
    }

    public boolean b(JSONObject jSONObject) {
        JSONObject jSONObjectOptJSONObject;
        if (jSONObject != null && (jSONObjectOptJSONObject = jSONObject.optJSONObject("assets")) != null) {
            JSONObject jSONObjectOptJSONObject2 = jSONObjectOptJSONObject.optJSONObject(CBUtility.a().a() ? "video-portrait" : "video-landscape");
            if (jSONObjectOptJSONObject2 == null) {
                return false;
            }
            String strOptString = jSONObjectOptJSONObject2.optString(TMXConstants.TAG_TILE_ATTRIBUTE_ID);
            if (strOptString.isEmpty()) {
                return false;
            }
            return new File(this.a.d().g, strOptString).exists();
        }
        return false;
    }

    public synchronized void d() {
        File[] fileArrListFiles;
        boolean z = false;
        try {
            try {
                CBLogging.a("AssetManager", "########### Trimming the disk cache");
                File file = this.a.d().a;
                ArrayList arrayList = new ArrayList();
                String[] list = file.list();
                if (list != null && list.length > 0) {
                    for (String str : list) {
                        if (!str.equalsIgnoreCase(com.chartboost.sdk.Libraries.g.a.RequestManager.toString()) && !str.equalsIgnoreCase(com.chartboost.sdk.Libraries.g.a.Track.toString()) && !str.equalsIgnoreCase(com.chartboost.sdk.Libraries.g.a.Session.toString()) && !str.equalsIgnoreCase(com.chartboost.sdk.Libraries.g.a.VideoCompletion.toString()) && !str.contains(".") && (fileArrListFiles = new File(file, str).listFiles()) != null) {
                            for (File file2 : fileArrListFiles) {
                                if (file2.isFile() && !file2.getName().equals(".nomedia")) {
                                    arrayList.add(file2);
                                }
                            }
                        }
                    }
                }
                File[] fileArr = new File[arrayList.size()];
                arrayList.toArray(fileArr);
                if (fileArr.length > 1) {
                    Arrays.sort(fileArr, new Comparator<File>() { // from class: com.chartboost.sdk.impl.b.1
                        @Override // java.util.Comparator
                        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
                        public int compare(File file3, File file4) {
                            return Long.valueOf(file3.lastModified()).compareTo(Long.valueOf(file4.lastModified()));
                        }
                    });
                }
                if (fileArr.length > 0) {
                    com.chartboost.sdk.Model.c cVar = this.d.get();
                    long j = cVar.q;
                    long jC = com.chartboost.sdk.Libraries.g.c(this.a.d().g);
                    long jCurrentTimeMillis = System.currentTimeMillis();
                    List<String> list2 = cVar.d;
                    CBLogging.a("AssetManager", "Total local file count:" + fileArr.length);
                    CBLogging.a("AssetManager", "Video Folder Size in bytes :" + jC);
                    CBLogging.a("AssetManager", "Max Bytes allowed:" + j);
                    long length = jC;
                    for (File file3 : fileArr) {
                        boolean z2 = TimeUnit.MILLISECONDS.toDays(jCurrentTimeMillis - file3.lastModified()) >= ((long) cVar.s);
                        boolean zEndsWith = file3.getName().endsWith(".tmp");
                        String name = file3.getParentFile().getName();
                        boolean zEquals = name.equals(com.chartboost.sdk.Libraries.g.a.Videos.toString());
                        if (file3.length() == 0 || zEndsWith || z2 || list2.contains(name) || ((length > j ? 1 : (length == j ? 0 : -1)) > 0 && zEquals)) {
                            if (zEquals) {
                                length -= file3.length();
                            }
                            CBLogging.a("AssetManager", "Deleting file at path:" + file3.getPath());
                            if (file3.delete()) {
                                z = true;
                            } else {
                                CBLogging.b("AssetManager", "Unable to delete " + file3.getPath());
                            }
                        }
                    }
                }
                this.e.a(com.chartboost.sdk.Libraries.g.f());
                if (z) {
                    e();
                }
            } catch (Exception e) {
                com.chartboost.sdk.Tracking.a.a(getClass(), "reduceCacheSize", e);
                if (z) {
                    e();
                }
            }
        } catch (Throwable th) {
            if (0 != 0) {
                e();
            }
            throw th;
        }
    }

    public synchronized void e() {
        boolean z;
        File[] fileArrListFiles;
        JSONObject jSONObjectA;
        if (!this.m) {
            try {
                this.m = true;
                this.l = this.a.d();
                this.f.clear();
                this.g.clear();
                this.h.clear();
                this.i.clear();
                this.j.clear();
                ArrayList<f> arrayList = new ArrayList();
                arrayList.addAll(this.k.values());
                this.k.clear();
                File[] fileArrListFiles2 = this.l.f.listFiles();
                if (fileArrListFiles2 != null) {
                    for (File file : fileArrListFiles2) {
                        if (file.isFile() && file.getName().endsWith(".json") && (jSONObjectA = this.a.a(file)) != null) {
                            a(jSONObjectA);
                        }
                    }
                }
                File[] fileArrListFiles3 = this.l.a.listFiles();
                if (fileArrListFiles3 != null) {
                    for (File file2 : fileArrListFiles3) {
                        String name = file2.getName();
                        if (!name.equalsIgnoreCase(com.chartboost.sdk.Libraries.g.a.TemplateMetaData.toString()) && !name.equalsIgnoreCase(com.chartboost.sdk.Libraries.g.a.RequestManager.toString()) && !name.equalsIgnoreCase(com.chartboost.sdk.Libraries.g.a.Track.toString()) && !name.equalsIgnoreCase(com.chartboost.sdk.Libraries.g.a.Session.toString()) && !name.equalsIgnoreCase(com.chartboost.sdk.Libraries.g.a.VideoCompletion.toString()) && !name.contains(".") && (fileArrListFiles = file2.listFiles()) != null) {
                            for (File file3 : fileArrListFiles) {
                                String name2 = file3.getName();
                                if (!name2.equals(".nomedia") && !name2.endsWith(".tmp")) {
                                    a(name, name2);
                                }
                            }
                        }
                    }
                }
                for (f fVar : arrayList) {
                    a(fVar.a, fVar.b);
                }
                z = false;
            } catch (Exception e) {
                com.chartboost.sdk.Tracking.a.a(getClass(), "updateTemplatesFromCache", e);
                z = false;
            } finally {
                this.m = false;
            }
        }
    }
}
