package cryptocli;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import util.CoinService;

@Command(name="search",
    description = "Look up a cryptocurrency"
)
public class Search implements Callable<Integer> {

    @Parameters
    public String query;

    @Override
    public Integer call() throws Exception {
        String rawResponse = CoinService.getCoinList();
        List<String> result = rawResponse.lines()
            .filter(s -> s.contains(query))
            .collect(Collectors.toList());
        result.forEach(System.out::println);
        return 0;
    }
    
}
