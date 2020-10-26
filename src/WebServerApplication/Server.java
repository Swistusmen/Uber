package WebServerApplication;

import CommonDataTypes.PersonalData;
import CommonDataTypes.Ride;

import java.io.File;

public class Server extends ServerCommunicator
{
    public static void main(String[] args) {
        Server server=new Server("192.160.0.31",1800);
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
            if(pData.isWantToSignUp()==true)
            {
                shouldIDisconnect=DB.AddPersonalData(pData);
            }
            else{
                PersonalData temp=DB.GetPersonalData(pData);
                shouldIDisconnect=(temp==pData?true:false);
            }
            this.SentConnectionConfirmation(!shouldIDisconnect);
            if(shouldIDisconnect==false)
            {
                Ride ride=this.LookForRide();
                //proceosowanie
                System.out.println(ride.printRide());
                ride.price+=10;
                DB.UpdateRide(ride);
                this.SentRideUpdate(ride);
            }
            this.Disconnect();
        }
    }

    private DataBaseComponent DB;

    Server(String clientIP, int portNumber)
    {
        super(clientIP, portNumber);
        DB=new DataBaseComponent("Resources");
    }

}
