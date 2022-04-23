package util;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import models.Coin;
import models.PriceSnapshot;
import models.SimpleCoin;

public class CoinJsonParser {
    private CoinJsonParser(){}

    public static Coin fromJsonString(String rawJson) {
        JsonObject rootObj = JsonParser.parseString(rawJson).getAsJsonObject();
        String id = rootObj.get("id").getAsString();
        String symbol = rootObj.get("symbol").getAsString();
        String name = rootObj.get("name").getAsString();
        String algo = rootObj.get("hashing_algorithm").isJsonNull() ? 
            null :
            rootObj.get("hashing_algorithm").getAsString();
        String descr = rootObj.get("description").getAsJsonObject().get("en").getAsString();
        JsonObject market = rootObj.get("market_data").getAsJsonObject();
        double price = market
            .get("current_price").getAsJsonObject()
            .get("usd").getAsDouble();
        BigDecimal marketCap = market
            .get("market_cap").getAsJsonObject()
            .get("usd").getAsBigDecimal();
        int marketCapRank = rootObj.get("market_cap_rank").getAsInt();
        BigDecimal maxSupply = market.get("max_supply").isJsonNull() ? 
            null :
            market.get("max_supply").getAsBigDecimal();
        BigDecimal circulatingSupply = market.get("circulating_supply").isJsonNull() ?
            null :
            market.get("circulating_supply").getAsBigDecimal();
        return new Coin.CoinBuilder()
            .addId(id)
            .addSymbol(symbol)
            .addName(name)
            .addDescription(descr)
            .addHashAlgorithm(algo)
            .addCurrentPrice(price)
            .addMarketCap(marketCap)
            .addMarketCapRank(marketCapRank)
            .addCirculatingSupply(circulatingSupply)
            .addMaxSupply(maxSupply)
            .build();
    }

    public static Coin fromDateSnapshotJson(String json, String currency) {
        final String lowerCaseCur = currency.toLowerCase(); // json property is in lowercase: 'usd', 'eur', etc...
        JsonObject root = JsonParser.parseString(json).getAsJsonObject();
        JsonObject prices = root.get("market_data").getAsJsonObject().get("current_price").getAsJsonObject();
        JsonObject marketCap = root.get("market_data").getAsJsonObject().get("market_cap").getAsJsonObject();
        return new Coin.CoinBuilder()
            .addId(root.get("id").getAsString())
            .addSymbol(root.get("symbol").getAsString())
            .addName(root.get("name").getAsString())
            .addCurrentPrice(prices.get(lowerCaseCur).getAsDouble())
            .addMarketCap(marketCap.get(lowerCaseCur).getAsBigDecimal())
            .build();
    }

    public static List<SimpleCoin> fromSimplePriceJson(String json, String[] coinNames, List<Currency> denominations) {
        List<SimpleCoin> list = new ArrayList<>();
        JsonObject root = JsonParser.parseString(json).getAsJsonObject();
        for (String name : coinNames) {
            SimpleCoin sc = new SimpleCoin();
            sc.id = name;
            JsonObject coin = root.get(name).getAsJsonObject();
            for (Currency denom : denominations) {
                final String lowerCaseCur = denom.getCurrencyCode().toLowerCase();
                sc.addPrice(denom, coin.get(lowerCaseCur).getAsDouble());
            }
            list.add(sc);
        }
        return list;
    }

    public static List<PriceSnapshot> extractPriceHistory(String jsonString) {
        JsonArray prices = JsonParser.parseString(jsonString)
            .getAsJsonObject().get("prices").getAsJsonArray();
        Gson gson = new Gson();
        double[][] priceHistory = gson.fromJson(prices, double[][].class);
        List<PriceSnapshot> list = new ArrayList<PriceSnapshot>();
        for (double[] entry : priceHistory) {
            PriceSnapshot ps = new PriceSnapshot();
            double epochTime = entry[0];
            LocalDateTime dateTime = Instant.ofEpochMilli((long) epochTime).atZone(ZoneOffset.UTC).toLocalDateTime();
            ps.preciseTime = dateTime;
            ps.price = entry[1];
            list.add(ps);
        }
        return list;
    }
}
