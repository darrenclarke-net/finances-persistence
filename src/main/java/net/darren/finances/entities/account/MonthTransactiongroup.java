package net.darren.finances.entities.account;

import com.sleepycat.persist.model.Entity;
import com.sleepycat.persist.model.PrimaryKey;
import com.sleepycat.persist.model.Relationship;
import com.sleepycat.persist.model.SecondaryKey;
import org.joda.time.LocalDate;

import java.util.Set;

@Entity
public class MonthTransactiongroup implements TransactionGroup {

    @PrimaryKey
    private String monthId;

    @SecondaryKey(relate = Relationship.ONE_TO_MANY)
    private Set<Transaction> transactions;

    private String name;
    private LocalDate startDate;
    private LocalDate endDate;

    @Override
    public String getId() {
        return monthId;
    }
}
