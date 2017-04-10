package pl.rozen.swim.BMICalculator.utils;


import pl.rozen.swim.BMICalculator.interfaces.CountBMI;

public class CountBMIForImperials implements CountBMI {

    private static final float MIN_MASS = 22.0f; //lbs
    private static final float MAX_MASS = 5511.5f;
    private static final float MIN_HEIGHT = 20f; //inches
    private static final float MAX_HEIGHT = 98.5f;
    private static final float FACTOR = 703.0f;

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

            return mass / (height * height) * FACTOR;
        }
    }
}
