package network;

import collectionManagers.StartCommand;
import data.Person;
import data.generators.PersonGenerator;


import java.util.Objects;
import java.util.Scanner;

public class Console {
    public void execute() {

        StartCommand startCommand = new StartCommand();
        Scanner scanner = new Scanner(System.in);
        Client client = new Client();
        User user = new User(null, null);

        System.out.println("Зарегестрируйтесь register [логин] [пароль], если же у вас уже есть аккаунт то используйте команду login [логин] [пароль]");

        try {
            while (scanner.hasNext()) {

                String[] str = scanner.nextLine().split(" ");

                if (Objects.equals(str[0], "register")) {
                    user.setUsername(str[1]);
                    user.setPassword(str[2]);
                    System.out.println(client.sendRequest(new Request(user, startCommand.getCommandTable().get(str[0]))).getMessage());
                    user = new User(null, null);
                } else if (Objects.equals(str[0], "login")) {
                    user.setUsername(str[1]);
                    user.setPassword(str[2]);
                    System.out.println(client.sendRequest(new Request(user, startCommand.getCommandTable().get(str[0]))).getMessage());
                } else if (user.getUsername() != null && user.getPassword() != null && str[0].equals("exit_from_account")) {
                    user = new User(null, null);
                    System.out.println("Вы успешно вышли из аккаунта");
                } else if (user.getUsername() != null && user.getPassword() != null && str[0].equals("insert") || str[0].equals("update")) {
                    try {
                        Person person = PersonGenerator.createPerson(Integer.parseInt(str[1]));
                        System.out.println(client.sendRequest(new Request(user, startCommand.getCommandTable().get(str[0]), person, str)).getMessage());
                    } catch (Exception exception) {
                        System.out.println("Ключь обязательно должен быть числом.");
                    }
                } else if (user.getUsername() != null && user.getPassword() != null && str[0].equals("remove_lower") || str[0].equals("remove_greater")) {
                    Person person = PersonGenerator.createPerson(0);
                    System.out.println(client.sendRequest(new Request(user, startCommand.getCommandTable().get(str[0]), person, str)).getMessage());
                } else if (user.getUsername() != null && user.getPassword() != null && str[0].equals("execute_script") || str[0].equals("remove_key") || str[0].equals("filter_by_hair_color") || str[0].equals("remove_greater_key")) {
                    System.out.println(client.sendRequest(new Request(user, startCommand.getCommandTable().get(str[0]), str)).getMessage());
                } else if (str[0].equals("exit")) {
                    System.exit(0);
                } else if (user.getUsername() != null && user.getPassword() != null) {
                    System.out.println(client.sendRequest(new Request(user, startCommand.getCommandTable().get(str[0]))).getMessage());
                } else {
                    System.out.println("Войдите в аккаунт или зарегестрируйтесь.");
                }
            }
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }
}
