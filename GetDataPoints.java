/**
 * This class can be used to get the data points to plot for an equation. The LENGTH variable
 * is the number of points you want, and the equation should be entered in the print statement
 * of the for loop right next to the (int) caster. Currently, the equation that is entered is
 * 200 * Math.cos(i) + 200. This gives all the y-values of that function in integers. After you
 * get the values, you can copy/paste them into the .txt files.
 */
public class GetDataPoints {
    private static void printPoints() {
        final int LENGTH = 100;

        // Prints y
        for (int i = 1; i <= LENGTH; i++) {
            System.out.print((int)(200 * Math.cos(i) + 200) + " ");
        }

        System.out.println();
        System.out.println();

        // Prints x
        for (int i = 1; i <= LENGTH; i++) {
            System.out.print(i + " ");
        }
    }

    public static void main(String[] args) {
        printPoints();
    }
}
