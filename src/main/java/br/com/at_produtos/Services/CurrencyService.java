package br.com.at_produtos.Services;

import br.com.at_produtos.Exception.ResourceNotFoundException;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
        import java.net.URI;
        import java.net.URISyntaxException;
        import java.net.http.HttpClient;
        import java.net.http.HttpRequest;
        import java.net.http.HttpResponse;

@Service
public class CurrencyService {

    public String realToDollar() {
        String uri = "http://economia.awesomeapi.com.br/json/last/USD-BRL";

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .uri(new URI(uri))
                    .version(HttpClient.Version.HTTP_2)
                    .build();

            HttpClient client = HttpClient.newBuilder().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("Response from API: " + response.body());

            // Trata o status code
            if (response.statusCode() == 404) {
                throw new ResourceNotFoundException(response.body());
            }

            // Converte o JSON para o objeto Java
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readValue(response.body(), JsonNode.class);

            // Obtém o valor de "high" como texto
            String highValueText = jsonNode.get("USDBRL").get("high").asText();

            // Converte o texto para um double

            return response.body();
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException("Erro ao chamar a API externa", e);
        }
    }

    public double getHighValue() {
        String uri = "http://economia.awesomeapi.com.br/USD-BRL/1";

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .uri(new URI(uri))
                    .version(HttpClient.Version.HTTP_2)
                    .build();

            HttpClient client = HttpClient.newBuilder().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Trata o status code
            if (response.statusCode() == 404) {
                throw new ResourceNotFoundException(response.body());
            }

            // Converte o JSON para o objeto Java
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(response.body());
            JsonNode highNode = rootNode.path("USDBRL").path("high");

            // Verifica se o nó "high" foi encontrado
            if (highNode.isMissingNode()) {
                throw new RuntimeException("Valor 'high' não encontrado na resposta JSON");
            }

            // Retorna o valor como double
            String highValueAsString = highNode.asText();
            double highValue = Double.parseDouble(highValueAsString);
            return highValue;

        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException("Erro ao chamar a API externa", e);
        }
    }
}
