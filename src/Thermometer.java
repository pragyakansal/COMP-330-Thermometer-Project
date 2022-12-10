public class Thermometer {
    public float input;
    public String output;
    public Display display;
    public Sensor sensor;
    public boolean isOn;
    public int maxTempReadingTime;
    public double minTemp;
    public double maxTemp;
    public char displayUnit;
    public char[] validUnits;
    public boolean hasFever;
    public boolean isCelsius;
    public boolean isTempReadingSuccessful;

    public Thermometer() {
        this.input = 0;
        this.output = "";
        this.display = new Display();
        this.sensor = new Sensor();
        this.isOn = false;
        this.maxTempReadingTime = 0;
        this.minTemp = 0.0;
        this.maxTemp = 0.0;
        this.hasFever = false;
        this.isCelsius = false;
        this.isTempReadingSuccessful = false;
    }


    public void powerOn(boolean isOn) {
        this.isOn = true;
    }

    public void powerOff(boolean isOn) {
        this.isOn = false;
    }

    /* public double fahrenheitToCelsius(float input) {
        return (input * 9/5) + 32;
    } */


    public static double celsiusToFahrenheit(double input) {
        return (input * 9/5) + 32;
    }

    public static double fahrenheitToCelsius(double input){
        return (input - 32) * (5/9);
    }


    public static boolean feverChecker(double input, boolean isCelsius) {
        if (isCelsius) {
            return (input > 37.8);
        } else {
            return (input > 99.5);
        }
    }


    public static String checkValidTemperatureRange(double input, boolean isCelsius) {
        if (isCelsius && (input < 32.0 || input > 38.0)) {
            return "Temperature reading outside of valid range. Please try again.";
        } else if (!isCelsius && (input < 88.0 || input > 108.0)) {
            return "Temperature reading outside of valid range. Please try again.";
        } else {
            return "Temperature reading in range.";
        }
    }


    /* public boolean makeBeepingSound(boolean isTempReadingSuccessful) {
        if (isTempReadingSuccessful) {

        }
    } */



   /* public String sendError(float input, int maxTempReadingTime) {
        if (maxTempReadingTime == 60) {
            return "Temperature could not be read successfully. Please try again.";
        }
    } */

    public void setInput(float input) {
        this.input = input;
    }

    public float getInput() {
        return this.input;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public String getOutput() {
        return this.output;
    }

    public void setDisplayUnit(char displayUnit) {
        this.displayUnit = displayUnit;
    }

    public char getDisplayUnit() {
        return this.displayUnit;
    }

    public void setMaxTempReadingTime(int maxTempReadingTime){
        this.maxTempReadingTime = maxTempReadingTime;
    }

    public int getMaxTempReadingTime() {
        return this.getMaxTempReadingTime();
    }

    public void setMinTemp(double minTemp) {
        this.minTemp = minTemp;
    }

    public double getMinTemp() {
        return this.minTemp;
    }

    public void setMaxTemp(double maxTemp) {
        this.maxTemp = maxTemp;
    }

    public double getMaxTemp() {
        return this.maxTemp;
    }

    public void setDisplay(Display display) {
        this.display = display;
    }

    public Display getDisplay() {
        return this.display;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }

    public Sensor getSensor() {
        return this.sensor;
    }


}
