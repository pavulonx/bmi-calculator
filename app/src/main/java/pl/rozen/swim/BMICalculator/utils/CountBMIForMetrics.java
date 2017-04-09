package pl.rozen.swim.BMICalculator.utils;

import pl.rozen.swim.BMICalculator.interfaces.ICountBMI;

/**
 * Created by rozen on 20.03.17.
 */

public class CountBMIForMetrics implements ICountBMI {

    private static final float MIN_MASS = 10.0f;
    private static final float MAX_MASS = 250.0f;
    private static final float MIN_HEIGHT = 50f;
    private static final float MAX_HEIGHT = 250f;
    private static final float CM_TO_M_RATIO = 100f;

    @Override
    public boolean isValidMass(float mass) {
        return mass >= MIN_MASS && mass <= MAX_MASS;
    }

    @Override
    public boolean isValidHeight(float height) {
        return height >= MIN_HEIGHT && height <= MAX_HEIGHT;
    }

    @Override
    public float countBMI(float mass, float height) {
        if (!isValidMass(mass))
            throw new IllegalArgumentException("Wrong mass of value: " + mass);
        if (!isValidHeight(height))
            throw new IllegalArgumentException("Wrong height of value: " + height);
        else {
            height /= CM_TO_M_RATIO;
            return mass / (height * height);
        }
    }
}
