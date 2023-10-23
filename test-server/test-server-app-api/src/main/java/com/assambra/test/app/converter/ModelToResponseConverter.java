package com.assambra.test.app.converter;

import com.assambra.test.app.model.EntitySpawnModel;
import com.assambra.test.app.response.EntitySpawnResponse;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import lombok.AllArgsConstructor;

@EzySingleton
@AllArgsConstructor
public class ModelToResponseConverter {
    public EntitySpawnResponse toResponse(EntitySpawnModel model)
    {
        return EntitySpawnResponse.builder()
                .entityName(model.getEntityName())
                .isLocalPlayer(model.getIsLocalPlayer())
                .position(model.getPosition())
                .rotation(model.getRotation())
                .build();
    }
}
