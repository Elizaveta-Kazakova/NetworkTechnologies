package ru.nsu.fit.networkTechnologies.lab1;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

public class MulticastMember {
    private MulticastSender multicastSender;
    private MulticastReceiver multicastReceiver;
    private MembersHandler membersHandler;

    public MulticastMember(String multiCastAddress) throws IOException {
        List<InetAddress> liveMembers = new ArrayList<>(List.of(InetAddress.getLocalHost()));
        multicastSender = new MulticastSender(multiCastAddress);
        multicastReceiver = new MulticastReceiver(multiCastAddress, liveMembers);
        membersHandler = new MembersHandler(liveMembers);
    }

    public void startMember() {
        membersHandler.start();
        multicastSender.start();
        multicastReceiver.start();
    }

    public void disconnectMember() {
        try {
            membersHandler.disconnect();
            multicastSender.disconnect();
            multicastSender.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
