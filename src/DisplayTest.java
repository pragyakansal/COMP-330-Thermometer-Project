package src;

import org.junit.*;
import static org.junit.Assert.*;

public class DisplayTest {

    float INITIAL_BATTERY_LEVEL = 1;
    int[] TEST_DISPLAY_AREA_SIZES = {0, 1, 2, 64};
    String DUMMY_DISPLAY_TEXT = "1fKpTCo015CeSXMdtL8u; yc9MRec8928kSA6CnJhy; seTwtlHAT2Z4OE4RpY31";
    float[] TEMPS = {-123f, -99f, -1f, 0f, 0.5f, 1f, 50f, 50.1f, 100f, 100.5f};
    float[] BATTERY_LEVEL_INCREMENTS = {-0.1f, -0.15f, -0.5f, 0f, 1f, 64f};
    char[] UNITS = {'C', 'F'};
    boolean[] FEVER_INDICATOR_STATES = {true, false};
    Display display;

    @Before
    public void setUp() {
        this.display = new MockDisplay();
    }

    @Test
    public void testDisplayAreaSetText() {
        this.display = new Display(INITIAL_BATTERY_LEVEL, TEST_DISPLAY_AREA_SIZES);
        for (int i = 0; i < this.TEST_DISPLAY_AREA_SIZES.length; i++) {
            this.display.screenDisplayAreas[i].setText(DUMMY_DISPLAY_TEXT);
            assertEquals(this.DUMMY_DISPLAY_TEXT.substring(0, this.TEST_DISPLAY_AREA_SIZES[i]),
                    this.display.screenDisplayAreas[i].getText());
        }
    }

    @Test
    public void testUpdateBatteryLevel() {
        for (float i : this.BATTERY_LEVEL_INCREMENTS) {
            float initialBatteryLevel = this.display.getBatteryLevel();
            this.display.incrementBatteryLevel(i);
            this.display.updateBatteryLevel();
            String expectedBatteryLevel = String.format("%.0f%%", Math.min(
                    Math.max(i + initialBatteryLevel, this.display.BATTERY_LEVEL_MIN),
                    this.display.BATTERY_LEVEL_MAX) * 100);
            expectedBatteryLevel += " ".repeat(
                    this.display.DEFAULT_BATTERY_DISPLAY_AREA_SIZE - expectedBatteryLevel.length());
            assertEquals(
                    expectedBatteryLevel,
                    this.display.screenDisplayAreas[this.display.DEFAULT_BATTERY_DISPLAY_AREA_INDEX].getText()
            );
        }
    }

    @Test
    public void testUpdateTemp() {
        for (float i : this.TEMPS) {
            this.display.updateTemp(i);
            String expectedTemp = String.format("%.2f", i);
            expectedTemp += " ".repeat(this.display.DEFAULT_TEMP_DISPLAY_AREA_SIZE);
            expectedTemp = expectedTemp.substring(0, this.display.DEFAULT_TEMP_DISPLAY_AREA_SIZE);
            assertEquals(
                    expectedTemp,
                    this.display.screenDisplayAreas[this.display.DEFAULT_TEMP_DISPLAY_AREA_INDEX].getText()
            );
        }
    }

    @Test public void testUpdateUnit() {
        for (char i : this.UNITS) {
            this.display.updateUnit(i);
            String expectedTemp = String.format("%c°", i);
            assertEquals(
                    expectedTemp,
                    this.display.screenDisplayAreas[this.display.DEFAULT_UNIT_DISPLAY_AREA_INDEX].getText()
            );
        }
    }

    @Test public void testUpdateErrorMessage() {
        this.display.updateErrorMessage(this.DUMMY_DISPLAY_TEXT);
        String expectedErrorMessage = this.DUMMY_DISPLAY_TEXT;
        expectedErrorMessage += " ".repeat(this.display.DEFAULT_ERRORS_DISPLAY_AREA_SIZE);
        expectedErrorMessage = expectedErrorMessage.substring(0, this.display.DEFAULT_ERRORS_DISPLAY_AREA_SIZE);
        assertEquals(
                expectedErrorMessage,
                this.display.screenDisplayAreas[this.display.DEFAULT_ERRORS_DISPLAY_AREA_INDEX].getText()
        );
    }

    @Test
    public void testUpdateFeverIndicator() {
        for (boolean i : this.FEVER_INDICATOR_STATES) {
            this.display.updateFeverIndicator(i);
            String expectedIndicator = (i) ? "X" : " ";
            assertEquals(
                    expectedIndicator,
                    this.display.screenDisplayAreas[this.display.DEFAULT_FEVER_DISPLAY_AREA_INDEX].getText()
            );
        }
    }

    @Test
    public void testIncrementBatteryLevel() {
        for (float i : this.BATTERY_LEVEL_INCREMENTS) {
            float initialBatteryLevel = this.display.getBatteryLevel();
            float expectedBatteryLevel = Math.min(
                    Math.max(initialBatteryLevel + i, this.display.BATTERY_LEVEL_MIN),
                    this.display.BATTERY_LEVEL_MAX);
            this.display.incrementBatteryLevel(i);
            assertEquals(expectedBatteryLevel, this.display.getBatteryLevel(), 0);
        }
    }

    @Test
    public void testGetPowerButtonState() {
        assertFalse(this.display.getPowerButtonState());
        ((MockDisplay.SimulatedButtonSet) this.display.buttonSet).simulateButtonPress(0);
        assertTrue(this.display.getPowerButtonState());
        ((MockDisplay.SimulatedButtonSet) this.display.buttonSet).simulateButtonRelease(0);
        assertFalse(this.display.getPowerButtonState());
    }

    @Test
    public void testGetDataButtonState() {
        assertFalse(this.display.getDataButtonState());
        ((MockDisplay.SimulatedButtonSet) this.display.buttonSet).simulateButtonPress(1);
        assertTrue(this.display.getDataButtonState());
        ((MockDisplay.SimulatedButtonSet) this.display.buttonSet).simulateButtonRelease(1);
        assertFalse(this.display.getDataButtonState());
    }

}
