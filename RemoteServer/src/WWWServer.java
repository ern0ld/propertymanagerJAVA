
import java.io.*;
import java.net.InetSocketAddress;
import com.sun.net.httpserver.HttpServer;

import java.net.URL;
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
//import javax.servlet.RequestDispatcher;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
//import javax.servlet.http.Cookie;
import javax.swing.text.html.HTML;





public class WWWServer {

    private HttpServer server;
    controlRemoteInterface CRI;

    public WWWServer(InetSocketAddress address) {
        //TODO: Create http-server instance and run it
        try {
            server = HttpServer.create(new InetSocketAddress(8000),
                    0);

            server.setExecutor(Executors.newCachedThreadPool());
            server.createContext("/test", new MyHandler());

            server.start();
            Registry reg = LocateRegistry.getRegistry("Localhost", 5300);
            controlRemoteInterface CRI = (controlRemoteInterface) reg.lookup("MyControlServer");
            setInterface(CRI);
            String str = "3";
            System.out.println("RMI returns:" + CRI.executeTask(str));
           /*Document doc = Jsoup.connect("http://localhost:8080/REST_war_exploded/helloworld").get();
            for (Element value : doc.select("input")){
                if(value.attr("value").equals("On") && value.attr("checked") != null && value.attr("name").equals("Valo1")) {
                    System.out.println("Valo1 on päällä");
                }
                if(value.attr("value").equals("On") && value.attr("checked") != null && value.attr("name").equals("Valo2")) {
                    System.out.println("Valo2 on päällä");
                }
            }*/


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


            OutputStream oss = t.getResponseBody();
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(oss));
            bw.write( "<h2>Valonohjaus</h2>" + "<br>" +
                    "Aseta lampotila:" + "<br>" +
                    "<input type=" + "text" + "name=" + "firstname"+  "value=>" +  "<input type=" + "submit>" +
                    "<br>" +
                    "<br>" +
                    "<br>" +

                    "Valojen katkaisijat:"+ "<br>"+
                    "<br>" +
                    "<input type=" + "submit>" + "<value= " + CRI.getID() +
                    "ON:"+ "<input type="+ "radio>" + "<value=" +"On>"+
                    "OFF:" +"<input type=" + "radio>" + "<value=" + "Off>"+
                    "<br>" +
                    "<input type="+ "submit"+ "value="+ "Valo2>"+
                    "ON:"+ "<input type="+ "radio" + "value=" +"On>"+
                    "OFF:" +"<input type=" + "radio" + "value=" + "Off>"+
                    "<br>" +
                    "<input type="+ "submit"+ "value="+ "Valo3>"+
                    "ON:"+ "<input type="+ "radio" + "value=" +"On>"+
                    "OFF:" +"<input type=" + "radio" + "value=" + "Off>"+
                    "<br>" +
                    "<input type="+ "submit"+ "value="+ "Valo4>"+
                    "ON:"+ "<input type="+ "radio" + "value=" +"On>"+
                    "OFF:" +"<input type=" + "radio" + "value=" + "Off>"+
                    "<br>" +
                    "<input type="+ "submit"+ "value="+ "Valo5>"+
                    "ON:"+ "<input type="+ "radio" + "value=" +"On>"+
                    "OFF:" +"<input type=" + "radio" + "value=" + "Off>"+
                    "<br>" +
                    "<input type="+ "submit"+ "value="+ "Valo6>"+
                    "ON:"+ "<input type="+ "radio" + "value=" +"On>"+
                    "OFF:" +"<input type=" + "radio" + "value=" + "Off>"+
                    "<br>" +
                    "<input type="+ "submit"+ "value="+ "Valo7>"+
                    "ON:"+ "<input type="+ "radio" + "value=" +"On>"+
                    "OFF:" +"<input type=" + "radio" + "value=" + "Off>"+
                    "<br>" +
                    "<input type="+ "submit"+ "value="+ "Valo8>"+
                    "ON:"+ "<input type="+ "radio" + "value=" +"On>"+
                    "OFF:" +"<input type=" + "radio" + "value=" + "Off>"+
                    "<br>" +
                    "<input type="+ "submit"+ "value="+ "Valo9>"+
                    "ON:"+ "<input type="+ "radio" + "value=" +"On>"+
                    "OFF:" +"<input type=" + "radio" + "value=" + "Off>"+
                    "<br>" +



                    "</body>"+
                    "</html>"
);
            bw.flush();


          /// File input = new File("Weblomake.html");
//t.sendResponseHeaders(200, file.length());
           //try (OutputStream oss = t.getResponseBody()) {
             //   Files.copy(file.toPath(), oss);
           // }

            //t.sendResponseHeaders(200, ID.length);
            //os.write(ID);
           /* File input = new File("Weblomake.html");
            t.sendResponseHeaders(200, input.length());
            try (OutputStream oss = t.getResponseBody()) {
                Files.copy(input.toPath(), oss);*/


                try {
                    URL address = new URL("http://localhost:8000/test");
                    InputStream in = address.openStream();
                    StringBuilder response = new StringBuilder();
                    //
                    // Map<String, String> params = queryToMap(t.getRequestURI().getQuery());
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder result = new StringBuilder();

                    String line;

                    while ((line = reader.readLine()) != null) {
                        result.append(line);


                        //System.out.println(params.size());

                        System.out.println(result.toString());

                    }
                } catch (IOException ioe) {

                }
            }
        }


       /* public static Map<String, String> queryToMap(String query) {
            Map<String, String> result = new HashMap<String, String>();
            for (String param : query.split("&")) {
                String pair[] = param.split("=");
                if (pair.length > 1) {
                    result.put(pair[0], pair[1]);
                } else {
                    result.put(pair[0], "");
                }
            }
            return result;
        }*/


    }



