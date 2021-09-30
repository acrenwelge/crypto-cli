package cryptocli;

import java.util.concurrent.Callable;

import models.Coin;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.ParseResult;
import picocli.CommandLine.ScopeType;
import util.CoinService;
import util.CustomLogger;
import views.CoinView;

@Command(name = "crypto", 
    mixinStandardHelpOptions = true, 
    version = "crypto 0.1",
    description = "Find information on any cryptocurrency",
    subcommands = {Search.class, Price.class, History.class}
    )
public class Crypto implements Callable<Integer> {
    @Option(names={"-c","--coin"},
        scope = ScopeType.INHERIT,
        description="bitcoin, ethereum, usdc, ...")
    String[] coinNames;

    @Option(names = {"-v", "--verbose"},
        description = "Set verbose mode")
    boolean verbose;

    private CustomLogger logger = CustomLogger.getInstance();
    private CoinService coinService = CoinService.getInstance();

    public static void main(String[] args) {
        Crypto crypto = new Crypto();
        int exitCode = new CommandLine(new Crypto())
            .setExecutionStrategy(crypto::executionStrategy)
            .execute(args);
        System.exit(exitCode);
    }

    private int executionStrategy(ParseResult parseResult) {
        if (parseResult.hasMatchedOption("-v")) {
            CustomLogger.LOGGING_LEVEL = true;
        }
        logger.debug("verbosity enabled");
        return new CommandLine.RunLast().execute(parseResult); // default execution strategy
    }

    @Override
    public Integer call() throws Exception {
        for (String name : coinNames) {
            Coin coin = coinService.getCoin(name);
            CoinView.displayCoinInfo(coin);
        }
        return 0;
    }
}