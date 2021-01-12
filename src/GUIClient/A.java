package GUIClient;

import ClientMobileApplication.ClientMobileApplication;
import CommonDataTypes.Ride;
import MobileApplication.MobileAppCommunicator;

public class A {
    public static int Switcher;

    public static String Name;
    public static String Surname;
    public static String Account;
    public static String Phone;
    public static String CurrentLocation;
    public static String Destination;
    public static ClientMobileApplication mobile;
    public static Ride ride;

    public static void Connect()
    {
        mobile=new ClientMobileApplication("192.168.0.143",45600);
        mobile.SignUp(Name,Surname,Account,Phone);
    }

    public static void OrderARide()
    {
        mobile.OrderARide(CurrentLocation,Destination);
        ride=mobile.Run();
    }


}
