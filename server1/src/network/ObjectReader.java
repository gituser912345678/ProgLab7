package network;

import java.io.IOException;
import java.io.ObjectInputStream;

public class ObjectReader {
    private Request request;
    public void objectReader(ObjectInputStream userRequest) throws IOException, ClassNotFoundException {
        request = (Request) userRequest.readObject();
    }

    public Request getRequest() {
        return request;
    }
}
