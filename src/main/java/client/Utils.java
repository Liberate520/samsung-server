package client;

import java.io.*;

public class Utils {
    public static Packet read(byte[] buf) {
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(buf));
            Packet myList = (Packet) objectInputStream.readObject();
            objectInputStream.close();
            return myList;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static byte[] write(Packet myList) throws IOException{
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(buf);
        objectOutputStream.writeObject(myList);
        objectOutputStream.flush();
        objectOutputStream.close();
        return buf.toByteArray();
    }
}
