package com.keksovmen.Pinger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class FileHandler extends AbstractHandler {

    public static final String DIRECTORY_NAME = "Pinger_Files_Storage";
    public static final String FILE_NAME = "Data.txt";
    public static final Path DESTINATION_PATH =
            Paths.get(System.getProperty("user.home"), DIRECTORY_NAME, FILE_NAME);


    private boolean fileCreated = false;

    public FileHandler(Handler successor) {
        super(successor);
    }

    @Override
    public boolean addSite(String site) {
        if (!fileCreated) {
            return false;
        }
        if (site.endsWith("\n")) {
            site = site.trim();
        }
        try {
            Files.writeString(DESTINATION_PATH, site + "\n", StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
            updateError(ErrorCode.FILE_OPEN);
            return false;
        }

        return super.addSite(site);
    }

    @Override
    public boolean removeSite(String site) {
        if (!fileCreated) {
            return false;
        }

        List<String> allSites = readAllLines();
        allSites.remove(site);

        try {
            Files.write(
                    DESTINATION_PATH,
                    allSites,
                    StandardOpenOption.WRITE,
                    StandardOpenOption.TRUNCATE_EXISTING
            );
        } catch (IOException e) {
            e.printStackTrace();
            updateError(ErrorCode.FILE_OPEN);
            return false;
        }

        return super.removeSite(site);
    }

    @Override
    public boolean changeDelay(int delay) {
        //save to property map
        return super.changeDelay(delay);
    }


    public boolean init() {
        Path storagePath = Paths.get(System.getProperty("user.home"), DIRECTORY_NAME);
        if (!Files.exists(storagePath)) {
            try {
                Files.createDirectory(storagePath);
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }

        if (!Files.exists(DESTINATION_PATH)) {
            try {
                Files.createFile(DESTINATION_PATH);
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }

        fileCreated = true;
        return true;
    }

    public List<String> readAllLines() {
        if (!fileCreated) {
            return new ArrayList<>();
        }
        try {
            return Files.readAllLines(DESTINATION_PATH);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }
}
