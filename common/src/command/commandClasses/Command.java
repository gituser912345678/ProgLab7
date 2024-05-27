package command.commandClasses;


import exception.IncorrectDataException;
import exception.NoElementException;
import exception.UnknowElementException;
import network.Request;
import network.Respons;

import java.io.IOException;
import java.io.Serializable;

public interface Command extends Serializable {
    Respons execute(Request request, AbstractReciever T) throws UnknowElementException, IncorrectDataException, NoElementException, IOException;
    String getName();
    String getDescription();
}
