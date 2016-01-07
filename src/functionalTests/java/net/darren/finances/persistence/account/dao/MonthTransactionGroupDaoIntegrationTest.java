package net.darren.finances.persistence.account.dao;

import com.sleepycat.persist.EntityStore;
import net.darren.finances.persistence.DatabaseEnvironment;
import net.darren.finances.persistence.account.entities.MonthTransactionGroupEntity;
import net.darren.persistence.util.TestFileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

public class MonthTransactionGroupDaoIntegrationTest {

    private MonthTransactionGroupDao test;
    private File testFileDir;
    private DatabaseEnvironment environment;
    private EntityStore entityStore;

    @Before
    public void setUp() {
        String database_dir = System.getProperties().getProperty("testDir");
        testFileDir = new File(database_dir);
        testFileDir.mkdirs();

        environment = new DatabaseEnvironment(database_dir, false);
        entityStore = environment.entityStore("testDB");

        test = new MonthTransactionGroupDao(entityStore);
    }

    @After
    public void tearDown() {
        test.close();
        environment.close();
        TestFileUtils.delete(testFileDir);
        assertFalse(testFileDir.exists());
    }

    @Test
    public void testGetAllForAccount_ShouldReturnTransactionGroupsForAccount() {
        UUID accountId = UUID.randomUUID();

        test.addTransactionGroup(MonthTransactionGroupEntity.Builder.newInstance(12,2015, accountId).build());

        List<MonthTransactionGroupEntity> monthTransactionGroupEntityList = test.getAllFor(accountId);

        assertNotNull(monthTransactionGroupEntityList);
        assertFalse(monthTransactionGroupEntityList.isEmpty());
    }


}
