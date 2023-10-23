package com.assambra.test.app.model;

import com.tvd12.ezyfox.entity.EzyArray;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EntitySpawnModel {
    private String entityName;
    private Boolean isLocalPlayer;
    private EzyArray position;
    private EzyArray rotation;
}
