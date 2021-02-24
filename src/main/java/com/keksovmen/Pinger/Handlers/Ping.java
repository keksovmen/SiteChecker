package com.keksovmen.Pinger.Handlers;

import com.keksovmen.Pinger.Util.Observer;
import com.keksovmen.Pinger.Util.SiteState;
import com.keksovmen.Pinger.Util.Subject;
import org.icmp4j.IcmpPingRequest;
import org.icmp4j.IcmpPingResponse;
import org.icmp4j.IcmpPingUtil;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Ping extends AbstractHandler implements Observer<String>, Subject<SiteState> {

    private final Map<String, IcmpPingRequest> requestMap = new HashMap<>();
    private final Map<String, SiteState> siteStateMap = Collections.synchronizedMap(new HashMap<>());
    private final List<Observer<SiteState>> observerList = new LinkedList<>();

    private final ExecutorService cachedExecutor = Executors.newCachedThreadPool();
    private final ScheduledExecutorService scheduledExecutor = Executors.newSingleThreadScheduledExecutor();

    /**
     * In millis
     */
    private int delay;

    public Ping(Handler successor, int delay) {
        super(successor);
        changeDelay(delay);
    }

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
        observerList.forEach(observer -> observer.observe(List.copyOf(siteStateMap.values())));
    }


    @Override
    public boolean addSite(String site) {
        if (siteAlreadyExists(site)) {
            return false;
        }
        createAndAdd(site);
        sayChanges();
        return super.addSite(site);
    }

    @Override
    public boolean removeSite(String site) {
        requestMap.remove(site);
        siteStateMap.remove(site);
        sayChanges();
        return super.removeSite(site);
    }

    @Override
    public boolean changeDelay(int delay) {
        this.delay = delay * 1000;
        return super.changeDelay(delay);
    }


    private void createAndAdd(String site) {
        IcmpPingRequest request = IcmpPingUtil.createIcmpPingRequest();
        request.setHost(site);
        schedule(request, site);
        requestMap.put(site, request);
        siteStateMap.put(site, new SiteState(site));
    }

    private void schedule(IcmpPingRequest request, String site) {
        cachedExecutor.execute(createTask(request, site, delay));
    }

    private Runnable createTask(IcmpPingRequest request, String site, long delay) {
        return () -> {
            IcmpPingResponse response = IcmpPingUtil.executePingRequest(request);

            SiteState state = siteStateMap.get(site);
            if (state == null) {
                return;
            }

            state.update(defineIsAlive(response), response.getRtt());
            sayChanges();

            if (delay == 0) {
                schedule(request, site);
            } else {
                scheduledExecutor.schedule(() -> schedule(request, site), delay, TimeUnit.MILLISECONDS);
            }
        };
    }

    private boolean defineIsAlive(IcmpPingResponse response) {
        String e = response.getErrorMessage();
        if (e == null)
            return true;
        else
            return e.equals("SUCCESS");
    }

    private boolean siteAlreadyExists(String site) {
        if (siteStateMap.containsKey(site)) {
            updateError(ErrorCode.ALREADY_EXISTS);
            return true;
        }

        return false;
    }

}
