package command.commandClasses;

import exception.IncorrectDataException;
import exception.UnknowElementException;
import network.Request;
import network.Respons;

import java.io.Serial;

/**
Удаляет из коллекции все элементы превышающие заданный.
 */
public class RemoveGreaterCommand implements Command {
    @Serial
    private final static long serialVersionUID = 19L;
    @Override
    public Respons execute(Request request, AbstractReciever reciver) throws UnknowElementException, IncorrectDataException {
        return reciver.removeGreater(request);
    }

    @Override
    public String getName() {
        return "remove_greater {element}: ";
    }

    @Override
    public String getDescription() {
        return "удалить из коллекции все элементы, превышающие заданный";
    }
}
