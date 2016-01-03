package net.darren.finances.persistence.user.dao;

import com.sleepycat.persist.EntityCursor;
import com.sleepycat.persist.EntityStore;
import com.sleepycat.persist.PrimaryIndex;
import com.sleepycat.persist.SecondaryIndex;
import net.darren.finances.persistence.account.entities.AccountEntity;
import net.darren.finances.persistence.exception.PersistenceException;
import net.darren.finances.persistence.user.entities.UserEntity;

import java.util.ArrayList;
import java.util.List;

public class UserDao {

    private EntityStore entityStore;

    private PrimaryIndex<String, UserEntity> primaryIndex;

    public UserDao(EntityStore entityStore) {
        this.entityStore = entityStore;
        this.primaryIndex = entityStore.getPrimaryIndex(String.class, UserEntity.class);
    }

    public List<UserEntity> userList() {
        List<UserEntity> result = new ArrayList<>();

        EntityCursor<UserEntity> entities = primaryIndex.entities();
        try {
            for (UserEntity user : entities) {
                result.add(user);
            }
        } finally {
            entities.close();
        }
        return result;
    }

    public UserEntity findUser(String username) throws PersistenceException {
        if (username == null || username.isEmpty()) {
            throw new PersistenceException("Username cannot be null for find operation");
        }

        UserEntity result = null;

        List<UserEntity> userList = userList();
        for (UserEntity user : userList) {
            if (user.getUsername().equals(username)) {
                result = user;
            }
        }
        return result;
    }

    public void addUser(UserEntity userEntity) throws PersistenceException {
        UserEntity existingUser = findUser(userEntity.getUsername());
        if (existingUser != null) {
            throw new PersistenceException("Username already found in DB");
        }

        primaryIndex.put(userEntity);
    }

    public void close() {
        entityStore.close();
    }
}
