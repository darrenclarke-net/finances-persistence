package net.darren.persistence.util;

import java.io.File;

public class TestFileUtils {
    public static void delete(File directory) {
        File[] contents = directory.listFiles();
        for(File file : contents) {
            if (file.isDirectory()) {
                delete(file);
                file.delete();
            } else {
                file.delete();
            }
        }
        directory.delete();
    }
}
