package pl.rozen.swim.BMICalculator.interfaces;

public interface CountBMI {

    boolean isValidMass(float mass);

    boolean isValidHeight(float height);

    float countBMI(float mass, float height);
}
