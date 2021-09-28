package cryptocli;

import java.time.LocalDate;
import java.util.concurrent.Callable;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.ParentCommand;
import util.CoinService;

@Command(name="price", 
    description = "Lookup price data on a cryptocurrency"
    )
public class Price implements Callable<Integer> {
    
    @ParentCommand
    Crypto crypto;

    @Option(names={"-cur","--currency"}, defaultValue = "usd")
    String[] denominations;

    @Option(names="--date")
    LocalDate date;

    @Override
    public Integer call() throws Exception {
        String result = CoinService.getCoinPrices(new String[] {crypto.coinName}, denominations);
        System.out.println(result);
        return 0;
    }
}
