package com.tapjoy.internal;

import android.graphics.Bitmap;
import java.net.URL;

/* JADX INFO: loaded from: classes.dex */
public final class gt {
    public static final bm c;
    private static final ar d;
    public URL a;
    public Bitmap b;

    static {
        ar avVar = new av();
        if (!(avVar instanceof aw)) {
            avVar = new at.a((au) avVar);
        }
        d = avVar;
        c = new bm() { // from class: com.tapjoy.internal.gt.1
            @Override // com.tapjoy.internal.bm
            public final /* synthetic */ Object a(br brVar) {
                return new gt(brVar);
            }
        };
    }

    public gt(URL url) {
        this.a = url;
    }

    /*  JADX ERROR: JadxRuntimeException in pass: ProcessVariables
        jadx.core.utils.exceptions.JadxRuntimeException: Method arg registers not loaded: com.tapjoy.internal.gp.2.<init>(com.tapjoy.internal.gp, java.net.URL, java.io.InputStream, long):void, class status: GENERATED_AND_UNLOADED
        	at jadx.core.dex.nodes.MethodNode.getArgRegs(MethodNode.java:309)
        	at jadx.core.dex.visitors.regions.variables.ProcessVariables$1.isArgUnused(ProcessVariables.java:146)
        	at jadx.core.dex.visitors.regions.variables.ProcessVariables$1.lambda$isVarUnused$0(ProcessVariables.java:131)
        	at jadx.core.utils.ListUtils.allMatch(ListUtils.java:224)
        	at jadx.core.dex.visitors.regions.variables.ProcessVariables$1.isVarUnused(ProcessVariables.java:131)
        	at jadx.core.dex.visitors.regions.variables.ProcessVariables$1.processBlock(ProcessVariables.java:82)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:93)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverse(DepthRegionTraversal.java:27)
        	at jadx.core.dex.visitors.regions.variables.ProcessVariables.removeUnusedResults(ProcessVariables.java:73)
        	at jadx.core.dex.visitors.regions.variables.ProcessVariables.visit(ProcessVariables.java:48)
        */
    public final void a() throws java.io.IOException {
        /*
            Method dump skipped, instruction units count: 234
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tapjoy.internal.gt.a():void");
    }

    gt(br brVar) {
        if (brVar.k() == bw.STRING) {
            this.a = brVar.e();
            return;
        }
        brVar.h();
        String strL = brVar.l();
        while (brVar.j()) {
            if ("url".equals(strL)) {
                this.a = brVar.e();
            } else {
                brVar.s();
            }
        }
        brVar.i();
    }
}
