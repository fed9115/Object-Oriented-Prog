package cs1410;

import static org.junit.Assert.assertEquals;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeMap;
import org.junit.Test;

public class MyTestCases
{
    @Test
    public void testReadTable1 ()
    {
        try (Scanner scn = new Scanner(
                "Utah\t10\nNevada\t3\nUtah\t2\nCalifornia\t14\nArizona\t21\nUtah\t2\nCalifornia\t7\nCalifornia\t6\nNevada\t11\nCalifornia\t1\n"))
        {
            TreeMap<String, ArrayList<Double>> actual = GraphingMethods.readTable(scn);

            TreeMap<String, ArrayList<Double>> expected = new TreeMap<>();
            ArrayList<Double> azList = new ArrayList<>();
            azList.add(21.0);
            expected.put("Arizona", azList);

            ArrayList<Double> caList = new ArrayList<>();
            caList.add(14.0);
            caList.add(7.0);
            caList.add(6.0);
            caList.add(1.0);
            expected.put("California", caList);

            ArrayList<Double> nvList = new ArrayList<>();
            nvList.add(3.0);
            nvList.add(11.0);
            expected.put("Nevada", nvList);

            ArrayList<Double> utList = new ArrayList<>();
            utList.add(10.0);
            utList.add(2.0);
            utList.add(2.0);
            expected.put("Utah", utList);

            assertEquals(expected, actual);
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testReadTable2 ()
    {
        try (Scanner scnr = new Scanner(""))
        {
            GraphingMethods.readTable(scnr);
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testReadTable3 ()
    {
        try (Scanner scnr = new Scanner("\t"))
        {
            GraphingMethods.readTable(scnr);
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testReadTable4 ()
    {
        try (Scanner scnr = new Scanner(" "))
        {
            GraphingMethods.readTable(scnr);
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testReadTable5 ()
    {
        try (Scanner scnr = new Scanner("Utah"))
        {
            GraphingMethods.readTable(scnr);
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testReadTable6 ()
    {
        try (Scanner scnr = new Scanner("Utah\t"))
        {
            GraphingMethods.readTable(scnr);
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testReadTable7 ()
    {
        try (Scanner scnr = new Scanner("Utah\t10\tCajkds\t13\n"))
        {
            GraphingMethods.readTable(scnr);
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testReadTable8 ()
    {
        try (Scanner scnr = new Scanner("Utah\t10.0232.432"))
        {
            GraphingMethods.readTable(scnr);
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testReadTable9 ()
    {
        try (Scanner scnr = new Scanner("Utah\t$#@$@"))
        {
            GraphingMethods.readTable(scnr);
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testReadTable10 ()
    {
        try (Scanner scnr = new Scanner("Utah\t10\tfwfoh"))
        {
            GraphingMethods.readTable(scnr);
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testReadTable11 ()
    {
        try (Scanner scnr = new Scanner("Utah 10"))
        {
            GraphingMethods.readTable(scnr);
        }
    }

    @Test
    public void testPrepareGraph1 ()
    {
        TreeMap<String, ArrayList<Double>> expectedMap = new TreeMap<>();
        ArrayList<Double> azList = new ArrayList<>();
        azList.add(21.0);
        expectedMap.put("Arizona", azList);

        ArrayList<Double> caList = new ArrayList<>();
        caList.add(14.0);
        caList.add(7.0);
        caList.add(6.0);
        caList.add(1.0);
        expectedMap.put("California", caList);

        ArrayList<Double> nvList = new ArrayList<>();
        nvList.add(3.0);
        nvList.add(11.0);
        expectedMap.put("Nevada", nvList);

        ArrayList<Double> utList = new ArrayList<>();
        utList.add(10.0);
        utList.add(2.0);
        utList.add(2.0);
        expectedMap.put("Utah", utList);

        TreeMap<String, Double> actualMax = GraphingMethods.prepareGraph(expectedMap, 0);
        TreeMap<String, Double> actualMin = GraphingMethods.prepareGraph(expectedMap, 1);
        TreeMap<String, Double> actualSum = GraphingMethods.prepareGraph(expectedMap, 2);
        TreeMap<String, Double> actualAvg = GraphingMethods.prepareGraph(expectedMap, 3);

        TreeMap<String, Double> expectedMax = new TreeMap<>();

        expectedMax.put("Arizona", 21.0);
        expectedMax.put("California", 14.0);
        expectedMax.put("Nevada", 11.0);
        expectedMax.put("Utah", 10.0);

        TreeMap<String, Double> expectedMin = new TreeMap<>();

        expectedMin.put("Arizona", 21.0);
        expectedMin.put("California", 1.0);
        expectedMin.put("Nevada", 3.0);
        expectedMin.put("Utah", 2.0);

        TreeMap<String, Double> expectedSum = new TreeMap<>();

        expectedSum.put("Arizona", 21.0);
        expectedSum.put("California", 28.0);
        expectedSum.put("Nevada", 14.0);
        expectedSum.put("Utah", 14.0);

        TreeMap<String, Double> expectedAvg = new TreeMap<>();

        expectedAvg.put("Arizona", 21.0);
        expectedAvg.put("California", 7.0);
        expectedAvg.put("Nevada", 7.0);
        expectedAvg.put("Utah", 14.0 / 3.0);

        assertEquals(expectedMax, actualMax);
        assertEquals(expectedMin, actualMin);
        assertEquals(expectedSum, actualSum);
        assertEquals(expectedAvg, actualAvg);

    }

    @Test(expected = IllegalArgumentException.class)
    public void testPrepareGraph2 ()
    {
        TreeMap<String, ArrayList<Double>> emptyMap = new TreeMap<>();
        GraphingMethods.prepareGraph(emptyMap, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPrepareGraph3 ()
    {
        TreeMap<String, ArrayList<Double>> emptyMap = new TreeMap<>();
        GraphingMethods.prepareGraph(emptyMap, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPrepareGraph4 ()
    {
        TreeMap<String, ArrayList<Double>> emptyMap = new TreeMap<>();
        GraphingMethods.prepareGraph(emptyMap, 2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPrepareGraph5 ()
    {
        TreeMap<String, ArrayList<Double>> emptyMap = new TreeMap<>();
        GraphingMethods.prepareGraph(emptyMap, 3);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPrepareGraph6 ()
    {
        TreeMap<String, ArrayList<Double>> emptyValueMap = new TreeMap<>();
        ArrayList<Double> emptyArray = new ArrayList<>();
        emptyValueMap.put("Utah", emptyArray);
        GraphingMethods.prepareGraph(emptyValueMap, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPrepareGraph7 ()
    {
        TreeMap<String, ArrayList<Double>> emptyValueMap = new TreeMap<>();
        ArrayList<Double> emptyArray = new ArrayList<>();
        emptyValueMap.put("Utah", emptyArray);
        GraphingMethods.prepareGraph(emptyValueMap, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPrepareGraph8 ()
    {
        TreeMap<String, ArrayList<Double>> emptyValueMap = new TreeMap<>();
        ArrayList<Double> emptyArray = new ArrayList<>();
        emptyValueMap.put("Utah", emptyArray);
        GraphingMethods.prepareGraph(emptyValueMap, 2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPrepareGraph9 ()
    {
        TreeMap<String, ArrayList<Double>> emptyValueMap = new TreeMap<>();
        ArrayList<Double> emptyArray = new ArrayList<>();
        emptyValueMap.put("Utah", emptyArray);
        GraphingMethods.prepareGraph(emptyValueMap, 3);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPrepareGraph10 ()
    {
        TreeMap<String, ArrayList<Double>> notPositiveValueMap = new TreeMap<>();
        ArrayList<Double> notPositiveArray = new ArrayList<>();
        notPositiveArray.add(-1.72);
        notPositiveArray.add(2.39);
        notPositiveValueMap.put("Utah", notPositiveArray);
        GraphingMethods.prepareGraph(notPositiveValueMap, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPrepareGraph11 ()
    {
        TreeMap<String, ArrayList<Double>> notPositiveValueMap = new TreeMap<>();
        ArrayList<Double> notPositiveArray = new ArrayList<>();
        notPositiveArray.add(-1.72);
        notPositiveArray.add(2.39);
        notPositiveValueMap.put("Utah", notPositiveArray);
        GraphingMethods.prepareGraph(notPositiveValueMap, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPrepareGraph12 ()
    {
        TreeMap<String, ArrayList<Double>> notPositiveValueMap = new TreeMap<>();
        ArrayList<Double> notPositiveArray = new ArrayList<>();
        notPositiveArray.add(-1.72);
        notPositiveArray.add(2.39);
        notPositiveValueMap.put("Utah", notPositiveArray);
        GraphingMethods.prepareGraph(notPositiveValueMap, 2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPrepareGraph13 ()
    {
        TreeMap<String, ArrayList<Double>> notPositiveValueMap = new TreeMap<>();
        ArrayList<Double> notPositiveArray = new ArrayList<>();
        notPositiveArray.add(-1.72);
        notPositiveArray.add(2.39);
        notPositiveValueMap.put("Utah", notPositiveArray);
        GraphingMethods.prepareGraph(notPositiveValueMap, 3);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPrepareGraph14 ()
    {
        TreeMap<String, ArrayList<Double>> notPositiveValueMap = new TreeMap<>();
        ArrayList<Double> array = new ArrayList<>();
        array.add(1.72);
        array.add(2.39);
        notPositiveValueMap.put("Utah", array);
        GraphingMethods.prepareGraph(notPositiveValueMap, 4);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDrawGraphError1 ()
    {
        TreeMap<String, Color> color = new TreeMap<>();
        TreeMap<String, Double> summary = new TreeMap<>();

        summary.put("Arizona", 21.0);
        summary.put("California", 7.0);
        summary.put("Nevada", 7.0);
        summary.put("Utah", 14.0 / 3.0);

        Graphics g = null;

        GraphingMethods.drawGraph(g, summary, color, true);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDrawGraphError2 ()
    {
        TreeMap<String, Color> color = new TreeMap<>();

        color.put("Arizona", Color.BLUE);
        color.put("California", Color.RED);
        color.put("Nevada", Color.GREEN);
        color.put("Utah", Color.ORANGE);
        color.put("Texas", Color.CYAN);

        TreeMap<String, Double> summary = new TreeMap<>();

        summary.put("Arizona", 21.0);
        summary.put("California", 7.0);
        summary.put("Nevada", 7.0);
        summary.put("Utah", 14.0 / 3.0);

        Graphics g = null;

        GraphingMethods.drawGraph(g, summary, color, true);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDrawGraphError3 ()
    {
        TreeMap<String, Color> color = new TreeMap<>();

        color.put("Arizona", Color.BLUE);
        color.put("California", Color.RED);
        color.put("Nevada", Color.GREEN);
        color.put("Utah", Color.ORANGE);

        TreeMap<String, Double> summary = new TreeMap<>();

        summary.put("Arizona", -21.0);
        summary.put("California", 7.0);
        summary.put("Nevada", 7.0);
        summary.put("Utah", -14.0 / 3.0);

        Graphics g = null;

        GraphingMethods.drawGraph(g, summary, color, true);
    }

}
