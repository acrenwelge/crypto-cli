package cryptocli;

import java.io.IOException;
import java.util.Currency;
import java.util.concurrent.Callable;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.ParentCommand;
import util.CoinService;
import views.CoinView;

@Command(name="history",
    description = "Show price history of a cryptocurrency"
)
public class History implements Callable<Integer> {

    @Option(names={"-d", "--days"})
    Integer numDaysHistory;

    @Option(names={"-cur", "--currency"}, defaultValue = "usd")
    String denomination;

    @ParentCommand
    Crypto crypto;

    private CoinService coinService = CoinService.getInstance();

    public Integer call() throws IOException, InterruptedException {
        Currency currency = Currency.getInstance(denomination);
        String res = coinService.getCoinHistory(crypto.coinNames[0], currency, numDaysHistory);
        CoinView.displayPriceHistory(res, currency);
        return 0;
    }
    
}
