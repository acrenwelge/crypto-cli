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

    @Option(names={"-d", "--days"}, required = true)
    Integer numDaysHistory;

    @ParentCommand
    Crypto crypto;

    private CoinService coinService = CoinService.getInstance();

    public Integer call() throws IOException, InterruptedException {
        Currency cur = crypto.denominations.get(0); // just use first denomination
        for (String coin : crypto.coinNames) {
            String res = coinService.getCoinHistory(coin, cur, numDaysHistory);
            CoinView.displayPriceHistory(res, cur);
        }
        return 0;
    }
    
}
