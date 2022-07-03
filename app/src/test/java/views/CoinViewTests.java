package views;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;

import org.junit.Test;

public class CoinViewTests {
    
    @Test
    public void datesAreInSameWeek() {
        LocalDate ld1 = LocalDate.of(2022, 7, 3);
        LocalDate ld2 = LocalDate.of(2022, 7, 4);
        assertTrue(null, CoinView.inSameCalendarWeek(ld1, ld2));
    }

    @Test
    public void datesNotInSameWeek() {
        LocalDate ld1 = LocalDate.of(2022, 7, 2);
        LocalDate ld2 = LocalDate.of(2022, 7, 3);
        assertFalse(null, CoinView.inSameCalendarWeek(ld1, ld2));
    }
}
