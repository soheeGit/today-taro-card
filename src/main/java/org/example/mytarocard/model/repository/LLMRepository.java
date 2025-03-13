package org.example.mytarocard.model.repository;

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
import java.util.List;
import java.util.logging.Logger;

public class LLMRepository {
    private LLMRepository() {}
    private final static LLMRepository instance = new LLMRepository();
    public static LLMRepository getInstance() {
        return instance;
    }

    private final Logger logger = Logger.getLogger(LLMRepository.class.getName());
    private final HttpClient client = HttpClient.newHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();
    private final Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();

    public String callModel(LLMModel model, String prompt) throws IOException, InterruptedException {
        String url = switch (model.platform) {
            case GEMINI -> "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key=%s".formatted(dotenv.get("GEMINI_KEY"));
            default -> throw new RuntimeException("Unknown platform: " + model.platform);
        };
        String[] headers = switch (model.platform) {
            case GEMINI -> new String[]{"Content-Type", "application/json"};
            default -> throw new IllegalStateException("Unexpected platform: " + model.platform);
        };
        String body = switch (model.modelName) {
            case "gemini-2.0-flash" -> mapper.writeValueAsString(new GeminiPayload(
                    List.of(new GeminiPayload.Content("user", List.of(
                            new GeminiPayload.Part(prompt))))
            ));
            default -> throw new RuntimeException("Unexpected model: " + model);
        };
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .method("POST", HttpRequest.BodyPublishers.ofString(body))
                .headers(headers)
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        logger.info("%d".formatted(response.statusCode()));
        if (response.statusCode() >= 400) {
            logger.info(response.body());
        }
        System.out.println(response.body());
        switch (model.modelName) {
            case "gemini-2.0-flash" -> {
                return mapper.readValue(response.body(), GeminiResponse.class).candidates().get(0).content().parts().get(0).text();
            }
            default -> throw new RuntimeException("Unexpected model: " + model);
        }
    }
}
