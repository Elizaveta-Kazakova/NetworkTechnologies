package ru.nsu.fit.networkTechnologies.lab1;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MembersHandler extends Thread {
    private static final double TIME_OF_DELIVERY = Math.pow(10, 3);

    private Set<InetAddress> liveMembers;
    private Set<InetAddress> membersCopy;
    private Map<InetAddress, Long> timeOfReceived = new HashMap<>();
    private boolean isAlive = true;

    private void deleteOldMembers() {
        liveMembers.removeIf(member -> (System.currentTimeMillis() - timeOfReceived.get(member)) > TIME_OF_DELIVERY);
    }

    private void addNewMembers(InetAddress inetAddress) {
        if (!liveMembers.contains(inetAddress)) {
            liveMembers.add(inetAddress);
        }
    }

    public void updateLiveMembers(InetAddress inetAddress) {
        timeOfReceived.put(inetAddress, System.currentTimeMillis());
        deleteOldMembers();
        addNewMembers(inetAddress);
    }

    private void initTimeReceived() {
        for (InetAddress member : liveMembers) {
            timeOfReceived.put(member, System.currentTimeMillis());
        }
    }

    public MembersHandler(Set<InetAddress> liveMembers) {
        this.liveMembers = liveMembers;
        membersCopy = new HashSet<>(liveMembers);
        initTimeReceived();
    }

    @Override
    public void run() {
        while(isAlive) {
            Set<InetAddress> localLiveMembers = new HashSet<>(liveMembers);
            if (!membersCopy.equals(localLiveMembers)) {
                System.out.println("list of members was changed : " + localLiveMembers);
                membersCopy = new HashSet<>(localLiveMembers);
            }
        }
    }

    public void disconnect() {
        isAlive = false;
    }
}
