package src;

import java.awt.*;
import java.util.Random;

public class MockDisplay extends Display {

    private static class ConsoleScreen implements Screen, SoundChip {

        private final String FORMAT_STRING = "Battery: [%s] Temp: [%s][%s] Errors: [%s] Fever: [%s]";

        public void initScreen(DisplayArea[] displayAreas) {
            String outString = String.format(this.FORMAT_STRING,
                    displayAreas[0].getText(),
                    displayAreas[1].getText(),
                    displayAreas[2].getText(),
                    displayAreas[3].getText(),
                    displayAreas[4].getText()
            );
            System.out.print(" ".repeat(outString.length()));
        }

        public void updateScreen(DisplayArea[] displayAreas) {
            String outString = String.format(this.FORMAT_STRING,
                    displayAreas[0].getText(),
                    displayAreas[1].getText(),
                    displayAreas[2].getText(),
                    displayAreas[3].getText(),
                    displayAreas[4].getText()
            );
            System.out.print("\b".repeat(outString.length()));
            System.out.print(outString);
        }

        public void beep() {
            Toolkit.getDefaultToolkit().beep();
        }
    }

    private static class SimulatedButtonSet implements ButtonSet {
        private final boolean[] buttonStates;

        public SimulatedButtonSet(int buttonCount) {
            this.buttonStates = new boolean[buttonCount];
        }

        public boolean getButtonState(int n) {
            return this.buttonStates[n];
        }

        public void simulateButtonPress(int n) {
            this.buttonStates[n] = true;
        }

        public void simulateButtonRelease(int n) {
            this.buttonStates[n] = false;
        }
    }

    public MockDisplay() {
        super();
        this.screen = new ConsoleScreen();
        this.screen.initScreen(this.screenDisplayAreas);
        this.soundChip = (SoundChip) this.screen;
        this.buttonSet = new SimulatedButtonSet(this.DEFAULT_BUTTON_COUNT);
    }

    public MockDisplay(float batteryLevel, int[] displayAreaSizes) {
        super(batteryLevel, displayAreaSizes);
        this.screen = new ConsoleScreen();
        this.screen.initScreen(this.screenDisplayAreas);
        this.soundChip = (SoundChip) this.screen;
        this.buttonSet = new SimulatedButtonSet(this.DEFAULT_BUTTON_COUNT);
    }

    public static void main(String[] args) throws InterruptedException {
        MockDisplay display = new MockDisplay();

        Random random = new Random();
        float temp = 70;
        char[] units = {'F', 'C'};
        char unit;
        String errorMessage = "Temp not read";
        boolean hasFever = false;

        while (true) {
            if (display.getBatteryLevel() <= 0) {
                ((SimulatedButtonSet) display.buttonSet).simulateButtonPress(0);
                if (display.getPowerButtonState()) {
                    System.exit(0);
                }
            }
            display.incrementBatteryLevel(-0.1f);
            display.updateBatteryLevel();

            temp += random.nextFloat(-1.0f, 5.0f);
            display.updateTemp(temp);
            unit = units[random.nextInt(2)];
            display.updateUnit(unit);
            hasFever = !hasFever;
            display.updateFeverIndicator(hasFever);
            if (temp > 90) {
                display.updateErrorMessage(errorMessage);
            } else {
                display.updateErrorMessage("");
            }
            display.recieveAlert();
            Thread.sleep(1000);
        }
    }
}
