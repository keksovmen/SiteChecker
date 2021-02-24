package com.keksovmen.Pinger.Util;

public interface Subject<T> {

    public void attach(Observer<T> observer);

    public void detach(Observer<T> observer);

    public void sayChanges();
}
