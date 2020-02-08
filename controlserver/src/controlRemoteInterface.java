import java.rmi.Remote;
import java.rmi.RemoteException;

public interface controlRemoteInterface extends Remote {
    int executeTask(String id)  throws RemoteException;
    String getID() throws RemoteException;
    String getStatus(int ID) throws RemoteException;
    String getTemperature() throws RemoteException;
}
