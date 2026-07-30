import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

/**
 * Minimal example of calling a chat-completions style Generative AI API
 * from plain Java, using only java.net.http (no extra dependencies).
 *
 * This is intentionally provider-agnostic pseudocode: point `endpoint`
 * and `apiKey` at whichever LLM API you have access to, and adjust the
 * JSON body to match that provider's request format.
 *
 * Read the API key from an environment variable — never hard-code it.
 */
public class SimpleAIClient {

    private static final String ENDPOINT = "https://api.example-ai-provider.com/v1/chat/completions";

    public static void main(String[] args) throws Exception {
        String apiKey = System.getenv("AI_API_KEY");
        if (apiKey == null || apiKey.isBlank()) {
            throw new IllegalStateException("Set the AI_API_KEY environment variable first.");
        }

        String userPrompt = "Summarize the key differences between REST and GraphQL in 3 bullet points.";
        String requestBody = buildRequestBody(userPrompt);

        HttpClient client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(ENDPOINT))
                .timeout(Duration.ofSeconds(30))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + apiKey)
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("Status: " + response.statusCode());
        System.out.println("Raw response body:");
        System.out.println(response.body());
        // In a real service you'd parse this JSON (e.g. with Jackson) and
        // extract just the generated message text before returning it
        // from your own REST controller.
    }

    private static String buildRequestBody(String userPrompt) {
        // Minimal hand-built JSON to avoid pulling in a JSON library just
        // for this example. In real code, use Jackson/Gson instead.
        String escapedPrompt = userPrompt.replace("\"", "\\\"");
        return """
                {
                  "model": "example-model-name",
                  "temperature": 0.3,
                  "messages": [
                    { "role": "system", "content": "You are a concise, accurate technical assistant." },
                    { "role": "user", "content": "%s" }
                  ]
                }
                """.formatted(escapedPrompt);
    }
}
