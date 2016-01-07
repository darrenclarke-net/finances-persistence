package net.darren.finances.persistence.account.entities.keys;

import com.sleepycat.persist.model.KeyField;
import com.sleepycat.persist.model.Persistent;

@Persistent
public class MonthTransactionGroupCompositeKey {

    @KeyField(1)
    private int month;

    @KeyField(2)
    private int year;

    public void setMonth(int month) {
        this.month = month;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public static class Builder {

        private final MonthTransactionGroupCompositeKey monthTransactionGroupCompositeKey;

        private Builder(int month, int year) {
            monthTransactionGroupCompositeKey = new MonthTransactionGroupCompositeKey();
            monthTransactionGroupCompositeKey.setMonth(month);
            monthTransactionGroupCompositeKey.setYear(year);
        }

        public MonthTransactionGroupCompositeKey build() {
            return monthTransactionGroupCompositeKey;
        }

        public static Builder newInstance(int month, int year) {
            return new Builder(month, year);
        }
    }
}
