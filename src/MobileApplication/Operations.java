package MobileApplication;

import CommonDataTypes.PersonalData;
import CommonDataTypes.Ride;

public interface Operations {
    public PersonalData SignUp(boolean isClient);
    public PersonalData LandingMenu(boolean isClient);
    public Ride CreateARide(boolean isClient, String phoneNumber);
}
