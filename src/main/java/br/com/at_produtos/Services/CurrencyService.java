package br.com.at_produtos.Services;

import br.com.at_produtos.Exception.ResourceNotFoundException;
import br.com.at_produtos.Model.CurrencyResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CurrencyService {

    Logger logger = LoggerFactory.getLogger(CurrencyService.class);

    public double getPriceUSD() {
        String apiUrl = "https://api.invertexto.com/v1/currency/USD_BRL?token=5910|IRxCD4Fz9qGRtEBCPJjV4MytiASHLo1D";
        RestTemplate restTemplate = new RestTemplate();
        CurrencyResponse response = restTemplate.getForObject(apiUrl, CurrencyResponse.class);

        try {
            if (response != null && response.getUsdBrl() != null) {
                logger.info("Preço em dólar obtido com sucesso");
                return response.getUsdBrl().getPrice();
            } else {
                throw new ResourceNotFoundException("Resposta da API inválida.");
            }
        } catch (Exception e) {
            throw new ResourceNotFoundException("Erro ao obter preço em dólar" + e.getMessage());
        }
    }
}
