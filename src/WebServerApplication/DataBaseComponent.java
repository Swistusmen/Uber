package WebServerApplication;

import CommonDataTypes.PersonalData;
import CommonDataTypes.Ride;
import CommonDataTypes.RideDistance;
import CommonDataTypes.RideState;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class DataBaseComponent {
    private List<PersonalData> personalData;
    private List<Ride> driverRides; //we have only driverRide, because client is immedietally processed, driver my wait
    private WebServerApplication.DistanceCalculator distanceCalculator;

    DataBaseComponent()
    {
        this.personalData=new LinkedList<PersonalData>();
        this.driverRides=new ArrayList<Ride>();
        this.distanceCalculator=new WebServerApplication.DistanceCalculator();
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

    public void AddARide(Ride ride)
    {
        driverRides.add(ride);
    }

    public RideDistance getBestFit(Ride ride)
    {
        final int noRides=driverRides.size();
        if(noRides==0)
            return null;
        double minimumDistance=10000000.0;
        int index=0;
        double currentDistance;
        for(int i=0;i<noRides;++i)
        {
            try {
                if ((currentDistance = this.distanceCalculator.getDistance(ride.inputAddress,
                        this.driverRides.get(i).inputAddress)) < minimumDistance) {
                    minimumDistance = currentDistance;
                    index = i;
                }
            }catch(Exception e)
            {
                System.out.println(e);
            }
        }
    return new RideDistance(this.driverRides.get(index),minimumDistance);
    }

    public RideDistance processClient(Ride ride)
    {
        RideDistance driver=getBestFit(ride);
        ride.carNumber=driver.ride.carNumber;
        ride.phoneDriver=driver.ride.phoneDriver;
        ride.state= RideState.Ordered;
        //TODO as this operation returns data for client only, int the future we will add subscription design pattern to inform driver
        return new RideDistance(ride,driver.distance);
    }

}
