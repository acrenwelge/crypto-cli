package models;

import java.util.Currency;
import java.util.HashMap;
import java.util.Map;

public class SimpleCoin {
    public String id;
    private Map<Currency,Double> pricesInCurrencies = new HashMap<>();

    public Map<Currency,Double> getPrices() {
        return this.pricesInCurrencies;
    }

    public void addPrice(String currency, double amount) {
        Currency c = Currency.getInstance(currency.toUpperCase());
        pricesInCurrencies.put(c, amount);
    }
}
