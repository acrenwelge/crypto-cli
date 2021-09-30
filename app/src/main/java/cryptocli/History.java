package cryptocli;

import java.io.IOException;
import java.util.concurrent.Callable;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.ParentCommand;
import util.CoinService;

@Command(name="history")
public class History implements Callable<Integer> {

    @Option(names={"-d", "--days"})
    Integer numDaysHistory;

    @Option(names={"-cur", "--currencies"}, defaultValue = "usd")
    String denomination;

    @ParentCommand
    Crypto crypto;

    private CoinService coinService = CoinService.getInstance();

    public Integer call() throws IOException, InterruptedException {
        String res = coinService.getCoinHistory(crypto.coinNames[0], denomination, numDaysHistory);
        System.out.println(res);
        return 0;
    }
    
}
