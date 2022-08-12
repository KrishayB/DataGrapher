import java.awt.*;
import javax.swing.*;
import java.io.*;
import java.util.*;
import java.awt.event.*;

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

        int windowWidth = 1000;
        int windowHeight = 600;

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
class GraphingPanel extends JPanel implements ActionListener {
    private ArrayList<Integer> xVals; // Original x-values from the file
    private ArrayList<Integer> yVals; // Original y-values from the file
    private ArrayList<Integer> xValsNew; // Scaled y-values
    private ArrayList<Integer> yValsNew; // Scaled x-values
    private ArrayList<String> allTxtFiles; // Contains all the .txt files' names
    private int[] previousPoint; // Contains the coordinates of the previous point when graphing
    private int maxX; // Max x-value of the xVals ArrayList
    private int maxY; // Max y-value of the yVals ArrayList
    private JCheckBox showAxes; // For showing graph axes
    private JCheckBox showTickmarks; // For showing tickmarks
    private JCheckBox showLabels; // For showing labels on the graph
    private JCheckBox printToConsole; // For printing to console

    public GraphingPanel() {
        xVals = new ArrayList<Integer>();
        yVals = new ArrayList<Integer>();
        yValsNew = new ArrayList<Integer>();
        xValsNew = new ArrayList<Integer>();
        previousPoint = new int[2];
        printToConsole = new JCheckBox("Print To Console");

        readFileAndInitArrays("data_set1.txt");
        analyzeArrays();

        setLayout(new BorderLayout());
        setBackground(Color.BLACK);

        JPanel northGrid = new JPanel();
        northGrid.setLayout(new GridLayout(1, 2));
        northGrid.setBackground(Color.BLACK);

        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        menuPanel.setBackground(Color.BLACK);

        String[] allFiles = fileNames(".");
        allTxtFiles = new ArrayList<String>();

        for (int i = 0; i < allFiles.length; i++) {
            if (allFiles[i].endsWith(".txt"))
                allTxtFiles.add(allFiles[i]);
        }

        JMenuBar fileBar = new JMenuBar();
        JMenu fileMenu = new JMenu("Open File");

        for (int i = 0; i < allTxtFiles.size(); i++) {
            JMenuItem newItem = new JMenuItem(allTxtFiles.get(i));
            newItem.addActionListener(this);
            fileMenu.add(newItem);
        }

        fileBar.add(fileMenu);
        menuPanel.add(fileBar);
        northGrid.add(menuPanel);

        JPanel checkBoxPanel = new JPanel();
        checkBoxPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        checkBoxPanel.setBackground(Color.BLACK);

        showAxes = new JCheckBox("Show Axes");
        showTickmarks = new JCheckBox("Show Tickmarks");
        showLabels = new JCheckBox("Show Labels");

        showAxes.setForeground(Color.WHITE);
        showTickmarks.setForeground(Color.WHITE);
        showLabels.setForeground(Color.WHITE);
        printToConsole.setForeground(Color.WHITE);

        showAxes.setSelected(true);
        showTickmarks.setSelected(true);
        showLabels.setSelected(true);
        printToConsole.setSelected(false);

        showAxes.addActionListener(this);
        showTickmarks.addActionListener(this);
        showLabels.addActionListener(this);

        checkBoxPanel.add(showAxes);
        checkBoxPanel.add(showTickmarks);
        checkBoxPanel.add(showLabels);
        checkBoxPanel.add(printToConsole);

        northGrid.add(checkBoxPanel);

        add(northGrid, BorderLayout.NORTH);
    }

    /**
     * Whenever this is called, the method first determines which component called it. If it was not a JCheckBox, then
     * it knows that it was a JMenuItem. It will then reread the file, reinitialize the ArrayLists and arrays, and redraw
     * the graph. Lastly, it repaints regardless of whether the component was a JCheckBox or a JMenuItem.
     * @param evt - The ActionEvent that allows more information to be found out about the event
     */
    public void actionPerformed(ActionEvent evt) {
        String command = evt.getActionCommand();

        if (!(command.equals("Show Axes") || command.equals("Show Tickmarks") || command.equals("Show Labels") || command.equals("Print To Console"))) {
            readFileAndInitArrays(command);
            analyzeArrays();
        }

        this.repaint();
    }

