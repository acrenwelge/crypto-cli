package cryptocli;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import models.Coin;
import util.CoinFileReaderWriter;

public class CoinJsonDeserializeTest {
    @Test
    public void testDeserializeCoin() throws IOException {
        Path p = Paths.get("../samplecoin.json");
        String rawJson = Files.readString(p);
        Coin sample = Coin.fromJsonString(rawJson);
        Assert.assertEquals("bitcoin", sample.id);
    }

    @Test
    public void testDeserializeList() throws IOException {
        List<Coin> coins = CoinFileReaderWriter.getCoinListFromFile();
        Assert.assertEquals(coins.get(0).id, "01coin");
        Assert.assertEquals(coins.get(0).name, "01coin");
        Assert.assertEquals(coins.get(0).symbol, "zoc");
    }
}
