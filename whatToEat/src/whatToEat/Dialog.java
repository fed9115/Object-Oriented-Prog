package whatToEat;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import cs1410lib.Dialogs;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.DriverManager;

public class Dialog
{
    private static ArrayList<String> vegetable = new ArrayList<>();
    private static ArrayList<String> meat = new ArrayList<>();
    private static String line;

    public static void main (String[] args) throws FileNotFoundException
    {
        try
        {
            File file1 = Dialogs.showOpenFileDialog("Select a menu file, only vegetable is valid 请选择素菜菜单");

            if (file1 == null)
                return;

            InputStreamReader reader = new InputStreamReader(new FileInputStream(file1), "utf-8");
            BufferedReader br = new BufferedReader(reader);
            while ((line = br.readLine()) != null)
            {
                vegetable.add(line);
            }
        }
        catch (Exception e)
        {
        }

        try
        {
            File file2 = Dialogs.showOpenFileDialog("Select a menu file, only meat is valid 请选择荤菜菜单");

            if (file2 == null)
                return;

            InputStreamReader reader = new InputStreamReader(new FileInputStream(file2), "utf-8");
            BufferedReader br = new BufferedReader(reader);
            while ((line = br.readLine()) != null)
            {
                meat.add(line);
            }
        }
        catch (Exception e)
        {
        }

        Dialogs.showMessageDialog("肥茜今天的午餐是" + vegetable.get(new Random().nextInt(vegetable.size())) + " "
                + meat.get(new Random().nextInt(meat.size())));
    }
}
