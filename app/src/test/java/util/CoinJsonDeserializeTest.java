package util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import models.Coin;
import util.CoinFileReaderWriter;
import util.CoinJsonParser;

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
        Assert.assertEquals(coins.get(0).id, "01coin");
        Assert.assertEquals(coins.get(0).name, "01coin");
        Assert.assertEquals(coins.get(0).symbol, "zoc");
    }
}
