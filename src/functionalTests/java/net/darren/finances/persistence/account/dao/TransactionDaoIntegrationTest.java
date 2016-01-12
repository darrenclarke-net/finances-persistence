package net.darren.finances.persistence.account.dao;

import com.sleepycat.persist.EntityStore;
import net.darren.finances.persistence.DatabaseEnvironment;
import net.darren.finances.persistence.account.entities.TransactionEntity;
import net.darren.persistence.util.TestFileUtils;
import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;

public class TransactionDaoIntegrationTest {

    private static final String GROUP_ID = "groupID";
    public static final String NAME = "name";
    public static final String DESCRIPTION = "description";
    public static final int AMOUNT = 10;
    private static final String TYPE = "DEBIT";

    private EntityStore entityStore;
    private File testFileDir;
    private DatabaseEnvironment environment;

    private TransactionDao test;

    @Before
    public void setUp() {
        String database_dir = System.getProperties().getProperty("testDir");
        testFileDir = new File(database_dir);
        testFileDir.mkdirs();

        environment = new DatabaseEnvironment(database_dir, false);
        entityStore = environment.entityStore("testDB");

        test = new TransactionDao(entityStore);
    }

    @After
    public void tearDown() {
        test.close();
        environment.close();
        TestFileUtils.delete(testFileDir);
        assertFalse(testFileDir.exists());
    }

    @Test
    public void testAddAndRetrieveTransaction() {
        UUID transactionId = UUID.randomUUID();
        LocalDate transactionDate = new LocalDate();


        test.add(TransactionEntity.Builder.newInstance(transactionId)
                .withGroupId(GROUP_ID)
                .withDate(transactionDate)
                .withName(NAME)
                .withDescription(DESCRIPTION)
                .withAmount(AMOUNT)
                .withType(TYPE)
                .build());

        TransactionEntity transactionEntity = test.retrieve(transactionId);
        assertNotNull("TransactionEntity was null", transactionEntity);
        assertEquals(transactionId.toString(), transactionEntity.getId());
        assertEquals(GROUP_ID, transactionEntity.getGroupId());
        assertEquals(transactionDate.toString(), transactionEntity.getDate());
        assertEquals(NAME, transactionEntity.getName());
        assertEquals(DESCRIPTION, transactionEntity.getDescription());
        assertEquals(AMOUNT, transactionEntity.getAmount());
        assertEquals(TYPE, transactionEntity.getType());
    }

    @Test
    public void testRetrieveAllForGroupWhenGroupEmpty() {
        List<TransactionEntity> transactions = test.retrieveAll(GROUP_ID);

        assertTrue(transactions.isEmpty());
    }

    @Test
    public void testRetrieveAllForGroupWhenGroupHasOneTransaction() {
        test.add(TransactionEntity.Builder.newInstance(UUID.randomUUID())
            .withGroupId(GROUP_ID)
            .build());

        List<TransactionEntity> transactions = test.retrieveAll(GROUP_ID);

        assertEquals(1, transactions.size());
    }



}
