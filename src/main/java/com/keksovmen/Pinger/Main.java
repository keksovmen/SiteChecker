package com.keksovmen.Pinger;

import javax.swing.*;
import javax.swing.table.TableModel;

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
        Ping ping = new Ping();
        FileHandler fileHandler = new FileHandler(ping);
        if(!fileHandler.init()){
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
