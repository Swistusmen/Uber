package WebServerApplication;

import CommonDataTypes.PersonalData;
import CommonDataTypes.Ride;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;


public abstract class ServerCommunicator {
        private Socket sender=null;
        private Socket listener=null;
        private ServerSocket server=null;
        private DataInputStream inputStream =null;
        private DataOutputStream outputStream=null;
        private Scanner skaner= null;
        private ObjectOutputStream serializationStream;
        private ObjectInputStream deserializationStream;
        private String clientIP;
        private int portNumber;

        public ServerCommunicator()
        {

        }

        public ServerCommunicator (String clientIP,int portNumber)
        {
            this.clientIP=clientIP;
            this.portNumber=portNumber;
        }

        protected PersonalData LookForConnection()
        {
            PersonalData data=null;
            try{
                server = new ServerSocket(portNumber);
                listener = server.accept(); //start to listen and do it until connection will be set up

                sender = new Socket(this.clientIP, this.portNumber+1);//start sending messages
                System.out.println("Connection has been set up");
                deserializationStream = new ObjectInputStream(listener.getInputStream());
                serializationStream = new ObjectOutputStream(sender.getOutputStream());
                serializationStream.flush();
            }catch (Exception e)
            {
                //System.out.println(e);
            }
            try{
                data=(PersonalData) deserializationStream.readObject();

                System.out.println("Message has been received");
            }catch (Exception e)
            {
                //System.out.println(e);
            }
            return data;
        }

        protected void Disconnect()
        {
            try{
                System.out.println("Disconnection");
                serializationStream.writeObject(null);
                deserializationStream.close();
                serializationStream.close();
                server.close();
                sender.close();
                listener.close();
            }catch(Exception e)
            {
                System.out.println(e);
            }
        }

        protected void SentConnectionConfirmation(boolean isConfirmed)
        {
            try{
                System.out.println("Sending confirmation");
                System.out.println(isConfirmed);
                for(int i=0;i<1;i++)
                {
                    serializationStream.writeChar('a');
                    serializationStream.writeBoolean(isConfirmed);
                }
                serializationStream.reset();

                System.out.println("Send");
            }catch(Exception e)
            {
                System.out.println(e);
            }
        }

        protected Ride LookForRide()
        {

            Ride ride=null;
            try {
                System.out.println("Waiting for Ride");
                ride = (Ride) deserializationStream.readObject();
                System.out.println("Got it");
            }catch(Exception e)
            {
                System.out.println(e);
            }
            return ride;
        }

        protected void SentRideUpdate(Ride ride)
        {
            try{
                System.out.println("Writing a ride");
                serializationStream.flush();
                System.out.println(ride.printRide());

                serializationStream.writeObject(ride);


                System.out.println("Wrote a ride");
            }catch(Exception e)
            {
                System.out.println(e);
            }
        }
};
