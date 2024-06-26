package com.assambra.test.app.config;

import com.assambra.test.app.UnityEngine.Time;
import com.assambra.test.app.server.UnityServerStarter;
import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfox.bean.annotation.EzyConfigurationBefore;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.ezyfox.util.EzyLoggable;
import com.tvd12.gamebox.constant.RoomStatus;
import com.tvd12.gamebox.entity.MMOGridRoom;
import com.tvd12.gamebox.entity.MMORoom;
import com.tvd12.gamebox.entity.MMOVirtualWorld;
import com.tvd12.gamebox.handler.MMORoomUpdatedHandler;

@EzyConfigurationBefore(priority = 2)
public class WorldConfig extends EzyLoggable {

    @EzyAutoBind
    MMOVirtualWorld mmoVirtualWorld;
    @EzyAutoBind
    MMORoomUpdatedHandler mmoRoomUpdatedHandler;
    @EzyAutoBind
    Time time;

    @EzySingleton
    public MMORoom world() {
        logger.info("Initialize world");
        MMORoom room =  MMOGridRoom.builder()
                .maxX(1000)
                .maxY(600)
                .maxZ(1000)
                .cellSize(100)
                .distanceOfInterest(150)
                .id(1)
                .addRoomUpdatedHandler(mmoRoomUpdatedHandler)
                .addRoomUpdatedHandler(time)
                .build();

        synchronized (mmoVirtualWorld){
            mmoVirtualWorld.addRoom(room);
        }

        room.setStatus(RoomStatus.PLAYING);

        UnityServerStarter serverStarter = new UnityServerStarter();
        serverStarter.spawnUnityServer("ServerWorld","123456");

        return room;
    }
}