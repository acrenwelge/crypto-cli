package cryptocli;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;

import models.Coin;
import models.SimpleCoin;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.ParentCommand;
import util.CoinJsonParser;
import util.CoinService;
import util.CustomLogger;
import views.CoinView;

@Command(name="price", 
    description = "Lookup price data on a cryptocurrency"
    )
public class Price implements Callable<Integer> {

    private CustomLogger logger = CustomLogger.getInstance();
    
    @ParentCommand
    Crypto crypto;

    @Option(names={"-cur","--currency"}, defaultValue = "usd")
    String[] denominations;

    /* Option for finding price on specific date*/
    @Option(names="--date")
    Optional<LocalDate> date;

    /* Options for watching the price */
    @Option(names={"-w","--watch"})
    boolean watch;

    @Option(names={"-i","--interval"}, defaultValue = "15")
    int timeIntervalInSeconds;

    @Option(names={"-s","--stop"}, defaultValue = "null")
    Optional<Integer> stopAfterTimeInMinutes;

    private CoinService coinService = CoinService.getInstance();

    @Override
    public Integer call() throws Exception {
        if (date.isPresent()) {
            Coin c = coinService.getCoinPriceOnDate(crypto.coinNames[0], date.get(), denominations[0]);
            CoinView.displayCoinOnDate(c, date.get());
        } else if (watch) {
            long start = System.currentTimeMillis();
            CoinView.displayPriceWatchHeader(crypto.coinNames[0]);
            while(true) {
                String jsonResult = coinService.getCoinPrices(
                    new String[]{crypto.coinNames[0]},denominations);
                List<SimpleCoin> coins = CoinJsonParser.fromSimplePriceJson(jsonResult, crypto.coinNames, denominations);
                for (SimpleCoin coin : coins) {
                    CoinView.displayPriceWatchRow(coin);
                }
                if (stopAfterTimeInMinutes.isPresent()) {
                    long end = System.currentTimeMillis();
                    float minutesElapsed = (end - start) / (float) (1000 * 60);
                    logger.trace("Minutes elapsed: %f", minutesElapsed);
                    if (minutesElapsed > stopAfterTimeInMinutes.get()) break;
                }
                Thread.sleep((long) timeIntervalInSeconds * 1000);
            }
        } else {
            String jsonResult = coinService.getCoinPrices(crypto.coinNames, denominations);
            CoinView.displayPriceTable(jsonResult, crypto.coinNames, denominations);
        }
        return 0;
    }
}
