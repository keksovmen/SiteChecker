package com.keksovmen.Pinger;

import com.keksovmen.Pinger.Handlers.FileHandler;
import com.keksovmen.Pinger.Handlers.Ping;
import com.keksovmen.Pinger.SwingParts.MainPage;
import com.keksovmen.Pinger.SwingParts.PingModel;
import com.keksovmen.Pinger.Util.PropertyHelper;

import javax.swing.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.function.Consumer;

public class Main {

    public static void main(String[] args) {

        PropertyHelper propertyHelper = new PropertyHelper();
        final int delayProperty = propertyHelper.getDelay();

        Ping ping = new Ping(null, delayProperty);
        FileHandler fileHandler = new FileHandler(ping);
        if (!fileHandler.init()) {
            SwingUtilities.invokeLater(() ->
                    JOptionPane.showMessageDialog(
                            null,
                            "Error with file initialisation",
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    ));
        }
        ping.observe(fileHandler.readAllLines());
        PingModel model = new PingModel();

        ping.attach(model);
        SwingUtilities.invokeLater(() -> {
            MainPage mainPage = new MainPage(
                    fileHandler,
                    model,
                    propertyHelper.createSaveFunction(),
                    delayProperty);
        });

    }
}
