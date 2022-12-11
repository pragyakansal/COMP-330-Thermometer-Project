package src;

import org.junit.*;
import static org.junit.Assert.*;


public class ThermometerTest {


    @Test
    public void testCelsiusToFahrenheit() {
        float celsius = 0.0f;
        float expectedFahrenheit = 32.0f;
        float actualFahrenheit = Thermometer.celsiusToFahrenheit(celsius);
        assertEquals(expectedFahrenheit, actualFahrenheit, 0.001);
    }

    @Test
    public void testFahrenheitToCelsius() {
        float fahrenheit = 32.0f;
        float expectedCelsius = 0.0f;
        float actualCelsius = Thermometer.fahrenheitToCelsius(fahrenheit);
        assertEquals(expectedCelsius, actualCelsius, 0.001);
    }


    @Test
    public void testFeverCheckerCelsiusTrue() {
        float tempInCelsius = 39.0f;
        boolean isCelsius = true;
        boolean expectedResult = true;
        boolean actualResult = Thermometer.feverChecker(tempInCelsius, isCelsius);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testFeverCheckerCelsiusFalse() {
        float tempInCelsius = 36.0f;
        boolean isCelsius = true;
        boolean expectedResult = false;
        boolean actualResult = Thermometer.feverChecker(tempInCelsius, isCelsius);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testFeverCheckerFahrenheitTrue() {
        float tempInFahrenheit = 100.0f;
        boolean isCelsius = false;
        boolean expectedResult = true;
        boolean actualResult = Thermometer.feverChecker(tempInFahrenheit, isCelsius);
        assertEquals(expectedResult, actualResult);
    }

    /*
    @Test
    public void testFeverCheckerFahrenheitFalse() {
        double tempInFahrenheit = 97.0;
        boolean isCelsius = false;
        boolean expectedResult = false;
        boolean actualResult = Thermometer.feverChecker(tempInFahrenheit, isCelsius);
        assertEquals(expectedResult, actualResult);
    } */


    @Test
    public void testCheckValidTemperatureRangeCelsiusLow() {
        float tempInCelsius = 20.0f;
        boolean isCelsius = true;
        String expectedResult = "Temperature reading outside of valid range. Please try again.";
        String actualResult = Thermometer.checkValidTemperatureRange(tempInCelsius, isCelsius);
        assertEquals(expectedResult, actualResult);

    }

    @Test
    public void testCheckValidTemperatureRangeCelsiusWithinRange() {
        float tempInCelsius = 35.0f;
        boolean isCelsius = true;
        String expectedResult = "Temperature reading in range.";
        String actualResult = Thermometer.checkValidTemperatureRange(tempInCelsius, isCelsius);
        assertEquals(expectedResult, actualResult);
    }


    @Test
    public void testCheckValidTemperatureRangeCelsiusHigh() {
        float tempInCelsius = 40.0f;
        boolean isCelsius = true;
        String expectedResult = "Temperature reading outside of valid range. Please try again.";
        String actualResult = Thermometer.checkValidTemperatureRange(tempInCelsius, isCelsius);
        assertEquals(expectedResult, actualResult);

    }

    @Test
    public void testCheckValidTemperatureRangeFahrenheitLow() {
        float tempInFahrenheit = 85.0f;
        boolean isCelsius = false;
        String expectedResult = "Temperature reading outside of valid range. Please try again.";
        String actualResult = Thermometer.checkValidTemperatureRange(tempInFahrenheit, isCelsius);
        assertEquals(expectedResult, actualResult);

    }

    @Test
    public void testCheckValidTemperatureRangeFahrenheitWithinRange() {
        float tempInFahrenheit = 98.0f;
        boolean isCelsius = false;
        String expectedResult = "Temperature reading in range.";
        String actualResult = Thermometer.checkValidTemperatureRange(tempInFahrenheit, isCelsius);
        assertEquals(expectedResult, actualResult);

    }

    @Test
    public void testCheckValidTemperatureRangeFahrenheitHigh() {
        float tempInFahrenheit = 111.0f;
        boolean isCelsius = false;
        String expectedResult = "Temperature reading outside of valid range. Please try again.";
        String actualResult = Thermometer.checkValidTemperatureRange(tempInFahrenheit, isCelsius);
        assertEquals(expectedResult, actualResult);

    }

    @Test
    public void testPowerOn() {

    }

    @Test
    public void testPowerOff() {

    }


    /*
    @Test
    public void testSwitchUnit() {
        char currentTempUnit = 'F';
        char expectedResult = 'C';
        char[] tempUnits = {'C', 'F'};
        char actualResult = Thermometer.switchUnit(tempUnits);
        assertEquals(expectedResult, actualResult);

    } */





}
