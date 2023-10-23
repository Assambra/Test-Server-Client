package com.assambra.test.app.utils;


import com.assambra.test.app.model.PlayerInputModel;
import com.tvd12.ezyfox.util.EzyLoggable;
import com.tvd12.gamebox.math.Vec3;
import java.lang.Math;

import org.apache.commons.math3.util.Precision;

public final class PlayerMovementUtils extends EzyLoggable {

    public static Vec3 getNextPosition(Vec3 currentPosition, PlayerInputModel model)
    {
        Vec3 movement = getMoveDirection(model.getInputs());
        movement.multiply(0.075 * 10);

        Vec3 rotation = new Vec3(model.getRotation());
        Vec3 forward = GetForwardDirection(rotation);

        forward.multiply(movement.z);
        Vec3 nextPosition = new Vec3(currentPosition);

        nextPosition.add(forward);

        return nextPosition;
    }

    public static Vec3 getNextRotation(Vec3 currentRotation, PlayerInputModel model)
    {
        Vec3 movement = getMoveDirection(model.getInputs());

        Vec3 nextRotation = new Vec3(0, movement.x, 0);
        nextRotation.multiply(0.075 * 150f);
        nextRotation.add(currentRotation);

        return nextRotation;
    }

    private static Vec3 getMoveDirection(boolean[] inputs)
    {
        Vec3 moveDirection = new Vec3();

        if(inputs[0])
            moveDirection.z += 1;
        if(inputs[1])
            moveDirection.z -= 1;
        if(inputs[2])
            moveDirection.x -= 1;
        if(inputs[3])
            moveDirection.x += 1;

        return moveDirection;
    }

    public static Vec3 GetForwardDirection(Vec3 rotation)
    {
        double xPos = Precision.round(Math.sin(Math.toRadians(rotation.y)) * Math.cos(Math.toRadians(rotation.x)), 2);
        double yPos = Precision.round(Math.sin(Math.toRadians(-rotation.x)), 2);
        double zPos = Precision.round(Math.cos(Math.toRadians(rotation.x)) * Math.cos(Math.toRadians(rotation.y)), 2);

        return new Vec3((float)xPos, (float)yPos, (float)zPos);
    }
}
