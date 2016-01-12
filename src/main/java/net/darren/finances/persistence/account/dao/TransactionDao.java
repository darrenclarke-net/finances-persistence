package net.darren.finances.persistence.account.dao;

import com.sleepycat.persist.EntityCursor;
import com.sleepycat.persist.EntityStore;
import com.sleepycat.persist.PrimaryIndex;
import com.sleepycat.persist.SecondaryIndex;
import net.darren.finances.persistence.account.entities.TransactionEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TransactionDao {

    private EntityStore entityStore;

    private final PrimaryIndex<String, TransactionEntity> primaryIndex;
    private final SecondaryIndex<String, String, TransactionEntity> transactionEntityByGroup;

    public TransactionDao(EntityStore entityStore) {
        this.entityStore = entityStore;
        this.primaryIndex = entityStore.getPrimaryIndex(String.class, TransactionEntity.class);
        this.transactionEntityByGroup = entityStore.getSecondaryIndex(primaryIndex, String.class, "groupId");
    }

    public void add(TransactionEntity transactionEntity) {
        primaryIndex.put(transactionEntity);
    }

    public TransactionEntity retrieve(UUID id) {
        return primaryIndex.get(id.toString());
    }

    public List<TransactionEntity> retrieveAll(String groupId) {
        List<TransactionEntity> result = new ArrayList<>();

        EntityCursor<TransactionEntity> transactionsForGroup = null;
        try {
            transactionsForGroup = transactionEntityByGroup.subIndex(groupId).entities();
            for (TransactionEntity transactionEntity : transactionsForGroup) {
                result.add(transactionEntity);
            }
        } finally {
            transactionsForGroup.close();
        }

        return result;
    }

    public void close() {
        entityStore.close();
    }
}
