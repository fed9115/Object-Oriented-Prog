package labB;

import static org.junit.Assert.*;
import org.junit.Test;
import java.util.Scanner;

public class MyLibraryTest
{

    @Test
    public void testCountTokens ()
    {
        Scanner scnr1 = new Scanner("this is a test");
        assertEquals(4, MyLibrary.countTokens(scnr1));
        
     }

    @Test
    public void testChangeCase ()
    {
        assertEquals("ickslay", MyLibrary.changeCase("ickslay"));
        assertEquals("ICKSLAY", MyLibrary.changeCase("Ickslay"));
    }

    @Test
    public void testFactorial ()
    {
        assertEquals(1,MyLibrary.factorial(1));
        assertEquals(6,MyLibrary.factorial(3));
    }

}
