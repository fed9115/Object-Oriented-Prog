package cs1410;

import java.util.HashSet;

/**
 * Represents a variety of information about a geocache. A geocache has a title, an owner, a difficulty rating, a
 * terrain rating, a GC code, a latitude, and a longitude.
 */
public class Cache
{

    /**
     * Creates a Cache from a string that consists of these seven cache attributes: the GC code, the title, the owner,
     * the difficulty rating, the terrain rating, the latitude, and the longitude, in that order, separated by single
     * TAB ('\t') characters.
     * 
     * If any of the following problems are present, throws an IllegalArgumentException:
     * <ul>
     * <li>Fewer than seven attributes</li>
     * <li>More than seven attributes</li>
     * <li>A GC code that is anything other than "GC" followed by one or more upper-case letters and/or digits</li>
     * <li>A difficulty or terrain rating that parses to anything other than the doubles 1, 1.5, 2, 2.5, 3, 3.5, 4, 4.5,
     * or 5.</li>
     * <li>A title, owner, latitude, or longitude that consists only of white space</li>
     */

    private String gcCode;
    private String title;
    private String owner;
    private double difficulty;
    private double terrain;
    private String latitude;
    private String longitude;

    public Cache (String attributes)
    {
        // Assign different attributes from the parameter
        String[] attr = attributes.split("\t");

        // Throw exception when it is not seven attributes
        if (attr.length != 7)
            throw new IllegalArgumentException();

        gcCode = attr[0];

        // Throw exception when GC code is not in the correct format
        if (!gcCode.substring(0, 2).equals("GC"))
            throw new IllegalArgumentException();
        String str = gcCode.substring(2);
        for (int i = 0; i < str.length(); i++)
        {
            if (!(Character.isDigit(str.charAt(i)) || Character.isUpperCase(str.charAt(i))))
            {
                throw new IllegalArgumentException();
            }
        }

        title = attr[1];
        owner = attr[2];

        try
        {
            difficulty = Double.parseDouble(attr[3]);
            terrain = Double.parseDouble(attr[4]);
        }
        // Throw exception if difficulty and/or terrain cannot be parsed into double type
        catch (NumberFormatException e)
        {
            throw new IllegalArgumentException();
        }

        // Throw exception when difficulty and terrain is not in the correct format
        HashSet<Double> candidates = new HashSet<>();
        candidates.add(1.0);
        candidates.add(1.5);
        candidates.add(2.0);
        candidates.add(2.5);
        candidates.add(3.0);
        candidates.add(3.5);
        candidates.add(4.0);
        candidates.add(4.5);
        candidates.add(5.0);
        Double diff = difficulty;
        Double terr = terrain;
        if (!candidates.contains(diff) || !candidates.contains(terr))
        {
            throw new IllegalArgumentException();
        }

        latitude = attr[5];
        longitude = attr[6];

        // Throw exception if title, owner, latitude, or longitude that consists only of white space
        if (title.trim().isEmpty() || owner.trim().isEmpty() || latitude.trim().isEmpty() || longitude.trim().isEmpty())
        {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Converts this cache to a string
     */
    public String toString ()
    {
        return getTitle() + " by " + getOwner();
    }

    /**
     * Returns the owner of this cache
     */
    public String getOwner ()
    {
        return owner;
    }

    /**
     * Returns the title of this cache
     */
    public String getTitle ()
    {
        return title;
    }

    /**
     * Returns the difficulty rating of this cache
     */
    public double getDifficulty ()
    {
        return difficulty;
    }

    /**
     * Returns the terrain rating of this cache
     */
    public double getTerrain ()
    {
        return terrain;
    }

    /**
     * Returns the GC code of this cache
     */
    public String getGcCode ()
    {
        return gcCode;
    }

    /**
     * Returns the latitude of this cache
     */
    public String getLatitude ()
    {
        return latitude;
    }

    /**
     * Returns the longitude of this cache
     */
    public String getLongitude ()
    {
        return longitude;
    }
}
