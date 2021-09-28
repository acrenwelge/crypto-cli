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

    public Integer call() throws IOException, InterruptedException {
        String res = CoinService.getCoinHistory(crypto.coinName, denomination, numDaysHistory);
        System.out.println(res);
        return 0;
    }
    
}
