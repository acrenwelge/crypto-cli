package cryptocli;

import java.io.IOException;
import java.util.concurrent.Callable;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.ScopeType;
import util.CoinService;

@Command(name = "crypto", 
    mixinStandardHelpOptions = true, 
    version = "crypto 1.0",
    description = "Find information on any cryptocurrency",
    subcommands = {Search.class, Price.class, History.class}
    )
public class Crypto implements Callable<Integer> {

    @Option(names={"-c","--coin"},
        scope = ScopeType.INHERIT,
        description="bitcoin, ethereum, usdc, ...")
    String coinName;

    public static void main(String[] args) throws IOException, InterruptedException {
        int exitCode = new CommandLine(new Crypto()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public Integer call() throws Exception {
        String result = CoinService.getCoin(coinName);
        System.out.println(result);
        return 0;
    }
}