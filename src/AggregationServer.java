import java.io.*;
import java.net.*;
import java.util.concurrent.*;
import com.sun.net.httpserver.*;

public class AggregationServer {
    private ConcurrentHashMap<String, Integer> Time_Map = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, WeatherInfo> weatherInfo_Map = new ConcurrentHashMap<>();
    // WeatherInfo id -> WeatherInfo
    // WeatherInfo id -> Lamport Clock
    private LamportClock lamportClock = new LamportClock();
    private int port;

    public AggregationServer(int port) {
        this.port = port;
    }

    public void start() {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
            server.createContext("/", new Handle_Request());
            server.setExecutor(null);
            server.start();
        } catch (IOException e) {
            System.err.println("Start Aggregation Server Failed: " + e.getMessage());
        }
    }

    class Handle_Request implements HttpHandler {
        public void handle(HttpExchange exc) throws IOException {
            try {
                String method = exc.getRequestMethod();
                if (method.equalsIgnoreCase("PUT")) {
                    handle_Put_Request(exc);
                } else if (method.equalsIgnoreCase("GET")) {
                    handle_Get_Request(exc);
                } else {
                    exc.sendResponseHeaders(400, 0); // Bad request
                }
                // remove servers elder than 30s
                Time_Map.entrySet().removeIf(entry -> lamportClock.get_time() - entry.getValue() > 30);
                weatherInfo_Map.entrySet().removeIf(entry -> !Time_Map.containsKey(entry.getKey()));
            } catch (Exception e) {
                System.err.println("An error occurred while handling a request: " + e.getMessage());
            }
        }

        private void handle_Put_Request(HttpExchange exchange) throws IOException {

        }

        private void handle_Get_Request(HttpExchange exchange) throws IOException {

        }

    }

    public static void main(String[] args) {
        int port = 6969;
        AggregationServer server = new AggregationServer(port);
        server.start();
    }
}