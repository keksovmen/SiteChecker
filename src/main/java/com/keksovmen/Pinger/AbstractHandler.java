package com.keksovmen.Pinger;

public abstract class AbstractHandler implements Handler {

    protected final Handler successor;

    private ErrorCode lastError = null;


    public AbstractHandler(Handler successor) {
        this.successor = successor;
    }

    @Override
    public boolean addSite(String site) {
        if (successor == null)
            return true;

        return successor.addSite(site);
    }

    @Override
    public boolean removeSite(String site) {
        if (successor == null)
            return true;

        return successor.removeSite(site);
    }

    @Override
    public boolean changeDelay(int delay) {
        if (successor == null)
            return true;

        return successor.changeDelay(delay);
    }

    @Override
    public ErrorCode getLastErrorCode() {
        if (lastError == null) {
            if (successor != null) {
                return successor.getLastErrorCode();
            }

            return ErrorCode.OK;
        } else {
            return lastError;
        }
    }

    protected void updateError(ErrorCode code) {
        lastError = code;
    }
}
