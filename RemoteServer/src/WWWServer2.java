
import java.io.*;
import java.net.InetSocketAddress;
import com.sun.net.httpserver.HttpServer;
import java.util.stream.Collectors;
import java.util.Scanner;
import java.net.URL;
import java.net.URLConnection;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.concurrent.Executors;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.util.Map;
import java.nio.file.*;
import java.util.HashMap;
import java.lang.Object.*;
import javax.swing.JFrame;
import javax.swing.JTextPane;
import java.util.Enumeration;
//import javax.servlet.http.Cookie;
import javax.swing.text.html.HTML;




public class WWWServer2 {

    private HttpServer server;
    controlRemoteInterface CRI;


    public WWWServer2(InetSocketAddress address) {
        //TODO: Create http-server instance and run it
        try {
            //Luodaan HTTP-palvelin ja lisätään sinne sisältöä MyHandler-luokan avulla
            server = HttpServer.create(new InetSocketAddress(8000),
                    0);

            server.setExecutor(Executors.newCachedThreadPool());

            server.createContext("/test", new MyHandler());

            server.start();
            //Yhdistetään RMI:llä controlserveriin
            Registry reg = LocateRegistry.getRegistry("Localhost", 5200);
            controlRemoteInterface CRI = (controlRemoteInterface) reg.lookup("MyControlServer");
            setInterface(CRI);




        } catch (IOException ioe) {
            System.out.println("Palvelimen luonti ei onnistunut");
        }
        catch (NotBoundException nbe) {

        }


    }

    public void setInterface(controlRemoteInterface CRI) {
        this.CRI = CRI;
    }

    //TODO: Create handlers for requests



    public class MyHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            try {//Luodaan html-muodossa sisältöä etäohjausyksikköön
                String alku = " <!DOCTYPE html>" +
                        "<html>" +
                        "<head>" +

                        "</head>" +

                        "<body>" +

                        "<h2>Ohjauskeskus</h2>" +
                        "<form action=" + "/test" + "method=" + "GET" + ">" +

                        "<br>" +
                        "Lampotila: " + CRI.getTemperature() + "<br>" + "<br>" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "<br>" + "<br>" +
                        "Valojen katkaisijat:" +
                        "<br>" +
                        "<br>" +
                        "\n";

                StringBuilder sb = new StringBuilder();
                sb.append(alku);
//Lisätään Valot ja niiden tilat String/HTML-koodiin
                for (int i = 1; i < 10; i++) {
                    sb.append(System.lineSeparator());

                    sb.append("<button type=" + "submit " + "id= " + Integer.toString(i) + " formaction= " + " /test" + Integer.toString(i) + ">" + " Valo " + Integer.toString(i) + " " + "</button>" + " " + "Tila "  + CRI.getStatus(i) + "<br>" +
                            "<meta http-equiv= " + "refresh " + "content=" + "3;url=http://localhost:8000/test" + "/>");
                }
                sb.append("\n" + "</form>" + "</body>" +
                        "</html>");

                //Kirjoitetaan tieto String-muodossa ja selain lukee sen html-muotoon
                t.sendResponseHeaders(200, sb.toString().getBytes().length);
                OutputStream os = t.getResponseBody();
                os.write(sb.toString().getBytes());

                os.close();

                //Kun nappulaa painetaan sivustolla, luetaan painetun nappulan tieto osoiteriviltä ja lähetetään se controlserverille
                //Tiedän, kamalaa spagettia, mutta hermo meni :DD

                String url = t.getRequestURI().toString();
                String s = url.substring(5, 6);
                CRI.executeTask(s);
            } catch (IOException ioe) {

            }

        }}


}