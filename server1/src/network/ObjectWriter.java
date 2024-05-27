package network;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class ObjectWriter {
    public void objectWriter(ObjectOutputStream toUser, Respons respons) throws IOException {
        toUser.writeObject(respons);
        toUser.flush();
    }
}
