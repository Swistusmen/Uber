package WebServerApplication;

import CommonDataTypes.PersonalData;
import CommonDataTypes.Ride;
import CommonDataTypes.RideDistance;
import CommonDataTypes.TransactionData;

public class Server extends WebServerApplication.ServerCommunicator
{
    public static void main(String[] args) {
        Server server=new Server(args[0],45600);
        server.Run();
    }

    public void Run()
    {
        while(true)
        {
            int port=ConnectClients();
            PersonalData pData=null;
            while(pData==null) {
                pData = this.LookForConnection(port);
            }
            boolean shouldIDisconnect=true;
            if(pData.isWantToSignUp()==true) //tested
            {
                System.out.println("Want to sign up");
                shouldIDisconnect=DB.AddPersonalData(pData);
            }
            else{
                System.out.println("Already in base");
                shouldIDisconnect=!DB.checkIfDataAreInDB(pData);
            }
            this.SentConnectionConfirmation(!shouldIDisconnect);
            if(shouldIDisconnect==false) //tested
            {
                Ride ride=this.LookForRide();
                if(pData.isClient()==true) {
                    DB.AddARide(ride);
                    //from here
                    System.out.println("I am client");
                    RideDistance processedRide=DB.processClient(ride);
                    ride=processedRide.ride;
                    double cost=-1.0;
                    TransactionData data=new TransactionData();
                    try {
                        cost = costCalculator.getCost(processedRide.distance);
                        data= moneyOperations.makeAnOperation(cost, processedRide.distance);
                    }catch(Exception e){
                        System.out.println(e);
                    }
                    System.out.println(data.Sum());
                    ride.price=data.Sum();
                    //to here- new features- not tested
                }
                else {
                    System.out.println("I am driver");
                }
                System.out.println(ride.printRide());
                this.SentRideUpdate(ride);
            }
            else{
                System.out.println("There is an error with connection to the server");
            }
            this.Disconnect();
        }
    }

    private WebServerApplication.DataBaseComponent DB;
    private WebServerApplication.CostCalculator costCalculator;
    private WebServerApplication.MoneyOperations moneyOperations;

    Server(String clientIP, int portNumber)
    {
        super(clientIP, portNumber);
        DB=new WebServerApplication.DataBaseComponent();
        costCalculator=new WebServerApplication.CostCalculator(1.5,0.4);
        moneyOperations=new WebServerApplication.MoneyOperations(0.5,23,0.1);

    }

}
