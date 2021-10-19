package cryptocli;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

import models.Coin;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Model.CommandSpec;
import picocli.CommandLine.Option;
import picocli.CommandLine.ParameterException;
import picocli.CommandLine.ParseResult;
import picocli.CommandLine.ScopeType;
import picocli.CommandLine.Spec;
import util.CoinFileReaderWriter;
import util.CoinService;
import util.CryptoDefaultValueProvider;
import util.CustomLogger;
import views.CoinView;

@Command(name = "crypto", 
    mixinStandardHelpOptions = true, 
    version = "crypto 1.1",
    description = "Find information on any cryptocurrency",
    subcommands = {Config.class, Search.class, Price.class, History.class},
    defaultValueProvider = CryptoDefaultValueProvider.class
    )
public class Crypto implements Callable<Integer> {
    @Spec CommandSpec spec;
    
    @Option(names={"-c","--coin"},
        scope = ScopeType.INHERIT,
        description = "bitcoin, ethereum, usdc, ...",
        paramLabel = "CRYPTOCURRENCY"
    )
    public void setCoinNames(String[] names) throws IOException, InterruptedException {
        List<Coin> coinMasterList;
        try {
            coinMasterList = CoinFileReaderWriter.getCoinListFromFile();
        } catch (FileNotFoundException fnfe) {
            coinMasterList = coinService.getCoinList();
        }
        Set<String> idSet = coinMasterList.stream().map(c -> c.id).collect(Collectors.toSet());
        for (String name : names) {
            if (!idSet.contains(name)) {
                throw new ParameterException(spec.commandLine(),
                String.format("Coin name %s is invalid", name));
            }
        }
        this.coinNames = names;
    }

    @Option(names={"-cur", "--currency"},
        scope = ScopeType.INHERIT,
        description = "USD, EUR, ...",
        paramLabel = "DENOMINATION"
    )
    public void setCurrency(String[] currencies) {
        this.denominations = new ArrayList<>();
        for (String cur : currencies) {
            try {
                Currency c = Currency.getInstance(cur);
                this.denominations.add(c);
            } catch (IllegalArgumentException e) {
                throw new ParameterException(spec.commandLine(),
                String.format("Currency value %s is not a supported ISO 4217 code.",cur));
            }
        }
    }

    String[] coinNames;
    List<Currency> denominations;

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
        // set file location for config properties file
        System.setProperty("picocli.defaults.config.path", System.getenv("HOME").concat("/.crypto/defaults.properties"));
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