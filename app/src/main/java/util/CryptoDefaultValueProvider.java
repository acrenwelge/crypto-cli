package util;

import java.util.Properties;

import cryptocli.Config;
import picocli.CommandLine.IDefaultValueProvider;
import picocli.CommandLine.Model.ArgSpec;
import picocli.CommandLine.Model.OptionSpec;

public class CryptoDefaultValueProvider implements IDefaultValueProvider {
    private CustomLogger logger = CustomLogger.getInstance();

    private static String lookupFallbacks(String optName) {
        if (optName.equals("--coin")) {
            return "bitcoin";
        } else if (optName.equals("--currency")) {
            return "USD";
        } else {
            return null;
        }
    }

    @Override
    public String defaultValue(ArgSpec argSpec) throws Exception {
        OptionSpec optSpec = (OptionSpec) argSpec;
        Properties props = Config.loadPropsFromFile();
        final String optionName = optSpec.longestName();
        String value = props.getProperty(optionName);
        if (value == null) {
            value = lookupFallbacks(optionName);
            logger.debug("No %s specified - defaulting to %s", optionName, value);
        }
        return value;
    }
    
}
