package cryptocli;

import java.io.IOException;
import java.nio.file.FileSystemException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import util.CoinFileReaderWriter;

public class CacheFileTests {
    public static final Path testFile = Paths.get(System.getenv("HOME"), "/.crypto/coins-test.json");

    // @Before
    // public void createDirectory() throws IOException {
    //     Files.createDirectory(Paths.get(System.getenv("HOME"),"/.crypto"));
    // }

    @Test
    public void persistToCacheFile() throws IOException {
        String jsonCoinList = "[{\"id\": \"bitcoin\",\"name\": \"Bitcoin\", \"symbol\": \"btc\"},{\"id\": \"ethereum\",\"name\": \"Ethereum\", \"symbol\": \"eth\"}]";
        if (!Files.exists(testFile)) {
            Files.createFile(testFile);
        }
        Files.writeString(testFile, jsonCoinList, StandardOpenOption.WRITE);
        System.out.println("DONE!!!");
    }

}
