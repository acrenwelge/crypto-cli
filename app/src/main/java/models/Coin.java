package models;

import java.math.BigDecimal;
import java.math.BigInteger;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Coin {
    public String id;
    public String symbol;
    public String name;
    public String description;
    public String hashAlgorithm;
    public double currentPrice;
    public BigDecimal marketCap;
    public int marketCapRank;
    public BigDecimal maxSupply;
    public BigDecimal circulatingSupply;

    public static class CoinBuilder {
        public Coin coin = new Coin();
        public CoinBuilder() {}
        public CoinBuilder addId(String id) {
            coin.id = id;
            return this;
        }
        public CoinBuilder addSymbol(String symbol) {
            coin.symbol = symbol;
            return this;
        }
        public CoinBuilder addName(String name) {
            coin.name = name;
            return this;
        }
        public CoinBuilder addDescription(String description) {
            coin.description = description;
            return this;
        }
        public CoinBuilder addHashAlgorithm(String algo) {
            coin.hashAlgorithm = algo;
            return this;
        }
        public CoinBuilder addCurrentPrice(double price) {
            coin.currentPrice = price;
            return this;
        }
        public CoinBuilder addMarketCap(BigDecimal marketCap) {
            coin.marketCap = marketCap;
            return this;
        }
        public CoinBuilder addMarketCapRank(int rank) {
            coin.marketCapRank = rank;
            return this;
        }
        public CoinBuilder addMaxSupply(BigDecimal maxSupply) {
            coin.maxSupply = maxSupply;
            return this;
        }
        public CoinBuilder addCirculatingSupply(BigDecimal circulatingSupply) {
            coin.circulatingSupply = circulatingSupply;
            return this;
        }
        public Coin build() {
            return this.coin;
        }
    }

    public static Coin fromJsonString(String rawJson) {
        JsonObject rootObj = JsonParser.parseString(rawJson).getAsJsonObject();
        String id = rootObj.get("id").getAsString();
        String symbol = rootObj.get("symbol").getAsString();
        String name = rootObj.get("name").getAsString();
        String algo = rootObj.get("hashing_algorithm").getAsString();
        String descr = rootObj.get("description").getAsJsonObject().get("en").getAsString();
        JsonObject market = rootObj.get("market_data").getAsJsonObject();
        double price = market
            .get("current_price").getAsJsonObject()
            .get("usd").getAsDouble();
        BigDecimal marketCap = market
            .get("market_cap").getAsJsonObject()
            .get("usd").getAsBigDecimal();
        int marketCapRank = rootObj.get("market_cap_rank").getAsInt();
        BigDecimal maxSupply = market.get("max_supply").getAsBigDecimal();
        BigDecimal circulatingSupply = market.get("circulating_supply").getAsBigDecimal();
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
}
