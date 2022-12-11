package src;

import java.awt.*;
import java.util.Arrays;

public class Display {

    final int DEFAULT_SCREEN_DISPLAY_AREAS_COUNT = 5;
    final int DEFAULT_BATTERY_DISPLAY_AREA_INDEX = 0;
    final int DEFAULT_TEMP_DISPLAY_AREA_INDEX = 1;
    final int DEFAULT_UNIT_DISPLAY_AREA_INDEX = 2;
    final int DEFAULT_ERRORS_DISPLAY_AREA_INDEX = 3;
    final int DEFAULT_FEVER_DISPLAY_AREA_INDEX = 4;
    final int DEFAULT_BATTERY_DISPLAY_AREA_SIZE = 4;
    final int DEFAULT_TEMP_DISPLAY_AREA_SIZE = 5;
    final int DEFAULT_UNIT_DISPLAY_AREA_SIZE = 2;
    final int DEFAULT_ERRORS_DISPLAY_AREA_SIZE = 32;
    final int DEFAULT_FEVER_DISPLAY_AREA_SIZE = 1;
    final int DEFAULT_BUTTON_COUNT = 2;
    final int DEFAULT_POWER_BUTTON_INDEX = 0;
    final int DEFAULT_DATA_BUTTON_INDEX = 1;
    final float BATTERY_LEVEL_MIN = 0.0f;
    final float BATTERY_LEVEL_MAX = 1.0f;
    int batteryDisplayAreaIndex;
    int tempDisplayAreaIndex;
    int unitDisplayAreaIndex;
    int errorsDisplayAreaIndex;
    int feverDisplayAreaIndex;
    int powerButtonIndex;
    int dataButtonIndex;
    float batteryLevel;
    DisplayArea[] screenDisplayAreas;
    Screen screen;
    SoundChip soundChip;
    ButtonSet buttonSet;
    static class DisplayArea {
        private final char FILL_CHAR = ' ';
        private final char[] currentText;
        private final int size;

        public DisplayArea() {
            this.size = 16;
            this.currentText = new char[this.size];
            Arrays.fill(this.currentText, this.FILL_CHAR);
        }

        public DisplayArea(int size) {
            this.size = size;
            this.currentText = new char[this.size];
            Arrays.fill(this.currentText, this.FILL_CHAR);
        }

        public DisplayArea(String currentText, int size) {
            this.size = size;
            this.currentText = new char[this.size];
            for (int i = 0; i < this.currentText.length; i++) {
                this.currentText[i] = (i < currentText.length()) ? currentText.charAt(i) : this.FILL_CHAR;
            }
        }

        public void setText(String text) {
            for (int i = 0; i < this.currentText.length; i++) {
                this.currentText[i] = (i < text.length()) ? text.charAt(i) : this.FILL_CHAR;
            }
        }

        public String getText() {
            return new String(this.currentText);
        }
    }

    interface Screen {
        public void initScreen(DisplayArea[] displayAreas);
        public void updateScreen(DisplayArea[] displayAreas);
    }

    interface SoundChip {
        public void beep();
    }

    interface ButtonSet {
        public boolean getButtonState(int n);
    }

