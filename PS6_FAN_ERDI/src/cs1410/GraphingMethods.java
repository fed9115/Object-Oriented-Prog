package cs1410;

import static org.junit.Assume.assumeNoException;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeMap;

/**
 * Methods in support of PS6.
 * 
 * @author Erdi Fan and Joe Zachary
 */
public class GraphingMethods
{
    /**
     * Constant used to request a max operation
     */
    public final static int MAX = 0;

    /**
     * Constant used to request a min operation
     */
    public final static int MIN = 1;

    /**
     * Constant used to request a sum operation
     */
    public final static int SUM = 2;

    /**
     * Constant used to request an average operation
     */
    public final static int AVG = 3;

    /**
     * The dataSource must consist of one or more lines. If there is not at least one line, the method throws an
     * IllegalArgumentException whose message explains what is wrong.
     * 
     * Each line must consist of some text (a key), followed by a tab character, followed by a double literal (a value),
     * followed by a newline.
     * 
     * If any lines are encountered that don't meet this criteria, the method throws an IllegalArgumentException whose
     * message explains what is wrong.
     * 
     * Otherwise, the map returned by the method (here called categoryMap) must have all of these properties:
     * 
     * (1) The set of keys contained by categoryMap must be the same as the set of keys that occur in the Scanner
     * 
     * (2) The list valueMap.get(key) must contain exactly the same numbers that appear as values on lines in the
     * Scanner that begin with key. The values must occur in the list in the same order as they appear in the Scanner.
     * 
     * For example, if the Scanner contains
     * 
     * <pre>
     * Utah        10
     * Nevada       3
     * Utah         2
     * California  14
     * Arizona     21
     * Utah         2
     * California   7
     * California   6
     * Nevada      11
     * California   1
     * </pre>
     * 
     * (where the spaces in each line are intended to be a single tab), then this map should be returned:
     * 
     * <pre>
     *  Arizona    {21}
     *  California {14, 7, 6, 1} 
     *  Nevada     {3, 11}
     *  Utah       {10, 2, 2}
     * </pre>
     */
    public static TreeMap<String, ArrayList<Double>> readTable (Scanner dataSource)
    {
        TreeMap<String, ArrayList<Double>> categoryMap = new TreeMap<>();

        while (dataSource.hasNextLine())
        {

            String line = dataSource.nextLine();
            String[] str = line.split("\t");

            if (str.length != 2)
            {
                throw new IllegalArgumentException("Extra token");
            }
            int dot = 0;
            for (int i = 0; i < str[1].length(); i++)
            {
                if ((!Character.isDigit(str[1].charAt(i))) && str[1].charAt(i) != '.')
                {
                    throw new IllegalArgumentException("not a double");
                }

                if (str[1].charAt(i) == '.')
                {
                    dot++;
                }

            }
            if (dot > 1)
                throw new IllegalArgumentException("dot > 1");

            String state = str[0];
            double stateNum = Double.parseDouble(str[1]);

            ArrayList<Double> numList = categoryMap.get(state);
            if (numList == null)
            {
                ArrayList<Double> num = new ArrayList<>();
                num.add(stateNum);
                categoryMap.put(state, num);
            }
            else
            {
                numList.add(stateNum);
            }
        }
        if (categoryMap.size() == 0)
        {
            throw new IllegalArgumentException("Nothing inside");
        }

        return categoryMap;

    }

