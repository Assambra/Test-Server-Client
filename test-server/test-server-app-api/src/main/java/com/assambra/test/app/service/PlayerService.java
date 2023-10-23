package com.assambra.test.app.service;

import com.assambra.test.app.model.PlayerInputModel;
import com.assambra.test.app.utils.PlayerMovementUtils;
import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.ezyfox.util.EzyLoggable;
import com.tvd12.gamebox.entity.MMOPlayer;
import com.tvd12.gamebox.math.Vec3;
import lombok.Setter;

@Setter
@EzySingleton
public class PlayerService extends EzyLoggable {

    @EzyAutoBind
    RoomService roomService;


    public void handlePlayerInput(String playerName, PlayerInputModel model)
    {
        MMOPlayer player = roomService.getPlayer(playerName);
        synchronized (player){
            Vec3 currentPosition = player.getPosition();
            Vec3 currentRotation = player.getRotation();
            Vec3 nextPosition = PlayerMovementUtils.getNextPosition(currentPosition, model);
            Vec3 nextRotation = PlayerMovementUtils.getNextRotation(currentRotation, model);
            //logger.info("Forward: {}", PlayerMovementUtils.GetForwardDirection(nextRotation));
            //logger.info("next position = {}", nextPosition);

            roomService.setPlayerPosition(player, nextPosition);
            player.setRotation(nextRotation);
            player.setClientTimeTick(model.getTime());
        }
    }
}
