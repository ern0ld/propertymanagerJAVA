import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RemoteToteutus extends UnicastRemoteObject implements remoteInterface {
    static String tulos;
    public RemoteToteutus() throws RemoteException {


    }
    public String executeTask(String ID) throws RemoteException {
        System.out.println("Tässä executeTask: "+ ID);
        return "Suoritettu "+ ID;
    }


}
