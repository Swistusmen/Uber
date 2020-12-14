package WebServerApplication;

import java.io.DataOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class PortListener implements Runnable{
    public WebServerApplication.MutableInt portToAssign;
    public ServerSocket server;
    public Socket sender;
    public int portN;
    public DataOutputStream outputStream;
    int port;

    public PortListener(int portN, WebServerApplication.MutableInt portToAssign )
    {
        this.portN=portN;
        this.portToAssign=portToAssign;
    }

    @Override
    public void run() {
        try {
            port=666;
            portToAssign.setVal(666);
            server = new ServerSocket(portN);
            try {
                sender = server.accept();
            } catch (Exception e) {
                port= -1;
            }
            System.out.println("Socket");
            outputStream = new DataOutputStream(sender.getOutputStream());
            System.out.println("Output");
            //port = portToAssign;
            port=666;
            System.out.println("Przydzielony port " + port);
            outputStream.writeInt(port);
            outputStream.close();
            server.close();
            sender.close();
        }catch (Exception e){
            //portToAssign=-1;
        }
    }
}
