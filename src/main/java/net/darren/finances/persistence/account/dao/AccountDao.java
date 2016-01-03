package net.darren.finances.persistence.account.dao;

import com.sleepycat.persist.EntityCursor;
import com.sleepycat.persist.EntityStore;
import com.sleepycat.persist.PrimaryIndex;
import com.sleepycat.persist.SecondaryIndex;
import net.darren.finances.persistence.account.entities.AccountEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AccountDao {

    private final EntityStore entityStore;

    private final PrimaryIndex<String, AccountEntity> primaryIndex;

    public AccountDao(EntityStore entityStore) {
        this.entityStore = entityStore;
        this.primaryIndex = entityStore.getPrimaryIndex(String.class, AccountEntity.class);
    }

    public void close() {
        entityStore.close();
    }

    public void addAccount(AccountEntity accountEntity) {
        primaryIndex.put(accountEntity);
    }

    public AccountEntity getAccount(UUID id) {
        return primaryIndex.get(id.toString());
    }

    public List<AccountEntity> accountsFor(UUID userId) {
        List<AccountEntity> result = new ArrayList<>();

        SecondaryIndex<String, String, AccountEntity> accountsByUserId = entityStore.getSecondaryIndex(primaryIndex, String.class, "userId");

        EntityCursor<AccountEntity> accountEntityCursor = null;
        try {
            accountEntityCursor = accountsByUserId.subIndex(userId.toString()).entities();
            for (AccountEntity accountEntity : accountEntityCursor) {
                result.add(accountEntity);
            }
        } finally {
            accountEntityCursor.close();
        }

        return result;
    }
}
