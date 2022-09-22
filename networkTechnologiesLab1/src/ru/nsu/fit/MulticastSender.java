package ru.nsu.fit;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;

public class MulticastSender extends Thread {
    private static final int PORT = 8080;
    private static final String DEFAULT_MESSAGE = "hello";

    private final MulticastSocket multicastSocket;
    private final InetAddress multiCastAddress;
    private final InetSocketAddress multicastSocketAddress;
    private boolean isAlive = true;

    public MulticastSender(String multiCastAddress) throws IOException {
        multicastSocket = new MulticastSocket();
        this.multiCastAddress = InetAddress.getByName(multiCastAddress);
        multicastSocketAddress = new InetSocketAddress(multiCastAddress, PORT);
    }

    @Override
    public void run() {
        try {
            multicastSocket.joinGroup(multicastSocketAddress, null);
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
        multicastSocket.leaveGroup(multicastSocketAddress, null);
        multicastSocket.close();
    }
}
