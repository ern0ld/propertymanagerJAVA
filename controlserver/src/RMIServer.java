import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.registry.LocateRegistry;
import javax.swing.JOptionPane;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class RMIServer  {

    int RMIport;
    ControlServer cs;
    public RMIServer (int RMIport, ControlServer cs) throws RemoteException {
        super();
        this.RMIport = RMIport;
        this.cs = cs;
        //Constructor
        //You will need Security manager to make RMI work
        //Remember to add security.policy to your run time VM options
        //-Djava.security.policy=[C:\Program Files\Java\jre1.8.0_181\server.policy
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    try {

                        Registry reg = LocateRegistry.getRegistry("Localhost", RMIport);
                        remoteInterface rmiInterface = (remoteInterface) reg.lookup("MyServer");

                        String str = Integer.toString(cs.getID());
                        System.out.println("RMI returns:" + rmiInterface.executeTask(str));
                        break;
                    }

                    catch (RemoteException re) {

                    }
                    catch (NotBoundException nbe) {
                        System.out.println("NotBound");
                    }}
            }}).start();
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    ControlRemoteToteutus controlRemoteToteutus = new ControlRemoteToteutus();
                    controlRemoteToteutus.setCs(cs);
                    Registry registry = LocateRegistry.createRegistry(5200);
                    registry.bind("MyControlServer", controlRemoteToteutus);

                } catch (RemoteException re) {
                    System.out.println("RemoteException");
                }
                catch (AlreadyBoundException abe) {
                    System.out.println("AlreadyBound");
                }

            }
        }).start();
    }


    public String executeTask(String id)  throws RemoteException {
        System.err.println(id + " is trying to contact!");
        return "Server says hello to " + id;
    }
}

