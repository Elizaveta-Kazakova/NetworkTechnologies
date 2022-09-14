package ru.nsu.fit.networkTechnologies.lab1;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

public class MulticastMember {
    private MulticastSender multicastSender;
    private MulticastReceiver multicastReceiver;

    public MulticastMember(String multiCastAddress) throws IOException {
        List<InetAddress> liveMembers = new ArrayList<>(List.of(InetAddress.getLocalHost()));
        multicastSender = new MulticastSender(multiCastAddress);
        multicastReceiver = new MulticastReceiver(multiCastAddress, liveMembers);
    }

    public void startMember() {
        multicastSender.start();
        multicastReceiver.start();
    }

    public void disconnectMember() {
        try {
            multicastReceiver.disconnect();
            multicastSender.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
