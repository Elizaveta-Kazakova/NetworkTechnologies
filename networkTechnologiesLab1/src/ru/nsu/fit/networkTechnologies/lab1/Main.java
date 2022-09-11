package ru.nsu.fit.networkTechnologies.lab1;

import java.io.IOException;

public class Main {
    private static final int MULTICAST_ADDRESS_INDEX = 0;


    public static void main(String[] args) {
        try {
            MulticastMember multicastMember = new MulticastMember(args[MULTICAST_ADDRESS_INDEX]);
            multicastMember.startMember();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
