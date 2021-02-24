package com.keksovmen.Pinger;

import com.keksovmen.Pinger.Handlers.FileHandler;
import com.keksovmen.Pinger.Handlers.Ping;
import com.keksovmen.Pinger.SwingParts.MainPage;
import com.keksovmen.Pinger.SwingParts.PingModel;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
//        IcmpPingRequest pingRequest = IcmpPingUtil.createIcmpPingRequest();
//        pingRequest.setHost("www.google.com");
//        pingRequest.setHost("10.60.0.100");

//        IcmpPingResponse pingResponse = IcmpPingUtil.executePingRequest(pingRequest);
//        String result = IcmpPingUtil.formatResponse(pingResponse);
//        System.out.println(result);
//
//
//        pingRequest = IcmpPingUtil.createIcmpPingRequest();
//        pingRequest.setHost("www.google.ru");
//
//        pingResponse = IcmpPingUtil.executePingRequest(pingRequest);
//        result = IcmpPingUtil.formatResponse(pingResponse);
//        System.out.println(result);
        Ping ping = new Ping(null, 5000);
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
            MainPage mainPage = new MainPage(fileHandler, model);
        });
//        if(fileHandler.init()){
//            fileHandler.addSite("www.loh.com");
//            fileHandler.addSite("www.boss.com");
//            fileHandler.addSite("127.0.0.1");
//
//            fileHandler.removeSite("www.boss.com");
//        }


    }
}
