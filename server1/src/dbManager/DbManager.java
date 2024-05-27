package dbManager;

import data.*;
import exception.UserExistsException;
import network.PasswordEncryptor;
import network.Respons;
import network.User;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.LinkedHashMap;
import java.util.Properties;

public class DbManager {
    Connection connect;

    public DbManager() {
        try {
            InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("res.properties");
            Properties props = new Properties();
            props.load(in);
            String dbURL = props.getProperty("dbURL");
            String user = props.getProperty("user");
            String password = props.getProperty("password");
            String dbDriver = props.getProperty("dbDriver");


            Class.forName(dbDriver);
            connect = DriverManager.getConnection(dbURL, user, password);

            PreparedStatement preparedStatement = connect.prepareStatement(DDLQuery.createDbTables);
            int resultSet = preparedStatement.executeUpdate();

        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public LinkedHashMap<String, Person> createMap() {
        LinkedHashMap<String, Person> mapOfPersons = new LinkedHashMap<>();
        try {
            try {
                PreparedStatement preparedStatement = connect.prepareStatement(DMLQuery.addObjects);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    mapOfPersons.put(String.valueOf(resultSet.getInt(3)), new Person(resultSet.getInt(3), resultSet.getString(4), new Coordinates(resultSet.getDouble(5),
                            resultSet.getInt(6)), resultSet.getDouble(8),
                            EyeColor.valueOf(resultSet.getString(9)),
                            HairColor.valueOf(resultSet.getString(10)),
                            Country.valueOf(resultSet.getString(11)),
                            new Location(resultSet.getDouble(12), resultSet.getLong(13), resultSet.getInt(14))));
                }
                return mapOfPersons;


            } catch (SQLException e) {
                e.printStackTrace();
                System.err.println("Ошибка выполнения запроса");
                return new LinkedHashMap<>();
            }


        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            System.err.println("Поля объектов не валидны");
            return new LinkedHashMap<>();
        }
    }

    public void addUser(User user) throws UserExistsException {

        try {
            PreparedStatement preparedStatement = connect.prepareStatement(DMLQuery.checkUser);
            preparedStatement.setString(1, user.getUsername());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                throw new UserExistsException();
            }
            PasswordEncryptor passwordEncryptor = new PasswordEncryptor();
            String salt = passwordEncryptor.saltGenerator();
            String password = passwordEncryptor.hashPassword(user.getPassword() + salt);
            PreparedStatement pr = connect.prepareStatement(DMLQuery.addUser);
            pr.setString(1, user.getUsername());
            pr.setString(2, password);
            pr.setString(3, salt);
            pr.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean existUser(User user) {
        try {
            PasswordEncryptor passwordEncryptor = new PasswordEncryptor();
            PreparedStatement getSalt = connect.prepareStatement(DMLQuery.getSalt);
            getSalt.setString(1, user.getUsername());
            ResultSet getSaltResult = getSalt.executeQuery();
            if (!getSaltResult.next()) {
                return false;
            }
            String salt = getSaltResult.getString(1);
            String password = passwordEncryptor.hashPassword(user.getPassword() + salt);
            PreparedStatement preparedStatement = connect.prepareStatement(DMLQuery.getUserId);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println("SQL error");
        }
        return false;
    }

    public Respons addPerson(Person person, User user) {
        int user_id = 0;
        try {
            PasswordEncryptor passwordEncryptor = new PasswordEncryptor();
            PreparedStatement getSalt = connect.prepareStatement(DMLQuery.getSalt);
            getSalt.setString(1, user.getUsername());
            ResultSet getSaltResult = getSalt.executeQuery();
            if (!getSaltResult.next()) {
                return new Respons("0");
            }
            String salt = getSaltResult.getString(1);
            String password = passwordEncryptor.hashPassword(user.getPassword() + salt);
            PreparedStatement preparedStatement = connect.prepareStatement(DMLQuery.getUserId);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user_id = resultSet.getInt(1);
            }
            preparedStatement = connect.prepareStatement(DMLQuery.addPerson);
            preparedStatement.setInt(1, user_id);
            preparedStatement.setDouble(2, person.getId());
            preparedStatement.setString(3, person.getName());
            preparedStatement.setDouble(4, person.getCoordinates().getX());
            preparedStatement.setDouble(5, person.getCoordinates().getX());
            preparedStatement.setString(6, person.getCreationDate().toString());
            preparedStatement.setDouble(7, person.getHeight());
            preparedStatement.setString(8, person.getEyeColor().toString());
            preparedStatement.setString(9, person.getHairColor().toString());
            preparedStatement.setString(10, person.getNationality().toString());
            preparedStatement.setDouble(11, person.getLocation().getX());
            preparedStatement.setLong(12, person.getLocation().getY());
            preparedStatement.setInt(13, person.getLocation().getZ());

            resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                return new Respons("Не удалось добавить объект");
            }
            return new Respons("Объект был успешно добавлен");
        } catch (SQLException e) {
            return new Respons("Ошибка при выполнении запроса");
        }
    }

    public boolean deleteObject(int id, User user) {
        int user_id = 0;
        try {
            PasswordEncryptor passwordEncryptor = new PasswordEncryptor();
            PreparedStatement getSalt = connect.prepareStatement(DMLQuery.getSalt);
            getSalt.setString(1, user.getUsername());
            ResultSet getSaltResult = getSalt.executeQuery();
            if (!getSaltResult.next()) {
                return false;
            }
            String salt = getSaltResult.getString(1);
            String password = passwordEncryptor.hashPassword(user.getPassword() + salt);
            PreparedStatement preparedStatement = connect.prepareStatement(DMLQuery.getUserId);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user_id = resultSet.getInt(1);
            }
            preparedStatement = connect.prepareStatement(DMLQuery.deleteObject);
            preparedStatement.setInt(1, user_id);
            preparedStatement.setLong(2, id);
            resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean updateObject(int id, User user, Person person) {
        int user_id = 0;
        try {
            PasswordEncryptor passwordEncryptor = new PasswordEncryptor();
            PreparedStatement getSalt = connect.prepareStatement(DMLQuery.getSalt);
            getSalt.setString(1, user.getUsername());
            ResultSet getSaltResult = getSalt.executeQuery();
            if (!getSaltResult.next()) {
                return false;
            }
            String salt = getSaltResult.getString(1);
            String password = passwordEncryptor.hashPassword(user.getPassword() + salt);
            PreparedStatement preparedStatement = connect.prepareStatement(DMLQuery.getUserId);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                user_id = resultSet.getInt(1);
            }
            preparedStatement = connect.prepareStatement(DMLQuery.updateObject);

            preparedStatement.setDouble(1, person.getId());
            preparedStatement.setString(2, person.getName());
            preparedStatement.setDouble(3, person.getCoordinates().getX());
            preparedStatement.setDouble(4, person.getCoordinates().getX());
            preparedStatement.setString(5, person.getCreationDate().toString());
            preparedStatement.setDouble(6, person.getHeight());
            preparedStatement.setString(7, person.getEyeColor().toString());
            preparedStatement.setString(8, person.getHairColor().toString());
            preparedStatement.setString(9, person.getNationality().toString());
            preparedStatement.setDouble(10, person.getLocation().getX());
            preparedStatement.setLong(11, person.getLocation().getY());
            preparedStatement.setInt(12, person.getLocation().getZ());

            preparedStatement.setInt(13, user_id);
            preparedStatement.setInt(14, id);

            resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


}
