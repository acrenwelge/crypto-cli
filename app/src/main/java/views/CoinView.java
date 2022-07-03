package views;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.Currency;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import models.Coin;
import models.PriceSnapshot;
import models.SimpleCoin;
import util.CustomLogger;

public class CoinView {
    private CoinView() {}
    static CustomLogger logger = CustomLogger.getInstance();

    public static void displayCoinInfo(Coin c) {
        logger.print(
        "| %10s | %10s | %10s | %15s | %n"
    +   "| %10s | %10s | %10s | %15s | %n"
        , "ID","Name","Symbol", "Hash Algorithm"
        , c.id, c.name, c.symbol, c.hashAlgorithm);
        logger.print();
        logger.print(
        "STATISTICS: %n"
    +   "- Max Supply: %,.0f%n"
    +   "- Circulating Supply: %,.0f%n"
    +   "- Market Cap: $%,.0f%n"
    +   "- Market Cap Rank: %d%n"
        ,c.maxSupply,c.circulatingSupply,c.marketCap,c.marketCapRank);
        logger.print();
        logger.print("DESCRIPTION: " + c.description);
        logger.print();
    }

    public static void displayPriceTable(List<SimpleCoin> coins, Set<Currency> currencies) {
        logger.print("=".repeat(80).concat("%n"));
        logger.print("%80s%n","Coin Prices");
        logger.print("=".repeat(80).concat("%n"));
        logger.print("| %30s |","Coin");
        for (Currency currency : currencies) {
            logger.print("%13s |",currency.getCurrencyCode());
        }
        logger.print("%n");
        for (SimpleCoin coin : coins) {
            logger.print("| %30s |",coin.id);
            for (Currency c : currencies) {
                if (coin.getPrices().containsKey(c)) {
                    logger.print("%2s %,10.0f |",c.getSymbol(),coin.getPrices().get(c).doubleValue());
                } else {
                    logger.print("N/A%n");
                }
            }
            logger.print("%n");
        }
        logger.print("-".repeat(80).concat("%n"));
    }

    public static void displayPriceWatchHeader(String coinName) {
        logger.print("=".repeat(33).concat("%n"));
        logger.print("Price of %s%n",coinName);
        logger.print("=".repeat(33).concat("%n"));
        logger.print("%20s | %10s %n","Time","Price");
        logger.print("-".repeat(33).concat("%n"));
    }

    public static void displayPriceWatchRow(SimpleCoin coin) {
        coin.getPrices().forEach((cur, price) -> 
            logger.print("%20s | %s%,9.0f %n",
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")),
                cur.getSymbol(),
                price));
    }

    public static void displayPriceHistory(List<PriceSnapshot> prices, Currency currency, char timeUnit) {
        Set<LocalDate> days = new HashSet<>();
        Set<YearMonth> months = new HashSet<>();
        logger.print("%20s | %10s %n","Date & Time","Price");
        logger.print("-".repeat(33).concat("%n"));
        for (PriceSnapshot ps : prices) {
            if (timeUnit == 'd') { // only record daily prices
                if (!days.contains(ps.preciseTime.toLocalDate())) {
                    printPriceAndTimeWithPattern(ps, currency, "yyyy-MM-dd");
                    days.add(ps.preciseTime.toLocalDate());
                }
            } else if (timeUnit == 'w') { // only record weekly prices
                // determine if days are in the same week
                boolean anyAreInSameWeek = false;
                for (LocalDate ld : days) {
                    if (inSameCalendarWeek(ld,ps.preciseTime.toLocalDate())) {
                        anyAreInSameWeek = true;
                    }
                }
                if (!anyAreInSameWeek) {
                    printPriceAndTimeWithPattern(ps, currency, "yyyy-MM-dd");
                    days.add(ps.preciseTime.toLocalDate());
                }
            } else if (timeUnit == 'm') { // only record monthly prices
                YearMonth ym = YearMonth.of(ps.preciseTime.getYear(), ps.preciseTime.getMonth());
                if (!months.contains(ym)) {
                    printPriceAndTimeWithPattern(ps, currency, "yyyy-MM");
                    months.add(ym);
                }
            } else if (timeUnit == 'a') { // print all prices
                printPriceAndTimeWithPattern(ps,currency,"yyyy-MM-dd HH:mm:ss");
            }
        }
    }

    static boolean inSameCalendarWeek(LocalDate firstDate, LocalDate secondDate) {
        // get a reference to the system of calendar weeks in your defaul locale
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        // find out the calendar week for each of the dates
        int firstDatesCalendarWeek = firstDate.get(weekFields.weekOfWeekBasedYear());
        int secondDatesCalendarWeek = secondDate.get(weekFields.weekOfWeekBasedYear());
        /*
         * find out the week based year, too,
         * two dates might be both in a calendar week number 1 for example,
         * but in different years
         */
        int firstWeekBasedYear = firstDate.get(weekFields.weekBasedYear());
        int secondWeekBasedYear = secondDate.get(weekFields.weekBasedYear());
        // return if they are equal or not
        return firstDatesCalendarWeek == secondDatesCalendarWeek
                && firstWeekBasedYear == secondWeekBasedYear;
    }

    static void printPriceAndTimeWithPattern(PriceSnapshot ps, Currency currency, String pattern) {
        logger.print("%20s | %s%,9.0f %n",
            ps.preciseTime.format(DateTimeFormatter.ofPattern(pattern)),
            currency.getSymbol(),
            ps.price);
    }

    public static void displayCoinOnDate(Coin c, LocalDate date) {
        logger.print("PRICE OF %s ON %s%n",c.id.toUpperCase(),date.format(DateTimeFormatter.ISO_DATE));
        logger.print("=".repeat(30).concat("%n"));
        logger.print("PRICE: $%,.0f%nMARKET CAP: $%,.0f%n",c.currentPrice, c.marketCap);
    }
}