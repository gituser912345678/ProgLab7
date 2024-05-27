package command.commandClasses;

import exception.UnknowElementException;
import network.Request;
import network.Respons;

import java.io.Serial;

/**
Команда позволяет удалить элемент колекции с заданным ключом.
 */
public class RemoveKeyNullCommand implements Command {
    @Serial
    private final static long serialVersionUID = 21L;
    @Override
    public Respons execute(Request request, AbstractReciever reciver) throws UnknowElementException {
        return reciver.removeKey(request);
    }

    @Override
    public String getName() {
        return "remove_key null {element}: ";
    }

    @Override
    public String getDescription() {
        return "удалить элемент из коллекции по его ключу";
    }
}
