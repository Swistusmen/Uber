package WebServerApplication;

import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class PortHandler implements Runnable{
    int portToAssign;
    int defaultPort;

    public PortHandler(int portToAssign, int defaultPort){
        this.portToAssign=portToAssign;
        this.defaultPort=defaultPort;
    }

    @Override
    public void run() {
        try {
            ServerSocket server = new ServerSocket(defaultPort);
            Socket sender = server.accept();
            DataOutputStream outputStream = new DataOutputStream(sender.getOutputStream());
            outputStream.writeInt(portToAssign);
            portToAssign=-1;
            outputStream.close();
            server.close();
            sender.close();
        }catch (Exception e){}
    }
}
