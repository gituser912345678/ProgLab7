package command.commandClasses;

import exception.IncorrectDataException;
import exception.UnknowElementException;
import network.Request;
import network.Respons;

import java.io.Serial;

/**
Команда позволяет удалить из коллекции все элементы, меньшие, чем заданный.
 */
public class RemoveLowerCommand implements Command {
    @Serial
    private final static long serialVersionUID = 22L;
    @Override
    public Respons execute(Request request, AbstractReciever reciver) throws UnknowElementException, IncorrectDataException {
        return reciver.removeLower(request);
    }

    @Override
    public String getName() {
        return "remove lower: ";
    }

    @Override
    public String getDescription() {
        return "удалить из коллекции все элементы, меньшие, чем заданный";
    }
}
