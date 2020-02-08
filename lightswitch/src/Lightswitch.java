
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;




public class Lightswitch {
    private JPanel mainPanel;
    private JButton switchbutton;
    private JLabel statuslabel;
    private Mode lightstatus;
    private enum Mode {OFF, ON, NOTCONNECTED}
    private static int ID;
    Socket socket, socketListener;
    InputStream iS;
    OutputStream oS;
    ObjectOutputStream oOut;
    ObjectInputStream oIn;
    BufferedReader in;
    PrintWriter out;
    private ConcurrentHashMap<Integer, String> lightsMap;
    public Lightswitch() {
        switchbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendChange(ID);
            }
        });

        connectSwitch(ID);
    }
    protected void setSocket(Socket s) {
        this.socket= s;
    }


    protected void connectSwitch(int ID) {
        //TODO: Create an socket connection connection to server
        Mode mode;
        try {
//Luodaan sokettiyhteys ja lähetetään argumentiksi asetetun lampun ID luettavaksi

            System.out.println("Tämä on connectSwitch");
            socket = new Socket("127.0.0.1", 5000);

            OutputStream oS = socket.getOutputStream();
            oS.write(ID);
            oS.flush();
            //Kutsutaan receiveStatusta jolloin kuuntelu ja lukeminen alkaa
            receiveStatus();


        }

        catch(IOException ioe){
            System.out.println("Varattu");

        }
        catch(IllegalMonitorStateException imse){

        }

        catch(NullPointerException npe){
            return;
        }
    }


    protected void sendChange(int ID)

    {
        //TODO: Send lightswitch action pressed to server
        try {
//Kirjoitetaan ID soketin virtaan
            OutputStream oS = socket.getOutputStream();
            String str = Integer.toString(ID);
            oS.write(ID);
            oS.flush();
            System.out.println("Kirjoitus onnistui: " + ID);
        }

        catch (IOException ioe) {
            System.out.println("Varattu sendChange");
        } catch (NullPointerException npe) {
            System.out.println("Tyhjää täynnä");
        }


    }



    protected void receiveStatus() {
        //Set default to not connected

        Mode receivedMode = Mode.NOTCONNECTED;
        //TODO: receive status of the light from the server
        System.out.println("Kutsuttu receivestatus");

        //Käynnistetään säie kuuntelemaan ja lukemaan lampun tilaa.
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {

                        InputStream iS = socket.getInputStream();
                        ObjectInputStream oIn = new ObjectInputStream(iS);

                        String mode = (String) oIn.readObject();
                        //Vaihdetaan tila
                        setLightStatus(Mode.valueOf(mode));
                        //socket.setSoTimeout(1000);
                        // System.out.println("Tässä mode: " + mode);
                    }


                    catch(IOException ioe){
                        System.out.println("Varattu receiveStatus");
                        return;

                    }
                    catch(ClassNotFoundException cnfe){
                        System.out.println("Luokkaa ei löydy");
                    }

                } }
        }).start();


    }




    //Update view to correspond the received status




    //Setter for the status of the light
// Modes are OFF, ON, NOTCONNECTED
    public void setLightStatus(Mode input) {
        if (input == Mode.ON) {
            lightstatus = Mode.ON;
            statuslabel.setText("Lights on");
            statuslabel.setBackground(Color.green);
            //System.out.println("Valo päällä");
        }
        else if (input == Mode.OFF){
            lightstatus = Mode.OFF;
            statuslabel.setText("Lights off");
            statuslabel.setBackground(Color.red);
            //System.out.println("Valo pois päältä");

        }
        else if (input == Mode.NOTCONNECTED){
            lightstatus = Mode.NOTCONNECTED;
            statuslabel.setText("Not connected");
            statuslabel.setBackground(Color.yellow);
            System.out.println("Valo ei yhdistetty");

        }
    }

    public static void main(String[] args) {
        //No need to edit main.
        //ID number is read from th first command line parameter

        if (args.length >0) {
            try {
                ID = Integer.parseInt(args[0]);
            } catch (Exception ex) {
                System.err.println("Error reading arguments");
                ID = 1;
            }
        }

        JFrame frame = new JFrame("Lightswitch");
        frame.setContentPane(new Lightswitch().mainPanel);
        frame.setTitle("Lightswitch");
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }
}