    public Display(){
        this.batteryLevel = 1.0f;
        this.screenDisplayAreas = new DisplayArea[this.DEFAULT_SCREEN_DISPLAY_AREAS_COUNT];
        this.batteryDisplayAreaIndex = this.DEFAULT_BATTERY_DISPLAY_AREA_INDEX;
        this.tempDisplayAreaIndex = this.DEFAULT_TEMP_DISPLAY_AREA_INDEX;
        this.unitDisplayAreaIndex = this.DEFAULT_UNIT_DISPLAY_AREA_INDEX;
        this.errorsDisplayAreaIndex = this.DEFAULT_ERRORS_DISPLAY_AREA_INDEX;
        this.feverDisplayAreaIndex = this.DEFAULT_FEVER_DISPLAY_AREA_INDEX;
        this.screenDisplayAreas[this.batteryDisplayAreaIndex] = new DisplayArea(this.DEFAULT_BATTERY_DISPLAY_AREA_SIZE);
        this.screenDisplayAreas[this.tempDisplayAreaIndex] = new DisplayArea(this.DEFAULT_TEMP_DISPLAY_AREA_SIZE);
        this.screenDisplayAreas[this.unitDisplayAreaIndex] = new DisplayArea(this.DEFAULT_UNIT_DISPLAY_AREA_SIZE);
        this.screenDisplayAreas[this.errorsDisplayAreaIndex] = new DisplayArea(this.DEFAULT_ERRORS_DISPLAY_AREA_SIZE);
        this.screenDisplayAreas[this.feverDisplayAreaIndex] = new DisplayArea(this.DEFAULT_FEVER_DISPLAY_AREA_SIZE);
        this.powerButtonIndex = this.DEFAULT_POWER_BUTTON_INDEX;
        this.dataButtonIndex = this.DEFAULT_DATA_BUTTON_INDEX;
    }

    public Display(float batteryLevel, int[] displayAreaSizes) {
        this.batteryLevel = batteryLevel;
        this.batteryDisplayAreaIndex = this.DEFAULT_BATTERY_DISPLAY_AREA_INDEX;
        this.tempDisplayAreaIndex = this.DEFAULT_TEMP_DISPLAY_AREA_INDEX;
        this.unitDisplayAreaIndex = this.DEFAULT_UNIT_DISPLAY_AREA_INDEX;
        this.errorsDisplayAreaIndex = this.DEFAULT_ERRORS_DISPLAY_AREA_INDEX;
        this.feverDisplayAreaIndex = this.DEFAULT_FEVER_DISPLAY_AREA_INDEX;
        this.screenDisplayAreas = new DisplayArea[displayAreaSizes.length];
        for (int i = 0; i < displayAreaSizes.length; i++) {
            this.screenDisplayAreas[i] = new DisplayArea(displayAreaSizes[i]);
        }
        this.powerButtonIndex = this.DEFAULT_POWER_BUTTON_INDEX;
        this.dataButtonIndex = this.DEFAULT_DATA_BUTTON_INDEX;
    }

    public void updateBatteryLevel() {
        String formattedOutput = (int)(this.batteryLevel * 100) + "%";
        this.screenDisplayAreas[this.batteryDisplayAreaIndex].setText(formattedOutput);
        this.screen.updateScreen(this.screenDisplayAreas);
    }

    public void updateTemp(float temp) {
        String formattedOutput = String.format("%.2f", temp);
        this.screenDisplayAreas[this.tempDisplayAreaIndex].setText(formattedOutput);
        this.screen.updateScreen(this.screenDisplayAreas);
    }

    public void updateUnit(char unit) {
        String formattedOutput = unit + "°";
        this.screenDisplayAreas[this.unitDisplayAreaIndex].setText(formattedOutput);
        this.screen.updateScreen(this.screenDisplayAreas);
    }

    public void updateErrorMessage(String errorMessage) {
        this.screenDisplayAreas[this.errorsDisplayAreaIndex].setText(errorMessage);
        this.screen.updateScreen(this.screenDisplayAreas);
    }

    public void updateFeverIndicator(boolean hasFever) {
        String formattedOutput = (hasFever) ? "X" : " ";
        this.screenDisplayAreas[this.feverDisplayAreaIndex].setText(formattedOutput);
        this.screen.updateScreen(this.screenDisplayAreas);
    }

    public void incrementBatteryLevel(float increment) {
        this.batteryLevel += increment;
        this.batteryLevel = Float.min(Float.max(this.BATTERY_LEVEL_MIN, this.batteryLevel), this.BATTERY_LEVEL_MAX);
    }

    public float getBatteryLevel() {
        return this.batteryLevel;
    }

    public void recieveAlert() {
        Toolkit.getDefaultToolkit().beep();
    }

    public boolean getPowerButtonState() {
        return this.buttonSet.getButtonState(this.powerButtonIndex);
    }

    public boolean getDataButtonState() {
        return this.buttonSet.getButtonState(this.dataButtonIndex);
    }

}
