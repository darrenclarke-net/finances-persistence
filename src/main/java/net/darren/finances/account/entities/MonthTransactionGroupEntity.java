package net.darren.finances.account.entities;

import com.sleepycat.persist.model.Entity;
import com.sleepycat.persist.model.PrimaryKey;
import com.sleepycat.persist.model.Relationship;
import com.sleepycat.persist.model.SecondaryKey;
import org.joda.time.LocalDate;

import java.util.Set;

@Entity
public class MonthTransactionGroupEntity implements TransactionGroupEntity {

    @PrimaryKey
    private String monthId;

    @SecondaryKey(relate = Relationship.ONE_TO_MANY)
    private Set<TransactionEntity> transactionEntities;

    private String name;
    private LocalDate startDate;
    private LocalDate endDate;

    @Override
    public String getId() {
        return monthId;
    }
}
