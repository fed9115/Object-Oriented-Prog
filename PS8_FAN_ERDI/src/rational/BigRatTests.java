package rational;

import static org.junit.Assert.*;
import java.math.BigInteger;
import org.junit.Test;

public class BigRatTests
{
    @Test
    public void testConstructor1 ()
    {
        BigRat r = new BigRat();
        assertEquals("0", r.toString());
    }

    @Test
    public void testConstructor2 ()
    {
        BigRat r = new BigRat(5);
        assertEquals("5", r.toString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNumZero1 ()
    {
        BigRat r = new BigRat(4, 0);
        assertEquals("4/0", r.toString());
    }

    @Test
    public void testConstructor3 ()
    {
        BigRat r1 = new BigRat(1, 2);
        assertEquals("1/2", r1.toString());

        BigRat r2 = new BigRat(5, 15);
        assertEquals("1/3", r2.toString());

        BigRat r3 = new BigRat(-4, -2);
        assertEquals("2", r3.toString());

        BigRat r4 = new BigRat(6, -8);
        assertEquals("-3/4", r4.toString());

        BigRat r5 = new BigRat(3, -15);
        assertEquals("-1/5", r5.toString());

        try
        {
            new BigRat(4, 0);
            fail("No exception thrown");
        }
        catch (IllegalArgumentException e)
        {
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNumZero2 ()
    {
        BigInteger n = new BigInteger("4");
        BigInteger d = new BigInteger("0");
        BigRat r = new BigRat(n, d);
        assertEquals("4/0", r.toString());
    }

    @Test
    public void testConstructor4 ()
    {
        BigInteger n1 = new BigInteger("1");
        BigInteger d1 = new BigInteger("2");
        BigRat r1 = new BigRat(n1, d1);
        assertEquals("1/2", r1.toString());

        BigInteger n2 = new BigInteger("1");
        BigInteger d2 = new BigInteger("3");
        BigRat r2 = new BigRat(n2, d2);
        assertEquals("1/3", r2.toString());

        BigInteger n3 = new BigInteger("-4");
        BigInteger d3 = new BigInteger("-2");
        BigRat r3 = new BigRat(n3, d3);
        assertEquals("2", r3.toString());

        BigInteger n4 = new BigInteger("6");
        BigInteger d4 = new BigInteger("-8");
        BigRat r4 = new BigRat(n4, d4);
        assertEquals("-3/4", r4.toString());

        BigInteger n5 = new BigInteger("3");
        BigInteger d5 = new BigInteger("-15");
        BigRat r5 = new BigRat(n5, d5);
        assertEquals("-1/5", r5.toString());

        BigInteger n6 = new BigInteger("6400000000000019260817");
        BigInteger d6 = new BigInteger("-6400000000000019260817");
        BigRat r6 = new BigRat(n6, d6);
        assertEquals("-1", r6.toString());

        BigInteger n7 = new BigInteger("6488888888819260817");
        BigInteger d7 = new BigInteger("-6400000000000019260817");
        BigRat r7 = new BigRat(n7, d7);
        assertEquals("-6488888888819260817/6400000000000019260817", r7.toString());

        BigInteger n8 = new BigInteger("1");
        BigInteger d8 = new BigInteger("200000000000019530615");
        BigRat r8 = new BigRat(n8, d8);
        assertEquals("1/200000000000019530615", r8.toString());

        try
        {
            BigInteger n9 = new BigInteger("4");
            BigInteger d9 = new BigInteger("00000");
            new BigRat(n9, d9);
            fail("No exception thrown");
        }
        catch (IllegalArgumentException e)
        {
        }
    }

    @Test
    public void testAdd ()
    {
        BigRat r1 = new BigRat(2, 5);
        BigRat r2 = new BigRat(3, 4);
        assertEquals("23/20", r1.add(r2).toString());

        r1 = new BigRat(1, 7);
        r2 = new BigRat(-1, 5);
        assertEquals("-2/35", r1.add(r2).toString());

        r1 = new BigRat(BigInteger.valueOf(2), BigInteger.valueOf(5));
        r2 = new BigRat(BigInteger.valueOf(3), BigInteger.valueOf(4));
        assertEquals("23/20", r1.add(r2).toString());

        r1 = new BigRat(BigInteger.valueOf(1), BigInteger.valueOf(7));
        r2 = new BigRat(BigInteger.valueOf(-1), BigInteger.valueOf(5));
        assertEquals("-2/35", r1.add(r2).toString());

        r1 = new BigRat(new BigInteger("1"), new BigInteger("8000000000000"));
        r2 = new BigRat(new BigInteger("1"), new BigInteger("8000000000000"));
        assertEquals("1/4000000000000", r1.add(r2).toString());

        r1 = new BigRat(new BigInteger("4"), new BigInteger("8000000000000"));
        r2 = new BigRat(new BigInteger("4"), new BigInteger("8000000000000"));
        assertEquals("1/1000000000000", r1.add(r2).toString());

    }

    @Test
    public void testSub ()
    {
        BigRat r1 = new BigRat(2, 5);
        BigRat r2 = new BigRat(3, 4);
        assertEquals("-7/20", r1.sub(r2).toString());

        r1 = new BigRat(1, 7);
        r2 = new BigRat(-1, 5);
        assertEquals("12/35", r1.sub(r2).toString());

        r1 = new BigRat(BigInteger.valueOf(2), BigInteger.valueOf(5));
        r2 = new BigRat(BigInteger.valueOf(3), BigInteger.valueOf(4));
        assertEquals("-7/20", r1.sub(r2).toString());

        r1 = new BigRat(BigInteger.valueOf(1), BigInteger.valueOf(7));
        r2 = new BigRat(BigInteger.valueOf(-1), BigInteger.valueOf(5));
        assertEquals("12/35", r1.sub(r2).toString());

        r1 = new BigRat(new BigInteger("1"), new BigInteger("2000000000000000"));
        r2 = new BigRat(new BigInteger("2"), new BigInteger("2000000000000000"));
        assertEquals("-1/2000000000000000", r1.sub(r2).toString());

        r1 = new BigRat(new BigInteger("6"), new BigInteger("20000000000000"));
        r2 = new BigRat(new BigInteger("2"), new BigInteger("20000000000000"));
        assertEquals("1/5000000000000", r1.sub(r2).toString());

    }

    @Test
    public void testMul ()
    {
        BigRat r1 = new BigRat(2, 5);
        BigRat r2 = new BigRat(3, 4);
        assertEquals("3/10", r1.mul(r2).toString());

        r1 = new BigRat(1, 7);
        r2 = new BigRat(-1, 5);
        assertEquals("-1/35", r1.mul(r2).toString());

        r1 = new BigRat(BigInteger.valueOf(2), BigInteger.valueOf(5));
        r2 = new BigRat(BigInteger.valueOf(3), BigInteger.valueOf(4));
        assertEquals("3/10", r1.mul(r2).toString());

        r1 = new BigRat(BigInteger.valueOf(1), BigInteger.valueOf(7));
        r2 = new BigRat(BigInteger.valueOf(-1), BigInteger.valueOf(5));
        assertEquals("-1/35", r1.mul(r2).toString());

        r1 = new BigRat(new BigInteger("1"), new BigInteger("2000000000000"));
        r2 = new BigRat(new BigInteger("1"), new BigInteger("2000000000000"));
        assertEquals("1/4000000000000000000000000", r1.mul(r2).toString());

        r1 = new BigRat(new BigInteger("10"), new BigInteger("20000000000000"));
        r2 = new BigRat(new BigInteger("7"), new BigInteger("15000000000000"));
        assertEquals("7/30000000000000000000000000", r1.mul(r2).toString());
    }

    @Test
    public void testDiv ()
    {
        BigRat r1 = new BigRat(2, 5);
        BigRat r2 = new BigRat(3, 4);
        assertEquals("8/15", r1.div(r2).toString());

        r1 = new BigRat(1, 7);
        r2 = new BigRat(-1, 5);
        assertEquals("-5/7", r1.div(r2).toString());

        r1 = new BigRat(BigInteger.valueOf(2), BigInteger.valueOf(5));
        r2 = new BigRat(BigInteger.valueOf(3), BigInteger.valueOf(4));
        assertEquals("8/15", r1.div(r2).toString());

        r1 = new BigRat(BigInteger.valueOf(1), BigInteger.valueOf(7));
        r2 = new BigRat(BigInteger.valueOf(-1), BigInteger.valueOf(5));
        assertEquals("-5/7", r1.div(r2).toString());

        r1 = new BigRat(new BigInteger("1"), new BigInteger("2000000000000"));
        r2 = new BigRat(new BigInteger("1"), new BigInteger("2000000000000"));
        assertEquals("1", r1.div(r2).toString());

        r1 = new BigRat(new BigInteger("6"), new BigInteger("2000000000000"));
        r2 = new BigRat(new BigInteger("2"), new BigInteger("2000000000000"));
        assertEquals("3", r1.div(r2).toString());

        r1 = new BigRat(new BigInteger("1"), new BigInteger("2000000000000"));
        r2 = new BigRat(new BigInteger("2"), new BigInteger("-2000000000000"));
        assertEquals("-1/2", r1.div(r2).toString());

        try
        {
            r1 = new BigRat(3, 4);
            r2 = new BigRat(0);
            r1.div(r2);
            fail("No exception thrown");
        }
        catch (IllegalArgumentException e)
        {
        }

        try
        {
            r1 = new BigRat(BigInteger.valueOf(3), BigInteger.valueOf(4));
            r2 = new BigRat(BigInteger.ZERO);
            r1.div(r2);
            fail("No exception thrown");
        }
        catch (IllegalArgumentException e)
        {
        }

    }

    @Test
    public void testCompareTo ()
    {
        BigRat r1 = new BigRat(3, 4);
        BigRat r2 = new BigRat(6, 8);
        BigRat r3 = new BigRat(1, 2);
        assertEquals(0, r1.compareTo(r2));
        assertTrue(r1.compareTo(r3) > 0);
        assertTrue(r3.compareTo(r1) < 0);

        r1 = new BigRat(BigInteger.valueOf(3), BigInteger.valueOf(4));
        r2 = new BigRat(BigInteger.valueOf(6), BigInteger.valueOf(8));
        r3 = new BigRat(BigInteger.valueOf(1), BigInteger.valueOf(2));
        assertEquals(0, r1.compareTo(r2));
        assertTrue(r1.compareTo(r3) > 0);
        assertTrue(r3.compareTo(r1) < 0);

        r1 = new BigRat(new BigInteger("1"), new BigInteger("123000000000000"));
        r2 = new BigRat(new BigInteger("2"), new BigInteger("246000000000000"));
        r3 = new BigRat(new BigInteger("4"), new BigInteger("223000000000000"));
        assertEquals(0, r1.compareTo(r2));
        assertTrue(r3.compareTo(r1) > 0);
        assertTrue(r2.compareTo(r3) < 0);
    }

    @Test
    public void testBigExample ()
    {
        BigRat r1 = new BigRat(new BigInteger("987876765674565435423544657879080989878678676"),
                new BigInteger("89787676564654675786897988908987786785764654645675"));
        BigRat r2 = r1.div(r1);
        assertEquals("1", r2.toString());
    }

    @Test
    public void testToString ()
    {
        assertEquals("3/4", new BigRat(3, 4).toString());
        assertEquals("3/4", new BigRat(6, 8).toString());
        assertEquals("2", new BigRat(2, 1).toString());
        assertEquals("0", new BigRat(0, 8).toString());
        assertEquals("-3/4", new BigRat(3, -4).toString());
        assertEquals("0", new BigRat(BigInteger.ZERO, new BigInteger("800000000000000000000000000000")).toString());
        assertEquals("-3/4", new BigRat(new BigInteger("300000000000000000000000000000"),
                new BigInteger("-400000000000000000000000000000")).toString());
        assertEquals("3/4", new BigRat(new BigInteger("300000000000000000000000000000"),
                new BigInteger("400000000000000000000000000000")).toString());
        assertEquals("3/4", new BigRat(new BigInteger("600000000000000000000000000000"),
                new BigInteger("800000000000000000000000000000")).toString());
        assertEquals("2", new BigRat(new BigInteger("200000000000000000000000000000"),
                new BigInteger("100000000000000000000000000000")).toString());
    }

    @Test
    public void testGcd ()
    {
        assertEquals(BigInteger.valueOf(2), BigRat.gcd(BigInteger.valueOf(6), BigInteger.valueOf(20)));
        assertEquals(BigInteger.valueOf(1), BigRat.gcd(BigInteger.valueOf(11), BigInteger.valueOf(29)));
        assertEquals(BigInteger.valueOf(10), BigRat.gcd(BigInteger.valueOf(30), BigInteger.valueOf(20)));

        assertEquals(BigInteger.valueOf(3), BigRat.gcd(new BigInteger("3"), new BigInteger("3000000000000")));
        assertEquals(BigInteger.valueOf(1),
                BigRat.gcd(new BigInteger("100000000019260817"), new BigInteger("100000000019530615")));
    }
}
