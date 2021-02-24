package com.keksovmen.Pinger.Util;

public class SiteState {

    public final static int FILEDS_AMOUTN = 3;

    private final String address;

    private boolean isActive = false;
    private int lastResponseTime = -1;

    public SiteState(String address) {
        this.address = address;
    }


    public void update(boolean isActive, int responseTime){
        this.isActive = isActive;
        this.lastResponseTime = responseTime;
    }

    public String getAddress() {
        return address;
    }

    public boolean isActive() {
        return isActive;
    }

    public int getLastResponseTime() {
        return lastResponseTime;
    }
}
