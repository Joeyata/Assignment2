import java.io.*;
import java.net.*;
import com.google.gson.*;


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
            WeatherInfo weatherInfo = ReadInfo(filePath);
            String jsonString = new Gson().toJson(weatherInfo);
            link.setRequestMethod("PUT");
            link.setRequestProperty("User-Agent", "ATOMClient/1/0");
            link.setRequestProperty("Content-Type", "application/json");
            link.setRequestProperty("Content-Length", String.valueOf(jsonString.length()));

            // Send the JSON data
            link.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(link.getOutputStream());
            wr.writeBytes(jsonString);
            wr.flush();
            wr.close();


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
    private WeatherInfo ReadInfo(String filepath)
    {
        WeatherInfo info = new WeatherInfo(null,null,null,null,null,null,null,null);

        try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                int len = line.length();
                String fr2 = line.substring(0,2);
                if(fr2.equals("id")) info.set_id(line.substring(3,len));
                else if(fr2.equals("na")) info.set_name(line.substring(5,len));
                else if(fr2.equals("st")) info.set_state(line.substring(6,len));
                else if(fr2.equals("ti")) info.set_time_zone(line.substring(10,len));
                else if(len>=21 && line.substring(0,20).equals("local_date_time_full"))
                    info.set_full_time(line.substring(21,len));
                else if(fr2.equals("ai")) info.set_air_temp(line.substring(9,len));
                else if(fr2.equals("ap")) info.set_apparent_t(line.substring(11,len));
                else if(fr2.equals("cl")) info.set_cloud(line.substring(6,len));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return info;
    }
    public static void main(String[] args) {

        if (args.length < 2) {
            System.err.println("Usage: java ContentServer <server_Url> <filePath>");
            System.exit(1);
        }

        String server_name = args[0];
        String filePath = args[1];
        ContentServer server = new ContentServer(server_name, filePath);

        /*
        ContentServer server = new ContentServer("http://localhost:6971/" , "./data/weather1.txt");
        server.Put_Request();

        */
    }
}
