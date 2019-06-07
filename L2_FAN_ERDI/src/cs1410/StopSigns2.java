package cs1410;

public class StopSigns2
{

    public static void main (String[] args)
    {
        // TODO Auto-generated method stub
        upper();
        bottom();
        upper();
        middle();
        bottom();
        upper();
        bottom();
        upper();
        middle();
        bottom();
        upper();
        bottom();
    }
    
    public static void upper() {
        System.out.println("  ______  ");
        System.out.println(" /      \\ ");
        System.out.println("/        \\");
    }

    
    public static void middle() {       
        System.out.println("|  STOP  |");
    }
    
    public static void bottom() {
        System.out.println("\\        /");
        System.out.println(" \\______/ ");
        System.out.println();
    }
}
