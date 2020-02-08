import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;


import java.net.URI;
import java.util.List;
import java.util.UUID;




public class RemoteServer {
    static String ID;


    public RemoteServer() throws RemoteException {
        //TODO: create and start your servers, make connection needed


    }

    public static void main(String[] args) throws RemoteException {
        try {

            //Käynnistetään WWWServer ja RMIClient
            InetSocketAddress address = new InetSocketAddress("127.0.0.1", 8000);
            WWWServer2 www = new WWWServer2(address);
            new RMIClient();
            System.err.println("Palvelin valmis");
        } catch (Exception e) {
            System.err.println("Palvelinvirhe: " + e.toString());
            e.printStackTrace();

        }


    }
}