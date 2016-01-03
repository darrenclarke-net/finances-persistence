package net.darren.finances.persistence.account.dao;

import com.sleepycat.je.ForeignConstraintException;
import com.sleepycat.persist.EntityStore;
import net.darren.finances.persistence.DatabaseEnvironment;
import net.darren.finances.persistence.account.entities.AccountEntity;
import net.darren.finances.persistence.user.dao.UserDao;
import net.darren.finances.persistence.user.entities.UserEntity;
import net.darren.persistence.util.TestFileUtils;
import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;

public class AccountDaoIntegrationTest {

    private EntityStore entityStore;
    private File testFileDir;
    private DatabaseEnvironment environment;

    private AccountDao test;

    private UUID userId1;
    private UUID userId2;

    @Before
    public void setUp() throws Exception {
        String database_dir = System.getProperties().getProperty("testDir");
        testFileDir = new File(database_dir);
        testFileDir.mkdirs();

        environment = new DatabaseEnvironment(database_dir, false);
        entityStore = environment.entityStore("testDB");

        UserDao userDao = new UserDao(entityStore);
        userId1 = UUID.randomUUID();
        userDao.addUser(UserEntity.Builder.newInstance("dummy user1", userId1).build());

        userId2 = UUID.randomUUID();
        userDao.addUser(UserEntity.Builder.newInstance("dummy user2", userId2).build());

        test = new AccountDao(entityStore);

    }

    @After
    public void tearDown() throws Exception {
        test.close();
        environment.close();
        TestFileUtils.delete(testFileDir);
        assertFalse(testFileDir.exists());
    }

    @Test
    public void testAddAndRetrieveAccount() {
        UUID accountId = UUID.randomUUID();
        test.addAccount(AccountEntity.Builder.newInstance(accountId, userId1)
                .withName("accountName")
                .withDescription("Account description")
                .withStartBalance(0)
                .withStartDate(new LocalDate(2015, 12, 14).toString())
                .build());

        AccountEntity accountEntity = test.getAccount(accountId);

        assertNotNull(accountEntity);
        assertEquals("accountName", accountEntity.getName());
        assertEquals("Account description", accountEntity.getDescription());
        assertEquals(0, accountEntity.getStartBalance());
        assertTrue(new LocalDate(2015, 12, 14).isEqual(new LocalDate(accountEntity.getStartDate())));
    }

    @Test
    public void testRetrieveAllAccountsForUserId() {

        test.addAccount(AccountEntity.Builder.newInstance(UUID.randomUUID(), userId1).build());
        test.addAccount(AccountEntity.Builder.newInstance(UUID.randomUUID(), userId1).build());
        test.addAccount(AccountEntity.Builder.newInstance(UUID.randomUUID(), userId2).build());

        List<AccountEntity> accountList = test.accountsFor(userId1);

        assertNotNull(accountList);
        assertEquals(2, accountList.size());


    }

    @Test(expected = ForeignConstraintException.class)
    public void testAddAccountWhenUserDoesNotExist() {
        test.addAccount(AccountEntity.Builder.newInstance(UUID.randomUUID(), UUID.randomUUID()).build());
    }
}