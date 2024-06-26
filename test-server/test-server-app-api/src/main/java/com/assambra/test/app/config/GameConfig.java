package com.assambra.test.app.config;

import com.assambra.test.app.service.RoomService;
import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfox.bean.annotation.EzyConfigurationBefore;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.ezyfox.util.EzyLoggable;
import com.tvd12.gamebox.entity.MMOGridRoom;
import com.tvd12.gamebox.entity.NormalRoom;
import com.tvd12.gamebox.entity.Player;
import com.tvd12.gamebox.manager.PlayerManager;
import com.tvd12.gamebox.manager.RoomManager;
import com.tvd12.gamebox.manager.SynchronizedPlayerManager;
import com.tvd12.gamebox.manager.SynchronizedRoomManager;
import lombok.Setter;

@Setter
@EzyConfigurationBefore(priority = 3)
public class GameConfig extends EzyLoggable {

    @EzyAutoBind
    private MMOGridRoom world;

    @EzySingleton("globalRoomManager")
    public RoomManager<NormalRoom> globalRoomManager() {
        RoomManager<NormalRoom> roomManager = new SynchronizedRoomManager<>();
        roomManager.addRoom(world);
        return roomManager;
    }

    @EzySingleton("globalPlayerManager")
    public PlayerManager<Player> globalPlayerManager() {
        return new SynchronizedPlayerManager<>();
    }
}

