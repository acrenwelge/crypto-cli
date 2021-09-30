package views;

import models.Coin;
import util.CustomLogger;

public class CoinView {
    private CoinView() {}
    static CustomLogger logger = CustomLogger.getInstance();

    public static void display(Coin c) {
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
}