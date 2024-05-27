package command.commandClasses;

import exception.IncorrectDataException;
import exception.NoElementException;
import exception.UnknowElementException;
import network.Request;
import network.Respons;

import java.io.Serial;

/**
Команда выводящая все элементы значение поля hairColor равно заданному.
 */
public class FilterByHairColorCommand implements Command {
    @Serial
    private final static long serialVersionUID = 15L;
    @Override
    public Respons execute(Request request, AbstractReciever reciver) throws UnknowElementException, IncorrectDataException, NoElementException {
        return reciver.filterByHairColor(request);
    }

    @Override
    public String getName() {
        return "filter_by_hair_color: ";
    }

    @Override
    public String getDescription() {
        return "вывести все элементы значение поля hairColor равно заданному";
    }
}
