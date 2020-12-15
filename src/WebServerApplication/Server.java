package WebServerApplication;

import CommonDataTypes.*;

public class Server extends WebServerApplication.ServerCommunicator
{
    public static void main(String[] args) {
        Server server=new Server(args[0],45600);
        server.Run();
    }

    public void Run() {
        ServerAction serverAction = ServerAction.LISTEN;
        int port = -1;
        while (true) {
            if(port!=-1)
                serverAction=ServerAction.SING;
            switch (serverAction) {
                case LISTEN: {
                    while (port == -1) {
                        port = ConnectClients();
                    }
                    PersonalData pData = null;
                    pData = loginToTheSystem(pData, port);
                    if (pData != null) {
                        Ride ride = this.LookForRide();
                        if (pData.isClient() == true) {
                            RideDistance processedRide = DB.processClient(ride, pData);
                            if (processedRide == null) {
                                Disconnect();
                                port = -1;
                                continue;
                            }
                            ride = operateOnClientMoney(processedRide);
                        } else {
                            System.out.println("I am driver");
                            DB.CreateTicket(ride, pData, port);
                        }
                        this.SentRideUpdate(ride);
                    }
                    this.Disconnect();
                }break;
                case SING: {
                    //connect
                    //przetworz dane
                    //this.SentRideUpdate(ride);
                }
                System.out.println();
                port = DB.checkIfAnyTicketWasUpdated(); //implement farther operations- connecting driver
            }
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

    private Ride sentMessage(Ride ride,int port)
    {

        return null;
    }

    private PersonalData loginToTheSystem(PersonalData pData,int port)
    {
        while(pData==null) {
            pData = (PersonalData) this.LookForConnection(port);
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
        if(shouldIDisconnect==false)
            return pData;
        return null;
    }

    private Ride operateOnClientMoney(RideDistance processedRide)
    {
        Ride ride=processedRide.ride;
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
        return ride;
    }

}
