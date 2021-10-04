package models;

import java.math.BigDecimal;

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

}
