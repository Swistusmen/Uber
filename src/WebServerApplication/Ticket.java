package WebServerApplication;

import CommonDataTypes.DriverData;
import CommonDataTypes.PersonalData;
import CommonDataTypes.Ride;

public class Ticket  { //DESIGN PATTERN -Mediator
    public PersonalData driverData;
    public PersonalData clientData=null;
    public Ride ride;
    public int port;
    public WebServerApplication.TicketObserver observer;

    public Ticket(WebServerApplication.TicketObserver observer, int port)
    {
        this.port=port;
        this.observer=observer;
        observer.port=port;
    }
}
