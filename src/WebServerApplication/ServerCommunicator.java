package WebServerApplication;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;


public class ServerCommunicator {
    public static void main(String[] args) { //this method need to be changed to Run() or something at the end
        ServerCommunicator a=new ServerCommunicator("192.168.0.255", 1800);

    }
        Socket sender=null;
        Socket listener=null;
        ServerSocket server=null;
        DataInputStream inputStream =null;
        DataOutputStream outputStream=null;
        Scanner skaner= null;

        public ServerCommunicator(String IP, int portNumber)
        {
            try {
                server = new ServerSocket(portNumber);
                listener = server.accept(); //start to listen and do it until connection will be set up

                sender = new Socket(IP, portNumber);//current object creates a socket, which will be used for sending messages
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
