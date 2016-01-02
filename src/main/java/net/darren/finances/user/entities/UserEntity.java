package net.darren.finances.user.entities;

import com.sleepycat.persist.model.Entity;
import com.sleepycat.persist.model.PrimaryKey;

import java.util.Set;

@Entity
public class UserEntity {

    @PrimaryKey
    private String id;

    private Set<String> accountList;

    private String userName;
    private String passwordHash;
    private String salt;

    public String getId() {
        return id;
    }

    public Set<String> getAccountList() {
        return accountList;
    }

    public String getUserName() {
        return userName;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public String getSalt() {
        return salt;
    }
}
