import java.io.*;
import java.net.*;
import java.util.concurrent.*;
import com.sun.net.httpserver.*;
import com.google.gson.*;

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

            InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), "utf-8");
            BufferedReader br = new BufferedReader(isr);
            //System.out.println("haha0");
            String jsonString = br.readLine();
            //System.out.println("haha3");
            Gson gson = new Gson(); // Initialize Gson object
            //System.out.println("haha1");
            WeatherInfo weather = gson.fromJson(jsonString, WeatherInfo.class);

            //System.out.println(jsonString);
            // Check if the weather data is valid
            if (weather == null || !weather.isValid()) {
                exchange.sendResponseHeaders(500, 0); // Internal server error
                return;
            }
            //System.out.println("haha4");

            // Add or update the weather data in the map
            weatherInfo_Map.put(weather.get_id(), weather);

            // Update the last contact time for this content server
            Time_Map.put(weather.get_id(), lamportClock.next_time());
            //System.out.println("haha5");
            // Send a response
            String response = "Weather data updated successfully";
            exchange.sendResponseHeaders(200, response.length());
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            //System.out.println("haha6");
            os.close();
        }

        private void handle_Get_Request(HttpExchange exchange) throws IOException {
            //System.out.println("nb1");
            Gson gson = new Gson(); // Initialize Gson object
            //System.out.println("nb2");
            Integer maxm = -1;
            WeatherInfo recent = new WeatherInfo(null, null,null,null,null,null,null,null);
            //System.out.println("nb3");
            for (String key : weatherInfo_Map.keySet()) {
                if(Time_Map.get(key) > maxm ) {maxm = Time_Map.get(key);recent = weatherInfo_Map.get(key);}
            }
            //System.out.println("nb4");
            String jsonString = gson.toJson(recent);
            //System.out.println(jsonString);
            // Send a response
            exchange.sendResponseHeaders(200, jsonString.length());
            OutputStream os = exchange.getResponseBody();
            os.write(jsonString.getBytes());
            os.close();
        }

    }

    public static void main(String[] args) {

        int port = 6971;
        AggregationServer server = new AggregationServer(port);
        server.start();

    }
}