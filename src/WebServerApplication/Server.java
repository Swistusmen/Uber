package WebServerApplication;

import CommonDataTypes.PersonalData;
import CommonDataTypes.Ride;

public class Server extends WebServerApplication.ServerCommunicator
{
    public static void main(String[] args) {
        Server server=new Server(args[0],45676);
        server.Run();
    }

    public void Run()
    {
        while(true)
        {
            PersonalData pData=null;
            while(pData==null) {
                pData = this.LookForConnection();
            }
            boolean shouldIDisconnect=true;
            if(pData.isWantToSignUp()==true) //tested
            {
                System.out.println("Want to sign up");
                shouldIDisconnect=DB.AddPersonalData(pData);
            }
            else{
                System.out.println("Already in base");
                shouldIDisconnect=DB.checkIfDataAreInDB(pData);
            }
            this.SentConnectionConfirmation(!shouldIDisconnect);
            if(shouldIDisconnect==false) //tested
            {
                Ride ride=this.LookForRide();
                DB.AddARide(ride);
                //proceosowanie
                ride=DB.UpdateRide(ride);
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

    Server(String clientIP, int portNumber)
    {
        super(clientIP, portNumber);
        DB=new WebServerApplication.DataBaseComponent("WebServerApplication");
    }

}
