
import javax.swing.*;
import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class RMIClient  extends UnicastRemoteObject implements remoteInterface {

    remoteInterface comp;
    String[] args;
    public RMIClient() throws MalformedURLException, RemoteException, NotBoundException {
        super();
        //Security manager is needed. Remember policy file and VM parameter again.
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        //TODO: RMI client connection
        try {
            RemoteToteutus remoteToteutus = new RemoteToteutus();
            Registry registry = LocateRegistry.createRegistry(5300);
            registry.bind("MyServer", remoteToteutus);
        }
        catch (AlreadyBoundException abe) {

        }
        //



    }
    public void setArgs(String[] args) {

        this.args = args;

    }@Override
    public String executeTask(String id)  throws RemoteException {
        System.err.println(id + " is trying to contact!");
        return "Server says hello to " + id;
    }

    //TODO: Create needed requests to control server



}


