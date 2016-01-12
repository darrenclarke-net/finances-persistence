package net.darren.finances.persistence.account.dao;

import com.sleepycat.persist.EntityCursor;
import com.sleepycat.persist.EntityStore;
import com.sleepycat.persist.PrimaryIndex;
import com.sleepycat.persist.SecondaryIndex;
import net.darren.finances.persistence.account.entities.MonthTransactionGroupEntity;
import net.darren.finances.persistence.account.entities.keys.MonthTransactionGroupCompositeKey;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MonthTransactionGroupDao {

    private final SecondaryIndex<String, MonthTransactionGroupCompositeKey, MonthTransactionGroupEntity> groupEntityByAccount;
    private EntityStore entityStore;

    private PrimaryIndex<MonthTransactionGroupCompositeKey, MonthTransactionGroupEntity> primaryIndex;

    public MonthTransactionGroupDao(EntityStore entityStore) {
        this.entityStore = entityStore;
        this.primaryIndex = entityStore.getPrimaryIndex(MonthTransactionGroupCompositeKey.class, MonthTransactionGroupEntity.class);
        this.groupEntityByAccount = entityStore.getSecondaryIndex(primaryIndex, String.class, "accountId");
    }

    public void addTransactionGroup(MonthTransactionGroupEntity monthTransactionGroupEntity) {
        primaryIndex.put(monthTransactionGroupEntity);
    }

    public List<MonthTransactionGroupEntity> getAllFor(UUID accountId) {
        List<MonthTransactionGroupEntity> result = new ArrayList<>();

        EntityCursor<MonthTransactionGroupEntity> groupsForAccount = null;
        try {
            groupsForAccount = groupEntityByAccount.subIndex(accountId.toString()).entities();
            for (MonthTransactionGroupEntity transactionGroupEntity : groupsForAccount) {
                result.add(transactionGroupEntity);
            }
        } finally {
            groupsForAccount.close();
        }
        return result;
    }

    public void close() {
        entityStore.close();
    }
}
