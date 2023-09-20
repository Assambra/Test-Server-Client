package com.assambra.app.service;

import com.assambra.app.model.EntitySpawnModel;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.ezyfox.util.EzyLoggable;
import com.tvd12.ezyfoxserver.entity.EzyUser;
import com.tvd12.gamebox.math.Vec3;
import lombok.Setter;

@Setter
@EzySingleton
public class GameService extends EzyLoggable {

    public EntitySpawnModel spawnModel(EzyUser user, Boolean isLocalPlayer, Vec3 position, Vec3 rotation)
    {

        return EntitySpawnModel.builder()
                .entityName(user.getName())
                .isLocalPlayer(isLocalPlayer)
                .position(position.toArray())
                .rotation(rotation.toArray())
                .build();
    }
}
