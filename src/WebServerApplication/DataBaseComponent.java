package WebServerApplication;

import CommonDataTypes.*;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static CommonDataTypes.RideState.Ordered;
import static CommonDataTypes.RideState.Unordered;

public class DataBaseComponent {
    private List<PersonalData> personalData;
    private List<WebServerApplication.Ticket> driverRides; //we have only driverRide, because client is immedietally processed, driver my wait
    private List<WebServerApplication.TicketObserver> observers;
    private WebServerApplication.DistanceCalculator distanceCalculator;

    DataBaseComponent()
    {
        this.personalData=new LinkedList<PersonalData>();
        this.driverRides=new ArrayList<WebServerApplication.Ticket>();
        this.distanceCalculator=new WebServerApplication.DistanceCalculator();
        this.observers=new ArrayList<WebServerApplication.TicketObserver>();
    }

    public boolean AddPersonalData(PersonalData pData)
    {
        System.out.println("DataBase component welcomes you");
        if(true==checkIfDataAreInDB(pData))
            return true;
        personalData.add(pData);
        return false;
    }

    public boolean checkIfDataAreInDB(PersonalData pData)
    {
        List<Integer> indexes=new ArrayList<Integer>();
        for(PersonalData p:personalData)
        {
            if(pData.equals(p))
                return true;
        }
        return false;
    }

    public void CreateTicket(Ride ride, PersonalData driver,int port)
    {
        WebServerApplication.TicketObserver observer=new WebServerApplication.TicketObserver();
        WebServerApplication.Ticket a=new WebServerApplication.Ticket(observer,port);
        a.ride=ride;
        a.driverData=driver;
        driverRides.add(a);
        observers.add(observer);

    }

    private void processTicket(int index, Ride ride,PersonalData pData)
    {
        System.out.println("Update");
        switch (ride.state){
            case Ordered:{
                this.driverRides.get(index).ride.state=Ordered;
                this.driverRides.get(index).ride.outputAddress=ride.outputAddress;
                this.driverRides.get(index).ride.phoneClient=ride.phoneClient;
                this.driverRides.get(index).clientData=pData;
                this.driverRides.get(index).observer.setUpdate(Ordered);
                System.out.println("Update");
            }break;
            case Finished: case Canceled:{
                this.driverRides.get(index).ride.state=Unordered;
                this.driverRides.get(index).ride.outputAddress=null;
                this.driverRides.get(index).ride.phoneClient=null;
                this.driverRides.get(index).clientData=null;
                this.driverRides.get(index).observer.unsetUpdate();
            }break;
        }
    }

    public RideDistance getBestFit(Ride ride,PersonalData pData)
    {
        final int noRides=driverRides.size();
        if(noRides==0)
            return null;
        double minimumDistance=10000000000000000000000000000000.0;
        int index=-1;
        double currentDistance;
        for(int i=0;i<noRides;++i)
        {
            try {
                if(this.driverRides.get(i).clientData==null) {
                    if ((currentDistance = this.distanceCalculator.getDistance(ride.inputAddress,
                            this.driverRides.get(i).ride.inputAddress)) < minimumDistance) {
                        minimumDistance = currentDistance;
                        index = i;
                    }
                }
            }catch(Exception e)
            {
                System.out.println(e);
            }
        }
        if(index==-1) {
            System.out.println("nie znaleziono kierowcy");
            return null;
        }
        double distance=0;
        try {
             distance = this.distanceCalculator.getDistance(ride.inputAddress, ride.outputAddress);
        }catch (Exception e){};
        processTicket(index,ride,pData);
    return new RideDistance(this.driverRides.get(index).ride,minimumDistance+distance);
    }

    public int checkIfAnyTicketWasUpdated(){
        for(WebServerApplication.TicketObserver o:observers){
            if(o.wasTicketUpdatd()==true){
                System.out.println("Obserwator portu "+o.port);
                return o.port;
            }
        }
        return -1;
    }

    public RideDistance processClient(Ride ride,PersonalData pData)
    {
        RideDistance driver=getBestFit(ride,pData);
        if(driver==null)
            return null;
        //TODO as this operation returns data for client only, int the future we will add subscription design pattern to inform driver
        return new RideDistance(ride,driver.distance);
    }

}
