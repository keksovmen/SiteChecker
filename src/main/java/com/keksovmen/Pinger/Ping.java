package com.keksovmen.Pinger;

import org.icmp4j.AsyncCallback;
import org.icmp4j.IcmpPingRequest;
import org.icmp4j.IcmpPingResponse;
import org.icmp4j.IcmpPingUtil;

import java.util.*;
import java.util.concurrent.*;

public class Ping implements Observer<String>, Subject<SiteState>, Handler {

    private final Map<String, IcmpPingRequest> requestMap = new HashMap<>();
    private final Map<String, SiteState> siteStateMap = Collections.synchronizedMap(new HashMap<>());
    private final List<Observer<SiteState>> observerList = new LinkedList<>();



    @Override
    public void observe(List<String> data) {
        requestMap.clear();
        siteStateMap.clear();
        data.forEach(this::createAndAdd);
        sayChanges();
    }

    @Override
    public void attach(Observer<SiteState> observer) {
        observerList.add(observer);
        observer.observe(List.copyOf(siteStateMap.values()));
    }

    @Override
    public void detach(Observer<SiteState> observer) {
        observerList.remove(observer);
    }

    @Override
    public void sayChanges() {
        observerList.forEach(observer -> {
            observer.observe(List.copyOf(siteStateMap.values()));
        });
    }


    @Override
    public boolean addSite(String site) {
        createAndAdd(site);
        sayChanges();
        return true;
    }

    @Override
    public boolean removeSite(String site) {
        requestMap.remove(site);
        siteStateMap.remove(site);
        sayChanges();
        return false;
    }

    private void createAndAdd(String site) {
        IcmpPingRequest request = IcmpPingUtil.createIcmpPingRequest();
        request.setHost(site);
        schedule(request, site);
        requestMap.put(site, request);
        siteStateMap.put(site, new SiteState(site));
    }

    private void schedule(IcmpPingRequest request, String site) {
        IcmpPingUtil.executePingRequest(request, new AsyncCallback<IcmpPingResponse>() {
            @Override
            public void onSuccess(IcmpPingResponse response) {
                SiteState state = siteStateMap.get(site);
                if (state == null) {
                    return;
                }

                boolean isAlive = false;
                String e = response.getErrorMessage();
                if (e == null) {
                    isAlive = true;
                } else {
                    isAlive = e.equals("SUCCESS");
                }
                state.update(isAlive, response.getRtt());
                sayChanges();

                try {
                    Thread.sleep(5000);
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }

                IcmpPingUtil.executePingRequest(request, this);
            }

            @Override
            public void onFailure(Throwable throwable) {
                throwable.printStackTrace();
            }
        });
    }

}
