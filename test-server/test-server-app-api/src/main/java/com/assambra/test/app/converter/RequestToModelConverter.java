package com.assambra.test.app.converter;

import com.assambra.test.app.model.PlayerInputModel;
import com.assambra.test.app.request.PlayerInputRequest;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import lombok.AllArgsConstructor;

@EzySingleton
@AllArgsConstructor
public class RequestToModelConverter {

    public PlayerInputModel toModel(PlayerInputRequest request) {
        return PlayerInputModel.builder()
                .time(request.getT())
                .inputs(request.getI())
                .rotation(request.getR())
                .build();
    }
}
