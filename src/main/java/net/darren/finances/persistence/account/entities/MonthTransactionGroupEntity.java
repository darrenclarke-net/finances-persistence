package net.darren.finances.persistence.account.entities;

import com.sleepycat.persist.model.Entity;
import com.sleepycat.persist.model.PrimaryKey;
import com.sleepycat.persist.model.Relationship;
import com.sleepycat.persist.model.SecondaryKey;
import net.darren.finances.persistence.account.entities.keys.MonthTransactionGroupCompositeKey;
import org.joda.time.LocalDate;

import java.util.Set;
import java.util.UUID;

@Entity
public class MonthTransactionGroupEntity implements TransactionGroupEntity {

    @PrimaryKey
    private MonthTransactionGroupCompositeKey monthId;

    @SecondaryKey(relate = Relationship.ONE_TO_MANY)
    private Set<String> transactionEntities;

    @SecondaryKey(relate = Relationship.MANY_TO_ONE)
    private String accountId;

    private String name;
    private String startDate;
    private String endDate;

    public void setMonthId(MonthTransactionGroupCompositeKey monthId) {
        this.monthId = monthId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public static class Builder {

        private final MonthTransactionGroupEntity monthTransactionGroupEntity;

        public Builder(int month, int year, String accountId) {
            monthTransactionGroupEntity = new MonthTransactionGroupEntity();
            monthTransactionGroupEntity.setMonthId(MonthTransactionGroupCompositeKey.Builder.newInstance(month, year).build());
            monthTransactionGroupEntity.setAccountId(accountId);
        }

        public MonthTransactionGroupEntity build() {
            return monthTransactionGroupEntity;
        }

        public static Builder newInstance(int month, int year, UUID accountId) {
            return new Builder(month, year, accountId.toString());
        }
    }

}
