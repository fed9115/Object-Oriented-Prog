package cs1410;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.util.ArrayList;
import java.util.Scanner;
import cs1410lib.Dialogs;

public class Generator
{

    public static void main (String[] args)
    {
        int level = firstDialog();
        if (level != -1)
        {
            int length = secondDialog();
            if (length != -1)
            {
                openFile(level, length);
            }
        }
    }

    public static int firstDialog ()
    {
        while (true)
        {
            try
            {
                String indexLevel = Dialogs.showInputDialog("Enter the desired analysis level");
                if (indexLevel == null)
                    return -1;
                int level = Integer.parseInt(indexLevel);
                if (level < 0)
                {
                    Dialogs.showMessageDialog("Negative number entered");
                }
                else
                {
                    return level;

                }
            }
            catch (NumberFormatException e)
            {
                Dialogs.showMessageDialog("Invalid input; please try again");
                continue;
            }
        }
    }

    public static int secondDialog ()
    {

        while (true)
        {
            try
            {
                String indexLength = Dialogs.showInputDialog("Enter the desired length of the output text");
                if (indexLength == null)
                    return -1;
                int length = Integer.parseInt(indexLength);
                if (length < 0)
                {
                    Dialogs.showMessageDialog("Negative number entered");
                }
                else
                {
                    return length;
                }
            }
            catch (NumberFormatException e)
            {
                Dialogs.showMessageDialog("Invalid input; please try again");
            }
        }
    }

    public static void openFile (int level, int length)
    {

        // Obtain the file, report any errors encountered
        while (true)
        {
            // Obtain the file, report any errors encountered
            File inputFile = Dialogs.showOpenFileDialog("Select a text file to be analyzed");
            if (inputFile == null)
                return;

            try
            {
                Scanner scn = new Scanner(inputFile);
                String result = PS5Library.generateText(scn, level, length);
                Dialogs.showMessageDialog(result);
                return;
            }
            catch (FileNotFoundException e)
            {
                Dialogs.showMessageDialog("Input file not found");
            }

        }
    }

}
