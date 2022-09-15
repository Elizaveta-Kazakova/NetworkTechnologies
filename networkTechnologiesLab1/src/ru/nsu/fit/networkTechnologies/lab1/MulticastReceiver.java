package ru.nsu.fit.networkTechnologies.lab1;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class MulticastReceiver extends Thread {
    private static final int PORT = 8080;
    private static final int BUF_SIZE = 256;

    private final InetAddress multiCastAddress;

    private MembersHandler membersHandler;
    private byte[] receiveBuffer = new byte[BUF_SIZE];
    private MulticastSocket multicastSocket;
    private boolean isAlive = true;

    public MulticastReceiver(String multiCastAddress, MembersHandler membersHandler) throws IOException {
        multicastSocket = new MulticastSocket(PORT);
        this.multiCastAddress = InetAddress.getByName(multiCastAddress);
        this.membersHandler = membersHandler;
    }

    @Override
    public void run() {
        try {
            multicastSocket.joinGroup(multiCastAddress);
            while (isAlive) {
                DatagramPacket receivedPacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                multicastSocket.receive(receivedPacket);
                // обработка пакета
                InetAddress inetAddress = receivedPacket.getAddress();
                membersHandler.updateLiveMembers(inetAddress);
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
