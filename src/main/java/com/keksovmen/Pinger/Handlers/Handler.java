package com.keksovmen.Pinger.Handlers;

public interface Handler {

    public enum ErrorCode {
        OK(0, "OK"),
        FILE_OPEN(1, "Can't open file"),
        ALREADY_EXISTS(2, "Site already exists");

        public final int id;
        public final String description;

        ErrorCode(int index, String description) {
            id = index;
            this.description = description;
        }
    }

    public boolean addSite(String site);

    public boolean removeSite(String site);

    /**
     * @param delay in seconds
     * @return true if handled well
     */
    public boolean changeDelay(int delay);

    public ErrorCode getLastErrorCode();

}
