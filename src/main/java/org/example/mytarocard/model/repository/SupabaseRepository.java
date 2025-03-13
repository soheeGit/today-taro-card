package org.example.mytarocard.model.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.cdimascio.dotenv.Dotenv;
import org.example.mytarocard.model.constant.LLMModel;
import org.example.mytarocard.model.dto.GeminiPayload;
import org.example.mytarocard.model.dto.GeminiResponse;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class SupabaseRepository {
    private SupabaseRepository() {}
    private final static SupabaseRepository instance = new SupabaseRepository();
    public static SupabaseRepository getInstance() {
        return instance;
    }

    private final Logger logger = Logger.getLogger(SupabaseRepository.class.getName());
    private final HttpClient client = HttpClient.newHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();
    private final Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();

    public void save(String id, String text, String image) throws IOException, InterruptedException {
        String supabaseKey = dotenv.get("SUPABASE_KEY");
        String[] headers = new String[]{
                "apikey", supabaseKey,
                "Authorization", "Bearer %s".formatted(supabaseKey),
                "Content-Type", "application/json",
                "Prefer", "return=minimal"
        };
        Map<String, String> map = new HashMap<>();
        map.put("id", id);
        map.put("text", text);
        map.put("image", image);
        String body = mapper.writeValueAsString(map);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(dotenv.get("SUPABASE_URL") + "/rest/v1/TARO"))
                .method("POST", HttpRequest.BodyPublishers.ofString(body))
                .headers(headers)
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        logger.info("%d".formatted(response.statusCode()));
        if(response.statusCode() >= 400) {
            logger.info(response.body());
        }
    }

    public String[] findById(String id) throws IOException, InterruptedException {
        String supabaseKey = dotenv.get("SUPABASE_KEY");
        String[] headers = new String[]{
                "apikey", supabaseKey,
                "Authorization", "Bearer %s".formatted(supabaseKey),
                "Content-Type", "application/json",
//                "Prefer", "return=minimal"
        };
        String url = dotenv.get("SUPABASE_URL") + "/rest/v1/TARO?select=*&id=eq.%s".formatted(id);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .method("GET", HttpRequest.BodyPublishers.ofString(""))
                .headers(headers)
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        logger.info("%d".formatted(response.statusCode()));
        if (response.statusCode() >= 400) {
            logger.info(response.body());
        }
        System.out.println(response.body());
        // 응답을 파싱하여 text와 image 추출
        List<Map<String, Object>> results = mapper.readValue(response.body(), new TypeReference<List<Map<String, Object>>>() {});

        if (results == null || results.isEmpty()) {
            return new String[]{"", ""}; // 결과가 없으면 빈 배열 반환
        }

        // 첫 번째 결과에서 text와 image 추출
        Map<String, Object> data = results.get(0);
        String text = data.get("text") != null ? data.get("text").toString() : "";
        String image = data.get("image") != null ? data.get("image").toString() : "";

        return new String[]{text, image};
    }
}
