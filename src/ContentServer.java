import java.io.*;
import java.net.*;

public class ContentServer {
    private String server_name;
    private String filePath;
    private LamportClock lamportClock = new LamportClock();

    public ContentServer(String url, String filePath) {
        this.server_name = url;
        this.filePath = filePath;
    }

    public void Put_Request() {
        try {

        } catch (Exception e) {
            System.err.println("Failure to send get request: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        if (args.length < 2) {
            System.err.println("Usage: java ContentServer <server_Url> <filePath>");
            System.exit(1);
        }

        String server_name = args[0];
        String filePath = args[1];

        ContentServer server = new ContentServer(server_name, filePath);
        server.Put_Request();
    }
}
