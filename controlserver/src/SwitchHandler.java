import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.*;
import java.io.*;
import java.util.concurrent.ConcurrentHashMap;

public class SwitchHandler extends Thread {
    enum Mode {ON, OFF, NOTCONNECTED};
    int ID;
    Socket client;
    ControlServer cs;
    ConcurrentHashMap<?,ControlServer.Mode> lightsMap;

    public SwitchHandler(Socket socket, int ID, ControlServer cs) {
        client = socket;
        this.cs  = cs;
        this.ID = ID;

    }

    public void setLightsMap(ConcurrentHashMap lightsMap){
        this.lightsMap = lightsMap;
    }
    public void setCs(ControlServer cs){
        this.cs = cs;
    }
    public void run() {
        //Jos lamppua ei ole vielä yhdistetty, kytketään se tässä
        if(cs.getLightstatus(ID ) == null) {
            cs.toggleLightstatus(ID);
        }

        while (true) {
            try {
                InputStream iS = client.getInputStream();
                OutputStream oS = client.getOutputStream();
                ObjectOutputStream ooS = new ObjectOutputStream(oS);
                String mode = cs.getLightstatus(ID).toString();
                ooS.writeObject(mode);
                ooS.flush();
                //Luetaan lightSwitchiltä tullut ID ja laitetaan se ControlServerin toggleLIghtStatukseen
                if(iS.available() > 0) {
                    int kopeloitu = iS.read();

                    cs.toggleLightstatus(kopeloitu);
                }
            } catch (IOException ioe) {
                System.out.println("Virhe säikeessä");
            }
        }




    }}