    /**
     * If categoryMap is of size zero, throws an IllegalArgumentException whose message explains what is wrong.
     * 
     * Else if any of the values in the category map is an empty set, throws an IllegalArgumentException whose message
     * explains what is wrong.
     * 
     * Else if any of the numbers in the categoryMap is not positive, throws an IllegalAgumentException whose message
     * explains what is wrong.
     * 
     * Else if operation is anything other than SUM, AVG, MAX, or MIN, throws an IllegalArgumentException whose message
     * explains what is wrong.
     *
     * Else, returns a TreeMap<String, Double> (here called summaryMap) such that:
     * 
     * (1) The sets of keys contained by categoryMap and summaryMap are the same
     * 
     * (2) For all keys, summaryMap.get(key) is the result of combining the numbers contained in the set
     * categoryMap.get(key) using the specified operation. If the operation is MAX, "combining" means finding the
     * largest of the numbers. If the operation is MIN, "combining" means finding the smallest of the numbers. If the
     * operation is SUM, combining means summing the numbers. If the operation is AVG, combining means averaging the
     * numbers.
     * 
     * For example, suppose the categoryMap maps like this:
     * 
     * <pre>
     *  Arizona    {21}
     *  California {14, 7, 6, 1} 
     *  Nevada     {3, 11}
     *  Utah       {10, 2, 2}
     * </pre>
     * 
     * and the operation is SUM. The map that is returned must map like this:
     * 
     * <pre>
     *  Arizona    21
     *  California 28 
     *  Nevada     14
     *  Utah       14
     * </pre>
     */
    public static TreeMap<String, Double> prepareGraph (TreeMap<String, ArrayList<Double>> categoryMap, int operation)
    {
        TreeMap<String, Double> summaryMap = new TreeMap<>();
        if (categoryMap.isEmpty())
        {
            throw new IllegalArgumentException("categoryMap is of size zero");
        }

        for (String key : categoryMap.keySet())
        {
            ArrayList<Double> curr = categoryMap.get(key);
            if (curr.isEmpty())
            {
                throw new IllegalArgumentException("any of the values in the category map is an empty set");
            }
            for (Double d : curr)
            {
                if (d <= 0)
                {
                    throw new IllegalArgumentException("any of the numbers in the categoryMap is not positive");
                }
            }
        }

        if (operation != MAX && operation != MIN && operation != SUM && operation != AVG)
        {
            throw new IllegalArgumentException();
        }
        if (operation == MAX)
        {
            for (String key : categoryMap.keySet())
            {
                ArrayList<Double> curr = categoryMap.get(key);
                double maxSoFar = curr.get(0);
                if (curr.size() == 1)
                {
                    summaryMap.put(key, maxSoFar);
                }
                for (int i = 1; i < curr.size(); i++)
                {
                    double d = curr.get(i);
                    if (d > maxSoFar)
                    {
                        maxSoFar = d;
                    }
                }
                summaryMap.put(key, maxSoFar);
            }
        }
        if (operation == MIN)
        {
            for (String key : categoryMap.keySet())
            {
                ArrayList<Double> curr = categoryMap.get(key);
                double minSoFar = curr.get(0);
                if (curr.size() == 1)
                {
                    summaryMap.put(key, minSoFar);
                }
                for (int i = 1; i < curr.size(); i++)
                {
                    double d = curr.get(i);
                    if (d < minSoFar)
                    {
                        minSoFar = d;
                    }
                }
                summaryMap.put(key, minSoFar);
            }
        }
        if (operation == SUM)
        {
            for (String key : categoryMap.keySet())
            {
                ArrayList<Double> curr = categoryMap.get(key);
                double sum = 0;
                for (int i = 0; i < curr.size(); i++)
                {
                    sum = sum + curr.get(i);
                }
                summaryMap.put(key, sum);
            }
        }
        if (operation == AVG)
        {
            for (String key : categoryMap.keySet())
            {
                ArrayList<Double> curr = categoryMap.get(key);
                double sum = 0;
                for (int i = 0; i < curr.size(); i++)
                {
                    sum = sum + curr.get(i);
                }
                summaryMap.put(key, sum / curr.size());
            }
        }
        return summaryMap;
    }

