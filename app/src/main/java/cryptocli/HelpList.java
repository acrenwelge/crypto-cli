package cryptocli;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Currency;
import java.util.List;

import com.google.gson.JsonParseException;

import models.Coin;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Help;
import picocli.CommandLine.Help.ColorScheme;
import picocli.CommandLine.IHelpCommandInitializable2;
import picocli.CommandLine.Parameters;
import util.CoinFileReaderWriter;

@Command(name = "list", description = "List out possible coin and currency values")
public class HelpList implements Runnable {

    @Parameters String whatToList;

    @Override
    public void run() {
        PrintWriter outWriter = new PrintWriter(System.out);
        PrintWriter errWriter = new PrintWriter(System.err);
        if (whatToList.equals("coins")) {
            try {
                List<Coin> coins = CoinFileReaderWriter.getCoinListFromFile();
                outWriter.println("List of possible cryptocurrency values:");
                coins.forEach(coin -> {
                    outWriter.println(coin.id);
                });
                outWriter.println();
            } catch (JsonParseException e) {
                errWriter.println("There was a problem parsing the coin list JSON string");
            } catch (IOException e) {
                errWriter.println("There was a problem reading the coin list file");
            }
        } else if (whatToList.equals("currencies")) {
            outWriter.println("List of possible currency values (must use currency code):");
            outWriter.format("%-40s|%5s | %s%n", "Name","Code","Symbol");
            outWriter.println("-".repeat(55));
            Currency.getAvailableCurrencies().forEach(cur -> 
                outWriter.format("%-40s|%5s | %s%n", 
                cur.getDisplayName(), 
                cur.getCurrencyCode(),
                cur.getSymbol())
            );
            outWriter.println();
        } else {
            errWriter.println("ERROR - must specify either 'coins' or 'currencies' to list");
        }
    }
}
