package command.commandClasses;

import exception.NoElementException;
import network.Request;
import network.Respons;

import java.io.Serial;

/**
Команда позволяет обновить значение элемента коллекции, id которого равен заданному.
 */
public class UpdateIdCommand implements Command {
    @Serial
    private final static long serialVersionUID = 27L;
    @Override
    public Respons execute(Request request, AbstractReciever reciver) throws NoElementException {
        return reciver.updateId(request);
    }

    @Override
    public String getName() {
        return "update id: ";
    }

    @Override
    public String getDescription() {
        return "обновить значение элемента коллекции, id которого равен заданному";
    }
}