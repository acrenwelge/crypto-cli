package cryptocli;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Currency;
import java.util.InputMismatchException;
import java.util.concurrent.Callable;

import models.Coin;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.ParentCommand;
import util.CoinService;
import util.CustomLogger;
import util.StatService;

@Command(name="gains",
    description = "Calculate price increase/decreases"
)
public class Gains implements Callable<Integer> {
    static CustomLogger logger = CustomLogger.getInstance();

    @Parameters LocalDate[] beginAndEndDates;

    @ParentCommand
    Crypto crypto;

    private CoinService coinService = CoinService.getInstance();

    public Integer call() throws IOException, InterruptedException {
        Currency cur = crypto.denominations.get(0); // just use first denomination
        if(beginAndEndDates.length != 2) {
            throw new InputMismatchException("Must include begin & end dates");
        }
        LocalDate begin = beginAndEndDates[0];
        LocalDate end = beginAndEndDates[1];
        for (int i=0; i < crypto.coinNames.length; i++) {
            Coin startCoin = coinService.getCoinPriceOnDate(crypto.coinNames[i], begin, cur);
            Coin endCoin = coinService.getCoinPriceOnDate(crypto.coinNames[i], end, cur);
            double change = (endCoin.currentPrice - startCoin.currentPrice) / startCoin.currentPrice * 100;
            logger.print("%s%n",startCoin.name.toUpperCase());
            logger.print("| %-20s | %12s | %s%10.2f |%n","Start date & Price",begin, cur.getSymbol(), startCoin.currentPrice);
            logger.print("| %-20s | %12s | %s%10.2f |%n","End date & Price",end, cur.getSymbol(), endCoin.currentPrice);
            logger.print("| %-20s | %,26.2f |%n","% Change", change);
            logger.print("%n");
        }
        return 0;
    }
    
}
