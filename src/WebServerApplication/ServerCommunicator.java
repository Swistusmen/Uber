package WebServerApplication;

import CommonDataTypes.Ride;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;


public class ServerCommunicator {
    public static void main(String[] args) { //this method need to be changed to Run() or something at the end
        ServerCommunicator a=new ServerCommunicator();//("Write your own", 1800);
        a.Run("Write your own", 1800);

    }
        private Socket sender=null;
        private Socket listener=null;
        private ServerSocket server=null;
        private DataInputStream inputStream =null;
        private DataOutputStream outputStream=null;
        private Scanner skaner= null;
        private ObjectOutputStream serializationStream;
        private ObjectInputStream deserializationStream;
        private String filePath="Resources/Ride.ser";

        ServerCommunicator()
        {

        }

        public Ride Run(String IP, int portNumber)
        {
            Ride ride=null;
            try {
                server = new ServerSocket(portNumber);
                listener = server.accept(); //start to listen and do it until connection will be set up

                sender = new Socket(IP, portNumber);//start sending messages

                deserializationStream = new ObjectInputStream(listener.getInputStream());
                serializationStream = new ObjectOutputStream(sender.getOutputStream());

            }catch(Exception e)
            {
                System.out.println(e);
            }
            skaner=new Scanner(System.in);
            try{
                ride=(Ride) deserializationStream.readObject();
                ride.price+=10;
                System.out.println(ride.printRide());
                
                serializationStream.writeObject(ride);
                serializationStream.flush();
                System.out.println("Send object");
                }catch (Exception e)
            {
                System.out.println(e);
            }
            try{
                inputStream.close();
                outputStream.close();
                server.close();
                sender.close();
                listener.close();
            }catch(Exception e)
            {
                System.out.println(e);
            }
            return ride;
        }

        public ServerCommunicator(String IP, int portNumber)
        {
            try {
                server = new ServerSocket(portNumber);
                listener = server.accept(); //start to listen and do it until connection will be set up

                sender = new Socket(IP, portNumber);//start sending messages

                inputStream = new DataInputStream(listener.getInputStream());
                outputStream = new DataOutputStream(sender.getOutputStream());
                System.out.println("Connection has been set up");
            }catch(Exception e)
            {
                System.out.println(e);
            }
            skaner=new Scanner(System.in);
            try{
                String line="";
                while (!line.equals("Over"))
                {
                    line=inputStream.readUTF();
                    System.out.println(line);

                    line=skaner.nextLine();

                    outputStream.writeUTF(line);
                    outputStream.flush();


                }}catch (Exception e)
                {
                    System.out.println(e);
                }
                try{
                    inputStream.close();
                    outputStream.close();
                    server.close();
                    sender.close();
                    listener.close();
                }catch(Exception e)
                {
                    System.out.println(e);
                }
            }


};
