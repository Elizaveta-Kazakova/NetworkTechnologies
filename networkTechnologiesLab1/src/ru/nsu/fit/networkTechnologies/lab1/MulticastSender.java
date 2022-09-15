package ru.nsu.fit.networkTechnologies.lab1;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;

public class MulticastSender extends Thread {
    private static final int PORT = 8080;
    private static final String DEFAULT_MESSAGE = "hello";

    private MulticastSocket multicastSocket;
    private InetAddress multiCastAddress;
    private boolean isAlive = true;

    public MulticastSender(String multiCastAddress) throws IOException {
        multicastSocket = new MulticastSocket();
        this.multiCastAddress = InetAddress.getByName(multiCastAddress);
    }

    @Override
    public void run() {
        try {
            //NetworkInterface networkInterface = new NetworkInterface()
            multicastSocket.joinGroup(multiCastAddress);
            while (isAlive) {
                DatagramPacket sendingPacket = new DatagramPacket(DEFAULT_MESSAGE.getBytes(),
                        DEFAULT_MESSAGE.length(), multiCastAddress, PORT);
                multicastSocket.send(sendingPacket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void disconnect() throws IOException {
        isAlive = false;
        multicastSocket.leaveGroup(multiCastAddress);
        multicastSocket.close();
    }
}
