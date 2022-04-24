package util;

import java.io.IOException;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import models.Coin;

public class CoinJsonDeserializeTest {

    @Test
    public void testDeserializeCoin() throws IOException {
        String coinJson = new String(CoinJsonDeserializeTest.class.getClassLoader()
            .getResourceAsStream("samplecoin.json").readAllBytes());
        Coin sample = CoinJsonParser.fromJsonString(coinJson);
        Assert.assertEquals("bitcoin", sample.id);
        Assert.assertEquals("Bitcoin", sample.name);
        Assert.assertEquals("btc", sample.symbol);
    }

    @Test
    public void testDeserializeList() throws IOException {
        List<Coin> coins = CoinFileReaderWriter.getCoinListFromFile("src/test/resources/coins.json");
        Assert.assertEquals("01coin",coins.get(0).id);
        Assert.assertEquals("01coin",coins.get(0).name);
        Assert.assertEquals("zoc",coins.get(0).symbol);
    }
}
