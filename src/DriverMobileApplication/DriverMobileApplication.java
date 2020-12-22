package DriverMobileApplication;

import MobileApplication.MobileAppCommunicator;

import CommonDataTypes.PersonalData;
import CommonDataTypes.Ride;
import CommonDataTypes.RideState;
import CommonDataTypes.DriverData;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DriverMobileApplication extends MobileAppCommunicator {
    public static void main(String[] args){
        System.out.println("Welcome in Uber Application!");
        System.out.println("Please wait for connection");
        DriverMobileApplication app=new DriverMobileApplication(args[0],45600);
        PersonalData personalData=app.LandingMenu(false);
        Ride ride=null;
        while(personalData!=null)
        {
            final int numberOfOperations=app.Options.size();
            for(String s:app.Options){
                System.out.println(s);
            }
            int choice=app.scanner.nextInt();
            switch(choice){
                case 0:{
                    if(ride==null) {
                        ride = app.CreateARide(false, personalData.getTelephoneNumber());
                    }
                    ride=app.Run(personalData,ride);
                }break;
                case 1:{
                    return;
                }
            }
        }
    }
    //variables
    public List<String> Options;

    //busines logic methods
    public DriverMobileApplication(String IP, int portNumber){
        super(IP, portNumber);

        Options=new ArrayList<String>(2);
        Options.add("0.Order a ride");
        Options.add("1.Exit");
    }

    public Ride Run(PersonalData personalData, Ride ride){
        this.InserPersonalData(personalData);
        boolean isConnectionSetUp=this.ConnectWithServer(personalData);
        if(isConnectionSetUp==true)
        {
            SentRide(ride);
            this.Disconnect();
            this.WaitForConnection();
            ride=this.ReadRide();
            try {
                Thread.sleep(10000);
            }catch(Exception e){};
            ride.state=RideState.Finished;
            this.SentRide(ride);
        }
        ride.inputAddress=ride.outputAddress;
        return ride;
    }

    public PersonalData testPersonalData()
    {
        DriverData driverData=new DriverData();
        driverData.setName("Rafał");
        driverData.setSurname("Widziszewski");
        driverData.setAccountNumber("9992");
        driverData.setClient(false);
        driverData.setRating(0.0f);
        driverData.setTelephoneNumber("798");
        driverData.setWantToSignUp(true);

        driverData.setCarLicenseID("673A2");
        driverData.setLicenseID("51627");
        return driverData;
    }

    public Ride testRide()
    {
        Ride ride=new Ride();
        ride.price=100;
        ride.phoneClient="1234567";
        ride.state= RideState.Unordered;
        ride.inputAddress="Czarnowiejska";
        ride.outputAddress="Miodowa";
        return ride;
    }

}
