package src;

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
    private char displayUnit;
    private char[] validUnits = new char[2];
    private boolean hasFever;
    private boolean isCelsius;
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
        this.hasFever = false;
        this.isCelsius = false;
        this.isTempReadingSuccessful = false;
        this.validUnits[0] = 'C';
        this.validUnits[1] = 'F';
        this.timeToRun = 30000;
        this.thisThread = Thread.currentThread();
        this.display = new MockDisplay();
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


    public static void main(String[] args) {
        Sensor sensor = new Sensor();

        Thermometer thermometer = new Thermometer();
        thermometer.run(37.9f);

    }

    public void run(Float userTemp) {

        ((MockDisplay.SimulatedButtonSet) display.buttonSet).simulateButtonPress(1);
        if (display.getPowerButtonState()) {
            powerOn();
        }

        //boolean state = display.getPowerButtonState();

        CountdownTimer countdownTimer = new CountdownTimer(30000);
        countdownTimer.start();
        boolean finished = false;
        String errorMessage = null;
        boolean hasFever = false;
        while (true) {
            finished = countdownTimer.isFinished();
            //call Sensor class and get recorded temperature

            errorMessage = checkValidTemperatureRange(userTemp, true);
            hasFever = feverChecker(userTemp, true);

            /*
            switchUnit('F');
            float temp = celsiusToFahrenheit(userTemp); */

            if (finished) {
                break;
            }
        }


        if (finished) {
            if (errorMessage != null && errorMessage.equals(INVALID_TEMP_RANGE_MESSAGE)) {
                display.updateErrorMessage(errorMessage);
            } else if (userTemp.floatValue() > 0) {
                display.updateTemp(userTemp);
                display.updateFeverIndicator(hasFever);
            } else if (userTemp == null || userTemp.floatValue() == 0) {
                display.updateErrorMessage("Temperature could not be recorded");
                display.recieveAlert();
            }
        }

        System.out.println("Finished: " + finished);
        ((MockDisplay.SimulatedButtonSet) display.buttonSet).simulateButtonPress(0);
        if (display.getPowerButtonState()) {
            powerOff();
        }

        /*
        String message = checkValidTemperatureRange(userTemp, true);
        display.updateErrorMessage(message);

        boolean hasFever = feverChecker(userTemp, true);
        display.updateFeverIndicator(hasFever);

        switchUnit('F');
        double temp = celsiusToFahrenheit(userTemp); */


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
                System.out.println("Thread is running");
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


    public void switchUnit(char currentTempUnit) {
        display.updateUnit(currentTempUnit);
    }



    public static float celsiusToFahrenheit(float input) {
        return (input * 9/5) + 32;
    }

    public static float fahrenheitToCelsius(float input){
        return (input - 32) * (5/9);
    }


    public static boolean feverChecker(float input, boolean isCelsius) {
        if (isCelsius) {
            return (input >= 37.8);
        } else {
            return (input >= 99.5);
        }
    }


    public static String checkValidTemperatureRange(float input, boolean isCelsius) {
        if (isCelsius && ( input > 0 && (input < 28.0 || input > 38.0))) {
            return "Temperature reading outside of valid range. Please try again.";
        } else if (!isCelsius && ( input > 0 && (input < 88.0 || input > 108.0))) {
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


}
