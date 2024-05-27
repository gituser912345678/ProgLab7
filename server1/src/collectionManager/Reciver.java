package collectionManager;

import command.commandClasses.AbstractReciever;
import command.commandClasses.Command;
import data.HairColor;
import data.Person;
import dbManager.DbManager;
import exception.NoElementException;
import exception.UnknowElementException;
import exception.UserExistsException;
import network.Request;
import network.Respons;
import network.User;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class Reciver implements AbstractReciever {

    public Respons login(Request request) {
        DbManager databaseManager = new DbManager();
        User user = request.getUser();
        if (databaseManager.existUser(user)) {
            return new Respons("Successfull authorization!");
        }
        return new Respons("User with this login and password does not exist");
    }

    @Override
    public Respons info() {
        return new Respons("Data type - " + CollectionManager.getMap().getClass().getName() + "\n" +
                "Count of persons - " + CollectionManager.getMap().size() + "\n" +
                "Init date - " + CollectionManager.getDate());
    }

    public Respons register(Request request) {

        User user = request.getUser();
        DbManager dbManager = new DbManager();

        try {
            dbManager.addUser(user);
            return new Respons("Пользователь успешно добавлен.");
        } catch (UserExistsException e) {
            return new Respons("User");
        }


    }

    public Respons clear(Request request) {
        User user = request.getUser();
        DbManager dbManager = new DbManager();
        LinkedHashMap<String, Person> map = CollectionManager.getMap();

        for (String key : map.keySet()) {
            if (dbManager.deleteObject(map.get(key).getId(), user)) {
                try {
                    CollectionManager.remove(key);
                } catch (UnknowElementException e) {
                    return new Respons("Такого элемента нет.");
                }
            }
        }

        return new Respons("Clear...........");
    }

    public Respons executeScript(Request request) {
        File file = new File(request.getArg()[1]);
        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        String respons = "";

        String line;
        try {
            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                kek:
                if (line.split(" ")[0].equals("insert")) {
                    ArrayList<String> array = new ArrayList<>();
                    String key = line.split(" ")[1];
                    StartCommand startCommand = new StartCommand();
                    while (!startCommand.getCommandTable().containsKey(line = scanner.nextLine())) {
                        if (scanner.hasNextLine()) {
                            if (startCommand.getCommandTable().containsKey(line.split(" ")[0])) {
                                startCommand.startExecute(new Request(request.getUser(), startCommand.getCommandTable().get(line.split(" ")[0])));
                            } else if (line.equals(" ") || line.isEmpty()) {
                                break kek;
                            } else {
                                array.add(line.split(" ")[0]);
                            }
                        } else {
                            break kek;
                        }
                    }
                    User user = request.getUser();
                    Person person = new Person(array);
                    DbManager dbManager = new DbManager();

                    dbManager.addPerson(person, user);
                    CollectionManager.add(key, person);
                    respons += "Персонаж успешно добавлен." + "\n";
                } else if (line.split(" ")[0].equals("update")) {
                    LinkedList<String> array = new LinkedList<>();
                    String key = line.split(" ")[1];
                    StartCommand startCommand = new StartCommand();
                    while (!startCommand.getCommandTable().containsKey(line = scanner.nextLine())) {
                        if (scanner.hasNextLine()) {
                            if (line.equals(" ") || line.isEmpty()) {
                                break kek;
                            } else {
                                array.add(line.split(" ")[0]);
                            }
                        } else {
                            System.out.println("...");
                            break kek;
                        }
                    }
                    CollectionManager.add(key, new Person(array));
                    respons += "Персонаж успешно обновлён." + "\n";
                } else if (line.split(" ")[0].equals("execute_script") && line.split(" ").length < 2) {
                    return new Respons("Я не буду выполнять команду execute_scrip");
                } else {
                    StartCommand startCommand = new StartCommand();
                    if (startCommand.getCommandTable().containsKey(line.split(" ")[0])) {
                        respons += startCommand.startExecute(new Request(request.getUser(), startCommand.getCommandTable().get(line.split(" ")[0]))).getMessage() + "\n";
                    } else {
                        respons += "..." + "\n";
                    }
                }
            }
        } catch (Exception | NoElementException e) {
            return new Respons(e.getMessage());
        }
        return new Respons(respons);
    }

    public Respons filterByHairColor(Request request) {
        try {
            LinkedHashMap<String, Person> map = CollectionManager.getMap();
            HairColor hairColor = HairColor.valueOf(request.getArg()[1]);
            String str = "";
            Map<String, Person> map1 = map.entrySet().stream().filter(x -> x.getValue().getHairColor() == hairColor).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
            for (String key : map1.keySet()) {
                str += map.get(key) + " ";
            }
            return new Respons(str);
        } catch (Exception e) {
            return new Respons(e.getMessage());
        }
    }

    public Respons help() {
        StartCommand startCommand = new StartCommand();
        LinkedHashMap<String, Command> commandTable = startCommand.getCommandTable();
        String str = "";
        for (String s : commandTable.keySet()) {
            str += commandTable.get(s).getName() + " " + commandTable.get(s).getDescription() + "\n";
        }
        return new Respons(str);
    }

    public Respons insert(Request request) {
        try {

            User user = request.getUser();
            Person person = request.getPerson();
            DbManager dbManager = new DbManager();

            dbManager.addPerson(person, user);
            Integer.parseInt(request.getArg()[1]);
            CollectionManager.add(request.getArg()[1], person);

            return new Respons("Персонаж успешно добавлен");
        } catch (Exception exception) {
            return new Respons(exception.getMessage());
        }


    }

    public Respons removeGreater(Request request) {

        User user = request.getUser();

        DbManager dbManager = new DbManager();
        LinkedHashMap<String, Person> map = CollectionManager.getMap();

        Person person = request.getPerson();
        for (Map.Entry<String, Person> p : map.entrySet()) {
            if (person.getHeight() < p.getValue().getHeight()) {
                dbManager.deleteObject(p.getValue().getId(), user);
                try {
                    CollectionManager.remove(p.getKey());
                } catch (UnknowElementException e) {
                    System.out.println("Такого элемента не существует.");
                }
            }
        }
        return new Respons("Ненужные элементы успешно удалены из коллекции");
    }

    public Respons removeGreaterKey(Request request) {

        User user = request.getUser();

        DbManager dbManager = new DbManager();
        LinkedHashMap<String, Person> map = CollectionManager.getMap();

        Person person = request.getPerson();
        for (Map.Entry<String, Person> p : map.entrySet()) {
            if (person.getId() < p.getValue().getId()) {
                dbManager.deleteObject(p.getValue().getId(), user);
                try {
                    CollectionManager.remove(p.getKey());
                } catch (UnknowElementException e) {
                    System.out.println("Такого элемента не существует.");
                }
            }
        }
        return new Respons("Ненужные элементы успешно удалены из коллекции");
    }

    public Respons removeKey(Request request) {
        try {

            User user = request.getUser();
            DbManager dbManager = new DbManager();
            String key = request.getArg()[1];
            Person person = CollectionManager.getMap().get(key);

            if (!dbManager.deleteObject(person.getId(), user)) {
                return new Respons("Что-то пошло не так.");
            } else {
                CollectionManager.remove(request.getArg()[1]);
                return new Respons("Ненужные элементы успешно удалены из коллекции");
            }

        } catch (Exception exception) {
            return new Respons(exception.getMessage());
        }
    }

    public Respons removeLower(Request request) {

        User user = request.getUser();

        DbManager dbManager = new DbManager();
        LinkedHashMap<String, Person> map = CollectionManager.getMap();

        Person person = request.getPerson();
        for (Map.Entry<String, Person> p : map.entrySet()) {
            if (person.getHeight() > p.getValue().getHeight()) {
                dbManager.deleteObject(p.getValue().getId(), user);
                try {
                    CollectionManager.remove(p.getKey());
                } catch (UnknowElementException e) {
                    System.out.println("Такого элемента не существует.");
                }
            }
        }
        return new Respons("Ненужные элементы успешно удалены из коллекции");
    }

    public Respons show() {
        try {
            LinkedHashMap<String, Person> map = CollectionManager.getMap();
            String str = "";
            if (map.isEmpty()) {
                return new Respons(CollectionManager.getMap().getClass().getName() + " is empty");
            } else {
                for (String s : map.keySet()) {
                    str += "Key: <" + s + "> " + map.get(s) + "\n";
                }
                return new Respons(str);
            }
        } catch (Exception e) {
            return new Respons(e.getMessage());
        }
    }

    public Respons updateId(Request request) {
        try {

            Integer id = Integer.parseInt(request.getArg()[1]);
            User user = request.getUser();
            DbManager dbManager = new DbManager();

            if (dbManager.updateObject(Integer.parseInt(request.getArg()[1]), user, request.getPerson())){
                for (String key : CollectionManager.getMap().keySet()) {
                    if (Objects.equals(CollectionManager.getMap().get(key).getId(), id)) {
                        Person person = request.getPerson();
                        CollectionManager.remove(key);
                        CollectionManager.add(key, person);
                    }
                }
                return new Respons("Объект коллекции успешно обновлён.");
            }
            return new Respons("Не получилось обновить указанный элемент коллекции.");
        } catch (Exception e) {
            return new Respons("Не получилось обновить указанный элемент коллекции.");
        }
    }
}
