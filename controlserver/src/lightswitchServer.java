

import javax.naming.ldap.Control;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.*;
import java.io.*;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;

public class lightswitchServer extends Thread {
    Socket socket;
    ServerSocket serverSocket;
    int ID;
    ControlServer cs;
    BlockingQueue queue, queue2;
    InputStream iS;
    OutputStream oS;
    String [] status;
    String IP;
    int port;
    ObjectInputStream oIn;
    ActionEvent e;


    private ConcurrentHashMap<Integer, Socket> lightsMap = new ConcurrentHashMap<>();
    public lightswitchServer(String IP, int port, ControlServer cs) {
        this.IP = IP;
        this.port = port;
        this.cs = cs;
        try {
            serverSocket = new ServerSocket(port, 10, InetAddress.getByName(IP));
        }
        catch (IOException ioe) {

        }
        //constructor, create server here and bind it to IP & port


    }
    public void setStatus(ActionEvent e) {
        this.e = e;
    }
    public String[] getStatus(){
        return status;
    }
    public void setID (int ID){
        this.ID = ID;
    }

    public int getID() {
        return ID;
    }

    public void setLightsMap(ConcurrentHashMap chm){
        this.lightsMap = chm;
    }
    public ConcurrentHashMap getLightsMap(){
        return lightsMap;
    }
    public void setSocket(Socket s){
        this.socket = s;
    }
    public Socket getSocket() {
        return socket;
    }

    public void run() {
        //And here you should listen to the server socket

        try {

            while (true) {
                socket = serverSocket.accept();
                setSocket(socket);
                System.out.println("Uusi yhteys" + socket.getLocalPort());
                int tunnus = socket.getInputStream().read();
                //socket.getInputStream().close();
                lightsMap.put(tunnus, socket);
                System.out.println("Moro tässä tunnus:"+  lightsMap.get(tunnus));



                SwitchHandler sh = new SwitchHandler(socket, tunnus, cs);
                System.out.println("Uusi säie luotu " + tunnus);
                sh.setLightsMap(lightsMap);
                sh.start();
                //int lamppu = iS.read();*/

            }
        }
        catch (IOException ioe){

        }
        catch (NullPointerException npe){
            npe.printStackTrace();

        }

    }
}

