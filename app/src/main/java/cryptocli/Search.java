package cryptocli;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

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
        String rawResponse = coinService.getCoinList();
        List<String> result = rawResponse.lines()
            .filter(s -> s.contains(query))
            .collect(Collectors.toList());
        result.forEach(logger::print);
        return 0;
    }
    
}
