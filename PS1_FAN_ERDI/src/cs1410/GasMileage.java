package cs1410;

import cs1410lib.Dialogs;

/**
 * Show some data about the gas and cost a car owner should know when s/he fills up the tank
 * 
 * @author Erdi Fan
 *
 */
public class GasMileage
{
    /**
     * Calculates and displays 4 data based on input from user
     */
    public static void main (String[] args)
    {
        String car = showInputDialog("A type of car");
        int miles = Integer
                .parseInt(showInputDialog("The number of miles driven since the last time gas was purchased"));
        double price = Double.parseDouble(showInputDialog("The price in dollars of a gallon of gasoline"));
        double gallon = Double
                .parseDouble(showInputDialog("The number of gallons currently required to fill the car's tank"));

        String costToFillTank = String.format("%.2f", price * gallon);
        String milesPerGallonSinceLastFillUp = String.format("%.2f", miles / gallon);
        String gasCostPerMileSinceLastFillUp = String.format("%.2f", price / (miles / gallon));

        Dialogs.showMessageDialog(car + "\nCost to fill tank: $" + costToFillTank
                + "\nMiles per gallon since last fill-up: " + milesPerGallonSinceLastFillUp
                + "\nGas cost per mile since last fill-up: " + gasCostPerMileSinceLastFillUp);
        System.out.println(car + "\nCost to fill tank: $" + costToFillTank + "\nMiles per gallon since last fill-up: "
                + milesPerGallonSinceLastFillUp + "\nGas cost per mile since last fill-up: "
                + gasCostPerMileSinceLastFillUp);
    }

    public static String showInputDialog (String message)
    {
        String input = Dialogs.showInputDialog(message);
        return input;
    }
}
