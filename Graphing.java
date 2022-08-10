import java.awt.*;
import javax.swing.*;
import java.io.*;
import java.util.*;

public class Graphing {
    private static void run() {
        JFrame frame = new JFrame();
        GraphingPanel graphingPan = new GraphingPanel();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        frame.setSize(new Dimension(300, 400));
        frame.setLocation((int)(screenSize.getWidth()/2 - 150), (int)(screenSize.getHeight()/2 - 200));
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.getContentPane().add(graphingPan);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        run();
    }
}

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
    }

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

        System.out.println(xVals.toString());
        System.out.println(yVals.toString());

        previousPoint[0] = xVals.get(0);
        previousPoint[1] = yVals.get(0);

        input.close();
    }

    @Override
    public void paintComponent(Graphics g) {
        g.drawLine(100, 100, 100, 200);
        g.drawLine(100, 200, 200, 200);

        for (int i = 1; i < xVals.size(); i++) {
            g.drawLine(previousPoint[0] + 100, 200 - previousPoint[1], xVals.get(i) + 100, 200 - yVals.get(i));

            previousPoint[0] = xVals.get(i);
            previousPoint[1] = yVals.get(i);
        }
    }
}