package cryptocli;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Currency;
import java.util.Optional;
import java.util.Properties;
import java.util.concurrent.Callable;

import picocli.CommandLine.Command;
import picocli.CommandLine.Model.CommandSpec;
import picocli.CommandLine.Option;
import picocli.CommandLine.ParameterException;
import picocli.CommandLine.ParentCommand;
import picocli.CommandLine.ParseResult;
import picocli.CommandLine.Spec;
import util.CustomLogger;

@Command(name="config", description = "Set default values")
public class Config implements Callable<Integer> {
    private static final CustomLogger logger = CustomLogger.getInstance();
    public static final Path FILEPATH = Paths.get(System.getenv("HOME")).resolve(".crypto/defaults.properties");

    @Spec CommandSpec spec;

    @ParentCommand
    Crypto crypto;

    public static Properties loadPropsFromFile() throws IOException {
        Properties props = new Properties();
        try {
            props.load(Files.newBufferedReader(FILEPATH));
        } catch (NoSuchFileException nsfe) {
            logger.error("Config file does not exist - creating new one...%n");
            Files.createDirectories(FILEPATH.getParent());
            Files.createFile(FILEPATH);
        }
        return props;
    }

    @Override
    public Integer call() throws IOException {
        Properties props = loadPropsFromFile();
        ParseResult pr = spec.commandLine().getParseResult();
        if (pr.hasMatchedOption("-c")) {
            logger.print("Old default coin value %s being updated to %s%n", props.getProperty("coin"), crypto.coinNames[0]);
            props.setProperty("coin", crypto.coinNames[0]);
        }
        if (pr.hasMatchedOption("-cur")) {
            String denom = crypto.denominations.get(0).getCurrencyCode();
            logger.print("Old default currency value %s being updated to %s%n", props.getProperty("currency"), denom);
            props.setProperty("currency", denom);
        }
        if (!pr.hasMatchedOption("-c") && !pr.hasMatchedOption("-cur")) {
            logger.print("No config options set - current defaults are:%n");
            logger.print("- Crypto: %s %n",props.getProperty("coin"));
            logger.print("- Currency: %s %n",props.getProperty("currency"));
        } else {
            props.store(new PrintWriter(FILEPATH.toFile()), null);
        }
        return 0;
    }
}
