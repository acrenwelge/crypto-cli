package views;

import models.Coin;

public class CoinView {
    private CoinView() {}
    public static void display(Coin c) {
        System.out.printf(
        "| %10s | %10s | %10s | %15s | %n"
    +   "| %10s | %10s | %10s | %15s | %n"
        , "ID","Name","Symbol", "Hash Algorithm"
        , c.id, c.name, c.symbol, c.hashAlgorithm);
        System.out.println();
        System.out.println("DESCRIPTION: " + c.description);
        System.out.println();
        System.out.printf(
        "STATISTICS: %n"
    +   "- Max Supply: %f.0%n"
    +   "- Circulating Supply: %f.0%n"
    +   "- Market Cap: %f.0%n"
    +   "- Market Cap Rank: %d%n"
        ,c.maxSupply,c.circulatingSupply,c.marketCap,c.marketCapRank);
    }
}