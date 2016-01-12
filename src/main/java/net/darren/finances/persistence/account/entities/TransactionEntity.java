package net.darren.finances.persistence.account.entities;

import com.sleepycat.persist.model.Entity;
import com.sleepycat.persist.model.PrimaryKey;
import com.sleepycat.persist.model.Relationship;
import com.sleepycat.persist.model.SecondaryKey;
import org.joda.time.LocalDate;

import java.util.UUID;

@Entity
public class TransactionEntity {

    @PrimaryKey
    private String id;

    @SecondaryKey(relate = Relationship.MANY_TO_ONE)
    private String groupId;

    private String date;
    private String name;
    private String description;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private String type;
    private int amount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static class Builder {

        private final TransactionEntity transactionEntity;

        private Builder(String transactionId) {
            transactionEntity = new TransactionEntity();
            transactionEntity.setId(transactionId);
        }
        public TransactionEntity build() {
            return transactionEntity;
        }

        public static Builder newInstance(UUID transactionId) {
            return new Builder(transactionId.toString());
        }

        public Builder withGroupId(String groupId) {
            transactionEntity.setGroupId(groupId);
            return this;
        }

        public Builder withDate(LocalDate transactionDate) {
            transactionEntity.setDate(transactionDate.toString());
            return this;
        }

        public Builder withName(String name) {
            transactionEntity.setName(name);
            return this;
        }

        public Builder withDescription(String description) {
            transactionEntity.setDescription(description);
            return this;
        }

        public Builder withAmount(int amount) {
            transactionEntity.setAmount(amount);
            return this;
        }

        public Builder withType(String type) {
            transactionEntity.setType(type);
            return this;
        }
    }
}
