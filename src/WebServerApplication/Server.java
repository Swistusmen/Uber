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
                //shouldIDisconnect=DB.AddPersonalData(pData);
                shouldIDisconnect=false;
            }
            else{
                System.out.println("Already in base");
                //PersonalData temp=DB.GetPersonalData(pData);
                //shouldIDisconnect=(temp==pData?true:false);
            }
            this.SentConnectionConfirmation(!shouldIDisconnect);
            if(shouldIDisconnect==false) //tested
            {
                Ride ride=this.LookForRide();
                //proceosowanie
                System.out.println(ride.printRide());
                ride.price+=10;
                //DB.UpdateRide(ride);
                this.SentRideUpdate(ride);
            }
            this.Disconnect();
        }
    }

    private DataBaseComponent DB;

    Server(String clientIP, int portNumber)
    {
        super(clientIP, portNumber);
        DB=new DataBaseComponent("WebServerApplication");
    }

}
