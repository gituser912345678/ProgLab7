package command.commandClasses;

import network.Request;
import network.Respons;

import java.io.Serial;
import java.io.Serializable;

public class Register implements Command, Serializable {

    @Serial
    private final static long serialVersionUID = 1333213333L;

    @Override
    public Respons execute(Request request, AbstractReciever abstractReciever) {
        return abstractReciever.register(request);
    }

    @Override
    public String getName() {
        return "register";
    }

    @Override
    public String getDescription() {
        return "";
    }
}
