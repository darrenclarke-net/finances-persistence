package net.darren.finances.persistence.account.entities;

import com.sleepycat.persist.model.Entity;
import com.sleepycat.persist.model.PrimaryKey;
import com.sleepycat.persist.model.Relationship;
import com.sleepycat.persist.model.SecondaryKey;
import net.darren.finances.persistence.user.entities.UserEntity;
import org.joda.time.LocalDate;

import java.util.UUID;

@Entity
public class AccountEntity {

    @PrimaryKey
    private String id;

    @SecondaryKey(relate = Relationship.MANY_TO_ONE, relatedEntity = UserEntity.class)
    public String userId;

    private String name;
    private String description;

    private int startBalance;
    private String startDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStartBalance() {
        return startBalance;
    }

    public void setStartBalance(int startBalance) {
        this.startBalance = startBalance;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public static class Builder {

        private final AccountEntity accountEntity;

        private Builder(UUID id, UUID userId) {
            accountEntity = new AccountEntity();
            accountEntity.setUserId(userId.toString());
            accountEntity.setId(id.toString());
        }

        public AccountEntity build() {
            return accountEntity;
        }

        public static Builder newInstance(UUID id, UUID userId) {
            return new Builder(id, userId);
        }

        public Builder withName(String accountName) {
            accountEntity.setName(accountName);
            return this;
        }

        public Builder withDescription(String description) {
            accountEntity.setDescription(description);
            return this;
        }

        public Builder withStartBalance(int balance) {
            accountEntity.setStartBalance(balance);
            return this;
        }

        public Builder withStartDate(String date) {
            accountEntity.setStartDate(date);
            return this;
        }
    }
}
