package pl.rozen.swim.BMICalculator.interfaces;

/**
 * Created by rozen on 20.03.17.
 */

public interface ICountBMI {

    boolean isValidMass(float mass);

    boolean isValidHeight(float height);

    float countBMI(float mass, float height);
}
