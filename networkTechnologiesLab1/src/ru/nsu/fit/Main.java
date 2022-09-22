package ru.nsu.fit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

public class Main {
    private static final int MULTICAST_ADDRESS_INDEX = 0;
    private static final String DISCONNECT_WORD = "end";

    public static void main(String[] args) {
        try {
            MulticastMember multicastMember = new MulticastMember(args[MULTICAST_ADDRESS_INDEX]);
            multicastMember.startMember();
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String word = reader.readLine();
            if (Objects.equals(word, DISCONNECT_WORD)) {
                multicastMember.disconnectMember();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
