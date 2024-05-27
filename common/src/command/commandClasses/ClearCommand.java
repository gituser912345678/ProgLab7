package command.commandClasses;

import exception.UnknowElementException;
import network.Request;
import network.Respons;

import java.io.Serial;

/**
*Команда удаляет все элементы коллекции.
*/
public class ClearCommand implements Command {
    @Serial
    private final static long serialVersionUID = 12L;

    public Respons execute(Request request, AbstractReciever reciver) throws UnknowElementException {
        return reciver.clear(request);
    }

    @Override
    public String getName() {
        return "clear: ";
    }

    @Override
    public String getDescription() {
        return "очистить коллекцию";
    }
}
