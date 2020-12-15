package WebServerApplication;

import CommonDataTypes.PersonalData;
import CommonDataTypes.Ride;
import CommonDataTypes.RideState;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.http.WebSocket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
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
        private List<Integer> availablePorts;
        private List<Integer> busyPorts;

        protected int AssignPort()
        {
            int size=this.availablePorts.size();
            if(size==0)
                return -1;
            int port=this.availablePorts.get(0);
            this.busyPorts.add(port);
            this.availablePorts.remove(0);
            return port;
        }

        protected int JoinClientsToTheSystem(int portN)
        {
            int port=-1;
            try{
                /*
                System.out.println("na porcie: "+portN);
                 server=new ServerSocket(portN);
                 server.setSoTimeout(10*1000); //TODO this doesn't work as expected under ubuntu
                 System.out.println("ServerSocket");
                 try {
                     sender = server.accept();
                 }catch(Exception e){
                     return -1;
                 }
                System.out.println("Socket");
                outputStream=new DataOutputStream(sender.getOutputStream());
                System.out.println("Output");
                port=AssignPort();
                System.out.println("Przydzielony port "+port);
                outputStream.writeInt(port);
                outputStream.close();
                server.close();
                sender.close();
                */
                int porcik=AssignPort();
                System.out.println("porcik "+porcik );
                port=porcik;
                WebServerApplication.PortHandler portHandler=new WebServerApplication.PortHandler(port,portN);
                Thread thread=new Thread(portHandler);
                thread.start();
                Thread.sleep(10000);
                thread.interrupt();
                port=portHandler.portToAssign;
                System.out.println(port);
                if(port==porcik) {
                    availablePorts.add(port);
                    busyPorts.remove((Object)(port));
                    return -1;
                }
                port=porcik;
                System.out.println(busyPorts.size());
            }catch(Exception e)
            {
                System.out.println(e);
                return port;
            }
            return port;
        }

        protected int ConnectClients(){
            int port=-1;
            if(-1!=(port=JoinClientsToTheSystem(portNumber))) {
                System.out.println(port);
                return port;
            }
            if(this.busyPorts.size()==0) {
                System.out.println("Pusto");
                return -1;
            }
            for(int i: this.busyPorts)
            {
                if(i<0)
                {
                    if(-1!=(port=JoinClientsToTheSystem(i)))
                        return port;
                }
            }
            return port;
        }

        public ServerCommunicator (String clientIP,int portNumber)
        {
            this.clientIP=clientIP;
            this.portNumber=portNumber;
            this.availablePorts=new LinkedList<Integer>();
            for(int i=45601;i<45641;i+=2)
            {
                availablePorts.add(i);
            }
            this.busyPorts=new LinkedList<Integer>();
        }

        protected Object LookForConnection(int port)
        {
            Object data=null;
            try{
                server = new ServerSocket(port);
                listener = server.accept(); //start to listen and do it until connection will be set up

                sender = new Socket(this.clientIP, port+1);//start sending messages
                System.out.println("Connection has been set up");
                deserializationStream = new ObjectInputStream(listener.getInputStream());
                serializationStream = new ObjectOutputStream(sender.getOutputStream());
                serializationStream.flush();
            }catch (Exception e)
            {
                //System.out.println(e);
            }
            try{
                //data=(PersonalData) deserializationStream.readObject();
                data=deserializationStream.readObject();
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
