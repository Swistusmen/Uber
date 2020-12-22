package WebServerApplication;

import CommonDataTypes.*;

import java.util.ArrayList;

public class Server extends WebServerApplication.ServerCommunicator
{
    public static void main(String[] args) {
        Server server=new Server(args[0],45600);
        server.Run();
    }

    public void Run() {
        ServerAction serverAction = ServerAction.LISTEN;
        int port = -1;
        Ride ride=null;
        while (true) {
            serverAction=(port!=-1) ?ServerAction.SING:ServerAction.LISTEN;
            switch (serverAction) {
                case LISTEN: {
                    PersonalData pData = null;
                    while(true) {
                        port = ConnectClients();
                        final int noPorts = this.busyPorts.size();
                        for (int i = 0; i < noPorts; ) {
                            System.out.println("Port z ktorym sie lacze: "+this.busyPorts.get(i));
                            ArrayList<Integer> ports=new ArrayList<Integer>();
                            for(int c=0;i<noPorts&&c<8;c++,i++){
                                ports.add(busyPorts.get(i));
                            }
                            PersonalPort pPort=null;
                            pPort=loginToTheSystem(pPort, ports);
                            if(pPort==null)
                                continue;
                            pData=pPort.pData;
                            port=pPort.port;
                            if(pData!=null) {
                                System.out.println("Rozne od null");
                                break;
                            }
                        }
                        if(pData!=null) {
                            System.out.println("Rozne od null");
                            break;
                        }
                    }

                    if (pData != null) {
                        ride = this.LookForRide();
                        if (pData.isClient() == true) {
                            RideDistance processedRide = DB.processClient(ride, pData);
                            if (processedRide == null) {
                                Disconnect();
                                port = -1;
                                continue;
                            }
                            ride = operateOnClientMoney(processedRide);
                            this.SentRideUpdate(ride);
                        } else {
                            System.out.println("I am driver");
                            DB.CreateTicket(ride, pData, port);
                        }
                    }
                    this.Disconnect();
                }break;
                case SING: {
                    this.SetUpConnection(port);
                    this.SentRideUpdate(ride);
                    ride=this.LookForRide();
                    DB.processTicket(port,ride,null);
                }break;
            }
            port = DB.checkIfAnyTicketWasUpdated();
            System.out.println("Port: "+port);
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

    private PersonalPort loginToTheSystem(PersonalPort pData,ArrayList<Integer> ports)
    {
        pData = this.LookForConnection(ports);
        if(pData.pData==null)
            return null;
        boolean shouldIDisconnect=true;
        if(pData.pData.isWantToSignUp()==true) //tested
        {
            System.out.println("Want to sign up");
            shouldIDisconnect=DB.AddPersonalData(pData.pData);
        }
        else{
            System.out.println("Already in base");
            shouldIDisconnect=!DB.checkIfDataAreInDB(pData.pData);
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
