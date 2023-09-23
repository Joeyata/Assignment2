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
            URL url = new URL(server_name);

            HttpURLConnection link = (HttpURLConnection) url.openConnection();

            link.setRequestMethod("PUT");

            int response = link.getResponseCode();

            if (response == HttpURLConnection.HTTP_OK) {
                // handle put
            } else {
                System.out.println("put request not link");
            }
        } catch (Exception e) {
            System.err.println("Failure to send put request: " + e.getMessage());
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
