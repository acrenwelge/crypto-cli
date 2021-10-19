package cryptocli;

import org.junit.Assert;
import org.junit.Test;

import picocli.CommandLine;
import picocli.CommandLine.ParameterException;

public class ArgumentValidationTests {
    
    @Test
    public void coinSingleArgTestPos() {
        String[] args = {"-c","bitcoin"};
        Crypto cr = new Crypto();
        new CommandLine(cr).parseArgs(args);
        Assert.assertEquals(cr.coinNames[0], "bitcoin");
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
        Assert.assertEquals(cr.coinNames[0], "bitcoin");
        Assert.assertEquals(cr.coinNames[1], "litecoin");
    }

    @Test
    public void currencySingleArgTestPos() {
        String[] args = {"-cur","JPY"};
        Crypto cr = new Crypto();
        new CommandLine(cr).parseArgs(args);
        Assert.assertEquals(cr.denominations.get(0).getCurrencyCode(), "JPY");
    }

    @Test(expected = ParameterException.class)
    public void currencySingleArgTestNeg() {
        String[] args = {"-cur","XYZ"};
        Crypto cr = new Crypto();
        new CommandLine(cr).parseArgs(args);
    }
}
