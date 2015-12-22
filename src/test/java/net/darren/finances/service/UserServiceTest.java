package net.darren.finances.service;

import net.darren.finances.entities.user.User;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class UserServiceTest {

    private String testDir;
    private String buildDir;

    private UserService test;

    @Before
    public void setUp() {
        buildDir = System.getProperty("buildDir");
        testDir = System.getProperty("testDir");

        test = new UserService();
    }

    @Test
    public void testAddOneUserAndRetrieveOneUser() {
        User testUser = new User("userName");
        test.addUser(testUser);

        List<User> users = test.getUsers();

        assertEquals(1, users.size());
    }
}
