package com.assambra.app.controller;

import com.assambra.app.constant.Commands;
import com.assambra.app.converter.ModelToResponseConverter;
import com.assambra.app.model.EntitySpawnModel;
import com.assambra.app.service.GameService;
import com.assambra.app.service.RoomService;
import com.tvd12.ezyfox.core.annotation.EzyDoHandle;
import com.tvd12.ezyfox.core.annotation.EzyRequestController;
import com.tvd12.ezyfox.util.EzyLoggable;
import com.tvd12.ezyfoxserver.entity.EzyUser;
import com.tvd12.ezyfoxserver.support.factory.EzyResponseFactory;
import com.tvd12.gamebox.entity.MMOPlayer;
import com.tvd12.gamebox.math.Vec3;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@EzyRequestController
public class GameController extends EzyLoggable {

    private final EzyResponseFactory responseFactory;
    private final RoomService roomService;
    private final GameService gameService;
    private final ModelToResponseConverter modelToResponseConverter;

    @EzyDoHandle(Commands.PLAY)
    public void play(EzyUser user)
    {
        logger.info("User {} request play", user.getName());

        MMOPlayer player = roomService.AddUser(user);
        Vec3 initialPosition = new Vec3(500,0,500);
        Vec3 initialRotation = new Vec3(0,0,0);

        roomService.setPlayerPosition(player, initialPosition);
        EntitySpawnModel entitySpawnModel = gameService.spawnModel(user, true, initialPosition, initialRotation);

        responseFactory.newObjectResponse()
                .command(Commands.PLAY)
                .param("entityName", user.getName())
                .data(
                        modelToResponseConverter.toResponse(entitySpawnModel))
                .user(user)
                .execute();
    }
}
