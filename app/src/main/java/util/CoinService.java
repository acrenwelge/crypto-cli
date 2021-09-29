package util;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Redirect;
import java.net.http.HttpClient.Version;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Singleton class for getting coin information from the API
 */
public class CoinService {
    CustomLogger logger = CustomLogger.getInstance();

    private CoinService() {}

    private static CoinService instance;

    public static CoinService getInstance() {
        if (instance == null) {
            instance = new CoinService();
        }
        return instance;
    }

    public static final String BASE_URL = "https://api.coingecko.com/api/v3";

    private static HttpClient client = HttpClient.newBuilder()
        .version(Version.HTTP_1_1)
        .followRedirects(Redirect.NORMAL)
        .connectTimeout(Duration.ofSeconds(20))
        .build();

    public String getCoin(String coin) throws IOException, InterruptedException {
        final String URL = BASE_URL.concat("/coins/").concat(coin);
        logger.trace(URL);
        HttpRequest req = HttpRequest.newBuilder()
            .uri(URI.create(URL))
            .build();
        HttpResponse<String> resp = client.send(req, BodyHandlers.ofString());
        return resp.body();
    }

    public String getCoinList() throws IOException, InterruptedException {
        final String URL = BASE_URL.concat("/coins/list");
        logger.trace(URL);
        HttpRequest req = HttpRequest.newBuilder()
            .uri(URI.create(URL))
            .build();
        HttpResponse<String> resp = client.send(req, BodyHandlers.ofString());
        return resp.body();
    }

    public String getCoinPrices(String[] coins, String[] denominations) 
        throws IOException, InterruptedException  {
        final String URL = BASE_URL
            .concat("/simple/price?ids=")
            .concat(String.join(",", coins))
            .concat("&vs_currencies=")
            .concat(String.join(",", denominations));
        logger.trace(URL);
        HttpRequest req = HttpRequest.newBuilder()
            .uri(URI.create(URL))
            .build();
        HttpResponse<String> resp = client.send(req, BodyHandlers.ofString());
        return resp.body();
    }

    public String getCoinHistory(String coin, String denomination, int daysAgo) 
        throws IOException, InterruptedException  {
        final String URL = BASE_URL
            .concat("/coins/").concat(coin)
            .concat("/market_chart?days=").concat(String.valueOf(daysAgo))
            .concat("&vs_currency=").concat(denomination);
        logger.trace(URL);
        HttpRequest req = HttpRequest.newBuilder()
            .uri(URI.create(URL))
            .build();
        HttpResponse<String> resp = client.send(req, BodyHandlers.ofString());
        return resp.body();
    }

    public String getCoinPriceOnDate(String coin, LocalDate date) 
        throws IOException, InterruptedException  {
        final String URL = BASE_URL
            .concat("/coins/").concat(coin)
            .concat("/history?date=")
            .concat(date.format(DateTimeFormatter.ofPattern("dd-mm-yyyy")));
        logger.trace(URL);
        HttpRequest req = HttpRequest.newBuilder()
            .uri(URI.create(URL))
            .build();
        HttpResponse<String> resp = client.send(req, BodyHandlers.ofString());
        return resp.body();
    }
}