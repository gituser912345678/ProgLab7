package collectionManagers;

import command.commandClasses.*;

import java.util.LinkedHashMap;

/**
 * Класс содержащий команды и вызывающий их.
 */
public class StartCommand {
    private LinkedHashMap<String, Command> commandTable;

    public StartCommand() {
        commandTable = new LinkedHashMap<>();
        commandTable.put("help", new HelpCommand());
        commandTable.put("show", new ShowCommand());
        commandTable.put("info", new InfoCommand());
        commandTable.put("insert", new InsertNullCommand());
        commandTable.put("remove_key", new RemoveKeyNullCommand());
        commandTable.put("update", new UpdateIdCommand());
        commandTable.put("clear", new ClearCommand());
        commandTable.put("remove_greater", new RemoveGreaterCommand());
        commandTable.put("remove_lower", new RemoveLowerCommand());
        commandTable.put("remove_greater_key", new RemoveGreaterKeyCommand());
        commandTable.put("filter_by_hair_color", new FilterByHairColorCommand());
        commandTable.put("execute_script", new ExecuteScriptCommand());
        commandTable.put("login", new Login());
        commandTable.put("register", new Register());
    }

    public LinkedHashMap<String, Command> getCommandTable() {
        return commandTable;
    }
}
