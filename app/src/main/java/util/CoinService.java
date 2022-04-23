package util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Redirect;
import java.net.http.HttpClient.Version;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import models.Coin;
import models.PriceSnapshot;

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
    public static final String DEBUG_REQUEST = "Sending request to: %s";

    private static HttpClient client = HttpClient.newBuilder()
        .version(Version.HTTP_1_1)
        .followRedirects(Redirect.NORMAL)
        .connectTimeout(Duration.ofSeconds(20))
        .build();

    public Coin getCoin(String coin) throws IOException, InterruptedException {
        final String URL = BASE_URL.concat("/coins/").concat(coin);
        URLEncoder.encode(URL,StandardCharsets.UTF_8);
        logger.trace(DEBUG_REQUEST,URL);
        HttpRequest req = HttpRequest.newBuilder()
            .uri(URI.create(URL))
            .build();
        HttpResponse<String> resp = client.send(req, BodyHandlers.ofString());
        return CoinJsonParser.fromJsonString(resp.body());
    }

    public List<Coin> getCoins(String... coins) throws IOException, InterruptedException {
        List<Coin> list = new ArrayList<>();
        for (String c : coins) {
            list.add(getCoin(c));
        }
        return list;
    }

    public List<Coin> getCoinList() throws IOException, InterruptedException {
        List<Coin> coins;
        try {
            if (CoinFileReaderWriter.cacheIsExpired()) {
                logger.info("Cache file expired - refreshing data from API");
                coins = getCoinListFromApi();
            } else {
                coins = CoinFileReaderWriter.getCoinListFromFile();
            }
        } catch (FileNotFoundException fnfe) {
            logger.print("Cache file not found - requesting data from API%n");
            coins = getCoinListFromApi();
        }
        return coins;
    }

    private List<Coin> getCoinListFromApi() throws IOException, InterruptedException {
        final String URL = BASE_URL.concat("/coins/list");
        logger.trace(DEBUG_REQUEST,URL);
        HttpRequest req = HttpRequest.newBuilder()
            .uri(URI.create(URL))
            .build();
        HttpResponse<String> resp = client.send(req, BodyHandlers.ofString());
        String rawJson = resp.body();
        logger.print("Writing coin list to cache file...%n");
        CoinFileReaderWriter.writeCoinListToFile(rawJson);
        return CoinFileReaderWriter.getCoinListFromJson(rawJson);
    }

    public String getCoinPrices(String[] coins, List<Currency> denominations) 
        throws IOException, InterruptedException  {
        String[] currencyCodes = new String[denominations.size()];
        for (int i = 0; i < denominations.size(); i++) {
            currencyCodes[i] = denominations.get(i).getCurrencyCode();
        }
        final String URL = BASE_URL
            .concat("/simple/price?ids=")
            .concat(String.join(",", coins))
            .concat("&vs_currencies=")
            .concat(String.join(",", currencyCodes));
        logger.trace(DEBUG_REQUEST,URL);
        HttpRequest req = HttpRequest.newBuilder()
            .uri(URI.create(URL))
            .build();
        HttpResponse<String> resp = client.send(req, BodyHandlers.ofString());
        return resp.body();
    }

    public List<PriceSnapshot> getCoinHistory(String coin, Currency currency, int daysAgo) 
        throws IOException, InterruptedException  {
        final String URL = BASE_URL
            .concat("/coins/").concat(coin)
            .concat("/market_chart?days=").concat(String.valueOf(daysAgo))
            .concat("&vs_currency=").concat(currency.getCurrencyCode());
        logger.trace(DEBUG_REQUEST,URL);
        HttpRequest req = HttpRequest.newBuilder()
            .uri(URI.create(URL))
            .build();
        HttpResponse<String> resp = client.send(req, BodyHandlers.ofString());
        String responseJson = resp.body();
        return CoinJsonParser.extractPriceHistory(responseJson);
    }

    public Coin getCoinPriceOnDate(String coin, LocalDate date, Currency currency)
        throws IOException, InterruptedException  {
        final String URL = BASE_URL
            .concat("/coins/").concat(coin)
            .concat("/history?date=")
            .concat(date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        logger.trace(DEBUG_REQUEST,URL);
        HttpRequest req = HttpRequest.newBuilder()
            .uri(URI.create(URL))
            .build();
        HttpResponse<String> resp = client.send(req, BodyHandlers.ofString());
        return CoinJsonParser.fromDateSnapshotJson(resp.body(), currency.getCurrencyCode());
    }
}