package DriverMobileApplication;

import MobileApplication.MobileAppCommunicator;

import CommonDataTypes.PersonalData;
import CommonDataTypes.Ride;
import CommonDataTypes.RideState;
import CommonDataTypes.DriverData;
import java.util.Scanner;

public class DriverMobileApplication extends MobileAppCommunicator {
    private Scanner scanner;

    public static void main(String[] args){

        DriverMobileApplication app=new DriverMobileApplication(args[0],45678);
        app.Run();
    }

    public DriverMobileApplication(String IP, int portNumber){
        super(IP, portNumber);
        scanner=new Scanner(System.in);
    }

    public void Run(){

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


        this.InserPersonalData(driverData);
        boolean isConnectionSetUp=this.ConnectWithServer(driverData);
        if(isConnectionSetUp==true)
        {
            Ride ride=new Ride();
            ride.price=100;
            ride.phoneDriver=driverData.getTelephoneNumber();
            ride.state= RideState.Unordered;
            ride.inputAddress="Czarnowiejska";
            ride.outputAddress="Miodowa";
            ride=this.OperateOnRide(ride);
            System.out.println(ride);
            ride.price+=30;
            ride=this.OperateOnRide(ride);
            System.out.println(ride);
            this.Disconnect();
        }


    }

}
