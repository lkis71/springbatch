package toyproject.springbatch.api;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Slf4j
public class TourListApiConnection {

    private static final String API_KEY = "DOLyJXlKA1Kmj7ewrzoYcfYL8EF%2FxGoHsBio2NjC5yFICe%2BKDKZfOASTsFU78GbuDkrEarQqd8nKGvCg%2FW4Cag%3D%3D";

    public static JSONObject getTourList() {

        JSONObject dataBody = new JSONObject();

        try {
            StringBuffer apiUrl = new StringBuffer();
            apiUrl.append("http://apis.data.go.kr/B551011/PhotoGalleryService1/galleryList1");
            apiUrl.append("?numOfRows=").append("10");
            apiUrl.append("&pageNo=").append("1");
            apiUrl.append("&MobileOS=").append("ETC");
            apiUrl.append("&MobileApp=").append("AppTest");
            apiUrl.append("&arrange=").append("A");
            apiUrl.append("&_type=").append("json");
            apiUrl.append("&serviceKey=").append(API_KEY);

            URL url = new URL(apiUrl.toString());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("auth", "auth");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuffer response = new StringBuffer();
            String inputLine;
            while((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();

            JSONObject data = new JSONObject(String.valueOf(response));

            JSONObject responseData = data.getJSONObject("response");
            dataBody = responseData.getJSONObject("body");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return dataBody;
    }
}
