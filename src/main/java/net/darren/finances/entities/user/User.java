package net.darren.finances.entities.user;

import com.sleepycat.persist.model.Entity;
import com.sleepycat.persist.model.PrimaryKey;

import java.util.Set;

@Entity
public class User {

    @PrimaryKey
    private String id;

    private Set<String> accountList;
    private String userName;

    public User(String userName) {
        this.userName = userName;
    }
}
