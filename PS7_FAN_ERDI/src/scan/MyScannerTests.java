package scan;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import org.junit.Test;

public class MyScannerTests
{

    // test MyScanner has next token and return the next token in MyScanner
    @Test
    public void testNext ()
    {
        MyScanner scn = new MyScanner("dssdv sdfsde 12 123");

        assertTrue(scn.hasNext());
        assertEquals("dssdv", scn.next());

        assertTrue(scn.hasNext());
        assertEquals("sdfsde", scn.next());

        assertTrue(scn.hasNext());
        assertEquals("12", scn.next());

        assertTrue(scn.hasNext());
        assertEquals("123", scn.next());

        assertFalse(scn.hasNext());
    }

    // test MyScanner has next integer and return the next token in MyScanner
    @Test
    public void testNextInt ()
    {
        MyScanner scn = new MyScanner("dssdv sdfsde 12 123");

        assertTrue(scn.hasNext());
        assertFalse(scn.hasNextInt());

        scn.next();
        scn.next();

        assertTrue(scn.hasNextInt());
        assertEquals(12, scn.nextInt());

        assertTrue(scn.hasNextInt());
        assertEquals(123, scn.nextInt());

        assertFalse(scn.hasNextInt());
    }
    
    @Test
    public void testNextInt2 ()
    {
        MyScanner scn = new MyScanner(" dssdv sdfsde 12 123 ");

        assertTrue(scn.hasNext());
        assertFalse(scn.hasNextInt());

        scn.next();
        scn.next();

        assertTrue(scn.hasNextInt());
        assertEquals(12, scn.nextInt());

        assertTrue(scn.hasNextInt());
        assertEquals(123, scn.nextInt());

        assertFalse(scn.hasNextInt());
    }
    
    @Test
    public void testNextInt3 ()
    {
        MyScanner scn = new MyScanner("   dssdv sdfsde 12 123  ");

        assertTrue(scn.hasNext());
        assertFalse(scn.hasNextInt());

        scn.next();
        scn.next();

        assertTrue(scn.hasNextInt());
        assertEquals(12, scn.nextInt());

        assertTrue(scn.hasNextInt());
        assertEquals(123, scn.nextInt());

        assertFalse(scn.hasNextInt());
    }
    
    @Test
    public void testStringInt ()
    {
        MyScanner scn = new MyScanner("dssdv sdfsde12123");

        assertTrue(scn.hasNext());
        assertFalse(scn.hasNextInt());

        assertEquals("dssdv", scn.next());
        
        assertTrue(scn.hasNext());
        assertFalse(scn.hasNextInt());
        
        assertEquals("sdfsde12123", scn.next());
        
        assertFalse(scn.hasNext());
                
    }
    
    @Test
    public void testWhiteSpace1()
    {
        MyScanner scn = new MyScanner("   12    ");
        
        assertTrue(scn.hasNext());
        assertTrue(scn.hasNextInt());
        
        assertEquals(12, scn.nextInt());
        
        assertFalse(scn.hasNext());
        assertFalse(scn.hasNextInt());

    }
    
    @Test
    public void testWhiteSpace2()
    {
        MyScanner scn = new MyScanner(" 12 ");
        
        assertTrue(scn.hasNext());
        assertTrue(scn.hasNextInt());
        
        assertEquals(12, scn.nextInt());
        
        assertFalse(scn.hasNext());
        assertFalse(scn.hasNextInt());

    }
    
    @Test
    public void testWhiteSpace3()
    {
        MyScanner scn = new MyScanner("   12  ");
        
        assertTrue(scn.hasNext());
        assertTrue(scn.hasNextInt());
        
        assertEquals(12, scn.nextInt());
        
        assertFalse(scn.hasNext());
        assertFalse(scn.hasNextInt());

    }
    
    @Test
    public void testWhiteSpace4()
    {
        MyScanner scn = new MyScanner("   1 2  ");
        
        assertTrue(scn.hasNext());
        assertTrue(scn.hasNextInt());
        
        assertEquals(1, scn.nextInt());
        
        assertTrue(scn.hasNext());
        assertTrue(scn.hasNextInt());
        
        assertEquals(2, scn.nextInt());
        
        assertFalse(scn.hasNext());
        assertFalse(scn.hasNextInt());

    }
    
    @Test
    public void testWhiteSpace5()
    {
        MyScanner scn = new MyScanner("   2.0  ");
        
        assertFalse(scn.hasNextInt());

    }
    
    @Test
    public void testWhiteSpace6()
    {
        MyScanner scn = new MyScanner("2.0");
        
        assertFalse(scn.hasNextInt());

    }

    // test input mismatch exception
    @Test(expected = InputMismatchException.class)
    public void testInputMismatchException ()
    {
        MyScanner scn = new MyScanner("afsg");
        scn.nextInt();
    }

    // test no such element exception
    @Test(expected = NoSuchElementException.class)
    public void testNoSuchElementExcetion ()
    {
        MyScanner scn = new MyScanner("   ");
        scn.next();
    }

    // test no such element exception
    @Test(expected = NoSuchElementException.class)
    public void testNoSuchElementExcetion2 ()
    {
        MyScanner scn = new MyScanner("");
        scn.next();
    }
    
    // test no such element exception
    @Test(expected = NoSuchElementException.class)
    public void testNoSuchElementExcetion3 ()
    {
        MyScanner scn = new MyScanner(" ");
        scn.next();
    }

    // test no such element exception
    @Test(expected = NoSuchElementException.class)
    public void testNoSuchElementExcetion4 ()
    {
        MyScanner scn = new MyScanner("");
        scn.nextInt();
    }
    
    // test no such element exception
    @Test(expected = NoSuchElementException.class)
    public void testNoSuchElementExcetion5 ()
    {
        MyScanner scn = new MyScanner("   ");
        scn.nextInt();
    }
    
    // test no such element exception
    @Test(expected = NoSuchElementException.class)
    public void testNoSuchElementExcetion6 ()
    {
        MyScanner scn = new MyScanner(" ");
        scn.nextInt();
    }
}
