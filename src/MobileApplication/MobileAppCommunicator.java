package MobileApplication;

import CommonDataTypes.PersonalData;
import CommonDataTypes.Ride;
import CommonDataTypes.RideState;

import java.awt.*;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Scanner;

public abstract class MobileAppCommunicator {

        /*
public class MobileAppCommunicator {
    public static void main(String[] args) {

        Ride currentRide=new Ride();
        currentRide.state= RideState.Unordered;
        currentRide.price=100;
        currentRide.inputAddress="Czarnowiejska";
        MobileAppCommunicator app= new MobileAppCommunicator ();//("192.168.0.143",1800);
        currentRide=app.Run("192.168.0.143",1800,currentRide);
        System.out.println(currentRide.printRide());

         */
    //}


    private ServerSocket sender=null;
    private Socket listener=null;
    private Socket client=null;
    private DataInputStream inputStream =null;
    private DataOutputStream outputStream=null;
    private Scanner skaner= null;
    private ObjectOutputStream serializationStream;
    private ObjectInputStream deserializationStream;
    private String resourcePath="MobileAppResources";
    private PersonalData pData;
    private String IP;
    private int portNumber;

    protected boolean InserPersonalData(PersonalData pData)
    {
        try{
            String fileName=pData+".ser";
            File plik=new File(fileName);
            if(!plik.exists())
            {
                plik.createNewFile();
            }
            System.out.println(pData);
            FileOutputStream fileOutputStream=new FileOutputStream(plik);
            System.out.println("AAAA");

            serializationStream=new ObjectOutputStream(fileOutputStream);
            serializationStream.writeObject(pData);
            serializationStream.flush();
            serializationStream.close();

            return true;
        }catch(Exception e)
        {
            System.out.println(e);
        }
        return false;
    }

    protected PersonalData LoadPersonalData(String phoneNumber)
    {
        PersonalData pData=null;
        try{
            String fileName=phoneNumber+".ser";
            FileInputStream fileInputStream=new FileInputStream(this.resourcePath+"/"+fileName);
            deserializationStream=new ObjectInputStream(fileInputStream);
            deserializationStream.close();
        }catch(Exception e)
        {
            System.out.println(e);
        }
        return pData;
    }

    protected Ride OperateOnRide(Ride ride)
    {
        Ride obj=null;
        try{
            System.out.println("Operating on ride");
            serializationStream.flush();
            serializationStream.writeObject(ride);
            serializationStream.flush();
            System.out.println("Wrote an object");

            System.out.println(ride.printRide());

            ride = (Ride) deserializationStream.readObject();
            System.out.println(ride.printRide());
            System.out.println("Read an object");
        }catch(Exception e)
        {
            System.out.println(e);
            System.out.println(((OptionalDataException)(e)).length);
        }
        System.out.println("Operate on ride- end");
        return ride;
    }

    protected boolean ConnectWithServer( PersonalData pData)
    {
        boolean isConnectionSetUp=false;
        try{
            System.out.println("A");
            client = new Socket(IP, this.portNumber); //setting up socket which response for sending messages
            System.out.println("B");
            sender=new ServerSocket(portNumber+1);
            System.out.println("C");
            listener=sender.accept(); //setting up socket which responds for receivinng messages
            System.out.println("Connection has been set up");
            try {
                //TODO here, the order matters, different order causes, program cannot set connection
                serializationStream =new ObjectOutputStream(client.getOutputStream());
                serializationStream.flush();
                deserializationStream = new ObjectInputStream(listener.getInputStream());
            }catch(Exception e)
            {
                System.out.println("Error with serialization streams, "+e);
            }
            System.out.println("Connection has been set up");
        }catch(Exception e)
        {
            System.out.println(e);
        }
        try{
            serializationStream.writeObject(pData);
            System.out.println("Sending initial data");
            serializationStream.flush();
            System.out.println("waiting for boolean");
            deserializationStream.readChar(); //TODO this one is very strange, communication is broken without it
            isConnectionSetUp=(boolean)deserializationStream.readBoolean();
            System.out.println("Got confirmation");
        }catch (Exception e)
        {
            System.out.println(e);
        }
        System.out.println("Koniec connect with server");
        return isConnectionSetUp;
    }

    protected void Disconnect()
    {
        try{
            System.out.println("A");
            deserializationStream.close();
            serializationStream.close();
            System.out.println("A");
            sender.close();
            listener.close();
            System.out.println("A");
        }catch (Exception e)
        {
            System.out.println(e);
        }
    }

    public MobileAppCommunicator(String IP, int portNumber)
    {
        this.IP=IP;
        this.portNumber=portNumber;
    }


    public Ride Run(String IP, int portNumber, Ride rideObject)
    {
        Ride ride=null;
        try{
            client = new Socket(IP, portNumber); //setting up socket which response for sending messages

            sender=new ServerSocket(portNumber);
            listener=sender.accept(); //setting up socket which responds for receivinng messages
            System.out.println("Hello");
            try {
                //TODO here, the order matters, different order causes, program cannot set connection
                serializationStream =new ObjectOutputStream(client.getOutputStream());
                deserializationStream = new ObjectInputStream(listener.getInputStream());
            }catch(Exception e)
            {
                System.out.println("Error with serialization streams, "+e);
            }
            System.out.println("Connection has been set up");
        }catch(Exception e)
        {
            System.out.println(e);
        }
        try{
            System.out.println(rideObject.printRide());
            serializationStream.writeObject(rideObject);
            serializationStream.flush();
            System.out.println("Send object");

            ride=(Ride) deserializationStream.readObject();
        }catch(Exception e)
        {
            System.out.println(e);
        }
        try{
            inputStream.close();
            outputStream.close();
            client.close();
            sender.close();
            listener.close();
        }catch(Exception e)
        {
            System.out.println(e);
        }
        return ride;
    }

    /*
    public MobileAppCommunicator(){

    }
    */

/*
    public MobileAppCommunicator(String IP, int portNumber)
    {
        try {
            client = new Socket(IP, portNumber); //setting up socket which response for sending messages

            sender=new ServerSocket(portNumber);
            listener=sender.accept(); //setting up socket which responds for receivinng messages

            inputStream = new DataInputStream(listener.getInputStream());
            outputStream = new DataOutputStream(client.getOutputStream());
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
                System.out.print(": ");
                line=skaner.nextLine();

                outputStream.writeUTF(line);
                outputStream.flush();

                line=inputStream.readUTF();
                System.out.println(line);

            }}catch (Exception e)
        {
            System.out.println(e);
        }
        try{
            inputStream.close();
            outputStream.close();
            client.close();
            sender.close();
            listener.close();
        }catch(Exception e)
        {
            System.out.println(e);
        }
    }
    */

}
