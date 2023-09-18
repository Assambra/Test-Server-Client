package com.assambra.plugin.controller;

import com.assambra.common.entity.User;
import com.assambra.plugin.service.UserService;
import com.assambra.plugin.service.WelcomeService;

import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.ezyfox.core.annotation.EzyEventHandler;
import com.tvd12.ezyfox.security.EzySHA256;
import com.tvd12.ezyfoxserver.constant.EzyLoginError;
import com.tvd12.ezyfoxserver.context.EzyPluginContext;
import com.tvd12.ezyfoxserver.controller.EzyAbstractPluginEventController;
import com.tvd12.ezyfoxserver.event.EzyUserLoginEvent;
import com.tvd12.ezyfoxserver.exception.EzyLoginErrorException;
import lombok.AllArgsConstructor;

import static com.tvd12.ezyfoxserver.constant.EzyEventNames.USER_LOGIN;

@EzySingleton
@EzyEventHandler(USER_LOGIN)
@AllArgsConstructor
public class UserLoginController extends EzyAbstractPluginEventController<EzyUserLoginEvent> {

    private final UserService userService;

    @Override
    public void handle(EzyPluginContext ctx, EzyUserLoginEvent event) {
        logger.info("{} login in", event.getUsername());

        String username = event.getUsername();
        String password = encodePassword(event.getPassword());

        User user = userService.getUser(username);

        if (user == null) {
            logger.info("User doesn't exist in db, create a new one!");
            user = userService.createUser(username, password);
            userService.saveUser(user);
        }

        if (!user.getPassword().equals(password)) {
            throw new EzyLoginErrorException(EzyLoginError.INVALID_PASSWORD);
        }

        logger.info("user and password match, accept user: {}", username);
    }

    private String encodePassword(String password) {
        return EzySHA256.cryptUtfToLowercase(password);
    }
}
