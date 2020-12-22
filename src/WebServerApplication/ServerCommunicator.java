package WebServerApplication;

import CommonDataTypes.PersonalData;
import CommonDataTypes.PersonalPort;
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
        protected List<Integer> busyPorts;

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
                System.out.println("Join Clinets to the system");
                int porcik=AssignPort();
                port=porcik;
                WebServerApplication.PortHandler portHandler=new WebServerApplication.PortHandler(port,portN);
                Thread thread=new Thread(portHandler);
                thread.start();
                thread.join(20000);
                port=portHandler.portToAssign;
                if(port==porcik) {
                    availablePorts.add(port);
                    busyPorts.remove((Object)(port));
                    return -1;
                }
                System.out.println("Przydzielony "+porcik);
                port=porcik;
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
                System.out.println("Docelowy port: "+port);
                return port;
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

        protected PersonalPort LookForConnection(ArrayList<Integer> ports) //przyjmowanie do 4 portow
        {
            PersonalPort p=new PersonalPort();
            try {
                int size=ports.size();
                ArrayList<WebServerApplication.PortListener> listeners=new ArrayList<WebServerApplication.PortListener>();
                ArrayList<Thread> threads=new ArrayList<Thread>();
                for(int i=0;i<size;i++)
                {
                    System.out.println("Port: "+ports.get(i));
                    listeners.add( new WebServerApplication.PortListener(ports.get(i), this.clientIP));
                    threads.add(new Thread(listeners.get(i)));
                    threads.get(i).start();
                }
                for(int i=0;i<size;i++)
                {
                    threads.get(i).join(10000);
                }
                int index=-1;
                for(int i=0;i<size;i++)
                {
                    if(listeners.get(i).deserializationStream!=null)
                    {
                        index=i;
                        break;
                    }
                }
                if(index!=-1)
                {
                    deserializationStream= listeners.get(index).deserializationStream;
                    serializationStream= listeners.get(index).serializationStream;
                    server= listeners.get(index).server;
                    sender= listeners.get(index).sender;
                    listener= listeners.get(index).listener;
                    p.pData = (PersonalData)deserializationStream.readObject();
                    p.port=index;
                }
            }catch (Exception e){};

            return p;
        }

        protected void SetUpConnection(int port)
        {
            try{
                this.sender=new Socket(this.clientIP, port);
                this.server= new ServerSocket(port+1);
                this.listener=server.accept();
                serializationStream=new ObjectOutputStream(sender.getOutputStream());
                serializationStream.flush();
                deserializationStream=new ObjectInputStream(listener.getInputStream());
            }catch(Exception e){
                System.out.println(e);
            }
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
