import java.io.*;
import java.net.*;
import java.util.Map;

import com.google.gson.*;

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
                StringBuilder sb = new StringBuilder();
                String rline;
                while ((rline = input_stream.readLine()) != null) {
                    sb.append(rline);
                }
                input_stream.close();

                System.out.println("AggregationServer give data: " + sb.toString());

                JsonObject jsonObject = new JsonParser().parse(sb.toString()).getAsJsonObject();
                for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
                    System.out.println(entry.getKey() + ": " + entry.getValue());
                }


            } else {
                System.out.println("get request not link");
            }
        } catch (Exception e) {
            System.err.println("Failure to send get request: " + e.getMessage());
            System.out.println("Recovery mode activated. Attempting to resend the request...");
            try {
                // Sleep for a while before attempting to resend the request
                Thread.sleep(5000);

                // Attempt to resend the request
                Get_Request();
            } catch (InterruptedException err) {
                System.err.println("Failed to sleep: " + err.getMessage());
            }
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

        /*
        GETClient client = new GETClient("http://localhost:6971/", null);
        client.Get_Request();
        */

    }
}
