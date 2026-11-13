package com.tapjoy.mraid.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import com.tapjoy.mraid.controller.Abstract;
import com.tapjoy.mraid.listener.Player;
import com.tapjoy.mraid.util.MraidPlayer;
import com.tapjoy.mraid.util.Utils;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class ActionHandler extends Activity {
    private HashMap a = new HashMap();
    private RelativeLayout b;

    @Override // android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        Bundle extras = getIntent().getExtras();
        this.b = new RelativeLayout(this);
        this.b.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
        this.b.setBackgroundColor(-16777216);
        setContentView(this.b);
        String string = extras.getString(MraidView.ACTION_KEY);
        if (string != null) {
            MraidView.Action actionValueOf = MraidView.Action.valueOf(string);
            switch (actionValueOf) {
                case PLAY_AUDIO:
                    a(extras, actionValueOf).playAudio();
                    break;
                case PLAY_VIDEO:
                    a(extras, actionValueOf).playVideo();
                    break;
            }
        }
    }

    private MraidPlayer a(Bundle bundle, MraidView.Action action) {
        RelativeLayout.LayoutParams layoutParams;
        Abstract.PlayerProperties playerProperties = (Abstract.PlayerProperties) bundle.getParcelable(MraidView.PLAYER_PROPERTIES);
        Abstract.Dimensions dimensions = (Abstract.Dimensions) bundle.getParcelable(MraidView.DIMENSIONS);
        MraidPlayer mraidPlayer = new MraidPlayer(this);
        mraidPlayer.setPlayData(playerProperties, Utils.getData(MraidView.EXPAND_URL, bundle));
        if (!playerProperties.inline && playerProperties.startStyle.equals(Abstract.FULL_SCREEN)) {
            getWindow().setFlags(1024, 1024);
            getWindow().setFlags(16777216, 16777216);
            layoutParams = new RelativeLayout.LayoutParams(-1, -1);
            layoutParams.addRule(13);
        } else {
            layoutParams = new RelativeLayout.LayoutParams(dimensions.width, dimensions.height);
            layoutParams.topMargin = dimensions.y;
            layoutParams.leftMargin = dimensions.x;
        }
        mraidPlayer.setLayoutParams(layoutParams);
        this.b.addView(mraidPlayer);
        this.a.put(action, mraidPlayer);
        mraidPlayer.setListener(new Player() { // from class: com.tapjoy.mraid.view.ActionHandler.1
            @Override // com.tapjoy.mraid.listener.Player
            public final void onPrepared() {
            }

            @Override // com.tapjoy.mraid.listener.Player
            public final void onError() {
                ActionHandler.this.finish();
            }

            @Override // com.tapjoy.mraid.listener.Player
            public final void onComplete() {
                ActionHandler.this.finish();
            }
        });
        return mraidPlayer;
    }

    @Override // android.app.Activity
    protected void onStop() {
        for (Map.Entry entry : this.a.entrySet()) {
            switch ((MraidView.Action) entry.getKey()) {
                case PLAY_AUDIO:
                case PLAY_VIDEO:
                    ((MraidPlayer) entry.getValue()).releasePlayer();
                    break;
            }
        }
        super.onStop();
    }
}
