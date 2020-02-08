import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ControlRemoteToteutus extends UnicastRemoteObject implements controlRemoteInterface {
    ControlServer controlServer;
    public ControlRemoteToteutus() throws RemoteException {
        super();
    }
    public void setCs(ControlServer cs){
        this.controlServer = cs;
    }
    public int executeTask(String ID) throws RemoteException {
        controlServer.toggleLightstatus(Integer.parseInt(ID));
        return Integer.parseInt(ID);
    }
    public String getID(){
        return Integer.toString(controlServer.getID());
    }
public String getStatus(int ID) {
       //Palauttaa halutun ID:n lampun statuksen
        if(controlServer.getLightstatus(ID) != null) {
            ControlServer.Mode mode = controlServer.getLightstatus(ID);

            String palautus = mode.toString();
            return palautus;
        }

        else return "N/A";
}
public String getTemperature(){
        return controlServer.getTemperature();

}

}