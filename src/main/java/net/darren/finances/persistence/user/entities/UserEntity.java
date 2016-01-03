package net.darren.finances.persistence.user.entities;

import com.sleepycat.persist.model.*;
import net.darren.finances.persistence.account.entities.AccountEntity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
public class UserEntity {

    @PrimaryKey
    private String id;

    @SecondaryKey(relate = Relationship.ONE_TO_MANY)
    Set<String> accounts = new HashSet<>();

    private String username;
    private String passwordHash;
    private String salt;

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public String getSalt() {
        return salt;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public static class Builder {

        private final UserEntity userEntity;

        private Builder(String username, UUID id) {
            userEntity = new UserEntity();
            userEntity.setUsername(username);
            userEntity.setId(id.toString());
        }

        public static UserEntity.Builder newInstance(String username, UUID id) {
            return new Builder(username, id);
        }

        public UserEntity build() {
            return userEntity;
        }

        public Builder withPasswordHash(String password) {
            userEntity.setPasswordHash(password);
            return this;
        }

        public Builder withSalt(String salt) {
            userEntity.setSalt(salt);
            return this;
        }
    }
}
