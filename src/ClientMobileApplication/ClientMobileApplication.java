package ClientMobileApplication;

import CommonDataTypes.PersonalData;
import CommonDataTypes.Ride;
import CommonDataTypes.RideState;
import MobileApplication.MobileAppCommunicator;

import java.util.Scanner;

public class ClientMobileApplication extends MobileAppCommunicator {
    private Scanner scanner;

    public static void main(String[] args) {
        ClientMobileApplication app=new ClientMobileApplication();
        app.Run();
    }

    public ClientMobileApplication(String IP, int portNumber) {
        super(IP, portNumber);
        scanner=new Scanner(System.in);
    }

    public void Run()
    {
        PersonalData personalData=new PersonalData();
        personalData.setName("Michal");
        personalData.setSurname("Switala");
        personalData.setAccountNumber("1234");
        personalData.setClient(true);
        personalData.setRating(0.0f);
        personalData.setTelephoneNumber("789");
        personalData.setWantToSignUp(true); //because it's first use

        this.InserPersonalData(personalData);
        boolean isConnectionSetUp=this.ConnectWithServer(personalData);
        if(isConnectionSetUp==true)
        {
            Ride ride=new Ride();
            ride.price=100;
            ride.phoneClient=personalData.getTelephoneNumber();
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
