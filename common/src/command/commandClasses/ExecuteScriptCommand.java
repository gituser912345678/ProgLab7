package command.commandClasses;

import exception.IncorrectDataException;
import exception.NoElementException;
import exception.UnknowElementException;
import network.Request;
import network.Respons;

import java.io.IOException;
import java.io.Serial;

/**
Команда выполняет скрипт из указанного файла
 */
public class ExecuteScriptCommand implements Command {
    @Serial
    private final static long serialVersionUID = 13L;
    @Override
    public Respons execute(Request request, AbstractReciever reciver) throws UnknowElementException, IncorrectDataException, NoElementException, IOException {
        return reciver.executeScript(request);
    }

    @Override
    public String getName() {
        return "execute_script: ";
    }

    @Override
    public String getDescription() {
        return "исполняет скрипт из  указанного файла.";
    }
}
