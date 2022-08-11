import java.awt.*;
import javax.swing.*;
import java.io.*;
import java.util.*;

/**
 * This class just runs the program by creating the frame for it. It adds the GraphingPanel class to the frame.
 */
public class Graphing {
    public Graphing () {}

    /**
     * This method is where the frame for the panel is created. The third line in the method gets the user's screen size
     * so that the window can automatically be centered in the middle of the screen. The windowWidth and windowHeight variables
     * are for the dimensions of the program's window.
     */
    private static void run() {
        JFrame frame = new JFrame();
        GraphingPanel graphingPan = new GraphingPanel();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        int windowWidth = 310;
        int windowHeight = 400;

        frame.setSize(new Dimension(windowWidth, windowHeight));
        frame.setLocation((int)(screenSize.getWidth()/2 - windowWidth/2), (int)(screenSize.getHeight()/2 - windowHeight/2));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.getContentPane().add(graphingPan);
        frame.setVisible(true);
        frame.setResizable(false);
    }

    public static void main(String[] args) {
        run();
    }
}

/**
 * This class is a JPanel where the graph will be drawn.
 */
class GraphingPanel extends JPanel {
    private ArrayList<Integer> xVals;
    private ArrayList<Integer> yVals;
    private int[] previousPoint;

    public GraphingPanel() {
        xVals = new ArrayList<Integer>();
        yVals = new ArrayList<Integer>();
        previousPoint = new int[2];

        readFileAndInitArrays();

        setLayout(new BorderLayout());
        setBackground(Color.BLACK);
    }

    /**
     * Here, the file data.txt is read and processed. The first line contains the x-values, and the second line contains
     * the y-values. In order to read the file in that format, the Scanner is first initialized, and it skips over the
     * "x" label in the file. It then stores all the numbers in that row using input.next() until the value of input.next()
     * becomes a String "y". At this point, it knows that it has reached the next line, and it will store the next row of
     * numbers into a different array called yVals. The previousPoint[] array is used for being able to draw in the
     * paintComponent() method.
     */
    public void readFileAndInitArrays() {
        Scanner input = null;
        File data = new File("data.txt");

        try {
            input = new Scanner(data);
        } catch (FileNotFoundException e) {
            System.out.println("The file wasn't found.");
            System.exit(1);
        }

        input.next();
        String value = input.next();
        
        while (!value.equals("y")) {
            xVals.add(Integer.parseInt(value));
            
            value = input.next();
        }

        while (input.hasNextLine()) {
            value = input.next();
            yVals.add(Integer.parseInt(value));
        }

        System.out.println("x: " + xVals.toString());
        System.out.println("y: " + yVals.toString());

        previousPoint[0] = xVals.get(0);
        previousPoint[1] = yVals.get(0);

        input.close();
    }

    /**
     * The grid is first drawn with the third and fourth lines in this method. Then, there is a for-loop that starts from
     * index 1 and repeats until xVals.size(). The reason why it starts from 1 and not 0 is because we already have the
     * point stored at the 0th index in the previousPoint[] array. The next line of code then checks the point is being
     * drawn from the last point to the first point, and if it's not, then only it will draw the line. The previousPoint[]
     * is then reinitialized with new values, and the graphing repeats until all the data has been drawn.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.WHITE);
        g.drawLine(100, 100, 100, 200);
        g.drawLine(100, 200, 200, 200);

        for (int i = 1; i < xVals.size(); i++) {
            g.drawLine(previousPoint[0] + 100, 200 - previousPoint[1], xVals.get(i) + 100, 200 - yVals.get(i));

            previousPoint[0] = xVals.get(i);
            previousPoint[1] = yVals.get(i);
        }
    }
}