package ClientMobileApplication;

import CommonDataTypes.PersonalData;
import CommonDataTypes.Ride;
import CommonDataTypes.RideState;
import MobileApplication.MobileAppCommunicator;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ClientMobileApplication extends MobileAppCommunicator
{
    PersonalData client;
    Ride ridee;

    public static void main(String[] args) {
        ClientMobileApplication app=new ClientMobileApplication(args[0],45600);
        PersonalData personalData=app.LandingMenu(true);
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
                    personalData.setWantToSignUp(false);
                }break;
                case 1:{
                    return;
                }
            }
        }
    }
    public List<String> Options;

    //busines logic methods
    public ClientMobileApplication(String IP, int portNumber) {
        super(IP, portNumber);
        Options=new ArrayList<String>(2);
        Options.add("0.Order a ride");
        Options.add("1.Exit");
    }

    public ClientMobileApplication(String IP,int portNumber, int c)
    {
        super(IP, portNumber);
        Options=new ArrayList<String>(2);
        Options.add("0.Order a ride");
        Options.add("1.Exit");
    }

    public Ride Run(PersonalData personalData, Ride ride)
    {
        this.InserPersonalData(personalData);
        boolean isConnectionSetUp=this.ConnectWithServer(personalData);
        System.out.println(isConnectionSetUp);
        if(isConnectionSetUp==true)
        {
            this.SentRide(ride);
            ride=this.ReadRide();
            System.out.println(ride);
            this.Disconnect();
        }
        return ride;
    }
    //test data
    public PersonalData testData()
    {
        PersonalData personalData=new PersonalData();
        personalData.setName("Michal");
        personalData.setSurname("Switala");
        personalData.setAccountNumber("1234");
        personalData.setClient(true);
        personalData.setRating(0.0f);
        personalData.setTelephoneNumber("789");
        personalData.setWantToSignUp(true); //because it's first use
        return personalData;
    }

    public Ride testRide()
    {
        Ride ride=new Ride();
        ride.price=100;
        ride.phoneClient="123456";
        ride.state= RideState.Unordered;
        ride.inputAddress="Czarnowiejska";
        ride.outputAddress="Miodowa";
        return ride;
    }

    public void OrderARide(String Current, String destination)
    {
        ridee=new Ride();
        ridee.inputAddress=Current;
        ridee.outputAddress=destination;
        ridee.state=RideState.Unordered;
        ridee.phoneClient=client.getTelephoneNumber();
        ridee.printRide();
    }

    public void SignUp(String name, String surname, String account, String phone)
    {
        client=new PersonalData();
        client.setName(name);
        client.setSurname(surname);
        client.setAccountNumber(account);
        client.setTelephoneNumber(phone);
        client.setWantToSignUp(true);
    }

    public Ride Run()
    {
        boolean isConnectionSetUp=this.ConnectWithServer(client);
        System.out.println("Connected");
        System.out.println(isConnectionSetUp);
        if(isConnectionSetUp==true)
        {
            this.SentRide(ridee);
            ridee=this.ReadRide();
            System.out.println(ridee);
            this.Disconnect();
        }
        return ridee;
    }
}
