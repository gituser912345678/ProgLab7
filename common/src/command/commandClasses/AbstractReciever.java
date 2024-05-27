package command.commandClasses;

import network.Request;
import network.Respons;

public interface AbstractReciever {
   Respons info();

    Respons clear(Request request);

    Respons executeScript(Request request);

    Respons filterByHairColor(Request request);

    Respons help();

    Respons insert(Request request);

    Respons removeGreater(Request request);

    Respons removeGreaterKey(Request request);

    Respons removeKey(Request request);

    Respons removeLower(Request request);

    Respons show();

    Respons updateId(Request request);
    Respons register(Request request);
    Respons login(Request request);
}
