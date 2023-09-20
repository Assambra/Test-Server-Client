package com.assambra.app.response;

import com.tvd12.ezyfox.binding.annotation.EzyObjectBinding;
import com.tvd12.ezyfox.entity.EzyArray;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@EzyObjectBinding
public class EntitySpawnResponse {
    private String entityName;
    private Boolean isLocalPlayer;
    private EzyArray position;
    private EzyArray rotation;
}
