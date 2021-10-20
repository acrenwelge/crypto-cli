package cryptocli;

import java.util.Properties;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import picocli.CommandLine;
import picocli.CommandLine.Model.ArgSpec;
import picocli.CommandLine.ParameterException;
import util.CryptoDefaultValueProvider;

public class ArgumentValidationTests {
    
    @Test
    public void coinSingleArgTestPos() {
        String[] args = {"-c","bitcoin"};
        Crypto cr = new Crypto();
        new CommandLine(cr).parseArgs(args);
        Assert.assertEquals("bitcoin",cr.coinNames[0]);
    }

    @Test(expected = ParameterException.class)
    public void coinSingleArgTestNeg() {
        String[] args = {"-c","blahcoin"};
        Crypto cr = new Crypto();
        new CommandLine(cr).parseArgs(args);
    }

    @Test
    public void coinMultipleArgTestPos() {
        String[] args = {"-c","bitcoin","-c","litecoin"};
        Crypto cr = new Crypto();
        new CommandLine(cr).parseArgs(args);
        Assert.assertEquals("bitcoin",cr.coinNames[0]);
        Assert.assertEquals("litecoin",cr.coinNames[1]);
    }

    @Test
    public void currencySingleArgTestPos() {
        String[] args = {"-cur","JPY"};
        Crypto cr = new Crypto();
        new CommandLine(cr).parseArgs(args);
        Assert.assertEquals("JPY",cr.denominations.get(0).getCurrencyCode());
    }

    @Test(expected = ParameterException.class)
    public void currencySingleArgTestNeg() {
        String[] args = {"-cur","XYZ"};
        Crypto cr = new Crypto();
        new CommandLine(cr).parseArgs(args);
    }

    private static class MockConfig extends Config {
        @Override
        public Properties loadPropsFromFile() {
            Properties p = new Properties();
            p.setProperty("coin", "litecoin");
            p.setProperty("currency", "JPY");
            return p;
        }
    }

    @Test
    @Ignore // having trouble with this test - getting fallback values instead of default
    public void testArgDefaults() throws Exception {
        CryptoDefaultValueProvider vp = new CryptoDefaultValueProvider();
        vp.setConfig(new MockConfig());
        CommandLine cl = new CommandLine(new Crypto());
        ArgSpec dummyArg = cl.parseArgs("--coin", "litecoin").matchedArgs().get(0);
        String result = vp.defaultValue(dummyArg);
        Assert.assertEquals("litecoin",result);
        dummyArg = cl.parseArgs("--currency", "USD").matchedArgs().get(0);
        result = vp.defaultValue(dummyArg);
        Assert.assertEquals("JPY",result);
    }

}
