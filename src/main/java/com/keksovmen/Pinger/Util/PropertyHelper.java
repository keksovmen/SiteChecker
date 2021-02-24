package com.keksovmen.Pinger.Util;

import com.keksovmen.Pinger.Handlers.FileHandler;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.function.Consumer;

public class PropertyHelper {

    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    private final Properties properties;

    public PropertyHelper() {
        Properties defProp = new Properties();
        defProp.setProperty("Delay", "5");

        properties = new Properties(defProp);
        try {
            properties.load(Files.newBufferedReader(getFilePath(), DEFAULT_CHARSET));
        } catch (IOException e) {
            //ignore it, means there is troubles with file opening or file is not exists
            e.printStackTrace();
        }
    }

    public Consumer<Integer> createSaveFunction() {
        return integer -> {
            properties.setProperty("Delay", String.valueOf(integer));

            try {
                properties.store(Files.newBufferedWriter(getFilePath(), DEFAULT_CHARSET), null);
            } catch (IOException e) {
                //ignore it, means there is troubles with file opening
                e.printStackTrace();
            }
        };
    }

    public int getDelay() {
        return Integer.parseInt(properties.getProperty("Delay"));
    }

    private Path getFilePath() {
        return Paths.get(FileHandler.DESTINATION_PATH.getParent().toString(), "Prop.prop");
    }


}
