package ru.nsu.fit;

import java.io.IOException;
import java.net.InetAddress;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class MulticastMember {
    private final MulticastSender multicastSender;
    private final MulticastReceiver multicastReceiver;
    private final MembersHandler membersHandler;

    public MulticastMember(String multiCastAddress) throws IOException {
        Map<InetAddress, Integer> liveMembers = new ConcurrentHashMap();
        multicastSender = new MulticastSender(multiCastAddress);
        membersHandler = new MembersHandler(liveMembers);
        multicastReceiver = new MulticastReceiver(multiCastAddress, membersHandler);
    }

    public void startMember() {
        membersHandler.start();
        multicastSender.start();
        multicastReceiver.start();
    }

    public void disconnectMember() {
        try {
            membersHandler.disconnect();
            multicastReceiver.disconnect();
            multicastSender.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
