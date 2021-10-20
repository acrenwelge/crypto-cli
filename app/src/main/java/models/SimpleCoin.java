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

    public void addPrice(Currency currency, double amount) {
        pricesInCurrencies.put(currency, amount);
    }
}
