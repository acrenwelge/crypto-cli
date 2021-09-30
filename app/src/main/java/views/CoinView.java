package views;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import models.Coin;
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

    public static void displayPriceTable(String jsonResponse, String[] coinNames, String[] denominations) {
        JsonObject root = JsonParser.parseString(jsonResponse).getAsJsonObject();
        logger.print("=".repeat(80).concat("%n"));
        logger.print("%80s%n","Coin Prices");
        logger.print("=".repeat(80).concat("%n"));
        logger.print("| %30s | ","Coin");
        for (String currency : denominations) {
            logger.print("%10s |",currency);
        }
        logger.print("%n");
        for (String name : coinNames) {
            logger.print("| %30s | ",name);
            if (root.has(name)) {
                JsonObject denoms = root.get(name).getAsJsonObject();
                for (String d : denominations) {
                    logger.print("%10s |", denoms.get(d).getAsBigDecimal());
                }
            } else {
                logger.print("N/A%n");
                break;
            }
            logger.print("%n");
        }
        logger.print("-".repeat(80).concat("%n"));
    }
}