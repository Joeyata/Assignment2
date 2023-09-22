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
            // send request
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