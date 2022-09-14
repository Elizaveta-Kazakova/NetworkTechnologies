package ru.nsu.fit.networkTechnologies.lab1;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MembersHandler extends Thread {
    private static final double TIME_OF_DELIVERY = Math.pow(10, 6);

    private List<InetAddress> liveMembers;
    private boolean isChanged = false;
    private Map<InetAddress, Long> timeOfReceived = new HashMap<>();
    private boolean isAlive = true;

    private void deleteOldMembers() {
        List<InetAddress> membersCopy = new ArrayList<>(liveMembers);
        liveMembers.removeIf(member -> (System.currentTimeMillis() - timeOfReceived.get(member)) > TIME_OF_DELIVERY);
        if (!membersCopy.equals(liveMembers)) {
            isChanged = true;
        }
    }

    private void addNewMembers(InetAddress inetAddress) {
        if (liveMembers.contains(inetAddress)) {
            return;
        }
        liveMembers.add(inetAddress);
        isChanged = true;
    }

    public void updateLiveMembers(InetAddress inetAddress) {
        timeOfReceived.put(inetAddress, System.currentTimeMillis());
        deleteOldMembers();
        addNewMembers(inetAddress);
    }

    public MembersHandler(List<InetAddress> liveMembers) {
        this.liveMembers = liveMembers;
        System.out.println("initial live members " + liveMembers);
    }

    @Override
    public void run() {
        while(isAlive) {
            if (isChanged) {
                System.out.println("list of members was changed : " + liveMembers);
                isChanged = false;
            }

        }
    }

    public void disconnect() {
        isAlive = false;
    }
}
