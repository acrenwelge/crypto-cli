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
import java.util.Map;

public class CoinService {
    private CoinService() {}

    public static final String BASE_URL = "https://api.coingecko.com/api/v3";

    private static HttpClient client = HttpClient.newBuilder()
        .version(Version.HTTP_1_1)
        .followRedirects(Redirect.NORMAL)
        .connectTimeout(Duration.ofSeconds(20))
        .build();

    public static String getCoin(String coin) throws IOException, InterruptedException {
        HttpRequest req = HttpRequest.newBuilder()
            .uri(URI.create(BASE_URL.concat("/coins/").concat(coin)))
            .build();
        HttpResponse<String> resp = client.send(req, BodyHandlers.ofString());
        return resp.body();
    }

    public static String getCoinList() throws IOException, InterruptedException {
        HttpRequest req = HttpRequest.newBuilder()
            .uri(URI.create(BASE_URL.concat("/coins/list")))
            .build();
        HttpResponse<String> resp = client.send(req, BodyHandlers.ofString());
        return resp.body();
    }

    public static String getCoinPrices(String[] coins, String[] denominations) 
        throws IOException, InterruptedException  {
        final String URL = BASE_URL
            .concat("/simple/price?ids=")
            .concat(String.join(",", coins))
            .concat("&vs_currencies=")
            .concat(String.join(",", denominations));
        HttpRequest req = HttpRequest.newBuilder()
            .uri(URI.create(URL))
            .build();
        HttpResponse<String> resp = client.send(req, BodyHandlers.ofString());
        return resp.body();
    }

    public static String getCoinHistory(String coin, String denomination, int daysAgo) 
        throws IOException, InterruptedException  {
        final String URL = BASE_URL
            .concat("/coins/").concat(coin)
            .concat("/market_chart?days=").concat(String.valueOf(daysAgo))
            .concat("&vs_currency=").concat(denomination);
        HttpRequest req = HttpRequest.newBuilder()
            .uri(URI.create(URL))
            .build();
        HttpResponse<String> resp = client.send(req, BodyHandlers.ofString());
        return resp.body();
    }

    public static String getCoinPriceOnDate(String coin, LocalDate date) 
        throws IOException, InterruptedException  {
        final String URL = BASE_URL
            .concat("/coins/").concat(coin)
            .concat("/history?date=")
            .concat(date.format(DateTimeFormatter.ofPattern("dd-mm-yyyy")));
        HttpRequest req = HttpRequest.newBuilder()
            .uri(URI.create(URL))
            .build();
        HttpResponse<String> resp = client.send(req, BodyHandlers.ofString());
        return resp.body();
    }
}