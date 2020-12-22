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

public abstract class MobileAppCommunicator implements  Operations{
    private ServerSocket sender=null;
    private Socket listener=null;
    private Socket client=null;
    private DataInputStream inputStream =null;
    private DataOutputStream outputStream=null;
    public Scanner scanner= null;
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

    protected int JoinToTheSystem(int portNumber)
    {
        int port=-1;
        while(port==-1) {
            try {
                System.out.println(this.IP);
                System.out.println(portNumber);
                client=new Socket(this.IP,portNumber);
                System.out.println("Socket");
                inputStream = new DataInputStream(client.getInputStream());
                System.out.println("Input");
                port = inputStream.readInt();
                System.out.println("Przydzielony port: "+port);
                client.close();
                inputStream.close();

            } catch (Exception e) {
                System.out.println(e);
            }
        }
        return port;
    }

    protected PersonalData LoadPersonalData(String phoneNumber) //will be used in case of signin- data are on device
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

    protected void SentRide(Ride ride)
    {
        try{
            System.out.println("Sending a ride object");
            serializationStream.flush();
            serializationStream.writeObject(ride);
            serializationStream.flush();
            System.out.println("Sent");
        }catch (Exception e){

        }
    }

    protected Ride ReadRide()
    {
        Ride ride=null;
        try{
            ride = (Ride) deserializationStream.readObject();
            System.out.println(ride.printRide());
        }catch(Exception e){
            System.out.println(e);
        }
        return ride;
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

    //CRITICAL
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
                //here, the order matters, different order causes, program cannot set connection
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
            deserializationStream.readChar(); //this one is very strange, communication is broken without it
            isConnectionSetUp=(boolean)deserializationStream.readBoolean();
            System.out.println("Got confirmation");
        }catch (Exception e)
        {
            System.out.println(e);
        }
        System.out.println("Koniec connect with server");
        return isConnectionSetUp;
    }

    protected void WaitForConnection()
    {
        try{
            sender=new ServerSocket(this.portNumber);
            listener=sender.accept();
            client=new Socket(this.IP,this.portNumber+1);
            deserializationStream=new ObjectInputStream(listener.getInputStream());
            serializationStream=new ObjectOutputStream(client.getOutputStream());
            serializationStream.flush();
        }catch(Exception e){
            System.out.println(e);
        }
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
        int port=this.JoinToTheSystem(portNumber);
        this.portNumber=port;
        this.scanner=new Scanner(System.in);
    }

    @Override
    public Ride CreateARide(boolean isClient, String phonNumber)
    {
        Ride currentRide=new Ride();
        Scanner scanner = new Scanner(System.in);
        currentRide.state= RideState.Ordered;
        System.out.print("Please enter your current location: ");
        currentRide.inputAddress=scanner.nextLine();
        if(isClient) {
            currentRide.phoneDriver = phonNumber;
            System.out.print("Please enter your destination: ");
            currentRide.outputAddress = scanner.nextLine();
        }
        else {
            currentRide.phoneClient = phonNumber;
            System.out.print("Please enter your car number: ");
            currentRide.carNumber = scanner.nextLine();
        }
        return currentRide;
    }

    @Override
    public PersonalData SignUp(boolean isClient)
    {
        PersonalData client=new PersonalData();
        System.out.print("Name: ");
        client.setName(scanner.nextLine());
        System.out.print("Surname: ");
        client.setSurname(scanner.nextLine());
        System.out.print("Account number: ");
        client.setAccountNumber(scanner.nextLine());
        client.setClient(isClient);
        client.setRating(0.0f);
        System.out.print("Phone number: ");
        client.setTelephoneNumber(scanner.nextLine());
        client.setWantToSignUp(true);
        return client;
    }

    public PersonalData LandingMenu(boolean isClient)
    {
        System.out.println("0.Sign up");
        System.out.println("1.Sign in");
        System.out.println("2.Exit");
        int switcher=scanner.nextInt();
        switch(switcher){
            case 0:
            {
                return SignUp(isClient);
            }
            case 1:{
                return null;
            }
            case 2:{
                return null;
            }
        }
        return null;
    }

}
