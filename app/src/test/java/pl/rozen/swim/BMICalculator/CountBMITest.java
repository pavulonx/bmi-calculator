package pl.rozen.swim.BMICalculator;

import org.junit.Test;

import pl.rozen.swim.BMICalculator.interfaces.CountBMI;
import pl.rozen.swim.BMICalculator.utils.CountBMIForMetrics;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by rozen on 20.03.17.
 */

public class CountBMITest {

    @Test
    public void massUnderZeroIsInvalid() {

        //GIVEN
        float testMass = -1.0f;

        //WHEN
        CountBMI countBMI = new CountBMIForMetrics();

        //THEN
        boolean actual = countBMI.isValidMass(testMass);
        assertFalse(actual);
    }

    @Test
    public void goodMassIsValid() {

        //GIVEN
        float testMass = 65f;

        //WHEN
        CountBMI countBMI = new CountBMIForMetrics();

        //THEN
        boolean actual = countBMI.isValidMass(testMass);
        assertTrue(actual);
    }

    @Test
    public void zeroMassIsInvalid() {

        //GIVEN
        float testMass = 0f;

        //WHEN
        CountBMI countBMI = new CountBMIForMetrics();

        //THEN
        boolean actual = countBMI.isValidMass(testMass);
        assertFalse(actual);
    }

    @Test
    public void heightUnderZeroIsInvalid() {

        //GIVEN
        float testHeight = -1.0f;

        //WHEN
        CountBMI countBMI = new CountBMIForMetrics();

        //THEN
        boolean actual = countBMI.isValidHeight(testHeight);
        assertFalse(actual);
    }

    @Test
    public void goodHeightIsValid() {

        //GIVEN
        float testHeight = 165f;

        //WHEN
        CountBMI countBMI = new CountBMIForMetrics();

        //THEN
        boolean actual = countBMI.isValidHeight(testHeight);
        assertTrue(actual);
    }

    @Test
    public void zeroHeightIsInvalid() {

        //GIVEN
        float testHeight = 0f;

        //WHEN
        CountBMI countBMI = new CountBMIForMetrics();

        //THEN
        boolean actual = countBMI.isValidHeight(testHeight);
        assertFalse(actual);
    }

    @Test
    public void bmiCalculationResultISGood() {

        //GIVEN
        float height = 150f;
        float mass = 70.0f;

        //WHEN
        CountBMIForMetrics countBMIForMetrics = new CountBMIForMetrics();

        //THEN
        float actual = countBMIForMetrics.countBMI(mass, height);
        double delta = 0.001;

        assertEquals(70 / (1.50 * 1.50), actual, delta);

    }


    @Test(expected = IllegalArgumentException.class)
    public void throwsProperExceptionWhenHeightIsInvalid() {

        //GIVEN
        float height = 0f;
        float mass = 105f;

        //WHEN
        CountBMIForMetrics countBMIForMetrics = new CountBMIForMetrics();

        //THEN
        countBMIForMetrics.countBMI(mass, height);

    }


    @Test(expected = IllegalArgumentException.class)
    public void throwsProperExceptionWhenMassIsInvalid() {

        //GIVEN
        float height = 150f;
        float mass = 0;

        //WHEN
        CountBMIForMetrics countBMIForMetrics = new CountBMIForMetrics();

        //THEN
        countBMIForMetrics.countBMI(mass, height);

    }


}
