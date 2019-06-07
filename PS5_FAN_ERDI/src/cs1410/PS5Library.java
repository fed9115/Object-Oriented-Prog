package cs1410;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Scanner;

/**
 * A library of methods for implementing the random text generation algorithm described in PS5, Fall 2018.
 * 
 * @author Erdi Fan and Joe Zachary
 */
public class PS5Library
{
    /**
     * Demonstrates the use of the generateText method.
     */
    public static void main (String[] args) throws IOException
    {
        // You won't need to use this feature in PS5, but this shows how to include a resource (in this
        // case a text file) as part of a project. I created a package called "books", then put two
        // text files into the package. I was then able to open one of those files as shown below. When
        // I export the project, the resources go along with it.
        try (InputStream book = PS5Library.class.getResourceAsStream("/books/PrideAndPrejudice.txt");
                Scanner input = new Scanner(book))
        {
            System.out.println(generateText(input, 6, 100));
        }
    }

    /**
     * Uses all the text in the input to generate and return random text with the specified level and length, using the
     * algorithm described in PS5, CS 1410, Fall 2018.
     * 
     * @throws IllegalArgumentException if level < 0, or length < 0, or there are less than level+1 chars in the input.
     */
    public static String generateText (Scanner input, int level, int length)
    {
        // Validate the parameters
        if (level < 0 || length < 0)
        {
            throw new IllegalArgumentException();
        }

        // Grab all the text from the Scanner and make sure it is long enough.
        String text = scannerToString(input);
        if (level >= text.length())
        {
            throw new IllegalArgumentException();
        }

        // Create a random number generator to pass to the methods that make random choices
        Random rand = new Random();

        // Get the initial pattern.
        String pattern = chooseSubstring(text, level, rand);

        // Build up the final result one character at a time. We use a StringBuilder because
        // it is more efficient than using a String when doing long sequences of appends.
        StringBuilder result = new StringBuilder();
        while (result.length() < length)
        {
            try
            {
                // Pick at random a character that follows the pattern in the text and append it
                // to the result. If there is no such character (which can happen if the pattern
                // occurs only once, at the very end of text), the method we're calling will throw
                // a NoSuchElementException, which is caught below.
                char newChar = pickCharThatFollowsPattern(text, pattern, rand);
                result.append(newChar);

                // Update the pattern by removing its first character and adding on the new
                // character. The length of the pattern remains the same. If the pattern is
                // the empty string, though, it never changes.)
                if (pattern.length() > 0)
                {
                    pattern = pattern.substring(1) + newChar;
                }
            }
            catch (NoSuchElementException e)
            {
                // It is possible to get stuck if the pattern occurs only once in the text and
                // that occurrence is at the very end of the text. In this case, we pick a new
                // seed and keep going.
                pattern = chooseSubstring(text, level, rand);
            }
        }

        // Return the string we've accumulated.
        return result.toString();
    }

    /**
     * takes a Scanner as its parameter and returns a String. The returned string consists of all the characters in the
     * scanner in their original order, including the newlines.
     * 
     * @param scnr
     * @return
     */
    public static String scannerToString (Scanner scnr)
    {
        StringBuilder sb = new StringBuilder();
        while (scnr.hasNextLine())
        {
            sb.append(scnr.nextLine().toString() + "\n");
        }
        return sb.toString();
    }

    /**
     * use the random number generator to return a randomly chosen substring of text that has the specified length. If
     * length is either negative or greater than the length of text, the method should throw an
     * IllegalArgumentException.
     * 
     * @param text
     * @param length
     * @param rd
     * @return
     */
    public static String chooseSubstring (String text, int length, Random rd)
    {
        if (length < 0 || length > text.length())
        {
            throw new IllegalArgumentException();
        }
        int randomNum = rd.nextInt(text.length() - length + 1);
        return text.substring(randomNum, randomNum + length);
    }

    /**
     * The returned list should contain the character that follows each non-tail occurrence of the pattern in the text.
     * (A non-tail occurrence of the pattern is one that is not at the very end of the text.) The length of the list
     * must be the same as the number of non-tail occurrences of the pattern. The character stored at index n of the
     * list must be the character that followed the nth non-tail occurrence of the pattern.
     * 
     * @param text
     * @param pattern
     * @return
     */
    public static ArrayList<Character> getCharsThatFollowPattern (String text, String pattern)
    {
        ArrayList<Character> result = new ArrayList<>();
        int indexSoFar = 0;
        while (indexSoFar < text.length())
        {
            indexSoFar = text.indexOf(pattern, indexSoFar);
            if (indexSoFar != -1)
            {
                int candidate = indexSoFar + pattern.length();
                if (candidate < text.length())
                {
                    char ans = text.charAt(candidate);
                    result.add(ans);
                }
            }
            else
            {
                break;
            }
            indexSoFar++;
        }
        return result;
    }

    /**
     * It should randomly choose a non-tail occurrence of the pattern in the text, returning the character that
     * immediately follows that occurrence of the pattern. If there are no non-tail occurrences of the pattern in the
     * text, the method should throw a NoSuchElementException
     * 
     * @param text
     * @param pattern
     * @param ran
     * @return
     */
    public static char pickCharThatFollowsPattern (String text, String pattern, Random ran)
    {
        if (!getCharsThatFollowPattern(text, pattern).isEmpty())
        {
            return getCharsThatFollowPattern(text, pattern)
                    .get(ran.nextInt(getCharsThatFollowPattern(text, pattern).size()));
        }
        else
        {
            throw new NoSuchElementException();
        }
    }
}
