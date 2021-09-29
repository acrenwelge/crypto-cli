package cryptocli;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Assert;
import org.junit.Test;

import models.Coin;

public class CoinJsonDeserializeTest {
    @Test
    public void testDeserialize() throws IOException {
        Path p = Paths.get("../samplecoin.json");
        String rawJson = Files.readString(p);
        Coin sample = Coin.fromJsonString(rawJson);
        Assert.assertEquals("bitcoin", sample.id);
    }
}
