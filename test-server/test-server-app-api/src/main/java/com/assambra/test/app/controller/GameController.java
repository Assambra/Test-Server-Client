package com.assambra.test.app.controller;

import com.assambra.test.app.constant.Commands;
import com.assambra.test.app.converter.ModelToResponseConverter;
import com.assambra.test.app.model.EntitySpawnModel;
import com.assambra.test.app.service.GameService;
import com.assambra.test.app.service.RoomService;
import com.tvd12.ezyfox.core.annotation.EzyDoHandle;
import com.tvd12.ezyfox.core.annotation.EzyRequestController;
import com.tvd12.ezyfox.util.EzyLoggable;
import com.tvd12.ezyfoxserver.entity.EzyUser;
import com.tvd12.ezyfoxserver.support.factory.EzyResponseFactory;
import com.tvd12.gamebox.entity.MMOPlayer;
import com.tvd12.gamebox.math.Vec3;
import lombok.AllArgsConstructor;

import java.util.List;

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

        responseFactory.newObjectResponse()
                .command(Commands.PLAY)
                .user(user)
                .execute();
        
        EntitySpawnModel localPlayerSpawnModel = gameService.spawnModel(user.getName(), true, initialPosition, initialRotation);
        responseFactory.newObjectResponse()
                .command(Commands.ENTITY_SPAWN)
                .param("entityName", user.getName())
                .data(
                        modelToResponseConverter.toResponse(localPlayerSpawnModel))
                .user(user)
                .execute();

        List<String> nearByPlayers = player.getNearbyPlayerNames();
        nearByPlayers.remove(user.getName());

        EntitySpawnModel playerSpawnModel = gameService.spawnModel(user.getName(), false, initialPosition, initialRotation);
        for (String playerName : nearByPlayers)
        {
            responseFactory.newObjectResponse()
                    .command(Commands.ENTITY_SPAWN)
                    .param("entityName", user.getName())
                    .data(
                            modelToResponseConverter.toResponse(playerSpawnModel))
                    .usernames(nearByPlayers)
                    .execute();
        }

        for(String playerName : nearByPlayers)
        {
            logger.info("PlayerName: {}", playerName);
            EntitySpawnModel otherPlayerSpawnModel = gameService.spawnModel(playerName, false, initialPosition, initialRotation);
            responseFactory.newObjectResponse()
                    .command(Commands.ENTITY_SPAWN)
                    .param("entityName", playerName)
                    .data(
                            modelToResponseConverter.toResponse(otherPlayerSpawnModel))
                    .user(user)
                    .execute();
        }
    }
}
