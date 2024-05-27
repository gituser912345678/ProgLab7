package command.commandClasses;

import network.Request;
import network.Respons;

import java.io.Serial;

/**
Команда позволяющая добавить новый элемент в коллекцию.
 */
public class InsertNullCommand implements Command {
    @Serial
    private final static long serialVersionUID = 17L;

    @Override
    public Respons execute(Request request, AbstractReciever reciver) {
        return reciver.insert(request);
    }

    @Override
    public String getName() {
        return "insert null: ";
    }

    @Override
    public String getDescription() {
        return "добавить новый элемент с заданным ключом";
    }
}
