package MobileApplication;

import CommonDataTypes.PersonalData;
import CommonDataTypes.Ride;

public interface Operations {
    //boolean Rate();
    public PersonalData SignUp(boolean isClient);
    public PersonalData LandingMenu();
    public Ride CreateARide(boolean isClient, String phoneNumber);
    //public PersonalData SignIn();
    //boolean ReportAProblem();
    //boolean CancelARide();
    //void ShowARide();
}
