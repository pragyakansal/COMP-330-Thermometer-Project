import org.junit.*;
import static org.junit.Assert.*;


public class ThermometerTest {

    @Test
    public void Thermometer() {
        Thermometer thermometer = new Thermometer();
        assert(thermometer.isOn == false);
    }

    @Test
    public void testCelsiusToFahrenheit() {
        double celsius = 0.0;
        double expectedFahrenheit = 32.0;
        double actualFahrenheit = Thermometer.celsiusToFahrenheit(celsius);
        assertEquals(expectedFahrenheit, actualFahrenheit, 0.001);
    }

    @Test
    public void testFahrenheitToCelsius() {
        double fahrenheit = 32.0;
        double expectedCelsius = 0.0;
        double actualCelsius = Thermometer.fahrenheitToCelsius(fahrenheit);
        assertEquals(expectedCelsius, actualCelsius, 0.001);
    }


    @Test
    public void testFeverCheckerCelsiusTrue() {
        double tempInCelsius = 39.0;
        boolean isCelsius = true;
        boolean expectedResult = true;
        boolean actualResult = Thermometer.feverChecker(tempInCelsius, isCelsius);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testFeverCheckerCelsiusFalse() {
        double tempInCelsius = 36.0;
        boolean isCelsius = true;
        boolean expectedResult = false;
        boolean actualResult = Thermometer.feverChecker(tempInCelsius, isCelsius);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testFeverCheckerFahrenheitTrue() {
        double tempInFahrenheit = 100.0;
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
        double tempInCelsius = 30.0;
        boolean isCelsius = true;
        String expectedResult = "Temperature reading outside of valid range. Please try again.";
        String actualResult = Thermometer.checkValidTemperatureRange(tempInCelsius, isCelsius);
        assertEquals(expectedResult, actualResult);

    }

    @Test
    public void testCheckValidTemperatureRangeCelsiusWithinRange() {
        double tempInCelsius = 35.0;
        boolean isCelsius = true;
        String expectedResult = "Temperature reading in range.";
        String actualResult = Thermometer.checkValidTemperatureRange(tempInCelsius, isCelsius);
        assertEquals(expectedResult, actualResult);
    }


    @Test
    public void testCheckValidTemperatureRangeCelsiusHigh() {
        double tempInCelsius = 40.0;
        boolean isCelsius = true;
        String expectedResult = "Temperature reading outside of valid range. Please try again.";
        String actualResult = Thermometer.checkValidTemperatureRange(tempInCelsius, isCelsius);
        assertEquals(expectedResult, actualResult);

    }

    @Test
    public void testCheckValidTemperatureRangeFahrenheitLow() {
        double tempInFahrenheit = 85.0;
        boolean isCelsius = false;
        String expectedResult = "Temperature reading outside of valid range. Please try again.";
        String actualResult = Thermometer.checkValidTemperatureRange(tempInFahrenheit, isCelsius);
        assertEquals(expectedResult, actualResult);

    }

    @Test
    public void testCheckValidTemperatureRangeFahrenheitWithinRange() {
        double tempInFahrenheit = 98.0;
        boolean isCelsius = false;
        String expectedResult = "Temperature reading in range.";
        String actualResult = Thermometer.checkValidTemperatureRange(tempInFahrenheit, isCelsius);
        assertEquals(expectedResult, actualResult);

    }

    @Test
    public void testCheckValidTemperatureRangeFahrenheitHigh() {
        double tempInFahrenheit = 111.0;
        boolean isCelsius = false;
        String expectedResult = "Temperature reading outside of valid range. Please try again.";
        String actualResult = Thermometer.checkValidTemperatureRange(tempInFahrenheit, isCelsius);
        assertEquals(expectedResult, actualResult);

    }



}
