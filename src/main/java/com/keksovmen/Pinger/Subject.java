package com.keksovmen.Pinger;

public interface Subject<T> {

    public void attach(Observer<T> observer);

    public void detach(Observer<T> observer);

    public void sayChanges();
}
