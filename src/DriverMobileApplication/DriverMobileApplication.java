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
        DriverMobileApplication app=new DriverMobileApplication(args[0],45678);
        PersonalData personalData=app.LandingMenu();
        while(personalData!=null)
        {
            final int numberOfOperations=app.Options.size();
            for(String s:app.Options){
                System.out.println(s);
            }
            int choice=app.scanner.nextInt();
            switch(choice){
                case 0:{
                    Ride ride=app.CreateARide(true,personalData.getTelephoneNumber());
                    app.Run(personalData,ride);
                }break;
                case 1:{
                    break;
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
            ride=this.OperateOnRide(ride);
            System.out.println(ride);
            ride.price+=30;
            ride=this.OperateOnRide(ride);
            System.out.println(ride);
            this.Disconnect();
        }
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
