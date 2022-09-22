package ru.nsu.fit;

import java.net.InetAddress;
import java.sql.SQLOutput;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class MembersHandler extends Thread {
    private static final double TIME_OF_DELIVERY = Math.pow(10, 3);

    private final Map<InetAddress, Integer> liveMembers;
    private Map<InetAddress, Integer> membersCopy;
    private final Map<InetAddress, Long> timeOfReceived = new HashMap<>();
    private boolean isAlive = true;

    private void deleteOldMembers() {
        for (Map.Entry<InetAddress, Integer> entry : liveMembers.entrySet()) {
            if ( System.currentTimeMillis() - timeOfReceived.get(entry.getKey())  > TIME_OF_DELIVERY) {
                liveMembers.remove(entry.getKey());
            }
        }
    }

    private void addNewMembers(InetAddress inetAddress, int port) {
        liveMembers.put(inetAddress, port);
    }

    private void printAllMembers() {
        for (Map.Entry<InetAddress, Integer> entry : liveMembers.entrySet()) {
            System.out.println("ip = " + entry.getKey() + " port = " + entry.getValue() );
        }
    }

    public void updateLiveMembers(InetAddress inetAddress, int port) {
        timeOfReceived.put(inetAddress, System.currentTimeMillis());
        deleteOldMembers();
        addNewMembers(inetAddress, port);
    }

    private void initTimeReceived() {
        for (Map.Entry<InetAddress, Integer> entry : liveMembers.entrySet()) {
            timeOfReceived.put(entry.getKey(), System.currentTimeMillis());
        }
    }

    public MembersHandler(Map<InetAddress, Integer> liveMembers) {
        this.liveMembers = liveMembers;
        membersCopy = new ConcurrentHashMap<>(liveMembers);
        initTimeReceived();
    }

    @Override
    public void run() {
        while(isAlive) {
            Map<InetAddress, Integer> localLiveMembers;
            synchronized (liveMembers) {
                localLiveMembers = new ConcurrentHashMap<>(liveMembers);
            }
            if (!membersCopy.equals(localLiveMembers)) {
                printAllMembers();
                membersCopy = new ConcurrentHashMap<>(localLiveMembers);
            }
        }
    }

    public void disconnect() {
        isAlive = false;
    }
}
