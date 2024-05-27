package command.commandClasses;

import network.Request;
import network.Respons;

import java.io.Serial;
import java.io.Serializable;

/**
Команда выводящая подсказку по доступным командам.
 */
public class HelpCommand implements Command, Serializable {
    @Serial
    private final static long serialVersionUID = 11L;
    @Override
    public Respons execute(Request request, AbstractReciever reciver) {
        return reciver.help();
    }

    @Override
    public String getName() {
        return "help: ";
    }

    @Override
    public String getDescription() {
        return "вывести справку по доступным командам";
    }


}
