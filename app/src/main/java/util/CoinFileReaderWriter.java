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
    private static final String CACHED_COIN_LIST_FILE = "../coins.json";

    private static final CustomLogger logger = CustomLogger.getInstance();

    public static List<Coin> getCoinListFromFile() throws FileNotFoundException {
        List<Coin> list = new ArrayList<>();
        String raw;
        try {
            Path filepath = Paths.get(CACHED_COIN_LIST_FILE);
            if (Files.exists(filepath)) {
                raw = Files.readString(filepath);
                list = getCoinListFromJson(raw);
            } else {
                logger.error("File Not Found: %s", CACHED_COIN_LIST_FILE);
                throw new FileNotFoundException();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JsonParseException jpe) {
            logger.error("There was a problem parsing the json string");
        }
        return list;
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

    public static void writeCoinListToFile(String coinsJson) {
        try {
            Files.writeString(Paths.get(CACHED_COIN_LIST_FILE), coinsJson, StandardOpenOption.WRITE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}