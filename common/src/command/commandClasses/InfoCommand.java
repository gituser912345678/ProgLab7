package command.commandClasses;

import network.Request;
import network.Respons;

import java.io.Serial;

/**
 * Команда выводящая информацию о коллекции.
 */
public class InfoCommand implements Command {
    @Serial
    private final static long serialVersionUID = 16L;

    @Override
    public Respons execute(Request request, AbstractReciever reciver) {
        return reciver.info();
    }

    @Override
    public String getName() {
        return "info: ";
    }

    @Override
    public String getDescription() {
        return "вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)";
    }
}
