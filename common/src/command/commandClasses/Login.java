package command.commandClasses;

import network.Request;
import network.Respons;

import java.io.Serial;
import java.io.Serializable;

public class Login implements Command, Serializable {

    @Serial
    private final static long serialVersionUID = 13332152353L;

    @Override
    public Respons execute(Request request, AbstractReciever abstractReciever) {
        return abstractReciever.login(request);
    }

    @Override
    public String getName() {
        return "login";
    }

    @Override
    public String getDescription() {
        return "";
    }
}
