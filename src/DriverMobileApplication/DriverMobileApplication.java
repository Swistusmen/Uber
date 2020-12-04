package DriverMobileApplication;

import MobileApplication.MobileAppCommunicator;

import CommonDataTypes.PersonalData;
import CommonDataTypes.Ride;
import CommonDataTypes.RideState;
import CommonDataTypes.DriverData;
import java.util.Scanner;

public class DriverMobileApplication extends MobileAppCommunicator {
    public static void main(String[] args){
        DriverMobileApplication app=new DriverMobileApplication(args[0],45678);
        PersonalData personalData=this.LandingMenu();
        while(personalData!=null)
        {
            final int numberOfOperations=Options.size();
            for(String s:Options){
                System.out.println(s);
            }
            int choice=scanner.nextInt();
            switch(choice){
                case 1:{
                    Ride ride=super.CreateRide(true);
                    app.Run(personalData,ride);
                }break;
                case 2:{
                    break;
                }break;
                 else{
                    break;
                }break;
            }
        }
    }
    //variables
    private Scanner scanner;
    List<String> Options;
    //busines logic methods
    public DriverMobileApplication(String IP, int portNumber){
        super(IP, portNumber);
        scanner=new Scanner(System.in);
    }

    public void Run(PersonalData personalData, Ride ride){
        this.InserPersonalData(driverData);
        boolean isConnectionSetUp=this.ConnectWithServer(driverData);
        if(isConnectionSetUp==true)
        {
            ride=this.OperateOnRide(ride);
            System.out.println(ride);
            ride.price+=30;
            ride=this.OperateOnRide(ride);
            System.out.println(ride);
            this.Disconnect();
        }
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
