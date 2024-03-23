package com.assambra.test.app.UnityEngine;

import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.ezyfox.util.EzyLoggable;
import com.tvd12.gamebox.entity.MMOPlayer;
import com.tvd12.gamebox.entity.MMORoom;
import com.tvd12.gamebox.handler.MMORoomUpdatedHandler;
import org.apache.commons.math3.util.Precision;

import java.util.List;

@EzySingleton
public class Time extends EzyLoggable implements MMORoomUpdatedHandler {
    public static float deltaTime;
    private long lastFrameTime;


    @Override
    public void onRoomUpdated(MMORoom mmoRoom, List<MMOPlayer> list) {
        long currentTime = System.nanoTime();
        deltaTime = Precision.round(((currentTime - lastFrameTime) / 1_000_000_000.0f),2);
        lastFrameTime = currentTime;

        //logger.info("{}",deltaTime);
    }
}
