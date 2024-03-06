package Main.Utilities;

import com.google.gson.Gson;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class requestExecutor {
    private static final HttpClient httpClient = HttpClient.newHttpClient();
    private static final Gson gson = new Gson();

    public static HttpResponse<String> sendPostRequest(String endpoint, Object body) {
        String json = gson.toJson(body);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endpoint))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();
        try{
            return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
