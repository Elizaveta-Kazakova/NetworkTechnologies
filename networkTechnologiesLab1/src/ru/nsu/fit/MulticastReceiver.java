package ru.nsu.fit;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;

public class MulticastReceiver extends Thread {
    private static final int PORT = 8080;
    private static final int BUF_SIZE = 256;

    private final InetSocketAddress multicastSocketAddress;
    private final MembersHandler membersHandler;
    private final byte[] receiveBuffer = new byte[BUF_SIZE];
    private final MulticastSocket multicastSocket;
    private final NetworkInterface networkInterface;
    private boolean isAlive = true;

    public MulticastReceiver(String multiCastAddress, MembersHandler membersHandler) throws IOException {
        multicastSocket = new MulticastSocket(PORT);
        this.membersHandler = membersHandler;
        multicastSocketAddress = new InetSocketAddress(multiCastAddress, PORT);
        networkInterface = NetworkInterface.getByInetAddress(InetAddress.getLocalHost());
    }

    @Override
    public void run() {
        try {
            multicastSocket.joinGroup(multicastSocketAddress, networkInterface);
            while (isAlive) {
                DatagramPacket receivedPacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                multicastSocket.receive(receivedPacket);
                // handling package
                InetAddress inetAddress = receivedPacket.getAddress();
                membersHandler.updateLiveMembers(inetAddress, receivedPacket.getPort());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void disconnect() throws IOException {
        isAlive = false;
        multicastSocket.leaveGroup(multicastSocketAddress, networkInterface);
        multicastSocket.close();
    }
}
