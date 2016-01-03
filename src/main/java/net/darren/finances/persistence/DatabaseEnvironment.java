package net.darren.finances.persistence;

import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;
import com.sleepycat.persist.EntityStore;
import com.sleepycat.persist.StoreConfig;

import java.io.File;

public class DatabaseEnvironment {

    private final Environment environment;
    private final StoreConfig storeConfig;

    public DatabaseEnvironment(String homeDir, boolean readOnly) {
        EnvironmentConfig environmentConfig = new EnvironmentConfig();
        environmentConfig.setReadOnly(readOnly);
        environmentConfig.setAllowCreate(!readOnly);

        storeConfig = new StoreConfig();
        storeConfig.setReadOnly(readOnly);
        storeConfig.setAllowCreate(!readOnly);

        environment = new Environment(new File(homeDir), environmentConfig);
    }

    public EntityStore entityStore(String entityStoreName) {
        return new EntityStore(environment, entityStoreName, storeConfig);
    }

    public void close() {
        environment.close();
    }
}
