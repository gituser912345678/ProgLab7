package command.commandClasses;

import exception.IncorrectDataException;
import exception.UnknowElementException;
import network.Request;
import network.Respons;

import java.io.Serial;

/**
Команда удаляет из колекции все элементы ключ которых превышает заданный.
 */
public class RemoveGreaterKeyCommand implements Command {
    @Serial
    private final static long serialVersionUID = 20L;

    @Override
    public Respons execute(Request request, AbstractReciever reciver) throws UnknowElementException, IncorrectDataException {
        return reciver.removeGreaterKey(request);
    }

    @Override
    public String getName() {
        return "remove_greater_key: ";
    }

    @Override
    public String getDescription() {
        return "удалить из  коллекции все элементы ключ которых превышает заданный";
    }
}
