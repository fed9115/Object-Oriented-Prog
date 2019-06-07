package cs1410;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.TreeSet;

/**
 * A CacheList is a collection of Cache objects together with these six constraints:
 * 
 * <ol>
 * <li>A title constraint</li>
 * <li>An owner constraint</li>
 * <li>A minimum difficulty constraint</li>
 * <li>A maximum difficulty constraint</li>
 * <li>A minimum terrain constraint</li>
 * <li>A maximum terrain constraint</li>
 * </ol>
 */
public class CacheList
{
    // The caches being managed by this CacheList. They are arranged in
    // ascending order according to cache title.
    private ArrayList<Cache> allCaches;

    private String title;
    private String owner;
    private double minDiff;
    private double maxDiff;
    private double minTerr;
    private double maxTerr;

    /**
     * Creates a CacheList from the specified Scanner. Each line of the Scanner should contain the description of a
     * cache in a format suitable for consumption by the Cache constructor. The resulting CacheList should contain one
     * Cache object corresponding to each line of the Scanner.
     * 
     * Sets the initial value of the title and owner constraints to the empty string, sets the minimum difficulty and
     * terrain constraints to 1.0, and sets the maximum difficulty and terrain constraints to 5.0.
     * 
     * Throws an IOException if the Scanner throws an IOException, or an IllegalArgumentException if any of the
     * individual lines are not appropriate for the Cache constructor.
     * 
     * When an IllegalArgumentException e is thrown, e.getMessage() is the number of the line that was being read when
     * the error that triggered the exception was encountered. Lines are numbered beginning with 1.
     */
    public CacheList (Scanner caches) throws IOException
    {
        // TODO: Complete this implementation
        allCaches = new ArrayList<Cache>();

        int line = 1;
        while (caches.hasNextLine())
        {
            try
            {
                Cache c = new Cache(caches.nextLine());
                allCaches.add(c);
            }
            catch (IllegalArgumentException e)
            {
                throw new IllegalArgumentException("Error happends on line" + line);
            }
            line++;
        }

        // Set the initial values
        title = "";
        owner = "";
        minDiff = 1.0;
        maxDiff = 5.0;
        minTerr = 1.0;
        maxTerr = 5.0;

        // Sort the list of caches
        Collections.sort(allCaches, (c1, c2) -> c1.getTitle().compareToIgnoreCase(c2.getTitle()));
    }

    /**
     * Sets the title constraint to the specified value.
     */
    public void setTitleConstraint (String title)
    {
        this.title = title;
    }

    /**
     * Sets the owner constraint to the specified value.
     */
    public void setOwnerConstraint (String owner)
    {
        this.owner = owner;
    }

    /**
     * Sets the minimum and maximum difficulty constraints to the specified values.
     */
    public void setDifficultyConstraints (double min, double max)
    {
        this.minDiff = min;
        this.maxDiff = max;
    }

    /**
     * Sets the minimum and maximum terrain constraints to the specified values.
     */
    public void setTerrainConstraints (double min, double max)
    {
        this.minTerr = min;
        this.maxTerr = max;
    }

    /**
     * Returns a list that contains each cache c from the CacheList so long as c's title contains the title constraint
     * as a substring, c's owner equals the owner constraint (unless the owner constraint is empty), c's difficulty
     * rating is between the minimum and maximum difficulties (inclusive), and c's terrain rating is between the minimum
     * and maximum terrains (inclusive). Both the title constraint and the owner constraint are case insensitive.
     * 
     * The returned list is arranged in ascending order by cache title.
     */
    public ArrayList<Cache> select ()
    {
        ArrayList<Cache> caches = new ArrayList<Cache>();
        for (int c = 0; c < allCaches.size(); c++)
        {
            if (allCaches.get(c).getTitle().indexOf(title) >= 0)
            {
                if (allCaches.get(c).getOwner().equals(owner) || owner.equals(""))
                {
                    if (allCaches.get(c).getDifficulty() >= minDiff && allCaches.get(c).getDifficulty() <= maxDiff)
                    {
                        if (allCaches.get(c).getTerrain() >= minTerr && allCaches.get(c).getTerrain() <= maxTerr)
                        {
                            caches.add(allCaches.get(c));
                        }
                    }
                }
            }
        }
        return caches;
    }

    /**
     * Returns a list containing all the owners of all of the Cache objects in this CacheList. There are no duplicates.
     * The list is arranged in ascending order.
     */
    public ArrayList<String> getOwners ()
    {
        ArrayList<String> owners = new ArrayList<String>();
        TreeSet<String> owner = new TreeSet<>();
        for (int i = 0; i < allCaches.size(); i++)
        {
            owner.add(allCaches.get(i).getOwner());
        }
        for (String o : owner)
        {
            owners.add(o);
        }

        // Sort the list of owners
        Collections.sort(owners, (s1, s2) -> s1.compareToIgnoreCase(s2));
        return owners;
    }
}
