package loops;

import java.util.Scanner;

/**
 * Contain implementations of the following five public, static methods.
 * 
 * @author Erdi Fan
 *
 */

public class Loops
{
    /**
     * returns true if t occurs as a token within s, and returns false otherwise. Implementation note: Use a Scanner to
     * break s into tokens, and use a searching loop to look for t.
     * 
     * @param s
     * @param t
     * @return
     */
    public static boolean containsToken (String s, String t)
    {
        boolean ans = false;
        Scanner scnr = new Scanner(s);
        while (scnr.hasNext())
        {
            String token = scnr.next();
            ans = ans || token.equals(t);
        }
        return ans;
    }

    /**
     * returns a line from scn that contains t as a token (if such a line exists) and returns the empty string
     * (otherwise). Implementation note: You'll find the containsToken method specified above helpful. This method calls
     * for a searching loop.
     * 
     * @param scn
     * @param t
     * @return
     */
    public static String findLineWithToken (Scanner scn, String t)
    {
        while (scn.hasNextLine())
        {
            String target = scn.nextLine();
            if (containsToken(target, t))
            {
                return target;
            }
        }
        return "";
    }

    /**
     * returns true if s reads the same forwards and backwards, and returns false otherwise. Implementation note: Use a
     * loop that searches for a character in s that doesn't "match up" with an identical character elsewhere in the
     * string. You'll need to determine the appropriate definition of "match up".
     * 
     * @param s
     * @return
     */
    public static boolean isPalindrome (String s)
    {
        boolean ans = true;
        int i = 0;
        while (i < s.length() - 1)
        {
            ans = ans && (s.charAt(i) == s.charAt(s.length() - 1 - i));
            i++;
        }
        return ans;
    }

    /**
     * returns the longest token from scn that is a palindrome (if one exists) or the empty string
     * (otherwise).Implementation note: You'll find the isPalindrome method helpful here. This method calls for an
     * optimization loop.
     * 
     * @param scn
     * @return
     */
    public static String findLongestPalindrome (Scanner scn)
    {
        int max = 0;
        String ans = "";
        while (scn.hasNext())
        {
            String longestSoFar = scn.next();
            if (isPalindrome(longestSoFar))
            {
                if (longestSoFar.length() > max)
                {
                    max = longestSoFar.length();
                    ans = longestSoFar;
                }
            }
        }
        return ans;
    }

    /**
     * finds the line in scn with the most whitespace characters and returns the number of whitespace characters it
     * contains (if scn contains at least one line) or -1 (otherwise). Implementation note: This method calls for an
     * optimization loop, and will be be easy if you first implement a method that counts the amount of whitespace in a
     * string. There is a method Character.isWhitespace() that you'll find useful.
     * 
     * @param scn
     * @return
     */
    public static int findMostWhitespace (Scanner scn)
    {
        int max = -1;

        while (scn.hasNextLine())
        {
            String token = scn.nextLine();
            int currentLength = 0;
            for (int i = 0; i < token.length(); i++)
            {
                char c = token.charAt(i);
                if (Character.isWhitespace(c))
                {
                    currentLength++;
                }
            }

            if (currentLength > max)
            {
                max = currentLength;
            }

        }
        return max;
    }

    public static void main (String[] args)
    {
        // TODO Auto-generated method stub
        System.out.println(isPalindrome("abcdedbba"));
    }

}
