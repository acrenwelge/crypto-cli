package util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

import models.Coin;

public class CoinFileReaderWriter {
    private CoinFileReaderWriter() {}
    public static final String CACHED_COIN_LIST_FILE = System.getenv("HOME").concat("/.crypto/coins.json");

    private static final CustomLogger logger = CustomLogger.getInstance();

    public static List<Coin> getCoinListFromFile() throws IOException, JsonParseException {
        String raw;
        Path filepath = Paths.get(CACHED_COIN_LIST_FILE);
        if (Files.exists(filepath)) {
            raw = Files.readString(filepath);
            return getCoinListFromJson(raw);
        } else {
            logger.error("File Not Found: %s%n", CACHED_COIN_LIST_FILE);
            throw new FileNotFoundException();
        }
    }

    public static List<Coin> getCoinListFromJson(String jsonCoinList) {
        List<Coin> list = new ArrayList<>();
        JsonParser.parseString(jsonCoinList).getAsJsonArray().forEach(jsa -> {
            JsonObject obj = jsa.getAsJsonObject();
            Coin coin = new Coin.CoinBuilder()
                .addId(obj.get("id").getAsString())
                .addSymbol(obj.get("symbol").getAsString())
                .addName(obj.get("name").getAsString())
                .build();
            list.add(coin);
        });
        return list;
    }

    public static void writeCoinListToFile(String coinsJson) throws IOException {
        Path p = Paths.get(CACHED_COIN_LIST_FILE);
        if (!Files.exists(p)) {
            Files.createDirectories(p.getParent());
            Files.createFile(p);
        }
        try {
            Files.writeString(Paths.get(CACHED_COIN_LIST_FILE), coinsJson, StandardOpenOption.WRITE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}