package cryptocli;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

import models.Coin;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import util.CoinService;
import util.CustomLogger;

@Command(name="search",
    description = "Look up a cryptocurrency"
)
public class Search implements Callable<Integer> {

    @Parameters
    public String query;

    private CustomLogger logger = CustomLogger.getInstance();
    private CoinService coinService = CoinService.getInstance();

    @Override
    public Integer call() throws Exception {
        List<Coin> listOfCoins = coinService.getCoinList();
        List<Coin> filteredCoins = listOfCoins.stream()
            .filter(coin -> coin.id.contains(query) || coin.name.contains(query) || coin.symbol.contains(query))
            .collect(Collectors.toList());
        logger.print("=".repeat(80).concat("%n"));
        logger.print("%80s%n",String.format("SEARCH RESULTS FOR \"%s\"", query));
        logger.print("=".repeat(80).concat("%n"));
        logger.print("| %30s | %30s | %10s | %n", "ID","Name","Symbol");
        for (Coin coin : filteredCoins) {
            logger.print("| %30s | %30s | %10s | %n", coin.id, coin.name, coin.symbol);
        }
        return 0;
    }
    
}
