package cs1410;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class CacheTests
{
    /**
     * An example test for Cache objects
     */
    @Test
    public void test ()
    {
        Cache c = new Cache("GCRQWK\tOld Three Tooth\tgeocadet\t3.5\t3\tN40 45.850\tW111 48.045");
        assertEquals("GCRQWK", c.getGcCode());

        assertEquals("Old Three Tooth", c.getTitle());

        assertEquals("geocadet", c.getOwner());

        assertEquals(3.5, c.getDifficulty(), 1e-2);

        assertEquals(3, c.getTerrain(), 1e-2);

        assertEquals("Old Three Tooth by geocadet", c.toString());

        assertEquals("N40 45.850", c.getLatitude());

        assertEquals("W111 48.045", c.getLongitude());

    }

    // less than 7 items
    @Test(expected = IllegalArgumentException.class)
    public void testException1 ()
    {
        Cache c = new Cache("GCRQWK\tOld Three Tooth\tgeocadet\t3.5\t3\tN40 45.850\t");
    }

    // more than 7 items
    @Test(expected = IllegalArgumentException.class)
    public void testException2 ()
    {
        Cache c = new Cache("GCRQWK\tOld Three Tooth\tgeocadet\t3.5\t3\tN40 45.850\tN40 45.850\tW111 48.045");
    }

    // A GC code that is started by anything other than "GC"
    @Test(expected = IllegalArgumentException.class)
    public void testException3 ()
    {
        Cache c = new Cache("GWRQWK\tOld Three Tooth\tgeocadet\t3.5\t3\tN40 45.850\tW111 48.045");
    }

    // A GC code that is not followed by one or more upper-case letters
    @Test(expected = IllegalArgumentException.class)
    public void testException4 ()
    {
        Cache c = new Cache("GCRQsK\tOld Three Tooth\tgeocadet\t3.5\t3\tN40 45.850\tW111 48.045");
    }

    // A GC code that is not followed by letters and/or digits
    @Test(expected = IllegalArgumentException.class)
    public void testException5 ()
    {
        Cache c = new Cache("GCRQK@&$(!\tOld Three Tooth\tgeocadet\t3.5\t3\tN40 45.850\tW111 48.045");
    }

    // A difficulty rating that parses to anything other than the doubles 1, 1.5, 2, 2.5, 3, 3.5, 4, 4.5, or 5.
    @Test(expected = IllegalArgumentException.class)
    public void testException6 ()
    {
        Cache c = new Cache("GCRQWK\tOld Three Tooth\tgeocadet\t5.5\t3\tN40 45.850\tW111 48.045");
    }

    // A terrain rating that parses to anything other than the doubles 1, 1.5, 2, 2.5, 3, 3.5, 4, 4.5, or 5.
    @Test(expected = IllegalArgumentException.class)
    public void testException7 ()
    {
        Cache c = new Cache("GCRQWK\tOld Three Tooth\tgeocadet\t3.5\t30\tN40 45.850\tW111 48.045");
    }

    // title only consists of white spaces
    @Test(expected = IllegalArgumentException.class)
    public void testException8 ()
    {
        Cache c = new Cache("GCRQWK\t   \tgeocadet\t3.5\t3\tN40 45.850\tW111 48.045");
    }

    // owner only consists of white spaces
    @Test(expected = IllegalArgumentException.class)
    public void testException9 ()
    {
        Cache c = new Cache("GCRQWK\tOld Three Tooth\t\t3.5\t3\tN40 45.850\tW111 48.045");
    }

    // latitude only consists of white spaces
    @Test(expected = IllegalArgumentException.class)
    public void testException10 ()
    {
        Cache c = new Cache("GCRQWK\tOld Three Tooth\tgeocadet\t3.5\t3\t\tW111 48.045");
    }

    // longitude only consists of white spaces
    @Test(expected = IllegalArgumentException.class)
    public void testException11 ()
    {
        Cache c = new Cache("GCRQWK\tOld Three Tooth\tgeocadet\t3.5\t3\tN40 45.850\t ");
    }
}
