package MobileApplication;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Scanner;

public class MobileAppCommunicator {
    public static void main(String[] args) {
        MobileAppCommunicator app= new MobileAppCommunicator("192.168.0.143",1800);

    }

    ServerSocket sender=null;
    Socket listener=null;
    Socket client=null;
    DataInputStream inputStream =null;
    DataOutputStream outputStream=null;
    Scanner skaner= null;

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
}
