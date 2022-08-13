import java.io.*;

/**
 * This class can be used to get the data points to plot for an equation. The LENGTH variable
 * is the number of points you want, and the equation should be entered in the print statement
 * of the for loop right next to the (int) caster. Currently, the equation that is entered is
 * 200 * Math.cos(i) + 200. This gives all the y-values of that function in integers. After you
 * get the values, you can copy/paste them into the .txt files.
 */
public class GetDataPoints {

    /**
     * customizable function for this class to generate it's data points based off
     * @param x the input value for the function
     * @return resulting y value or f(x)
     */
    private static double equation(int x){
        return 200 * Math.sin(x) + 200;
    }

    private static void printPoints(String outputFileName) {
        final int LENGTH = 100;
        FileWriter fileWriter = null;

        //open the output file if output file name isnt null
        if (outputFileName != null)
        {
            try {
                fileWriter = new FileWriter(new File(outputFileName));
            } catch (IOException e) {
                System.out.println("IO error while opening file: " + outputFileName);
                System.exit(1);
            }
        }
        
        // Prints x values
        for (int x = 1; x <= LENGTH; x++) {
            if (outputFileName != null) {
                try {
                    fileWriter.write(x + " ");
                } catch (IOException e) {
                    System.out.println("IO error while writing to file: " + outputFileName);
                    System.exit(1);
                }
            }    
            else {
                System.out.print(x + " ");
            }
        }

        //blank lines
        if (outputFileName != null) {
            try {
                fileWriter.write("\n\n");;
            } catch (IOException e) {
                System.out.println("IO error while writing to file: " + outputFileName);
                System.exit(1);
            }
        }    
        else {
            System.out.println();
            System.out.println();
        }

        // Prints y values based on the equation function
        for (int x = 1; x <= LENGTH; x++) {
            if (outputFileName != null) {
                try {
                    fileWriter.write(equation(x) + " ");
                } catch (IOException e) {
                    System.out.println("IO error while writing to file: " + outputFileName);
                    System.exit(1);
                }
            }    
            else {
                System.out.print(equation(x) + " ");
            }
        }

        try {
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("Error while closing file: " + outputFileName);
        }
    }

    public static void main(String[] args) {
        if (args.length > 0 && args[0] instanceof String && args[0].endsWith(".txt")) {
            printPoints(args[0]);
        } else {
            printPoints(null);
        }
    }
}
