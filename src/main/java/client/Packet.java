package client;

import java.io.Serializable;

public class Packet implements Serializable {
    private String a;
    int b = 10;

    public Packet(String a) {
        this.a = a;
    }

    public String getA() {
        return a;
    }
}
