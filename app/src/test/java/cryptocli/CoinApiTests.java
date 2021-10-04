package cryptocli;

import java.net.URLEncoder;

import com.google.common.base.Charsets;

import org.junit.Assert;
import org.junit.Test;

import util.CoinService;

public class CoinApiTests {
    @Test
    public void validateURL() {
        String[] coinNamesToTest = {
            "bitcoin",
            "Bankless DAO",
            "s_hit+coin",
            "PieDAO BTC++",
            "token 1.0"
        };
        for (String name : coinNamesToTest) {
            final String URL = CoinService.BASE_URL.concat("/coins/")
                .concat(URLEncoder.encode(name, Charsets.UTF_8));
            Assert.assertEquals(true,URL.contains("/coins"));
        }
    }   
}