    /**
     * If colorMap is empty, throws an IllegalArgumentException.
     * 
     * If there is a key in colorMap that does not occur in summaryMap, throws an IllegalArgumentException whose message
     * explains what is wrong.
     * 
     * If any of the numbers in the summaryMap is non-positive, throws an IllegalArgumentException whose message
     * explains what is wrong.
     * 
     * Otherwise, displays on g the subset of the data contained in summaryMap that has a key that appears in colorMap
     * with either a pie chart (if usePieChart is true) or a bar graph (otherwise), using the colors in colorMap.
     * 
     * Let SUM be the sum of all the values in summaryMap whose keys also appear in colorMap, let KEY be a key in
     * colorMap, let VALUE be the value to which KEY maps in summaryMap, and let COLOR be the color to which KEY maps in
     * colorMap. The area of KEY's slice (in a pie chart) and the length of KEY's bar (in a bar graph) must be
     * proportional to VALUE/SUM. The slice/bar should be labeled with both KEY and VALUE, and it should be colored with
     * COLOR.
     * 
     * For example, suppose summaryMap has this mapping:
     * 
     * <pre>
     *  Arizona    21
     *  California 28 
     *  Nevada     14
     *  Utah       14
     * </pre>
     * 
     * and colorMap has this mapping:
     * 
     * <pre>
     *  California Color.GREEN
     *  Nevada     Color.BLUE
     *  Utah       Color.RED
     * </pre>
     * 
     * Since Arizona does not appear as a key in colorMap, Arizona's entry in summaryMap is ignored.
     * 
     * In a pie chart Utah and Nevada should each have a quarter of the pie and California should have half. In a bar
     * graph, California's line should be twice as long as Utah's and Nevada's. Utah's slice/bar should be red, Nevada's
     * blue, and California's green.
     * 
     * The method should display the pie chart or bar graph by drawing on the g parameter. The example code below draws
     * both a pie chart and a bar graph for the situation described above.
     */
    public static void drawGraph (Graphics g, TreeMap<String, Double> summaryMap, TreeMap<String, Color> colorMap,
            boolean usePieChart)
    {
        if (colorMap.isEmpty())
        {
            throw new IllegalArgumentException("colorMap is empty");
        }
        for (String key : colorMap.keySet())
        {
            if (!summaryMap.keySet().contains(key))
            {
                throw new IllegalArgumentException("there is a key in colorMap that does not occur in summaryMap");
            }

            double curr = summaryMap.get(key);
            if (curr <= 0)
            {
                throw new IllegalArgumentException("any of the numbers in the summaryMap is non-positive");
            }

        }
        // This implementation ignores its parameters (except for usePieChart) and draws a pie chart or a bar graph
        // for the data described in the Javadoc.

        final int TOP = 10;        // Offset of graph from top edge
        final int LEFT = 10;       // Offset of graph from left edge
        final int DIAM = 300;      // Diameter of pie chart
        final int WIDTH = 10;      // Width of bar in bar chart

        double SUM = 0;

        for (String KEY : colorMap.keySet())
        {
            double VALUE = summaryMap.get(KEY);
            SUM = SUM + VALUE;
        }

        // Draw a pie chart if requested
        if (usePieChart)
        {
            int curr = 0;
            int top = 0;
            int counter = 0;
            for (String KEY : colorMap.keySet())
            {
                Color COLOR = colorMap.get(KEY);
                double VALUE = summaryMap.get(KEY);
                g.setColor(COLOR);
                if (counter < colorMap.size() - 1)
                {
                    g.fillArc(LEFT, TOP, DIAM, DIAM, (int) (curr), (int) ((VALUE / SUM * 360)));
                }
                else
                {
                    g.fillArc(LEFT, TOP, DIAM, DIAM, (int) (curr), (int) (360 - curr));
                }
                curr = (int) (curr + VALUE / SUM * 360);
                g.fillRect(LEFT + DIAM + 2 * WIDTH, TOP + top * WIDTH, WIDTH, WIDTH);
                g.setColor(Color.black);
                g.drawString(KEY + " " + VALUE, LEFT + DIAM + 4 * WIDTH, TOP + (top + 1) * WIDTH);
                top = top + 2;
                counter++;
            }

        }

        // Draw a bar chart if requested
        else
        {
            int top = 1;
            for (String KEY : colorMap.keySet())
            {
                Color COLOR = colorMap.get(KEY);
                double VALUE = summaryMap.get(KEY);
                g.setColor(COLOR);
                g.fillRect((int) (LEFT + DIAM - DIAM * VALUE / SUM), (int) (TOP + (top - 1) * WIDTH),
                        (int) (DIAM * VALUE / SUM), 2 * WIDTH);
                g.setColor(Color.black);
                g.drawString(KEY + " " + VALUE, LEFT + DIAM + 2 * WIDTH, TOP + top * WIDTH + WIDTH / 2);
                top = top + 3;
            }

        }
    }
}
