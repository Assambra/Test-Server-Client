package com.assambra.app.controller;

import com.assambra.app.constant.Commands;
import com.tvd12.ezyfox.core.annotation.EzyDoHandle;
import com.tvd12.ezyfox.core.annotation.EzyRequestController;
import com.tvd12.ezyfox.util.EzyLoggable;
import com.tvd12.ezyfoxserver.entity.EzyUser;
import com.tvd12.ezyfoxserver.support.factory.EzyResponseFactory;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@EzyRequestController
public class GameController extends EzyLoggable {

    private final EzyResponseFactory responseFactory;

    @EzyDoHandle(Commands.PLAY)
    public void play(EzyUser user)
    {
        logger.info("User {} request play", user.getName());

        responseFactory.newObjectResponse()
                .command(Commands.PLAY)
                .user(user)
                .execute();
    }
}
