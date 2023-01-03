import java.io.*;

/**
 * This class can be used to get the data points to plot for an equation. LENGTH is the number of
 * points needed, and the equation should be entered below in the respective method. Currently,
 * the equation is 200 * Math.sin(i) + 200. Compile and run this file with javac/java, and provide
 * a file name if a seperate file is needed to be automatically created.
 * 
 * Example usage: java GetDataPoints euler.txt
 *                java GetDataPoints
 */
public class GetDataPoints {
    private static final int LENGTH = 100;

    /**
     * Customizable function for this class to generate data points based off of an equation.
     * 
     * @param x     The input value for the function
     * @return      Resulting y value or f(x)
     */
    private static double equation(int x) {
        return 200 * Math.sin(x) + 200;
    }

    /**
     * Prints all the points to a new or existing output file. Further details are explained
     * below.
     * 
     * @param outputFileName    File name for output
     */
    private static void printPoints(String outputFileName) {
        FileWriter fileWriter = null;

        // Open output file if output file name isn't null
        if (outputFileName != null) {
            try {
                fileWriter = new FileWriter(new File(outputFileName));
                fileWriter.write("x ");
            } catch (IOException e) {
                System.out.println("IO error while opening file: " + outputFileName);
                System.exit(1);
            }
        } else
            System.out.print("x ");
        
        // Prints x values
        for (int x = 1; x <= LENGTH; x++) {
            if (outputFileName != null) {
                try {
                    fileWriter.write(x + " ");
                } catch (IOException e) {
                    System.out.println("IO error while writing to file: " + outputFileName);
                    System.exit(1);
                }
            } else
                System.out.print(x + " ");
        }

        // Prints blank lines
        if (outputFileName != null) {
            try {
                fileWriter.write("\ny ");;
            } catch (IOException e) {
                System.out.println("IO error while writing to file: " + outputFileName);
                System.exit(1);
            }
        } else {
            System.out.println();
            System.out.print("\ny ");
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
            } else
                System.out.print(equation(x) + " ");
        }

        if (fileWriter != null) {
            try {
                fileWriter.close();
            } catch (IOException e) {
                System.out.println("Error while closing file: " + outputFileName);
            }
        }
    }

    /**
     * Main method. Either creates a new file or prints to terminal if no file name is specified.
     * 
     * @param args  Optional file name for outputting to a file
     */
    public static void main(String[] args) {
        if (args.length > 0 && args[0] instanceof String && args[0].endsWith(".txt")) {
            printPoints(args[0]);
            System.out.println("File created.");
        } else
            printPoints(null);
    }
}
