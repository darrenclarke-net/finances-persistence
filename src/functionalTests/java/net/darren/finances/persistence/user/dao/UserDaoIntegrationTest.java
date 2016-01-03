package net.darren.finances.persistence.user.dao;

import com.sleepycat.persist.EntityStore;
import net.darren.finances.persistence.DatabaseEnvironment;
import net.darren.finances.persistence.exception.PersistenceException;
import net.darren.finances.persistence.user.entities.UserEntity;
import net.darren.persistence.util.TestFileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;

public class UserDaoIntegrationTest {

    private static final UUID ID = UUID.randomUUID();

    private EntityStore entityStore;
    private File testFileDir;
    private DatabaseEnvironment environment;

    private UserDao test;

    @Before
    public void setUp() {
        String database_dir = System.getProperties().getProperty("testDir");
        testFileDir = new File(database_dir);
        testFileDir.mkdirs();

        environment = new DatabaseEnvironment(database_dir, false);
        entityStore = environment.entityStore("testDB");

        test = new UserDao(entityStore);
    }

    @After
    public void tearDown() {
        test.close();
        environment.close();
        TestFileUtils.delete(testFileDir);
        assertFalse(testFileDir.exists());
    }

    @Test
    public void testUserListReturnsEmptyListWhenNoUsers() throws Exception {
        List<UserEntity> userEntityList = test.userList();

        assertTrue(userEntityList.isEmpty());
    }

    @Test
    public void testUserListReturnsListWithOneItemWhenSingleUser() throws Exception {
        test.addUser(UserEntity.Builder.newInstance("username", ID).build());

        List<UserEntity> userEntityList = test.userList();

        assertFalse(userEntityList.isEmpty());
        assertEquals(1, userEntityList.size());
    }

    @Test
    public void testUserListReturnsListWhenMultipleUsers() throws Exception {
        test.addUser(UserEntity.Builder.newInstance("username1", ID).build());
        test.addUser(UserEntity.Builder.newInstance("username2", UUID.randomUUID()).build());

        List<UserEntity> userEntityList = test.userList();

        assertFalse(userEntityList.isEmpty());
        assertEquals(2, userEntityList.size());
    }

    @Test(expected = PersistenceException.class)
    public void testAddUserWhenUsernameAlreadyExists() throws Exception {
        test.addUser(UserEntity.Builder.newInstance("username", ID).build());
        test.addUser(UserEntity.Builder.newInstance("username", UUID.randomUUID()).build());
    }

    @Test
    public void testFindUserByUsernameReturnsCorrectUser() throws Exception {
        test.addUser(UserEntity.Builder.newInstance("username", ID)
                .withPasswordHash("password")
                .withSalt("salt")
                .build());

        assertNull(test.findUser("blah"));

        UserEntity user = test.findUser("username");
        assertNotNull(user);
        assertEquals("password", user.getPasswordHash());
        assertEquals("salt", user.getSalt());
    }

    @Test(expected = PersistenceException.class)
    public void testFindUserThrowsExceptionWhenBlankUsername() throws Exception {
        test.findUser("");
    }

    @Test(expected = PersistenceException.class)
    public void testFindUserThrowsExceptionWhenNullUsername() throws Exception {
        test.findUser(null);
    }
}
