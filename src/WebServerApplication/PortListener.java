package WebServerApplication;


import java.io.DataOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class PortListener implements Runnable{
    public int port;
    public String IP;
    public ServerSocket server=null;
    public Socket listener=null;
    public Socket sender=null;
    public ObjectOutputStream serializationStream=null;
    public ObjectInputStream deserializationStream=null;

    public PortListener(int portToAssign, String IP){
        this.port=portToAssign;
        this.IP=IP;
    }

    @Override
    public void run() {
        try{
            server = new ServerSocket(port);
            listener = server.accept(); //start to listen and do it until connection will be set up

            sender = new Socket(this.IP, port+1);//start sending messages
            System.out.println("Connection has been set up");
            deserializationStream = new ObjectInputStream(listener.getInputStream());
            serializationStream = new ObjectOutputStream(sender.getOutputStream());
            serializationStream.flush();
        }catch (Exception e)
        {

        }
    }
}
