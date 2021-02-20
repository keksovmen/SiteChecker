package com.keksovmen.Pinger;

public interface Handler {

    public boolean addSite(String site);

    public boolean removeSite(String site);

    /**
     * @param delay in seconds
     * @return true if handled well
     */
    public boolean changeDelay(int delay);


}
