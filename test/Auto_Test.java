import java.io.*;
import java.net.*;
import com.google.gson.*;

import java.util.Map;
import java.util.concurrent.*;
import com.sun.net.httpserver.*;
import static org.junit.Assert.*;
import org.junit.Test;

public class Auto_Test {

    public static void testReadToJson() throws IOException{
        String file_path = "data/weather_test.txt";
        WeatherInfo weather = ContentServer.ReadInfo(file_path);
        assertTrue(weather.isValid());
        String jsonString = new Gson().toJson(weather);
        JsonParser parser = new JsonParser();
        JsonObject jsonObject = parser.parse(jsonString).getAsJsonObject();
        String id = jsonObject.get("id").getAsString();
        String name = jsonObject.get("name").getAsString();
        String state = jsonObject.get("state").getAsString();
        String time_zone = jsonObject.get("time_zone").getAsString();
        String full_time = jsonObject.get("full_time").getAsString();
        String air_temp = jsonObject.get("air_temp").getAsString();
        String apparent_t = jsonObject.get("apparent_t").getAsString();
        String cloud = jsonObject.get("cloud").getAsString();
        assertEquals("testid",id);
        assertEquals("testname",name);
        assertEquals("teststate",state);
        assertEquals("testtz",time_zone);
        assertEquals("testtime",full_time);
        assertEquals("testtemp",air_temp);
        assertEquals("testap",apparent_t);
        assertEquals("testcl",cloud);
    }

    public static void PutAndGetFunction() throws IOException {
        AggregationServer server = new AggregationServer(6975);
        server.start();
        ContentServer server1 = new ContentServer("http://localhost:6975/" , "data/weather1.txt");
        ContentServer server2 = new ContentServer("http://localhost:6975/" , "data/weather2.txt");//get this
        server1.Put_Request();
        server2.Put_Request();
        //client1
        URL url = new URL("http://localhost:6975/");
        HttpURLConnection link = (HttpURLConnection) url.openConnection();

        link.setRequestMethod("GET");

        int response = link.getResponseCode();

        assertEquals(response,HttpURLConnection.HTTP_OK);  //connect successfully

        BufferedReader input_stream = new BufferedReader(new InputStreamReader(link.getInputStream()));

        String rline = input_stream.readLine();

        assertEquals(rline,"{\"id\":\"IDS76755\",\"name\":\"Sydney (beautiful place)\",\"state\":\" SA\",\"time_zone\":\"CST\",\"full_time\":\"20230624231400\",\"air_temp\":\"9.5\",\"apparent_t\":\"10.1\",\"cloud\":\"No cloud\"}");

    }


    public static void main(String[] args) {
        try {
            testReadToJson();
            PutAndGetFunction();
            System.out.println("test successfully");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
