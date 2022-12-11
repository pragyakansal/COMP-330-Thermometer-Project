

package src;

import java.util.Arrays;
import java.util.Random;


public class Thermometer {
    private float input;
    private String output;
    private Display display;
    private Sensor sensor;
    private boolean isOn;
    private int maxTempReadingTime;
    private double minTemp;
    private double maxTemp;
    private int displayUnitIndex;
    private char[] validUnits = new char[2];
    private boolean isTempReadingSuccessful;
    private int timeToRun;
    private Thread thisThread;
    public static final String INVALID_TEMP_RANGE_MESSAGE = "Temperature reading outside of valid range. Please try again.";

    public Thermometer() {
        this.input = 0;
        this.output = "";
        this.sensor = new Sensor();
        this.isOn = false;
        this.maxTempReadingTime = 0;
        this.minTemp = 0.0;
        this.maxTemp = 0.0;
        this.isTempReadingSuccessful = false;
        this.validUnits[0] = 'C';
        this.validUnits[1] = 'F';
        this.timeToRun = 30000;
        this.thisThread = Thread.currentThread();
        this.display = new MockDisplay();
        this.displayUnitIndex = 0;
    }

    // setters and getters
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

    public void setDisplayUnitIndex(int displayUnit) {
        this.displayUnitIndex = displayUnitIndex;

    }

