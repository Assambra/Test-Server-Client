package com.assambra.app.service;

import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.ezyfox.util.EzyLoggable;
import com.tvd12.ezyfoxserver.entity.EzyUser;
import com.tvd12.gamebox.entity.*;
import com.tvd12.gamebox.manager.PlayerManager;
import com.tvd12.gamebox.manager.RoomManager;
import com.tvd12.gamebox.math.Vec3;
import lombok.AllArgsConstructor;

@EzySingleton
@AllArgsConstructor
@SuppressWarnings({"unchecked"})
public class RoomService extends EzyLoggable {

    private PlayerManager<Player> globalPlayerManager;
    private RoomManager<NormalRoom> globalRoomManager;
    private MMOGridRoom world;

    public MMOPlayer AddUser(EzyUser user) {
        long roomId = world.getId();
        MMORoom room = (MMORoom) globalRoomManager.getRoom(roomId);

        MMOPlayer player = new MMOPlayer(user.getName());
        globalPlayerManager.addPlayer(player);

        player.setCurrentRoomId(roomId);
        room.addPlayer(player);

        return player;
    }

    public void setPlayerPosition(MMOPlayer player, Vec3 position) {
        MMOGridRoom currentRoom = (MMOGridRoom) getCurrentRoom(player);
        currentRoom.setPlayerPosition(player, position);
    }

    public NormalRoom getCurrentRoom(Player player) {
        long currentRoomId = player.getCurrentRoomId();
        return globalRoomManager.getRoom(currentRoomId);
    }

    public MMOPlayer getPlayer(String playerName) {
        return (MMOPlayer) globalPlayerManager.getPlayer(playerName);
    }
}
