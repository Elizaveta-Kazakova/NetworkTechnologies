package ru.nsu.fit.networkTechnologies.lab1;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MembersHandler extends Thread {
    private static final double UPDATED_TIME = 0.0;
    private static final double TIME_OF_DELIVERY = Math.pow(10, 6);

    private List<InetAddress> liveMembers;
    private boolean isChanged = false;
    private Map<InetAddress, Double> pingTime = new HashMap<>();
    private boolean isAlive = true;

    private void updateMemberTime(InetAddress member) {
        pingTime.put(member, System.currentTimeMillis() - pingTime.getOrDefault(member, UPDATED_TIME));
    }

    private void deleteOldMembers() {
        for (InetAddress member : liveMembers) {
            updateMemberTime(member);
        }
        System.out.println(liveMembers);
        List<InetAddress> membersCopy = new ArrayList<>(liveMembers);
        liveMembers.removeIf(member -> pingTime.get(member) > TIME_OF_DELIVERY);
        if (!membersCopy.equals(liveMembers)) {
            isChanged = true;
        }
        //for (InetAddress member : liveMembers) {
        //    updateMemberTime(member);
        //    if (pingTime.get(member) > TIME_OF_DELIVERY) {
        //        pingTime.remove(member);
        //        liveMembers.remove(member);
        //        isChanged = true;
        //    }
        //}
    }

    private void addNewMembers(InetAddress inetAddress) {
        if (liveMembers.contains(inetAddress)) {
            return;
        }
        liveMembers.add(inetAddress);
        isChanged = true;
    }

    public void updateLiveMembers(InetAddress inetAddress) {
        pingTime.put(inetAddress, UPDATED_TIME);
        deleteOldMembers();
        addNewMembers(inetAddress);
    }

    public MembersHandler(List<InetAddress> liveMembers) {
        this.liveMembers = liveMembers;
    }

    @Override
    public void run() {
        while(isAlive) {
            if (isChanged) {
                System.out.println(liveMembers);
                isChanged = false;
            }

        }
    }

    public void disconnect() throws IOException {
        isAlive = false;
    }
}