    public int getDisplayUnitIndex() {
        return this.displayUnitIndex;

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

    public static void main(String[] args) {
        Sensor sensor = new Sensor();
        Thermometer thermometer = new Thermometer();
        try {

            int unitIndex = thermometer.switchUnit(0);
            thermometer.run(20.1f, true, getRandomFloatNumber(1.0f, 5.0f), unitIndex);
            /* unit = thermometer.switchUnit('C');
            thermometer.run(37.9f, true, getRandomFloatNumber(1.0f, 5.0f), unit);
            unit = thermometer.switchUnit('C');
            thermometer.run(40.5f, true, getRandomFloatNumber(1.0f, 5.0f), unit);
            char unit = thermometer.switchUnit('F');
            thermometer.run(98.4f, false, getRandomFloatNumber(1.0f, 5.0f), unit);
            unit = thermometer.switchUnit('F');
            thermometer.run(100.5f, false, getRandomFloatNumber(1.0f, 5.0f), unit);
            unit = thermometer.switchUnit('F');
            thermometer.run(130.5f, false, getRandomFloatNumber(1.0f, 5.0f), unit); */
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void run() throws InterruptedException {
        // run until the user hits the power button or the display runs out of battery
        while (!display.getPowerButtonState() && display.getBatteryLevel() > 0f) {
            CountdownTimer timer = new CountdownTimer(30000);
            timer.start();
            while (!timer.isFinished()) {
                try {
                    float temp = this.sensor.getTemp();
                    char unit = (this.display.getDataButtonState()) ?
                            this.validUnits[(this.displayUnitIndex + 1) % this.validUnits.length] :
                            this.validUnits[this.displayUnitIndex];
                    String errorMessage = checkValidTemperatureRange(temp, true);
                    boolean hasFever = feverChecker(temp, true);
                    this.display.updateTemp(temp);
                    this.display.updateUnit(unit);
                    this.display.updateErrorMessage(errorMessage);
                    this.display.updateFeverIndicator(hasFever);
                } catch (Exception e) {
                    String errorMessage = e.getMessage();
                    this.display.updateErrorMessage(errorMessage);
                }
                Thread.sleep(1000);
            }
        }
    }

    public void run(Float userTemp, boolean isCelsius, float batteryLevel, int tempUnitIndex) throws InterruptedException {
        ((MockDisplay.SimulatedButtonSet) display.buttonSet).simulateButtonPress(1);
        if (display.getPowerButtonState()) {
            powerOn();
        }
        CountdownTimer countdownTimer = new CountdownTimer(30000);
        countdownTimer.start();
        boolean finished = false;
        String errorMessage = null;
        boolean hasFever = false;
        while (true) {
            hasFever = false;
            finished = countdownTimer.isFinished();
            //call Sensor class and get recorded temperature
            // Simulating the time lag to record the temperature
            int randomInt = getRandomNumber(1, 29);
            long randomTimeInMillis = randomInt * 1000;
            System.out.println ("Waiting for: " + randomTimeInMillis + " ms to record the temperature");
            display.updateBatteryLevel();
            display.incrementBatteryLevel(-0.1f);
            Thread.sleep(randomTimeInMillis);
            errorMessage = checkValidTemperatureRange(userTemp, isCelsius);
            hasFever = feverChecker(userTemp, isCelsius);

            if (errorMessage != null && errorMessage.equals(INVALID_TEMP_RANGE_MESSAGE)) {
                display.updateTemp(userTemp);
                display.updateErrorMessage(errorMessage);
                this.isTempReadingSuccessful = false;
                break;
            } else if (userTemp.floatValue() > 0.00) {
                display.updateTemp(userTemp);
                System.out.println("\n");
                if (hasFever) {
                    display.updateFeverIndicator(hasFever);
                }
                this.isTempReadingSuccessful = true;
                break;
            }
            if (finished) {
                break;
            }
        }

        if (finished) {
            if (userTemp == null || userTemp.floatValue() == 0) {
                display.updateErrorMessage("Temperature could not be recorded");
                this.isTempReadingSuccessful = false;
                display.recieveAlert();
            }
        }
        // System.out.println("Finished: " + finished);
        ((MockDisplay.SimulatedButtonSet) display.buttonSet).simulateButtonPress(0);
        if (display.getPowerButtonState()) {
            powerOff();
        }
    }
    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public static float getRandomFloatNumber(float min, float max) {
        return (int) ((Math.random() * (max - min)) + min);
    }


    public class CountdownTimerThread extends Thread {
        private long time;
        /** Create a new timer */
        public CountdownTimerThread(long time) {
            this.time = time;
        }

        /**
         * Start a countdown
         *      Period to count down given in milliseconds
         */
        public void run() {
            try {
                sleep(this.time);
            } catch (InterruptedException e) {
            }
        }
    }

    public class CountdownTimer extends Thread {
        /* Countdown timer */
        private CountdownTimerThread timer;
        /**
         * Creates a new timer and sets time to count down
         * @param time
         *          Time to count down
         */

        public CountdownTimer(long time) {
            this.timer = new CountdownTimerThread(time);
        }
        public void run() {
            this.timer.start();
        }
        /**
         * @return
         *      False if timer is running, else true
         */
        public boolean isFinished() {
            if(this.timer.getState() == Thread.State.TERMINATED) {
                return true;
            }
            return false;
        }
    }

    public boolean powerOn() {
        if (this.isOn) {
            return true;
        }

        display.recieveAlert();
        this.isOn = true;
        display.updateTemp(0.0f);
        return this.isOn;
    }

    public boolean powerOff() {
        if (!this.isOn) {
            return false;
        }
        this.isOn = false;
        //display.updateTemp(null);
        display.recieveAlert();
        Display.DisplayArea displayArea = new Display.DisplayArea();
        displayArea.setText(" ");
        return this.isOn;
    }

    public int switchUnit(int tempUnitIndex) {
        if (tempUnitIndex == this.displayUnitIndex) {
            display.updateUnit(this.validUnits[tempUnitIndex]);
            return tempUnitIndex;
        }
        this.displayUnitIndex = tempUnitIndex;
        display.updateUnit(this.validUnits[this.displayUnitIndex]);
        return tempUnitIndex;
    }

    public static float celsiusToFahrenheit(float input) {
        return (input * 9/5) + 32;
    }

    public static float fahrenheitToCelsius(float input){
        return (input - 32) * (5/9);
    }

    public static boolean feverChecker(float input, boolean isCelsius) {
        if (input <= 0f || input <= 0.0f || input <= 0.00f) {
            return false;
        }
        if (isCelsius) {
            return (input >= 37.8f);
        } else {
            return (input >= 99.5f);
        }
    }

    public static String checkValidTemperatureRange(float input, boolean isCelsius) {
        if (isCelsius && ((input > 0f || input > 0.0f || input > 0.00f) && (input < 28.0f || input > 38.0f))) {
            return "Temperature reading outside of valid range. Please try again.";
        } else if (!isCelsius && ((input > 0f || input > 0.0f || input > 0.00f) && (input < 88.0f || input > 108.0f))) {
            return "Temperature reading outside of valid range. Please try again.";
        } else {
            return "Temperature reading in range.";
        }
    }

}




