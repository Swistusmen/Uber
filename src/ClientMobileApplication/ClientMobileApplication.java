package ClientMobileApplication;

import CommonDataTypes.PersonalData;
import CommonDataTypes.Ride;
import CommonDataTypes.RideState;
import MobileApplication.MobileAppCommunicator;

import java.util.Scanner;

public class ClientMobileApplication extends MobileAppCommunicator implements ClientOperations
{
    public static void main(String[] args) {
        ClientMobileApplication app=new ClientMobileApplication(args[0],45676);
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
    public ClientMobileApplication(String IP, int portNumber) {
        super(IP, portNumber);
        scanner=new Scanner(System.in);
        Options=new ArrayList<String>(){
            "1.Order a ride","2.Exit"
        };
    }

    public void Run(PersonalData personalData, Ride ride)
    {
        this.InserPersonalData(personalData);
        boolean isConnectionSetUp=this.ConnectWithServer(personalData);
        System.out.println(isConnectionSetUp);
        if(isConnectionSetUp==true)
        {
            System.out.println(ride);
            ride.price+=30;
            ride=this.OperateOnRide(ride);
            System.out.println(ride);
            this.Disconnect();
        }
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
}
