package cryptocli;

import java.time.LocalDate;
import java.util.Currency;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

import models.Coin;
import models.SimpleCoin;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.ParentCommand;
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

    /* Option for finding price on specific date*/
    @Option(names="--date")
    Optional<LocalDate> date;

    /* Options for watching the price */
    @Option(names={"-w","--watch"})
    boolean watch;

    @Option(names={"-i","--interval"}, defaultValue = "15")
    int timeIntervalInSeconds;

    @Option(names={"-s","--stop"})
    Optional<Integer> stopAfterTimeInMinutes;

    private CoinService coinService = CoinService.getInstance();

    @Override
    public Integer call() throws Exception {
        if (date.isPresent()) {
            Coin c = coinService.getCoinPriceOnDate(crypto.coinNames[0], date.get(), crypto.denominations.get(0));
            CoinView.displayCoinOnDate(c, date.get());
        } else if (watch) {
            long start = System.currentTimeMillis();
            CoinView.displayPriceWatchHeader(crypto.coinNames[0]);
            while(true) {
                List<SimpleCoin> coins = coinService.getCoinPrices(new String[]{crypto.coinNames[0]},crypto.denominations);
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
            List<SimpleCoin> coins = coinService.getCoinPrices(crypto.coinNames, crypto.denominations);
            Set<Currency> currencySet = crypto.denominations.stream().collect(Collectors.toSet());
            CoinView.displayPriceTable(coins, currencySet);
        }
        return 0;
    }
}
