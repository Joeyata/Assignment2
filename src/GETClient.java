import java.io.*;
import java.net.*;

public class GETClient {
    private String server_name;
    private String port_number;
    private LamportClock lamportClock = new LamportClock();

    public GETClient(String url, String port) {
        this.server_name = url;
        this.port_number = port;
    }

    public void Get_Request() {
        try {
            URL url = new URL(server_name + (port_number != null ? "/" + port_number : ""));

            HttpURLConnection link = (HttpURLConnection) url.openConnection();

            link.setRequestMethod("GET");

            int response = link.getResponseCode();

            if (response == HttpURLConnection.HTTP_OK) //200
            {
                BufferedReader input_stream = new BufferedReader(new InputStreamReader(link.getInputStream()));
                StringBuffer bf = new StringBuffer();

                while (input_stream.readLine() != null) {
                    bf.append(input_stream.readLine());
                }
                input_stream.close();

                System.out.println("AggregationServer give data: " + bf.toString());
            } else {
                System.out.println("get request not link");
            }
        } catch (Exception e) {
            System.err.println("Failure to send get request: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("Usage: java GETClient <server_Url> [<port_number>]");
            System.exit(1);
        }

        String server_name = args[0];
        String port_number = args.length > 1 ? args[1] : null;

        GETClient client = new GETClient(server_name, port_number);
        client.Get_Request();
    }
}
