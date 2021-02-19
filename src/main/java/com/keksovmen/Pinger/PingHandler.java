package com.keksovmen.Pinger;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class PingHandler implements Handler, Subject<String>{

    private final List<String> sites = new LinkedList<>();
    private final List<Observer> observers = new LinkedList<>();

    @Override
    public boolean addSite(String site) {
        sites.add(site);
        return true;
    }

    @Override
    public boolean removeSite(String site) {
        sites.remove(site);
        return true;
    }

    @Override
    public void attach(Observer<String> observer) {
        observers.add(observer);
        observer.observe(Collections.unmodifiableList(sites));
    }

    @Override
    public void detach(Observer<String> observer) {
        observers.remove(observer);
    }

    @Override
    public void sayChanges() {
        observers.forEach(observer ->
                observer.observe(Collections.unmodifiableList(sites)));
    }
}
