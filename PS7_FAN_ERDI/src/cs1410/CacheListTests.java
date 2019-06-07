package cs1410;

import static org.junit.Assert.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import org.junit.Test;

public class CacheListTests
{
    /**
     * An example test for CacheList objects
     */
    // test on the title constraints
    @Test
    public void test () throws IOException
    {
        CacheList clist = new CacheList(new Scanner(
                "GCABCD\tAs The World Turns\tbunny\t1\t1\tN40 45.875\tW111 48.986\nGCRQWK\tOld Three Tooth\tgeocadet\t3.5\t3\tN40 45.850\tW111 48.045\n"));
        clist.setTitleConstraint("Turns");
        ArrayList<Cache> selected = clist.select();
        assertEquals(1, selected.size());
        assertEquals("GCABCD", selected.get(0).getGcCode());
    }

    // test on the owner constraints
    @Test
    public void test2 () throws IOException
    {
        CacheList clist = new CacheList(new Scanner(
                "GCABCD\tAs The World Turns\tbunny\t1\t1\tN40 45.875\tW111 48.986\nGCRQWK\tOld Three Tooth\tgeocadet\t3.5\t3\tN40 45.850\tW111 48.045\n"));
        clist.setOwnerConstraint("geocadet");
        ArrayList<Cache> selected = clist.select();
        assertEquals(1, selected.size());
        assertEquals("GCRQWK", selected.get(0).getGcCode());
    }

    // test on the difficulty constraints
    @Test
    public void test3 () throws IOException
    {
        CacheList clist = new CacheList(new Scanner(
                "GCABCD\tAs The World Turns\tbunny\t1\t1\tN40 45.875\tW111 48.986\nGCRQWK\tOld Three Tooth\tgeocadet\t3.5\t3\tN40 45.850\tW111 48.045\n"));
        clist.setDifficultyConstraints(1.0, 1.5);
        ArrayList<Cache> selected = clist.select();
        assertEquals(1, selected.size());
        assertEquals("GCABCD", selected.get(0).getGcCode());
    }

    // test on the terrain constraints
    @Test
    public void test4 () throws IOException
    {
        CacheList clist = new CacheList(new Scanner(
                "GCABCD\tAs The World Turns\tbunny\t1\t1\tN40 45.875\tW111 48.986\nGCRQWK\tOld Three Tooth\tgeocadet\t3.5\t3\tN40 45.850\tW111 48.045\n"));
        clist.setTerrainConstraints(2.0, 2.5);
        ArrayList<Cache> selected = clist.select();
        assertEquals(0, selected.size());
    }

    // test the owners are in an ascending order
    @Test
    public void test5 () throws IOException
    {
        CacheList clist = new CacheList(new Scanner(
                "GCABCD\tAs The World Turns\tbunny\t1\t1\tN40 45.875\tW111 48.986\nGCRQWK\tOld Three Tooth\tgeocadet\t3.5\t3\tN40 45.850\tW111 48.045\n"));
        ArrayList<Cache> selected = clist.select();
        assertEquals(2, selected.size());
        assertEquals("bunny", selected.get(0).getOwner());
        assertEquals("geocadet", selected.get(1).getOwner());
    }

    // test if no specific constraints (are just initial values)
    @Test
    public void test6 () throws IOException
    {
        CacheList clist = new CacheList(new Scanner(
                "GCABCD\tAs The World Turns\tbunny\t1\t1\tN40 45.875\tW111 48.986\nGCRQWK\tOld Three Tooth\tgeocadet\t3.5\t3\tN40 45.850\tW111 48.045\n"));
        ArrayList<Cache> selected = clist.select();
        assertEquals(2, selected.size());
        assertEquals("GCABCD", selected.get(0).getGcCode());
        assertEquals("GCRQWK", selected.get(1).getGcCode());
    }

    // test if all constrains work
    @Test
    public void test7 () throws IOException
    {
        CacheList clist = new CacheList(new Scanner(
                "GCABCD\tAs The World Turns\tbunny\t1\t1\tN40 45.875\tW111 48.986\nGCRQWK\tOld Three Tooth\tgeocadet\t3.5\t3\tN40 45.850\tW111 48.045\n"));
        clist.setTitleConstraint("The World Turns");
        clist.setOwnerConstraint("bunny");
        clist.setDifficultyConstraints(1.0, 1.0);
        clist.setTerrainConstraints(1.0, 1.0);
        ArrayList<Cache> selected = clist.select();
        assertEquals(1, selected.size());
        assertEquals("GCABCD", selected.get(0).getGcCode());
    }

    // test if difficulty is bad on line 1
    @Test
    public void testCacheListExp1 () throws IOException
    {
        try
        {
            CacheList clist = new CacheList(new Scanner(
                    "GCABCD\tAs The World Turns\tbunny\t11\t1\tN40 45.875\tW111 48.986\nGCRQWK\tOld Three Tooth\tgeocadet\t3.5\t3\tN40 45.850\tW111 48.045\n"));
            fail("No exception thrown");
        }
        catch (IllegalArgumentException e)
        {
            assertEquals("Error happends on line1", e.getMessage());
        }
    }

    // test if terrain is bad on line 2
    @Test
    public void testCacheListExp2 () throws IOException
    {
        try
        {
            CacheList clist = new CacheList(new Scanner(
                    "GCABCD\tAs The World Turns\tbunny\t1\t1\tN40 45.875\tW111 48.986\nGCRQWK\tOld Three Tooth\tgeocadet\t3.5\t30\tN40 45.850\tW111 48.045\n"));
            fail("No exception thrown");
        }
        catch (IllegalArgumentException e)
        {
            assertEquals("Error happends on line2", e.getMessage());
        }
    }

    // test if GCCode is bad on line 2
    @Test
    public void testCacheListExp3 () throws IOException
    {
        try
        {
            CacheList clist = new CacheList(new Scanner(
                    "GCABCD\tAs The World Turns\tbunny\t1\t1\tN40 45.875\tW111 48.986\nGRQWK\tOld Three Tooth\tgeocadet\t3.5\t3\tN40 45.850\tW111 48.045\n"));
            fail("No exception thrown");
        }
        catch (IllegalArgumentException e)
        {
            assertEquals("Error happends on line2", e.getMessage());
        }
    }
}
