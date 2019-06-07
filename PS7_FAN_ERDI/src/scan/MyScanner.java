package scan;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;

public class MyScanner
{

    private String[] tokens;
    private ArrayList<String> tok;
    private int nextInt;

    // Constructor
    public MyScanner (String str)
    {
        tokens = str.trim().split("\\s+");
        tok = new ArrayList<>(Arrays.asList(tokens));
        if (tok.get(0).equals(""))
        {
            tok = new ArrayList<>();
        }
    }

    /**
     * return true when there is a next token in MyScanner
     * 
     * @return
     */
    public boolean hasNext ()
    {

        return tok.size() > 0;
    }

    /**
     * return next token in MyScanner
     * 
     * @return
     */
    public String next ()
    {
        if (hasNext())
        {
            String next = tok.get(0);
            tok.remove(0);
            return next;
        }
        throw new NoSuchElementException();
    }

    /**
     * return true when there is a next integer in MyScanner
     * 
     * @return
     */
    public boolean hasNextInt ()
    {
        if (hasNext())
        {
            try
            {
                nextInt = Integer.parseInt(tok.get(0));
            }
            catch (NumberFormatException e)
            {
                return false;
            }
            return true;
        }
        return false;
    }

    /**
     * return next integer in MyScanner
     * 
     * @return
     */
    public int nextInt ()
    {
        if (hasNext())
        {
            if (hasNextInt())
            {
                tok.remove(0);
                return nextInt;
            }
            else
                throw new InputMismatchException();
        }
        else
            throw new NoSuchElementException();

    }
}
