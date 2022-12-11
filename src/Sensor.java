package src;
import java.util.Scanner;
public class Sensor {
        // Use a scanner to get input for the next temp reading
        public static float getTemp() {

            // Create a scanner object.
            Scanner input = new Scanner(System.in);

            // Display a prompt to enter the temp.
            System.out.print("Enter your temperature: ");

            // get the temp (double)
            float temp = input.nextFloat();
            System.out.println(); // Clear the line

            // return the temp
            return temp;
        }

    }