    /**
     * This method finds all the files in the directory.
     * @param directoryPath - The path of the directory that is needed to be searched. In this case, it's the current directory,
     * so the path is ".".
     * @return - Returns a String[] containing the names of all of the directory's files.
     */
    public static String[] fileNames(String directoryPath) {
        File dir = new File(directoryPath);
    
        Collection<String> files = new ArrayList<String>();
    
        if (dir.isDirectory()) {
            File[] listFiles = dir.listFiles();
    
            for (File file : listFiles) {
                if (file.isFile()) {
                    files.add(file.getName());
                }
            }
        }
    
        return files.toArray(new String[]{});
    }

    /**
     * Here, the file data.txt is read and processed. The first line contains the x-values, and the second line contains
     * the y-values. In order to read the file in that format, the Scanner is first initialized, and it skips over the
     * "x" label in the file. It then stores all the numbers in that row using input.next() until the value of input.next()
     * becomes a String "y". At this point, it knows that it has reached the next line, and it will store the next row of
     * numbers into a different array called yVals. The previousPoint[] array is used for being able to draw in the
     * paintComponent() method.
     */
    public void readFileAndInitArrays(String fileName) {
        xVals = new ArrayList<Integer>();
        yVals = new ArrayList<Integer>();
        yValsNew = new ArrayList<Integer>();
        xValsNew = new ArrayList<Integer>();
        previousPoint = new int[2];

        Scanner input = null;
        File data = new File(fileName);

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

        if (printToConsole.isSelected()) {
            System.out.println("\n\n\n");
            System.out.println("x: " + xVals.toString());
            System.out.println("y: " + yVals.toString());
            System.out.println();
        }

        input.close();
    }

    /**
     * In this method, the values are scaled so that they cover the entire grid. All the x-values and y-values are multiplied
     * by 400/maxY or 800/maxX.
     */
    public void analyzeArrays() {
        maxX = Collections.max(xVals);
        for (int i = 0; i < xVals.size(); i++) {
            xValsNew.add(xVals.get(i) * 800/maxX);
        }

        maxY = Collections.max(yVals);
        for (int i = 0; i < yVals.size(); i++) {
            yValsNew.add(yVals.get(i) * 400/maxY);
        }

        if (printToConsole.isSelected()) {
            System.out.println("Max Y: " + maxX);
            System.out.println("Max Y: " + maxY);
            System.out.println("x new: " + xValsNew.toString());
            System.out.println("y new: " + yValsNew.toString());
            System.out.println("\n\n\n");
        }
    }

    /**
     * First, the axes, the tickmarks, and labels are drawn if needed. For drawing the graph, the grid is drawn first.
     * Then, there is a for-loop that starts from index 1 and repeats until xVals.size(). The reason why it starts
     * from 1 and not 0 is because we already have the point stored at the 0th index in the previousPoint[] array if
     * i == 1 in the for-loop. The previousPoint[] is then reinitialized with new values, and the graphing repeats
     * until all the data has been drawn.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.WHITE);

        // Graph axes
        if (showAxes.isSelected()) {
            g.drawLine(100, 100, 100, 500);
            g.drawLine(100, 500, 900, 500);
        }

        // Tick marks
        if (showTickmarks.isSelected()) {
            g.drawLine(90, 100, 110, 100);
            g.drawLine(900, 490, 900, 510);

            g.drawLine(90, 300, 110, 300);
            g.drawLine(500, 490, 500, 510);

            g.drawLine(100, 500, 90, 500);
            g.drawLine(100, 500, 100, 510);
        }

        // Labels
        if (showLabels.isSelected()) {
            g.drawString("" + maxY, 25, 105);
            g.drawString("" + maxX, 893, 550);

            g.drawString("" + maxY/2.0, 25, 305);
            g.drawString("" + maxX/2.0, 493, 550);

            g.drawString("0", 25, 505);
            g.drawString("0", 97, 550);
        }

        for (int i = 1; i < xValsNew.size(); i++) {
            g.setColor(Color.GREEN);
            if (i == 1) {
                previousPoint[0] = xValsNew.get(0);
                previousPoint[1] = yValsNew.get(0);
            }

            g.drawLine(previousPoint[0] + 100, 500 - previousPoint[1], xValsNew.get(i) + 100, 500 - yValsNew.get(i));

            previousPoint[0] = xValsNew.get(i);
            previousPoint[1] = yValsNew.get(i);
        }
    }
}