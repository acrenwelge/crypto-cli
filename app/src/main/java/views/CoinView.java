package views;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import models.Coin;
import models.PriceSnapshot;
import models.SimpleCoin;
import util.CustomLogger;

public class CoinView {
    private CoinView() {}
    static CustomLogger logger = CustomLogger.getInstance();

    public static void displayCoinInfo(Coin c) {
        logger.print(
        "| %10s | %10s | %10s | %15s | %n"
    +   "| %10s | %10s | %10s | %15s | %n"
        , "ID","Name","Symbol", "Hash Algorithm"
        , c.id, c.name, c.symbol, c.hashAlgorithm);
        logger.print();
        logger.print(
        "STATISTICS: %n"
    +   "- Max Supply: %,.0f%n"
    +   "- Circulating Supply: %,.0f%n"
    +   "- Market Cap: $%,.0f%n"
    +   "- Market Cap Rank: %d%n"
        ,c.maxSupply,c.circulatingSupply,c.marketCap,c.marketCapRank);
        logger.print();
        logger.print("DESCRIPTION: " + c.description);
        logger.print();
    }

    public static void displayPriceTable(String jsonResponse, String[] coinNames, List<Currency> denominations) {
        JsonObject root = JsonParser.parseString(jsonResponse).getAsJsonObject();
        logger.print("=".repeat(80).concat("%n"));
        logger.print("%80s%n","Coin Prices");
        logger.print("=".repeat(80).concat("%n"));
        logger.print("| %30s |","Coin");
        for (Currency currency : denominations) {
            logger.print("%13s |",currency.getCurrencyCode());
        }
        logger.print("%n");
        for (String name : coinNames) {
            logger.print("| %30s |",name);
            if (root.has(name)) {
                JsonObject denoms = root.get(name).getAsJsonObject();
                for (Currency d : denominations) {
                    logger.print("%2s %,10.0f |", 
                    d.getSymbol(),
                    denoms.get(d.getCurrencyCode().toLowerCase()).getAsBigDecimal());
                }
            } else {
                logger.print("N/A%n");
                break;
            }
            logger.print("%n");
        }
        logger.print("-".repeat(80).concat("%n"));
    }

    public static void displayPriceWatchHeader(String coinName) {
        logger.print("=".repeat(33).concat("%n"));
        logger.print("Price of %s%n",coinName);
        logger.print("=".repeat(33).concat("%n"));
        logger.print("%20s | %10s %n","Time","Price");
        logger.print("-".repeat(33).concat("%n"));
    }
    public static void displayPriceWatchRow(SimpleCoin coin) {
        coin.getPrices().forEach((cur, price) -> 
            logger.print("%20s | %s%,9.0f %n",
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")),
                cur.getSymbol(),
                price));
    }
    public static void displayPriceHistory(List<PriceSnapshot> prices, Currency currency) {
        logger.print("%20s | %10s %n","Date & Time","Price");
        logger.print("-".repeat(33).concat("%n"));
        for (PriceSnapshot ps : prices) {
            logger.print("%20s | %s%,9.0f %n",
                ps.preciseTime.format(DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:ss")),
                currency.getSymbol(),
                ps.price);
        }
    }

    public static void displayCoinOnDate(Coin c, LocalDate date) {
        logger.print("PRICE OF %s ON %s%n",c.id.toUpperCase(),date.format(DateTimeFormatter.ISO_DATE));
        logger.print("=".repeat(30).concat("%n"));
        logger.print("PRICE: $%,.0f%nMARKET CAP: $%,.0f%n",c.currentPrice, c.marketCap);
    }
}